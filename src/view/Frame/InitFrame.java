package view.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Timer;

public class InitFrame extends JFrame{
    public static double initFrameResize = 1.0;
    private final Dimension screenSize = new Dimension(800,600);
    private final JButton loginButton = new JButton();
    private final JButton registerButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final Frame frame;

    public InitFrame(Frame frame) {
        this.frame = frame;
        initJFrame();

        initLabel();
        initLoginButton();
        initRegisterButton();
        initMusicButton();
        initExitButton();
        initBackground("Background\\Jungle.gif");

        this.setVisible(false);

        Timer resizeTimer = new Timer(150, e -> {
            Dimension newDimension =  frame.getInitFrame().getSize();
            if(newDimension.height != (int) (initFrameResize * screenSize.getHeight())){
                initFrameResize = (double) newDimension.height / screenSize.height;
            }else if(newDimension.width != (int) (initFrameResize * screenSize.getWidth())){
                initFrameResize = (double) newDimension.width / screenSize.width;
            }else{
                return;
            }
            if(initFrameResize > 1.5){
                initFrameResize = 1.5;
            }
            if(initFrameResize < 0.5){
                initFrameResize = 0.5;
            }
            frame.getInitFrame().getContentPane().removeAll();
            initLabel();
            initLoginButton();
            initRegisterButton();
            initMusicButton();
            initExitButton();
            initBackground("Background\\Jungle.gif");
            frame.getInitFrame().setSize((int) (initFrameResize * screenSize.getWidth()), (int) (initFrameResize * screenSize.getHeight()));
        });

        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTimer.restart();
            }
            @Override public void componentMoved(ComponentEvent e) {}
            @Override public void componentShown(ComponentEvent e) {}
            @Override public void componentHidden(ComponentEvent e) {}
        });
    }

    //初始化Frame
    //关闭模式是关掉就退出
    //不可改变大小
    public void initJFrame() {
//        System.out.println("InitFrame is initializing...");
        this.setLayout(null);
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.setTitle("Jungle");
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(true);
    }

    //初始化背景，使用的是JLabel
    public void initBackground(String Address) {
//        System.out.println("InitFrame background is initializing...");
        ImageIcon backgroundIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address).getScaledInstance((int) (initFrameResize * screenSize.getWidth()), (int) (initFrameResize * screenSize.getHeight()), Image.SCALE_DEFAULT));
        JLabel background = new JLabel(backgroundIcon);
        background.setBounds(0, 0, (int)(initFrameResize * screenSize.getWidth()), (int)(initFrameResize * screenSize.getHeight()));
        this.getContentPane().add(background);
    }

    //初始化Button，传入数据是
    //1. 明暗图片对应的位置
    //2. 按钮的位置
    //3. 按钮的目标大小（在方法当中有resize按钮的大小）
    public void initLoginButton() {
//        System.out.println("Login button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Login_Light.png").getScaledInstance((int) (initFrameResize * 160), (int) (initFrameResize * 90), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Login_Dark.png").getScaledInstance((int) (initFrameResize * 160), (int) (initFrameResize * 90), Image.SCALE_SMOOTH));

        loginButton.setBorderPainted(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setFocusPainted(false);
        loginButton.setOpaque(false);

        loginButton.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 430), (int) (initFrameResize * 160), (int) (initFrameResize * 90));
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
//        System.out.println("Register button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Register_Light.png").getScaledInstance((int) (initFrameResize * 160), (int) (initFrameResize * 90), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Register_Dark.png").getScaledInstance((int) (initFrameResize * 160), (int) (initFrameResize * 90), Image.SCALE_SMOOTH));

        registerButton.setBorderPainted(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setFocusPainted(false);
        registerButton.setOpaque(false);

        registerButton.setBounds((int) (initFrameResize * 540), (int) (initFrameResize * 430), (int) (initFrameResize * 160), (int) (initFrameResize * 90));
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
//        System.out.println("Music button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds((int) (initFrameResize * 650), (int) (initFrameResize * 20), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickMusicButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                musicButton.setIcon(Button_Dark_New);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                musicButton.setToolTipText("Music Adjustment");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(musicButton);
    }
    public void initExitButton() {
//        System.out.println("Exit button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds((int) (initFrameResize * 710), (int) (initFrameResize * 20), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                exitButton.setToolTipText("Exit the game");
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
//        System.out.println("InitFrame label is initializing...");

        /* To get the scaled image. */
        ImageIcon newIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Jungle.png").getScaledInstance((int) (initFrameResize * 512), (int) (initFrameResize * 324), Image.SCALE_SMOOTH));

        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds((int) (initFrameResize * 150), (int) (initFrameResize * 10), newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
