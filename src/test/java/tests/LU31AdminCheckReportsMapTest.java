package tests;

import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminReportMapPage;

public class LU31AdminCheckReportsMapTest extends BaseTest {

    @Test
    public void adminCheckReportsMapTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone edycji zgloszenia
        AdminReportMapPage reportMapPage = new AdminReportMapPage(driver).get();
        //Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header", reportMapPage.getPageHeaderText().contains("Mapa zgłoszeń")); //TODO: zmienic na poprawne

        //Ilosc markerow (nie da sie zweryfikowac poprawnosci, jedno zgloszenie moze miec wiecej niz 1 marker lub wcale)
        //reportMapPage.calculateMarkerCount();
        //System.out.println("INFO: Markerow znaleziono: "+reportMapPage.getMarkerCount());

        //sprawdzenie "dymka" po kliknięciu w marker
        reportMapPage.openMarkerDetails();

        //sprawdzenie pól w "Podgląd zgłoszenia"
        reportMapPage.verifyReportPreviewModal(10);
    }

}
