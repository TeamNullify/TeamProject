package model;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class FolderFrame extends JFrame {

    public FolderFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Folder Actions");
        setSize(300, 200);

        JButton loadButton = new JButton("Load");
        JButton saveButton = new JButton("Save");

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the load functionality here
            }
        });

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Implement the save functionality here
            }
        });

        getContentPane().setLayout(new FlowLayout());
        getContentPane().add(loadButton);
        getContentPane().add(saveButton);
    }
}
