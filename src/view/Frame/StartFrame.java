package view.Frame;

import view.UI.ChessClick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.Toolkit.getDefaultToolkit;

public class StartFrame extends JFrame {
    private final Dimension screenSize = new Dimension(500,729);
    private final JButton localButton = new JButton();
    private final JButton netButton = new JButton();
    private final JButton aiButton = new JButton();
    private final JButton rankButton = new JButton();
    private final JButton musicButton = new JButton();
    private final JButton exitButton = new JButton();
    private final JButton returnButton = new JButton();
    private final JButton ruleButton = new JButton();
    private JLayeredPane layeredPane = new JLayeredPane();
    private JLabel rulesLabel;
    private final Frame frame;
    public static boolean enabled = true;

    public StartFrame(Frame frame) {
        this.frame = frame;

        initJFrame();
        initLocalPVPButton();
        initNetPVPButton();
        initPVEButton();
        initRankButton();
        initMusicButton();
        initExitButton();
        initReturnButton();
        initRuleButton();
        initRulesLabel();
        initLabel();
        initBackground();

        this.add(layeredPane);
        this.setVisible(false);
    }

    //Initialize the Frames
    public void initJFrame() {
//        System.out.println("StartFrame is initializing...");
        this.setLayout(null);
        this.setSize((int)screenSize.getWidth(), (int)screenSize.getHeight());
        this.setTitle("Jungle");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        layeredPane.setBounds(0,0,500,729);
        layeredPane.setLayout(null);
    }

    //Initialize the background image
    public void initBackground() {
//        System.out.println("StartFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon("Background\\Spring.gif"));
        background.setBounds(0, 0, (int)screenSize.getWidth(), (int)screenSize.getHeight());
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);
    }
    //Initialize the Label(Title)
    public void initLabel() {
//        System.out.println("StartFrame label is initializing...");
        ImageIcon newIcon = new ImageIcon(getDefaultToolkit().getImage("image\\InitFrame\\Jungle.png").getScaledInstance(200, 150, Image.SCALE_SMOOTH));

        JLabel label = new JLabel(newIcon);
        label.setPreferredSize(new Dimension(newIcon.getIconWidth(), newIcon.getIconHeight()));
        label.setBounds(150, 60, newIcon.getIconWidth(), newIcon.getIconHeight());
        layeredPane.add(label,JLayeredPane.DEFAULT_LAYER);
    }

    //Initialize the Buttons
    public void initLocalPVPButton() {
//        System.out.println("Local PVP Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\LocalButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\LocalButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        localButton.setBorderPainted(false);
        localButton.setContentAreaFilled(false);
        localButton.setFocusPainted(false);
        localButton.setOpaque(false);

        localButton.setBounds(100, 224, 145, 145);
        localButton.setIcon(Button_Light_New);
        localButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.playerClickLocalPVPButton();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    localButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    localButton.setToolTipText("Local PVP mode");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    localButton.setIcon(Button_Light_New);
                    localButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(localButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initNetPVPButton() {
//        System.out.println("Net PVP Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\NetButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\NetButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        netButton.setBorderPainted(false);
        netButton.setContentAreaFilled(false);
        netButton.setFocusPainted(false);
        netButton.setOpaque(false);

        netButton.setBounds(255, 224, 145, 145);
        netButton.setIcon(Button_Light_New);
        netButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.getGameController().onPlayerSelectOnlinePVPMode();
                    frame.getChessGameFrame().initFunctionalButtons();
                    frame.playerClickNetPVPButton();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    netButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    netButton.setToolTipText("Online PVP mode");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    netButton.setIcon(Button_Light_New);
                    netButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(netButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initMusicButton() {
//        System.out.println("Music button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds(360, 20, 50, 50);
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.playerClickMusicButton();
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    musicButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    musicButton.setToolTipText("Music Player");
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    musicButton.setIcon(Button_Light_New);
                    musicButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(musicButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initExitButton() {
//        System.out.println("Exit button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds(430, 20, 50, 50);
        exitButton.setIcon(Button_Light_New);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    System.exit(0);
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    exitButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    exitButton.setToolTipText("Exit the game");
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    exitButton.setIcon(Button_Light_New);
                    exitButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(exitButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initReturnButton(){
//        System.out.println("Return button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds( 290, 20, 50, 50);
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.playerClickReturnButton(frame.getStartFrame(),frame.getInitFrame());
                    frame.getGameController().onPlayerClickLogoutButton();
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    returnButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    returnButton.setToolTipText("Return to the beginning frame");
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    returnButton.setIcon(Button_Light_New);
                    returnButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(returnButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initPVEButton() {
//        System.out.println("PVE Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\AIButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\AIButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        aiButton.setBorderPainted(false);
        aiButton.setContentAreaFilled(false);
        aiButton.setFocusPainted(false);
        aiButton.setOpaque(false);

        aiButton.setBounds(100, 380, 145, 145);
        aiButton.setIcon(Button_Light_New);
        aiButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.playerClickPVEButton();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    aiButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    aiButton.setToolTipText("AI mode");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    aiButton.setIcon(Button_Light_New);
                    aiButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(aiButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initRankButton(){
//        System.out.println("Rank Button is initializing...");

        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\RankButton_Light.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("image\\StartFrame\\RankButton_Dark.png").getScaledInstance(145, 145, Image.SCALE_SMOOTH));

        rankButton.setBorderPainted(false);
        rankButton.setContentAreaFilled(false);
        rankButton.setFocusPainted(false);
        rankButton.setOpaque(false);

        rankButton.setBounds(255, 380, 145, 145);
        rankButton.setIcon(Button_Light_New);
        rankButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    frame.playerClickRankButton();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    rankButton.setIcon(Button_Dark_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    rankButton.setToolTipText("Rank");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    rankButton.setIcon(Button_Light_New);
                    rankButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(rankButton,JLayeredPane.PALETTE_LAYER);
    }
    public void initRuleButton() {
//        System.out.println("Music button is initializing...");

        /* To get the scaled Image */
        ImageIcon Button_Small_New = new ImageIcon(getDefaultToolkit().getImage("Image/StartFrame/RulesButton_Small.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        ImageIcon Button_Big_New = new ImageIcon(getDefaultToolkit().getImage("Image/StartFrame/RulesButton_Big.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH));

        ruleButton.setBorderPainted(false);
        ruleButton.setContentAreaFilled(false);
        ruleButton.setFocusPainted(false);
        ruleButton.setOpaque(false);

        ruleButton.setBounds(20, 20, 40, 40);
        ruleButton.setIcon(Button_Small_New);
        ruleButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (enabled) {
                    new ChessClick();
                    addRulesLabel();
                }
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                if (enabled) {
                    ruleButton.setIcon(Button_Big_New);
                    ToolTipManager.sharedInstance().setInitialDelay(0);
                    ruleButton.setToolTipText("See the rules");
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (enabled) {
                    ruleButton.setIcon(Button_Small_New);
                    ruleButton.setToolTipText(null);
                }
            }
        });
        layeredPane.add(ruleButton,JLayeredPane.PALETTE_LAYER);
    }

    public void initRulesLabel(){
        rulesLabel= new JLabel(new ImageIcon("Image/StartFrame/Rules.png"));
        rulesLabel.setBounds(0, 0, 500, 729);

        JButton closeButton = new JButton();
        ImageIcon Button_Light_New = new ImageIcon(getDefaultToolkit().getImage("Image/StartFrame/CloseButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(getDefaultToolkit().getImage("Image/StartFrame/CloseButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        closeButton.setBorderPainted(false);
        closeButton.setContentAreaFilled(false);
        closeButton.setFocusPainted(false);
        closeButton.setOpaque(false);

        closeButton.setBounds(400, 50, 50, 50);
        closeButton.setIcon(Button_Light_New);
        closeButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                new ChessClick();
                removeRulesLabel();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setIcon(Button_Dark_New);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                closeButton.setToolTipText("Return to Start Frame");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setIcon(Button_Light_New);
                closeButton.setToolTipText(null);
            }
        });
        rulesLabel.add(closeButton);
    }
    public void addRulesLabel(){
        enabled = false;
        ruleButton.setIcon(new ImageIcon(getDefaultToolkit().getImage("Image/StartFrame/RulesButton_Small.png").getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        layeredPane.add(rulesLabel,JLayeredPane.POPUP_LAYER);
    }
    public void removeRulesLabel(){
        enabled = true;
        layeredPane.remove(rulesLabel);
    }
}
