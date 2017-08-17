package tests;

import org.junit.Test;
import pages.PublicIncidentsMapPage;
import pages.PublicReportAddPage;

public class LU32GuestCheckIncidentsMapTest extends BaseTest {

    @Test
    public void guestCheckIncidentsMapTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //publiczna mapa zgloszen
        PublicIncidentsMapPage publicIncidentsMapPage = new PublicIncidentsMapPage(driver).get();

        //sprawdzenie "dymka" po kliknięciu w marker
        publicIncidentsMapPage.openMarkerDetails();

        //sprawdzenie pól w "Podgląd zgłoszenia"
        publicIncidentsMapPage.verifyReportPreview(10);

        //klikniecie w przycisk nowe zgloszenie
        PublicReportAddPage publicReportAddPage = publicIncidentsMapPage.useAddButton();
    }

}
