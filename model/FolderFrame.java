package model;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FolderFrame extends JFrame {
    public Project project = new Project(null);

    public FolderFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Folder Actions");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        JPanel filePanel = new JPanel();
        JButton loadButton = new JButton("Add File");
        JButton saveButton = new JButton("Save");
        JButton backButton = new JButton("Back");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the load functionality here
                project.loadFile();
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the save functionality here
                project.saveFile();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the save functionality here
                project.openProjectFrame();
                dispose();
                // project.openProjectFrame();
            }
        });
        panel.add(loadButton);
        panel.add(saveButton);
        panel.add(backButton);
        getContentPane().add(filePanel, BorderLayout.CENTER);
        getContentPane().add(panel, BorderLayout.SOUTH);
    }
}
