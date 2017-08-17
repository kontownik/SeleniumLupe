package tests;

import org.junit.Test;
import pages.GoogleProviderPage;
import pages.SurferLoginPage;

public class LU2ValidGoogleLoginTest extends BaseTest {

    @Test
    public void validGoogleLoginTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        SurferLoginPage loginPage = new SurferLoginPage(driver).get();
        GoogleProviderPage googlePage = loginPage.surferGoogleLogin().get();
        googlePage.doGoogleLogin(properties.getPropValue("google_login"),properties.getPropValue("google_password"));
    }

}
