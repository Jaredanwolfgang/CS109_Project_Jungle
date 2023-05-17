package view.Frame;

import view.Dialog.FailDialog;
import view.Dialog.SuccessDialog;
import view.UI.ChessClick;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class RegisterFrame extends JFrame implements ActionListener {
    private final Dimension screenSize = new Dimension(300,320);
/*    private final int WIDTH =300;
    private final int HEIGHT = 320;*/
    private JTextField userText;
    private JPasswordField passwordField,passwordCheckField;//这里要设计PassField，但是还没写
    private final JButton registerButton = new JButton();
    private Frame frame;

    public RegisterFrame(Frame frame) {
        this.frame = frame;
        initUsernameTextField();
        initPasswordPassField();
        initPasswordCheckPassField();

        initButton();
        initBackground("Background\\Spring.gif");
        initFrame();
        this.setVisible(false);
    }

    public void initFrame() {
        this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        this.setTitle("Jungle Register");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.getRegisterFrame().setVisible(false);
                frame.getInitFrame().setVisible(true);
            }
        });
    }

    public void initBackground(String Address) {
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, 300, 320);
        this.getContentPane().add(background);
    }

    public void initUsernameTextField() {
        Color backGroundColor = new Color(0, 0, 0, 128);

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
                    userText.setText("Create your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 12));
                }
            }
        });

        userText.setBorder(new RegisterFrame.RoundBorder(10, Color.WHITE));
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
    public void initPasswordCheckPassField(){
        passwordCheckField = new JPasswordField("Confirm your Password here", 20);
        passwordCheckField.setFont(new Font("Calibri", Font.ITALIC, 12));
        passwordCheckField.setEchoChar('\0');

        Color backGroundColor = new Color(0,0,0,128);
        passwordCheckField.setBackground(backGroundColor);
        passwordCheckField.setForeground(Color.WHITE);
        passwordCheckField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordCheckField.setText("");
                passwordCheckField.setFont(new Font("Calibri", Font.BOLD, 14));
                passwordCheckField.setEchoChar('*'); // set echo char to a dot or any other character you prefer
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordCheckField.getText().equals("")) {
                    passwordCheckField.setText("Enter your Password here");
                    passwordCheckField.setFont(new Font("Calibri", Font.ITALIC, 12));
                    passwordCheckField.setEchoChar('\0');
                }
            }
        });
        passwordCheckField.setBorder(new RoundBorder(10,Color.WHITE));
        passwordCheckField.setBounds(50, 160, 200, 50);
        this.getContentPane().add(passwordCheckField);
    }

    public void initButton() {
//        System.out.println("RegisterFrame: registerButton is initializing...");
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
        new ChessClick();
        if(!passwordField.getText().equals(passwordCheckField.getText())){
            new FailDialog("The passwords do not match.");
            return;
        }
        if(frame.getGameController().onPlayerClickRegisterButton(userText.getText(),passwordField.getText())){
            this.setVisible(false);
            frame.getLoginFrame().setVisible(true);
            new SuccessDialog("Successfully registered!");
        }else{
            new FailDialog("The username has been used.");
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
