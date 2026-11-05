// The page's handle on the live Java runner. Java runs in the browser through CheerpJ (https://cheerpj.com), inside
// a hidden frame that shows public/runner.html. The frame starts the first time someone presses Run (never for
// people who are only reading), and runs one program at a time.
//
// Output comes back as it is printed. When the program reads the keyboard (Scanner, System.in) the runner reports
// that it is waiting, the page shows an input line, and sendLine() hands the typed text to the program.
import { useSyncExternalStore } from 'react'

export type JavaPhase = 'idle' | 'loading' | 'warming' | 'ready' | 'compiling' | 'running' | 'waiting'
export interface JavaStatus {
  phase: JavaPhase
  message: string
  /** Which editor owns the run in progress, so only that editor shows the input line. */
  owner: string | null
}

/** 'out' is what the program printed, 'err' its error stream, 'build' the compiler's messages. */
export type JavaStream = 'out' | 'err' | 'build'

export interface JavaRunOptions {
  code: string
  owner: string
  onOutput: (stream: JavaStream, text: string) => void
  /**
   * The same program, already compiled when the site was built. It is used instead of `code`, which skips the
   * compiler, the slowest part of a first run. Leave it out once the reader has edited the code.
   */
  prebuilt?: { jar: string; mainClass: string }
  /** A silent run that only gets the compiler ready for the reader's first real one. */
  warm?: boolean
}

export interface JavaRunResult {
  ok: boolean
  ms: number
}

/** Thrown to a waiting run when the reader presses Stop. */
export class JavaRunStopped extends Error {
  constructor() {
    super('Stopped')
    this.name = 'JavaRunStopped'
  }
}

const STREAMS: JavaStream[] = ['out', 'err', 'build']
// The first compile in a visit is slow (about 40 seconds) because the compiler's own code is being prepared as it is
// used, and a later compile is only quick for the parts already used. So the warm-up is a small program that touches
// what lessons use: classes, an interface, loops, text joining, generics, printf, switch and an exception.
// Scanner is compiled but never reached, so the warm-up never waits for typing.
const WARM_UP_CODE = `import java.util.*;
public class Warm {
    interface Shape { double area(); }
    static class Square implements Shape {
        private final double side;
        Square(double side) { this.side = side; }
        public double area() { return side * side; }
        @Override public String toString() { return "Square " + side; }
    }
    static int twice(int n) { return n * 2; }
    public static void main(String[] args) {
        int total = 0;
        for (int i = 0; i < 3; i++) { total += twice(i); }
        String text = "total = " + total + ", " + 1.5 + ", " + true;
        List<String> list = new ArrayList<>();
        list.add(text);
        for (String s : list) { System.out.println(s); }
        Shape shape = new Square(2);
        System.out.printf("%.1f %s%n", shape.area(), shape);
        switch (total) { case 6: System.out.println("six"); break; default: System.out.println("other"); }
        if (args.length > 5) { Scanner scan = new Scanner(System.in); System.out.println(scan.nextLine()); }
        try { int[] a = new int[1]; a[2] = 1; } catch (ArrayIndexOutOfBoundsException e) { System.out.println("caught"); }
    }
}`
const START_FAILED = 'Java could not start. Check your internet connection and try again.'

interface Active {
  options: JavaRunOptions
  id: number
  startedAt: number
  resolve(result: JavaRunResult): void
  reject(error: Error): void
}

class JavaRunner {
  private frame: HTMLIFrameElement | null = null
  private ready: Promise<void> | null = null
  private active: Active | null = null
  private warmed = false
  private nextId = 1
  private queue: Promise<unknown> = Promise.resolve()
  private status: JavaStatus = { phase: 'idle', message: '', owner: null }
  private listeners = new Set<() => void>()

  subscribe = (listener: () => void) => {
    this.listeners.add(listener)
    return () => void this.listeners.delete(listener)
  }
  getStatus = () => this.status

  private setStatus(phase: JavaPhase, message = '', owner: string | null = this.status.owner) {
    this.status = { phase, message, owner }
    this.listeners.forEach((listener) => listener())
  }

  private handle = (event: MessageEvent) => {
    if (!this.frame || event.source !== this.frame.contentWindow || event.origin !== window.location.origin) return
    const message = event.data as { type?: string; stream?: number; text?: string; phase?: string; detail?: string; id?: number; exit?: number; message?: string }
    switch (message.type) {
      case 'status':
        if (message.phase === 'loading') this.setStatus('loading', message.detail ?? 'Starting Java...')
        else if (message.phase === 'running' && this.active && !this.active.options.warm) this.setStatus('running', '')
        break
      case 'out':
        this.active?.options.onOutput(STREAMS[message.stream ?? 0] ?? 'out', message.text ?? '')
        break
      case 'input-wait':
        if (this.active) this.setStatus('waiting', 'The program is waiting for you to type something.')
        break
      case 'done': {
        const active = this.active
        if (!active || active.id !== message.id) break
        this.active = null
        this.setStatus('ready', '', null)
        active.resolve({ ok: message.exit === 0, ms: performance.now() - active.startedAt })
        break
      }
      case 'fatal':
        this.fail(START_FAILED)
        break
    }
  }

  /** Something went wrong with the runner itself: tell whoever is waiting, and start from scratch next time. */
  private fail(text: string) {
    const active = this.active
    this.active = null
    this.teardown()
    active?.reject(new Error(text))
  }

  private teardown() {
    window.removeEventListener('message', this.handle)
    this.frame?.remove()
    this.frame = null
    this.ready = null
    this.warmed = false
    this.setStatus('idle', '', null)
  }

  private start(): Promise<void> {
    if (this.ready) return this.ready
    this.setStatus('loading', 'Starting Java in your browser. The first run takes about half a minute; after that it is quick.')
    const frame = document.createElement('iframe')
    frame.src = `${import.meta.env.BASE_URL}runner.html`
    frame.title = 'Java runner (hidden)'
    frame.setAttribute('aria-hidden', 'true')
    frame.tabIndex = -1
    frame.style.cssText = 'position:fixed;left:-9999px;top:0;width:1px;height:1px;border:0;visibility:hidden'
    this.frame = frame
    window.addEventListener('message', this.handle)
    this.ready = new Promise<void>((resolve, reject) => {
      const timer = window.setTimeout(() => {
        reject(new Error(START_FAILED))
        this.teardown()
      }, 30000)
      const onReady = (event: MessageEvent) => {
        if (event.source !== frame.contentWindow || (event.data as { type?: string })?.type !== 'ready') return
        window.clearTimeout(timer)
        window.removeEventListener('message', onReady)
        resolve()
      }
      window.addEventListener('message', onReady)
      frame.addEventListener('error', () => {
        window.clearTimeout(timer)
        reject(new Error(START_FAILED))
        this.teardown()
      })
    })
    this.ready.catch(() => undefined)
    document.body.appendChild(frame)
    return this.ready
  }

  run(options: JavaRunOptions): Promise<JavaRunResult> {
    // A program still waiting for typed text cannot make progress for someone else: end its input so it finishes.
    if (this.status.phase === 'waiting') this.endInput()
    const task = this.queue.then(async () => {
      await this.start()
      const frame = this.frame
      if (!frame?.contentWindow) throw new Error(START_FAILED)
      const id = this.nextId++
      if (options.warm) this.setStatus('warming', 'Getting the Java compiler ready. This happens once per visit, and the first run is slow because of it.', null)
      else this.setStatus(options.prebuilt ? 'running' : 'compiling', options.prebuilt ? 'Starting...' : 'Compiling...', options.owner)
      return new Promise<JavaRunResult>((resolve, reject) => {
        this.active = { options, id, startedAt: performance.now(), resolve, reject }
        const program = options.prebuilt ? { jar: options.prebuilt.jar, mainClass: options.prebuilt.mainClass } : { code: options.code }
        frame.contentWindow!.postMessage({ type: 'run', id, ...program }, window.location.origin)
      })
    })
    this.queue = task.catch(() => undefined)
    return task
  }

  /**
   * Starts Java ahead of time, for when a reader is about to press Run (the pointer is over the button). It
   * downloads and starts the Java machine, which takes a few seconds, but compiles nothing.
   */
  prepare() {
    void this.start()
      .then(() => this.frame?.contentWindow?.postMessage({ type: 'prepare' }, window.location.origin))
      .catch(() => undefined)
  }

  /**
   * Gets the compiler ready for a reader who is about to edit code. Runs in the background; a real run that
   * arrives meanwhile waits behind it, which is still quicker than starting the compiler cold.
   */
  warmUp() {
    // Not while a program is running or waiting for typing: the warm-up would have to wait behind it, or cut it short.
    if (this.warmed || ['compiling', 'running', 'waiting'].includes(this.status.phase)) return
    this.warmed = true
    void this.run({ code: WARM_UP_CODE, owner: 'warm-up', onOutput: () => undefined, warm: true }).catch(() => {
      this.warmed = false
    })
  }

  /** Gives the program one line of typed input. */
  sendLine(line: string) {
    if (!this.active || !this.frame?.contentWindow) return
    this.setStatus('running', '')
    this.frame.contentWindow.postMessage({ type: 'stdin', line }, window.location.origin)
  }

  /** Ends the input, like Ctrl+D in a terminal: the program's next read finds nothing more. */
  endInput() {
    if (!this.active || !this.frame?.contentWindow) return
    this.setStatus('running', '')
    this.frame.contentWindow.postMessage({ type: 'eof' }, window.location.origin)
  }

  /** Ends whatever is running, even a program stuck in a loop. Java starts again on the next Run. */
  stop() {
    const active = this.active
    this.active = null
    this.teardown()
    active?.reject(new JavaRunStopped())
  }
}

export const javaRunner = new JavaRunner()

export function useJavaStatus(): JavaStatus {
  return useSyncExternalStore(javaRunner.subscribe, javaRunner.getStatus)
}
