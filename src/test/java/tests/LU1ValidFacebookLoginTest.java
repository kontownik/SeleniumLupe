package tests;

import org.junit.Test;
import pages.FacebookProviderPage;
import pages.SurferLoginPage;

public class LU1ValidFacebookLoginTest extends BaseTest {

    @Test
    public void validFacebookLoginTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        SurferLoginPage loginPage = new SurferLoginPage(driver).get();
        FacebookProviderPage facebookProviderPage = loginPage.surferFacebookLogin().get();
        facebookProviderPage.doFacebookLogin(properties.getPropValue("facebook_login"),properties.getPropValue("facebook_password"));
    }

}
