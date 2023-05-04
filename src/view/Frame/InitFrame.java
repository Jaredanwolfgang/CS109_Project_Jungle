package view.Frame;

import listener.GameListener;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InitFrame extends JFrame{
    private final Dimension screenSize = new Dimension(800,600);
    private final JButton loginButton = new JButton();
    private final JButton registerButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private Frame frame;

    public InitFrame(Frame frame) {
        this.frame = frame;
        initJFrame();

        /** If the path to files never change, could we put it into the specific init method?
         * You might want to keep the constructor simple and clean.
         * like this:
         *   initLabel();
         *   initButton();
         *   initBackground();
         * As some of the initialization might be used in other methods.(Like a reset of chessboard)
         */
        initLabel();
        initLoginButton();
        initRegisterButton();
        initMusicButton();
        initExitButton();
        initBackground("Background\\Jungle.gif");

        this.setVisible(false);
    }

    //初始化Frame
    //关闭模式是关掉就退出
    //不可改变大小
    public void initJFrame() {
        System.out.println("InitFrame is initializing...");
        this.setLayout(null);
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.setTitle("Jungle");
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    //初始化背景，使用的是JLabel
    public void initBackground(String Address) {
        System.out.println("InitFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
        this.getContentPane().add(background);
    }

    //初始化Button，传入数据是
    //1. 明暗图片对应的位置
    //2. 按钮的位置
    //3. 按钮的目标大小（在方法当中有resize按钮的大小）
    public void initLoginButton() {
        System.out.println("Login button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Login_Light.png").getScaledInstance(160, 90, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Login_Dark.png").getScaledInstance(160, 90, Image.SCALE_SMOOTH));

        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);

        loginButton.setBounds(100, 430, 160, 90);
        loginButton.setIcon(Button_Light_New);
        loginButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickLoginButton();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(loginButton);
    }
    public void initRegisterButton() {
        System.out.println("Register button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Register_Light.png").getScaledInstance(160, 90, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Register_Dark.png").getScaledInstance(160, 90, Image.SCALE_SMOOTH));

        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setOpaque(false);

        registerButton.setBounds(540, 430, 160, 90);
        registerButton.setIcon(Button_Light_New);
        registerButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickRegisterButton();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setIcon(Button_Dark_New);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(registerButton);
    }
    public void initMusicButton() {
        System.out.println("Music button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds(650, 20, 50, 50);
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new MusicPlayerFrame();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                musicButton.setIcon(Button_Dark_New);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(musicButton);
    }
    public void initExitButton() {
        System.out.println("Exit button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds(710, 20, 50, 50);
        exitButton.setIcon(Button_Light_New);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                exitButton.setIcon(Button_Dark_New);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(exitButton);
    }

    //初始化Label，为游戏的标题
    public void initLabel() {
        System.out.println("InitFrame label is initializing...");

        /** To get the scaled image. */
        ImageIcon newIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Jungle.png").getScaledInstance(512, 324, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(150, 10, newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
