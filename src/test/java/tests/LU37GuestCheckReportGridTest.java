package tests;

import org.junit.Test;
import pages.PublicIncidentsMapPage;
import pages.PublicReportGridPage;

public class LU37GuestCheckReportGridTest extends BaseTest {

    @Test
    public void guestCheckReportGridTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        String desc = "awaria rury";
        PublicIncidentsMapPage publicIncidentsMapPage = new PublicIncidentsMapPage(driver);

        //grid publiczny zgloszen
        PublicReportGridPage publicReportGridPage = new PublicReportGridPage(driver);

        //wyszukanie zgloszenia na gridzie
        publicReportGridPage.doGridSearch(desc);

        //weryfikacja tresci i statusu
        publicReportGridPage.isGridColumnContainsText(2, desc);
        publicReportGridPage.isGridColumnContainsText(5, "Potwierdzone");
    }

}
