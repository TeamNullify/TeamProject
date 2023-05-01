import javax.swing.*;
import java.awt.event.*;

public class UI implements ActionListener {

    public void buildUI() {

        JFrame window = new JFrame("Home Renovation Tool");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // window.pack();

        //MENU BAR

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Help");  
        JMenuItem aboutButton=new JMenuItem("About");  
        aboutButton.addActionListener(this);
        menu.add(aboutButton);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);

        final JMenuItem about = new JMenuItem("About...");

        aboutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, 
                "Developers:\nNate Mann\nAnthony Green\nChristopher Yuan\n",
                Info.getVersion(), 1);
            }
        });


        JLabel aboutLabel = new JLabel("Home Renovation Application");


        // window.add(about);
        // JButton aboutButton = new JButton("About");
        
       // aboutButton.addActionListener(ActionEvent e){
         // JPanel panel = new JPanel(new GridLayout(3, 1));
        // panel.add(appNameLabel);
        // window.pack();
        window.setVisible(true);
        
    }

    private void buildAboutPopup(){

    }

    public void actionPerformed(ActionEvent e) {    
        if(e.getSource()=="About")  {  
            buildAboutPopup();
        }   
    }
}