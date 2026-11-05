/*
 * Threads and GUI: Threads
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a thread is, and why programs use several at once
///   - Creating a thread two ways: extending Thread and implementing Runnable (with a lambda)
///   - The difference between start() and run()
///   - Pausing with sleep(), waiting with join(), and the thread life cycle
/// ============================================================================


/// ### Topic 1: What is a thread? ###
/// Every program you have written so far does one thing at a time: line 1, then line 2, then line 3. Real programs often
/// need to do several things AT ONCE. A music player plays a song while you scroll the playlist. A game moves the
/// enemies while you press keys. A browser downloads a file and keeps the page responsive.
///
/// A THREAD is a separate path of execution inside a program. A program with several threads can make progress on several
/// jobs at the same time. This is called MULTITHREADING, and doing several things in overlapping time is
/// CONCURRENCY.
///
/// ANALOGY: A kitchen. One cook doing everything in order (chop, boil, fry, plate) is slow. With three cooks working at the
/// same time (each is a thread) dinner is ready sooner, as long as they do not collide over the same pan. The kitchen is
/// the program, and the cooks are the threads.
///
/// Every Java program already has one thread, called `main`, which runs your `main` method. You start extra ones.
///
/// NOTE: A PROCESS is a whole running program with its own memory. THREADS live inside a process and share its memory, which
/// makes them light and quick to start, and also is the source of the dangers in the next lesson.

/// ### Topic 2: The main thread ###
/// You can ask which thread is running your code. `Thread.currentThread()` gives it, and `getName()` its name.

public class Main {
    public static void main(String[] args) {
        Thread t = Thread.currentThread();
        System.out.println("Running in the thread called: " + t.getName());
    }
}

/// ### Topic 3: Creating a thread, way 1: extend Thread ###
/// Write a class that `extends Thread` and override its `run()` method. `run()` holds what the thread does. To launch it,
/// call `start()`.

class Worker extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from " + getName());
    }
}

public class Main {
    public static void main(String[] args) {
        Worker w = new Worker();
        w.setName("worker-1");

        w.start();                     // starts a new thread, which calls run()
        System.out.println("main goes on without waiting");
    }
}

/// Be careful reading that output. After `start()`, the new thread and `main` run at the same time. The two lines can come
/// out in either order, and the order may change from one run to another. That unpredictability is normal for threads and
/// is the first thing to get used to.

/// ### Topic 4: Creating a thread, way 2: implement Runnable ###
/// Extending `Thread` uses up your one chance to extend a class (Java has single inheritance). The usual better way is to
/// put the work in a `Runnable`, an interface with a single method `run()`, and hand it to a Thread.

class Task implements Runnable {
    private String label;

    Task(String label) {
        this.label = label;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(label + " step " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new Task("A"));
        t1.start();
        t1.join();                       // wait for A to finish (join is explained below)

        Thread t2 = new Thread(new Task("B"));
        t2.start();
        t2.join();

        System.out.println("both finished");
    }
}

/// Because `Runnable` has just one method, you can also write the work as a lambda, with no class at all. This is the
/// most common style today.

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("running in " + Thread.currentThread().getName());
        }, "my-thread");

        t.start();
        t.join();
        System.out.println("back in " + Thread.currentThread().getName());
    }
}

/// ### Topic 5: start() versus run() ###
/// This is the most common beginner mistake. `run()` is just an ordinary method. Calling it directly does NOT create a new
/// thread; it runs on the current thread, like any other call. Only `start()` creates a new thread and then calls `run()` on
/// it.

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> System.out.println("job running in: " + Thread.currentThread().getName());

        Thread t = new Thread(job, "worker");

        t.run();          // a plain method call: runs in main
        t.start();        // a real new thread
        t.join();
    }
}

/// The first line says `main`, the second says `worker`. Also note that a thread can be started only once:

public class Main {
    public static void main(String[] args) {
        Thread t = new Thread(() -> System.out.println("hi"));
        t.start();
        t.start();
    }
}
/// @error

/// A second `start()` throws `IllegalThreadStateException`. Create a new Thread object instead.

/// ### Topic 6: sleep() and doing things at the same time ###
/// `Thread.sleep(milliseconds)` pauses the current thread. It is declared to throw `InterruptedException` (a checked
/// exception from the Exceptions lessons), so it needs a try and catch or a `throws`. While one thread sleeps, others keep
/// working. Here two threads take turns:

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread ping = new Thread(() -> {
            try {
                for (int i = 1; i <= 3; i++) {
                    System.out.println("ping " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("ping interrupted");
            }
        });

        Thread pong = new Thread(() -> {
            try {
                Thread.sleep(100);                  // start half a beat later
                for (int i = 1; i <= 3; i++) {
                    System.out.println("pong " + i);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                System.out.println("pong interrupted");
            }
        });

        ping.start();
        pong.start();
        ping.join();
        pong.join();
        System.out.println("game over");
    }
}

/// The two loops run at the same time, and the sleeps weave them together: ping, pong, ping, pong. With one thread this
/// would have taken twice as long, because each would wait for the other to finish.

/// ### Topic 7: join(): waiting for a thread ###
/// `t.join()` makes the CURRENT thread wait until `t` has finished. Without it, `main` may reach the end and print its final
/// message before the helpers are done. Compare:

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread slow = new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) { }
            System.out.println("slow job done");
        });

        slow.start();
        System.out.println("main: started the slow job");
        slow.join();                                      // comment this line out and see the order change
        System.out.println("main: the job has finished");
    }
}

/// ### Topic 8: The life of a thread ###
/// A thread moves through states:
///
///     NEW          created with new Thread(...), not started
///     RUNNABLE     started, running or ready to run
///     BLOCKED/WAITING/TIMED_WAITING   waiting for something (a lock, a join, a sleep)
///     TERMINATED   run() has finished
///
/// You can read it with `t.getState()`.

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) { }
        });

        System.out.println(t.getState());          // NEW
        t.start();
        Thread.sleep(50);
        System.out.println(t.getState());          // TIMED_WAITING: it is inside sleep
        t.join();
        System.out.println(t.getState());          // TERMINATED
    }
}

/// --- Useful extras -----
///    - `t.setDaemon(true)` makes a background helper thread that does not keep the program alive once all normal threads end.
///    - `t.isAlive()` tells you whether it is still running.
///    - `t.interrupt()` politely asks a sleeping or waiting thread to stop (it then gets an `InterruptedException`).
///
/// REMEMBER: Create threads with a Runnable (a lambda is fine), launch with `start()` (never call `run()` yourself), wait
/// with `join()`, and expect their output order to vary unless you control it. The next lesson is about what goes wrong when
/// threads share data.

/// ============================================================================
/// YOUR TURN
///   1. Write a Runnable that prints the numbers 1 to 5, and start two threads with it. Run it several times. Does the order
///      change?
///   2. Make a thread that counts down from 5 to 1, sleeping one second between numbers, and prints "Liftoff!" from `main`
///      after joining it.
///   3. Call `run()` instead of `start()` and use `Thread.currentThread().getName()` to prove no new thread was made.
///   4. Start three threads named "A", "B" and "C" that each print their name 3 times with a short sleep between prints.
///   5. Start the same thread twice and read the error.
///   6. In your own words: what is the difference between a process and a thread?
/// ============================================================================
