package View;

import Controller.LoginController;
import DAO.UserRepository;
import Exceptions.AlreadyPresentInDbException;
import Model.User;
import Model.UserType;

/**
 * Bean used in login process
 */
public class LoginBean {

    private String userID;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Boolean isAdmin;

    public LoginBean() {
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

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    /**
     * checks if user is registred
     */
    public boolean validate() {

        User user = new User("ovi","stars");
        user.setEmail("ovi.daniel.b@gmail.com");
        user.setFirstName("Ovi");
        user.setLastName("Barba");
        user.setUserType(UserType.ADMINISTRATOR);

        try {
            UserRepository.store(user);
        } catch (AlreadyPresentInDbException e) {
            e.printStackTrace();
        }

        User u;
        LoginController loginController = LoginController.getInstance();

        u = loginController.login(this.userID,this.password);

        if(u == null){
            return false;
        }

        /*
         * fills useful user info after successful login
         */
        loginController.fillUserInfo(this,u);

        return true;

    }
}
