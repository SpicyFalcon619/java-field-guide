/*
 * Lab: Lab 7: GUI with Swing
 */

/// ============================================================================
/// WHAT YOU WILL PRACTISE
///   - Building windows with JFrame, JPanel and layout managers
///   - Text fields, buttons, labels, check boxes and combo boxes
///   - Reacting to events with listeners
///   - Reading input safely, and showing results
/// ============================================================================
///
/// NOTE: Every program in this lab opens windows, so none of them can run inside the page. Each one is compiled when this guide
/// is built, so it works as written. Copy it into your IDE (or save it as `Main.java`, run `javac Main.java` then `java Main`) to
/// see it on your screen. The sketches show what to expect.


/// ### Task 1: A login form ###
/// Build a window with a username field, a password field and a Login button. When the button is pressed, check the details
/// (for the exercise, username "admin" and password "1234") and show "Welcome" or "Wrong details" in a label.

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setSize(320, 200);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new GridLayout(4, 2, 5, 5));

            JTextField user = new JTextField();
            JPasswordField pass = new JPasswordField();
            JButton login = new JButton("Login");
            JLabel message = new JLabel(" ");

            login.addActionListener(e -> {
                String u = user.getText();
                String p = new String(pass.getPassword());
                if (u.equals("admin") && p.equals("1234")) {
                    message.setText("Welcome, " + u);
                } else {
                    message.setText("Wrong details");
                }
            });

            frame.add(new JLabel("Username:"));
            frame.add(user);
            frame.add(new JLabel("Password:"));
            frame.add(pass);
            frame.add(new JLabel());
            frame.add(login);
            frame.add(new JLabel("Result:"));
            frame.add(message);

            frame.setVisible(true);
        });
    }
}

///     +-------------------------------+
///     | Login                         |
///     +-------------------------------+
///     | Username: [__________]        |
///     | Password: [**********]        |
///     |            [  Login   ]        |
///     | Result:    Welcome, admin      |
///     +-------------------------------+

/// ### Task 2: A BMI calculator ###
/// Ask for weight (kg) and height (m), and show the BMI (weight / height squared) with a category: under 18.5 underweight,
/// under 25 normal, under 30 overweight, otherwise obese. Catch bad input.

import javax.swing.*;
import java.awt.*;

public class Main {
    static String category(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25) return "Normal";
        if (bmi < 30) return "Overweight";
        return "Obese";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("BMI calculator");
            frame.setSize(340, 220);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JTextField weight = new JTextField(6);
            JTextField height = new JTextField(6);
            JButton calculate = new JButton("Calculate");
            JLabel result = new JLabel("Enter weight and height");

            calculate.addActionListener(e -> {
                try {
                    double w = Double.parseDouble(weight.getText());
                    double h = Double.parseDouble(height.getText());
                    double bmi = w / (h * h);
                    result.setText(String.format("BMI %.1f: %s", bmi, category(bmi)));
                } catch (NumberFormatException ex) {
                    result.setText("Please enter two numbers");
                }
            });

            frame.add(new JLabel("Weight (kg):"));
            frame.add(weight);
            frame.add(new JLabel("Height (m):"));
            frame.add(height);
            frame.add(calculate);
            frame.add(result);
            frame.setVisible(true);
        });
    }
}

/// ### Task 3: A calculator with a keypad ###
/// Build a calculator with a display at the top, and a grid of buttons for digits and + - * /, = and C. Keep the first number
/// and the operator in fields while the second is typed.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CalculatorFrame extends JFrame implements ActionListener {
    private final JTextField display = new JTextField();
    private double first = 0;
    private String operator = "";
    private boolean startNew = true;

    CalculatorFrame() {
        super("Calculator");
        setSize(260, 340);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));

        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFont(display.getFont().deriveFont(22f));
        add(display, BorderLayout.NORTH);

        JPanel keys = new JPanel(new GridLayout(4, 4, 4, 4));
        String[] labels = {"7", "8", "9", "/", "4", "5", "6", "*", "1", "2", "3", "-", "C", "0", "=", "+"};
        for (String label : labels) {
            JButton b = new JButton(label);
            b.addActionListener(this);
            keys.add(b);
        }
        add(keys, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String key = e.getActionCommand();

        if (key.matches("[0-9]")) {
            display.setText(startNew ? key : display.getText() + key);
            startNew = false;
        } else if (key.equals("C")) {
            display.setText("");
            first = 0;
            operator = "";
            startNew = true;
        } else if (key.equals("=")) {
            if (!operator.isEmpty() && !display.getText().isEmpty()) {
                double second = Double.parseDouble(display.getText());
                display.setText(String.valueOf(compute(first, second, operator)));
                operator = "";
                startNew = true;
            }
        } else {
            if (!display.getText().isEmpty()) {
                first = Double.parseDouble(display.getText());
                operator = key;
                startNew = true;
            }
        }
    }

    private double compute(double a, double b, String op) {
        switch (op) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            default:  return b == 0 ? 0 : a / b;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculatorFrame().setVisible(true));
    }
}

/// ### Task 4: Choices: check boxes, combo box and text area ###
/// A pizza order form: a combo box for size, check boxes for toppings, and an "Order" button that writes a summary in a text area.

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pizza order");
            frame.setSize(360, 320);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout(5, 5));

            JComboBox<String> size = new JComboBox<>(new String[] {"Small", "Medium", "Large"});
            JCheckBox cheese = new JCheckBox("Extra cheese");
            JCheckBox olives = new JCheckBox("Olives");
            JCheckBox mushrooms = new JCheckBox("Mushrooms");
            JButton order = new JButton("Order");
            JTextArea summary = new JTextArea(6, 25);
            summary.setEditable(false);

            JPanel options = new JPanel(new FlowLayout());
            options.add(new JLabel("Size:"));
            options.add(size);
            options.add(cheese);
            options.add(olives);
            options.add(mushrooms);

            order.addActionListener(e -> {
                StringBuilder text = new StringBuilder("Order: " + size.getSelectedItem() + " pizza");
                if (cheese.isSelected()) text.append("\n  + extra cheese");
                if (olives.isSelected()) text.append("\n  + olives");
                if (mushrooms.isSelected()) text.append("\n  + mushrooms");
                summary.setText(text.toString());
            });

            frame.add(options, BorderLayout.NORTH);
            frame.add(new JScrollPane(summary), BorderLayout.CENTER);
            frame.add(order, BorderLayout.SOUTH);
            frame.setVisible(true);
        });
    }
}

/// ### Task 5: A counter with a menu ###
/// Add a menu bar with File > Reset and File > Exit to a counter window that has "+" and "-" buttons.

import javax.swing.*;
import java.awt.FlowLayout;

public class Main {
    static int count = 0;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Counter");
            frame.setSize(260, 140);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());

            JLabel label = new JLabel("0");
            JButton plus = new JButton("+");
            JButton minus = new JButton("-");
            plus.addActionListener(e -> label.setText(String.valueOf(++count)));
            minus.addActionListener(e -> label.setText(String.valueOf(--count)));

            JMenuBar bar = new JMenuBar();
            JMenu file = new JMenu("File");
            JMenuItem reset = new JMenuItem("Reset");
            JMenuItem exit = new JMenuItem("Exit");
            reset.addActionListener(e -> {
                count = 0;
                label.setText("0");
            });
            exit.addActionListener(e -> System.exit(0));
            file.add(reset);
            file.add(exit);
            bar.add(file);
            frame.setJMenuBar(bar);

            frame.add(minus);
            frame.add(label);
            frame.add(plus);
            frame.setVisible(true);
        });
    }
}

/// ### Check yourself ###
///   - Why is `invokeLater` used to create the window?
///   - What does a layout manager do, and which one suits a keypad?
///   - Why do we wrap `Double.parseDouble` in try and catch?
///   - How does one listener serve many buttons?

/// ### Try next ###
///   1. Add a Clear button to the login form and make the password field show the characters while a check box is ticked.
///   2. Make the BMI window change the colour of the result label by category.
///   3. Add a "%" and a "sqrt" button to the calculator.
///   4. Build a to-do list: a text field, an Add button, and a list that shows the items with a Remove button.
