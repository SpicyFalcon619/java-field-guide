/*
 * Practice: Threads problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Creating a thread by extending `Thread` and by implementing `Runnable`
///   - Starting threads with `start()` and waiting for them with `join()`
///   - Splitting one job between two threads and combining the partial answers
///   - Why two threads that change the same variable need `synchronized`
/// ============================================================================

/// Four exercises, each a little harder than the last. They all share one pattern: make the threads, `start()` them, `join()`
/// them, and only then use their results.
///
/// NOTE: When several threads print at the same time, the order of their lines is up to the operating system and can change
/// from one run to the next. Where that matters below, the text says so.

/// ### Exercise 1: Hello from two threads ###
/// Create a thread class that receives the thread's name in its constructor. The thread prints "Hello from" followed by its
/// name. In `main`, create two objects of the class and, after both threads have finished, print "main is terminating".

class MyThread extends Thread {
    MyThread(String name) {
        super(name);                              // Thread already stores a name, so pass it up
    }

    @Override
    public void run() {
        System.out.println("Hello from " + getName());
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread1 = new MyThread("thread 1");
        MyThread thread2 = new MyThread("thread 2");

        thread1.start();                          // start() creates the new thread, which then calls run()
        thread2.start();

        thread1.join();                           // main waits here until thread1 has finished
        thread2.join();

        System.out.println("main is terminating");
    }
}

/// The two "Hello" lines can appear in either order, but "main is terminating" is always last, because of the two `join()`
/// calls.
///
/// `join()` is declared to throw `InterruptedException`, since the thread that waits (here `main`) could itself be interrupted
/// while it waits. It is a checked exception, so `main` either catches it or, as here, declares `throws InterruptedException`.
///
/// MISTAKE: Calling `thread1.run()` instead of `thread1.start()` runs the method in the CURRENT thread, like any ordinary method
/// call. No new thread is created. Always use `start()`.

/// ### Exercise 2: Sum an array in a thread ###
/// Create a thread class `SumThread` that implements `Runnable`. It receives an array in its constructor, and its `run` method
/// computes the sum of the array. In `main`, create an object, start a thread with it and finally print the sum.

class SumThread implements Runnable {
    int[] arr;
    int sum = 0;

    SumThread(int[] arr) {
        this.arr = arr;
    }

    @Override
    public void run() {
        for (int x : arr) {
            sum += x;
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] a = {1, 3, 4, 5, 7};

        SumThread job = new SumThread(a);          // the job to do
        Thread thread = new Thread(job);           // a worker to do it
        thread.start();
        thread.join();                             // wait, or the sum may not be ready yet

        System.out.println("sum: " + job.sum);
    }
}

/// With `Runnable`, the class describes the JOB, and a separate `Thread` object does the work. Without `join()`, `main` could
/// print the sum before the thread finished adding, and show 0.

/// ### Exercise 3: Two threads, half an array each ###
/// Create a thread class `ComputeArraySum` that extends `Thread`. In `main`, create two objects, split the array into two
/// equal halves so that each thread sums one half, and print the total in `main`.
///
/// WARNING: A common first solution keeps the answer in one `static int sum` that BOTH threads add to. That is a data race.
/// `sum += x` is really three steps (read, add, write back), and if the two threads interleave those steps, one addition can
/// be lost and the total comes out wrong, only sometimes. The safe design below gives each thread its own `partial` result and
/// lets `main` add them up after `join()`.

class ComputeArraySum extends Thread {
    int[] arr;
    int start;
    int end;
    int partial = 0;                           // this thread's own answer

    ComputeArraySum(int[] arr, int start, int end) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i <= end; i++) {
            partial += arr[i];
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        int[] a = {1, 3, 4, 4, 2, 5, 7};
        int middle = a.length / 2;

        ComputeArraySum first = new ComputeArraySum(a, 0, middle);                    // indexes 0 to 3
        ComputeArraySum second = new ComputeArraySum(a, middle + 1, a.length - 1);    // indexes 4 to 6

        first.start();
        second.start();

        first.join();
        second.join();

        System.out.println("first half:  " + first.partial);
        System.out.println("second half: " + second.partial);
        System.out.println("sum: " + (first.partial + second.partial));
    }
}

/// The two threads never touch the same variable, so there is nothing to protect. Two threads genuinely working in parallel
/// is where multithreading pays off, and the join-then-combine pattern is the heart of it.

/// ### Exercise 4: A shared counter ###
/// Create a thread class `CountNumber` that implements `Runnable`. In `main`, create two objects of it to implement a SHARED
/// counter that counts from 1 to n. (The slides use n = 10 for one thread and n = 5 for the other, so both share one counter
/// and together they print 1 to 10.) Then make `main` wait for the threads and print "Count completed".
///
/// The two threads share the static variable `counter`, so they can collide: both read the same value, both print it, and
/// both add one, so a number is printed twice, or the prints come out of order. The fix is to let only ONE thread at a time
/// run the "check, print, add" steps. `synchronized` on a shared lock object does exactly that.

class CountNumber implements Runnable {
    static int counter = 1;                        // shared by every CountNumber
    static final Object lock = new Object();       // the one lock that guards it

    int n;
    Thread t;

    CountNumber(int n) {
        this.n = n;
        t = new Thread(this);
        t.start();                                  // the thread starts as soon as the object is made
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {                   // only one thread can be in here at a time
                if (counter > n) {
                    break;
                }
                System.out.println(counter++);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        CountNumber t1 = new CountNumber(10);
        CountNumber t2 = new CountNumber(5);

        t1.t.join();
        t2.t.join();
        System.out.println("Count completed");
    }
}

/// The numbers always come out as 1 to 10, each exactly once, in order. Which thread printed which number can differ from one
/// run to another, but the output cannot. The thread with `n = 5` stops as soon as the shared counter passes 5, and the one
/// with `n = 10` carries on to 10.
///
/// Without `join()`, `main` would finish first and "Count completed" would print while the counting was still going on. The
/// JVM keeps running until every thread ends, but the message would be wrong.
