package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginErrorPage;
import pages.AdminLoginPage;

public class LU5InvalidAdminLoginTest extends BaseTest {

    @Test
    public void invalidAdminLoginTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        AdminLoginErrorPage errorPage = loginPage.incorrectLogin(properties.getPropValue("admin_login"), properties.getPropValue("incorect_password")).get();

        String expectedMessageError = "Nieprawidłowy login lub hasło lub nie masz uprawnień do tej witryny.";
        Assert.assertTrue("ERROR: Komunikat wyswietlil sie nieprawidlowo: '"+errorPage.getErrorText()+"'", errorPage.getErrorText().contains(expectedMessageError));
    }

}
