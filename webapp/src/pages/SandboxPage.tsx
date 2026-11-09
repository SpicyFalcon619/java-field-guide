import { lazy, Suspense, useEffect, useState } from 'react'
import { RotateCcw } from 'lucide-react'
import { useDocumentTitle } from '@/hooks/useDocumentTitle'
import { javaRunner } from '@/lib/javaRunner'
import { cn } from '@/lib/cn'

// The editor, the Java runner and CheerpJ load only when this page is opened.
const LiveJava = lazy(() => import('@/components/code/LiveJava'))

const STORAGE_KEY = 'jfg-sandbox-code'

const EXAMPLES: { id: string; label: string; code: string }[] = [
  {
    id: 'hello',
    label: 'Hello',
    code: `// Change anything, then press Run (or Ctrl+Enter).
public class Main {
    public static void main(String[] args) {
        String name = "world";
        System.out.println("Hello, " + name + "!");

        for (int i = 1; i <= 5; i++) {
            System.out.println(i + " squared is " + (i * i));
        }
    }
}
`,
  },
  {
    id: 'input',
    label: 'Asking for input',
    code: `import java.util.Scanner;

// When the program asks, a box appears under the output. Type your answer and press Enter.
public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("What is your name? ");
        String name = scan.nextLine();

        System.out.print("How old are you? ");
        int age = scan.nextInt();

        System.out.println(name + " will be " + (age + 10) + " in ten years.");
        scan.close();
    }
}
`,
  },
  {
    id: 'objects',
    label: 'Classes and objects',
    code: `class Student {
    private String name;
    private int marks;

    public Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public String grade() {
        if (marks >= 80) return "A";
        if (marks >= 60) return "B";
        return "C";
    }

    @Override
    public String toString() {
        return name + " scored " + marks + " (" + grade() + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Student[] students = { new Student("Rafi", 91), new Student("Mim", 67), new Student("Anik", 45) };
        for (Student s : students) {
            System.out.println(s);
        }
    }
}
`,
  },
  {
    id: 'poly',
    label: 'Polymorphism',
    code: `class Animal {
    void sound() { System.out.println("Some sound"); }
}

class Dog extends Animal {
    @Override
    void sound() { System.out.println("Woof"); }
}

class Cat extends Animal {
    @Override
    void sound() { System.out.println("Meow"); }
}

public class Main {
    public static void main(String[] args) {
        Animal[] farm = { new Dog(), new Cat(), new Animal() };
        for (Animal a : farm) {
            a.sound();       // the real object decides which version runs
        }
    }
}
`,
  },
  {
    id: 'exceptions',
    label: 'Exceptions',
    code: `public class Main {
    public static void main(String[] args) {
        int[] numbers = {10, 20, 30};

        try {
            System.out.println(numbers[5]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("That position does not exist.");
        } finally {
            System.out.println("This always runs.");
        }
    }
}
`,
  },
]

function stored(): string | null {
  try {
    return window.localStorage.getItem(STORAGE_KEY)
  } catch {
    return null
  }
}

export function SandboxPage() {
  useDocumentTitle('Sandbox')
  const [code, setCode] = useState(() => stored() ?? EXAMPLES[0].code)
  // Picking an example replaces the editor, so it is given a new identity to start fresh.
  const [version, setVersion] = useState(0)
  // The example the editor started from, so Reset can bring it back.
  const [current, setCurrent] = useState(EXAMPLES[0])

  // Someone on this page is here to run code, so start Java and warm the compiler now, while they read and type.
  useEffect(() => {
    javaRunner.prepare()
    javaRunner.warmUp()
  }, [])

  const remember = (next: string) => {
    setCode(next)
    try {
      window.localStorage.setItem(STORAGE_KEY, next)
    } catch {
      // Not remembering the text is fine.
    }
  }

  const pick = (example: (typeof EXAMPLES)[number]) => {
    setCurrent(example)
    remember(example.code)
    setVersion((v) => v + 1)
  }

  return (
    <div className="mx-auto max-w-4xl animate-fade-up pb-16">
      <header className="mb-6">
        <h1 className="text-5xl leading-none font-bold text-[var(--color-ink)] sm:text-6xl">Sandbox</h1>
        <p className="mt-2 max-w-2xl text-[var(--color-ink-dim)]">
          Write your own Java and run it here. It is a real Java compiler running inside your browser, so nothing you type is sent
          anywhere. The first run downloads Java (a few tens of megabytes, once) and can take a minute; after that it is quick.
        </p>
      </header>

      <div className="mb-3 flex flex-wrap items-center gap-2" role="group" aria-label="Start from an example">
        <span className="text-sm text-[var(--color-ink-faint)]">Start from</span>
        {EXAMPLES.map((example) => (
          <button
            key={example.id}
            type="button"
            onClick={() => pick(example)}
            className={cn(
              'min-h-6 cursor-pointer rounded-md border border-[var(--color-border)] px-2.5 py-0.5 text-sm text-[var(--color-ink-dim)] transition-colors',
              'hover:border-[var(--color-border-strong)] hover:text-[var(--color-ink)]',
            )}
          >
            {example.label}
          </button>
        ))}
        <button
          type="button"
          onClick={() => pick(current)}
          title={`Bring back the "${current.label}" example and clear the output`}
          className={cn(
            'ml-auto flex min-h-6 cursor-pointer items-center gap-1.5 rounded-md border border-[var(--color-border)] px-2.5 py-0.5 text-sm text-[var(--color-ink-dim)] transition-colors',
            'hover:border-[var(--color-border-strong)] hover:text-[var(--color-ink)]',
          )}
        >
          <RotateCcw className="h-3.5 w-3.5" aria-hidden />
          Reset
        </button>
      </div>

      <div className="guide-code code-font">
        <Suspense fallback={<p className="guide-part-none">Opening the editor...</p>}>
          <LiveJava key={version} initialCode={code} onCodeChange={remember} variant="page" label="Java sandbox editor" />
        </Suspense>
      </div>

      <ul className="mt-4 max-w-2xl list-disc space-y-1.5 pl-5 text-sm leading-relaxed text-[var(--color-ink-faint)]">
        <li>
          Programs that ask for typing, with <code>Scanner</code>, show an input box under the output when they reach that line.
        </li>
        <li>The file can hold several classes. The one with <code>main</code> runs, and if you name a public class, it is the one that is used.</li>
        <li>Press Stop to end a program that never finishes. Java then restarts, which makes the next run slower.</li>
        <li>Windows (Swing and other graphics) are not available here. Run those on your own computer.</li>
        <li>
          Java runs in your browser through{' '}
          <a href="https://cheerpj.com" target="_blank" rel="noreferrer" className="underline decoration-[var(--color-border-strong)] underline-offset-4 hover:text-[var(--color-ink)]">
            CheerpJ
          </a>
          .
        </li>
      </ul>
    </div>
  )
}
