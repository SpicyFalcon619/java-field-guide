// Splits a lesson file into its code blocks, the same way the site's Guided view does:
// lines starting with /// (three slashes, at column 0) are explanation, and the code between them is a
// block. Ordinary // comments are always code, wherever they sit.
// Every block is a complete program. This file has to agree with src/lib/lesson.ts about where
// blocks start, because the captured output is looked up by the block's first line.

// A file that exists before the block runs:  /// @file example.txt: first line | second line
const FILE_DIRECTIVE = /^\/\/\/\s*@file\s+([^:]+):\s*(.*)$/
// This block is MEANT to fail (to show an error message):  /// @error
const ERROR_DIRECTIVE = /^\/\/\/\s*@error\b/
const INPUT_DIRECTIVE = /^\/\/\/\s*@input(?:\(([^)]*)\))?:?\s*(.*)$/

/** The title comment at the top of a lesson: /* ... *\/ . Returns the index of the line after it. */
export function skipTitleComment(lines) {
  const first = lines.findIndex((l) => l.trim())
  if (first === -1 || !lines[first].trim().startsWith('/*')) return 0
  let j = first
  while (j < lines.length && !lines[j].includes('*/')) j++
  return j + 1
}

/** Scans Java source, ignoring strings, characters and comments, and calls onChar for real code characters. */
function scanCode(code, onChar) {
  let i = 0
  while (i < code.length) {
    const ch = code[i]
    const two = code.slice(i, i + 2)
    if (two === '//') {
      while (i < code.length && code[i] !== '\n') i++
    } else if (two === '/*') {
      const end = code.indexOf('*/', i + 2)
      i = end === -1 ? code.length : end + 2
    } else if (code.startsWith('"""', i)) {
      const end = code.indexOf('"""', i + 3)
      i = end === -1 ? code.length : end + 3
    } else if (ch === '"' || ch === "'") {
      i++
      // A string or character literal cannot span lines, so an unclosed one ends at the line break.
      while (i < code.length && code[i] !== ch && code[i] !== '\n') i += code[i] === '\\' ? 2 : 1
      if (code[i] === ch) i++
    } else {
      onChar(ch, i)
      i++
    }
  }
}

/** The top-level types (class, interface, enum, record) of a block, with their source ranges. */
export function topLevelTypes(code) {
  const types = []
  let depth = 0
  scanCode(code, (ch, i) => {
    if (ch === '{') {
      if (depth === 0) {
        const head = code.slice(Math.max(0, code.lastIndexOf('}', i) + 1), i)
        const match = head.match(/(?:^|\s)(class|interface|enum|record)\s+(\w+)[^{;]*$/)
        types.push({ name: match?.[2] ?? null, isPublic: /(^|\s)public\s/.test(head), open: i, close: -1 })
      }
      depth++
    } else if (ch === '}') {
      depth--
      if (depth === 0 && types.length) types[types.length - 1].close = i
    }
  })
  return types.filter((t) => t.name)
}

/** Which class has main(), and which name the file must have (the public class, else the main class). */
export function describeBlock(code) {
  const types = topLevelTypes(code)
  const main = types.find((t) => t.close > t.open && /static\s+void\s+main\s*\(/.test(code.slice(t.open, t.close)))
  const publicType = types.find((t) => t.isPublic)
  return { mainClass: main?.name ?? null, fileClass: publicType?.name ?? main?.name ?? null }
}

export function splitBlocks(source) {
  const lines = source.replace(/\r\n/g, '\n').split('\n')
  const blocks = []
  let code = []
  const flush = () => {
    while (code.length && !code[code.length - 1].text.trim()) code.pop()
    while (code.length && !code[0].text.trim()) code.shift()
    if (code.length) {
      const text = code.map((c) => c.text).join('\n')
      blocks.push({ line: code[0].idx + 1, code: text, inputs: [], files: [], expectError: false, ...describeBlock(text) })
    }
    code = []
  }
  for (let i = skipTitleComment(lines); i < lines.length; i++) {
    const line = lines[i]
    if (!line.startsWith('///')) {
      code.push({ idx: i, text: line })
      continue
    }
    if (ERROR_DIRECTIVE.test(line)) {
      flush()
      const lastBlock = blocks[blocks.length - 1]
      if (lastBlock) lastBlock.expectError = true
      continue
    }
    const fileDirective = line.match(FILE_DIRECTIVE)
    if (fileDirective) {
      flush()
      const lastBlock = blocks[blocks.length - 1]
      if (lastBlock) lastBlock.files.push({ name: fileDirective[1].trim(), content: fileDirective[2].split(' | ').join('\n') + '\n' })
      continue
    }
    const directive = line.match(INPUT_DIRECTIVE)
    if (directive && blocks.length + (code.length ? 1 : 0) > 0) {
      // The directive belongs to the block just above it, so close that block first.
      flush()
      const lastBlock = blocks[blocks.length - 1]
      if (lastBlock) lastBlock.inputs.push({ label: (directive[1] ?? '').trim(), lines: directive[2].split(' | ').map((s) => s.trim()) })
      continue
    }
    flush()
  }
  flush()
  return blocks
}
