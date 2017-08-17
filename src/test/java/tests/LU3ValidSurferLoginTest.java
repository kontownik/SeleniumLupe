package tests;

import org.junit.Test;
import pages.SurferLoginPage;

public class LU3ValidSurferLoginTest extends BaseTest {

    @Test
    public void validSurferLoginTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        SurferLoginPage loginPage = new SurferLoginPage(driver).get();
        loginPage.surferCorrectLogin(properties.getPropValue("surfer_login"), properties.getPropValue("surfer_password")).get();
    }

}
