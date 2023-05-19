package view.Frame;

import model.Enum.AIDifficulty;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//Draw Pictures First
public class SelectOnlinePVPModeFrame extends JFrame {
    private final Dimension screenSize = new Dimension(700,500);
    private final Frame frame;
    private final JButton createButton = new JButton();
    private final JButton joinButton = new JButton();

    public SelectOnlinePVPModeFrame(Frame frame) {
        this.frame = frame;
        initJFrame();

        initCreateButton();
        initJoinButton();

        initBackground("Background\\Jungle2.gif");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.getStartFrame().setVisible(true);
                frame.getSelectOnlinePVPFrame().setVisible(false);
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
    public void initCreateButton() {
        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\EasyButton_Light.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\EasyButton_Dark.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        createButton.setBorderPainted(false);
        createButton.setContentAreaFilled(false);
        createButton.setFocusPainted(false);
        createButton.setOpaque(false);

        createButton.setBounds(270, 120, 80, 80);
        createButton.setIcon(Button_Light_New);
        createButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false);
                frame.getChessGameFrame().setVisible(true);
                frame.getGameController().onPlayerCreateServer();
                frame.getChessGameFrame().initFunctionalButtons();
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                createButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(createButton);
    }
    public void initJoinButton() {
        /* To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\MediumButton_Light.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image\\GameFrame\\MediumButton_Dark.png").getScaledInstance(80, 80, Image.SCALE_SMOOTH));

        joinButton.setBorderPainted(false);
        joinButton.setContentAreaFilled(false);
        joinButton.setFocusPainted(false);
        joinButton.setOpaque(false);

        joinButton.setBounds(400, 120, 80, 80);
        joinButton.setIcon(Button_Light_New);
        joinButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String ipAddress = JOptionPane.showInputDialog(frame.getSelectOnlinePVPFrame(), "Please input the IP address of the server you want to join:", "Input IP Address", JOptionPane.PLAIN_MESSAGE);
                frame.getGameController().onPlayerJoinServer(ipAddress);
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                joinButton.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                joinButton.setIcon(Button_Light_New);
            }
        });
        this.getContentPane().add(joinButton);
    }
}
