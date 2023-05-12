package view.UI;

import controller.GameController;
import model.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RankLabel extends JLabel {
    private final Color backgroundColor = new Color(246, 240, 226);
    private final Color currentUserBackgroundColor = new Color(202, 236, 212);
    private final Color borderColor = new Color(0,0,0);
    private final int cornerRadius = 10;
    private User user;
    private int rank;
    private boolean isCurrentUser;
    private boolean sortByScore;

    public RankLabel(){

    }
    public RankLabel(User user, int rank, boolean isCurrentUser, boolean sortByScore) {
        this.user = user;
        this.rank = rank;
        this.isCurrentUser =isCurrentUser;
        this.sortByScore = sortByScore;

        JLabel rankLabel = new JLabel(String.valueOf(rank));
        rankLabel.setForeground(Color.BLACK);
        rankLabel.setBounds(0,0,50,50);
        rankLabel.setFont(new Font("Britannic Bold", Font.BOLD, 18));
        rankLabel.setHorizontalAlignment(CENTER);
        rankLabel.setOpaque(false);

        JLabel usernameLabel = new JLabel(this.user.getUsername());
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(50,0,150,50);
        usernameLabel.setFont(new Font("Britannic Bold", Font.BOLD, 18));
        usernameLabel.setHorizontalAlignment(LEFT);

        JLabel scoreLabel = new JLabel(String.format("%.2f", this.user.getScore()));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBounds(200,0,100,50);
        //To set the score bold when ranking by scores.
        if(sortByScore){scoreLabel.setFont(new Font("Britannic Bold", Font.BOLD, 18));}
        else{scoreLabel.setFont(new Font("Calibri", Font.PLAIN, 18));}
        scoreLabel.setHorizontalAlignment(CENTER);

        JLabel winrateLabel = new JLabel(String.format("%.2f",this.user.getWinRate()));
        winrateLabel.setForeground(Color.BLACK);
        winrateLabel.setBounds(300,0,100,50);
        //To set the win rate bold when ranking by win rate.
        if(!sortByScore){winrateLabel.setFont(new Font("Britannic Bold", Font.BOLD, 18));}
        else{winrateLabel.setFont(new Font("Calibri", Font.PLAIN, 18));}
        winrateLabel.setHorizontalAlignment(CENTER);

        add(rankLabel);
        add(usernameLabel);
        add(scoreLabel);
        add(winrateLabel);
    }


    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill round rectangular
        if (isCurrentUser) {
            g2d.setColor(currentUserBackgroundColor);
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 5, cornerRadius, cornerRadius);
        }else{
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 5, cornerRadius, cornerRadius);
        }

        // Paint round border
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 5, cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

}
