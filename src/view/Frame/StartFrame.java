package view.Frame;

import controller.GameController;
import model.ChessBoard.Chessboard;
import model.Enum.Mode;
import model.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class StartFrame extends JFrame {
    private final Dimension screenSize = new Dimension(500,729);
    private final JButton localButton = new JButton();
    private final JButton netButton = new JButton();
    private final JButton aiButton = new JButton();
    private final JButton rankButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private Frame frame;

    public StartFrame(Frame frame) {
        this.frame = frame;

        initJFrame();
        initLocalPVPButton();
        initNetPVPButton();
        initPVEButton();
        initRankButton();
        initMusicButton();
        initExitButton();
        initReturnButton();
        initBackground("Background\\L_R_bg.gif");

        this.setVisible(true);
    }

    //初始化Frame
    //关闭模式是关掉就退出
    //不可改变大小
    public void initJFrame() {
        System.out.println("StartFrame is initializing...");
        this.setLayout(null);
        this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        this.setTitle("Jungle");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }

    //初始化背景，使用的是JLabel
    public void initBackground(String Address) {
        System.out.println("StartFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
    }

    //初始化Button，传入数据是
    //1. 明暗图片对应的位置
    //2. 按钮的位置
    //3. 按钮的目标大小（在方法当中有resize按钮的大小）
    //4. 按钮的index，用于判断按钮的功能
    public void initLocalPVPButton() {
        System.out.println("Local PVP Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\LocalButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\LocalButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        localButton.setBorderPainted(false);
        localButton.setContentAreaFilled(false);
        localButton.setFocusPainted(false);
        localButton.setOpaque(false);

        localButton.setBounds(100, 224, 145, 145);
        localButton.setIcon(Button_Light_New);
        localButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickLocalPVPButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                localButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                localButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(localButton);
    }
    public void initNetPVPButton() {
        System.out.println("Net PVP Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\NetButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\NetButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        netButton.setBorderPainted(false);
        netButton.setContentAreaFilled(false);
        netButton.setFocusPainted(false);
        netButton.setOpaque(false);

        netButton.setBounds(255, 224, 145, 145);
        netButton.setIcon(Button_Light_New);
        netButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickNetPVPButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                netButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                netButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(netButton);
    }
    public void initPVEButton() {
        System.out.println("PVE Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\AIButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\AIButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        aiButton.setBorderPainted(false);
        aiButton.setContentAreaFilled(false);
        aiButton.setFocusPainted(false);
        aiButton.setOpaque(false);

        aiButton.setBounds(100, 380, 145, 145);
        aiButton.setIcon(Button_Light_New);
        aiButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickPVEButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                aiButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                aiButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(aiButton);
    }
    public void initRankButton(){
        System.out.println("Rank Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\RankButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\StartFrame\\RankButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        rankButton.setBorderPainted(false);
        rankButton.setContentAreaFilled(false);
        rankButton.setFocusPainted(false);
        rankButton.setOpaque(false);

        rankButton.setBounds(255, 380, 145, 145);
        rankButton.setIcon(Button_Light_New);
        rankButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickNetPVPButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                rankButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                rankButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(rankButton);
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

        musicButton.setBounds(360, 20, 50, 50);
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

        exitButton.setBounds(430, 20, 50, 50);
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
    public void initReturnButton(){
        System.out.println("Return button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds( 290, 20, 50, 50);
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickReturnButton();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setIcon(Button_Dark_New);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(returnButton);
    }


    //初始化Label，为游戏的标题
    public void initLabel() {
        System.out.println("StartFrame label is initializing...");
        ImageIcon newIcon = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\InitFrame\\Jungle.png").getScaledInstance(500, 729, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(122, 60, newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
