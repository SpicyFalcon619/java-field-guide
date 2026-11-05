/*
 * Threads and GUI: Event handling
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - The event model: sources, events and listeners
///   - Reacting to a button click with an ActionListener (a class, an anonymous class and a lambda)
///   - Reading from text fields and updating labels
///   - Mouse and keyboard events, and a timer
/// ============================================================================


/// ### Topic 1: What is an event? ###
/// A window sits there until the user does something: clicks a button, types a key, moves the mouse. Each of those is an
/// EVENT. Your program does not poll for them in a loop. Instead you tell the component "when this happens, call my code".
///
/// Three players are involved:
///
///     EVENT SOURCE    the component the user touched (a JButton)
///     EVENT           an object describing what happened (an ActionEvent)
///     LISTENER        your object, with a method the source calls when the event happens (an ActionListener)
///
/// ANALOGY: A doorbell. The button is the source. Pressing it creates an event. The speaker inside the house is the listener
/// that reacts. You register the listener once; after that, every press triggers it.
///
/// The steps are always the same:
///    1. Make a listener (an object that implements the right interface).
///    2. Register it with the component: `button.addActionListener(listener)`.
///
/// NOTE: As in the last lesson, these programs open windows, so they are not run inside the page. Copy them into your IDE.

/// ### Topic 2: A click, way 1: a listener class ###
/// `ActionListener` is an interface (from the Interfaces lesson) with ONE method, `actionPerformed`. Implement it, then
/// register an object of your class.

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class HelloListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("The button was clicked!");
    }
}

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Click me");
        frame.setSize(250, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton("Click");
        button.addActionListener(new HelloListener());      // register the listener

        frame.add(button);
        frame.setVisible(true);
    }
}

/// Each click prints a line in the console. The button never calls your code by name. It calls `actionPerformed` on whatever
/// listener you registered, because it knows the ActionListener interface. That is polymorphism, doing real work.

/// ### Topic 3: A click, way 2: an anonymous class ###
/// Making a whole named class for a three-line reaction is heavy. An ANONYMOUS CLASS defines and creates the listener on the
/// spot, with no name: `new ActionListener() { ... }`.

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Counter");
        frame.setSize(250, 120);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Clicks: 0");
        JButton button = new JButton("Add one");

        button.addActionListener(new ActionListener() {
            int clicks = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                clicks++;
                label.setText("Clicks: " + clicks);
            }
        });

        frame.add(button);
        frame.add(label);
        frame.setVisible(true);
    }
}

/// Every click updates the label with `setText`. The anonymous class keeps its own `clicks` count between clicks, because it
/// is an object that stays alive.

/// ### Topic 4: A click, way 3: a lambda ###
/// `ActionListener` has a single method, so it is a functional interface and a lambda fits. This is the shortest and the most
/// common style now.

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Greeter");
        frame.setSize(320, 130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JTextField nameField = new JTextField(12);
        JButton button = new JButton("Greet");
        JLabel result = new JLabel(" ");

        button.addActionListener(e -> {
            String name = nameField.getText();
            result.setText("Hello, " + name + "!");
        });

        frame.add(new JLabel("Name:"));
        frame.add(nameField);
        frame.add(button);
        frame.add(result);
        frame.setVisible(true);
    }
}

/// `getText()` reads what the user typed, and `setText(...)` writes a label. Read, compute, show: that is the shape of almost
/// every GUI reaction. The `e` is the ActionEvent, which you may ignore when you do not need it.

/// ### Topic 5: Which button was clicked? ###
/// One listener can serve several buttons. The event tells you which: `e.getSource()` returns the component, and
/// `e.getActionCommand()` returns its text.

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Calculator extends JFrame implements ActionListener {
    JTextField a = new JTextField(5);
    JTextField b = new JTextField(5);
    JLabel result = new JLabel("Result:");
    JButton plus = new JButton("+");
    JButton multiply = new JButton("x");

    Calculator() {
        super("Mini calculator");
        setSize(380, 120);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(a);
        add(b);
        add(plus);
        add(multiply);
        add(result);

        plus.addActionListener(this);              // this window listens to both buttons
        multiply.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            double x = Double.parseDouble(a.getText());
            double y = Double.parseDouble(b.getText());
            if (e.getSource() == plus) {
                result.setText("Result: " + (x + y));
            } else {
                result.setText("Result: " + (x * y));
            }
        } catch (NumberFormatException ex) {
            result.setText("Please type two numbers");
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }
}

/// Notice the try and catch from the Exceptions lessons: whatever a user types in a text field can be anything, so
/// the conversion with `parseDouble` must be guarded.

/// ### Topic 6: Mouse and keyboard events ###
/// Other kinds of events use other listener interfaces:
///
///     ActionListener       a button click, Enter in a text field, a menu choice
///     MouseListener        mouse pressed, released, clicked, entered, exited
///     MouseMotionListener  the mouse moving or dragging
///     KeyListener          key pressed, released, typed
///     WindowListener       window opened, closing, minimised
///
/// Some of these have several methods. To avoid writing the ones you do not need, there are ADAPTER classes
/// (`MouseAdapter`, `KeyAdapter`) with every method already present and empty. You extend one and override only what you
/// need.

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mouse and keys");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel info = new JLabel("Click anywhere, or press a key", SwingConstants.CENTER);
        frame.add(info);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                info.setText("Clicked at x=" + e.getX() + ", y=" + e.getY());
            }
        });

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                info.setText("You pressed: " + KeyEvent.getKeyText(e.getKeyCode()));
            }
        });

        frame.setVisible(true);
        frame.requestFocus();           // so the frame receives the key presses
    }
}

/// ### Topic 7: Doing something every so often: javax.swing.Timer ###
/// A game needs to move things many times a second, and a clock needs to tick. A `Timer` fires an `ActionEvent` every given
/// number of milliseconds. Each tick calls your ActionListener, on the correct thread for Swing. (This is the right tool
/// here; a loop with `Thread.sleep` would freeze the window.)

import javax.swing.*;
import java.awt.FlowLayout;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clock");
        frame.setSize(220, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel clock = new JLabel("--:--:--");
        frame.add(clock);

        Timer timer = new Timer(1000, e -> {                            // every 1000 ms
            clock.setText(LocalTime.now().withNano(0).toString());
        });
        timer.start();

        frame.setVisible(true);
    }
}

/// WARNING: Never put a long task (sleeping, downloading, a big loop) inside an event handler. Swing does everything on one
/// thread, so while your handler is busy the whole window freezes. Long jobs belong on another thread, from the Threads
/// lessons, which then hands results back with `SwingUtilities.invokeLater`.
///
/// REMEMBER: A listener is an object registered with a component. `addActionListener(...)` takes a class, an anonymous class
/// or a lambda. Read with `getText()`, show with `setText()`. Use adapters for listeners with many methods, a Timer for
/// repeated actions, and keep handlers short.

/// ============================================================================
/// YOUR TURN
///   1. Make a button that changes its own text to "Clicked!" when pressed.
///   2. Make a counter with three buttons: "+", "-" and "Reset", and a label showing the value.
///   3. Build a mini calculator with two text fields and four operator buttons, with error handling for bad input.
///   4. Show the position of the mouse in a label while it moves (use `MouseMotionListener` or `MouseMotionAdapter`).
///   5. Make a window with a text field where pressing Enter adds the typed text to a `JTextArea`.
///   6. Use a Timer to count seconds on a label. Then add a Stop button that stops the timer.
/// ============================================================================
