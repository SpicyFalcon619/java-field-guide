// Suggestions for the Java editor: keywords and common types, ready-made snippets (type "sout" and press Tab),
// members after a dot (System.out., Math., list. and string methods), and your own words.
import { completeAnyWord, snippetCompletion, type Completion, type CompletionContext, type CompletionResult, type CompletionSource } from '@codemirror/autocomplete'

const words = (list: string, type: Completion['type'], detail?: string): Completion[] =>
  list.split(/\s+/).filter(Boolean).map((label) => ({ label, type, detail }))

const KEYWORDS = words(
  'abstract assert boolean break byte case catch char class continue default do double else enum extends final finally float for if implements import instanceof int interface long new null package private protected public return short static super switch synchronized this throw throws try void volatile while true false var record',
  'keyword',
)

const TYPES = words(
  'String Integer Double Boolean Character Long Object Math System Scanner ArrayList LinkedList HashMap HashSet TreeMap List Map Set Queue Deque Arrays Collections StringBuilder Random Thread Runnable Exception RuntimeException IOException NullPointerException ArithmeticException IllegalArgumentException InputMismatchException ArrayIndexOutOfBoundsException File FileReader FileWriter BufferedReader Optional Comparable Comparator Iterator',
  'class',
)

// Snippets: the label is what you type; Tab jumps between the filled-in fields.
const SNIPPETS: Completion[] = [
  // psvm and main add only the method, as in IntelliJ. The class around it has its own snippet, below.
  snippetCompletion('public static void main(String[] args) {\n\t${}\n}', { label: 'psvm', detail: 'public static void main', type: 'keyword', boost: 4 }),
  snippetCompletion('public static void main(String[] args) {\n\t${}\n}', { label: 'main', detail: 'the main method', type: 'keyword', boost: 3 }),
  snippetCompletion('public class ${Main} {\n\tpublic static void main(String[] args) {\n\t\t${}\n\t}\n}', { label: 'mainclass', detail: 'a whole class with main', type: 'keyword', boost: 3 }),
  snippetCompletion('System.out.println(${});', { label: 'sout', detail: 'print a line', type: 'function', boost: 3 }),
  snippetCompletion('System.out.print(${});', { label: 'soutp', detail: 'print without a new line', type: 'function', boost: 2 }),
  snippetCompletion('System.out.printf("${%d%n}", ${value});', { label: 'soutf', detail: 'print with a format', type: 'function', boost: 2 }),
  snippetCompletion('System.out.println("${name} = " + ${name});', { label: 'soutv', detail: 'print a variable with its name', type: 'function', boost: 2 }),
  snippetCompletion('for (int ${i} = 0; ${i} < ${n}; ${i}++) {\n\t${}\n}', { label: 'fori', detail: 'counting for loop', type: 'keyword', boost: 3 }),
  snippetCompletion('for (int ${i} = ${n} - 1; ${i} >= 0; ${i}--) {\n\t${}\n}', { label: 'forr', detail: 'for loop, counting down', type: 'keyword', boost: 2 }),
  snippetCompletion('for (${String} ${item} : ${items}) {\n\t${}\n}', { label: 'foreach', detail: 'for-each loop', type: 'keyword', boost: 3 }),
  snippetCompletion('while (${condition}) {\n\t${}\n}', { label: 'while', detail: 'while loop', type: 'keyword', boost: 2 }),
  snippetCompletion('do {\n\t${}\n} while (${condition});', { label: 'dowhile', detail: 'do-while loop', type: 'keyword', boost: 2 }),
  snippetCompletion('if (${condition}) {\n\t${}\n}', { label: 'if', detail: 'if statement', type: 'keyword', boost: 2 }),
  snippetCompletion('if (${condition}) {\n\t${}\n} else {\n\t${}\n}', { label: 'ifelse', detail: 'if / else', type: 'keyword', boost: 2 }),
  snippetCompletion('if (${condition}) {\n\t${}\n} else if (${other}) {\n\t${}\n} else {\n\t${}\n}', { label: 'ifelif', detail: 'if / else if / else', type: 'keyword', boost: 2 }),
  snippetCompletion('switch (${value}) {\n\tcase ${value1}:\n\t\t${}\n\t\tbreak;\n\tdefault:\n\t\t${}\n}', { label: 'switch', detail: 'switch statement', type: 'keyword', boost: 2 }),
  snippetCompletion('${String} ${result} = switch (${value}) {\n\tcase ${value1} -> "${one}";\n\tdefault -> "${other}";\n};', { label: 'switchx', detail: 'switch expression with arrows', type: 'keyword', boost: 2 }),
  snippetCompletion('Scanner ${scan} = new Scanner(System.in);', { label: 'scan', detail: 'a Scanner for the keyboard', type: 'function', boost: 3 }),
  snippetCompletion('System.out.print("${Enter a number: }");\nint ${n} = ${scan}.nextInt();', { label: 'readint', detail: 'ask for a whole number', type: 'function', boost: 2 }),
  snippetCompletion('System.out.print("${Enter your name: }");\nString ${name} = ${scan}.nextLine();', { label: 'readline', detail: 'ask for a line of text', type: 'function', boost: 2 }),
  snippetCompletion('import java.util.Scanner;', { label: 'impscan', detail: 'import Scanner', type: 'keyword' }),
  snippetCompletion('import java.util.*;', { label: 'imputil', detail: 'import java.util', type: 'keyword' }),
  snippetCompletion('class ${Name} {\n\t${}\n}', { label: 'class', detail: 'a class', type: 'keyword', boost: 2 }),
  snippetCompletion(
    'class ${Name} {\n\tprivate ${String} ${name};\n\n\tpublic ${Name}(${String} ${name}) {\n\t\tthis.${name} = ${name};\n\t}\n\n\tpublic ${String} get${Name2}() {\n\t\treturn ${name};\n\t}\n}',
    { label: 'classf', detail: 'class with a field, constructor and getter', type: 'keyword', boost: 2 },
  ),
  snippetCompletion('public ${Name}(${params}) {\n\t${}\n}', { label: 'ctor', detail: 'a constructor', type: 'keyword', boost: 2 }),
  snippetCompletion('public ${String} get${Name}() {\n\treturn ${name};\n}\n\npublic void set${Name}(${String} ${name}) {\n\tthis.${name} = ${name};\n}', { label: 'getset', detail: 'a getter and a setter', type: 'keyword', boost: 2 }),
  snippetCompletion('public ${void} ${name}(${}) {\n\t${}\n}', { label: 'method', detail: 'an instance method', type: 'keyword', boost: 2 }),
  snippetCompletion('public static ${int} ${name}(${int a, int b}) {\n\t${return a + b;}\n}', { label: 'smethod', detail: 'a static method', type: 'keyword', boost: 2 }),
  snippetCompletion('@Override\npublic String toString() {\n\treturn ${};\n}', { label: 'tostring', detail: 'override toString()', type: 'keyword', boost: 2 }),
  snippetCompletion('@Override\npublic boolean equals(Object ${o}) {\n\tif (this == ${o}) return true;\n\tif (!(${o} instanceof ${Name})) return false;\n\t${Name} ${other} = (${Name}) ${o};\n\treturn ${true};\n}', { label: 'equalsm', detail: 'override equals()', type: 'keyword' }),
  snippetCompletion('interface ${Name} {\n\t${void} ${action}();\n}', { label: 'interface', detail: 'an interface', type: 'keyword', boost: 2 }),
  snippetCompletion('abstract class ${Name} {\n\tabstract ${void} ${action}();\n}', { label: 'abstract', detail: 'an abstract class', type: 'keyword', boost: 2 }),
  snippetCompletion('class ${Child} extends ${Parent} {\n\t${}\n}', { label: 'extends', detail: 'a child class', type: 'keyword', boost: 2 }),
  snippetCompletion('try {\n\t${}\n} catch (${Exception} ${e}) {\n\tSystem.out.println(${e}.getMessage());\n}', { label: 'try', detail: 'try / catch', type: 'keyword', boost: 3 }),
  snippetCompletion('try {\n\t${}\n} catch (${Exception} ${e}) {\n\t${}\n} finally {\n\t${}\n}', { label: 'tryf', detail: 'try / catch / finally', type: 'keyword', boost: 2 }),
  snippetCompletion('throw new ${IllegalArgumentException}("${message}");', { label: 'throw', detail: 'throw an exception', type: 'keyword', boost: 2 }),
  snippetCompletion('ArrayList<${String}> ${list} = new ArrayList<>();', { label: 'alist', detail: 'a new ArrayList', type: 'function', boost: 2 }),
  snippetCompletion('HashMap<${String}, ${Integer}> ${map} = new HashMap<>();', { label: 'hmap', detail: 'a new HashMap', type: 'function', boost: 2 }),
  snippetCompletion('int[] ${numbers} = {${a}, ${b}, ${c}};', { label: 'arr', detail: 'an int array with values', type: 'function', boost: 2 }),
  snippetCompletion('int[] ${numbers} = new int[${size}];', { label: 'newarr', detail: 'an empty int array', type: 'function', boost: 2 }),
  snippetCompletion('Thread ${t} = new Thread(() -> {\n\t${}\n});\n${t}.start();', { label: 'thread', detail: 'start a thread', type: 'function', boost: 2 }),
]

const PRINT = 'println print printf format flush'
const MATH = 'sqrt pow abs max min round floor ceil random PI E sin cos tan log log10 exp cbrt hypot signum toRadians toDegrees floorDiv floorMod'
const STR =
  'length charAt substring indexOf lastIndexOf contains startsWith endsWith equals equalsIgnoreCase compareTo toUpperCase toLowerCase trim strip replace split isEmpty isBlank concat repeat toCharArray matches format valueOf join chars'
const LIST = 'add get set remove size isEmpty contains clear indexOf addAll sort forEach stream iterator subList toArray removeIf containsAll'
const MAP = 'put get getOrDefault containsKey containsValue remove size isEmpty keySet values entrySet clear putIfAbsent forEach merge'
const SCANNER = 'nextInt nextDouble nextLine next nextBoolean nextLong hasNext hasNextInt hasNextLine close'
const BUILDER = 'append insert delete deleteCharAt reverse toString length charAt setCharAt indexOf replace capacity setLength'

// What follows a dot, by the name before it. Unknown names fall back to the common methods of strings, lists and maps.
const MEMBERS: Record<string, Completion[]> = {
  'System.out': words(PRINT, 'method', 'PrintStream'),
  'System.err': words(PRINT, 'method', 'PrintStream'),
  System: words('out err in currentTimeMillis nanoTime exit getProperty lineSeparator arraycopy', 'function', 'System'),
  Math: words(MATH, 'function', 'Math'),
  Integer: words('parseInt valueOf toString MAX_VALUE MIN_VALUE compare sum max min toBinaryString toHexString', 'function', 'Integer'),
  Double: words('parseDouble valueOf toString compare MAX_VALUE MIN_VALUE isNaN sum max min', 'function', 'Double'),
  Boolean: words('parseBoolean valueOf toString TRUE FALSE', 'function', 'Boolean'),
  Character: words('isDigit isLetter isLetterOrDigit isUpperCase isLowerCase toUpperCase toLowerCase isWhitespace getNumericValue toString', 'function', 'Character'),
  String: words('valueOf format join copyValueOf', 'function', 'String'),
  Arrays: words('toString sort fill copyOf copyOfRange asList equals deepToString stream binarySearch', 'function', 'Arrays'),
  Collections: words('sort reverse shuffle max min unmodifiableList swap frequency emptyList', 'function', 'Collections'),
  List: words('of copyOf', 'function', 'List'),
  Thread: words('sleep currentThread start join interrupt', 'function', 'Thread'),
}

const generic = [
  ...words(STR, 'method', 'String'),
  ...words(LIST, 'method', 'List'),
  ...words(MAP, 'method', 'Map'),
  ...words(SCANNER, 'method', 'Scanner'),
  ...words(BUILDER, 'method', 'StringBuilder'),
  ...words('length getClass hashCode toString equals getMessage printStackTrace', 'method', 'Object'),
]
// The same name can belong to several kinds (get, add, remove). Show each once.
const GENERIC = generic.filter((item, index) => generic.findIndex((other) => other.label === item.label) === index)

const memberSource: CompletionSource = (context: CompletionContext): CompletionResult | null => {
  const before = context.matchBefore(/[A-Za-z_][\w.]*\.\w*/)
  if (!before) return null
  const dot = before.text.lastIndexOf('.')
  const owner = before.text.slice(0, dot)
  return { from: before.from + dot + 1, options: MEMBERS[owner] ?? GENERIC, validFor: /^\w*$/ }
}

const baseSource: CompletionSource = (context) => {
  const before = context.matchBefore(/\w+/)
  if (!before || (before.from === before.to && !context.explicit)) return null
  // Right after a dot the member list takes over.
  if (before.from > 0 && context.state.sliceDoc(before.from - 1, before.from) === '.') return null
  return { from: before.from, options: [...SNIPPETS, ...KEYWORDS, ...TYPES], validFor: /^\w*$/ }
}

// Words from your own code, minus the ones already offered above (so String is not listed twice).
const known = new Set([...SNIPPETS, ...KEYWORDS, ...TYPES].map((item) => item.label))
const ownWords: CompletionSource = async (context) => {
  const result = await completeAnyWord(context)
  if (!result) return null
  return { ...result, options: result.options.filter((item) => !known.has(item.label)) }
}

export const javaCompletions: CompletionSource[] = [memberSource, baseSource, ownWords]
