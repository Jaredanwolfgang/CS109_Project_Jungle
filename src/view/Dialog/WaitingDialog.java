package view.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class WaitingDialog extends JDialog{
    private JButton copyButton;
    private JLabel label1;
    private JLabel label2;
    public WaitingDialog(){

        initLabel();
        initDialog();
        initCopyButton();

        this.setVisible(true);
    }

    private void initCopyButton() {
        copyButton = new JButton("Copy Address");
        copyButton.setBounds(90,90,140,30);
        copyButton.setFocusPainted(false);
        copyButton.addActionListener(e -> {
            StringSelection stringSelection = null;
            try {
                stringSelection = new StringSelection(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
            new Thread(() -> {
                copyButton.setText("Copied!");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                copyButton.setText("Copy Address");
            }).start();
        });
        this.getContentPane().add(copyButton);
    }

    public void initDialog(){
        setSize(320,180);
        setTitle("Waiting for player...");
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLayout(null);
    }
    public void initLabel(){
        label1 = new JLabel("Waiting for player to join...");
        label1.setBounds(20,20,280,30);
        label1.setFont(new Font("Arial",Font.BOLD,14));
        this.getContentPane().add(label1);

        try {
            label2 = new JLabel("Your IP Address is: " + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        label2.setBounds(20,50,280,30);
        label2.setFont(new Font("Arial",Font.BOLD,14));
        this.getContentPane().add(label2);
    }
}