package users;

import java.util.Random;

/**
 * Created By: bsmith on 2/26/14.
 * Package: users
 * Description: Create basic user data all users have in common.
 */
public class User {
    private String[] userInfo;

    /**
     * The constructor sets all basic user account data into a field array of strings.
     * @param firstName first name
     * @param lastName last name
     * @param email email address
     * @param userName user name
     * @param password password
     */
    public User(String firstName, String lastName, String email, String userName, String password) {
        this.userInfo = new String[] {firstName, lastName, email, userName + generateRandomUserNameID(), password};
    }

    /**
     * Get first name.
     * @return user's first name
     */
    public String getFirstName() {
        return this.userInfo[0];
    }

    /**
     * Set first name.
     * @param firstName user's first name
     */
    public void setFirstName(String firstName) {
        this.userInfo[0] = firstName;
    }

    /**
     * Get last name.
     * @return user's last name
     */
    public String getLastName() {
        return this.userInfo[1];
    }

    /**
     * Set last name.
     * @param lastName user's last name
     */
    public void setLastName(String lastName) {
        this.userInfo[1] = lastName;
    }

    /**
     * Get email address.
     * @return user's email address
     */
    public String getEmail() {
        return this.userInfo[2];
    }

    /**
     * Set email address.
     * @param email user's email address
     */
    public void setEmailAddress(String email) {
        this.userInfo[2] = email;
    }

    /**
     * Get user name.
     * @return user's user name
     */
    public String getUserName() {
        return this.userInfo[3];
    }

    /**
     * Set user name.
     * @param userName user's user name
     */
    public void setUserName(String userName) {
        this.userInfo[3] = userName;
    }

    /**
     * Get password.
     * @return user's password
     */
    public String getPassword() {
        return this.userInfo[4];
    }

    /**
     * Set password.
     * @param password user's password
     */
    public void setPassword(String password) {
        this.userInfo[4] = password;
    }

    /**
     * Append random ID to user name offering over 17.5 million possible combinations.
     * This method allows dynamic user creation during runtime, when needed.
     * @return random ID from 0a0a0a to 9z9z9z
     */
    private static String generateRandomUserNameID() {
        Random numberGenerator = new Random();
        String id = "";
        String alphaCharacterSet = "abcdefghijklmnopqrstuvwxyz";

        //Concatenate a number and letter 3 times to the id.
        for (Integer i = 0; i < 3; i++) {
            Integer num = numberGenerator.nextInt(9); //Generate a random number including 0 through 9.
            //Generate a random number corresponding to a character in alphaCharacterSet.
            Integer alpha = numberGenerator.nextInt(25);
            String idPart = num.toString() + alphaCharacterSet.charAt(alpha);
            id += idPart;
        }

        return id;
    }
}
