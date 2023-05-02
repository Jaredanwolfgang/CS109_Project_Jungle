package view.Frame;

import controller.GameController;
import model.Enum.Mode;
import model.Enum.Seasons;
import model.User.User;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH = 500;
    private final int HEIGHT = 729;

    /**
     * I've written a GameMode enum class in my code.
     * You can easily get current game mode by accessing gameMode variable in the GameController class, as it is set to be static.
     */
    private final Mode mode;
    private JLabel background;

    /**
     * You can know which button can be used in which mode by reading the comments in gameListener class.
     * Here I summarized it for you:
     *   1. saveButton: all modes
     *   2. loadButton: local PVP only (should display additional warning information, clicking this button will cause the game to be reset, even if the file is invalid)
     *   3. regretButton: local PVP and PVE
     *   4. restartButton: local PVP and PVE
     *
     *   (There is a onPlayerExitGameFrame method, please call it when the player exits current game mode and returns to the game mode selection frame)
     */
    private final JButton backgroundButton = new JButton();
    private final JButton loadButton = new JButton();
    private final JButton saveButton = new JButton();
    private final JButton regretButton = new JButton();
    private final JButton restartButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private User user;
    private int season;
    private Seasons seasons = Seasons.SPRING;

    private final int ONE_CHESS_SIZE;

    private ChessboardComponent chessboardComponent;
    public ChessGameFrame(Mode mode,User user) {
        setTitle("Jungle Game"); //设置标题
        this.mode = mode;
        this.ONE_CHESS_SIZE = (HEIGHT * 3 / 5) / 9;
        this.user = user;

        initFrame();
        addChessboard();
        initButton("image\\GameFrame\\RestartButton_Light.png","image\\GameFrame\\RestartButton_Dark.png", restartButton, 0, 20, 609, 50, 50);
        initButton("image\\GameFrame\\RegretButton_Light.png","image\\GameFrame\\RegretButton_Dark.png", regretButton, 1, 90, 609, 50, 50);
        initButton("image\\GameFrame\\SaveButton_Light.png","image\\GameFrame\\SaveButton_Dark.png", saveButton, 2, 160, 609, 50, 50);
        initButton("image\\GameFrame\\LoadButton_Light.png","image\\GameFrame\\LoadButton_Dark.png", loadButton, 3, 230, 609, 50, 50);

        initButton("image\\GameFrame\\BackgroundButton_Light.png","image\\GameFrame\\BackgroundButton_Dark.png", backgroundButton, 4, 220, 20, 50, 50);
        initButton("image\\AllFrame\\MusicButton_Light.png","image\\AllFrame\\MusicButton_Dark.png", musicButton, 5, 360, 20, 50, 50);
        initButton("image\\AllFrame\\ReturnButton_Light.png","image\\AllFrame\\ReturnButton_Dark.png", returnButton, 6, 290, 20, 50, 50);
        initButton("image\\AllFrame\\ExitButton_Light.png", "image\\AllFrame\\ExitButton_Dark.png", exitButton, 7, 430, 20, 50, 50);

        initBackground("Background\\L_R_bg.gif");
        this.setVisible(true);
    }
    public void initFrame(){
        System.out.println("ChessGameFrame is initializing...");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setResizable(false);
    }
    public void initBackground(String Address){
        System.out.println("ChessGameFrame background is initializing...");
        this.season = 0;
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
    }
    public void changeBackground(String Address){
        this.getContentPane().remove(background);
        System.out.println("ChessGameFrame background is changing...");
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
        repaint();
    }
    //The links are not added.
    public void initButton(String Address1, String Address2, JButton jb, int index, int x, int y, int width, int height) {
        System.out.println("ChessGameFrame button" + jb + " is initializing...");
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

                } else if (index == 1) {

                } else if (index == 2) {

                } else if (index == 3) {

                } else if (index == 4) {
                    changeBackground("Background\\"+ seasons.getSeason((season)%4).getName());
                    System.out.println("Background is changing to "+seasons.getSeason((season)%4).getName());
                    season = (season+1)%4;
                    System.out.println(season);
                } else if (index == 5) {
                    new MusicPlayerFrame();
                } else if (index == 6) {
                    new StartFrame();
                    dispose();
                }else if(index == 7){
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
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(80, 130);
        add(chessboardComponent);
    }
    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }
    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

}
