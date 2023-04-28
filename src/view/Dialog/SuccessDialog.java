package view.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SuccessDialog extends JDialog {
    private String message;
    private JFrame toFrame;
    public SuccessDialog(String message,JFrame toFrame){
        this.message = message;
        this.toFrame = toFrame;

        initLabel(message);
        initDialog();

        this.setVisible(true);
    }
    public void initDialog(){
        this.setSize(320,180);
        this.setTitle("Success Dialog");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        //WindowListenr会使得Dialog在关闭之后导入到toFrame中
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {

            }

            @Override
            public void windowClosing(WindowEvent e) {
                toFrame.setVisible(true);
            }

            @Override
            public void windowClosed(WindowEvent e) {

            }

            @Override
            public void windowIconified(WindowEvent e) {

            }

            @Override
            public void windowDeiconified(WindowEvent e) {

            }

            @Override
            public void windowActivated(WindowEvent e) {

            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
    }
    public void initLabel(String message){
        JLabel label = new JLabel(message);
        label.setBounds(60,10,200,100);
        this.add(label);
    }
}
