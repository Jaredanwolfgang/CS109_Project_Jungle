package view.Frame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class LoadFileFrame extends JFrame implements ActionListener {

    private JTextField textField;
    private JButton button;
    private Frame frame;

    public LoadFileFrame(Frame frame) {
        this.frame = frame;
        setTitle("File Chooser Frame");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(400, 100);
        setLocationRelativeTo(null);

        // Create the text field
        textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 20));

        // Create the button to choose a file
        button = new JButton("Please Select File");
        button.addActionListener(this);

        // Add the components to the frame
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(textField);
        panel.add(button);
        getContentPane().add(panel, BorderLayout.CENTER);

        setVisible(true);
        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == button) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("Save Files"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (file.getName().endsWith(".txt")) {
                    textField.setText(file.getAbsolutePath());
                    // Here you can input the file to the system
                    frame.getGameController().onPlayerClickLoadButton(textField.getText());
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "ERROR 101: Please select a .txt file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
