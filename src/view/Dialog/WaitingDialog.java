package view.Dialog;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WaitingDialog extends JDialog{
    public WaitingDialog(){

        initLabel();
        initDialog();


        this.setVisible(true);
    }
    public void initDialog(){
        this.setSize(320,180);
        this.setTitle("Waiting for player...");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
    }
    public void initLabel(){
        JLabel label = new JLabel("Waiting for online player...");
        label.setBounds(60,10,200,80);
        this.add(label);
    }
}