package view.Frame;

import view.Dialog.FailDialog;
import view.Dialog.SuccessDialog;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterFrame extends JFrame implements ActionListener {
    private final int WIDTH =300;
    private final int HEIGHT = 320;
    private JTextField userText, passText, passCheckText;
    private JPasswordField passField;//这里要设计PassField，但是还没写
    private final JButton registerButton = new JButton();

    public RegisterFrame() {
        initTextArea();
        initButton("Image\\InitFrame\\Register_Light.png", "Image\\InitFrame\\" +
                "Register_Dark.png", registerButton, 100, 220, 100, 56);
        initBackground("Background\\L_R_bg.gif");
        initFrame();
        this.setVisible(true);
    }

    public void initFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Jungle Register");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
    }

    public void initBackground(String Address) {
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
    }
    //这里设置了三个TextField，用于输入数据
    public void initTextArea() {
        Color backGroundColor = new Color(0, 0, 0, 128);

        userText = new JTextField("Enter your Username here", 20);
        userText.setFont(new Font("Calibri", Font.ITALIC, 8));
//        userText.setOpaque(false);
        userText.setBackground(backGroundColor);
        userText.setForeground(Color.WHITE);
        userText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userText.setText("");
                userText.setFont(new Font("Calibri", Font.BOLD, 12));
                userText.setForeground(Color.WHITE);
                userText.setBackground(backGroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userText.getText().equals("")) {
                    userText.setText("Create your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    userText.setForeground(Color.WHITE);
                    userText.setBackground(backGroundColor);
                }
            }
        });
        userText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        userText.setBounds(50, 20, 200, 50);

        passText = new JTextField("Use a strong Password", 20);
        passText.setFont(new Font("Calibri", Font.ITALIC, 8));

//        passText.setOpaque(false);
        passText.setBackground(backGroundColor);
        passText.setForeground(Color.WHITE);
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passText.setText("");
                passText.setFont(new Font("Calibri", Font.BOLD, 12));
                passText.setForeground(Color.WHITE);
                passText.setBackground(backGroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passText.getText().equals("")) {
                    passText.setText("Enter your Password here");
                    passText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    passText.setForeground(Color.WHITE);
                    passText.setBackground(backGroundColor);
                }
            }
        });
        passText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passText.setBounds(50, 90, 200, 50);

        passCheckText = new JTextField("Check your Password again", 20);
        passCheckText.setFont(new Font("Calibri", Font.ITALIC, 8));

//        passText.setOpaque(false);
        passCheckText.setBackground(backGroundColor);
        passCheckText.setForeground(Color.WHITE);
        passCheckText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passCheckText.setText("");
                passCheckText.setFont(new Font("Calibri", Font.BOLD, 12));
                passCheckText.setForeground(Color.WHITE);
                passCheckText.setBackground(backGroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passCheckText.getText().equals("")) {
                    passCheckText.setText("Check your Password again");
                    passCheckText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    passCheckText.setForeground(Color.WHITE);
                    passCheckText.setBackground(backGroundColor);
                }
            }
        });
        passCheckText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passCheckText.setBounds(50, 160, 200, 50);

        this.getContentPane().add(userText);
        this.getContentPane().add(passText);
        this.getContentPane().add(passCheckText);
    }

    public void initButton(String Address1, String Address2, JButton jb, int x, int y, int width, int height) {
        ImageIcon Button_Light = new ImageIcon(Address1);
        Image img = Button_Light.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Light_New = new ImageIcon(newimg);
        ImageIcon Button_Dark = new ImageIcon(Address2);
        Image img2 = Button_Dark.getImage();
        Image newimg2 = img2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Dark_New = new ImageIcon(newimg2);

        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);

        jb.setBounds(x, y, width, height);
        jb.setIcon(Button_Light_New);
        jb.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                jb.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jb.setIcon(Button_Light_New);
            }
        });
        jb.addActionListener(this);
        this.getContentPane().add(jb);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            // Get the entered username and password
            String username = userText.getText();

            // Check if the entered username and password match the local file
            try {
                BufferedReader reader = new BufferedReader(new FileReader("Information\\users.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    String storedUsername = parts[0];
                    if (username.equals(storedUsername)) {
                        System.out.println("This username has been used.");
                        new FailDialog("This username has been used.",this);
                        return;
                    }
                }
                if (passText.getText().equals(passCheckText.getText())) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter("Information\\users.txt", true));
                    writer.write(username + ":" + passText.getText()+":"+0.0+"\n");
                    System.out.println("You have successfully registered!");
                    LoginFrame loginFrame = new LoginFrame();
                    new SuccessDialog("You have successfully registered!", loginFrame);
                    this.setVisible(false);

                    writer.close();
                    return;
                } else {
                    System.out.println("The passwords you entered do not match, please try again.");
                    new FailDialog("This username has already been used, please choose another one.",this);
                }
                reader.close();
            } catch (IOException ex) {
                System.out.println("Error reading file.");
                ex.printStackTrace();
            }
        }
    }


    private static class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius + 1;
            insets.top = radius + 1;
            insets.bottom = radius + 2;
            return insets;
        }
    }
}
