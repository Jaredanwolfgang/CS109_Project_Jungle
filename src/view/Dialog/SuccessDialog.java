package view.Dialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class SuccessDialog{
    public SuccessDialog(String message, JFrame fatherComponent){
            JOptionPane.showMessageDialog(fatherComponent,
                    message,
                    "Confirmation",
                    JOptionPane.INFORMATION_MESSAGE);
    }
}
