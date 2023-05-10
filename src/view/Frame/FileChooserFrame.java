package view.Frame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class FileChooserFrame extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton button;
    private Frame frame;

    public FileChooserFrame(Frame frame) {
        this.frame = frame;
        setTitle("File Chooser Frame");
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        setSize(400, 100);
        setLocationRelativeTo(null);

        // Create the text field
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 20));

        // Create the button to choose a file
        button = new JButton("Select File");
        button.addActionListener(this);

        // Add the components to the frame
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(textField);
        panel.add(button);
        getContentPane().add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                if (file.getName().endsWith(".txt")) {
                    textField.setText(file.getAbsolutePath());
                    // Here you can input the file to the system
                    this.setVisible(false);
                    frame.getGameController().onPlayerClickLoadButton(textField.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "Please select a .txt file.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
