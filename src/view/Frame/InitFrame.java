package view.Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class InitFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;
    private final JButton loginButton = new JButton();
    private final JButton registerButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();

    public InitFrame(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        initJFrame();

        /** If the path to files never change, could we put it into the specific init method?
         * You might want to keep the constructor simple and clean.
         * like this:
         *   initLabel();
         *   initButton();
         *   initBackground();
         * As some of the initialization might be used in other methods.(Like a reset of chessboard)
         */
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
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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

        /** Directly printing jb will display too much information, you might want to rewrite this line. */
        System.out.println("InitFrame button" + jb + " is initializing...");

        /*ImageIcon Button_Light = new ImageIcon(Address1);
        Image img = Button_Light.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Light_New = new ImageIcon(newimg);
        ImageIcon Button_Dark = new ImageIcon(Address2);
        Image img2 = Button_Dark.getImage();
        Image newimg2 = img2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Dark_New = new ImageIcon(newimg2);*/

        /** Here is a way to simplify the code above. */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address1).getScaledInstance(width, height, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address2).getScaledInstance(width, height, Image.SCALE_SMOOTH));

        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);

        jb.setBounds(x, y, width, height);
        jb.setIcon(Button_Light_New);
        jb.addMouseListener(new MouseListener() {

            /**
             *  You might want to reuse frames, instead of creating new ones every time.
             *  (About how to reuse frames, you can check my previous example code on QQ)
             *  Here is what chatGPT says:
             *    "Additionally, it is worth noting that creating a new frame every time a button is clicked might not always be the best approach,
             *    especially if you need to maintain state or pass data between frames.
             *    In those cases, it might be better to use a single frame and update its contents dynamically."
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if (index == 0) {
                    new LoginFrame();
                } else if (index == 1) {
                    new RegisterFrame();
                } else if (index == 2) {
                    new MusicPlayerFrame();
                } else if (index == 3) {
                    System.exit(0);
                }
            }

            /** You can make unused methods shorter like this. */
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}

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

        /*ImageIcon icon = new ImageIcon(Address);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(512, 324, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);*/

        /** Here is a way to simplify the code above. */
        ImageIcon newIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address).getScaledInstance(512, 324, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(150, 10, newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
