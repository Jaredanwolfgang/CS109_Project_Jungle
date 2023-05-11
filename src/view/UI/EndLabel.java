package view.UI;

import controller.GameController;
import model.Enum.GameMode;
import model.Enum.PlayerColor;
import model.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EndLabel extends JLabel {
    private JButton returnButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();
    private JButton resetButton = new JButton();
    private JLabel winInformation;
    private JLabel user1Information;
    private JLabel user2Information;
    private GameController gameController;
    private PlayerColor winPlayer;
    private JLabel background;

    public EndLabel(GameController gameController) {
        this.winPlayer = gameController.win();
        this.gameController = gameController;
        setBounds(0,0,500,729);
        setOpaque(false);
        setLayout(null);

        initWinLabel(this.winPlayer);
        initExitButton();
        initMusicButton();
        initReturnButton();
        initResetButton();
        initUser1Label(GameController.user1);
        if (GameController.getGameMode() == GameMode.Local_PVP) {
            initUser2Label(GameController.user2);
        }

        background = new JLabel(new ImageIcon("Image/ColorLabel.png"));
        background.setBounds(0,0,500,729);
//        background.setOpaque(false);
        add(background);
    }
    public void initMusicButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds(267,450,50,50);
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().playerClickMusicButton();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

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
        this.add(musicButton);
    }
    public void initExitButton() {
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds(350,450,50,50);
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds(183,450,50,50);
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().getGameController().onPlayerExitGameFrame();
                gameController.getView().playerClickReturnButton(gameController.getView().getChessGameFrame(),gameController.getView().getStartFrame());
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
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\GameFrame\\RestartButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        resetButton.setBorderPainted(false);
        resetButton.setContentAreaFilled(false);
        resetButton.setFocusPainted(false);
        resetButton.setOpaque(false);
        resetButton.setBounds(100,450, 50, 50);
        resetButton.setIcon(Button_Light_New);
        resetButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
            }
        });
        this.add(resetButton);
    }

    public void initWinLabel(PlayerColor color){
        winInformation= new JLabel("Win");
        winInformation.setBounds(100,100, 300, 200);
        winInformation.setFont(new Font("Britannic Bold", Font.BOLD, 150));
        winInformation.setForeground(color.getColor());

        winInformation.setHorizontalAlignment(SwingConstants.CENTER);
        winInformation.setVerticalAlignment(SwingConstants.CENTER);
        winInformation.repaint();
        this.add(winInformation);
    }
    public void initUser1Label(User user1){
        user1Information= new JLabel();
        user1Information.setBounds(100,250, 300, 100);
        user1Information.setFont(new Font("Britannic Bold", Font.BOLD, 14));
        user1Information.setForeground(Color.WHITE);
        user1Information.setText(String.format("Player:%s  Score:%.2f  Win-rate:%.2f",user1.getUsername(),user1.getScore(),user1.getWinRate()));

        user1Information.setHorizontalAlignment(SwingConstants.CENTER);
        user1Information.setVerticalAlignment(SwingConstants.CENTER);
        user1Information.repaint();
        this.add(user1Information);
    }
    public void initUser2Label(User user2){
        user2Information= new JLabel();
        user2Information.setBounds(100,350, 300, 100);
        user2Information.setFont(new Font("Britannic Bold", Font.BOLD, 14));
        user2Information.setForeground(Color.WHITE);
        user2Information.setText(String.format("Player:%s  Score:%.2f  Win-rate:%.2f ",user2.getUsername(),user2.getScore(),user2.getWinRate()));

        user2Information.setHorizontalAlignment(SwingConstants.CENTER);
        user2Information.setVerticalAlignment(SwingConstants.CENTER);
        user2Information.repaint();
        this.add(user2Information);
    }
}
