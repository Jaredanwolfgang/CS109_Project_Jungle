package view.UI;

import controller.GameController;
import model.Enum.AIDifficulty;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import view.Frame.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndLabel extends JLabel {
    public double initFrameResize;
    private JButton returnButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton resetButton = new JButton();
    private JLabel winInformation;
    private JLabel winnerInformation;
    private JLabel AIInformation;
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

        initWinnerLabel();
        if(GameController.gameMode == GameMode.PVE) {
            initAIInformation();
        }

        background = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/ColorLabel.png").getScaledInstance((int) (500 * initFrameResize), (int) (729 * initFrameResize), Image.SCALE_DEFAULT)));
        background.setBounds(0, 0, (int) (initFrameResize * 500), (int) (initFrameResize * 729));

        add(background);
    }

    public void initMusicButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds((int) (initFrameResize * 267), (int) (initFrameResize * 450), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().playerClickMusicButton();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds((int) (initFrameResize * 350), (int) (initFrameResize * 450), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        exitButton.setIcon(Button_Light_New);
        exitButton.addMouseListener(new MouseListener() {
            @Override public void mouseClicked(MouseEvent e) {
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
        this.add(exitButton);
    }
    public void initReturnButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds((int) (initFrameResize * 183), (int) (initFrameResize * 450), (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.onPlayerExitGameFrame();
                gameController.getView().playerClickReturnButton(gameController.getView().getChessGameFrame(), gameController.getView().getStartFrame());
                gameController.getView().getChessGameFrame().removeWinLabel();
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
        this.add(returnButton);
    }
    public void initResetButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance((int) (initFrameResize * 50), (int) (initFrameResize * 50), Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 450), (int) (initFrameResize * 50), (int) (initFrameResize * 50));

        if(GameController.gameMode == GameMode.Online_PVP_Server || GameController.gameMode == GameMode.Online_PVP_Client || GameController.gameMode == GameMode.Online_PVP_Spectator) {
            resetButton.setIcon(Button_Dark_New);
            resetButton.addMouseListener(new MouseListener() {
                @Override public void mouseClicked(MouseEvent e) {}
                @Override public void mousePressed(MouseEvent e) {}
                @Override public void mouseReleased(MouseEvent e) {}

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
        }
        else {
            resetButton.setIcon(Button_Light_New);
            resetButton.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    gameController.getView().getChessGameFrame().removeWinLabel();
                    gameController.onPlayerClickResetButton();
                }

                @Override public void mousePressed(MouseEvent e) {}

                @Override public void mouseReleased(MouseEvent e) {}

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

    public void initWinLabel(PlayerColor color) {
        winInformation = new JLabel("Win");
        winInformation.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 100), (int) (initFrameResize * 300), (int) (initFrameResize * 100));
        winInformation.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 150)));
        winInformation.setForeground(color.getColor());

        winInformation.setHorizontalAlignment(SwingConstants.CENTER);
        winInformation.setVerticalAlignment(SwingConstants.CENTER);
        winInformation.repaint();
        this.add(winInformation);
    }
    public void initWinnerLabel() {
        winnerInformation = new JLabel();
        winnerInformation.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 250), (int) (initFrameResize * 300), (int) (initFrameResize * 100));
        winnerInformation.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 14)));
        winnerInformation.setForeground(Color.WHITE);
        if(winPlayer == PlayerColor.BLUE){
            winnerInformation.setText(String.format("Blue Player %s wins the game in %d turns!", GameController.user1.getUsername(), GameController.turnCount - 1));
        }else{
            winnerInformation.setText(String.format("Red Player %s wins the game in %d turns!", GameController.user2.getUsername(), GameController.turnCount - 1));
        }
        winnerInformation.setHorizontalAlignment(SwingConstants.CENTER);
        winnerInformation.setVerticalAlignment(SwingConstants.CENTER);
        winnerInformation.repaint();
        this.add(winnerInformation);
    }
    public void initAIInformation() {
        AIInformation = new JLabel();
        AIInformation.setBounds((int) (initFrameResize * 100), (int) (initFrameResize * 350), (int) (initFrameResize * 300), (int) (initFrameResize * 100));
        AIInformation.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 14)));
        AIInformation.setForeground(Color.WHITE);
        if(GameController.aiDifficulty == AIDifficulty.EASY){
            AIInformation.setText(String.format("You beat the easy AI!(Score: %.2f Win rate: %.2f)", GameController.user2.getScore(), GameController.user2.getWinRate()));
        }else if (GameController.aiDifficulty == AIDifficulty.MEDIUM){
            AIInformation.setText(String.format("You beat the medium AI!(Score: %.2f Win rate: %.2f)", GameController.user2.getScore(), GameController.user2.getWinRate()));
        }else{
            AIInformation.setText(String.format("You beat the hard AI!(Score: %.2f Win rate: %.2f)", GameController.user2.getScore(), GameController.user2.getWinRate()));
        }
        AIInformation.setHorizontalAlignment(SwingConstants.CENTER);
        AIInformation.setVerticalAlignment(SwingConstants.CENTER);
        AIInformation.repaint();
        this.add(AIInformation);
    }
}
