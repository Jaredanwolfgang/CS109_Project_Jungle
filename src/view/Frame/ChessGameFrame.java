package view.Frame;

import controller.GameController;
import model.ChessPieces.ChessPiece;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import model.Enum.Seasons;
import view.ChessComponent.*;
import view.ChessboardComponent;
import view.UI.EndLabel;
import view.UI.ColorBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements ComponentListener {
    public Dimension screenSize = new Dimension(500, 729);
    private int WIDTH = (int) screenSize.getWidth();
    private int HEIGHT = (int) screenSize.getHeight();
    private final JLayeredPane layeredPane = new JLayeredPane();
    private JLabel background;
    private ColorBoard colorBoard;
    private JLabel timeLabel;
    private JLabel turnLabel;
    private int seasons;

    /*
     * You can know which button can be used in which mode by reading the comments in gameListener class.
     * Here I summarized it for you:
     * 1. saveButton: all modes
     * 2. loadButton: local PVP only (should display additional warning information, clicking this button will cause the game to be reset, even if the file is invalid)
     * 3. regretButton: local PVP and PVE
     * 4. restartButton: local PVP and PVE
     * (There is a onPlayerExitGameFrame method, please call it when the player exits current game mode and returns to the game mode selection frame)
     */

    private final JButton backgroundButton = new JButton();
    private JButton loadButton = new JButton();
    private JButton saveButton = new JButton();
    private JButton undoButton = new JButton();
    private JButton resetButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private JButton playbackButton = new JButton();

    private final Frame frame;
    private final int ONE_CHESS_SIZE = 45;
    private final int ONE_BUTTON_SIZE = 50;
    private String Address = "Background\\Spring.gif";

    private ChessboardComponent chessboardComponent;
    private EndLabel winLabel;



    public ChessGameFrame(Frame frame) {
        this.frame = frame;
        setTitle("Jungle Game"); //设置标题

        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, 500, 729);

        initFrame();
        addChessboard();

        initPlayBackButton();
        initLoadButton();
        initResetButton();
        initUndoButton();
        initSaveButton();

        initMusicButton();
        initBackgroundButton();
        initExitButton();
        initReturnButton();

        initColorBoard();
        initBackground();

        this.add(layeredPane);
        this.setVisible(false);
    }

    public void initFrame() {
//        System.out.println("ChessGameFrame is initializing...");
        setSize(500, 729);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);
        setResizable(false);

        screenSize = this.getSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();

        addComponentListener(this);
    }

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation((WIDTH - 7 * ONE_CHESS_SIZE) / 2, (HEIGHT - 9 * ONE_CHESS_SIZE) / 2);
        layeredPane.add(chessboardComponent, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void componentResized(ComponentEvent e) {
        screenSize = this.getSize();
        WIDTH = (int) screenSize.getWidth();
        HEIGHT = (int) screenSize.getHeight();
    }

    //Init Methods
    public void initResetButton() {
        resetButton = new JButton();
//        System.out.println("ChessGameFrame button Reset Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds(HEIGHT / 50, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, 50, 50);

        if (GameController.gameMode == GameMode.Online_PVP_Server || GameController.gameMode == GameMode.Online_PVP_Client || GameController.gameMode == GameMode.Online_PVP_Spectator) {
            resetButton.setIcon(Button_Dark_New);
            resetButton.addMouseListener(new MouseListener() {
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    loadButton.setToolTipText("Unavailable in this game mode");
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        } else {
            resetButton.setIcon(Button_Light_New);
            resetButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    frame.getGameController().onPlayerClickResetButton();
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    resetButton.setToolTipText("Reset the chess game");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    resetButton.setIcon(Button_Light_New);
                }
            });
        }
        layeredPane.add(resetButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initUndoButton() {
        undoButton = new JButton();
//        System.out.println("ChessGameFrame button Undo Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        undoButton.setBorderPainted(false);
        undoButton.setContentAreaFilled(false);
        undoButton.setFocusPainted(false);
        undoButton.setOpaque(false);
        undoButton.setBounds(HEIGHT * 2 / 50 + ONE_CHESS_SIZE, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, 50, 50);

        if (GameController.gameMode == GameMode.Online_PVP_Server || GameController.gameMode == GameMode.Online_PVP_Client || GameController.gameMode == GameMode.Online_PVP_Spectator) {
            undoButton.setIcon(Button_Dark_New);
            undoButton.addMouseListener(new MouseListener() {
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    loadButton.setToolTipText("Unavailable in this game mode");
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        } else {
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    undoButton.setToolTipText("Undo move(s)");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    undoButton.setIcon(Button_Light_New);
                }
            });
        }
        layeredPane.add(undoButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initSaveButton() {
        saveButton = new JButton();
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                saveButton.setToolTipText("Save chess game to local .txt file");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(saveButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initLoadButton() {
        loadButton = new JButton();
//        System.out.println("ChessGameFrame button Load Button is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Light.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Dark.png").getScaledInstance(ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, Image.SCALE_SMOOTH));

        loadButton.setBorderPainted(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setFocusPainted(false);
        loadButton.setOpaque(false);
        loadButton.setBounds(HEIGHT * 4 / 50 + ONE_CHESS_SIZE * 3, HEIGHT - HEIGHT / 50 - 2 * ONE_BUTTON_SIZE, ONE_BUTTON_SIZE, ONE_BUTTON_SIZE);
        if (GameController.gameMode != GameMode.Local_PVP) {
            loadButton.setIcon(Button_Dark_New);
            loadButton.addMouseListener(new MouseListener() {
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    loadButton.setToolTipText("Unavailable in this game mode");
                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        } else {
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
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    loadButton.setToolTipText("Load a chess game from local .txt file");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    loadButton.setIcon(Button_Light_New);
                }
            });
        }
        layeredPane.add(loadButton, JLayeredPane.PALETTE_LAYER);
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                returnButton.setToolTipText("Change the background and the chessboard.");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backgroundButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(backgroundButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initMusicButton() {
//        System.out.println("ChessGameFrame button Music button is initializing...");

        /* To get the scaled Image */
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
                musicButton.setToolTipText("Music adjustment");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(musicButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initExitButton() {
//        System.out.println("ChessGameFrame button Exit button is initializing...");

        /* To get the scaled Image */
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                exitButton.setToolTipText("Exit the game");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exitButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(exitButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initReturnButton() {
//        System.out.println("ChessGameFrame button Return button is initializing...");

        /* To get the scaled Image */
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
                frame.playerClickReturnButton(frame.getChessGameFrame(), frame.getStartFrame());
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                returnButton.setToolTipText("Return to mode choosing frame");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(returnButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initPlayBackButton() {
//        System.out.println("ChessGameFrame button Playback button is initializing...");

        playbackButton = new JButton();
        /* To get the scaled Image */
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
                ToolTipManager.sharedInstance().setInitialDelay(0);
                playbackButton.setToolTipText("Auto playback the game");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                playbackButton.setIcon(Button_Light_New);
            }
        });
        playbackButton.addActionListener((actionEvent) -> frame.getGameController().onPlayerClickPlayBackButton());
        layeredPane.add(playbackButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initBackground() {
        this.seasons = 0;
//        System.out.println("InitFrame background is initializing...");
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    public void initColorBoard() {
        colorBoard = new ColorBoard(new Color(78, 150, 253), ONE_BUTTON_SIZE / 4);
        colorBoard.setBounds(10, 20, ONE_BUTTON_SIZE * 2, ONE_BUTTON_SIZE);
        colorBoard.setLayout(new GridLayout(1, 2));

        turnLabel = new JLabel("1");
        turnLabel.setFont(new Font("Britannic Bold", Font.BOLD, ONE_BUTTON_SIZE / 2));
        turnLabel.setForeground(new Color(231, 167, 47));
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setVerticalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel("45");
        timeLabel.setFont(new Font("Britannic Bold", Font.BOLD, ONE_BUTTON_SIZE / 2));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);

        colorBoard.add(turnLabel);
        colorBoard.add(timeLabel);

        layeredPane.add(colorBoard, JLayeredPane.PALETTE_LAYER);
    }

    public void addWinLabel() {
        winLabel = new EndLabel(frame.getGameController());
        layeredPane.add(winLabel, JLayeredPane.POPUP_LAYER);
        repaint();
    }

    public void initFunctionalButtons() {
        layeredPane.remove(playbackButton);
        layeredPane.remove(loadButton);
        layeredPane.remove(resetButton);
        layeredPane.remove(undoButton);
        layeredPane.remove(saveButton);
        initPlayBackButton();
        initLoadButton();
        initResetButton();
        initUndoButton();
        initSaveButton();
        layeredPane.revalidate();
        layeredPane.repaint();
    }

    //Change Methods
    public void changeTurnLabel(int turnCount, PlayerColor playerColor) {
        turnLabel.setText(String.valueOf(turnCount));

        if (playerColor.getColor() == Color.BLUE) {
            colorBoard.setBackgroundColor(new Color(78, 150, 253));
        } else {
            colorBoard.setBackgroundColor(new Color(218, 60, 45));
        }
    }

    public void changeTimeLabel(int remaining) {
        timeLabel.setText(String.valueOf(remaining));
    }

    public void changeBackground() {
        layeredPane.remove(background);
//        System.out.println("ChessGameFrame background is changing...");
        Address = "Background\\" + Seasons.values()[seasons].getName();
        background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        repaint();
    }

    public void changeChessBoard() {
        getChessboardComponent().setSeason(Seasons.values()[seasons]);
        getChessboardComponent().refreshGridComponents();
        seasons = (seasons + 1) % 4;
    }

    public void removeWinLabel() {
        layeredPane.remove(winLabel);
        repaint();
    }

    //Getters
    public ChessComponent getChessComponent(ChessPiece chessPiece) {
        switch (chessPiece.getCategory()) {
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

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    //The following listener is not used.
    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
