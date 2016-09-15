package Model;
import javax.persistence.*;

/**
 * a registered user
 */
@Entity
@Table(name = "RegisteredUser")
public class User {

    /**
     * Default constructor
     */
    public User() {
    }

    public User(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    /**
     * User ID, must be at least 6 characters long
     */
    @Id
    @Column(length = 50)
    private String userID;

    /**
     * first name of user; cannot be null
     */
    @Column(length = 50,nullable = false)
    private String firstName;

    /**
     * last name of user, cannot be null
     */
    @Column(length = 50,nullable = false)
    private String lastName;

    /**
     * user password,at least 6 characters long
     */
    @Column(length = 50,nullable = false)
    private String password;

    /**
     * user email, cannot be null
     */
    @Column(length = 50,nullable = false)
    private String email;

    /**
     * User Type (ex. USER or ADMINISTRATOR with more privileges)
     */
    @Enumerated(value = EnumType.STRING)
    @Column(length = 20,nullable = false)
    private UserType userType;

    /**
     * @return true if username and password are valid;
     *         false otherwise
     */
    public Boolean isValid() {
        if(userID.length() >= 6 && password.length() >= 6) {
            return true;
        }
        return false;
    }

    // checks if user is admin
    public boolean isAdmin(){
        if (userType == UserType.ADMINISTRATOR)
            return true;

        return false;
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

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}