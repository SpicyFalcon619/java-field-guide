#!/usr/bin/env node
// Compiles java/LiveRunner.java into public/java/live.jar, the small program that runs inside CheerpJ
// (Java in the browser) and drives a visitor's code. The jar is committed, so a site build needs no JDK;
// run this again only after editing LiveRunner.java:  npm run build-runner
import { spawnSync } from 'node:child_process'
import { mkdirSync, mkdtempSync, rmSync } from 'node:fs'
import { dirname, join } from 'node:path'
import { fileURLToPath } from 'node:url'
import os from 'node:os'

const root = join(dirname(fileURLToPath(import.meta.url)), '..')
const outDir = join(root, 'public', 'java')
const work = mkdtempSync(join(os.tmpdir(), 'live-runner-'))
mkdirSync(outDir, { recursive: true })

// --release 11 keeps the classes readable by every Java version CheerpJ can start.
const compiled = spawnSync('javac', ['--release', '11', '-d', work, join(root, 'java', 'LiveRunner.java')], { encoding: 'utf8' })
if (compiled.status !== 0) {
  console.error(compiled.stderr || compiled.stdout)
  process.exit(1)
}
const packed = spawnSync('jar', ['--create', '--file', join(outDir, 'live.jar'), '-C', work, '.'], { encoding: 'utf8' })
rmSync(work, { recursive: true, force: true })
if (packed.status !== 0) {
  console.error(packed.stderr || packed.stdout)
  process.exit(1)
}
console.log('Wrote public/java/live.jar')
