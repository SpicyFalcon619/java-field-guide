/*
 * Practice: GUI and event handling problem set
 */

/// ============================================================================
/// WHAT YOU WILL LEARN
///   - Building a window with JFrame, JLabel, JTextField and JButton, and laying it out with FlowLayout
///   - Reacting to a click with an ActionListener (a class, an anonymous class and a lambda)
///   - Reading text from a field with `getText()` and showing results with `setText()`
///   - Keeping a little state between clicks, such as a countdown step
/// ============================================================================

/// Five problems that were set for a class test. Each one asks for a small window that reacts to buttons.
///
/// NOTE: The programs in this set open real windows, so they cannot run inside this page. Copy one into your IDE, or save it
/// as `Main.java` and run `javac Main.java` then `java Main`. Every program here compiles. The pictures in the comments show
/// what you will see.

/// ### Problem 1: Add and Subtract ###
/// A window has three text fields (N1 and N2 for typing two integers, and Result for showing the answer) and two buttons,
/// Add and Subtract. "Add" puts N1 + N2 in Result, "Subtract" puts N1 - N2 in Result. The test asks you to write ONLY the
/// event handling function, but here is the whole program, so you can run it. The part the question wants is
/// `actionPerformed`.

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class AddSubtract extends JFrame implements ActionListener {
    JTextField n1 = new JTextField(5);
    JTextField n2 = new JTextField(5);
    JTextField result = new JTextField(8);
    JButton addButton = new JButton("Add");
    JButton subtractButton = new JButton("Subtract");

    AddSubtract() {
        super("Add and Subtract");
        setSize(380, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        result.setEditable(false);
        add(new JLabel("N1:"));
        add(n1);
        add(new JLabel("N2:"));
        add(n2);
        add(new JLabel("Result:"));
        add(result);
        add(addButton);
        add(subtractButton);

        addButton.addActionListener(this);          // this window listens to both buttons
        subtractButton.addActionListener(this);
    }

    // The event handling function: it runs every time either button is pressed.
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int a = Integer.parseInt(n1.getText().trim());
            int b = Integer.parseInt(n2.getText().trim());

            if (e.getSource() == addButton) {
                result.setText(String.valueOf(a + b));
            } else if (e.getSource() == subtractButton) {
                result.setText(String.valueOf(a - b));
            }
        } catch (NumberFormatException ex) {
            result.setText("Bad input");              // the text in a field was not an integer
        }
    }
}

public class Main {
    public static void main(String[] args) {
        new AddSubtract().setVisible(true);
    }
}

///     N1: [ 5 ]  N2: [ 2 ]  Result: [ 7 ]            (after pressing Add)
///     N1: [ 5 ]  N2: [ 2 ]  Result: [ 3 ]            (after pressing Subtract)
///
/// `e.getSource()` tells the handler WHICH button was pressed, so one method can serve both. `getText()` always returns a
/// String, so `Integer.parseInt` turns it into a number, and the `catch` handles the case where the user typed letters.

/// ### Problem 2: A button that changes its label ###
/// Create a button with the label "click me". When it is pressed the label changes to "clicked". If it is pressed again, the
/// label goes back to "click me".

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Toggle");
        frame.setSize(250, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton button = new JButton("click me");
        button.addActionListener(e -> {
            if (button.getText().equals("click me")) {
                button.setText("clicked");
            } else {
                button.setText("click me");
            }
        });

        frame.add(button);
        frame.setVisible(true);
    }
}

/// The label itself remembers the state, so no extra variable is needed. Compare with `equals`, never `==`, when you test
/// the content of a String.

/// ### Problem 3: Copy the text into a label ###
/// Create a text field (empty at the start), a label that also shows nothing at the start, and a Submit button. When the button
/// is pressed, the text in the text field is shown in the label.

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Echo");
        frame.setSize(300, 130);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JTextField field = new JTextField(15);
        JLabel label = new JLabel("");
        JButton submit = new JButton("Submit");

        submit.addActionListener(e -> label.setText(field.getText()));

        frame.add(field);
        frame.add(submit);
        frame.add(label);
        frame.setVisible(true);
    }
}

///     [ hello there      ] [Submit]
///     hello there                        (the label, after pressing Submit)

/// ### Problem 4: The countdown ###
/// Remember racing friends as a child: "3... 2... 1... GO!" The frame uses `FlowLayout` and has only two components, a label
/// (empty at the start) and a button. The first press shows "3...", the next "2...", then "1...", and last "GO!". Complete the
/// part of this code that says "Write your code here":
///
///     JFrame fr = new JFrame("Run!");
///     fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
///     fr.setSize(250, 100);
///     JLabel label = new JLabel();
///     JButton button = new JButton("Click!");
///     // Write your code here.
///     fr.setVisible(true);

import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        JFrame fr = new JFrame("Run!");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fr.setSize(250, 100);
        JLabel label = new JLabel();
        JButton button = new JButton("Click!");

        // Write your code here.
        fr.setLayout(new FlowLayout());
        fr.add(label);
        fr.add(button);

        String[] steps = {"3...", "2...", "1...", "GO!"};
        button.addActionListener(new ActionListener() {
            int step = 0;                                  // which message comes next

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText(steps[step]);
                step++;
                if (step == steps.length) {
                    button.setEnabled(false);              // the race has started, nothing more to count
                }
            }
        });

        fr.setVisible(true);
    }
}

///     press 1: 3...    press 2: 2...    press 3: 1...    press 4: GO!

/// An anonymous class is used here because the listener has to REMEMBER how many times the button was pressed, and an object
/// can keep a field (`step`) between calls. A lambda cannot change a local variable of `main`, but an anonymous class can have
/// its own field.

/// ### Problem 5: A foot and inch converter ###
/// a) Write a GUI application with a Foot text field, an Inch text field and a "Convert to Inch" button. When the button is
/// pressed, the value of Foot (decimals such as 10 or 5.5 are allowed) is converted to inches and shown in the Inch field.
/// 1 foot = 12 inches.
///
/// --- Part a: Convert to Inch -----

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Converter");
        frame.setSize(260, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JTextField foot = new JTextField("0", 10);
        JTextField inch = new JTextField("0", 10);
        JButton toInch = new JButton("Convert to Inch");

        toInch.addActionListener(e -> {
            try {
                double feet = Double.parseDouble(foot.getText().trim());
                inch.setText(String.format("%.2f", feet * 12));
            } catch (NumberFormatException ex) {
                inch.setText("Bad input");
            }
        });

        frame.add(new JLabel("Foot"));
        frame.add(foot);
        frame.add(toInch);
        frame.add(new JLabel("Inch"));
        frame.add(inch);
        frame.setVisible(true);
    }
}

///     Foot [ 5.5 ]
///     [Convert to Inch]
///     Inch [ 66.00 ]
///
/// b) Add another button, "Convert to Foot", beside "Convert to Inch". "Convert to Inch" works as before. "Convert to Foot"
/// reads the Inch field and shows the result, divided by 12, in the Foot field.
///
/// --- Part b: both directions -----

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Converter");
        frame.setSize(340, 170);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JTextField foot = new JTextField("0", 10);
        JTextField inch = new JTextField("0", 10);
        JButton toInch = new JButton("Convert to Inch");
        JButton toFoot = new JButton("Convert to Foot");

        toInch.addActionListener(e -> {
            try {
                double feet = Double.parseDouble(foot.getText().trim());
                inch.setText(String.format("%.2f", feet * 12));
            } catch (NumberFormatException ex) {
                inch.setText("Bad input");
            }
        });

        toFoot.addActionListener(e -> {
            try {
                double inches = Double.parseDouble(inch.getText().trim());
                foot.setText(String.format("%.2f", inches / 12));
            } catch (NumberFormatException ex) {
                foot.setText("Bad input");
            }
        });

        frame.add(new JLabel("Foot"));
        frame.add(foot);
        frame.add(toInch);
        frame.add(toFoot);
        frame.add(new JLabel("Inch"));
        frame.add(inch);
        frame.setVisible(true);
    }
}

/// Each button has its own listener that reads from one field and writes to the other. Rounding to two decimals with
/// `String.format("%.2f", ...)` keeps results such as 1/3 foot tidy.
