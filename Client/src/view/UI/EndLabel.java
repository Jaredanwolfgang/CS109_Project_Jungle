package view.UI;

import controller.GameController;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import model.Enum.PlayerType;
import model.User.User;
import view.Frame.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class EndLabel extends JLabel {
    public double initFrameResize;
    private JButton returnButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton resetButton = new JButton();
    private JButton saveButton = new JButton();

    private JLabel winInformation;
    private JLabel information;
    private JLabel winnerInformation;
    private JLabel userInformation;

    private GameController gameController;
    private PlayerColor winPlayer;
    private JLabel background;

    public EndLabel(GameController gameController) {
        this.initFrameResize = ChessGameFrame.gameFrameResize;
        this.winPlayer = gameController.win();
        this.gameController = gameController;

        setBounds(0, 0, (int) (500 * initFrameResize), (int) (729 * initFrameResize));
        setOpaque(false);
        setLayout(null);

        initWinLabel(this.winPlayer);
        initExitButton();
        initMusicButton();
        initReturnButton();
        initResetButton();
        initSaveButton();

        initInformationBoard();

        background = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/ColorLabel.png").getScaledInstance((int) (500 * initFrameResize), (int) (729 * initFrameResize), Image.SCALE_DEFAULT)));
        background.setBounds(0, 0, (int) (initFrameResize * 500), (int) (initFrameResize * 729));

        add(background);
    }

    public void initMusicButton() {
        musicButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds((int) (initFrameResize * 220), (int) (initFrameResize * 500), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().playerClickMusicButton();
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
                musicButton.setToolTipText("Music Player");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                musicButton.setIcon(Button_Light_New);
                musicButton.setToolTipText(null);
            }
        });
        this.add(musicButton);
    }

    public void initExitButton() {
        exitButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds((int) (initFrameResize * 280), (int) (initFrameResize * 500), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
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
        this.add(exitButton);
    }

    public void initReturnButton() {
        returnButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds((int) (initFrameResize * 160), (int) (initFrameResize * 500), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ChessGameFrame.enabled = true;
                gameController.onPlayerExitGameFrame();
                gameController.getView().playerClickReturnButton(gameController.getView().getChessGameFrame(), gameController.getView().getStartFrame());
                gameController.getView().getChessGameFrame().removeWinLabel();
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
        this.add(returnButton);
    }

    public void initResetButton() {
        resetButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 500), (int) (initFrameResize * 50), (int) (initFrameResize * 50));

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
                    resetButton.setToolTipText("Unavailable in current game mode");
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    resetButton.setToolTipText(null);
                }
            });
        } else {
            resetButton.setIcon(Button_Light_New);
            resetButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    ChessGameFrame.enabled = true;
                    gameController.getView().getChessGameFrame().removeWinLabel();
                    gameController.onPlayerClickResetButton();
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
                    resetButton.setToolTipText(null);
                }
            });
        }
        this.add(resetButton);
    }

    public void initSaveButton() {
        saveButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Light.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\SaveButton_Dark.png").getScaledInstance((int) (50 * initFrameResize), (int) (50 * initFrameResize), Image.SCALE_SMOOTH));

        saveButton.setBorderPainted(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setFocusPainted(false);
        saveButton.setOpaque(false);
        saveButton.setBounds((int) (340 * initFrameResize), (int) (500 * initFrameResize), (int) (50 * initFrameResize), (int) (50 * initFrameResize));
        saveButton.setIcon(Button_Light_New);
        saveButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().playerClickSaveButton();
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
                saveButton.setToolTipText(null);
            }
        });
        this.add(saveButton, JLayeredPane.PALETTE_LAYER);
    }

    public void initWinLabel(PlayerColor color) {
        if (winPlayer == gameController.getColorOfUser()) {
            JLabel winAnimation = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/win.gif").getScaledInstance((int) (100 * initFrameResize), (int) (100 * initFrameResize), Image.SCALE_DEFAULT)));
            winAnimation.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 130), (int) (initFrameResize * 100), (int) (initFrameResize * 100));
            this.add(winAnimation);
        }

        winInformation = new JLabel("Win");
        winInformation.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 100), (int) (initFrameResize * 300), (int) (initFrameResize * 100));
        winInformation.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 150)));
        winInformation.setForeground(color.getColor());

        winInformation.setHorizontalAlignment(SwingConstants.CENTER);
        winInformation.setVerticalAlignment(SwingConstants.CENTER);
        winInformation.repaint();
        this.add(winInformation);
    }

    public void initInformationBoard() {
        information = new JLabel();
        information.setLayout(new GridLayout(4, 1));
        information.setBounds((int) (initFrameResize * 50), (int) (initFrameResize * 250), (int) (initFrameResize * 400), (int) (initFrameResize * 200));
        initWinnerInformation();
        HeadLabel headLabel = new HeadLabel();
        information.add(headLabel);
        initUserInformation();

        this.add(information);
    }

    public void initWinnerInformation() {
        winnerInformation = new WinLabel(winPlayer, initFrameResize);
        winnerInformation.setBounds(0, 0, (int) (initFrameResize * 400), (int) (initFrameResize * 100));
        information.add(winnerInformation);
    }

    public void initUserInformation() {
        ArrayList<User> users = gameController.onPlayerClickRankListButtonByScore();

        if (GameController.gameMode == GameMode.PVE) {
            int rank = 1;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPlayerType() != PlayerType.AI) {
                    if (GameController.user1.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user1, rank, true, true, initFrameResize);
                        information.add(userInformation);
                        break;
                    }
                    rank++;
                }
            }
        } else if (GameController.gameMode == GameMode.Local_PVP) {
            int rank = 1;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPlayerType() != PlayerType.AI) {
                    if (GameController.user1.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user1, rank, true, true, initFrameResize);
                        information.add(userInformation);
                    }
                    if (GameController.user2.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user2, rank, false, true, initFrameResize);
                        information.add(userInformation);
                    }
                    rank++;
                }
            }
        }else if (GameController.gameMode == GameMode.Online_PVP_Spectator) {
            int rank = 1;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPlayerType() != PlayerType.AI) {
                    if (GameController.user1.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user1, rank, false, true, initFrameResize);
                        information.add(userInformation);
                    }
                    if (GameController.user2.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user2, rank, false, true, initFrameResize);
                        information.add(userInformation);
                    }
                    rank++;
                }
            }
        } else if (GameController.gameMode == GameMode.Online_PVP_Server) {
            int rank = 1;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPlayerType() != PlayerType.AI) {
                    if (GameController.user1.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user1, rank, true, true, initFrameResize);
                        information.add(userInformation);
                    }
                    if (GameController.user2.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user2, rank, false, true, initFrameResize);
                        information.add(userInformation);
                    }
                    rank++;
                }
            }
        }else if (GameController.gameMode == GameMode.Online_PVP_Client) {
            int rank = 1;
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getPlayerType() != PlayerType.AI) {
                    if (GameController.user1.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user1, rank, false, true, initFrameResize);
                        information.add(userInformation);
                    }
                    if (GameController.user2.getUsername().equals(users.get(i).getUsername())) {
                        userInformation = new RankLabel(GameController.user2, rank, true, true, initFrameResize);
                        information.add(userInformation);
                    }
                    rank++;
                }
            }
        }
        userInformation.setBounds(0, 0, (int) (initFrameResize * 400), (int) (initFrameResize * 100));
    }
}
