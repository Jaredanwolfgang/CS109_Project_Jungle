package view.UI;

import controller.GameController;
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
    private JLabel winInformation;
    private JLabel user1Information;
    private JLabel user2Information;
    private GameController gameController;

    public EndLabel(GameController gameController) {
        this.gameController = gameController;
        setBounds(0,0,500,729);
        setBackground(new Color(0,0,0,230));
        setLayout(null);

        /*initExitButton();
        initMusicButton();
        initReturnButton();*/
    }
   /* public void initMusicButton() {
//        System.out.println("ChessGameFrame button Music button is initializing...");

        *//** To get the scaled Image *//*
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds();
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().getMusicPlayerFrame().setVisible(true);
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
//        System.out.println("ChessGameFrame button Exit button is initializing...");

        *//** To get the scaled Image *//*
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds();
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
//        System.out.println("ChessGameFrame button Return button is initializing...");

        *//** To get the scaled Image *//*
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(50,50, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds();
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameController.getView().getGameController().onPlayerExitGameFrame();
                gameController.getView().resetChessBoardComponent();
                gameController.getView().playerClickReturnButton(gameController.getView().getChessGameFrame(),gameController.getView().getStartFrame());
                removeAll();
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
    }*/
    /*public void initWinLabel(PlayerColor color){
        winInformation= new JLabel("Win");
        winInformation.setBounds(10,20, 400, 200);
        winInformation.setFont(new Font("TimesRoman", Font.BOLD, ));
        winInformation.setForeground(color.getColor());

        winInformation.setHorizontalAlignment(SwingConstants.CENTER);
        winInformation.setVerticalAlignment(SwingConstants.CENTER);
        winInformation.repaint();
        this.add(winInformation);
    }*/
    public void initUser1Label(User user1){
        user1Information= new JLabel();
        user1Information.setBounds(10,20, 400, 200);
        user1Information.setFont(new Font("TimesRoman", Font.BOLD, 50));
        user1Information.setForeground(Color.WHITE);
        user1Information.setText("%s's Score: %2f Win rate:%2f ");
        user1Information.setHorizontalAlignment(SwingConstants.CENTER);
        user1Information.setVerticalAlignment(SwingConstants.CENTER);
        user1Information.repaint();
        this.add(user1Information);
    }
    public void initUser2Label(User user2){

    }
}
