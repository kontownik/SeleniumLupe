package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminMessageEditPage;
import pages.AdminMessageGridPage;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class LU13AdminAddMessageTest extends BaseTest {

    @Test
    public void adminAddMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //parametry nowego komunikatu
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String desc = "Smog nad miastem "+timestamp;
        //String desc = "1"; //DEBUG
        List<String> params = Arrays.asList("point-center", "Ostrzeżenia meteorologiczne", "Wydarzenie wygenerowane automatycznie", desc, "http://bit-sa.pl", "2017-04-29 23:59", "", "Opublikowany", "Alarmowy", "");

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone dodawanie nowego komunikatu (wypelnienie i wyslanie formularza)
        AdminMessageEditPage adminMessageEditPage = new AdminMessageEditPage(driver, 0, "add");

        //przejscie na niepubliczny grid komunikatow
        AdminMessageGridPage adminMessageGridPage = adminMessageEditPage.addMessage(params);

        //Wyszukanie komunikatu
        adminMessageGridPage.doGridSearch(desc);
        Assert.assertTrue("ERROR: Nie znaleziono tekstu", adminMessageGridPage.isGridColumnContainsText(4, desc));

    }
}
