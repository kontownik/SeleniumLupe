package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminMessageEditPage;
import pages.AdminMessageGridPage;

public class LU45AdminDeleteMessageTest extends BaseTest {
    private static String desc;

    @Test
    public void adminDeleteMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: " + getClass().toString() + "." + name.getMethodName());

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na grid administracyjny komunikatow
        AdminMessageGridPage adminMessageGridPage = new AdminMessageGridPage(driver);

        Thread.sleep(7000);

        //pobieranie wartosci z grida
        adminMessageGridPage.waitForGridReload();
        desc = adminMessageGridPage.getTextFromGridColumn("4");
        String IDText = adminMessageGridPage.getTextFromGridColumn("1");
        System.out.println("Znaleziono ID:"+IDText);
        //usuniecie potencjalnego 'Czytaj więcej' ktore nie jest trescia mimo ze jest w polu tresci
        desc = desc.replace(" Czytaj więcej","");

        //przejscie do edycji
        AdminMessageEditPage adminMessageEditPage = adminMessageGridPage.doGridEditFirstRow();

        // skasowanie przyciskiem z formularza edycji (wraca na grid)
        AdminMessageGridPage gridPage = adminMessageEditPage.deleteByButton();

        // pokaz usuniete
        gridPage.doShowDeleted();

        // wyszukanie na gridzie
        gridPage.doGridSearch(desc);

        //czy to ten sam komunikat
        Assert.assertTrue("ERROR: ID usunietego komunikatu się nie zgadza", gridPage.isGridColumnContainsText(1, IDText));

        // przywroc komunikat
        gridPage.doGridRestoreFirstRow();
        gridPage.waitForGridReload();

        //czy to ten sam komunikat
        Assert.assertTrue("ERROR: ID przywroconego komunikatu się nie zgadza", gridPage.isGridColumnContainsText(1, IDText));

        //komunikat nie powinien miec statusu 'Usuniety'
        Assert.assertFalse("ERROR: Nie powiodlo sie przywrocenie komunikatu (nadal status to Usunięte)", gridPage.isGridColumnContainsText(8, "Usunięty"));
    }


}
