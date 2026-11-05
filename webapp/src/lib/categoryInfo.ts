// Plain-language description of each track, shown on the home page and at the top
// of the track page. Keyed by the category slug (the folder name, slugified).
// A track with no entry here simply shows without a description, so a brand-new
// folder still appears in the guide without touching this file.
export const CATEGORY_INFO: Record<string, string> = {
  'java-basics':
    'Your first programs: printing, variables and data types, user input, decisions, loops, arrays, methods and strings.',
  'classes-and-objects':
    'Classes and objects, constructors, this, initialization blocks, variable scope, and static members.',
  'inheritance-and-polymorphism':
    'extends and super, method overloading and overriding, one name with many forms, casting and instanceof.',
  'encapsulation-and-access':
    'public, private and protected, getters and setters, and the final keyword.',
  'working-with-objects':
    'Passing objects to methods, returning them, copy constructors and constructor chaining.',
  'abstraction-and-interfaces':
    'Abstract classes and interfaces: describing what something does without saying how.',
  'exceptions-and-files':
    'Handling errors with try, catch and finally, throwing your own, and reading and writing files.',
  lab: 'The twelve labs of the lab course, each a set of tasks to try yourself with a worked solution.',
}
