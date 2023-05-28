package View;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;

import org.json.simple.JSONObject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import model.Project;
import model.ValidChecker;
public class UI implements ActionListener {
    public static boolean userInfoSet = false;
    // JSON file path
    // public static final String JSON_FILE_PATH = "user_info.json";

    public static JFrame window = new JFrame("Home Renovation Tool");
    public static JFrame userInfoInput = new JFrame("Login");
    Project project = new Project(this);
    private JButton signUpButton;
    private JButton submitButton;
    public JTextField nameInput = new JTextField();
    public JTextField emailInput = new JTextField();

    public Component buildUI() {
        window.setSize(500, 500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);

        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new GridBagLayout());
        toolbarPanel.setBackground(Color.DARK_GRAY);

        // About Button
        JButton aboutButton = createIconButton("profile", "TeamProject/maleprofileicon.png");
        aboutButton.addActionListener(this);
        customizeButton(aboutButton);

        JButton projectButton = createIconButton("Project", "TeamProject/icons8-mac-folder-48.png");
        Project project = new Project(this);
        projectButton.addActionListener(e -> {
            window.setVisible(false);
            // Create an instance of the Project class and call the openProjectFrame() method
            project.openProjectFrame();
        });
        customizeButton(projectButton);

        // Set the layout of the JFrame's content pane to BorderLayout
        window.setUndecorated(true);
        window.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
        window.setLayout(new BorderLayout());
        window.getContentPane().setBackground(Color.WHITE);
        // Add the JPanel to the north position
        window.getContentPane().add(toolbarPanel, BorderLayout.WEST);
        // Set the shape of the frame to have rounded edges
        int cornerRadius = 20;  // Adjust the corner radius as needed
        window.setShape(new RoundRectangle2D.Double(0, 0, window.getWidth(), window.getHeight(), cornerRadius, cornerRadius));

        // Export Button
        JButton exportButton = createIconButton("Export", "TeamProject/Export-06.png");
        exportButton.addActionListener(this);
        exportButton.addActionListener(e -> {
            UserInfo.exportSettingsAndData();
        });
        customizeButton(exportButton);

        // Budget Button
        JButton budgetButton = createIconButton("Budget", "TeamProject/budget.jpg");
        budgetButton.addActionListener(this);
        budgetButton.addActionListener(e -> {
            openBudgetPlanner();
        });
        customizeButton(budgetButton);

         // Add the profile button to the toolbar panel
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 0;
        buttonGbc.insets = new Insets(10, 0, 10, 10);
        buttonGbc.anchor = GridBagConstraints.NORTHWEST;
        toolbarPanel.add(aboutButton, buttonGbc);

        // Add the export button below the profile button
        buttonGbc.gridy = 1;
        toolbarPanel.add(exportButton, buttonGbc);

        // Add the project button below the export button
        buttonGbc.gridy = 2;
        toolbarPanel.add(projectButton, buttonGbc);

        // Add the budget button below the project button
        buttonGbc.gridy = 3;
        toolbarPanel.add(budgetButton, buttonGbc);

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

                String additionalText3 = "Anthony Green\n";
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

                JButton logoutButton = new JButton("Log Out");
                logoutButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Perform log out operations here
                        // For example, you can close the current panel and go back to the UserInfoInput frame

                        // Assuming the UserInfoInput frame is stored in a variable called 'userInfoInputFrame'
                        //panel.setVisible(false); // Hide the current panel
                        Window myWindow = SwingUtilities.getWindowAncestor(panel);
                        if (myWindow != null) {
                            myWindow.dispose();  // Dispose of the parent window
                        }
                        userInfoInput.setVisible(true);  // Show the UserInfoInput frame
                        window.setVisible(false);
                        // panel.setVisible(false); // Hide the current panel
                    }
                });

                panel.add(logoutButton);

                int result = JOptionPane.showConfirmDialog(null, panel, "About",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JFileChooser.APPROVE_OPTION) {
                    String username = nameField.getText();
                    String email = emailField.getText();
                    if (!ValidChecker.isValidUsername(username)) {
                        JOptionPane.showMessageDialog(null, "Invalid username. Please try again.");
                        return;
                    }
                    if (!ValidChecker.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(null, "Invalid email address. Please try again.");
                        return;
                    }
                    UserInfo.setName(username);
                    UserInfo.setEmail(email);
                    JSONObject filesObject = createFilesObject();
                    UserInfo.storeUserInfo(filesObject);
                    //UserInfo.storeUserInfo(username,email,"","");
        }
            }
        });
        window.setVisible(false);

        // User input window that will open if the user has not set user info yet.
        if (!userInfoSet) {
            // JFrame userInfoInput = new JFrame("Login");
            userInfoInput.setSize(500, 400); // Increase the height to accommodate the additional elements
            userInfoInput.setLocationRelativeTo(null);
            userInfoInput.setResizable(false);
            // Color armyGreen = new Color(75, 83, 32);
            // Color cobaltBlue = new Color(0, 71, 171);
            // Color cobaltGreen = new Color(61, 145, 64);
            // Color darkGreen = new Color(0, 100, 0);
            Color jetStream = new Color(169,  179, 189);
            userInfoInput.setBackground(null);


            Container c = userInfoInput.getContentPane(); //Gets the content layer
            JLabel label = new JLabel(); //JLabel Creation
            ImageIcon icon = new ImageIcon("TeamProject/appface2.jpg");
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(250, 400, java.awt.Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaledImage);
            label.setIcon(icon); //Sets the image to be displayed as an icon
            Dimension size = label.getPreferredSize(); //Gets the size of the image
            label.setBounds(0, 0, 250, size.height); //Sets the location of the image

            c.add(label); //Adds objects to the container

            // app Name
            JLabel appName = new JLabel("BuildPlan");
            Font font = new Font("SansSerif", Font.BOLD, 18);
            appName.setFont(font);
            appName.setBounds(320, 28, 200, 20);

            // welcome msg
            JLabel welcomeMsg = new JLabel("Welcome to BuildPlan");
            Font font1 = new Font("SFPro", Font.ITALIC, 13);
            welcomeMsg.setFont(font1);
            welcomeMsg.setBounds(290, 60, 200, 20);

            JLabel nameLabel = new JLabel("Username:");
            nameLabel.setBounds(270, 88, 70, 20);
            //JTextField nameInput = new JTextField();
            nameInput.setBounds(270, 107, 193, 28);

            JLabel emailLabel = new JLabel("Email:");
            emailLabel.setBounds(270, 140, 100, 20);
            // JTextField emailInput = new JTextField();
            emailInput.setBounds(270, 160, 193, 28);

            submitButton = new JButton("Sign In");
            submitButton.setBounds(310, 200, 100, 25);
            submitButton.setBorderPainted(false);
            submitButton.setFocusPainted(false);
            submitButton.setContentAreaFilled(false);
            submitButton.setOpaque(true);
            submitButton.setBackground(new Color(220, 220, 220));
            submitButton.setForeground(Color.BLACK);
            submitButton.setFont(submitButton.getFont().deriveFont(Font.PLAIN));
            submitButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            userInfoInput.setLayout(null); // Set layout to null for absolute positioning
            userInfoInput.getContentPane().setBackground(jetStream); // Set background color to white

            // GitHub Button
            JButton githubButton = createIconButton("GitHub", "TeamProject/GitHub.png");
            githubButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String githubUrl = "https://github.com/TeamNullify/TeamProject";
                    try {
                        Desktop.getDesktop().browse(java.net.URI.create(githubUrl));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            });
            customizeButton(githubButton);

            userInfoInput.add(appName);
            userInfoInput.add(welcomeMsg);
            userInfoInput.add(nameLabel);
            userInfoInput.add(nameInput);
            userInfoInput.add(emailLabel);
            userInfoInput.add(emailInput);
            userInfoInput.add(submitButton);
            //illustrationLabel.setVisible(true);
            // Add the sign-up button

            JLabel createAccMsg = new JLabel("Don't have an account yet?");
            Font font2 = new Font("SFPro", Font.ITALIC, 13);
            createAccMsg.setFont(font2);
            createAccMsg.setBounds(280, 240, 200, 20);
            userInfoInput.add(createAccMsg);

            initializeSignUpButton();
            signUpButton.setBounds(310, 260, 100, 25);
            githubButton.setBounds(300, 300,20,20);
            userInfoInput.add(githubButton);
            submitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent theEvent) {
                    String username = nameInput.getText();
                    String email = emailInput.getText();

                    if (!ValidChecker.isValidUsername(username)) {
                        JOptionPane.showMessageDialog(null, "Invalid username. Please try again.");
                        return;
                    }

                    if (!ValidChecker.isValidEmail(email)) {
                        JOptionPane.showMessageDialog(null, "Invalid email address. Please try again.");
                        return;
                    }

                    UserInfo.setName(username);
                    UserInfo.setEmail(email);

                    // Store user info
                    JSONObject filesObject = new JSONObject();
                    // Add the file details to the filesObject
                    // Example: filesObject.put("file1", "file1_path");

                    UserInfo.storeUserInfo(filesObject);
                    //UserInfo.storeUserInfo(username,email,"","");

                    userInfoSet = true;
                    userInfoInput.dispose(); // Add this line to close the dialog box
                    window.setVisible(true);
                }
            });
            userInfoInput.setVisible(true);
        }
        return aboutButton;
    }

    private void initializeSignUpButton() {
        signUpButton = new JButton("Sign Up");
        customizeButton(signUpButton);

        // Add ActionListener to handle sign-up button click
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a new frame to request name, email, and password
                JFrame signUpFrame = new JFrame("Sign Up");
                signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                signUpFrame.setSize(400, 200);
                signUpFrame.setLayout(null);

                // Create name label and text field
                JLabel nameLabel = new JLabel("Name:");
                nameLabel.setBounds(20, 20, 80, 25);
                JTextField nameField = new JTextField();
                nameField.setBounds(100, 20, 260, 25);

                // Create email label and text field
                JLabel emailLabel = new JLabel("Email:");
                emailLabel.setBounds(20, 50, 80, 25);
                JTextField emailField = new JTextField();
                emailField.setBounds(100, 50, 260, 25);

                // Create password label and text field
                JLabel passwordLabel = new JLabel("Password:");
                passwordLabel.setBounds(20, 80, 80, 25);
                JPasswordField passwordField = new JPasswordField();
                passwordField.setBounds(100, 80, 260, 25);

                // Create back button
                JButton backButton = new JButton("Back");
                backButton.setBounds(100, 120, 80, 25);
                backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        signUpFrame.dispose(); // Close the sign-up frame
                        userInfoInput.setVisible(true); // Show the user info input frame
                    }
                });

                // Create sign-up button
                JButton submitButton = new JButton("Submit");
                submitButton.setBounds(180, 120, 80, 25);
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String email = emailField.getText();
                        String password = new String(passwordField.getPassword());

                        // Create a new account
                        // Implement the logic to create a new account here
                        // You can display a success message or navigate to a new frame

                        UserInfo.setName(name); // Update the name in UserInfo class
                        UserInfo.setEmail(email); // Update the email in UserInfo class

                        // Create a JSONObject to store user info
                        JSONObject userObject = new JSONObject();
                        userObject.put("name", UserInfo.getName());
                        userObject.put("email", UserInfo.getEmail());
                        userObject.put("password", password);

                        // Store user info in JSON file
                        UserInfo.storeUserInfo(userObject);
                        //UserInfo.storeUserInfo(name,email,"","");

                        // Display a success message or navigate to a new frame
                        // ...

                        signUpFrame.dispose(); // Close the sign-up frame
                        window.setVisible(true);
                    }
                });

                // Add components to the sign-up frame
                signUpFrame.add(nameLabel);
                signUpFrame.add(nameField);
                signUpFrame.add(emailLabel);
                signUpFrame.add(emailField);
                signUpFrame.add(passwordLabel);
                signUpFrame.add(passwordField);
                signUpFrame.add(backButton);
                signUpFrame.add(submitButton);

                signUpFrame.setVisible(true); // Show the sign-up frame
                userInfoInput.setVisible(false); // Hide the user info input frame
            }
        });

        // Set the position and size of the sign-up button
        //signUpButton.setBounds(250, 180, 90, 25); // Adjust the position as needed
        // Add the sign-up button to the userInfoInput frame
        userInfoInput.add(signUpButton);
    }

    private JButton createIconButton(String tooltip, String iconName) {
        // Load the icon image from a file
        ImageIcon icon = new ImageIcon(iconName);
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);

        // Create a JButton with the icon and tooltip
        JButton button = new JButton(icon);
        button.setToolTipText(tooltip);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(40, 40));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        return button;
    }

    protected JSONObject createFilesObject() {
        return null;
    }

    private void customizeButton(JButton button) {
        // Set background and foreground colors
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);

        // Set button border
        Border emptyBorder = BorderFactory.createEmptyBorder();
        Border lineBorder = BorderFactory.createLineBorder(Color.DARK_GRAY, 1);
        Border compoundBorder = BorderFactory.createCompoundBorder(lineBorder, emptyBorder);
        button.setBorder(compoundBorder);

        // Set button insets
        button.setMargin(new Insets(5, 10, 5, 10));

        // Set cursor on hover
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.GRAY);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    emptyBorder
                ));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY);
                button.setBorder(compoundBorder);
            }
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
    }

    private void openBudgetPlanner() {
        BudgetPlannerFrame budgetPlanner = new BudgetPlannerFrame();
        budgetPlanner.startUp();
        window.setVisible(false);
    }

    public Window getMainFrame() {
        return window;
    }

}
