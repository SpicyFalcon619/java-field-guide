// Plain-language meanings of the words that make beginners stop reading. A lesson never has to stop and
// explain a word like "polymorphism": the first time it appears in a block of explanation it is
// underlined, and a tap or hover shows the short meaning with a link to the longer one here.
//
// To add a word: give it an id (lowercase, dashes), the term as written, any other spellings that should
// be recognised (plural, abbreviation), a `short` meaning that fits in one sentence, and a `long` one
// with an everyday comparison where it helps. `lesson` names the lesson file (by its title) to read next.

export interface GlossaryEntry {
  id: string
  term: string
  aliases?: string[]
  short: string
  long: string
  /** Title of the lesson that teaches it properly. */
  lesson?: string
  /**
   * False for words that are also everyday English (this, new, public): they are explained on the glossary
   * page, but never underlined automatically in a lesson, where that would only get in the way.
   */
  auto?: boolean
}

export const GLOSSARY: GlossaryEntry[] = [
  // ------------------------------------------------------------------ how Java works
  {
    id: 'program',
    term: 'program',
    aliases: ['programs'],
    short: 'A list of instructions the computer follows, one after another.',
    long: 'A program is a recipe for a computer. Each line is an instruction ("print this", "add these two numbers"), and the computer carries them out in order, from the top. Everything you build in this guide, from a one-line greeting to a game, is a program.',
  },
  {
    id: 'source-code',
    term: 'source code',
    short: 'The instructions as a human writes them, in a text file.',
    long: 'Source code is the text you type. A Java source file ends in .java. The computer cannot run it directly: it has to be translated first, which is the compiler\'s job.',
  },
  {
    id: 'compiler',
    term: 'compiler',
    aliases: ['compile', 'compiles', 'compiled', 'compilation', 'javac'],
    short: 'The tool that translates your source code into something the computer can run, and checks it for mistakes while doing so.',
    long: 'The Java compiler is called javac. It reads your .java file, reports every grammar mistake it finds (these are called compile errors), and if there are none it produces a .class file of bytecode. Think of it as a strict proofreader and translator in one: it will not let a sentence with broken grammar through.',
    lesson: 'What is Java, and how does a program run?',
  },
  {
    id: 'bytecode',
    term: 'bytecode',
    short: 'The in-between language the compiler produces, which the Java Virtual Machine understands.',
    long: 'Bytecode is not your source code and not the machine language of your processor: it sits in the middle. That is why the same .class file runs on Windows, Mac and Linux. Each of them has its own Java Virtual Machine that knows how to run it.',
    lesson: 'What is Java, and how does a program run?',
  },
  {
    id: 'jvm',
    term: 'JVM',
    aliases: ['Java Virtual Machine'],
    short: 'The Java Virtual Machine: the program that runs your compiled Java.',
    long: 'The JVM reads bytecode and carries it out on the real computer. Because every operating system has its own JVM that understands the same bytecode, Java programs are "write once, run anywhere".',
    lesson: 'What is Java, and how does a program run?',
  },
  {
    id: 'jdk',
    term: 'JDK',
    aliases: ['Java Development Kit'],
    short: 'The Java Development Kit: everything you install to write and run Java (the compiler, the JVM and the standard library).',
    long: 'To write Java you install a JDK. It contains javac (the compiler), java (which starts the JVM) and the standard library of ready-made tools such as Scanner and ArrayList.',
    lesson: 'What is Java, and how does a program run?',
  },
  {
    id: 'syntax',
    term: 'syntax',
    short: 'The grammar rules of a programming language.',
    long: 'Syntax is to Java what grammar is to English: where the semicolons go, how braces pair up, how a statement is spelled. A program with a syntax mistake will not compile.',
  },
  {
    id: 'statement',
    term: 'statement',
    aliases: ['statements'],
    short: 'One complete instruction, usually ending with a semicolon.',
    long: 'In Java most instructions are statements, and each ends with a semicolon: System.out.println("Hi"); is one statement. The semicolon plays the role of the full stop in a sentence.',
  },
  {
    id: 'expression',
    term: 'expression',
    aliases: ['expressions'],
    short: 'Anything that works out to a single value, like 3 + 4 or age > 18.',
    long: 'An expression is a piece of code that produces a value. 3 + 4 produces 7. age > 18 produces true or false. You can put an expression anywhere a value is allowed.',
  },
  {
    id: 'comment',
    term: 'comment',
    aliases: ['comments'],
    short: 'A note for humans that the computer ignores.',
    long: 'In Java a comment starts with // (one line) or sits between /* and */ (many lines). Comments explain why the code does something. The compiler throws them away, so they cost nothing.',
  },
  {
    id: 'identifier',
    term: 'identifier',
    aliases: ['identifiers'],
    short: 'A name you give to something: a variable, a method, a class.',
    long: 'An identifier is any name you choose. It may contain letters, digits, underscores and dollar signs, may not start with a digit, and may not be a reserved word such as class or int. Java is case-sensitive, so age and Age are different names.',
  },
  {
    id: 'keyword',
    term: 'keyword',
    aliases: ['keywords', 'reserved word', 'reserved words'],
    short: 'A word that already has a fixed meaning in Java, such as class, if or return.',
    long: 'Keywords are the vocabulary Java reserves for itself. You cannot use them as your own names. Your editor usually shows them in a special colour.',
  },
  {
    id: 'camelcase',
    term: 'camelCase',
    short: 'A naming style: the first word is lowercase and every later word starts with a capital, like totalMarks.',
    long: 'Java programmers write variable and method names in camelCase (totalMarks, printReport) and class names in PascalCase, where the very first letter is capital too (StudentRecord). It is not required, but everyone expects it, so following it makes your code easy for others to read.',
  },

  // ------------------------------------------------------------------ variables and types
  {
    id: 'variable',
    term: 'variable',
    aliases: ['variables'],
    short: 'A named box in memory that holds a value.',
    long: 'Think of a labelled jar: the label is the variable\'s name and what is inside is its value. int age = 20; makes a jar labelled age and puts 20 in it. You can later take the value out, or replace it with another.',
    lesson: 'Variables and data types',
  },
  {
    id: 'data-type',
    term: 'data type',
    aliases: ['data types'],
    short: 'The kind of value a variable holds: a whole number, a decimal, a letter, true/false, text.',
    long: 'Java wants to know in advance what each variable will hold, so it can reserve the right amount of memory and stop you from mixing things that do not belong together (like adding a name to a number). The main types are int, double, char, boolean and String.',
    lesson: 'Variables and data types',
  },
  {
    id: 'primitive',
    term: 'primitive type',
    aliases: ['primitive types', 'primitive', 'primitives'],
    short: 'One of the eight basic, built-in types, like int or double, that store a plain value directly.',
    long: 'Java has eight primitive types: byte, short, int, long, float, double, char and boolean. They hold their value right inside the variable. Anything else (String, arrays, your own classes) is a reference type.',
    lesson: 'Variables and data types',
  },
  {
    id: 'literal',
    term: 'literal',
    aliases: ['literals'],
    short: 'A value written directly in the code, like 42, 3.14, \'A\' or "hello".',
    long: 'A literal is the value itself, as opposed to a variable that holds a value. 20 is an int literal, 10.5 a double literal, \'A\' a char literal, "Noman" a String literal.',
  },
  {
    id: 'constant',
    term: 'constant',
    aliases: ['constants'],
    short: 'A variable whose value can never change after it is set, declared with final.',
    long: 'Write final before a variable and Java refuses any later attempt to change it. By convention constants are written in UPPER_CASE, like MAX_SPEED.',
    lesson: 'Variables and data types',
  },
  {
    id: 'operator',
    term: 'operator',
    aliases: ['operators'],
    short: 'A symbol that does something with values, like +, -, *, / or >.',
    long: 'Operators combine values into new values. Arithmetic operators calculate (+ - * / %), comparison operators ask a question (> < == !=), and logical operators combine questions (&& || !).',
    lesson: 'Operators',
  },
  {
    id: 'assignment',
    term: 'assignment',
    short: 'Storing a value in a variable with a single equals sign.',
    long: 'In age = 21; the = sign does not mean "is equal to" as in maths. It means "put the value on the right into the variable on the left". To ask whether two things are equal you use two signs: ==.',
  },
  {
    id: 'string',
    term: 'String',
    aliases: ['Strings'],
    short: 'Text: a sequence of characters between double quotes.',
    long: 'String is not one of the eight primitive types: it is a class from the standard library. That is why it starts with a capital letter and why it comes with methods such as length() and toUpperCase().',
    lesson: 'Strings',
  },
  {
    id: 'casting',
    term: 'casting',
    aliases: ['cast', 'typecasting', 'type conversion'],
    short: 'Converting a value from one type to another.',
    long: 'Java converts automatically when nothing can be lost (an int fits inside a double). When something could be lost (a double has a fraction, an int does not) you have to say so yourself: (int) 3.9 gives 3. The cut-off part is simply thrown away.',
    lesson: 'Variables and data types',
  },

  // ------------------------------------------------------------------ the shape of a program
  {
    id: 'import',
    term: 'import',
    aliases: ['imports', 'importing'],
    short: 'A line that tells Java where to find a tool that is not in the default toolbox.',
    long: 'Java comes with thousands of ready-made classes, stored in groups called packages. Only a few (like String and System) are available automatically. For the others you write an import line at the top of the file, such as import java.util.Scanner;. Without it the compiler says it cannot find the class, because it has no idea which package you mean.',
    lesson: 'Reading input with Scanner',
  },
  {
    id: 'package',
    term: 'package',
    aliases: ['packages'],
    short: 'A folder-like group of related classes, such as java.util.',
    long: 'Packages keep thousands of classes organised and prevent two classes with the same name from clashing. The dots in java.util.Scanner are the path: the class Scanner, inside util, inside java.',
    lesson: 'Reading input with Scanner',
  },
  {
    id: 'main-method',
    term: 'main method',
    aliases: [],
    short: 'The door into a program: Java starts running your code at main.',
    long: 'When you run a class, the JVM looks for a method named exactly public static void main(String[] args) and starts there. Without it nothing happens, because Java would not know where to begin.',
    lesson: 'Your first program, word by word',
  },
  {
    id: 'static',
    term: 'static',
    short: 'Belongs to the class itself, not to any one object made from it.',
    long: 'A static member exists once for the whole class and can be used without creating an object. main is static because Java has to call it before any object exists. Non-static members belong to each individual object.',
    lesson: 'Static keyword',
  },
  {
    id: 'void',
    term: 'void',
    short: 'Means "gives nothing back": a method marked void does its job and returns no value.',
    long: 'Every method declares what kind of value it hands back. void is the "nothing" answer. A method that prints something, for example, does its work and has nothing to return.',
  },
  {
    id: 'public',
    term: 'public',
    auto: false,
    short: 'Anyone, anywhere, may use this.',
    long: 'public is an access modifier: it says the class, method or variable can be used from any other code. Its opposite is private, which keeps things hidden inside the class.',
    lesson: 'Access control',
  },
  {
    id: 'method',
    term: 'method',
    aliases: ['methods'],
    short: 'A named, reusable block of code that does one job.',
    long: 'A method is a mini-program with a name. You write it once and then call it by name whenever you need that job done, as often as you like. In many other languages the same thing is called a function.',
    lesson: 'Methods',
  },
  {
    id: 'parameter',
    term: 'parameter',
    aliases: ['parameters', 'argument', 'arguments'],
    short: 'A value you hand to a method when you call it, so it can work with it.',
    long: 'In void greet(String name) the word name is a parameter: a placeholder. When you call greet("Ada"), the value "Ada" is the argument that fills it. Parameters are how methods receive their input.',
    lesson: 'Methods',
  },
  {
    id: 'return-value',
    term: 'return value',
    aliases: [],
    short: 'The answer a method hands back to whoever called it.',
    long: 'A method can finish by sending a value back with return. int add(int a, int b) { return a + b; } hands back the sum, and the caller can store it in a variable or use it in a bigger expression.',
    lesson: 'Methods',
  },
  {
    id: 'scope',
    term: 'scope',
    short: 'The part of the program where a variable can be used.',
    long: 'A variable created inside a pair of braces { } only exists inside them. Outside, Java has never heard of it. Scope keeps names from colliding and keeps memory tidy.',
    lesson: 'Variable scope and lifetime',
  },
  {
    id: 'scanner',
    term: 'Scanner',
    short: 'A ready-made tool for reading what the user types.',
    long: 'Scanner belongs to the java.util package, so you must import it. new Scanner(System.in) connects it to the keyboard, and then nextLine(), nextInt() and nextDouble() read a line of text, a whole number or a decimal.',
    lesson: 'Reading input with Scanner',
  },

  // ------------------------------------------------------------------ control flow
  {
    id: 'condition',
    term: 'condition',
    aliases: ['conditions', 'boolean expression'],
    short: 'A yes-or-no question the program asks, like age >= 18.',
    long: 'A condition is an expression whose answer is true or false. if, while and for all use one to decide what happens next.',
    lesson: 'Making decisions with if and else',
  },
  {
    id: 'loop',
    term: 'loop',
    aliases: ['loops', 'looping'],
    short: 'Code that repeats until a condition says stop.',
    long: 'A loop runs the same block again and again. Java has for (when you know how many times), while (repeat as long as a condition holds), do-while (like while, but always at least once) and for-each (visit every item of a collection).',
    lesson: 'Loops',
  },
  {
    id: 'iteration',
    term: 'iteration',
    aliases: ['iterations', 'iterate'],
    short: 'One trip around a loop.',
    long: 'If a loop repeats five times, it has five iterations. "To iterate over" a list means to visit its items one by one.',
    lesson: 'Loops',
  },
  {
    id: 'array',
    term: 'array',
    aliases: ['arrays'],
    short: 'A fixed-size row of values of the same type, reached by position.',
    long: 'An array is like a row of numbered lockers: all the same size, all holding the same kind of thing. Its size is fixed when you create it. Positions are called indexes and start at 0, not 1.',
    lesson: 'Arrays',
  },
  {
    id: 'index',
    term: 'index',
    aliases: ['indexes', 'indices'],
    short: 'The position of an item in an array or list, counting from 0.',
    long: 'In an array of five items the indexes are 0, 1, 2, 3, 4. Asking for index 5 is a mistake that stops the program with an ArrayIndexOutOfBoundsException.',
    lesson: 'Arrays',
  },

  // ------------------------------------------------------------------ classes and objects
  {
    id: 'class',
    term: 'class',
    aliases: ['classes'],
    short: 'A blueprint that describes what a kind of thing knows and can do.',
    long: 'A class is a plan, like the blueprint of a house or a cookie cutter. It lists the data each thing will carry (name, age) and the actions it can perform (greet, study). The plan itself is not a house: you build actual houses from it, and those are objects.',
    lesson: 'Classes and objects',
  },
  {
    id: 'object',
    term: 'object',
    aliases: ['objects'],
    short: 'A real thing built from a class, with its own data.',
    long: 'If Student is the blueprint, then the student called Ada and the student called Noman are two objects built from it. They follow the same plan but each has its own name, age and marks. new Student() builds one.',
    lesson: 'Classes and objects',
  },
  {
    id: 'oop',
    term: 'object oriented programming',
    aliases: ['OOP', 'object-oriented programming', 'object oriented'],
    short: 'A way of organising a program as a set of objects that hold data and know how to act on it.',
    long: 'Instead of one long list of instructions, an object oriented program is a team of objects, each responsible for its own data and behaviour. Its four big ideas are encapsulation, inheritance, polymorphism and abstraction, all of which have lessons in this guide.',
  },
  {
    id: 'field',
    term: 'field',
    aliases: ['fields', 'attribute', 'attributes', 'instance variable', 'instance variables', 'member variable'],
    short: 'A variable that belongs to an object: one piece of the data it carries.',
    long: 'The fields of a Student class might be name, id and cgpa. Every student object gets its own copy of each. Another word for the same thing is attribute.',
    lesson: 'Classes and objects',
  },
  {
    id: 'constructor',
    term: 'constructor',
    aliases: ['constructors'],
    short: 'A special method that runs when an object is created, to set it up.',
    long: 'A constructor has the same name as the class and no return type. new Student("Ada", 3.9) calls it, and its job is to fill in the new object\'s fields so it starts life in a sensible state.',
    lesson: 'Constructors',
  },
  {
    id: 'this',
    term: 'this',
    auto: false,
    short: 'A word an object uses to mean "me": the object whose code is running right now.',
    long: 'Inside a constructor or method, this refers to the current object. It is mostly used to tell a field apart from a parameter with the same name: this.name = name; stores the parameter into the field.',
    lesson: 'The this keyword',
  },
  {
    id: 'new',
    term: 'new',
    auto: false,
    short: 'The keyword that creates an object.',
    long: 'new Student() asks Java to reserve memory for a brand new Student object, run its constructor, and hand back a reference to it.',
    lesson: 'Classes and objects',
  },
  {
    id: 'reference',
    term: 'reference',
    aliases: ['references', 'reference variable', 'reference type'],
    short: 'A variable that does not hold an object itself, but the address where the object lives.',
    long: 'Student s = new Student(); puts the object in memory and stores its address in s. Copying s into another variable copies the address, not the object, so both variables now point at the same object. This is the key to understanding why changes made inside a method can be seen outside it.',
    lesson: 'Call by value and call by reference',
  },
  {
    id: 'null',
    term: 'null',
    short: 'The reference that points at nothing.',
    long: 'A reference variable that has not been given an object holds null. Trying to use it (calling a method on it) stops the program with a NullPointerException, the most common beginner crash.',
  },
  {
    id: 'garbage-collection',
    term: 'garbage collection',
    aliases: ['garbage collector'],
    short: 'Java automatically cleaning up objects nobody is using any more.',
    long: 'When no variable points at an object any longer, nothing can ever reach it again, so the JVM\'s garbage collector reclaims its memory. Unlike in C, you never free memory yourself.',
  },

  // ------------------------------------------------------------------ the four pillars
  {
    id: 'encapsulation',
    term: 'encapsulation',
    short: 'Keeping an object\'s data private and letting the outside world reach it only through methods.',
    long: 'Like a capsule keeps its medicine inside, a class keeps its fields private and offers controlled doors (methods) to read or change them. That way the class can refuse nonsense, like a negative age, and can change how it works inside without breaking everything that uses it.',
    lesson: 'Encapsulation',
  },
  {
    id: 'getter',
    term: 'getter',
    aliases: ['getters', 'setter', 'setters'],
    short: 'A small public method that reads a private field (a getter) or changes it (a setter).',
    long: 'getName() returns the private field name; setName(String n) changes it, perhaps after checking the new value is sensible. They are the doors in front of encapsulated data.',
    lesson: 'Encapsulation',
  },
  {
    id: 'access-modifier',
    term: 'access modifier',
    aliases: ['access modifiers'],
    short: 'A keyword (public, private, protected) that says who is allowed to use something.',
    long: 'private: only inside the same class. protected: the same package, plus subclasses. public: anyone. With no modifier at all, the default is "package-private": anything in the same package.',
    lesson: 'Access control',
  },
  {
    id: 'inheritance',
    term: 'inheritance',
    short: 'A new class getting everything a existing class has, then adding or changing a few things.',
    long: 'A Dog class can inherit from Animal and automatically get eat() and sleep(), so you only write what makes a dog special. Inheritance models an "is a" relationship: a dog is an animal. It removes copy-and-paste.',
    lesson: 'Inheritance',
  },
  {
    id: 'superclass',
    term: 'superclass',
    aliases: ['parent class', 'base class', 'subclass', 'child class', 'derived class'],
    short: 'The class you inherit from (parent) and the class that inherits (child).',
    long: 'In class Dog extends Animal, Animal is the superclass (parent) and Dog is the subclass (child). The child gets the parent\'s accessible members, and can add its own.',
    lesson: 'Inheritance',
  },
  {
    id: 'extends',
    term: 'extends',
    short: 'The keyword that makes one class inherit from another.',
    long: 'class Dog extends Animal means: Dog is a kind of Animal. Java allows a class to extend only one other class.',
    lesson: 'Inheritance',
  },
  {
    id: 'super',
    term: 'super',
    auto: false,
    short: 'A word a child class uses to reach its parent: super() calls the parent constructor, super.method() calls the parent\'s version.',
    long: 'Inside a subclass, super means "the parent part of me". It is how a child builds its inherited half (super(name)) or reuses a parent method it has changed.',
    lesson: 'Inheritance',
  },
  {
    id: 'override',
    term: 'overriding',
    aliases: ['override', 'overrides', 'overridden', 'method overriding'],
    short: 'A child class replacing a method it inherited with its own version.',
    long: 'Animal has makeSound(). Dog overrides it to print "Woof", Cat to print "Meow". The method name and parameters stay identical; only the behaviour changes. Writing @Override above it asks the compiler to check you really are replacing something.',
    lesson: 'Method overriding',
  },
  {
    id: 'overload',
    term: 'overloading',
    aliases: ['overload', 'overloaded', 'method overloading'],
    short: 'Having several methods with the same name but different parameters.',
    long: 'print(int x) and print(String s) can live side by side. Java picks the right one by looking at what you pass in. This is the compile-time kind of polymorphism.',
    lesson: 'Method overloading',
  },
  {
    id: 'polymorphism',
    term: 'polymorphism',
    aliases: ['polymorphic'],
    short: 'One name, many forms: the same call behaves differently depending on the kind of object it is made on.',
    long: '"Poly" means many and "morph" means form. If you have a list of animals and tell each one makeSound(), the dog barks, the cat meows and the cow moos, though you wrote the same line for all of them. You can write code for the general idea (Animal) and it automatically does the right specific thing at run time.',
    lesson: 'Polymorphism',
  },
  {
    id: 'upcasting',
    term: 'upcasting',
    aliases: ['upcast'],
    short: 'Treating a specific object (a Dog) as its more general type (an Animal).',
    long: 'Animal a = new Dog(); is always safe, because every dog is an animal. The object is still a dog underneath, but through this variable you can only use what Animal offers.',
    lesson: 'Polymorphism',
  },
  {
    id: 'downcasting',
    term: 'downcasting',
    aliases: ['downcast'],
    short: 'Turning a general reference (Animal) back into the specific type (Dog) so you can use its extra methods.',
    long: 'This is not always safe: the animal might really be a cat. Check first with instanceof, or Java will stop the program with a ClassCastException.',
    lesson: 'Polymorphism',
  },
  {
    id: 'instanceof',
    term: 'instanceof',
    short: 'An operator that asks "is this object of that type?"',
    long: 'if (a instanceof Dog) is true only when a really refers to a Dog (or something that inherits from Dog). Use it before downcasting.',
    lesson: 'Polymorphism',
  },
  {
    id: 'abstraction',
    term: 'abstraction',
    short: 'Describing what something does while hiding how.',
    long: 'When you press the brake you do not need to know about hydraulics. In Java, abstract classes and interfaces let you describe the actions a thing must offer without writing the details, which the specific classes fill in.',
    lesson: 'Abstraction and interfaces',
  },
  {
    id: 'abstract-class',
    term: 'abstract class',
    aliases: ['abstract classes', 'abstract method', 'abstract methods'],
    short: 'A class that is only a partial plan: it cannot be created directly, and may leave some methods for its children to fill in.',
    long: 'You cannot make a Shape object (what would it look like?), but you can make Circle and Rectangle objects that extend an abstract Shape and each supply their own area().',
    lesson: 'Abstraction and interfaces',
  },
  {
    id: 'interface',
    term: 'interface',
    aliases: ['interfaces', 'implements'],
    short: 'A list of methods a class promises to provide.',
    long: 'An interface is a contract: any class that implements it must write every listed method. A class can implement many interfaces, which is how Java gets around the rule that a class can only extend one parent.',
    lesson: 'Abstraction and interfaces',
  },

  // ------------------------------------------------------------------ errors, files and beyond
  {
    id: 'exception',
    term: 'exception',
    aliases: ['exceptions'],
    short: 'An unexpected event that interrupts the normal flow of a program, such as dividing by zero.',
    long: 'When something goes wrong while the program is running, Java creates an exception object and starts searching for code that handles it. If none does, the program stops and prints a message. Handling exceptions with try and catch lets a program recover instead of crashing.',
    lesson: 'Exception handling',
  },
  {
    id: 'try-catch',
    term: 'try-catch',
    aliases: [],
    short: 'A way to attempt risky code (try) and say what to do if it fails (catch).',
    long: 'Code that might fail goes in the try block. If an exception happens, Java jumps to the matching catch block instead of crashing. A finally block runs afterwards no matter what, which is handy for cleaning up.',
    lesson: 'Exception handling',
  },
  {
    id: 'throw',
    term: 'throw',
    aliases: ['throws'],
    auto: false,
    short: 'throw raises an exception on purpose; throws on a method says it might raise one.',
    long: 'throw new IllegalArgumentException("Age cannot be negative"); stops the current method and hands the problem to whoever called it. Writing throws in the method\'s heading warns callers that they may need to handle it.',
    lesson: 'Exception handling',
  },
  {
    id: 'stream',
    term: 'stream',
    aliases: ['streams', 'input stream', 'output stream'],
    short: 'A flow of data between your program and somewhere else: the keyboard, the screen, a file.',
    long: 'Java treats input and output as streams of data flowing like water through a pipe. System.in is a stream coming from the keyboard, System.out one going to the screen, and files have their own.',
    lesson: 'File handling',
  },
  {
    id: 'collection',
    term: 'collection',
    aliases: ['collections', 'collections framework'],
    short: 'A ready-made container for a group of objects, such as ArrayList or HashMap.',
    long: 'Arrays have a fixed size. Collections grow and shrink as you add and remove things, and come with methods for searching, sorting and more.',
  },
  {
    id: 'arraylist',
    term: 'ArrayList',
    short: 'A resizable list: like an array that grows when you add to it.',
    long: 'You can add(), remove() and get() items by position, and it keeps track of its own size. Unlike an array it can only hold objects, not primitives, so you write ArrayList<Integer> for numbers.',
  },
  {
    id: 'generic',
    term: 'generics',
    aliases: ['generic'],
    short: 'The <Type> in ArrayList<String>: it says what kind of thing the collection holds.',
    long: 'Generics let the compiler check that you only put Strings into a list of Strings, so a mix-up is caught before the program runs rather than crashing it later.',
  },
  {
    id: 'thread',
    term: 'thread',
    aliases: ['threads', 'multithreading'],
    short: 'A separate path of execution: it lets a program do more than one thing at a time.',
    long: 'A program normally follows a single path. Threads let it follow several at once, for example downloading a file while still responding to the mouse.',
  },
  {
    id: 'gui',
    term: 'GUI',
    aliases: ['graphical user interface'],
    short: 'A graphical user interface: windows, buttons and text boxes instead of typed text.',
    long: 'Java\'s Swing library builds GUIs out of components such as JFrame (the window), JButton and JLabel. Reacting to a click is done with event handling.',
  },
  {
    id: 'event',
    term: 'event',
    aliases: ['events', 'event handling', 'listener', 'listeners'],
    short: 'Something that happens that the program can react to, like a button click.',
    long: 'In a GUI the program mostly waits. When the user clicks, an event is created and sent to a listener, a piece of code you registered earlier to say what should happen.',
  },
]

const byId = new Map(GLOSSARY.map((entry) => [entry.id, entry]))
export const getTerm = (id: string) => byId.get(id)

const escape = (text: string) => text.replace(/[.*+?^${}()|[\]\\]/g, '\\$&')

/** Every spelling that should be recognised, longest first so "abstract class" wins over "abstract". */
const spellings = GLOSSARY.filter((entry) => entry.auto !== false).flatMap((entry) => [entry.term, ...(entry.aliases ?? [])].map((spelling) => ({ spelling, id: entry.id })))
  .sort((a, b) => b.spelling.length - a.spelling.length)

const lookup = new Map(spellings.map((s) => [s.spelling.toLowerCase(), s.id]))

/** One pattern that matches any spelling as a whole word, case-insensitively. */
export const TERM_PATTERN = new RegExp(`(?<![\\w.])(${spellings.map((s) => escape(s.spelling)).join('|')})(?![\\w(])`, 'gi')

export function termIdOf(spelling: string): string | undefined {
  return lookup.get(spelling.toLowerCase())
}

/** The ids of the glossary terms mentioned in a piece of plain text, in order of appearance. */
export function termsIn(text: string): string[] {
  const found: string[] = []
  for (const match of text.matchAll(TERM_PATTERN)) {
    const id = termIdOf(match[1])
    if (id && !found.includes(id)) found.push(id)
  }
  return found
}
