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
    private final int WIDTH = 500;
    private final int HEIGHT = 729;
    private final JButton localButton = new JButton();
    private final JButton netButton = new JButton();
    private final JButton aiButton = new JButton();
    private final JButton rankButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private User user;

    public StartFrame(User user) {
        this.user = user;

        initJFrame();
        initLabel("image\\InitFrame\\Jungle.png");
        initButton("image\\StartFrame\\LocalButton_Light.png", "image\\StartFrame\\LocalButton_Dark.png", localButton, 0, 100, 224, 145, 145);
        initButton("image\\StartFrame\\NetButton_Light.png", "image\\StartFrame\\NetButton_Dark.png", netButton, 1, 255, 224, 145, 145);
        initButton("image\\StartFrame\\AIButton_Light.png", "image\\StartFrame\\AIButton_Dark.png", aiButton, 2, 100, 380, 145, 145);
        initButton("image\\StartFrame\\RankButton_Light.png", "image\\StartFrame\\RankButton_Dark.png", rankButton, 3, 255, 380, 145, 145);
        initButton("image\\AllFrame\\MusicButton_Light.png", "image\\AllFrame\\MusicButton_Dark.png", musicButton, 4, 360, 20, 50, 50);
        initButton("image\\AllFrame\\ReturnButton_Light.png", "image\\AllFrame\\ReturnButton_Dark.png", returnButton, 5, 290, 20, 50, 50);
        initButton("image\\AllFrame\\ExitButton_Light.png", "image\\AllFrame\\ExitButton_Dark.png", exitButton, 6, 430, 20, 50, 50);
        initBackground("Background\\L_R_bg.gif");

        this.setVisible(true);
    }

    //初始化Frame
    //关闭模式是关掉就退出
    //不可改变大小
    public void initJFrame() {
        System.out.println("StartFrame is initializing...");
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
    public void initButton(String Address1, String Address2, JButton jb, int index, int x, int y, int width, int height) {
        System.out.println("StartFrame button" + jb + " is initializing...");
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
                /**
                 *  Please do not create new gameController here.
                 *  There is only one gameController in the whole game.
                 *  And any new gameController do not have any data of the chessboard.
                 */
                if (index == 0) {
                    ChessGameFrame mainFrame = new ChessGameFrame(Mode.PVP, user);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                } else if (index == 1) {
                    ChessGameFrame mainFrame = new ChessGameFrame( Mode.NET, user);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                } else if (index == 2) {
                    ChessGameFrame mainFrame = new ChessGameFrame(Mode.AI, user);
                    GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
                } else if (index == 3) {
                    new RankFrame();
                } else if (index == 4) {
                    new MusicPlayerFrame();
                } else if (index == 5) {
                    new InitFrame(800, 600);
                } else if (index == 6) {
                    System.exit(0);
                }
                dispose();
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
        System.out.println("StartFrame label is initializing...");
        ImageIcon icon = new ImageIcon(Address);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(256, 162, Image.SCALE_SMOOTH);
        ImageIcon newIcon = new ImageIcon(newImg);
        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(122, 60, newIcon.getIconWidth(), newIcon.getIconHeight());
        this.getContentPane().add(label);
    }
}
