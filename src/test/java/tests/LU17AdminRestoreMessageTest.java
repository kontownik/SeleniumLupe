package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminMessageEditPage;
import pages.AdminMessageGridPage;

public class LU17AdminRestoreMessageTest extends BaseTest {
    private static String desc;

    @Test
    public void adminRestoreMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: " + getClass().toString() + "." + name.getMethodName());

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na grid administracyjny komunikatow
        AdminMessageGridPage adminMessageGridPage = new AdminMessageGridPage(driver);
        adminMessageGridPage.waitForGridReload();
        desc = adminMessageGridPage.getTextFromGridColumn("4");
        String IDText = adminMessageGridPage.getTextFromGridColumn("1");
        System.out.println("Znaleziono ID:"+IDText);
        //usuniecie potencjalnego 'Czytaj więcej' ktore nie jest trescia mimo ze jest w polu tresci
        desc = desc.replace(" Czytaj więcej","");

        // usun komunikat
        adminMessageGridPage.doGridDeleteFirstRow();
        adminMessageGridPage.waitForGridReload();

        // pokaz usuniete
        adminMessageGridPage.doShowDeleted();

        // wyszukanie na gridzie
        adminMessageGridPage.doGridSearch(desc);

        //czy to ten sam komunikat
        Assert.assertTrue("ERROR: ID usunietego komunikatu się nie zgadza", adminMessageGridPage.isGridColumnContainsText(1, IDText));

        //przejscie do edycji
        AdminMessageEditPage adminMessageEditPage = adminMessageGridPage.doGridEditFirstRow();

        // przywrocenie przyciskiem w edycji (wraca na grid)
        AdminMessageGridPage gridPage = adminMessageEditPage.restoreByButton();

        //ukryj usuniete
        gridPage.doHideDeleted();

        //wyszukaj zgloszenie na gridzie
        gridPage.doGridSearch(desc);

        //czy to ten sam komunikat
        Assert.assertTrue("ERROR: ID przywroconego komunikatu się nie zgadza", adminMessageGridPage.isGridColumnContainsText(1, IDText));

        //komunikat nie powinien miec statusu 'Usuniety'
        Assert.assertFalse("ERROR: Nie powiodlo sie przywrocenie komunikatu (nadal status to Usunięte)", gridPage.isGridColumnContainsText(8, "Usunięty"));

        //skasowanie zgloszenia
        //gridPage.doGridDeleteFirstRow();
    }


}
