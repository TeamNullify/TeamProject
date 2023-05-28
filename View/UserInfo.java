package View;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.BreakIterator;

import javax.swing.JFileChooser;

import org.json.simple.JSONArray;

public class UserInfo {
    private static String userName;
    private static String userEmail;
    public static final String JSON_FILE_PATH = "user_info.json";

    public static String getName() {
        return userName;
    }

    public static String getEmail() {
        return userEmail;
    }

    public static void setName(String name) {
        userName = name;
    }

    public static void setEmail(String email) {
        userEmail = email;
    }

   // Store user info in JSON file
public static void storeUserInfo(JSONObject filesObject) {

     // Check if the JSON file exists
     File jsonFile = new File(JSON_FILE_PATH);
     boolean fileExists = jsonFile.exists();

     if (!fileExists) {
         try {
             // Create the JSON file
             jsonFile.createNewFile();
         } catch (IOException e) {
             e.printStackTrace();
             return; // Exit the method if file creation fails
         }
     }
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
                if (existingName != null && existingEmail != null) {
                    if (existingName.equals(UserInfo.getName()) && existingEmail.equals(UserInfo.getEmail())) {
                        userExists = true;
                        break;
                    }
                }
            }
        }

        if (!userExists) {
            // Create a new array with the current user
            JSONArray newUserArray = new JSONArray();
            newUserArray.add(userObject);

            // Add the new array to the existing user info
            if (existingUserInfo != null) {
                existingUserInfo.add(newUserArray);
            } else {
                existingUserInfo = newUserArray;
            }
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
    public static JSONArray retrieveUserInfo() {
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

    public static void exportSettingsAndData() {
        // Retrieve user info from the JSON file
        JSONArray userInfoArray = retrieveUserInfo();

        // Check if user info is available
        if (userInfoArray != null && userInfoArray.size() > 0) {
            // Convert the JSON array to a formatted string
            String exportData = userInfoArray.toJSONString();
            String exportFileName = "user_info_export.json";  // Modify with your desired file name

            try {
                // Create a temporary file
                Path tempFilePath = Files.createTempFile("export", ".json");

                // Write the export data to the file
                try (BufferedWriter writer = Files.newBufferedWriter(tempFilePath, StandardCharsets.UTF_8)) {
                    writer.write(exportData);
                }

                // Show the file chooser dialog for saving the file
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setSelectedFile(new File(exportFileName));

                int result = fileChooser.showSaveDialog(null);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    // Copy the temporary file to the selected destination
                    Files.copy(tempFilePath, selectedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // Open the exported file using the default application
                    Desktop.getDesktop().open(selectedFile);
                }

                // Delete the temporary file
                Files.delete(tempFilePath);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle any exceptions that occur during export
            }
        } else {
            System.out.println("No user info available to export.");
        }
    }
}
