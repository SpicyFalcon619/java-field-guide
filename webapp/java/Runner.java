import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Runs one compiled lesson example exactly as `java Main` would, and records WHICH LINE of the
 * example printed each piece of output. The site uses that to show a block's own output, and
 * it runs the same way on a build machine (a real JDK) and in the browser (CheerpJ).
 *
 * Usage: java Runner <MainClass> <ExampleFile.java> <resultFile> [inputFile]
 *
 *   MainClass     the class whose main() to run
 *   ExampleFile   the source file name, so frames from the example are told apart from the JDK's
 *   resultFile    where the JSON result goes: {"segments":[[line,"text",stream],...]} (stream 0 = out, 1 = err)
 *   inputFile     optional; one line of typed input per line. Scanner / BufferedReader read from it.
 *
 * Printing made inside a method is credited to the line that CALLED it at the top of the example's
 * own code (the outermost frame in the example file), because that is the line the reader is looking at.
 */
public class Runner {
    // A program that prints this much is almost certainly a runaway loop. It is stopped.
    private static final int MAX_CHARS = 200_000;

    private static final List<Object[]> segments = new ArrayList<>();
    private static String exampleFile;
    private static int total = 0;
    private static boolean tripped = false;
    // While the uncaught exception is printed, that text is credited to the line that threw it.
    private static int forcedLine = -1;

    /** Receives everything written to System.out / System.err and notes which example line wrote it. */
    private static class Recorder extends OutputStream {
        private final int stream;

        Recorder(int stream) {
            this.stream = stream;
        }

        @Override
        public synchronized void write(int b) {
            write(new byte[] {(byte) b}, 0, 1);
        }

        @Override
        public synchronized void write(byte[] bytes, int off, int len) {
            if (len <= 0) return;
            if (tripped) {
                // After the limit only error text (a short stack trace) still gets through.
                if (stream == 1) record(new String(bytes, off, len, StandardCharsets.UTF_8), 1);
                return;
            }
            total += len;
            if (total > MAX_CHARS) {
                tripped = true;
                record("\n[Stopped: the program printed more than " + MAX_CHARS + " characters.]\n", 1);
                // Raising here surfaces inside the learner's println and ends their program.
                throw new IllegalStateException("Output limit reached");
            }
            record(new String(bytes, off, len, StandardCharsets.UTF_8), stream);
        }

        private void record(String text, int which) {
            int line = forcedLine;
            if (line < 0) {
                for (StackTraceElement frame : new Throwable().getStackTrace()) {
                    if (exampleFile.equals(frame.getFileName())) line = frame.getLineNumber();
                }
            }
            Object[] last = segments.isEmpty() ? null : segments.get(segments.size() - 1);
            if (last != null && (int) last[0] == line && (int) last[2] == which) last[1] = (String) last[1] + text;
            else segments.add(new Object[] {line, text, which});
        }
    }

    /** Typed input, served one line at a time; each line is echoed as it is handed over, like a terminal. */
    private static class TypedInput extends InputStream {
        private final List<byte[]> lines = new ArrayList<>();
        private byte[] current;
        private int position;

        TypedInput(List<String> typed) {
            for (String line : typed) lines.add((line + "\n").getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public int read() {
            byte[] one = new byte[1];
            int n = read(one, 0, 1);
            return n < 0 ? -1 : one[0] & 0xff;
        }

        @Override
        public int read(byte[] b, int off, int len) {
            if (len == 0) return 0;
            if (current == null || position >= current.length) {
                if (lines.isEmpty()) return -1;
                current = lines.remove(0);
                position = 0;
                System.out.print(new String(current, StandardCharsets.UTF_8));
            }
            int n = Math.min(len, current.length - position);
            System.arraycopy(current, position, b, off, n);
            position += n;
            return n;
        }

        @Override
        public int available() {
            return current == null ? 0 : current.length - position;
        }
    }

    private static String json(String text) {
        StringBuilder out = new StringBuilder("\"");
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            switch (c) {
                case '"': out.append("\\\""); break;
                case '\\': out.append("\\\\"); break;
                case '\n': out.append("\\n"); break;
                case '\r': out.append("\\r"); break;
                case '\t': out.append("\\t"); break;
                default:
                    if (c < 0x20) out.append(String.format("\\u%04x", (int) c));
                    else out.append(c);
            }
        }
        return out.append('"').toString();
    }

    private static void writeResult(Path file) {
        StringBuilder out = new StringBuilder("{\"segments\":[");
        synchronized (segments) {
            for (int i = 0; i < segments.size(); i++) {
                Object[] s = segments.get(i);
                if (i > 0) out.append(',');
                out.append('[').append(s[0]).append(',').append(json((String) s[1])).append(',').append(s[2]).append(']');
            }
        }
        out.append("]}");
        try {
            Files.write(file, out.toString().getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            // Nothing sensible left to do while the JVM is shutting down.
        }
    }

    public static void main(String[] args) throws Exception {
        String mainClass = args[0];
        exampleFile = args[1];
        Path result = Path.of(args[2]);
        List<String> typed = new ArrayList<>();
        if (args.length > 3 && Files.exists(Path.of(args[3]))) {
            String all = Files.readString(Path.of(args[3]), StandardCharsets.UTF_8);
            if (!all.isEmpty()) for (String line : all.split("\n", -1)) typed.add(line);
            if (!typed.isEmpty() && typed.get(typed.size() - 1).isEmpty()) typed.remove(typed.size() - 1);
        }

        System.setOut(new PrintStream(new Recorder(0), true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(new Recorder(1), true, StandardCharsets.UTF_8));
        System.setIn(new TypedInput(typed));
        // The result is written when the JVM ends, however it ends (finished, System.exit, other threads done).
        Runtime.getRuntime().addShutdownHook(new Thread(() -> writeResult(result)));

        try {
            Method main = Class.forName(mainClass).getMethod("main", String[].class);
            main.invoke(null, (Object) new String[0]);
        } catch (InvocationTargetException wrapper) {
            Throwable cause = wrapper.getCause();
            StringBuilder trace = new StringBuilder("Exception in thread \"main\" ").append(cause).append('\n');
            int where = -1;
            for (StackTraceElement frame : cause.getStackTrace()) {
                if (exampleFile.equals(frame.getFileName())) {
                    trace.append("\tat ").append(frame).append('\n');
                    if (where < 0) where = frame.getLineNumber();
                }
            }
            forcedLine = where;
            System.err.print(trace);
        }
    }
}
