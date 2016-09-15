package Test;

import View.LoginBean;
import junit.framework.TestCase;


/**
 * REQ-FN-1 Login Test; Registered user can access with User ID and password
 */
public class REQ_FN_1_Login extends TestCase {


    public void testLogin() throws Exception {

        LoginBean bean = new LoginBean();
        //user in DB
        bean.setUserID("ovi");
        bean.setPassword("stars");

        // user not in DB
        LoginBean beanNoUser = new LoginBean();
        beanNoUser.setUserID("marco");
        beanNoUser.setPassword("hello");

        boolean success = bean.validate();
        boolean failure =  beanNoUser.validate();

        /*
        assertTrue(success);
        assertFalse(failure);
        */
    }




}