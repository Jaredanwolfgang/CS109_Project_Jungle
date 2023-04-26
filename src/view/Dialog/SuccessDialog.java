package view.Dialog;

import javax.swing.*;
import java.awt.*;

public class SuccessDialog extends JDialog {
    private String message;
    private JFrame toFrame;
    public SuccessDialog(String message,JFrame toFrame){
        this.message = message;
        this.toFrame = toFrame;
        this.setSize(320,180);
        this.setTitle("Success Dialog");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
