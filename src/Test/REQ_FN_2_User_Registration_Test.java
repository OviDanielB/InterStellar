package Test;

import DAO.UserRepository;
import Model.User;
import View.UserRegistrationBean;
import junit.framework.TestCase;
import org.junit.Test;


import javax.persistence.NoResultException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * REQ-FUN-2 User Registration : register a new user with name, last name, UserID (min 6 chars), password(min 6 chars) and email
 */
public class REQ_FN_2_User_Registration_Test {


    @Test
    public void userRegistration(){

        UserRegistrationBean registrationBean = new UserRegistrationBean();
        registrationBean.setFirstName("firstName");
        registrationBean.setLastName("lastName");
        registrationBean.setUserID("TestTestTest");
        registrationBean.setPassword("passwordPassword");
        registrationBean.setEmail("test@test.com");

        boolean success = registrationBean.validate();

        User u = new User();
        try {
            u = UserRepository.getUserByIDAndPassword(registrationBean.getUserID(), registrationBean.getPassword());
        } catch (NoResultException e){
            //failed
            assertTrue(false);
        }

        assertTrue(success);

        assertEquals(registrationBean.getFirstName(),u.getFirstName());
        assertEquals(registrationBean.getLastName(),u.getLastName());
        assertEquals(registrationBean.getUserID(),u.getUserID());
        assertEquals(registrationBean.getEmail(),u.getEmail());
        assertEquals(registrationBean.getPassword(),u.getPassword());

    }

}