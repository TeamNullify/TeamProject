package View;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;

public class UserInfo {
    static String userName;
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
public static void storeUserInfo() {
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
}
