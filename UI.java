import javax.swing.*;

import java.awt.event.*;

public class UI implements ActionListener {

    private static boolean userInfoSet = false;

    public void buildUI() {

        JFrame window = new JFrame("Home Renovation Tool");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // window.pack();

        //MENU BAR

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Help");  
        JMenuItem aboutButton = new JMenuItem("About");  
        aboutButton.addActionListener(this);
        menu.add(aboutButton);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);

        aboutButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JOptionPane.showMessageDialog(null, 
                "Username: " + UserInfo.getName() + "\nUser email: " + UserInfo.getEmail() 
                + "\nDeveloped by Team Nullify\n"
                + "\nDevelopers:\nNate Mann\nAnthony Green\nChristopher Yuan\nElroy Mbabazi\nAbdel Rahman Abudayyah",
                Info.getVersion(), 1);
            }
        });
        window.setVisible(true);

        // User input window that will open if the user has not set user info yet.
        if (userInfoSet == false) {
            JFrame userInfoInput = new JFrame("Enter User Info");
            userInfoInput.setSize(400, 200);
            userInfoInput.setLocationRelativeTo(null);

            JLabel nameLabel = new JLabel("Username: ");
            nameLabel.setBounds(100, 8, 70, 20);
            JTextField nameInput = new JTextField();
            nameInput.setBounds(100, 27, 193, 28);
            JLabel emailLabel = new JLabel("User Email: ");
            emailLabel.setBounds(100, 80, 100, 20);
            JTextField emailInput = new JTextField();
            emailInput.setBounds(100, 100, 193, 28);
            JButton submit = new JButton("Submit");
            submit.setBounds(230, 130, 90, 25);
            JLabel placeholder = new JLabel(); // Do not remove this

            userInfoInput.add(nameLabel);
            userInfoInput.add(nameInput);
            userInfoInput.add(emailLabel);
            userInfoInput.add(emailInput);
            userInfoInput.add(submit);
            userInfoInput.add(placeholder);

            submit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(final ActionEvent theEvent) {
                    UserInfo.setName(nameInput.getText());
                    UserInfo.setEmail(emailInput.getText());
                    userInfoSet = true;
                }
            });

            userInfoInput.setVisible(true);
        }

    }

    // private void buildAboutPopup(){

    // }

    // public void actionPerformed(ActionEvent e) {    
    //     if(e.getSource()=="About")  {  
    //         buildAboutPopup();
    //     }   
    // }
}