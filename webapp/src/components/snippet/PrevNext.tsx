import { Link } from 'react-router-dom'
import { ArrowLeft, ArrowRight } from 'lucide-react'
import type { Snippet } from '@/types'
import { useLibrary } from '@/hooks/useLibrary'
import { DoneButton } from '@/components/snippet/DoneButton'
import { cn } from '@/lib/cn'

// Two quiet arrows for the top of a lesson, so a reader can step back or on without scrolling to the end. They are
// icons only: the lesson's name shows on hover and is read out by screen readers. At the very start or end of the path
// the missing arrow stays in place, faded, so the pair never changes shape from one lesson to the next.
function Arrow({ lesson, direction }: { lesson?: Snippet; direction: 'prev' | 'next' }) {
  const next = direction === 'next'
  const Icon = next ? ArrowRight : ArrowLeft
  const label = lesson ? `${next ? 'Next' : 'Previous'} lesson: ${lesson.title}` : next ? 'No next lesson' : 'No previous lesson'
  const shape =
    'inline-flex h-8 w-8 items-center justify-center rounded-lg border border-[var(--color-border)] text-[var(--color-ink-dim)] transition-colors'

  if (!lesson) {
    return (
      <span role="img" aria-label={label} className={cn(shape, 'opacity-35')}>
        <Icon className="h-4 w-4" aria-hidden />
      </span>
    )
  }
  return (
    <Link
      to={`/s/${lesson.id}`}
      title={label}
      aria-label={label}
      className={cn(shape, 'hover:border-[var(--color-border-strong)] hover:bg-[var(--color-surface)] hover:text-[var(--color-ink)]')}
    >
      <Icon className="h-4 w-4" aria-hidden />
    </Link>
  )
}

export function LessonArrows({ prev, next }: { prev?: Snippet; next?: Snippet }) {
  return (
    <nav aria-label="Previous and next lesson" className="flex shrink-0 items-center gap-1.5">
      <Arrow lesson={prev} direction="prev" />
      <Arrow lesson={next} direction="next" />
    </nav>
  )
}

// Two plain doors at the end of a lesson: back, and on. Moving on never changes your progress: marking a lesson
// done is its own choice, offered just above them (and again at the top of the lesson).
function Door({ lesson, from, direction }: { lesson: Snippet; from: Snippet; direction: 'prev' | 'next' }) {
  const next = direction === 'next'
  const Icon = next ? ArrowRight : ArrowLeft
  const otherSection = lesson.categoryLabel !== from.categoryLabel
  return (
    <Link
      to={`/s/${lesson.id}`}
      className={cn(
        'group flex min-h-20 flex-col justify-center gap-1 rounded-[4px] border border-[var(--color-border)] bg-[var(--color-surface)] px-4 py-3 transition-colors hover:border-[var(--color-accent)] hover:bg-[var(--color-surface-hover)]',
        next ? 'items-end text-right sm:col-start-2' : 'items-start text-left',
      )}
    >
      <span className="inline-flex items-center gap-1.5 text-xs font-semibold tracking-wider text-[var(--color-ink-faint)] uppercase transition-colors group-hover:text-[var(--color-accent-soft)]">
        {!next && <Icon className="h-3.5 w-3.5 transition-transform group-hover:-translate-x-0.5" aria-hidden />}
        {next ? 'Next lesson' : 'Previous lesson'}
        {next && <Icon className="h-3.5 w-3.5 transition-transform group-hover:translate-x-0.5" aria-hidden />}
      </span>
      <span className="text-[15px] font-medium text-[var(--color-ink)]">{lesson.title}</span>
      {otherSection && <span className="text-xs text-[var(--color-ink-dim)]">{lesson.categoryLabel}</span>}
    </Link>
  )
}

export function PrevNext({ snippet, prev, next }: { snippet: Snippet; prev?: Snippet; next?: Snippet }) {
  const { isDone } = useLibrary()
  // Data files are shown in the guide but aren't something you finish (the top of the page hides the button for them too).
  const finishable = !snippet.filename.endsWith('.csv')
  if (!prev && !next && !finishable) return null

  return (
    <div className="mt-14 border-t border-[var(--color-border)] pt-6">
      {finishable && (
        <div className="mb-5 flex flex-wrap items-center justify-between gap-x-4 gap-y-2">
          <p className="text-sm text-[var(--color-ink-dim)]">{isDone(snippet.id) ? 'You have finished this lesson.' : 'Finished this lesson?'}</p>
          <DoneButton id={snippet.id} />
        </div>
      )}
      {(prev || next) && (
        <nav aria-label="Lesson navigation" className="grid gap-3 sm:grid-cols-2">
          {prev && <Door lesson={prev} from={snippet} direction="prev" />}
          {next && <Door lesson={next} from={snippet} direction="next" />}
        </nav>
      )}
    </div>
  )
}
