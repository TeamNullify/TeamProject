/**
* @author Christopher Yuan, Elroy Mbabazi, Anthony Green, Nathaniel Mann
*/

public class UserInfo {
    private static String userName;
    private static String userEmail;

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
}
