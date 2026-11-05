#!/usr/bin/env node
// Compiles and runs every code block of every lesson with a real JDK, and caches what each one printed,
// so the site can show real output next to the code without running anything in the browser.
// Re-run whenever a lesson is added or edited:  npm run capture-outputs  [-- <part of a lesson path>]
//
// A lesson is a .java file made of explanation (comment lines at column 0) and code blocks (everything
// in between). Every block is a complete program with its own main(). Typed input for a block is declared
// right after it:   /// @input Noman | 35      or      /// @input(Failing mark): 30
// A file the block needs to find:   /// @file example.txt: first line | second line
// (` | ` separates the lines typed for each Scanner call; each @input line is one scenario).
import { spawn, spawnSync } from 'node:child_process'
import { copyFileSync, mkdirSync, mkdtempSync, readFileSync, readdirSync, rmSync, writeFileSync, existsSync } from 'node:fs'
import { delimiter, dirname, extname, join, relative } from 'node:path'
import { fileURLToPath } from 'node:url'
import os from 'node:os'
import { splitBlocks } from './lib/blocks.mjs'

const __dirname = dirname(fileURLToPath(import.meta.url))
const repoRoot = join(__dirname, '..', '..')
const outputPath = join(__dirname, '..', 'src', 'lib', 'output-cache.json')
const filter = process.argv[2]

const EXCLUDED = new Set(['webapp', 'Test', 'tools', 'node_modules'])
const contentDirs = readdirSync(repoRoot, { withFileTypes: true })
  .filter((e) => e.isDirectory() && !e.name.startsWith('.') && !EXCLUDED.has(e.name))
  .map((e) => e.name)

const walk = (dir) =>
  readdirSync(dir, { withFileTypes: true }).flatMap((e) => {
    const full = join(dir, e.name)
    return e.isDirectory() ? walk(full) : e.isFile() && extname(e.name) === '.java' ? [full] : []
  })

const javaVersion = spawnSync('java', ['-version'], { encoding: 'utf8' })
if (javaVersion.error) {
  console.error('No JDK found on the PATH (need `java` and `javac`).')
  process.exit(1)
}
const versionLine = (javaVersion.stderr || javaVersion.stdout).split('\n')[0]
console.log(`Using ${versionLine}\n`)

// Runner is compiled once and shared by every run.
const work = mkdtempSync(join(os.tmpdir(), 'jfg-capture-'))
const runtimeDir = join(work, 'rt')
mkdirSync(runtimeDir)
copyFileSync(join(__dirname, '..', 'java', 'Runner.java'), join(runtimeDir, 'Runner.java'))
const built = spawnSync('javac', ['-d', runtimeDir, join(runtimeDir, 'Runner.java')], { encoding: 'utf8' })
if (built.status !== 0) {
  console.error('Could not compile Runner.java:\n' + built.stderr)
  process.exit(1)
}

// Every compile and every run is a JVM. Starting many at once exhausts memory on a small machine, so
// only a few run at a time, and a start that failed for lack of memory is tried again.
const MAX_JOBS = 3
let active = 0
const waiting = []
const acquire = () =>
  new Promise((resolve) => {
    if (active < MAX_JOBS) {
      active++
      resolve()
    } else waiting.push(resolve)
  })
const release = () => {
  const next = waiting.shift()
  if (next) next()
  else active--
}

async function run(cmd, args, options) {
  for (let attempt = 0; attempt < 3; attempt++) {
    await acquire()
    let result
    try {
      result = await runOnce(cmd, args, options)
    } finally {
      release()
    }
    if (!/paging file|Could not reserve enough space|commit_memory/.test(result.stderr)) return result
    await new Promise((r) => setTimeout(r, 1500))
  }
  return await runOnce(cmd, args, options)
}

function runOnce(cmd, args, options) {
  return new Promise((resolve) => {
    const child = spawn(cmd, args, { ...options, stdio: ['ignore', 'pipe', 'pipe'] })
    let stdout = ''
    let stderr = ''
    let timedOut = false
    const timer = setTimeout(() => {
      timedOut = true
      child.kill('SIGKILL')
    }, options.timeout ?? 20000)
    child.stdout.on('data', (d) => (stdout += d))
    child.stderr.on('data', (d) => (stderr += d))
    child.on('error', (error) => resolve({ status: -1, stdout, stderr: String(error), timedOut }))
    child.on('close', (status) => {
      clearTimeout(timer)
      resolve({ status, stdout, stderr, timedOut })
    })
  })
}

const GUI = /\bjavax\.swing\b|\bjava\.awt\b|\bJFrame\b|\bJOptionPane\b|\bjavafx\b/

async function captureBlock(lessonKey, index, block) {
  const dir = join(work, `${lessonKey.replace(/[^a-z0-9]+/gi, '_')}_${index}`)
  mkdirSync(dir, { recursive: true })
  const entry = { mainClass: block.mainClass ?? null, scenarios: [], expectError: block.expectError || undefined }
  if (!block.mainClass) return { ...entry, status: 'no-main' }
  if (GUI.test(block.code)) return { ...entry, status: 'gui' }

  const fileName = `${block.fileClass}.java`
  writeFileSync(join(dir, fileName), block.code)
  for (const file of block.files) writeFileSync(join(dir, file.name), file.content)
  const classes = join(dir, 'classes')
  mkdirSync(classes)
  const compiled = await run('javac', ['-J-Xmx256m', '-J-XX:+UseSerialGC', '-J-XX:TieredStopAtLevel=1', '-g', '-encoding', 'UTF-8', '-Xlint:none', '-cp', runtimeDir, '-d', classes, join(dir, fileName)], { cwd: dir })
  if (compiled.status !== 0) {
    const message = (compiled.stderr || compiled.stdout).replaceAll(dir, '').replace(/\r/g, '').trim()
    return { ...entry, status: 'compile-error', compile: message }
  }

  const scenarios = block.inputs.length ? block.inputs : [{ label: '', lines: [] }]
  let worst = 'ok'
  for (const [s, scenario] of scenarios.entries()) {
    const inputFile = join(dir, `input${s}.txt`)
    writeFileSync(inputFile, scenario.lines.join('\n') + (scenario.lines.length ? '\n' : ''))
    const resultFile = join(dir, `result${s}.json`)
    const result = await run('java', ['-Xmx128m', '-XX:+UseSerialGC', '-XX:TieredStopAtLevel=1', '-Dfile.encoding=UTF-8', '-cp', [runtimeDir, classes].join(delimiter), 'Runner', block.mainClass, fileName, resultFile, inputFile], { cwd: dir, timeout: 15000 })
    let segments = []
    if (existsSync(resultFile)) {
      try {
        segments = JSON.parse(readFileSync(resultFile, 'utf8')).segments
      } catch {
        // A killed run leaves no result.
      }
    }
    // Line numbers in the result belong to this block's own file; shift them to the lesson's.
    const shifted = segments.map(([line, text, stream]) => [line > 0 ? line + block.line - 1 : block.line, text.replace(/\r\n/g, '\n'), stream])
    const stdout = shifted.map(([, text]) => text).join('')
    // A run that left no result and a non-zero exit never really ran: say why instead of showing nothing.
    const launchError = !segments.length && result.status !== 0 && !result.timedOut ? (result.stderr || `exit code ${result.status}`).trim().slice(0, 400) : undefined
    if (launchError) worst = 'launch-error'
    if (result.timedOut) worst = 'timeout'
    else if (shifted.some(([, , stream]) => stream === 1) && /Exception in thread/.test(stdout)) worst = worst === 'ok' ? 'runtime-error' : worst
    entry.scenarios.push({
      label: scenario.label || (scenarios.length > 1 ? `Input ${s + 1}` : ''),
      inputs: scenario.lines,
      stdout,
      segments: shifted,
      timedOut: result.timedOut || undefined,
      launchError,
    })
  }
  return { ...entry, status: worst }
}

const cache = {}
const counts = { 'launch-error': 0, ok: 0, 'runtime-error': 0, 'compile-error': 0, 'no-main': 0, gui: 0, timeout: 0 }
const problems = []
let lessons = 0

for (const dir of contentDirs) {
  for (const file of walk(join(repoRoot, dir))) {
    const relPath = relative(repoRoot, file).split('\\').join('/')
    if (filter && !relPath.includes(filter)) continue
    const blocks = splitBlocks(readFileSync(file, 'utf8'))
    lessons++
    const results = await Promise.all(blocks.map((block, i) => captureBlock(relPath, i, block)))
    const byLine = {}
    blocks.forEach((block, i) => {
      byLine[block.line] = results[i]
      counts[results[i].status] = (counts[results[i].status] ?? 0) + 1
      if (results[i].status === 'compile-error' && !block.expectError) problems.push(`${relPath}:${block.line}  compile error\n${results[i].compile.split('\n').slice(0, 4).join('\n')}`)
      if (block.expectError && !['compile-error', 'runtime-error'].includes(results[i].status)) problems.push(`${relPath}:${block.line}  was marked @error but ran fine`)
      if (results[i].status === 'timeout') problems.push(`${relPath}:${block.line}  timed out`)
      if (results[i].status === 'launch-error') problems.push(`${relPath}:${block.line}  did not run: ${results[i].scenarios.map((x) => x.launchError).filter(Boolean)[0]}`)
    })
    cache[relPath] = { capturedAt: new Date().toISOString(), java: versionLine, blocks: byLine }
    console.log(`${String(blocks.length).padStart(3)} blocks  ${relPath}`)
  }
}

// A partial run (a filter) updates only those lessons.
let existing = {}
if (filter && existsSync(outputPath)) existing = JSON.parse(readFileSync(outputPath, 'utf8'))
// Drop lessons that no longer exist, so a deleted or renamed lesson does not leave stale output behind.
for (const key of Object.keys(existing)) if (!existsSync(join(repoRoot, key))) delete existing[key]
writeFileSync(outputPath, JSON.stringify({ ...existing, ...cache }, null, 1) + '\n')
rmSync(work, { recursive: true, force: true })

console.log(`\n${lessons} lessons: ${Object.entries(counts).map(([k, v]) => `${v} ${k}`).join(', ')}`)
if (problems.length) {
  console.log('\nProblems:\n' + problems.join('\n\n'))
  process.exitCode = 1
}
