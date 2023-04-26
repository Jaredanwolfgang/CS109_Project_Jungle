package view.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InitFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private JButton loginButton = new JButton();
    private JButton registerButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();

    public InitFrame(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        initJFrame();

        initLabel("image\\InitFrame\\Jungle.png");
        initButton("image\\InitFrame\\Login_Light.png", "image\\InitFrame\\Login_Dark.png", loginButton, 0, 100, 430, 160, 90);
        initButton("image\\InitFrame\\Register_Light.png", "image\\InitFrame\\Register_Dark.png", registerButton, 1, 540, 430, 160, 90);
        initButton("image\\AllFrame\\MusicButton_Light.png", "image\\AllFrame\\MusicButton_Dark.png", musicButton, 2, 650, 20, 50, 50);
        initButton("image\\AllFrame\\ExitButton_Light.png", "image\\AllFrame\\ExitButton_Dark.png", exitButton, 3, 710, 20, 50, 50);
        initBackground("Background\\Jungle.gif");

        this.setVisible(true);
    }

    //初始化Frame
    //关闭模式是关掉就退出
    //不可改变大小
    public void initJFrame() {
        System.out.println("InitFrame is initializing...");
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Jungle");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    //初始化背景，使用的是JLabel
    public void initBackground(String Address) {
        System.out.println("InitFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
    }

    //初始化Button，传入数据是
    //1. 明暗图片对应的位置
    //2. 按钮的位置
    //3. 按钮的目标大小（在方法当中有resize按钮的大小）
    //4. 按钮的index，用于判断按钮的功能
    public void initButton(String Address1, String Address2, JButton jb, int index, int x, int y, int width, int height) {
        System.out.println("InitFrame button" + jb + " is initializing...");
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
                if (index == 0) {
                    new LoginFrame(300, 250);
                } else if (index == 1) {
                    new RegisterFrame();
                } else if (index == 2) {
                    new MusicPlayerFrame();
                } else if (index == 3) {
                    System.exit(0);
                }
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

        this.getContentPane().add(jb);
    }

    //初始化Label，为游戏的标题
    public void initLabel(String Address) {
        System.out.println("InitFrame label is initializing...");
        ImageIcon icon = new ImageIcon(Address);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(512, 324, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);
        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(150, 10, newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
