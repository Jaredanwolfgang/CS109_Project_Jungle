package view.Frame;
import model.User.User;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.lang.Object;

public class RankFrame extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 729;
    private ArrayList<User> users = new ArrayList<>();
    private JTable rankTable;
    private DefaultTableModel tableModel;
    private Frame frame;

    /** Now you can get the ArrayList of players by calling a method in gameListener */
    public RankFrame(Frame frame){
        this.frame = frame;
        this.users = frame.getGameController().onPlayerClickRankListButtonByScore();

        initTable();
        initBackground("Background/Spring.gif");
        initFrame();
        this.setVisible(false);
    }
    public void initFrame(){
        System.out.println("RankFrame is initializing...");
        this.setLayout(null);
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Rank");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    public void initBackground(String Address){
        System.out.println("RankFrame background is initializing...");
        JLabel background = new JLabel(new ImageIcon(Address));
        background.setBounds(0, 0, WIDTH, HEIGHT);
        this.getContentPane().add(background);
    }
    public void initTable(){
        // Create a table model with columns for rank, name, and score
        System.out.println("Initializing the table...");
        tableModel = new DefaultTableModel(new String[]{"Rank", "Name", "Score"}, 0);
        int rank = 1;
        /*for (Map.Entry<String, Double> entry : sortedPlayers) {
            String name = entry.getKey();
            double score = entry.getValue();
            tableModel.addRow(new Object[]{rank, name, score});
            rank++;
        }*/
        System.out.println("Initializing the JTable...");
        rankTable = new JTable(tableModel);
        rankTable.setFont(new Font("Calibri", Font.PLAIN, 12));
        rankTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));
        rankTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        rankTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        rankTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        rankTable.setAutoCreateRowSorter(true);
        rankTable.setGridColor(new Color(0,0,0,128));

//        System.out.println("Initializing the JScrollPane...");
//        JScrollPane scrollPane = new JScrollPane(rankTable);
//        scrollPane.setBorder(BorderFactory.createEmptyBorder());
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        System.out.println("Initializing the JPanel...");
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new RoundBorder(20, new Color(0, 0, 0, 128)));
        panel.setBounds(10,10,460,650);
        panel.add(new JLabel("Rank List", JLabel.CENTER), BorderLayout.NORTH);
//        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(rankTable, BorderLayout.CENTER);
        panel.setOpaque(false);

        this.getContentPane().add(panel);
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