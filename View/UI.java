package View;
import javax.swing.*;
import java.awt.Component;
import java.awt.event.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
// Import the required libraries
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;


public class UI implements ActionListener {

    public static boolean userInfoSet = false;
     // JSON file path
     private static final String JSON_FILE_PATH = "user_info.json";

    public Component buildUI() {

        JFrame window = new JFrame("Home Renovation Tool");
        window.setSize(400, 300);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        // window.pack();
        //MENU BAR
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Profile");  
        JMenuItem aboutButton = new JMenuItem("About");  
        aboutButton.addActionListener(this);
        menu.add(aboutButton);
        menuBar.add(menu);
        window.setJMenuBar(menuBar);

        aboutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent theEvent) {
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
                JLabel nameLabel = new JLabel("Username: ");
                JTextField nameField = new JTextField(UserInfo.getName(), 20);
                panel.add(nameLabel);
                panel.add(nameField);
        
                JLabel emailLabel = new JLabel("User Email: ");
                JTextField emailField = new JTextField(UserInfo.getEmail(), 20);
                panel.add(emailLabel);
                panel.add(emailField);
        
                // Additional text
                String additionalText = "\nDeveloped by Team Nullify\n";
                JLabel additionalLabel = new JLabel(additionalText);
                panel.add(additionalLabel);

                String additionalText1 = "\nDevelopers:\n";
                JLabel additionalLabel1 = new JLabel(additionalText1);
                panel.add(additionalLabel1);

                String additionalText2 = "Nate Mann\n";
                JLabel additionalLabel2 = new JLabel(additionalText2);
                panel.add(additionalLabel2);

                String additionalText3 =  "Anthony Green\n";
                JLabel additionalLabel3 = new JLabel(additionalText3);
                panel.add(additionalLabel3);

                String additionalText4 = "Christopher Yuan\n";
                JLabel additionalLabel4 = new JLabel(additionalText4);
                panel.add(additionalLabel4); 

                String additionalText5 = "Elroy Mbabazi\n";
                JLabel additionalLabel5 = new JLabel(additionalText5);
                panel.add(additionalLabel5);

                String additionalText6 = "Abdel Rahman Abudayyah";
                JLabel additionalLabel6 = new JLabel(additionalText6);
                panel.add(additionalLabel6);
        
                int result = JOptionPane.showConfirmDialog(null, panel, "About",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
                if (result == JOptionPane.OK_OPTION) {
                    String username = nameField.getText();
                    String email = emailField.getText();
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
                    storeUserInfo();
                }
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
                     // Store user info
                     storeUserInfo();
                    userInfoSet = true;
                    userInfoInput.dispose(); // Add this line to close the dialog box
                    window.setVisible(true);
                }
            });
             // Retrieve user info if available
             //retrieveUserInfo();
            userInfoInput.setVisible(true);
        }
        return aboutButton;

    }
    /**Checks if a given string is a valid email address using regular expressions.
     *@param email the email address to be checked
     *@return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
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
    public static boolean isValidUsername(String username) {
        //a regular expression pattern that is used to validate a username.
        String usernameRegex = "^[a-zA-Z0-9_-]{3,20}$";
        Pattern pattern = Pattern.compile(usernameRegex);
        return pattern.matcher(username).matches();
    }
     // Store user info in JSON file
private void storeUserInfo() {
    JSONArray existingUserInfo = retrieveUserInfo();
    JSONObject userObject = new JSONObject();
    userObject.put("name", UserInfo.getName());
    userObject.put("email", UserInfo.getEmail());

    if (existingUserInfo != null) {
        // Check if user information already exists in the array
        boolean userExists = false;
        for (Object obj : existingUserInfo) {
            if (obj instanceof JSONObject) {
                JSONObject existingUser = (JSONObject) obj;
                String existingName = (String) existingUser.get("name");
                String existingEmail = (String) existingUser.get("email");
                if (existingName.equals(UserInfo.getName()) && existingEmail.equals(UserInfo.getEmail())) {
                    userExists = true;
                    break;
                }
            }
        }

        if (!userExists) {
            // Remove the existing user object from the array
            existingUserInfo.removeIf(obj -> {
                if (obj instanceof JSONObject) {
                    JSONObject existingUser = (JSONObject) obj;
                    String existingName = (String) existingUser.get("name");
                    String existingEmail = (String) existingUser.get("email");
                    return existingName.equals(UserInfo.getName()) || existingEmail.equals(UserInfo.getEmail());
                }
                return false;
            });

            // Add the updated user object to the array
            existingUserInfo.add(userObject);
        }
    } else {
        // If no existing information found, create a new array with the current user
        existingUserInfo = new JSONArray();
        existingUserInfo.add(userObject);
    }

    try (FileWriter file = new FileWriter(JSON_FILE_PATH)) {
        file.write(existingUserInfo.toJSONString());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    // Retrieve user info from JSON file
    private JSONArray retrieveUserInfo() {
        JSONParser parser = new JSONParser();
    JSONArray userInfoArray = null;

    try (FileReader fileReader = new FileReader(JSON_FILE_PATH)) {
        Object obj = parser.parse(fileReader);
        if (obj instanceof JSONArray) {
            userInfoArray = (JSONArray) obj;
        }
    } catch (IOException | ParseException e) {
        e.printStackTrace();
    }

    return userInfoArray;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}
