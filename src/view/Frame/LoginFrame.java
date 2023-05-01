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

    private final int WIDTH = 300;
    private final int HEIGHT = 250;

    /** I have to apologize here.
     *  As java can’t rewrite a specific line in the users file. I must rewrite the whole file.
     *  Therefore, I have to have all users in memory.
     *  I read all users at the beginning and create an ArrayList for it.
     *  And when any user's information changes, I update the Arraylist and write all users back to the file. All this is done in gameController.
     *  I have already created interfaces in gameListeners for user login, register, logout and get player list. Don't forget to check it out.
     */
    private User user = new User();
    private JTextField userText, passText;
    private JPasswordField passwordField;//这里要设置PassField，但是还没写
    private JButton loginButton = new JButton();

    public LoginFrame() {
        initTextArea();
        initButton("Image\\InitFrame\\Login_Light.png","Image\\InitFrame\\" +
                "Login_Dark.png",loginButton,0, 100, 150, 100, 56);
        initBackground("Background\\L_R_bg.gif");
        initFrame();
        this.setVisible(true);
    }

    public void initFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Jungle Login");
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
    //这里设置了两个TextField，用于输入数据
    public void initTextArea() {
        Color backGroundColor = new Color(0,0,0,128);

        /**
         * You might want to change the font size here. Current size is too small to read.
         */
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
                    userText.setText("Enter your Username here");
                    userText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    userText.setForeground(Color.WHITE);
                    userText.setBackground(backGroundColor);
                }
            }
        });
        userText.setBorder(new RoundBorder(10,Color.WHITE));
        userText.setBounds(50, 20, 200, 50);

        passText = new JTextField("Enter your Password here", 20);
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
                userText.setBackground(backGroundColor);
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passText.getText().equals("")) {
                    passText.setText("Enter your Password here");
                    passText.setFont(new Font("Calibri", Font.ITALIC, 8));
                    passText.setForeground(Color.WHITE);
                    userText.setBackground(backGroundColor);
                }
            }
        });
        passText.setBorder(new RoundBorder(10,Color.WHITE));
        passText.setBounds(50, 90, 200, 50);

        this.getContentPane().add(userText);
        this.getContentPane().add(passText);
    }
    //初始化按钮
    public void initButton(String Address1, String Address2, JButton jb, int index, int x, int y, int width, int height) {
        ImageIcon Button_Light = new ImageIcon(Address1);
        Image img = Button_Light.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);//重新调整图片大小
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
    //设置按钮的动作，触发之后会读取并比对本地文件中的用户名和密码
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
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
                        StartFrame startFrame = new StartFrame(user);
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




