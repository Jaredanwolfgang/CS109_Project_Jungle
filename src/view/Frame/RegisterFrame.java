package view.Frame;

import view.Dialog.FailDialog;
import view.Dialog.SuccessDialog;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterFrame extends JFrame implements ActionListener {
    private final Dimension screenSize = new Dimension(300,320);
/*    private final int WIDTH =300;
    private final int HEIGHT = 320;*/
    private JTextField userText, passText, passCheckText;
    private JPasswordField passField,passCheckField;//这里要设计PassField，但是还没写
    private final JButton registerButton = new JButton();
    private Frame frame;

    public RegisterFrame(Frame frame) {
        this.frame = frame;
        initUsernameTextField();
        initPasswordTextField();
        initPasswordPassField();
        initPasswordCheckPassField();
        initPasswordCheckTextField();

        initButton();
        initBackground("Background\\Spring.gif");
        initFrame();
        this.setVisible(false);
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
        background.setBounds(0, 0, 300, 320);
        this.getContentPane().add(background);
    }

    public void initUsernameTextField() {
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
        this.getContentPane().add(userText);
    }
    public void initPasswordTextField(){
        Color backGroundColor = new Color(0, 0, 0, 128);
        passText = new JTextField("Use a strong Password", 20);
        passText.setFont(new Font("Calibri", Font.ITALIC, 8));
        passText.setBackground(backGroundColor);
        passText.setForeground(Color.WHITE);
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passText.setVisible(false);
                passField.setVisible(true);
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (passField.getText().equals("")) {
                    passText.setVisible(true);
                    passField.setVisible(false);
                }
            }
        });
        passText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passText.setBounds(50, 90, 200, 50);
        this.getContentPane().add(passText);
    }
    public void initPasswordPassField(){
        Color backGroundColor = new Color(0, 0, 0, 128);
        passField = new JPasswordField("");
        passField.setBackground(backGroundColor);
        passField.setForeground(Color.WHITE);
        passField.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passField.setBounds(50, 90, 200, 50);

        passField.setVisible(false);
        this.getContentPane().add(passField);
    }
    public void initPasswordCheckTextField(){
        Color backGroundColor = new Color(0, 0, 0, 128);
        passCheckText = new JTextField("Check your Password again", 20);
        passCheckText.setFont(new Font("Calibri", Font.ITALIC, 8));
//        passText.setOpaque(false);
        passCheckText.setBackground(backGroundColor);
        passCheckText.setForeground(Color.WHITE);
        passCheckText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passCheckText.setVisible(false);
                passCheckField.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passCheckField.getText().equals("")) {
                    passCheckText.setVisible(true);
                    passCheckField.setVisible(false);
                }
            }
        });
        passCheckText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passCheckText.setBounds(50, 160, 200, 50);
        this.getContentPane().add(passCheckText);
    }
    public void initPasswordCheckPassField(){
        Color backGroundColor = new Color(0, 0, 0, 128);
        passCheckField = new JPasswordField("");
        passCheckField.setBackground(backGroundColor);
        passCheckField.setForeground(Color.WHITE);
        passCheckField.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
        passCheckField.setBounds(50, 160, 200, 50);

        passCheckField.setVisible(false);
        this.getContentPane().add(passCheckField);
    }

    public void initButton() {
        System.out.println("RegisterFrame: registerButton is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\InitFrame\\Register_Light.png").getScaledInstance(100, 56, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\InitFrame\\Register_Dark.png").getScaledInstance(100, 56, Image.SCALE_SMOOTH));

        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setOpaque(false);

        registerButton.setBounds(100, 220, 100, 56);
        registerButton.setIcon(Button_Light_New);
        registerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setIcon(Button_Light_New);
            }
        });
        registerButton.addActionListener(this);
        this.getContentPane().add(registerButton);
    }

    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource() == registerButton) {
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
        }*/
        if(passText.getText() != passCheckField.getText()){
            new FailDialog("The passwords do not match.",this);
            return;
        }
        if(frame.getGameController().onPlayerClickRegisterButton(userText.getText(),passField.getText())){
            this.setVisible(false);
            new SuccessDialog("Successfully registered!",frame.getLoginFrame());
        }else{
            new FailDialog("The username has been used.",this);
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
