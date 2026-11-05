import { Fragment, useEffect, useMemo, useState, type ReactNode } from 'react'
import { Play } from 'lucide-react'
import { chunkTransformer, getHighlighter, SHIKI_THEME_BY_SITE_THEME } from '@/lib/highlighter'
import type { BannerSection, LessonBlock, ProseNode } from '@/lib/lesson'
import { blockHasResult, type BlockRun, type CapturedOutput } from '@/lib/outputs'
import type { Topic } from '@/types'
import { useTheme } from '@/hooks/useTheme'
import { CopyButton } from '@/components/code/CopyButton'
import { FontSizeControl } from '@/components/code/FontSizeControl'
import { CaptureDialogButton } from '@/components/code/CaptureDialog'
import { cn } from '@/lib/cn'

interface GuidedLessonProps {
  /** Shown at the left of the toolbar (the lesson's view switcher). */
  leading?: ReactNode
  blocks: LessonBlock[]
  code: string
  filename: string
  topics: Topic[]
  /** What every code block of this lesson printed when it was compiled and run with a real JDK. */
  output?: CapturedOutput
  focusLine?: number
}

// Words in prose that are really code: `backticked`, name(...) calls, __dunders__.
const INLINE_CODE = /(`[^`]+`|(?<!\w)\.?[A-Za-z_][\w.]*\((?:[^()]|\([^()]*\))*\)|__\w+__)/g

function Inline({ text }: { text: string }) {
  return (
    <>
      {text.split(INLINE_CODE).map((part, index) =>
        index % 2 === 1 ? (
          <code key={index} className="guide-inline-code">
            {part.startsWith('`') ? part.slice(1, -1) : part}
          </code>
        ) : (
          <Fragment key={index}>{part}</Fragment>
        ),
      )}
    </>
  )
}

function Prose({ nodes }: { nodes: ProseNode[] }) {
  return (
    <div className="guide-prose">
      {nodes.map((node, index) => {
        switch (node.type) {
          case 'sub':
            return (
              <h3 key={index} className="guide-sub">
                {node.text}
              </h3>
            )
          case 'pre':
            return (
              <pre key={index} className="guide-pre" tabIndex={0}>
                {node.text}
              </pre>
            )
          case 'list': {
            const List = node.ordered ? 'ol' : 'ul'
            return (
              <List key={index} className={node.ordered ? 'guide-list guide-list-ordered' : 'guide-list'}>
                {node.items.map((item, i) => (
                  <li key={i}>
                    <Inline text={item} />
                  </li>
                ))}
              </List>
            )
          }
          default:
            return node.callout ? (
              <div key={index} role="note" className="guide-callout">
                <p className={node.breaks ? 'guide-breaks' : undefined}>
                  <span className="guide-callout-label">{node.callout}.</span>
                  <Inline text={node.text} />
                </p>
              </div>
            ) : (
              <p key={index} className={node.breaks ? 'guide-breaks' : undefined}>
                <Inline text={node.text} />
              </p>
            )
        }
      })}
    </div>
  )
}

function Banner({ kind, sections }: { kind: 'learn' | 'practice' | 'note'; sections: BannerSection[] }) {
  return (
    <section className={cn('guide-card', kind === 'practice' && 'guide-card-practice')}>
      {sections.map((section, index) => (
        <div key={index} className={index > 0 ? 'mt-4' : undefined}>
          {section.heading && (
            <h2 className="guide-card-title">
              {section.heading}
              {section.subtitle && <span className="guide-card-subtitle"> {section.subtitle}</span>}
            </h2>
          )}
          {groupItems(section).map((group, gi) =>
            group.type === 'text' ? (
              <p key={gi} className="guide-card-text">
                <Inline text={group.text} />
              </p>
            ) : group.type === 'pre' ? (
              <pre key={gi} className="guide-pre" tabIndex={0}>
                {group.text}
              </pre>
            ) : (
              <ul key={gi} className={cn('guide-card-list', group.ordered && 'guide-card-list-ordered')}>
                {group.items.map((item, ii) => (
                  <li key={ii}>
                    <Inline text={item} />
                  </li>
                ))}
              </ul>
            ),
          )}
        </div>
      ))}
    </section>
  )
}

type ItemGroup =
  | { type: 'text'; text: string }
  | { type: 'pre'; text: string }
  | { type: 'list'; ordered: boolean; items: string[] }

// Consecutive bullets (or numbers) become one list; text and preformatted lines stay as they are.
function groupItems(section: BannerSection): ItemGroup[] {
  const groups: ItemGroup[] = []
  for (const item of section.items) {
    if (item.kind === 'bullet' || item.kind === 'number') {
      const ordered = item.kind === 'number'
      const last = groups[groups.length - 1]
      if (last?.type === 'list' && last.ordered === ordered) last.items.push(item.text)
      else groups.push({ type: 'list', ordered, items: [item.text] })
    } else {
      groups.push(item.kind === 'pre' ? { type: 'pre', text: item.text } : { type: 'text', text: item.text })
    }
  }
  return groups
}


// One code block's own run bar. Every block is a complete program, so "Run" shows what it printed when it
// was compiled and run for real (captured with a JDK, instantly), and a block with several typed-input
// scenarios lets the reader pick which one to look at.
function PartRun({
  run,
  open,
  onToggle,
  scenario,
  onScenario,
}: {
  run: BlockRun
  open: boolean
  onToggle: () => void
  scenario: number
  onScenario: (index: number) => void
}) {
  const current = run.scenarios[Math.min(scenario, run.scenarios.length - 1)]
  const broken = run.status === 'compile-error'
  // Output and error text interleave in the order they happened; the error stream is drawn as an error.
  const pieces: { text: string; error: boolean }[] = []
  for (const [, text, stream] of current?.segments ?? []) {
    const error = stream === 1
    const last = pieces[pieces.length - 1]
    if (last && last.error === error) last.text += text
    else pieces.push({ text, error })
  }
  return (
    <>
      <div className="guide-part-bar">
        <button
          type="button"
          onClick={onToggle}
          aria-expanded={open}
          className={cn(
            'flex min-h-6 cursor-pointer items-center gap-1.5 rounded-md border px-2.5 py-0.5 text-[13px] transition-colors',
            open
              ? 'border-[var(--color-code-accent-glow)] bg-[var(--color-code-accent-glow)]/20 text-[var(--color-code-accent-soft)]'
              : 'border-[var(--color-code-border)] bg-[var(--color-code-surface)] text-[var(--color-code-ink-dim)] hover:border-[var(--color-code-border-strong)] hover:bg-[var(--color-code-surface-hover)] hover:text-[var(--color-code-ink)]',
          )}
        >
          <Play className="h-3 w-3" aria-hidden /> {open ? 'Hide output' : broken ? 'Show the error' : 'Run'}
        </button>
        {open && run.scenarios.length > 1 && (
          <label className="flex items-center gap-2 text-xs text-[var(--color-code-ink-faint)]">
            Typed input
            <select
              value={Math.min(scenario, run.scenarios.length - 1)}
              onChange={(event) => onScenario(Number(event.target.value))}
              className="min-h-6 max-w-[16rem] cursor-pointer rounded-md border border-[var(--color-code-border)] bg-[var(--color-code-surface)] px-1.5 text-[13px] text-[var(--color-code-ink)]"
            >
              {run.scenarios.map((s, index) => (
                <option key={index} value={index}>
                  {s.label || `Input ${index + 1}`}
                </option>
              ))}
            </select>
          </label>
        )}
      </div>
      {open && (
        <div className="guide-part-out" role="region" aria-label="Output of this block">
          {broken ? (
            <pre tabIndex={0} className="live-err">{run.compile}</pre>
          ) : pieces.length ? (
            <pre tabIndex={0}>
              {pieces.map((piece, index) => (
                <span key={index} className={piece.error ? 'live-err' : undefined}>
                  {piece.text}
                </span>
              ))}
            </pre>
          ) : (
            <p className="guide-part-none">This block prints nothing.</p>
          )}
        </div>
      )}
    </>
  )
}

export function GuidedLesson({ leading, blocks, code, filename, topics, output, focusLine }: GuidedLessonProps) {
  const [siteTheme] = useTheme()
  const [chunks, setChunks] = useState<Record<number, string>>({})
  const [openParts, setOpenParts] = useState<Set<number>>(() => new Set())
  const [scenarios, setScenarios] = useState<Record<number, number>>({})

  const togglePart = (line: number) =>
    setOpenParts((current) => {
      const next = new Set(current)
      if (!next.delete(line)) next.add(line)
      return next
    })

  const codeBlocks = useMemo(
    () => blocks.filter((b): b is Extract<LessonBlock, { type: 'code' }> => b.type === 'code'),
    [blocks],
  )

  useEffect(() => {
    let cancelled = false
    getHighlighter().then((highlighter) => {
      if (cancelled) return
      const next: Record<number, string> = {}
      for (const block of codeBlocks) {
        next[block.line] = highlighter.codeToHtml(block.code, {
          lang: 'java',
          theme: SHIKI_THEME_BY_SITE_THEME[siteTheme],
          transformers: [chunkTransformer(block.line)],
        })
      }
      setChunks(next)
    })
    return () => {
      cancelled = true
    }
  }, [codeBlocks, siteTheme])

  // Jump to a line from a search result / link. Prose has no per-line elements, so
  // fall back to the nearest earlier line that exists.
  const ready = Object.keys(chunks).length > 0
  useEffect(() => {
    if (!ready || !focusLine) return
    for (let n = focusLine; n >= 1; n--) {
      const el = document.getElementById(`L${n}`)
      if (!el) continue
      el.scrollIntoView({ behavior: 'smooth', block: 'center' })
      el.classList.add('line-flash')
      const timer = window.setTimeout(() => el.classList.remove('line-flash'), 1800)
      return () => window.clearTimeout(timer)
    }
  }, [ready, focusLine])

  const body: ReactNode[] = blocks.map((block) => {
    switch (block.type) {
      case 'banner':
        return (
          <div key={`b${block.line}`} id={`L${block.line}`}>
            <Banner kind={block.kind} sections={block.sections} />
          </div>
        )
      case 'topic':
        return (
          <h2 key={`t${block.line}`} id={`L${block.line}`} className="guide-topic">
            {block.eyebrow && <span className="guide-topic-num">{block.eyebrow.replace(/^Topic\s+/, '')}</span>}
            {block.title}
          </h2>
        )
      case 'prose':
        return (
          <div key={`p${block.line}`} id={`L${block.line}`} className="scroll-mt-24">
            <Prose nodes={block.nodes} />
          </div>
        )
      case 'code': {
        const run = output?.runs.blocks[String(block.line)]
        return (
          <div
            key={`c${block.line}`}
            className="guide-code code-font"
            style={{ ['--line-start' as string]: block.line - 1 }}
          >
            {chunks[block.line] ? (
              <div className="overflow-x-auto py-2.5" tabIndex={0} role="group" aria-label="Code, scrollable" dangerouslySetInnerHTML={{ __html: chunks[block.line] }} />
            ) : (
              <pre className="overflow-x-auto px-4 py-2.5 font-mono text-[var(--color-code-ink-dim)]" tabIndex={0}>{block.code}</pre>
            )}
            {blockHasResult(run) && (
              <PartRun
                run={run}
                open={openParts.has(block.line)}
                onToggle={() => togglePart(block.line)}
                scenario={scenarios[block.line] ?? 0}
                onScenario={(index) => setScenarios((current) => ({ ...current, [block.line]: index }))}
              />
            )}
          </div>
        )
      }
    }
  })

  return (
    <div className="guide">
      <div className="guide-toolbar" data-code-actions>
        {leading ?? <span className="font-mono text-xs text-[var(--color-code-ink-faint)]">{filename}</span>}
        <div className="flex flex-wrap items-center gap-2">
          <span className="hidden sm:contents">
            <FontSizeControl />
          </span>
          <CopyButton text={code} />
          <span className="hidden sm:contents">
            <CaptureDialogButton code={code} filename={filename} topics={topics} output={output} />
          </span>
        </div>
      </div>

      <div className="guide-body">{body}</div>
    </div>
  )
}
