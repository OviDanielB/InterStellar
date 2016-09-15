package View;

import Controller.LoginController;

/**
 * Bean used in user registration proces
 */
public class UserRegistrationBean {

    private String userID;
    private String firstName;
    private String lastName;
    private String password;
    private String email;

    public UserRegistrationBean() {
    }

    /**
     * calls loginController and persists user if information are correct
     * @return true if process completed successfully, else false
     */
    public boolean validate(){

        LoginController loginController = LoginController.getInstance();

        try {

            loginController.registerUser(this);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserRegistrationBean{" +
                "userID='" + userID + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
