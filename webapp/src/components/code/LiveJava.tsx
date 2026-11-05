import { useCallback, useEffect, useId, useRef, useState, type FormEvent } from 'react'
import { Play, Square, Undo2, X } from 'lucide-react'
import { javaRunner, JavaRunStopped, useJavaStatus, type JavaStream } from '@/lib/javaRunner'
import JavaEditor from '@/components/code/JavaEditor'
import { cn } from '@/lib/cn'

type ChunkStream = JavaStream | 'in'
interface Chunk {
  stream: ChunkStream
  text: string
}

interface LiveJavaProps {
  initialCode: string
  /** The number shown beside the first line, so a lesson's blocks keep their real line numbers. */
  firstLine?: number
  variant?: 'block' | 'page'
  label?: string
  onCodeChange?: (code: string) => void
  /** Run as soon as the editor appears (the reader pressed Run on a lesson's block). */
  autoRun?: boolean
  /** This code, compiled when the site was built. While the code is unedited it runs without the compiler. */
  prebuilt?: { jar: string; mainClass: string }
  /** Lets a lesson's block go back to its plain code and sample output. */
  onClose?: () => void
}

// A page of output is plenty; a runaway loop printing forever must not freeze the tab.
const OUTPUT_LIMIT = 100_000

const reads = (code: string) => /System\s*\.\s*in\b/.test(code)

const button =
  'flex min-h-6 cursor-pointer items-center gap-1.5 rounded-md border px-2.5 py-0.5 text-[13px] transition-colors disabled:cursor-not-allowed disabled:opacity-50'
const quiet =
  'border-[var(--color-code-border)] bg-[var(--color-code-surface)] text-[var(--color-code-ink-dim)] hover:border-[var(--color-code-border-strong)] hover:bg-[var(--color-code-surface-hover)] hover:text-[var(--color-code-ink)]'

export default function LiveJava({
  initialCode,
  firstLine = 1,
  variant = 'block',
  label = 'Editable Java code',
  onCodeChange,
  autoRun = false,
  prebuilt,
  onClose,
}: LiveJavaProps) {
  const owner = useId()
  const status = useJavaStatus()
  const [code, setCode] = useState(initialCode)
  const [chunks, setChunks] = useState<Chunk[]>([])
  const [running, setRunning] = useState(false)
  const [outcome, setOutcome] = useState<{ ok: boolean; ms: number } | null>(null)
  const [note, setNote] = useState('')
  const [line, setLine] = useState('')
  const inputBox = useRef<HTMLInputElement>(null)

  // Output arrives in many small pieces; paint it once per frame.
  const buffer = useRef<Chunk[]>([])
  const total = useRef(0)
  const frame = useRef(0)
  const flush = useCallback(() => {
    frame.current = 0
    const pending = buffer.current
    buffer.current = []
    if (!pending.length) return
    setChunks((current) => {
      const next = current.slice()
      for (const piece of pending) {
        const last = next[next.length - 1]
        if (last && last.stream === piece.stream) next[next.length - 1] = { stream: last.stream, text: last.text + piece.text }
        else next.push(piece)
      }
      return next
    })
  }, [])
  const append = useCallback(
    (stream: ChunkStream, text: string) => {
      if (total.current >= OUTPUT_LIMIT) return
      total.current += text.length
      if (total.current >= OUTPUT_LIMIT) {
        text = text.slice(0, Math.max(0, text.length - (total.current - OUTPUT_LIMIT))) + '\n... output cut off here ...\n'
      }
      buffer.current.push({ stream, text })
      if (!frame.current) frame.current = requestAnimationFrame(flush)
    },
    [flush],
  )
  useEffect(() => () => cancelAnimationFrame(frame.current), [])

  const edit = (next: string) => {
    setCode(next)
    onCodeChange?.(next)
    // Edited code needs the compiler, which is slow the first time: start getting it ready while they type.
    javaRunner.warmUp()
  }

  const run = useCallback(async () => {
    if (running) return
    setRunning(true)
    setChunks([])
    setOutcome(null)
    setNote('')
    setLine('')
    buffer.current = []
    total.current = 0
    try {
      const result = await javaRunner.run({
        code,
        owner,
        onOutput: (stream, text) => append(stream, text),
        prebuilt: prebuilt && code === initialCode ? prebuilt : undefined,
      })
      flush()
      setOutcome(result)
    } catch (error) {
      flush()
      if (error instanceof JavaRunStopped) setNote('Stopped. Java restarts on the next run, so the first run after a stop takes a little longer.')
      else append('err', `${error instanceof Error ? error.message : String(error)}\n`)
    } finally {
      flush()
      setRunning(false)
    }
  }, [running, code, owner, prebuilt, initialCode, append, flush])

  // A block opened with Run starts straight away. The guard stops React's double effect from running it twice.
  const started = useRef(false)
  useEffect(() => {
    if (autoRun && !started.current) {
      started.current = true
      void run()
    }
  }, [autoRun, run])

  // Leaving while the program waits for typing would leave it stuck for good: end its input so it can finish.
  useEffect(
    () => () => {
      const current = javaRunner.getStatus()
      if (current.owner === owner && current.phase === 'waiting') javaRunner.endInput()
    },
    [owner],
  )

  const waiting = running && status.phase === 'waiting' && status.owner === owner
  useEffect(() => {
    if (waiting) inputBox.current?.focus()
  }, [waiting])

  const submit = (event: FormEvent) => {
    event.preventDefault()
    append('in', `${line}\n`)
    javaRunner.sendLine(line)
    setLine('')
  }
  const endInput = () => {
    append('in', '(input ended)\n')
    javaRunner.endInput()
  }

  const busyElsewhere = !running && status.owner !== null && status.owner !== owner && (status.phase === 'running' || status.phase === 'waiting' || status.phase === 'compiling')
  const changed = code !== initialCode
  const progress = running && (status.phase === 'loading' || status.phase === 'warming' || status.phase === 'compiling') ? status.message : ''
  const showOutput = chunks.length > 0 || note || outcome || waiting

  return (
    <div className={cn('live', variant === 'page' && 'live-page')}>
      <JavaEditor value={code} onChange={edit} onRun={() => void run()} firstLine={firstLine} label={label} />

      <div className="guide-part-bar">
        <div className="flex flex-wrap items-center gap-2">
          {running ? (
            <button type="button" onClick={() => javaRunner.stop()} className={cn(button, 'border-[var(--color-code-ember)] text-[var(--color-code-ember-soft)] hover:bg-[var(--color-code-surface-hover)]')}>
              <Square className="h-3 w-3 fill-current" aria-hidden /> Stop
            </button>
          ) : (
            <button
              type="button"
              onClick={() => void run()}
              className={cn(button, 'border-[var(--color-code-accent-glow)] bg-[var(--color-code-accent-glow)]/20 text-[var(--color-code-accent-soft)] hover:bg-[var(--color-code-accent-glow)]/35')}
            >
              <Play className="h-3 w-3" aria-hidden /> Run
            </button>
          )}
          <span className="hidden text-xs text-[var(--color-code-ink-faint)] sm:inline">Ctrl+Enter</span>
        </div>
        <div className="flex flex-wrap items-center gap-2">
          {changed && (
            <button type="button" onClick={() => edit(initialCode)} className={cn(button, quiet)}>
              <Undo2 className="h-3 w-3" aria-hidden /> Revert
            </button>
          )}
          {onClose && (
            <button type="button" onClick={onClose} className={cn(button, quiet)} title="Back to the lesson's code and sample output">
              <X className="h-3 w-3" aria-hidden /> Close
            </button>
          )}
        </div>
      </div>

      {reads(code) && !running && !outcome && (
        <p className="live-hint">This program reads from the keyboard. When it asks, a box appears below the output: type your answer and press Enter.</p>
      )}

      <div className="live-status" role="status" aria-live="polite">
        {progress || (busyElsewhere ? 'Java is busy with another block. This one will run when that finishes.' : '')}
      </div>

      {showOutput && (
        <div className="guide-part-out" role="region" aria-label="Output">
          {(chunks.length > 0 || waiting) && (
            <pre tabIndex={0}>
              {chunks.map((chunk, index) => (
                <span key={index} className={chunk.stream === 'err' || chunk.stream === 'build' ? 'live-err' : chunk.stream === 'in' ? 'live-typed' : undefined}>
                  {chunk.text}
                </span>
              ))}
            </pre>
          )}
          {waiting && (
            <form className="live-stdin" onSubmit={submit}>
              <label htmlFor={`${owner}-in`}>Your turn to type</label>
              <input
                id={`${owner}-in`}
                ref={inputBox}
                value={line}
                onChange={(event) => setLine(event.target.value)}
                autoComplete="off"
                spellCheck={false}
                placeholder="Type here, then press Enter"
              />
              <button type="submit" className={cn(button, 'border-[var(--color-code-accent-glow)] bg-[var(--color-code-accent-glow)]/20 text-[var(--color-code-accent-soft)]')}>
                Send
              </button>
              <button type="button" onClick={endInput} className={cn(button, quiet)} title="Like pressing Ctrl+D: nothing more will be typed">
                End input
              </button>
            </form>
          )}
          {note && <p className="guide-part-none">{note}</p>}
          {outcome && chunks.length === 0 && !note && <p className="guide-part-none">The program ran and printed nothing.</p>}
          {outcome && <p className="live-meta">{outcome.ok ? `Finished in ${(outcome.ms / 1000).toFixed(2)} s` : 'Stopped with an error'}</p>}
        </div>
      )}
    </div>
  )
}
