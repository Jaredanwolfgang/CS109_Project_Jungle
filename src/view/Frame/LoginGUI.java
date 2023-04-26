package view.Frame;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.io.*;

public class LoginGUI extends JFrame implements ActionListener {
    private JLabel resultLabel;
    private JTextField userText, passText;
    private JButton loginButton;

    public LoginGUI() {
        // Set the title of the frame
        super("Login");

        // Create the labels, text fields, and button
        resultLabel = new JLabel("");


        initInput();
        initFrame();
        setVisible(true);

        // Add an action listener to the login button
        loginButton.addActionListener(this);
        initFrame();
    }

    public void initInput() {
        userText = new JTextField("Enter your Username here", 20);
        userText.setFont(new Font("Calibri", Font.ITALIC, 8));
        userText.setForeground(Color.LIGHT_GRAY);
        userText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userText.setText("");
                userText.setFont(new Font("Calibri", Font.BOLD, 12));
                userText.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userText.getText().equals("")) {
                    userText.setText("Enter your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    userText.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
        userText.setBorder(new RoundBorder());
        passText = new JTextField("Enter your Password here", 20);
        passText.setFont(new Font("Calibri", Font.ITALIC, 8));
        passText.setForeground(Color.LIGHT_GRAY);
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passText.setText("");
                passText.setFont(new Font("Calibri", Font.BOLD, 12));
                passText.setForeground(Color.BLACK);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passText.getText().equals("")) {
                    passText.setText("Enter your Password here");
                    passText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    passText.setForeground(Color.LIGHT_GRAY);
                }
            }
        });
        passText.setBorder(new RoundBorder());
        loginButton = new JButton("Login");
    }

    public void initFrame() {
        setLayout(new GridLayout(2, 2, 5, 5));
        add(userText,0);
        add(passText,1);
        add(resultLabel,2);
        add(loginButton,3);
        setSize(300, 150);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
    }

    public void actionPerformed(ActionEvent e) {
        // Get the entered username and password
        String username = userText.getText();
        String password = passText.getText();

        // Check if the entered username and password match the local file
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Information\\users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String storedUsername = parts[0];
                String storedPassword = parts[1];
                if (username.equals(storedUsername) && password.equals(storedPassword)) {
                    resultLabel.setText("Login successful!");
                    return;
                }
            }
            resultLabel.setText("Incorrect username or password.");
        } catch (IOException ex) {
            System.out.println("Error reading file.");
            ex.printStackTrace();
        }

    }

    private static class RoundBorder implements Border {
        private final int radius = 10;

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(Color.GRAY);
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    public static void main(String[] args) {
        // Create a new instance of the LoginGUI
        LoginGUI gui = new LoginGUI();
    }

}

