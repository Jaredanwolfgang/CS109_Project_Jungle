package view.Frame;

import controller.GameController;
import model.ChessPieces.ChessPiece;
import model.Enum.PlayerColor;
import model.Enum.Seasons;
import model.User.User;
import view.ChessComponent.*;
import view.ChessboardComponent;
import view.UI.RoundLabel;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements ComponentListener {
    public Dimension screenSize = new Dimension(500, 729);
    private int WIDTH = (int) screenSize.getWidth();
    private int HEIGHT = (int) screenSize.getHeight();

    private JLabel background;
    private RoundLabel turnLabel;
    private int seasons;

    /**
     * You can know which button can be used in which mode by reading the comments in gameListener class.
     * Here I summarized it for you:
     * 1. saveButton: all modes
     * 2. loadButton: local PVP only (should display additional warning information, clicking this button will cause the game to be reset, even if the file is invalid)
     * 3. regretButton: local PVP and PVE
     * 4. restartButton: local PVP and PVE
     * (There is a onPlayerExitGameFrame method, please call it when the player exits current game mode and returns to the game mode selection frame)
     */

    private final JButton backgroundButton = new JButton();
    private final JButton loadButton = new JButton();
    private final JButton saveButton = new JButton();
    private final JButton undoButton = new JButton();
    private final JButton resetButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private final JButton playbackButton = new JButton();

    private Frame frame;
    private int ONE_CHESS_SIZE = 45;
    private int ONE_BUTTON_SIZE = 50;
    private String Address = "Background\\Spring.gif";

    private ChessboardComponent chessboardComponent;


    public ChessGameFrame(Frame frame) {
        this.frame = frame;
        setTitle("Jungle Game"); //设置标题

        initFrame();
        addChessboard();

        initLoadButton();
        initResetButton();
        initUndoButton();
        initExitButton();
        initReturnButton();
        initSaveButton();
        initMusicButton();
        initBackgroundButton();
        initPlayBackButton();

        initTurnLabel();
        initBackground();
        this.setVisible(false);
    }

    public void initFrame() {
//        System.out.println("ChessGameFrame is initializing...");
        setSize(500, 729);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setResizable(true);

        screenSize = this.getSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();

        addComponentListener(this);
    }

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation((WIDTH - 7 * ONE_CHESS_SIZE) / 2, (HEIGHT - 9 * ONE_CHESS_SIZE) / 2);
        add(chessboardComponent);
    }


    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        screenSize = this.getSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
    }

    //Init Methods
    public void initResetButton() {
//        System.out.println("ChessGameFrame button Reset Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds(HEIGHT / 50, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, 50, 50);
        resetButton.setIcon(Button_Light_New);
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getGameController().onPlayerClickResetButton();
                frame.resetChessBoardComponent();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                resetButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                resetButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(resetButton);
    }
    public void initUndoButton() {
//        System.out.println("ChessGameFrame button Undo Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        undoButton.setBorderPainted(false);
        undoButton.setContentAreaFilled(false);
        undoButton.setFocusPainted(false);
        undoButton.setOpaque(false);
        undoButton.setBounds(HEIGHT * 2 / 50 + ONE_CHESS_SIZE, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, 50, 50);
        undoButton.setIcon(Button_Light_New);
        undoButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getGameController().onPlayerClickUndoButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                undoButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                undoButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(undoButton);
    }
    public void initSaveButton() {
//        System.out.println("ChessGameFrame button Save Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setOpaque(false);
        saveButton.setBounds(HEIGHT * 3 / 50 + ONE_CHESS_SIZE * 2, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        saveButton.setIcon(Button_Light_New);
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickSaveButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(saveButton);
    }
    public void initLoadButton() {
//        System.out.println("ChessGameFrame button Load Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        loadButton.setBorderPainted(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setFocusPainted(false);
        loadButton.setOpaque(false);
        loadButton.setBounds(HEIGHT * 4 / 50 + ONE_CHESS_SIZE * 3, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        loadButton.setIcon(Button_Light_New);
        loadButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickLoadButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                loadButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loadButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(loadButton);
    }
    public void initBackgroundButton() {
//        System.out.println("ChessGameFrame button BackgroundButton is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\BackgroundButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\BackgroundButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        backgroundButton.setBorderPainted(false);
        backgroundButton.setContentAreaFilled(false);
        backgroundButton.setFocusPainted(false);
        backgroundButton.setOpaque(false);
        backgroundButton.setBounds(WIDTH - HEIGHT * 4 / 50 - ONE_BUTTON_SIZE * 4, HEIGHT / 50, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        backgroundButton.setIcon(Button_Light_New);
        backgroundButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeBackground();
                changeChessBoard();
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                backgroundButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backgroundButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(backgroundButton);
    }
    public void initMusicButton() {
//        System.out.println("ChessGameFrame button Music button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds(WIDTH - HEIGHT * 2 / 50 - ONE_BUTTON_SIZE * 2, HEIGHT / 50, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getMusicPlayerFrame().setVisible(true);
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
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(musicButton);
    }
    public void initExitButton() {
//        System.out.println("ChessGameFrame button Exit button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds(WIDTH - HEIGHT / 50 - ONE_BUTTON_SIZE, HEIGHT / 50, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        exitButton.setIcon(Button_Light_New);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

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
    public void initReturnButton() {
//        System.out.println("ChessGameFrame button Return button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds(WIDTH - HEIGHT * 3 / 50 - ONE_BUTTON_SIZE * 3, HEIGHT / 50, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getGameController().onPlayerExitGameFrame();
                frame.resetChessBoardComponent();
                frame.playerClickReturnButton(frame.getChessGameFrame(),frame.getStartFrame());
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
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
    public void initPlayBackButton() {
//        System.out.println("ChessGameFrame button Playback button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\PlaybackButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\PlaybackButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        playbackButton.setBorderPainted(false);
        playbackButton.setContentAreaFilled(false);
        playbackButton.setFocusPainted(false);
        playbackButton.setOpaque(false);

        playbackButton.setBounds(HEIGHT * 5 / 50 + ONE_CHESS_SIZE * 4, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        playbackButton.setIcon(Button_Light_New);
        playbackButton.addMouseListener(new MouseListener() {
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
                playbackButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playbackButton.setIcon(Button_Light_New);
            }
        });
        playbackButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getGameController().onPlayerClickPlayBackButton();
            }
        });
        this.getContentPane().add(playbackButton);
    }
    public void initBackground() {
        this.seasons = 0;
//        System.out.println("InitFrame background is initializing...");
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.getContentPane().add(background);
    }

    public void initTurnLabel(){
        turnLabel= new RoundLabel("1",new Color(78, 150, 253),ONE_BUTTON_SIZE/4);
        turnLabel.setBounds(10,20, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        turnLabel.setFont(new Font("TimesRoman", Font.BOLD, ONE_BUTTON_SIZE/2));
        turnLabel.setForeground(new Color(231,167,47));

        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setVerticalAlignment(SwingConstants.CENTER);
        turnLabel.repaint();
        this.getContentPane().add(turnLabel);
    }

    //Change Methods
    public void changeTurnLabel(int turnCount, PlayerColor playerColor){
        turnLabel.setText(String.valueOf(turnCount));
        if(playerColor.getColor()==Color.BLUE) {
            turnLabel.setBackgroundColor(new Color(78, 150, 253));
        } else {
            turnLabel.setBackgroundColor(new Color(218, 60, 45));
        }
    }
    public void changeBackground() {
        this.getContentPane().remove(background);
//        System.out.println("ChessGameFrame background is changing...");
        Address = "Background\\" + Seasons.values()[seasons].getName();
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
        repaint();
    }
    public void changeChessBoard(){
        getChessboardComponent().setSeason(Seasons.values()[seasons]);
        getChessboardComponent().refreshGridComponents();
        seasons = (seasons + 1) % 4;
    }
    //Getters
    public ChessComponent getChessComponent(ChessPiece chessPiece) {
        switch (chessPiece.getCategory()){
            case CAT -> {
                return new CatChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case DOG -> {
                return new DogChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case ELEPHANT -> {
                return new ElephantChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case LEOPARD -> {
                return new LeopardChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case LION -> {
                return new LionChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case TIGER -> {
                return new TigerChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case WOLF -> {
                return new WolfChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            case RAT -> {
                return new RatChessComponent(chessPiece.getOwner(), ONE_CHESS_SIZE);
            }
            default -> {
                return null;
            }
        }
    }
    //The following listener is not used.
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
}
