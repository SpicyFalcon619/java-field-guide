import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

/**
 * Runs inside CheerpJ (Java in the browser), started by public/runner.html. It compiles one source file, then runs
 * the program, with its console wired to the page: whatever the program prints is sent out through emit(), and
 * whenever it reads the keyboard (Scanner, System.in) it waits in readLine() until the visitor types a line.
 *
 * emit() and readLine() are native methods, implemented in JavaScript by runner.html.
 * Rebuild the jar after editing this file:  npm run build-runner
 */
public final class LiveRunner {
    /** stream: 0 is normal output, 1 is the error stream, 2 is a compiler message. */
    private static native void emit(int stream, String text);

    /** Blocks until the page supplies a line of input. Returns null when the visitor ends the input. */
    private static native String readLine();

    private static final class Sink extends OutputStream {
        private final int stream;

        Sink(int stream) {
            this.stream = stream;
        }

        @Override
        public void write(int b) {
            write(new byte[] { (byte) b }, 0, 1);
        }

        @Override
        public void write(byte[] bytes, int off, int len) {
            if (len > 0) emit(stream, new String(bytes, off, len, StandardCharsets.UTF_8));
        }
    }

    /** System.in: hands out one typed line at a time, so a Scanner waits for you instead of racing ahead. */
    private static final class Keyboard extends InputStream {
        private byte[] line = new byte[0];
        private int next = 0;
        private boolean ended = false;

        private boolean fill() {
            if (next < line.length) return true;
            if (ended) return false;
            String typed = readLine();
            if (typed == null) {
                ended = true;
                return false;
            }
            line = (typed + "\n").getBytes(StandardCharsets.UTF_8);
            next = 0;
            return true;
        }

        @Override
        public int read() {
            return fill() ? (line[next++] & 0xff) : -1;
        }

        @Override
        public int read(byte[] bytes, int off, int len) {
            if (len == 0) return 0;
            if (!fill()) return -1;
            int count = Math.min(len, line.length - next);
            System.arraycopy(line, next, bytes, off, count);
            next += count;
            return count;
        }

        @Override
        public int available() {
            return line.length - next;
        }
    }

    /** Compiles and runs a program, or runs one that was compiled earlier (--jar). The exit code says how it went. */
    public static void main(String[] args) throws Exception {
        PrintStream out = new PrintStream(new Sink(0), true, "UTF-8");
        PrintStream err = new PrintStream(new Sink(1), true, "UTF-8");
        System.setOut(out);
        System.setErr(err);
        System.setIn(new Keyboard());

        Method main;
        if (args[0].equals("--jar")) {
            // Already compiled when the site was built: args are --jar, the jar's path and the main class.
            // This skips the compiler, which is by far the slowest part of a first run in the browser.
            URLClassLoader loader = new URLClassLoader(new URL[] { new File(args[1]).toURI().toURL() }, LiveRunner.class.getClassLoader());
            try {
                main = Class.forName(args[2], true, loader).getMethod("main", String[].class);
                main.setAccessible(true);
            } catch (ReflectiveOperationException | LinkageError e) {
                emit(2, "Could not load the prepared program: " + e + "\n");
                System.exit(2);
                return;
            }
        } else {
            // args: the file holding the program's text, the name it must be saved under (it has to match the
            // public class), and a folder to work in.
            File workDir = new File(args[2]);
            String outDir = new File(workDir, "classes").getPath();
            new File(outDir).mkdirs();
            File sourceFile = new File(workDir, args[1]);
            Files.write(sourceFile.toPath(), Files.readAllBytes(new File(args[0]).toPath()));
            String source = sourceFile.getPath();

            // 1. Compile, keeping the compiler's messages instead of letting them vanish into a console.
            StringWriter messages = new StringWriter();
            int code;
            try {
                Method compile = Class.forName("com.sun.tools.javac.Main").getMethod("compile", String[].class, PrintWriter.class);
                code = (Integer) compile.invoke(null, new String[] { "-g", "-d", outDir, "-encoding", "UTF-8", "-Xlint:none", source }, new PrintWriter(messages, true));
            } catch (ReflectiveOperationException | RuntimeException e) {
                emit(2, "The compiler could not start: " + e + "\n");
                System.exit(2);
                return;
            }
            if (code != 0) {
                emit(2, messages.toString().replace(workDir.getPath() + "/", ""));
                System.exit(1);
                return;
            }

            // 2. Load the result with a fresh class loader, so running an edited program never reuses an old class.
            URLClassLoader loader = new URLClassLoader(new URL[] { new File(outDir).toURI().toURL() }, LiveRunner.class.getClassLoader());
            main = findMain(outDir, loader);
            if (main == null) {
                emit(2, "No main method found. A program needs: public static void main(String[] args)\n");
                System.exit(1);
                return;
            }
        }

        // 3. Run it.
        int exit = 0;
        try {
            main.invoke(null, (Object) new String[0]);
        } catch (InvocationTargetException e) {
            printTrace(e.getCause(), err);
            exit = 1;
        }
        out.flush();
        err.flush();
        System.exit(exit);
    }

    private static Method findMain(String outDir, ClassLoader loader) throws Exception {
        File[] files = new File(outDir).listFiles((dir, name) -> name.endsWith(".class") && name.indexOf('$') < 0);
        if (files == null) return null;
        Arrays.sort(files, (a, b) -> a.getName().equals("Main.class") ? -1 : b.getName().equals("Main.class") ? 1 : a.getName().compareTo(b.getName()));
        for (File file : files) {
            String name = file.getName().substring(0, file.getName().length() - ".class".length());
            try {
                Method method = Class.forName(name, true, loader).getMethod("main", String[].class);
                if (Modifier.isStatic(method.getModifiers())) {
                    method.setAccessible(true);
                    return method;
                }
            } catch (NoSuchMethodException | LinkageError ignored) {
                // not a class with main
            }
        }
        return null;
    }

    /** The usual "Exception in thread main" report, cut off before our own plumbing starts. */
    private static void printTrace(Throwable thrown, PrintStream err) {
        StringWriter text = new StringWriter();
        PrintWriter writer = new PrintWriter(text);
        writer.println("Exception in thread \"main\" " + thrown);
        for (StackTraceElement frame : thrown.getStackTrace()) {
            String owner = frame.getClassName();
            if (owner.startsWith("java.lang.reflect.") || owner.startsWith("jdk.internal.reflect.") || owner.equals("LiveRunner")) break;
            writer.println("\tat " + frame);
        }
        for (Throwable cause = thrown.getCause(); cause != null; cause = cause.getCause()) writer.println("Caused by: " + cause);
        writer.flush();
        err.print(text);
        err.flush();
    }
}
