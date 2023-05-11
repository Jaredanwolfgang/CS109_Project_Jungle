package view.Frame;

import controller.GameController;
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
    private JTextField userText;
    private JPasswordField passwordField;
    private JButton loginButton = new JButton();
    private Frame frame;
    //This variable is used to distinguish the Login Frame for User1 Login and User2 Login
    private int state;

    public LoginFrame(Frame frame) {
        this.frame = frame;
        initUsernameTextField();
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
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(GameController.user1 == null) {
                    frame.getLoginFrame().setVisible(false);
                    frame.getInitFrame().setVisible(true);
                }else{
                    frame.getLoginFrame().setVisible(false);
                    frame.getStartFrame().setVisible(true);
                }
            }
        });
    }
    public void initBackground(String Address) {
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, 300, 250);
        this.getContentPane().add(background);
    }
    //Username uses TextField, while the password uses PasswordField
    public void initUsernameTextField() {
        Color backGroundColor = new Color(0,0,0,128);

        userText = new JTextField("Enter your Username here", 20);
        userText.setFont(new Font("Calibri", Font.ITALIC, 12));
        userText.setBackground(backGroundColor);
        userText.setForeground(Color.WHITE);
        userText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                userText.setText("");
                userText.setFont(new Font("Calibri", Font.BOLD, 14));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userText.getText().equals("")) {
                    userText.setText("Enter your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 12));
                }
            }
        });
        userText.setBorder(new RoundBorder(10,Color.WHITE));
        userText.setBounds(50, 20, 200, 50);
        this.getContentPane().add(userText);
    }
    public void initPasswordPassField(){
        passwordField = new JPasswordField("Enter your Password here", 20);
        passwordField.setFont(new Font("Calibri", Font.ITALIC, 12));
        passwordField.setEchoChar('\0');

        Color backGroundColor = new Color(0,0,0,128);
        passwordField.setBackground(backGroundColor);
        passwordField.setForeground(Color.WHITE);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setText("");
                passwordField.setFont(new Font("Calibri", Font.BOLD, 14));
                passwordField.setEchoChar('*'); // set echo char to a dot or any other character you prefer
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().equals("")) {
                    passwordField.setText("Enter your Password here");
                    passwordField.setFont(new Font("Calibri", Font.ITALIC, 12));
                    passwordField.setEchoChar('\0');
                }
            }
        });
        passwordField.setBorder(new RoundBorder(10,Color.WHITE));
        passwordField.setBounds(50, 90, 200, 50);
        this.getContentPane().add(passwordField);
    }
    //The Button Initiation
    public void initButton() {
//        System.out.println("LoginFrame:loginButton is initializing...");
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
    //ActionListener, the method will call the method in Game Controller.
    public void actionPerformed(ActionEvent e) {
        if(frame.getGameController().onPlayerClickLoginButton(userText.getText(),passwordField.getText())){
            if (GameController.user2 == null) {
                this.setVisible(false);
                frame.getStartFrame().setVisible(true);
                new SuccessDialog("Login successful",frame.getStartFrame());

                //Initialize the UserText and PasswordField
                userText.setText("Enter your Username here");
                userText.setFont(new Font("Calibri", Font.ITALIC, 12));
                passwordField.setText("Enter your Password here");
                passwordField.setFont(new Font("Calibri", Font.ITALIC, 12));
                passwordField.setEchoChar('\0');
            }else {
                this.setVisible(false);
                frame.getChessGameFrame().setVisible(true);
                frame.getGameController().onPlayerSelectLocalPVPMode();
                new SuccessDialog("Login successful",frame.getChessGameFrame());

                //Initialize the UserText and PasswordField
                userText.setText("Enter your Username here");
                userText.setFont(new Font("Calibri", Font.ITALIC, 12));
                passwordField.setText("Enter your Password here");
                passwordField.setFont(new Font("Calibri", Font.ITALIC, 12));
                passwordField.setEchoChar('\0');
            }
        }else{
            new FailDialog("Incorrect username or password",this);
            //Initialize the UserText and PasswordField
            userText.setText("Enter your Username here");
            userText.setFont(new Font("Calibri", Font.ITALIC, 12));
            passwordField.setText("Enter your Password here");
            passwordField.setFont(new Font("Calibri", Font.ITALIC, 12));
            passwordField.setEchoChar('\0');
        }
    }

    @Override
    public int getState() {
        return state;
    }
    @Override
    public void setState(int state) {
        this.state = state;
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




