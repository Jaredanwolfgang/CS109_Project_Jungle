package view.Frame;

import controller.GameController;
import model.Enum.PlayerType;
import model.User.User;
import view.UI.HeadLabel;
import view.UI.RankLabel;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.lang.Object;

import static java.awt.BorderLayout.*;

public class RankFrame extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 729;
    private ArrayList<User> users = new ArrayList<>();
    private JPanel rankPanel = new JPanel();
    private JLabel backgroundLabel = new JLabel(new ImageIcon("Image/ColorLabel.png"));
    private JTable rankTable;
    private JLabel title = new JLabel("Rank", JLabel.CENTER);
    private DefaultTableModel tableModel;
    private Frame frame;
    private boolean sortByScore = true;
    private JButton sortByScoreButton = new JButton();
    private JButton sortByWinrateButton = new JButton();
    private JButton returnButton = new JButton();
    private JButton musicButton = new JButton();
    private JButton exitButton = new JButton();
    private JLayeredPane layeredPane = new JLayeredPane();

    public RankFrame(Frame frame) {
        this.frame = frame;
        this.users = frame.getGameController().onPlayerClickRankListButtonByScore();

        initFrame();
        initTitle();
        initSortByScoreButton();
        initSortByWinRateButton();
        initExitButton();
        initMusicButton();
        initReturnButton();
        initRankPanel();
        initBackgroundLabel();
        initBackground("Background/Spring.gif");

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                frame.playerClickReturnButton(frame.getRankFrame(),frame.getStartFrame());
                dispose();
            }
        });

        this.add(layeredPane);
    }

    public void initFrame() {
        //System.out.println("RankFrame is initializing...");
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Rank");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        layeredPane.setBounds(0,0,WIDTH,HEIGHT);
    }

    public void initBackground(String Address) {
        //System.out.println("RankFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        layeredPane.add(background,JLayeredPane.DEFAULT_LAYER);
    }

    public void initRankPanel() {
        rankPanel.removeAll();
        rankPanel.setLayout(new GridLayout(10, 1));
        rankPanel.setBounds(50, 150, 400, 500);
        rankPanel.setOpaque(false);
        rankPanel.add(new HeadLabel());
        int rank = 1;
        for (int i = 0; i < Math.min(users.size(), 19); i++) {
            if (users.get(i).getPlayerType()!= PlayerType.AI) {
                boolean isCurrentUser = false;
                boolean sortByScore = false;

                if (users.get(i).getUsername().equals(frame.getGameController().user1.getUsername())) {
                    isCurrentUser = true;
                }
                if(this.sortByScore){
                    sortByScore = true;
                }
                RankLabel rankLabel = new RankLabel(users.get(i),rank,isCurrentUser,sortByScore);
                rankPanel.add(rankLabel);
                rank++;
            }
        }
        layeredPane.add(rankPanel,JLayeredPane.MODAL_LAYER);
    }

    public void initBackgroundLabel() {
        backgroundLabel.setBounds(0, 0, 500, 729);
        backgroundLabel.setOpaque(false);
        layeredPane.add(backgroundLabel,JLayeredPane.PALETTE_LAYER);
    }

    public void initTitle(){
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Britannic Bold", Font.BOLD, 50));
        title.setBounds(20, 50, 460, 100);
        layeredPane.add(title,JLayeredPane.MODAL_LAYER);
    }
    public void initSortByScoreButton(){
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/RankFrame/Score_Light.png").getScaledInstance(80, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/RankFrame/Score_Dark.png").getScaledInstance(80, 50, Image.SCALE_SMOOTH));

        sortByScoreButton.setBorderPainted(false);
        sortByScoreButton.setContentAreaFilled(false);
        sortByScoreButton.setFocusPainted(false);
        sortByScoreButton.setOpaque(false);

        sortByScoreButton.setBounds(10, 10, 80, 50);
        sortByScoreButton.setIcon(Button_Light_New);
        sortByScoreButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!sortByScore) {
                    sortByScore = true;
                    users = frame.getGameController().onPlayerClickRankListButtonByScore();
                    layeredPane.remove(rankPanel);
                    rankPanel.removeAll();
                    initRankPanel();
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                sortByScoreButton.setIcon(Button_Dark_New);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                returnButton.setToolTipText("Sort by Scores");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sortByScoreButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(sortByScoreButton,JLayeredPane.MODAL_LAYER);
    }
    public void initSortByWinRateButton(){
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/RankFrame/WinRate_Light.png").getScaledInstance(80, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Image/RankFrame/WinRate_Dark.png").getScaledInstance(80, 50, Image.SCALE_SMOOTH));

        sortByWinrateButton.setBorderPainted(false);
        sortByWinrateButton.setContentAreaFilled(false);
        sortByWinrateButton.setFocusPainted(false);
        sortByWinrateButton.setOpaque(false);

        sortByWinrateButton.setBounds(110, 10, 80, 50);
        sortByWinrateButton.setIcon(Button_Light_New);
        sortByWinrateButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (sortByScore) {
                    sortByScore = false;
                    users = frame.getGameController().onPlayerClickRankListButtonByWinRate();
                    layeredPane.remove(rankPanel);
                    initRankPanel();
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                sortByWinrateButton.setIcon(Button_Dark_New);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                returnButton.setToolTipText("Sort by Win rate");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                sortByWinrateButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(sortByWinrateButton,JLayeredPane.MODAL_LAYER);
    }
    public void initMusicButton() {
//        System.out.println("Music button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\MusicButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        musicButton.setBorderPainted(false);
        musicButton.setContentAreaFilled(false);
        musicButton.setFocusPainted(false);
        musicButton.setOpaque(false);

        musicButton.setBounds(360, 10, 50, 50);
        musicButton.setIcon(Button_Light_New);
        musicButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickMusicButton();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
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
        layeredPane.add(musicButton,JLayeredPane.MODAL_LAYER);
    }
    public void initExitButton() {
//        System.out.println("Exit button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ExitButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        exitButton.setBorderPainted(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setFocusPainted(false);
        exitButton.setOpaque(false);

        exitButton.setBounds(430, 10, 50, 50);
        exitButton.setIcon(Button_Light_New);
        exitButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
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
        layeredPane.add(exitButton,JLayeredPane.MODAL_LAYER);
    }
    public void initReturnButton(){
//        System.out.println("Return button is initializing...");

        /** To get the scaled Image */
        ImageIcon Button_Light_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Light.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        ImageIcon Button_Dark_New = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image\\AllFrame\\ReturnButton_Dark.png").getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        returnButton.setBorderPainted(false);
        returnButton.setContentAreaFilled(false);
        returnButton.setFocusPainted(false);
        returnButton.setOpaque(false);

        returnButton.setBounds( 290, 10, 50, 50);
        returnButton.setIcon(Button_Light_New);
        returnButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.playerClickReturnButton(frame.getRankFrame(),frame.getStartFrame());
                dispose();
            }
            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override
            public void mouseEntered(MouseEvent e) {
                returnButton.setIcon(Button_Dark_New);
                ToolTipManager.sharedInstance().setInitialDelay(0);
                returnButton.setToolTipText("Return to the last frame");
            }
            @Override
            public void mouseExited(MouseEvent e) {
                returnButton.setIcon(Button_Light_New);
            }
        });
        layeredPane.add(returnButton,JLayeredPane.MODAL_LAYER);
    }

    public void initTable() {
        // Create a table model with columns for rank, name, and score
        System.out.println("Initializing the table...");
        tableModel = new DefaultTableModel(new String[]{"Rank", "Name", "Score", "Win-Rate"}, 0);
        int rank = 1;
        for (User user : users) {
            String name = user.getUsername();
            double score = user.getScore();
            double winRate = user.getWinRate();
            tableModel.addRow(new Object[]{rank, name, String.format("%.2f", score), String.format("%.2f", winRate)});
            rank++;
        }
        System.out.println("Initializing the JTable...");

        rankTable = new JTable(tableModel);
        rankTable.setFont(new Font("Calibri", Font.PLAIN, 12));
        rankTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        rankTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        rankTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        rankTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        rankTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        rankTable.setAutoCreateRowSorter(true);
        rankTable.setGridColor(new Color(0, 0, 0, 128));

        System.out.println("Initializing the JScrollPane...");
        JScrollPane scrollPane = new JScrollPane(rankTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);


    }

    private static class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius + 1;
            insets.top = radius + 1;
            insets.bottom = radius + 2;
            return insets;
        }
    }
}