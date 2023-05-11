package view.Frame;
import view.Dialog.SuccessDialog;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class RankFrame extends JFrame {
    private final int WIDTH = 500;
    private final int HEIGHT = 729;
    private Map<String,Double> players;
    private JTable rankTable;
    private DefaultTableModel tableModel;
    private Frame frame;

    /** Now you can get the ArrayList of players by calling a method in gameListener */
    public RankFrame(Frame frame){
        this.frame = frame;
        initFrame();
        try {
            this.players = initRank();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initTable();
        initBackground("Background/Spring.gif");

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
        System.out.println("Sorting the players by scores...");
        // First, sort the players by scores
        // Convert the map to a list and sort it by scores in descending order
        List<Map.Entry<String, Double>> sortedPlayers = new ArrayList<>(players.entrySet());
        // Lambda expression: sort the list by scores in descending order
        Collections.sort(sortedPlayers, (p1, p2) -> p2.getValue().compareTo(p1.getValue()));
        // Create a table model with columns for rank, name, and score
        System.out.println("Initializing the table...");
        tableModel = new DefaultTableModel(new String[]{"Rank", "Name", "Score"}, 0);
        int rank = 1;
        for (Map.Entry<String, Double> entry : sortedPlayers) {
            String name = entry.getKey();
            double score = entry.getValue();
            tableModel.addRow(new Object[]{rank, name, score});
            rank++;
        }
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
    public void initButton(String Address1, String Address2, JButton jb, int index, int x, int y, int width, int height) {
        System.out.println("RankFrame button" + jb + " is initializing...");
        ImageIcon Button_Light = new ImageIcon(Address1);
        Image img = Button_Light.getImage();
        Image newimg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Light_New = new ImageIcon(newimg);
        ImageIcon Button_Dark = new ImageIcon(Address2);
        Image img2 = Button_Dark.getImage();
        Image newimg2 = img2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon Button_Dark_New = new ImageIcon(newimg2);

        jb.setBorderPainted(false);
        jb.setContentAreaFilled(false);
        jb.setFocusPainted(false);
        jb.setOpaque(false);

        jb.setBounds(x, y, width, height);
        jb.setIcon(Button_Light_New);
        jb.addMouseListener(new MouseListener() {
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
                jb.setIcon(Button_Dark_New);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jb.setIcon(Button_Light_New);
            }
        });

        this.getContentPane().add(jb);
    }
    public Map<String, Double> initRank() throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Information\\users.txt"));
            String line;
            Map<String, Double> players = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String storedUsername = parts[0];
                Double storedScore = Double.parseDouble(parts[2]);
                players.put(storedUsername, storedScore);
                System.out.println("Player " + storedUsername + " is added to the rank list. With score: "+storedScore);
            }
            return players;
        } catch (IOException e) {
            System.out.println("Error reading file.");
            e.printStackTrace();
            return null;
        }
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