/*
 * Threads and GUI: GUI with Swing
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - What a GUI is, and what Swing gives you: windows, buttons, labels and text fields
///   - Building your first window with JFrame
///   - Adding components, and arranging them with FlowLayout, BorderLayout and GridLayout
///   - Panels, and simple dialog boxes with JOptionPane
/// ============================================================================


/// ### Topic 1: What is a GUI? ###
/// Until now your programs talked through a text console. A GUI (Graphical User Interface) is what you normally use: windows,
/// buttons, text boxes, menus, the mouse. Java's classic toolkit for building them is SWING, in the packages `javax.swing`
/// (the components) and `java.awt` (layouts, colours and events).
///
/// NOTE: The programs in this lesson open real windows, so they cannot run inside this page. Each block has a note saying so.
/// Copy it into your IDE, or save it as `Main.java` and run `javac Main.java` then `java Main`. The pictures in the comments
/// show what you will see.
///
/// A GUI program is built from COMPONENTS (JButton, JLabel, JTextField...) placed inside CONTAINERS (JFrame, JPanel). All of
/// them are objects, so everything you learned about classes, inheritance and interfaces applies. In fact a JButton IS-A
/// JComponent, which IS-A Container, which IS-A Component: a long inheritance chain.

/// ### Topic 2: Your first window ###
/// A `JFrame` is a top-level window with a title bar. Three calls make it appear:
///    - `setSize(width, height)`: how big it is, in pixels.
///    - `setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)`: closing the window ends the program. Without it the window
///      disappears but the program keeps running.
///    - `setVisible(true)`: show it. A new frame is invisible until you ask.

import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("My first window");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);          // centre it on the screen
        frame.setVisible(true);
    }
}

/// You will see an empty 400 by 300 window titled "My first window".

/// ### Topic 3: Adding components ###
/// Create a component and `add` it to the frame. Common ones:
///
///     JLabel       a piece of text (or an image) that the user only reads
///     JButton      a clickable button
///     JTextField   a one-line box to type in
///     JTextArea    a multi-line text box
///     JCheckBox    a tick box
///     JComboBox    a drop-down list

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Greeting");
        frame.setSize(350, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Your name:");
        JTextField field = new JTextField(15);
        JButton button = new JButton("Say hello");

        frame.add(label);
        frame.add(field);
        frame.add(button);

        frame.setVisible(true);
    }
}

///     +-------------------------------------+
///     | Greeting                      _ [] X |
///     +-------------------------------------+
///     |  Your name: [______________] [Say hello]
///     |                                      |
///     +-------------------------------------+
///
/// The button does nothing yet when you click it. Making it respond is the subject of the next lesson, Event handling.

/// ### Topic 4: Layout managers ###
/// Where does each component go? Components do not use fixed positions. A LAYOUT MANAGER arranges them, and keeps them
/// sensible when the window is resized. Set it with `setLayout(...)`. The three you need first:
///
///     FlowLayout      left to right, wrapping to a new row when full (the default for panels)
///     BorderLayout    five regions: NORTH, SOUTH, EAST, WEST and CENTER (the default for a frame)
///     GridLayout      an equal grid of rows and columns
///
/// --- BorderLayout -----

import javax.swing.*;
import java.awt.BorderLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        frame.add(new JButton("North"), BorderLayout.NORTH);
        frame.add(new JButton("South"), BorderLayout.SOUTH);
        frame.add(new JButton("West"), BorderLayout.WEST);
        frame.add(new JButton("East"), BorderLayout.EAST);
        frame.add(new JButton("Centre"), BorderLayout.CENTER);

        frame.setVisible(true);
    }
}

///     +--------------------------------+
///     |             North              |
///     +------+------------------+------+
///     | West |      Centre      | East |
///     +------+------------------+------+
///     |             South              |
///     +--------------------------------+
///
/// The centre takes all the space left over, so it grows when you resize the window.
///
/// --- GridLayout -----
/// `new GridLayout(rows, columns)`: every cell is the same size, filled left to right and top to bottom. A calculator keypad
/// is a natural fit.

import javax.swing.*;
import java.awt.GridLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Keypad");
        frame.setSize(250, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(4, 3, 5, 5));      // 4 rows, 3 columns, 5 pixel gaps

        for (int i = 1; i <= 9; i++) {
            frame.add(new JButton(String.valueOf(i)));
        }
        frame.add(new JButton("*"));
        frame.add(new JButton("0"));
        frame.add(new JButton("#"));

        frame.setVisible(true);
    }
}

/// ### Topic 5: Panels: groups of components ###
/// A `JPanel` is an invisible box that groups components, and it can have its own layout. Building a screen out of panels
/// inside panels is how real layouts are made: for example, a BorderLayout frame whose SOUTH region is a panel of buttons in
/// a FlowLayout.

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Notes");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextArea notes = new JTextArea();
        JScrollPane scroll = new JScrollPane(notes);             // adds scroll bars when needed

        JPanel buttons = new JPanel(new FlowLayout());           // a row of buttons
        buttons.add(new JButton("Save"));
        buttons.add(new JButton("Clear"));
        buttons.add(new JButton("Close"));

        frame.add(scroll, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);

        frame.setVisible(true);
    }
}

/// ### Topic 6: A class that IS a window ###
/// Writing everything in `main` gets messy. Real programs usually make their own class that extends `JFrame`, and build the
/// window in its constructor. This is inheritance at work.

import javax.swing.*;
import java.awt.FlowLayout;

class LoginWindow extends JFrame {
    LoginWindow() {
        super("Login");                                    // the title, via the parent constructor
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("User:"));
        add(new JTextField(15));
        add(new JLabel("Password:"));
        add(new JPasswordField(15));
        add(new JButton("Log in"));
    }
}

public class Main {
    public static void main(String[] args) {
        // Swing windows should be created on the "event dispatch thread". invokeLater does that.
        SwingUtilities.invokeLater(() -> new LoginWindow().setVisible(true));
    }
}

/// `SwingUtilities.invokeLater(...)` ties in with the Threads lessons. Swing is not thread-safe, so all work on windows
/// should happen on one special thread, the Event Dispatch Thread, and `invokeLater` hands your code to it. The
/// earlier examples skipped it for brevity and usually work, but this is the correct habit.

/// ### Topic 7: Simple dialogs with JOptionPane ###
/// For a quick message or question you do not need a frame at all. `JOptionPane` pops up a ready-made dialog box.

import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String name = JOptionPane.showInputDialog("What is your name?");
        JOptionPane.showMessageDialog(null, "Hello, " + name + "!");

        int answer = JOptionPane.showConfirmDialog(null, "Do you like Java?");
        if (answer == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null, "Great!");
        }
    }
}

/// The three common ones:
///
///     showMessageDialog   tell the user something (an OK button)
///     showInputDialog     ask for text, returns it as a String (or null if cancelled)
///     showConfirmDialog   a Yes / No / Cancel question, returns which button was pressed
///
/// TIP: `showInputDialog` returns a String, like `nextLine()`. For a number, convert it with `Integer.parseInt(...)` and
/// catch `NumberFormatException`, exactly as you would with Scanner.
///
/// REMEMBER: A JFrame holds components. A layout manager arranges them. JPanel groups them. `setVisible(true)` shows the
/// window, and `EXIT_ON_CLOSE` ends the program with it. Make windows on the event dispatch thread with
/// `SwingUtilities.invokeLater`.

/// ============================================================================
/// YOUR TURN
///   1. Open a 500 by 300 window titled with your name, centred on the screen.
///   2. Add a label, a text field and a button using FlowLayout.
///   3. Build a calculator keypad with GridLayout: digits 0 to 9 and the symbols + - * /.
///   4. Make a window with BorderLayout: a title label at the top, a text area in the middle, a row of three buttons at the
///      bottom (use a JPanel).
///   5. Use `JOptionPane` to ask for two numbers and show their sum in a message dialog.
///   6. Make your own `class MyWindow extends JFrame` and open it from `main` with `invokeLater`.
/// ============================================================================
