import javax.swing.*;

import java.awt.Component;
import java.awt.event.*;
import java.util.regex.Pattern;

public class UI implements ActionListener {

    public static boolean userInfoSet = false;
    JFrame window;

    public Component buildUI() {

        window = new JFrame("Home Renovation Tool");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // window.pack();



        //MENU BAR

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Help");  
        JMenuItem aboutButton = new JMenuItem("About");  
        //aboutButton.addActionListener(this);
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
        window.setVisible(false);

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
                    String username = nameInput.getText();
                    String email = emailInput.getText();
                    if (!isValidUsername(username)) {
                        JOptionPane.showMessageDialog(null, "Invalid username,Please Try Again.");
                        return;
                    }
                    if (!isValidEmail(email)) {
                        JOptionPane.showMessageDialog(null, "Invalid email address,Please Try Again.");
                        return;
                    }
                    UserInfo.setName(username);
                    UserInfo.setEmail(email);
                    userInfoSet = true;
                    userInfoInput.dispose(); // Add this line to close the dialog box


                    window.setVisible(true);
                }
            });

            userInfoInput.setVisible(true);
        }

        // FileFinder
        JButton foldersButton= new JButton("Folders");
        foldersButton.setBounds(50,50,50,50);
        window.add(foldersButton);
        foldersButton.addActionListener(this);
        foldersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.setVisible(false);
                FileFinder fileFinder=new FileFinder("Ahmed");
                //fileFinder.buildFileFinder();
            }
        });












        return aboutButton;

    }

    public void setWindowVisibale(){
        window.setVisible(true);
    }
    /**Checks if a given string is a valid email address using regular expressions.
     *@param email the email address to be checked
     *@return true if the email is valid, false otherwise
     */
    boolean isValidEmail(String email) {
        //a regular expression pattern that is used to validate an email address.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                             "[a-zA-Z0-9_+&*-]+)*@" +
                             "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                             "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
    /** Checks if a given string is a valid username using regular expressions.
      *@param username the username to be checked
       *@return true if the username is valid, false otherwise
       */
    boolean isValidUsername(String username) {
        //a regular expression pattern that is used to validate a username.
        String usernameRegex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        return pattern.matcher(username).matches();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
    // private void buildAboutPopup(){

    // }

    // public void actionPerformed(ActionEvent e) {    
    //     if(e.getSource()=="About")  {  
    //         buildAboutPopup();
    //     }   
    // }
}
