package view.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SuccessDialog extends JDialog {
    private String message;
    private JButton confirmButton;
    public SuccessDialog(String message){
        this.message = message;

        initLabel(this.message);
        initDialog();

        initConfirmButton();

        this.setVisible(true);
    }

    private void initConfirmButton() {
        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(120,30,100,30);
        confirmButton.addActionListener(e -> {
            dispose();
        });
        this.add(confirmButton);
    }

    public void initDialog(){
        this.setSize(350,100);
        this.setTitle("Success Dialog");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        //WindowListenr会使得Dialog在关闭之后导入到toFrame中
        this.addWindowListener(new WindowListener() {
            @Override public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
            @Override public void windowClosed(WindowEvent e) {}
            @Override public void windowIconified(WindowEvent e) {}
            @Override public void windowDeiconified(WindowEvent e) {}
            @Override public void windowActivated(WindowEvent e) {}
            @Override public void windowDeactivated(WindowEvent e) {}
        });
    }
    public void initLabel(String message){
        JLabel label = new JLabel(message);
        label.setBounds(110,10,200,20);
        this.add(label);
    }
}
