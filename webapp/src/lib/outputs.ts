import rawCache from './output-cache.json'

// The site never runs Java to show a lesson: `npm run capture-outputs` compiles and runs every code
// block of every lesson with a real JDK and stores what each one printed (src/lib/output-cache.json).

export type BlockStatus = 'ok' | 'runtime-error' | 'compile-error' | 'no-main' | 'gui' | 'timeout' | 'launch-error'

/** [lesson line that printed it, text, stream]: stream 0 is normal output, 1 is the error stream. */
export type OutputSegment = [number, string, number]

/** One run of a block, with the input that was typed for it. */
export interface Scenario {
  label: string
  inputs: string[]
  stdout: string
  segments: OutputSegment[]
  timedOut?: boolean
  launchError?: string
}

export interface BlockRun {
  status: BlockStatus
  mainClass: string | null
  compile?: string
  /** The lesson shows this block failing on purpose, to explain the error. */
  expectError?: boolean
  scenarios: Scenario[]
}

export interface LessonRuns {
  capturedAt: string
  java: string
  /** Keyed by the line the code block starts on. */
  blocks: Record<string, BlockRun>
}

const cache = rawCache as unknown as Record<string, LessonRuns>

export function getRuns(path: string): LessonRuns | undefined {
  return cache[path]
}

/** True when a block has something to show: it ran and printed, or it did not compile. */
export function blockHasResult(run: BlockRun | undefined): run is BlockRun {
  if (!run) return false
  if (run.status === 'compile-error') return true
  return run.scenarios.some((s) => s.stdout.trim() !== '')
}

// -------------------------------------------------------------------------------------------------
// A single lesson-level view, for the few places that show one preview of a lesson (the home page's
// peek, the capture dialog's "include the output").
// -------------------------------------------------------------------------------------------------

export interface CapturedOutput {
  status: 'ok' | 'empty'
  stdout: string
  runs: LessonRuns
}

/** The output of a whole lesson read as one: every block's first run, in order. */
export function getOutput(path: string): CapturedOutput | undefined {
  const runs = getRuns(path)
  if (!runs) return undefined
  const stdout = Object.entries(runs.blocks)
    .sort((a, b) => Number(a[0]) - Number(b[0]))
    .map(([, block]) => block.scenarios[0]?.stdout.trim() ?? '')
    .filter(Boolean)
    .join('\n\n')
  return { status: stdout ? 'ok' : 'empty', stdout, runs }
}

/** What the blocks overlapping the given lines printed (each block's first run), or '' for none. */
export function outputForLines(runs: LessonRuns | undefined, blockLines: { line: number; end: number }[], chosen: Set<number>): string {
  if (!runs) return ''
  return blockLines
    .filter((block) => [...chosen].some((n) => n >= block.line && n <= block.end))
    .map((block) => runs.blocks[String(block.line)]?.scenarios[0]?.stdout.trim() ?? '')
    .filter(Boolean)
    .join('\n\n')
}
