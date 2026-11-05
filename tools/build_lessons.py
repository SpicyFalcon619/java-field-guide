#!/usr/bin/env python3
"""Builds the Theory lessons from a runnable export of the author's Notion codes.

The export is a folder per topic ("01_Basic" ... "27_File_Handling"), each holding a 00_Notes.md
(headings, explanations and "> Example: [`ExNN_Name.java`]" links) and the runnable examples.
This script turns each topic into one lesson file in the site's format:

  - explanation lines start with /// (three slashes) at column 0
  - every example is a complete program, with its public class renamed to Main (as in Notion)
  - the trailing /* Input: ... Output: ... */ comment is removed; typed input is kept as
    /// @input lines, and the expected output is saved to tools/expected.json so that
    `npm run verify-outputs` can compare it with what the code really prints.

Usage: python tools/build_lessons.py ["path to the export"]
"""
import glob
import json
import os
import re
import sys
import textwrap

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = sys.argv[1] if len(sys.argv) > 1 else r'D:\D Drive\Codes\Java\VS Code Testing'

# The author's own order, gathered into sections.
SECTIONS = [
    ('Java Basics', ['01', '02', '03', '04', '05', '06']),
    ('Classes and Objects', ['07', '08', '09', '10', '11', '12', '13', '14', '15']),
    ('Inheritance and Polymorphism', ['16', '17']),
    ('Encapsulation and Access', ['18', '19', '20']),
    ('Working with Objects', ['21', '22', '23', '24']),
    ('Abstraction and Interfaces', ['25']),
    ('Exceptions and Files', ['26', '27']),
]
SECTION_OF = {num: (i + 1, name) for i, (name, nums) in enumerate(SECTIONS) for num in nums}

EMOJI = re.compile('[\U0001F300-\U0001FAFF\u2705\u274C\u2714\u2716\u26A0]')


def slug(text):
    return re.sub(r'[^a-z0-9]+', '_', text.lower()).strip('_')


def clean(text):
    """Notion export artifacts and decorative emoji out; bold markers become plain text."""
    text = text.replace('****', '')
    text = re.sub(r'\*\*(.+?)\*\*', r'\1', text)
    text = text.replace('**', '')
    text = text.replace('\u2794', '->')
    text = EMOJI.sub('', text)
    return re.sub(r' {2,}', ' ', text).strip()


def wrap(text, indent=''):
    return textwrap.wrap(text, width=96, initial_indent=indent, subsequent_indent=indent) or ['']


def table_lines(rows):
    cells = [[clean(c) for c in r.strip().strip('|').split('|')] for r in rows]
    cells = [r for r in cells if not all(re.fullmatch(r':?-{2,}:?', c.strip()) for c in r if c.strip())]
    widths = [max(len(r[i]) if i < len(r) else 0 for r in cells) for i in range(max(len(r) for r in cells))]
    return ['    ' + '   '.join((r[i] if i < len(r) else '').ljust(widths[i]) for i in range(len(widths))).rstrip() for r in cells]


# ---------------------------------------------------------------------------------------------
# Examples
# ---------------------------------------------------------------------------------------------
TRAILER = re.compile(r'\n/\*\s*\n(?P<body>.*?)\*/\s*$', re.S)


def parse_trailer(body):
    """Splits the Input:/Output: comment into (input_text, output_text)."""
    inline_in = re.search(r'^Input:[ \t]*(\S.*)$', body, re.M)
    block_in = re.search(r'^Input:[ \t]*\n(.*?)(?=^Output:|\Z)', body, re.M | re.S)
    inp = None
    if inline_in:
        inp = inline_in.group(1).strip()
    elif block_in:
        inp = block_in.group(1).strip('\n')
    out_inline = re.search(r'^Output:[ \t]*(\S.*)$', body, re.M)
    out_block = re.search(r'^Output:[ \t]*\n(.*)\Z', body, re.M | re.S)
    out = None
    if out_inline:
        out = out_inline.group(1).strip()
    elif out_block:
        out = out_block.group(1).strip('\n')
    return inp, out


def prompts_of(code):
    """Text the program prints with print(...) (no newline): these are what it asks the user for."""
    found = re.findall(r'System\.out\.print\("((?:[^"\\]|\\.)*)"\)', code)
    return [bytes(p, 'utf-8').decode('unicode_escape') for p in found]


def typed_inputs(code, inp, out):
    """Works out what was typed for each run from the saved transcript. Returns a list of scenarios (lists of lines)."""
    prompts = [p for p in prompts_of(code) if p.strip()]
    reads = len(re.findall(r'\.next\w*\(|\.readLine\(', code))
    scenarios = []
    if inp and '\n' not in inp:
        # "Input: 2" on one line: the typed values, split when the program reads several.
        values = inp.split() if reads > 1 else [inp]
        return [values]
    transcript = inp if inp else (out or '')
    group = []
    for line in transcript.split('\n') + ['']:
        if not line.strip():
            if group:
                scenarios.append(group)
                group = []
            continue
        for p in sorted(prompts, key=len, reverse=True):
            if line.startswith(p):
                group.append(line[len(p):].strip())
                break
    return [g for g in scenarios if g]


OVERRIDES_PATH = os.path.join(ROOT, 'tools', 'input_overrides.json')
OVERRIDES = json.load(open(OVERRIDES_PATH, encoding='utf-8')) if os.path.exists(OVERRIDES_PATH) else {}
WARNINGS = []


def load_example(path):
    src = open(path, encoding='utf-8').read().replace('\r\n', '\n')
    expected_in = expected_out = None
    m = TRAILER.search(src)
    if m:
        expected_in, expected_out = parse_trailer(m.group('body'))
        src = src[:m.start()]
    # The export renamed some classes to avoid clashes between files; that note means nothing in a lesson.
    code = '\n'.join(l for l in src.split('\n') if 'does not clash with other files' not in l).rstrip() + '\n'
    code = re.sub(r'\bEx\d+_\w+\b', 'Main', code)
    code = EMOJI.sub('', code)
    scenarios = typed_inputs(code, expected_in, expected_out) if re.search(r'Scanner|BufferedReader|System\.in', code) else []
    name = os.path.basename(path)
    files = {}
    if name in OVERRIDES:
        scenarios = OVERRIDES[name].get('inputs', scenarios)
        files = OVERRIDES[name].get('files', {})
    elif not scenarios and re.search(r'\.next\w*\(|\.readLine\(', code):
        WARNINGS.append(name)
    return code.rstrip('\n'), scenarios, expected_out, files


# ---------------------------------------------------------------------------------------------
# Notes
# ---------------------------------------------------------------------------------------------
def convert_topic(folder):
    notes = open(os.path.join(folder, '00_Notes.md'), encoding='utf-8').read().replace('\r\n', '\n').split('\n')
    title = None
    items = []  # ('topic', n, text) | ('prose', [lines]) | ('example', filename)
    prose = []
    topic_no = 0
    headings = []

    def flush():
        nonlocal prose
        while prose and prose[-1] == '':
            prose.pop()
        while prose and prose[0] == '':
            prose.pop(0)
        if prose:
            items.append(('prose', prose))
        prose = []

    i = 0
    while i < len(notes):
        raw = notes[i]
        line = raw.strip()
        i += 1
        if not line:
            if prose and prose[-1] != '':
                prose.append('')
            continue
        h = re.match(r'^(#{1,6})\s+(.*)$', line)
        if h:
            level, text = len(h.group(1)), clean(h.group(2))
            if level == 1:
                title = title or text
            elif level == 2:
                flush()
                topic_no += 1
                headings.append(text)
                items.append(('topic', topic_no, text))
            else:
                if prose and prose[-1] != '':
                    prose.append('')
                prose.append(f'--- {text} -----')
                prose.append('')
            continue
        ex = re.match(r'^>\s*Example:\s*\[`([^`]+)`\]', line)
        if ex:
            flush()
            items.append(('example', ex.group(1)))
            continue
        if line.startswith('```'):
            code_lines = []
            while i < len(notes) and not notes[i].strip().startswith('```'):
                code_lines.append(notes[i].rstrip())
                i += 1
            i += 1
            if prose and prose[-1] != '':
                prose.append('')
            prose.extend('    ' + c if c.strip() else '    ' for c in code_lines)
            prose.append('')
            continue
        if line.startswith('|'):
            rows = [line]
            while i < len(notes) and notes[i].strip().startswith('|'):
                rows.append(notes[i].strip())
                i += 1
            if prose and prose[-1] != '':
                prose.append('')
            prose.extend(table_lines(rows))
            prose.append('')
            continue
        if line.startswith('>'):
            text = clean(line.lstrip('>').strip())
            if text:
                if prose and prose[-1] != '':
                    prose.append('')
                prose.extend(wrap('NOTE: ' + text))
                prose.append('')
            continue
        b = re.match(r'^[-*]\s+(.*)$', line)
        n = re.match(r'^(\d+)\.\s+(.*)$', line)
        if b or n:
            marker = '-' if b else n.group(1) + '.'
            body = clean(b.group(1) if b else n.group(2))
            # a list follows its lead-in line directly, with no blank line between
            while prose and prose[-1] == '' and len(prose) > 1 and not prose[-2].startswith('   '):
                prose.pop()
            prose.extend(wrap(f'{marker} {body}', '   ')[:1] + [l for l in wrap(f'{marker} {body}', '   ')[1:]])
            continue
        if re.fullmatch(r'-{3,}', line):
            continue
        text = clean(line)
        if text:
            if prose and prose[-1].startswith('   ') and prose[-1] != '':
                prose.append('')
            prose.extend(wrap(text))
    flush()
    return title or os.path.basename(folder), headings, items


# ---------------------------------------------------------------------------------------------
# Output
# ---------------------------------------------------------------------------------------------
def build():
    expected = {}
    written = 0
    topics = sorted(d for d in glob.glob(os.path.join(SRC, '[0-9][0-9]_*')) if os.path.isdir(d))
    for folder in topics:
        num = os.path.basename(folder)[:2]
        if num not in SECTION_OF:
            continue
        sec_no, sec_name = SECTION_OF[num]
        title, headings, items = convert_topic(folder)
        lines = []
        add = lines.append

        add('/*')
        add(f' * {sec_name}: {title}')
        add(' */')
        add('')
        add('/// ' + '=' * 76)
        add('/// WHAT YOU WILL LEARN')
        for h in headings[:6]:
            add(f'///   - {h}')
        add('/// ' + '=' * 76)
        add('')

        records = []
        for item in items:
            if item[0] == 'topic':
                add('')
                add(f'/// ### Topic {item[1]}: {item[2]} ###')
            elif item[0] == 'prose':
                for p in item[1]:
                    add('///' + (' ' + p if p else ''))
                add('')
            else:
                path = os.path.join(folder, item[1])
                if not os.path.exists(path):
                    add(f'/// NOTE: the example {item[1]} was not found.')
                    continue
                code, scenarios, out, files = load_example(path)
                start = len(lines) + 1
                for c in code.split('\n'):
                    add(c)
                for fname, content in files.items():
                    add(f'/// @file {fname}: ' + ' | '.join(content))
                for sc in scenarios:
                    add('/// @input ' + ' | '.join(sc))
                add('')
                records.append({'line': start, 'file': item[1], 'inputs': scenarios, 'output': out})

        while lines and lines[-1] == '':
            lines.pop()
        # Each section is a folder of its own at the top of the repo: it becomes one line of the journey map.
        sec_dir = os.path.join(ROOT, re.sub(r'[^A-Za-z0-9]+', '_', sec_name))
        os.makedirs(sec_dir, exist_ok=True)
        out_path = os.path.join(sec_dir, f'{num}_{slug(title)}.java')
        open(out_path, 'w', encoding='utf-8', newline='\n').write('\n'.join(lines) + '\n')
        expected[os.path.relpath(out_path, ROOT).replace(os.sep, '/')] = records
        written += 1
        print(f'{os.path.relpath(out_path, ROOT)}  ({len(records)} examples)')

    os.makedirs(os.path.join(ROOT, 'tools'), exist_ok=True)
    json.dump(expected, open(os.path.join(ROOT, 'tools', 'expected.json'), 'w', encoding='utf-8'), indent=1)
    print(f'\nWrote {written} lessons.')
    if WARNINGS:
        print('\nThese read input but no typed input could be worked out; add them to tools/input_overrides.json:')
        for w in WARNINGS:
            print('  ' + w)


if __name__ == '__main__':
    build()
