export interface FoldRange {
  /** 1-indexed line of the compound-statement header, e.g. "def foo():" */
  start: number
  /** 1-indexed line of the last line inside that indented block */
  end: number
}

function indentWidth(line: string): number {
  const match = line.match(/^[ \t]*/)
  if (!match) return 0
  return match[0].replace(/\t/g, '    ').length
}

/** Brace-based fold detection for Java: a `{` that is closed later folds everything between. */
function computeJavaFoldRanges(code: string): FoldRange[] {
  const lines = code.split('\n')
  const ranges: FoldRange[] = []
  const open: number[] = []
  let inBlockComment = false
  lines.forEach((raw, index) => {
    // Braces inside strings, characters and comments do not count.
    let line = ''
    let i = 0
    while (i < raw.length) {
      const two = raw.slice(i, i + 2)
      if (inBlockComment) {
        const end = raw.indexOf('*/', i)
        if (end === -1) break
        inBlockComment = false
        i = end + 2
      } else if (two === '//') {
        break
      } else if (two === '/*') {
        inBlockComment = true
        i += 2
      } else if (raw[i] === '"' || raw[i] === "'") {
        const quote = raw[i++]
        while (i < raw.length && raw[i] !== quote) i += raw[i] === '\\' ? 2 : 1
        i++
      } else {
        line += raw[i++]
      }
    }
    for (const ch of line) {
      if (ch === '{') open.push(index + 1)
      else if (ch === '}') {
        const start = open.pop()
        if (start !== undefined && index > start) ranges.push({ start, end: index })
      }
    }
  })
  return ranges
}

/**
 * Indentation-based fold detection for Python: any line ending in `:` (a
 * compound statement — def/class/if/for/while/try/with/else/...) followed by
 * more-indented lines is foldable, matching how editors fold Python source
 * without needing a full parser.
 */
export function computeFoldRanges(code: string): FoldRange[] {
  if (/^\s*(\/\*|\/\/\/|import |package |public |class )/.test(code)) return computeJavaFoldRanges(code)
  const lines = code.split('\n')
  const ranges: FoldRange[] = []

  for (let i = 0; i < lines.length; i++) {
    const line = lines[i]
    const trimmed = line.trim()
    if (!trimmed || trimmed.startsWith('#')) continue
    if (!/:\s*(#.*)?$/.test(line)) continue

    const headerIndent = indentWidth(line)
    let end = i
    let sawContent = false
    let j = i + 1

    while (j < lines.length) {
      const next = lines[j]
      if (next.trim() === '') {
        j++
        continue
      }
      if (indentWidth(next) > headerIndent) {
        end = j
        sawContent = true
        j++
      } else {
        break
      }
    }

    if (sawContent && end > i) {
      ranges.push({ start: i + 1, end: end + 1 })
    }
  }

  return ranges
}
