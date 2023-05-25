        package View;
        import javax.swing.*;

        import org.json.simple.JSONObject;

        import java.awt.BorderLayout;
        import java.awt.Color;
        import java.awt.Component;
        import java.awt.Cursor;
        import java.awt.Font;
        import java.awt.GridBagConstraints;
        import java.awt.GridBagLayout;
        import java.awt.Insets;
        import java.awt.Window;
        import java.awt.event.*;
        import java.awt.geom.RoundRectangle2D;

        import model.Project;
        import model.ValidChecker;
        public class UI implements ActionListener {
            public static boolean userInfoSet = false;
            // JSON file path
            // public static final String JSON_FILE_PATH = "user_info.json";

            public static JFrame window = new JFrame("Home Renovation Tool");
            public static JFrame userInfoInput = new JFrame("Login");
            Project project = new Project(this);

            public Component buildUI() {
                window.setSize(400, 300);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setLocationRelativeTo(null);

                JPanel toolbarPanel = new JPanel();
                toolbarPanel.setLayout(new GridBagLayout());
                toolbarPanel.setBackground(Color.DARK_GRAY);

                // About Button
                JButton aboutButton = new JButton("profile");
                aboutButton.addActionListener(this);
                customizeButton(aboutButton);

                // Add the profile button to the toolbar panel
                GridBagConstraints buttonGbc = new GridBagConstraints();
                buttonGbc.gridx = 1;
                buttonGbc.gridy = 0;
                buttonGbc.insets = new Insets(10, 0, 10, 10);
                buttonGbc.anchor = GridBagConstraints.NORTHWEST;
                toolbarPanel.add(aboutButton, buttonGbc);

                JButton projectButton = new JButton("Project");
                Project project = new Project(this);
                projectButton.addActionListener(e -> {
                    window.setVisible(false);
                    // Create an instance of the Project class and call the openProjectFrame() method
                    project.openProjectFrame();
                });
                customizeButton(projectButton);

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.anchor = GridBagConstraints.WEST;

                toolbarPanel.add(aboutButton, gbc);

                gbc.gridy = 1;
                toolbarPanel.add(projectButton, gbc);

                // Set the layout of the JFrame's content pane to BorderLayout
                window.setUndecorated(true);
                window.getRootPane().setWindowDecorationStyle(JRootPane.PLAIN_DIALOG);
                window.setLayout(new BorderLayout());
                window.getContentPane().setBackground(Color.BLACK);
                // Add the JPanel to the north position
                window.getContentPane().add(toolbarPanel, BorderLayout.WEST);
                // Set the shape of the frame to have rounded edges
                int cornerRadius = 20;  // Adjust the corner radius as needed
                window.setShape(new RoundRectangle2D.Double(0, 0, window.getWidth(), window.getHeight(), cornerRadius, cornerRadius));

                // Export Button
                JButton exportButton = new JButton("Export");
                exportButton.addActionListener(this);
                exportButton.addActionListener(e -> {
                    UserInfo.exportSettingsAndData();
                });
                customizeButton(exportButton);

                buttonGbc.gridx = 2;  // Adjust the grid position as needed
                toolbarPanel.add(exportButton, buttonGbc);
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
                }
                    }
                });
                window.setVisible(false);

                // User input window that will open if the user has not set user info yet.
                if (!userInfoSet) {
                    // JFrame userInfoInput = new JFrame("Login");
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

                            userInfoSet = true;
                            userInfoInput.dispose(); // Add this line to close the dialog box
                            window.setVisible(true);
                        }
                    });
                    userInfoInput.setVisible(true);
                }
                return aboutButton;
            }

            protected JSONObject createFilesObject() {
                return null;
            }

            private void customizeButton(AbstractButton button) {
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setContentAreaFilled(false);
                button.setOpaque(true);
                button.setBackground(new Color(220, 220, 220));
                button.setForeground(Color.BLACK);
                button.setFont(button.getFont().deriveFont(Font.PLAIN));
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }

            public Window getMainFrame() {
                return window;
            }

        }
