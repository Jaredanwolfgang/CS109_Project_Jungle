package view.Frame;

import model.User.User;
import view.Dialog.FailDialog;
import view.Dialog.SuccessDialog;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class LoginFrame extends JFrame implements ActionListener{
    private final Dimension screenSize = new Dimension(300,250);

/*
    private final int WIDTH = 300;
    private final int HEIGHT = 250;
*/
    private JTextField userText, passText;
    private JPasswordField passwordField;//这里要设置PassField，但是还没写
    private JButton loginButton = new JButton();
    private Frame frame;

    public LoginFrame(Frame frame) {
        this.frame = frame;
        initUsernameTextField();
        initPasswordTextField();
        initPasswordPassField();

        initButton();
        initBackground("Background\\Spring.gif");
        initFrame();
        this.setVisible(false);
    }

    public void initFrame() {
        this.setSize((int)screenSize.getWidth(),(int)screenSize.getHeight());
        this.setTitle("Jungle Login");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.getLoginFrame().setVisible(false);
                frame.getInitFrame().setVisible(true);
            }
        });
    }

    public void initBackground(String Address) {
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, 300, 250);
        this.getContentPane().add(background);
    }
    //这里设置了两个TextField，用于输入数据
    public void initUsernameTextField() {
        Color backGroundColor = new Color(0,0,0,128);

        userText = new JTextField("Enter your Username here", 20);
        userText.setFont(new Font("Calibri", Font.ITALIC, 12));
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
                    userText.setText("Enter your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 12));
                    userText.setForeground(Color.WHITE);
                    userText.setBackground(backGroundColor);
                }
            }
        });
        userText.setBorder(new RoundBorder(10,Color.WHITE));
        userText.setBounds(50, 20, 200, 50);
        this.getContentPane().add(userText);
    }
    public void initPasswordTextField(){
        Color backGroundColor = new Color(0,0,0,128);
        passText = new JTextField("Enter your Password here", 20);
        passText.setFont(new Font("Calibri", Font.ITALIC, 12));

//        passText.setOpaque(false);
        passText.setBackground(backGroundColor);
        passText.setForeground(Color.WHITE);
        passText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passText.setVisible(false);
                passwordField.setVisible(true);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText()==""||passwordField.getText()==null) {
                    passText.setVisible(true);
                    passwordField.setVisible(false);
                }
            }
        });
        passText.setBorder(new RoundBorder(10,Color.WHITE));
        passText.setBounds(50, 90, 200, 50);
        this.getContentPane().add(passText);
    }
    public void initPasswordPassField(){
        passwordField = new JPasswordField("");
        Color backGroundColor = new Color(0,0,0,128);
        passwordField.setBackground(backGroundColor);
        passwordField.setForeground(Color.WHITE);
        passwordField.setBorder(new RoundBorder(10,Color.WHITE));
        passwordField.setBounds(50, 90, 200, 50);
        this.getContentPane().add(passwordField);
        passwordField.setVisible(false);
    }
    //初始化按钮
    public void initButton() {
        System.out.println("LoginFrame:loginButton is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\InitFrame\\Login_Light.png").getScaledInstance(100, 56, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\InitFrame\\Login_Dark.png").getScaledInstance(100, 56, Image.SCALE_SMOOTH));

        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);

        loginButton.setBounds(100, 150, 100, 56);
        loginButton.setIcon(Button_Light_New);
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {}
            @Override
            public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e){
                loginButton.setIcon(Button_Dark_New);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setIcon(Button_Light_New);
            }
        });
        loginButton.addActionListener(this);
        this.getContentPane().add(loginButton);
    }
    //设置按钮的动作，触发之后会读取并比对本地文件中的用户名和密码
    public void actionPerformed(ActionEvent e) {
        /*if (e.getSource() == loginButton) {
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
                        System.out.println("Login successful!");
                        this.setVisible(false);
                        StartFrame startFrame = new StartFrame(frame);
                        startFrame.setVisible(false);
                        SuccessDialog successDialog = new SuccessDialog("Login successful", startFrame);
                        user.setUsername(storedUsername);
                        user.setPassword(storedPassword);
                        user.setScore(Double.parseDouble(parts[2]));
                        return;
                    }
                }
                System.out.println("Incorrect username or password.");
                this.setVisible(false);
                FailDialog failDialog = new FailDialog("Incorrect username or password",this);
            } catch (IOException ex) {
                System.out.println("Error reading file.");
                ex.printStackTrace();
            }
        }*/
        if(frame.getGameController().onPlayerClickLoginButton(userText.getText(),passwordField.getText())){
            this.setVisible(false);
            new SuccessDialog("Login successful",frame.getStartFrame());
        }else{
            new FailDialog("Incorrect username or password",this);
        }
    }


    //设置圆角边框
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




