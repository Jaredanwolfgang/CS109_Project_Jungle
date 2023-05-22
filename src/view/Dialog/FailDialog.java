package view.Dialog;

import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FailDialog {
    public FailDialog(String message, JFrame fatherComponent) {
        JOptionPane.showMessageDialog(fatherComponent,
                message,
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }
}