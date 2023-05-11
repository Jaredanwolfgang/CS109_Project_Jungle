package view.Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SaveFileFrame extends JFrame implements ActionListener {
    private JTextField filenameField;
    private JTextField directoryField;
    private Frame frame;

    public SaveFileFrame(Frame frame) {
        super("Output Frame");
        this.frame = frame;

        // Create the main content panel
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Create the file name input panel
        JPanel filenamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel filenameLabel = new JLabel("Output File Name: ");
        filenamePanel.add(filenameLabel);
        filenameField = new JTextField(20);
        filenamePanel.add(filenameField);
        contentPanel.add(filenamePanel, BorderLayout.NORTH);

        // Create the directory input panel
        JPanel directoryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel directoryLabel = new JLabel("Output Directory: ");
        directoryPanel.add(directoryLabel);
        directoryField = new JTextField(20);
        directoryPanel.add(directoryField);
        JButton chooseDirButton = new JButton("Choose Directory");
        chooseDirButton.addActionListener(e -> chooseOutputDirectory());
        directoryPanel.add(chooseDirButton);
        contentPanel.add(directoryPanel, BorderLayout.CENTER);

        // Create the write button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton writeButton = new JButton("Write Output");
        writeButton.addActionListener(this);
        buttonPanel.add(writeButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set the content pane and show the frame
        setLocationRelativeTo(null);
        setContentPane(contentPanel);
        pack();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String filename = filenameField.getText();
        String directory = directoryField.getText();
        if (filename == null || filename.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a file name.");
            return;
        }
        if (directory == null || directory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please choose an output directory.");
            return;
        }
        String filePath;
        if(!directoryField.getText().endsWith(".txt")){
            filePath = directoryField.getText() + File.separator + filenameField.getText() + ".txt";
        }else{
            filePath = directoryField.getText() + File.separator + filenameField.getText();
        }
        frame.getGameController().onPlayerClickSaveButton(filePath);
        JOptionPane.showMessageDialog(this, "Output written to: " + filePath);
    }

    private void chooseOutputDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Choose Output Directory");
        fileChooser.setCurrentDirectory(new File("Save Files"));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedDir = fileChooser.getSelectedFile();
            directoryField.setText(selectedDir.getAbsolutePath());
        }
    }
}
