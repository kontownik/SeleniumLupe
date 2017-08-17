package tests;

import org.junit.Test;
import pages.PublicMessagesMapPage;

public class LU38GuestCheckMessagesMapTest extends BaseTest {

    @Test
    public void guestCheckMessagesMapTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //publiczna mapa zgloszen
        PublicMessagesMapPage publicMessagesMapPage = new PublicMessagesMapPage(driver).get();

        //sprawdzenie "dymka" po kliknięciu w marker
        publicMessagesMapPage.openMarkerDetails();

        //sprawdzenie pól w "Podgląd zgłoszenia"
        publicMessagesMapPage.verifyReportPreview(10);
    }

}
