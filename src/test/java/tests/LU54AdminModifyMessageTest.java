package tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pages.AdminLoginPage;
import pages.AdminMessageEditPage;
import pages.AdminMessageGridPage;
import pages.PublicMessagesGridPage;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LU54AdminModifyMessageTest extends BaseTest {
    private static int reportID;
    private static Timestamp timestamp;
    private static String desc;

    @Test
    public void T_01_ADMIN_addMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //parametry nowego komunikatu
        reportID = 0;
        timestamp = new Timestamp(System.currentTimeMillis());
        desc = "Burze nad miastem "+timestamp;
        List<String> params = Arrays.asList("point-center", "Ostrzeżenia meteorologiczne", "Burze", desc, "http://bit-sa.pl", "", "", "Nowy", "Alarmowy", "");

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone dodawanie nowego komunikatu
        AdminMessageEditPage adminMessageEditPage = new AdminMessageEditPage(driver, 0, "add");

        //wypelnienie i wyslanie formularza (przechodzi na grid komunikatow)
        AdminMessageGridPage adminMessageGridPage = adminMessageEditPage.addMessage(params);

        //wyszukanie komunikatu na gridzie administracyjnym
        adminMessageGridPage.doGridSearch(desc);
        Assert.assertTrue("ERROR: Nie znaleziono tekstu", adminMessageGridPage.isGridColumnContainsText(4, desc));

        //pobranie ID
        reportID = Integer.parseInt(adminMessageGridPage.getTextFromGridColumn("1"));

    }

    @Test
    public void T_02_ADMIN_editMessageConfirmTest() throws Exception {

        System.out.println("Rozpoczeto test: " + getClass().toString() + "." + name.getMethodName());
        List<String> params = Arrays.asList("", "", "", "", "", "", "", "Opublikowany", "", "");

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone dodawanie nowego komunikatu
        AdminMessageEditPage adminMessageEditPage = new AdminMessageEditPage(driver, reportID, "edit");

        //wypelnienie i wyslanie formularza (przechodzi na grid komunikatow)
        AdminMessageEditPage adminMessageEditedPage = adminMessageEditPage.editMessage(params);

        //przejscie na publiczny grid komunikatow
        PublicMessagesGridPage publicMessagesGridPage = new PublicMessagesGridPage(driver);

        //wyszukanie komunikatu na gridzie publicznym
        publicMessagesGridPage.doGridSearch(desc);
        Assert.assertTrue("ERROR: Nie znaleziono tekstu", publicMessagesGridPage.isGridColumnContainsText(3, desc));
    }

    @Test
    public void T_03_ADMIN_editMessageArchiveTest() throws Exception {

        System.out.println("Rozpoczeto test: " + getClass().toString() + "." + name.getMethodName());
        List<String> params = Arrays.asList("", "", "", "", "", "", "", "Archiwalny", "", "");

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone dodawanie nowego komunikatu
        AdminMessageEditPage adminMessageEditPage = new AdminMessageEditPage(driver, reportID, "edit");

        //wypelnienie i wyslanie formularza (przechodzi na grid komunikatow)
        AdminMessageEditPage adminMessageEditedPage = adminMessageEditPage.editMessage(params);

        //przejscie na publiczny grid komunikatow
        PublicMessagesGridPage publicMessagesGridPage = new PublicMessagesGridPage(driver);

        //wyszukanie komunikatu na gridzie publicznym
        publicMessagesGridPage.doGridSearch(desc);
        Assert.assertFalse("ERROR: Znaleziono tekst wersji archiwalnej w widoku publicznym", publicMessagesGridPage.isGridColumnContainsText(3, desc));
    }


}
