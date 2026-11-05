import type { Snippet } from '@/types'
import { getOutput } from '@/lib/outputs'
import { siteMap } from '@/lib/siteIndex'
import { getAdjacent } from '@/lib/roadmap'
import { nextUpLesson, overallProgress, trackProgress } from '@/lib/progress'
import type { ChatMessage } from '@/lib/llm'

// The free plan allows roughly 8K tokens a minute, so what is sent is capped: about 1.5K
// tokens of code, a little output, and only the most recent turns of the conversation.
const MAX_CODE_CHARS = 4500
const MAX_OUTPUT_CHARS = 1500
const MAX_HISTORY_MESSAGES = 8
const MAX_HISTORY_CHARS = 9000

const SYSTEM = `You are the study assistant inside "Java Field Guide", a site for learning Java and object oriented programming. It follows a university theory course (CSE 1115) and its lab (CSE 1116). Each lesson is explanations beside complete, runnable Java programs.
Answer accurately and in plain language, the way a patient tutor would. If you are not sure, say so instead of guessing. Only refer to the lesson below when the question is about it. Do not make up facts about the learner.

How to answer: lead with the direct answer in a sentence or two, then explain why it works, then show a small complete example (a class with a main method) when code helps, and finish with one practical tip or a common pitfall when there is a useful one. Be thorough where the question needs it and brief where it doesn't; use a short heading only for long answers. When someone asks what to do or learn next, start from their real progress below: say briefly what they have already finished, then recommend the next step, preferring to carry on from the lesson they opened last. When you point someone to a lesson in this guide, write its exact title from the list below in **bold** (it becomes a link), and only name lessons that are in that list.

About the site, which is everything you know about it:
- It is a personal learning project built around two courses at a university: Object Oriented Programming (theory, CSE 1115) and its laboratory (CSE 1116). It follows the courses' outlines, and the theory lessons follow the order the author learned things in. It is not an official university resource, a book or a company product, and it has no instructor or certificates.
- Every lesson shows complete Java programs beside the real output they printed when they were compiled and run. Lessons can be searched (Ctrl+K), browsed on the Explore page, marked done or saved, and shown as an image with "Capture" (the image can include just the output of the blocks of the lines picked). "Your progress" shows progress, which is kept in the visitor's own browser.
- A lesson page has three views, switched at the top of the lesson: Guided (the explanations as readable text, with the code in blocks under numbered topics), Full code (the whole file) and Bare code (only the code, with the comments removed). Every code block is a complete program with its own main method, so it can be copied and run as it is. A block that asks for input has a "Typed input" picker that shows the output for each example input.
- Progress, saved lessons and settings live only in the visitor's own browser, not on a server or in the repository. "Your progress" can export them to a file and import that file in another browser or on another device.
- The code is public at the GitHub repository SpicyFalcon619/java-field-guide. That repository has no license file, so do not call it open source or say it can be reused without restrictions; say it is public and that reuse is a question for its owner.
- If someone asks whether they should use the site, be honest and balanced rather than promotional: it suits people who learn from complete working programs with explanations beside them, and it is not a full course with an instructor, graded exercises or certificates. Say what it does not have, and ask what they want to achieve if that changes the answer.
- You do not know the owner's name or anything else about them. If asked who made the site or who it is for, say it is a personal learning project by its owner (see the repository above), useful to anyone studying Java and object oriented programming, and that you can't say more. Never invent names, credentials or facts about the site or its author.`

function clip(text: string, max: number): string {
  return text.length > max ? `${text.slice(0, max)}\n... (cut off to keep this short)` : text
}

function numbered(code: string): string {
  return code
    .split('\n')
    .map((line, index) => `${index + 1} ${line}`)
    .join('\n')
}

function lessonContext(snippet: Snippet): string {
  const output = getOutput(snippet.path)
  const printed = output && output.status !== 'empty' ? output.stdout : ''
  return [
    `The learner is looking at this lesson: "${snippet.title}" (${snippet.filename}) in the track "${snippet.categoryLabel}".`,
    // Numbered, so the model can say "line 12" and the learner can find it in the lesson.
    `Code (each line starts with its line number; refer to lines as "line N"):\n\`\`\`\n${clip(numbered(snippet.code), MAX_CODE_CHARS)}\n\`\`\``,
    printed ? `Output it produced:\n\`\`\`\n${clip(printed, MAX_OUTPUT_CHARS)}\n\`\`\`` : '',
  ]
    .filter(Boolean)
    .join('\n')
}

function progressContext(done: string[], recent: Snippet[]): string {
  const overall = overallProgress(done)
  const next = nextUpLesson(done)
  const progress = trackProgress(done)
  const tracks = progress.map((t) => `${t.label} ${t.done}/${t.total}`).join(', ')
  const finished = progress.filter((t) => t.total > 0 && t.done === t.total).map((t) => t.label)
  const underway = progress.filter((t) => t.done > 0 && t.done < t.total).map((t) => t.label)
  const untouched = progress.filter((t) => t.done === 0).map((t) => t.label)
  // The lesson after the one they opened last, if they haven't done it: "carry on from where you were".
  const latest = recent[0]
  const carryOn = latest ? getAdjacent(latest.id).next : undefined
  return [
    `The learner's real progress, which you must use exactly as given: ${overall.done} of ${overall.total} lessons finished (${tracks}).`,
    overall.done > 0
      ? `Finished tracks: ${finished.join(', ') || 'none'}. Tracks in progress: ${underway.join(', ') || 'none'}. Tracks not started: ${untouched.join(', ') || 'none'}.`
      : 'They have not finished any lesson yet.',
    next ? `The first unfinished lesson on the trail is "${next.title}" (${next.categoryLabel}).` : 'They have finished every lesson.',
    recent.length ? `Lessons they opened most recently, newest first: ${recent.map((s) => `"${s.title}"`).join(', ')}.` : '',
    carryOn && !done.includes(carryOn.id) ? `The lesson that follows their latest one is "${carryOn.title}".` : '',
    'Never say they have not started or finished lessons when the numbers above say otherwise.',
  ]
    .filter(Boolean)
    .join(' ')
}

export function buildMessages(history: ChatMessage[], lesson: Snippet | undefined, done: string[], recent: Snippet[] = []): ChatMessage[] {
  const system = [SYSTEM, siteMap(), progressContext(done, recent), lesson ? lessonContext(lesson) : ''].filter(Boolean).join('\n\n')
  // Newest messages first until the budget is used, then restore the order.
  const kept: ChatMessage[] = []
  let used = 0
  for (const message of [...history].reverse().slice(0, MAX_HISTORY_MESSAGES)) {
    used += message.content.length
    if (used > MAX_HISTORY_CHARS && kept.length > 0) break
    kept.unshift(message)
  }
  return [{ role: 'system', content: system }, ...kept]
}

export interface QuickAction {
  id: string
  label: string
  prompt: string
}

export const LESSON_ACTIONS: QuickAction[] = [
  { id: 'explain', label: 'Explain this lesson', prompt: 'Explain this lesson step by step: what each part of the code does and why it is written that way.' },
  { id: 'output', label: 'Explain the output', prompt: 'Explain the output this code produced and how each part of it comes from the code.' },
  { id: 'quiz', label: 'Quiz me', prompt: 'Ask me 3 short questions about this lesson, numbered, without the answers. I will reply and you can check them.' },
  { id: 'simple', label: 'Make it simpler', prompt: 'Explain the main idea of this lesson as if I am new to programming, with one everyday analogy.' },
  { id: 'mistakes', label: 'Common mistakes', prompt: 'What are the common mistakes and gotchas with the ideas and code in this lesson?' },
  { id: 'related', label: 'Related lessons', prompt: 'Which other lessons in this guide relate to this one, and how? Name two or three, and say what I would gain from each.' },
  { id: 'next', label: 'What next?', prompt: 'Based on this lesson, what should I practise or learn next? Give two or three concrete suggestions.' },
]

export const GENERAL_ACTIONS: QuickAction[] = [
  { id: 'next', label: 'What should I learn next?', prompt: 'Given my progress, what should I learn next, and why? Keep it short.' },
  { id: 'plan', label: 'A one-week study plan', prompt: 'Make me a realistic one-week study plan for the rest of this guide, day by day, based on my progress.' },
  { id: 'find', label: 'Find a lesson', prompt: 'Help me find the right lesson in this guide. Ask what I want to learn, then point me to the best lessons for it, in the order to read them.' },
  { id: 'concept', label: 'Explain a concept', prompt: 'I want to understand a concept. Ask me which one, and then explain it simply with a small example.' },
  { id: 'debug', label: 'Help with my code', prompt: 'I will paste some code and an error. Ask me for them, then help me find the problem.' },
]

/** Short buttons shown under the latest answer. */
export const FOLLOW_UPS: QuickAction[] = [
  { id: 'example', label: 'Give an example', prompt: 'Give a short, concrete example of that I can run.' },
  { id: 'simpler', label: 'Explain more simply', prompt: 'Explain that again more simply, as if I am new to it.' },
  { id: 'deeper', label: 'Go deeper', prompt: 'Go deeper: explain the details and the edge cases.' },
  { id: 'quiz', label: 'Quiz me on this', prompt: 'Quiz me on that with 2 short questions, without the answers.' },
]

export const CONTINUE_PROMPT = 'Your last answer was cut off. Continue from exactly where it stopped, without repeating what you already wrote.'

/** A question about a piece of code the visitor highlighted on the page. */
export const askAboutSelection = (text: string): string =>
  `Explain this part of the code, and why it is written this way:\n\`\`\`\n${text.slice(0, 2500)}\n\`\`\``

// Longest question accepted, so one huge paste can't use up the free plan's token allowance.
export const MAX_QUESTION_CHARS = 6000
