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

/*
 * You can know which button can be used in which mode by reading the comments in gameListener class.
 * Here I summarized it for you:
 * 1. saveButton: all modes
 * 2. loadButton: local PVP only (should display additional warning information, clicking this button will cause the game to be reset, even if the file is invalid)
 * 3. regretButton: local PVP and PVE
 * 4. restartButton: local PVP and PVE
 * (There is a onPlayerExitGameFrame method, please call it when the player exits current game mode and returns to the game mode selection frame)
 */

public class ChessGameFrame extends JFrame {
    public static double initFrameResize = 1.0;
    public Dimension screenSize = new Dimension(500, 729);

    /*private int WIDTH = (int) (50 * initFrameResize);
    private int HEIGHT = (int) (screenSize.getHeight() * initFrameResize);
    private int ONE_CHESS_SIZE = (int) (50 * initFrameResize);
    private int ONE_BUTTON_SIZE = (int) (50 * initFrameResize);*/

    private final JLayeredPane layeredPane = new JLayeredPane();
    private JLabel background;
    private ColorBoard colorBoard;
    private JLabel blueStack = new JLabel();
    private JLabel redStack = new JLabel();;
    private JLabel timeLabel;
    private JLabel turnLabel;
    private int seasons;

    private JButton backgroundButton = new JButton();
    private JButton loadButton = new JButton();
    private JButton saveButton = new JButton();
    private JButton undoButton = new JButton();
    private JButton resetButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton returnButton = new JButton();
    private JButton playbackButton = new JButton();

    private final Frame frame;
    private String Address = "Background\\Spring.gif";
    private ChessboardComponent chessboardComponent;
    private EndLabel winLabel;

    public ChessGameFrame(Frame frame) {
        this.frame = frame;

        setTitle("Jungle Game"); //设置标题

        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, (int) (500 * initFrameResize), (int) (729 * initFrameResize));

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

        initBlueStackBoard();
        initRedStackBoard();

        initColorBoard();
        initBackground();

        add(layeredPane);
        setVisible(false);

        Timer resizeTimer = new Timer(150, e -> {
            Dimension newDimension = getSize();
            if(newDimension.equals(screenSize)){
                return;
            }
            if (newDimension.height != (int) (initFrameResize * screenSize.getHeight())) {
                initFrameResize = (double) newDimension.height / screenSize.height;
            } else if (newDimension.width != (int) (initFrameResize * screenSize.getWidth())) {
                initFrameResize = (double) newDimension.width / screenSize.width;
            } else {
                return;
            }

            if (initFrameResize > 1.5) {
                initFrameResize = 1.5;
            }
            if (initFrameResize < 0.5) {
                initFrameResize = 0.5;
            }

            layeredPane.removeAll();
            layeredPane.setBounds(0, 0, (int) (initFrameResize * screenSize.getWidth()), (int) (initFrameResize * screenSize.getHeight()));
            moveChessboard();

            initPlayBackButton();
            initLoadButton();
            initResetButton();
            initUndoButton();
            initSaveButton();
            initMusicButton();
            initBackgroundButton();
            initExitButton();
            initReturnButton();

            changeBlueStackBoard();
            changeRedStackBoard();

            initColorBoard();
            initBackground();

            if(frame.getGameController().win() != null){
                resizeWinLabel();
            }

            setSize((int) (initFrameResize * screenSize.getWidth()), (int) (initFrameResize * screenSize.getHeight()));
            add(layeredPane);
        });
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeTimer.restart();
            }
            @Override
            public void componentMoved(ComponentEvent e) {}
            @Override
            public void componentShown(ComponentEvent e) {}
            @Override
            public void componentHidden(ComponentEvent e) {}
        });
    }

    public void initFrame() {
//        System.out.println("ChessGameFrame is initializing...");
        this.setTitle("Jungle");
        this.setSize((int) (screenSize.getWidth()), (int) (screenSize.getHeight()));
        this.setLocationRelativeTo(null); // Center the window.
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        this.setLayout(null);
        this.setResizable(true);
    }

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent((int) (initFrameResize * 50));
        chessboardComponent.setLocation((int) (initFrameResize * 75), (int)(initFrameResize * 105));
        layeredPane.add(chessboardComponent, JLayeredPane.PALETTE_LAYER);
    }

    private void moveChessboard() {
        chessboardComponent.changeSize((int) (initFrameResize * 50));
        chessboardComponent.setLocation((int) (initFrameResize * 75), (int)(initFrameResize * 105));
        layeredPane.add(chessboardComponent, JLayeredPane.PALETTE_LAYER);
    }

    //Init Methods
    //These are the buttons below the chessboard.
    public void initResetButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds((int) (initFrameResize * 25), (int) (initFrameResize * 585), (int) (initFrameResize * 50), (int) (initFrameResize * 50));

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
                public void mouseExited(MouseEvent e) {}
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RegretButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        undoButton.setBorderPainted(false);
        undoButton.setContentAreaFilled(false);
        undoButton.setFocusPainted(false);
        undoButton.setOpaque(false);
        undoButton.setBounds((int) (95 * initFrameResize), (int) (585 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));

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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setOpaque(false);
        saveButton.setBounds((int) (165 * initFrameResize), (int) (585 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\LoadButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        loadButton.setBorderPainted(false);
        loadButton.setContentAreaFilled(false);
        loadButton.setFocusPainted(false);
        loadButton.setOpaque(false);
        loadButton.setBounds((int) (235 * initFrameResize), (int) (585 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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
    public void initPlayBackButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\PlaybackButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\PlaybackButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        playbackButton.setBorderPainted(false);
        playbackButton.setContentAreaFilled(false);
        playbackButton.setFocusPainted(false);
        playbackButton.setOpaque(false);

        playbackButton.setBounds((int) (305 * initFrameResize), (int) (585 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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

    //These are the buttons above the chessboard.
    public void initBackgroundButton() {
//        System.out.println("ChessGameFrame button BackgroundButton is initializing...");
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\BackgroundButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\BackgroundButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        backgroundButton.setBorderPainted(false);
        backgroundButton.setContentAreaFilled(false);
        backgroundButton.setFocusPainted(false);
        backgroundButton.setOpaque(false);
        backgroundButton.setBounds((int) (195 * initFrameResize), (int) (25 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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
    public void initReturnButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds((int) (265 * initFrameResize), (int) (25 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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
    public void initMusicButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds((int) (335 * initFrameResize), (int) (25 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds((int) (405 * initFrameResize), (int) (25 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
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

    //Initialize the background.
    public void initBackground() {
        this.seasons = 0;
        ImageIcon bg = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address).getScaledInstance((int) (500 * initFrameResize), (int) (729 * initFrameResize), Image.SCALE_DEFAULT));
        background = new JLabel(bg);
        background.setBounds(0, 0, (int) (500 * initFrameResize), (int) (729 * initFrameResize));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
    }

    //Initialize the board for turns and timers and boards for stacking eaten components.
    public void initColorBoard() {
        colorBoard = new ColorBoard(new Color(78, 150, 253), (int) (10 * initFrameResize));
        colorBoard.setBounds((int) (25 * initFrameResize), (int) (25 * initFrameResize), (int) (100 * initFrameResize), (int) (50 * initFrameResize));
        colorBoard.setLayout(new GridLayout(1, 2));

        turnLabel = new JLabel("1");
        turnLabel.setFont(new Font("Britannic Bold", Font.BOLD, (int) (25 * initFrameResize)));
        turnLabel.setForeground(new Color(231, 167, 47));
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        turnLabel.setVerticalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel("45");
        timeLabel.setFont(new Font("Britannic Bold", Font.BOLD, (int) (25 * initFrameResize)));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setVerticalAlignment(SwingConstants.CENTER);

        colorBoard.add(turnLabel);
        colorBoard.add(timeLabel);

        layeredPane.add(colorBoard, JLayeredPane.PALETTE_LAYER);
    }
    public void initBlueStackBoard(){
        layeredPane.remove(blueStack);
        blueStack = new JLabel();
        blueStack.setBackground(new Color(0,0,0,100));
        blueStack.setOpaque(true);
        blueStack.setLayout(new GridLayout(8,1));
        blueStack.setBounds((int) (initFrameResize * 25), (int)(initFrameResize * 105),(int) (initFrameResize * 40), (int)(initFrameResize * 320));
        layeredPane.add(blueStack,JLayeredPane.MODAL_LAYER);
        revalidate();
        repaint();
    }
    public void changeBlueStackBoard(){
        blueStack.setBounds((int) (initFrameResize * 25), (int)(initFrameResize * 105),(int) (initFrameResize * 40), (int)(initFrameResize * 320));
        layeredPane.add(blueStack,JLayeredPane.MODAL_LAYER);
    }
    public void addInBlueStack(ChessComponent eatenComponent){
        eatenComponent.setSelected(true);
        blueStack.add(eatenComponent);
        repaint();
    }
    public void initRedStackBoard(){
        layeredPane.remove(redStack);
        redStack = new JLabel();
        redStack.setBackground(new Color(0,0,0,100));
        redStack.setOpaque(true);
        redStack.setLayout(new GridLayout(8,1));
        redStack.setBounds((int) (initFrameResize * 435), (int)(initFrameResize * 235),(int) (initFrameResize * 40), (int)(initFrameResize * 320));
        layeredPane.add(redStack,JLayeredPane.MODAL_LAYER);
        revalidate();
        repaint();
    }
    public void changeRedStackBoard(){
        redStack.setBounds((int) (initFrameResize * 435), (int)(initFrameResize * 235),(int) (initFrameResize * 40), (int)(initFrameResize * 320));
        layeredPane.add(redStack,JLayeredPane.MODAL_LAYER);
    }
    public void addInRedStack(ChessComponent eatenComponent){
        eatenComponent.setSelected(true);
        redStack.add(eatenComponent);
        repaint();
    }

    //In different modes, the buttons will display differently.
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
    public void addWinLabel() {
        winLabel = new EndLabel(frame.getGameController());
        layeredPane.add(winLabel, JLayeredPane.POPUP_LAYER);
        /*for (int i = 0; i < layeredPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER).length; i++) {
            layeredPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER)[i].setEnabled(false);
        }*/
        repaint();
    }
    public void removeWinLabel() {
        /*for (int i = 0; i < layeredPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER).length; i++) {
            layeredPane.getComponentsInLayer(JLayeredPane.MODAL_LAYER)[i].setEnabled(true);
        }*/
        layeredPane.remove(winLabel);
        repaint();
    }

    //Only used in resizing: a combination of remove and add
    public void resizeWinLabel(){
        layeredPane.remove(winLabel);
        winLabel = new EndLabel(frame.getGameController());
        layeredPane.add(winLabel, JLayeredPane.POPUP_LAYER);
        repaint();
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
        Address = "Background\\" + Seasons.values()[seasons].getName();
        ImageIcon bg = new ImageIcon(Toolkit.getDefaultToolkit().getImage(Address).getScaledInstance((int) (500 * initFrameResize), (int) (729 * initFrameResize), Image.SCALE_DEFAULT));
        background = new JLabel(bg);
        background.setBounds(0, 0, (int) (screenSize.getWidth() * initFrameResize),(int) (screenSize.getHeight() * initFrameResize));
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        repaint();
    }

    public void changeChessBoard() {
        getChessboardComponent().setSeason(Seasons.values()[seasons]);
        getChessboardComponent().refreshGridComponents();
        seasons = (seasons + 1) % 4;
    }



    //Getters
    public ChessComponent getChessComponent(ChessPiece chessPiece) {
        switch (chessPiece.getCategory()) {
            case CAT -> {
                return new CatChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case DOG -> {
                return new DogChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case ELEPHANT -> {
                return new ElephantChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case LEOPARD -> {
                return new LeopardChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case LION -> {
                return new LionChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case TIGER -> {
                return new TigerChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case WOLF -> {
                return new WolfChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            case RAT -> {
                return new RatChessComponent(chessPiece.getOwner(), (int) (50 * initFrameResize));
            }
            default -> {
                return null;
            }
        }
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

}
