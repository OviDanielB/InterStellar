package Controller;


import Model.UserType;
import View.LoginBean;
import DAO.UserRepository;
import Model.User;
import View.UserRegistrationBean;

import javax.persistence.NoResultException;

/**
 * Singleton login controller; controls user authentication and registration (only by an admin)
 */
public class LoginController {

    private static LoginController instance;

    private LoginController() {
    }

    public static LoginController getInstance() {
        if(instance == null){
            instance = new LoginController();
        }
        return instance;
    }

    /*
     * Returns a User if userID and password corrispond to a registred user
     */
    public User login(String userID,String password){

        try {
            return UserRepository.getUserByIDAndPassword(userID,password);

        } catch (NoResultException e) {

            return null;
        }
    }

    /**
     *  fills other user info(name,email,etc) after successful login
     */
    public void fillUserInfo(LoginBean loginBean,User user){

        loginBean.setFirstName(user.getFirstName());
        loginBean.setLastName(user.getLastName());
        loginBean.setEmail(user.getEmail());
        loginBean.setAdmin(user.isAdmin());
    }

    /**
     * registers a new user
     * @param bean class contains new user info
     * @throws Exception
     */
    public void registerUser(UserRegistrationBean bean) throws Exception {


        if(checkRegistrationFrom(bean)){
            User user = new User();
            user.setFirstName(bean.getFirstName());
            user.setLastName(bean.getLastName());
            user.setUserID(bean.getUserID());
            user.setPassword(bean.getPassword());
            user.setEmail(bean.getEmail());
            user.setUserType(UserType.USER);

            UserRepository.store(user);

        } else {
            throw new Exception("User info not correct");
        }
    }

    /**
     * checks if form is valid
     * @param bean new user info
     * @return true if info correct,false otherwise
     */
    private boolean checkRegistrationFrom(UserRegistrationBean bean) {

        boolean nameOK = (!bean.getFirstName().isEmpty() && !bean.getLastName().isEmpty());
        boolean idOK = (bean.getUserID().length() >= 6);
        boolean passwordOK = (bean.getUserID().length() >= 6);

        return  nameOK && idOK && passwordOK;
    }

}
