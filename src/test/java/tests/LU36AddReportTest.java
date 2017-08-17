package tests;

import org.junit.*;
import org.junit.runners.MethodSorters;
import pages.*;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LU36AddReportTest extends BaseTest {
    private static int reportID = 973;
    private static Timestamp timestamp;

    @Test
    public void T_01_ADMIN_addNewReportTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //parametry nowego zgloszenia
        reportID = 0;
        timestamp = new Timestamp(System.currentTimeMillis());
        List<String> params = Arrays.asList("Porządek i śmieci", "Wydarzenia", "zgloszenie testowe do usuniecia "+timestamp, "112/2017", "Nowe", "", "", "","","","","","point-center");

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone edycji zgloszenia
        AdminReportEditPage addReportPage = new AdminReportEditPage(driver, reportID).get();
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header", addReportPage.getPageHeaderText().contains("Dodaj zgłoszenie"));

        //dodawanie zgloszenia (ta sama akcja co : edycja zgloszenia)
        AdminReportEditPage successAddReportPage = addReportPage.editDataInReport(params);

        //pobieranie ID nowo dodanego zgloszenia
        reportID = successAddReportPage.getPageID();
        System.out.println("INFO: ID dodanego zgloszenia: "+reportID);

        //sprawdzenie czy nie ma bledow
        successAddReportPage.checkSuccessFormPost(); //sprawdzenie czy strona nie ma komunikatu błędu
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",successAddReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //przejscie na grid zgloszen
        AdminReportGridPage reportGrid = new AdminReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie administracyjnym
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(7, "Nowe"));

    }

    @Test
    public void T_02_ADMIN_editReportConfirmTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        List<String> params = Arrays.asList("", "", "", "", "Potwierdzone", "dummydata@dummydata", "dummydata", "zgloszenie zostalo przypisane","","2017-12-12","","","");

        //asercja czy jest co edytowac
        Assert.assertTrue("ERROR: Brak okreslonego ID zgloszenia (ReportID: "+reportID+") do edycji. Wykonaj test dodajacy zgloszenie",reportID != 0);

        //przejscie na strone edycji zgloszenia
        System.out.println("INFO: Przejscie do zgloszenia ID: "+reportID);
        AdminReportEditPage editReportPage = new AdminReportEditPage(driver, reportID).get();
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",editReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //edycja zgloszenia
        AdminReportEditPage successAddReportPage = editReportPage.editDataInReport(params);

        //czy akcja zakonczyla sie sukcesem
        successAddReportPage.checkSuccessFormPost(); //sprawdzenie czy strona nie ma komunikatu błędu
        Assert.assertTrue(successAddReportPage.isInChangelog("Zmieniono Status z Nowe na Potwierdzone"));
        Assert.assertTrue(successAddReportPage.isInChangelog("Zmieniono Przypisane do z brak na dummydata"));
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",successAddReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //przejscie na grid zgloszen administracyjny
        AdminReportGridPage reportGrid = new AdminReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie administracyjnym
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(7, "Potwierdzone"));

        //przejscie na grid zgloszen publiczny
        PublicReportGridPage publicReportGrid = new PublicReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie publicznym
        publicReportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", publicReportGrid.isGridColumnContainsText(2, "zgloszenie testowe do usuniecia "+timestamp));
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", publicReportGrid.isGridColumnContainsText(5, "Potwierdzone"));

        //this.tearDown();
    }

    @Test
    public void T_03_ADMIN_editAssignedReportDoneTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        List<String> params = Arrays.asList("", "", "", "", "Wykonane", "", "", "zgloszenie zostalo wykonane","","","","","");

        //asercja czy dane sa prawidlowe
        Assert.assertTrue("ERROR: Brak okreslonego ID zgloszenia (ReportID: "+reportID+") do edycji. Wykonaj test dodajacy zgloszenie",reportID != 0);
        Assert.assertTrue("ERROR: Brak okreslonego Timestampa zgloszenia ("+timestamp+")",timestamp != null);

        ///przejscie na grid MOICH zgloszen
        AdminAssignedReportGridPage reportGrid = new AdminAssignedReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));

        //akcja edycji za pomoca ikonki, potwierdzenie modala
        AdminReportEditPage editReportPage = reportGrid.doGridEditFirstRow();
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",editReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //edycja zgloszenia
        AdminReportEditPage successAddReportPage = editReportPage.editDataInReport(params);

        //czy akcja zakonczyla sie sukcesem
        successAddReportPage.checkSuccessFormPost(); //sprawdzenie czy strona nie ma komunikatu błędu
        successAddReportPage.isInChangelog("Zmieniono Status z Potwierdzone na Wykonane");
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",successAddReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

    }

    @Test
    public void T_04_ADMIN_editReportAddComment() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //asercja czy jest co edytowac
        Assert.assertTrue("ERROR: Brak okreslonego ID zgloszenia (ReportID: "+reportID+") do edycji. Wykonaj test dodajacy zgloszenie",reportID != 0);

        //przejscie na strone edycji zgloszenia
        System.out.println("INFO: Przejscie do zgloszenia ID: "+reportID);
        AdminReportEditPage editReportPage = new AdminReportEditPage(driver, reportID).get();
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",editReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //dodanie komentarza do gormularza
        AdminReportEditPage successEditReportPage = editReportPage.addComment("to jest automatyczny komentarz do zgloszenia ID "+reportID);

        //czy akcja zakonczyla sie sukcesem
        successEditReportPage.checkSuccessFormPost(); //sprawdzenie czy strona nie ma komunikatu błędu
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",successEditReportPage.getPageHeaderText().contains("Zgłoszenie #"+reportID));

        //this.tearDown();
    }

    // Kasowanie przyciskiem usun ze strony edycyjnej
    @Test
    public void T_05_ADMIN_deleteReportByEditPage() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //asercja czy jest co kasowac
        Assert.assertTrue("ERROR: Brak okreslonego ID zgloszenia (ReportID: "+reportID+") do edycji. Wykonaj test dodajacy zgloszenie",reportID != 0);

        //formularz edycji zgloszen
        AdminReportEditPage editReportPage = new AdminReportEditPage(driver, reportID).get();

        //akcja usuniecia zgloszenia przyciskiem w formularzu
        AdminReportGridPage deletedReportPage = editReportPage.deleteByButton();

        //czy akcja zakonczyla sie sukcesem
        deletedReportPage.checkActionSuccess();

        //przejscie na grid zgloszen administracyjny
        AdminReportGridPage reportGrid = new AdminReportGridPage(driver).get();

        //akcja przelaczenia trybu z widokiem na usuniete zgloszenia
        reportGrid.doShowDeleted();

        //akcja wyszukania zgloszenia na gridzie
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));
        Assert.assertTrue("ERROR: Zgloszenie nie ma statusu 'Usuniete'", reportGrid.isGridColumnContainsText(7, "Usunięte"));

        //przejscie na grid zgloszen publiczny
        PublicReportGridPage publicReportGrid = new PublicReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie publicznym
        publicReportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Wyszukiwanie powinno zwrocic 'Brak danych' (zgloszenie jest usuniete)", publicReportGrid.isGridColumnContainsText(1, "Brak danych"));

    }

    //przywracanie strony z grida
    @Test
    public void T_06_ADMIN_restoreReportByGrid() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //asercja czy dane sa prawidlowe
        Assert.assertTrue("ERROR: Brak okreslonego Timestampa zgloszenia ("+timestamp+")",timestamp != null);

        //przejscie na grid zgloszen administracyjny
        AdminReportGridPage reportGrid = new AdminReportGridPage(driver).get();

        //akcja przelaczenia trybu z widokiem na usuniete zgloszenia
        reportGrid.doShowDeleted();

        //akcja wyszukania zgloszenia na gridzie administracyjnym
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));
        Assert.assertTrue("ERROR: Zgloszenie nie ma statusu 'Usuniete'", reportGrid.isGridColumnContainsText(7, "Usunięte"));

        //akcja przywrocenia za pomoca ikonki, potwierdzenie modala
        reportGrid.doGridRestoreFirstRow();

        //czy akcja zakonczyla sie sukcesem czy zgloszenie jest niewidoczne na gridzie?)
        reportGrid.waitForGridReload();

        //czy akcja zakonczyla sie sukcesem
        Assert.assertFalse("ERROR: Zgloszenie ma nadal status 'Usuniete'", reportGrid.isGridColumnContainsText(7, "Usunięte"));
    }

    //Kasowanie strony z grida
    @Test
    public void T_07_ADMIN_deleteReportByGrid() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //asercja czy dane sa prawidlowe
        Assert.assertTrue("ERROR: Brak okreslonego Timestampa zgloszenia ("+timestamp+")",timestamp != null);

        //przejscie na grid zgloszen administracyjny
        AdminReportGridPage reportGrid = new AdminReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie administracyjnym
        reportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Nie udalo sie znalesc tekstu", reportGrid.isGridColumnContainsText(4, "zgloszenie testowe do usuniecia "+timestamp));

        //akcja usuniecia za pomoca ikonki, potwierdzenie modala
        reportGrid.doGridDeleteFirstRow();

        //czy akcja zakonczyla sie sukcesem czy zgloszenie jest niewidoczne na gridzie?)
        reportGrid.waitForGridReload();
        Assert.assertFalse("ERROR: Tekst jest dalej widoczny na gridzie", reportGrid.isGridColumnContainsText(1, "zgloszenie testowe do usuniecia "+timestamp));

        //przejscie na grid zgloszen publiczny
        PublicReportGridPage publicReportGrid = new PublicReportGridPage(driver).get();

        //akcja wyszukania zgloszenia na gridzie publicznym
        publicReportGrid.doGridSearch("zgloszenie testowe do usuniecia "+timestamp);

        //czy akcja zakonczyla sie sukcesem (czy zgloszenie jest widoczne na gridzie?)
        Assert.assertTrue("ERROR: Wyszukiwanie powinno zwrocic 'Brak danych' (zgloszenie jest usuniete)", publicReportGrid.isGridColumnContainsText(1, "Brak danych"));

    }

}
