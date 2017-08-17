package tests;

import org.junit.Test;
import pages.AdminLoginPage;


public class LU4ValidAdminLoginTest extends BaseTest {

    @Test
    public void validAdminLoginTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();
    }

}
