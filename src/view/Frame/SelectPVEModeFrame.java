package view.Frame;

import model.Enum.AIDifficulty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Draw Pictures First
public class SelectPVEModeFrame extends JFrame {
    private final Dimension screenSize = new Dimension(700,500);
    private Frame frame;
    private final JButton easyButton = new JButton();
    private final JButton mediumButton = new JButton();
    private final JButton hardButton = new JButton();

    public SelectPVEModeFrame(Frame frame) {
        this.frame = frame;
        initJFrame();

        initEasyButton();
        initMediumButton();
        initHardButton();

        initBackground("Background\\Jungle2.gif");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.getStartFrame().setVisible(true);
                frame.getSelectPVEModeFrame().setVisible(false);
            }
        });

        this.setVisible(false);
    }

    public void initJFrame() {
//        System.out.println("InitFrame is initializing...");
        this.setLayout(null);
        this.setSize((int) screenSize.getWidth(), (int) screenSize.getHeight());
        this.setTitle("Mode Selection");
        //this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
    }

    //初始化背景，使用的是JLabel
    public void initBackground(String Address) {
//        System.out.println("InitFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
        this.getContentPane().add(background);
    }
    public void initEasyButton() {
        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\EasyButton_Light.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\EasyButton_Dark.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        easyButton.setBorderPainted(false);
        easyButton.setContentAreaFilled(false);
        easyButton.setFocusPainted(false);
        easyButton.setOpaque(false);

        easyButton.setBounds(270, 150, 80, 80);
        easyButton.setIcon(Button_Light_New);
        easyButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                frame.getGameController().onPlayerSelectLocalPVEMode(AIDifficulty.EASY);
                frame.playerClickModesButton();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                easyButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                easyButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(easyButton);
    }
    public void initMediumButton() {
        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\MediumButton_Light.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\MediumButton_Dark.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        mediumButton.setBorderPainted(false);
        mediumButton.setContentAreaFilled(false);
        mediumButton.setFocusPainted(false);
        mediumButton.setOpaque(false);

        mediumButton.setBounds(400, 150, 80, 80);
        mediumButton.setIcon(Button_Light_New);
        mediumButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                frame.getGameController().onPlayerSelectLocalPVEMode(AIDifficulty.MEDIUM);
                frame.playerClickModesButton();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                mediumButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                mediumButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(mediumButton);
    }
    public void initHardButton() {
        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\HardButton_Light.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\HardButton_Dark.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        hardButton.setBorderPainted(false);
        hardButton.setContentAreaFilled(false);
        hardButton.setFocusPainted(false);
        hardButton.setOpaque(false);

        hardButton.setBounds(530, 150, 80, 80);
        hardButton.setIcon(Button_Light_New);
        hardButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                frame.getGameController().onPlayerSelectLocalPVEMode(AIDifficulty.HARD);
                frame.playerClickModesButton();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                hardButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hardButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(hardButton);
    }

}
