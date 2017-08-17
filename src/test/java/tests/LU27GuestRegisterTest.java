package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminPersonGridPage;
import pages.RegisterPage;
import pages.RegisterSuccessPage;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class LU27GuestRegisterTest extends BaseTest {

    private static Timestamp timestamp;

    @Test
    public void guestRegisterTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        timestamp = new Timestamp(System.currentTimeMillis());
        String clearTimestamp = timestamp+"";
        clearTimestamp = clearTimestamp.replace(":","");
        clearTimestamp = clearTimestamp.replace(".","");
        clearTimestamp = clearTimestamp.replace(" ","");
        String newAccountEmail = "dummydata";
        List<String> params = Arrays.asList("Jan", "Selenium", newAccountEmail, "dummydata", "dummydata");

        //przejscie na strone rejestracji
        RegisterPage registerPage = new RegisterPage(driver).get();

        //wypelnienie i wyslanie formularza (strona sukcesu sprawdza czy pojawil sie poprawny komunikat i czy zgadza sie adres URL)
        RegisterSuccessPage afterRegisterPage = registerPage.registerAccount(params).get();

        //logowanie sie na administratora
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie do grida uzytkownikow
        AdminPersonGridPage personGrid = new AdminPersonGridPage(driver).get();

        // odnalezienie uzytkownika za pomoca adresu email
        personGrid.doGridSearch(newAccountEmail);
        Assert.assertTrue("ERROR: Nie znaleziono uzytkownika o tym adresie email", personGrid.isGridColumnContainsText(3, newAccountEmail));

        //aktywacja konta za pomoca przycisku aktywacji
        personGrid.doGridActivateFirstRow();
        personGrid.waitForGridReload();

        //sprawdzenie statusu (czy jest aktywny)
        Assert.assertTrue("ERROR: Nie znaleziono uzytkownika o tym adresie email", personGrid.isGridColumnContainsText(5, "Aktywny"));
    }

}
