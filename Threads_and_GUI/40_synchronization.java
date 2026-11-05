/*
 * Threads and GUI: Synchronization and thread safety
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Why threads that share data can corrupt it: the race condition
///   - Fixing it with `synchronized` methods and blocks
///   - Atomic classes, and a first look at the ExecutorService
///   - Deadlock and how to avoid it
/// ============================================================================


/// ### Topic 1: The danger of sharing ###
/// Threads share the memory of their program. That is useful (they can work on the same data) and dangerous (they can
/// trample on each other). The classic problem is the RACE CONDITION: the result depends on which thread happens to get
/// there first.
///
/// ANALOGY: Two people update the same bank balance at the same moment. Both read 100. One adds 50 and writes 150. The
/// other, who read the old 100, adds 20 and writes 120. The 50 has vanished. Each did their job correctly; the DAMAGE came
/// from the overlap.
///
/// This is exactly what happens with `count++`. It looks like one step, but it is really three: read the number, add one,
/// write it back. Two threads can read the same old value.

/// ### Topic 2: A race condition you can see ###
/// Two threads each add 1 to a shared counter a million times. The right answer is 2,000,000.

public class Main {
    static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                count++;                   // three steps hiding in one line
            }
        };

        Thread a = new Thread(job);
        Thread b = new Thread(job);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println("Expected 2000000, got " + count);
    }
}

/// On most computers this prints a number smaller than 2,000,000, and a different one each run. Some increments were lost
/// in the overlaps. (On a machine that happens to run the two threads one after the other, you may get the right answer. A
/// program that is only sometimes right is the worst kind of bug, because testing may never catch it.)

/// ### Topic 3: The fix: synchronized ###
/// The keyword `synchronized` lets only ONE thread at a time run a piece of code. A thread must hold the object's LOCK
/// (every object has one) to enter. Others wait outside until it is released. Putting `synchronized` on a method locks the
/// whole method.

public class Main {
    static int count = 0;

    static synchronized void increment() {
        count++;
    }

    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                increment();
            }
        };

        Thread a = new Thread(job);
        Thread b = new Thread(job);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println("Expected 2000000, got " + count);
    }
}

/// Now the answer is always correct. The price is speed: threads queue up at the lock, so keep synchronized sections
/// short.
///
/// ANALOGY: A single-stall toilet with a lock on the door. Only one person can be inside, the rest wait. It is safe, and
/// slower than having no queue.

/// ### Topic 4: A synchronized block ###
/// Instead of the whole method, you can lock just the part that needs it, on a lock object of your choice. This is finer and
/// usually better.

public class Main {
    static int count = 0;
    static final Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable job = () -> {
            for (int i = 0; i < 500_000; i++) {
                synchronized (lock) {
                    count++;
                }
            }
        };

        Thread a = new Thread(job);
        Thread b = new Thread(job);
        Thread c = new Thread(job);
        a.start(); b.start(); c.start();
        a.join(); b.join(); c.join();

        System.out.println(count);
    }
}

/// For instance methods, `synchronized` locks `this` (the object the method is called on). Two threads using two DIFFERENT
/// objects do not block each other, as they hold different locks.

/// ### Topic 5: Atomic classes ###
/// For simple counters there is an easier tool: `AtomicInteger`. Its operations are ATOMIC, meaning indivisible. They
/// cannot be interrupted halfway, so no lock is needed.

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger count = new AtomicInteger(0);

        Runnable job = () -> {
            for (int i = 0; i < 1_000_000; i++) {
                count.incrementAndGet();
            }
        };

        Thread a = new Thread(job);
        Thread b = new Thread(job);
        a.start();
        b.start();
        a.join();
        b.join();

        System.out.println(count.get());
    }
}

/// ### Topic 6: A realistic example: a shared bank account ###
/// Here two threads deposit into the same account. The `synchronized` method keeps the balance consistent.

class Account {
    private int balance = 0;

    synchronized void deposit(int amount) {
        balance += amount;
    }

    synchronized int getBalance() {
        return balance;
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();

        Thread husband = new Thread(() -> {
            for (int i = 0; i < 1000; i++) account.deposit(10);
        });
        Thread wife = new Thread(() -> {
            for (int i = 0; i < 1000; i++) account.deposit(20);
        });

        husband.start();
        wife.start();
        husband.join();
        wife.join();

        System.out.println("Final balance: " + account.getBalance());
    }
}

/// ### Topic 7: Thread pools with ExecutorService ###
/// Creating a thread for every little job is wasteful. A THREAD POOL keeps a fixed number of worker threads and gives them
/// jobs from a queue. `ExecutorService` is the standard tool. You submit tasks, and shut the pool down when you are done.

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 5; i++) {
            int jobNumber = i;
            pool.submit(() -> {
                int square = jobNumber * jobNumber;
                // (the order of lines is not guaranteed, so only the total is printed below)
            });
        }

        pool.shutdown();                                   // no new jobs; finish the ones queued
        pool.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("All five jobs finished");
    }
}

/// Five jobs shared among three workers. `shutdown()` is essential: without it the pool's threads keep the program alive
/// forever. (A pool that RETURNS a value from a task uses `Callable` and `Future`, which belong to a later course.)

/// ### Topic 8: Deadlock ###
/// If two threads each hold one lock and wait for the other's, neither can ever continue. This is a DEADLOCK.
///
/// ANALOGY: Two cars meet head-on on a narrow bridge, each waiting for the other to reverse. Neither does, and both are
/// stuck for ever.
///
///     Thread 1:  lock A, then wait for B
///     Thread 2:  lock B, then wait for A      -> both wait for ever
///
/// The code is not shown running here, because it would never finish. The remedies are simple rules:
///    - Always take locks in the SAME ORDER in every thread.
///    - Hold locks for as short a time as you can.
///    - Prefer higher-level tools (atomic classes, thread pools, concurrent collections) over hand-written locks.
///
/// --- Concurrent collections -----
/// `ArrayList` and `HashMap` are NOT safe to modify from several threads at once. For shared use, Java offers
/// `ConcurrentHashMap`, `CopyOnWriteArrayList` and `Collections.synchronizedList(...)`.
///
/// REMEMBER: Shared data plus several threads can give wrong answers (a race condition). `synchronized` lets one thread at a
/// time through. Keep locked sections short, take locks in one order to avoid deadlock, and use atomics and pools when you
/// can.

/// ============================================================================
/// YOUR TURN
///   1. Run the unsynchronized counter several times. Write down the numbers you get, and explain why they differ.
///   2. Fix a shared `int total` using a synchronized method, then again using `AtomicInteger`.
///   3. Make three threads each add 100 items to the same list. Why can an `ArrayList` give an error or lose items, and what
///      would you use instead?
///   4. Create a pool of 2 threads and submit 4 tasks that each print the name of the thread running them.
///   5. Describe a deadlock using an everyday story of your own.
///   6. Why is it best to keep a synchronized section as small as possible?
/// ============================================================================
