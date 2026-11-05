#!/usr/bin/env node
// Compares what the examples REALLY print (the capture cache) with the output the author wrote down in
// Notion (tools/expected.json, produced by tools/build_lessons.py). A line the author recorded that the
// code does not print is reported: either the code changed, or the note was wrong.
import { readFileSync } from 'node:fs'
import { dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'

const __dirname = dirname(fileURLToPath(import.meta.url))
const cache = JSON.parse(readFileSync(join(__dirname, '..', 'src', 'lib', 'output-cache.json'), 'utf8'))
const expected = JSON.parse(readFileSync(join(__dirname, '..', '..', 'tools', 'expected.json'), 'utf8'))

const norm = (s) => s.replace(/\s+/g, ' ').trim()
let checked = 0
let withNote = 0
const problems = []

for (const [lesson, records] of Object.entries(expected)) {
  for (const record of records) {
    const block = cache[lesson]?.blocks?.[record.line]
    if (!block) continue
    checked++
    const note = record.output
    if (!note || !note.trim()) continue
    withNote++
    if (block.status === 'no-main' || block.status === 'gui') continue
    const printed = new Set(block.scenarios.flatMap((s) => s.stdout.split('\n').map(norm)).filter(Boolean))
    const missing = note
      .split('\n')
      .map(norm)
      .filter(Boolean)
      .filter((line) => !printed.has(line))
    if (missing.length) problems.push({ lesson, line: record.line, file: record.file, missing })
  }
}

console.log(`${checked} examples checked, ${withNote} with a recorded output.`)
if (!problems.length) {
  console.log('Every recorded output line is printed by the code.')
} else {
  console.log(`\n${problems.length} examples differ from the recorded output:\n`)
  for (const p of problems) {
    console.log(`${p.lesson.split('/').slice(-1)[0]}:${p.line}  (${p.file})`)
    for (const m of p.missing.slice(0, 4)) console.log(`    not printed: ${m}`)
  }
  process.exitCode = 1
}
