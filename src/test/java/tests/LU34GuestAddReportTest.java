package tests;

import org.junit.Test;
import pages.PublicIncidentsMapPage;
import pages.PublicReportAddPage;
import pages.PublicReportGridPage;

import java.util.Arrays;
import java.util.List;

public class LU34GuestAddReportTest extends BaseTest {

    @Test
    public void guestAddReportTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        String desc = "zniszczony chodnik i odkryta studzienka kanalizacyjna";
        List<String> params = Arrays.asList("true", "Zagrożenie bezpieczeństwa", desc, "", "");
        //przejscie do strony ze zgloszeniami
        PublicIncidentsMapPage publicIncidentsMapPage = new PublicIncidentsMapPage(driver).get();

        //przycisk dodaj zgloszenie
        PublicReportAddPage publicReportAddPage = publicIncidentsMapPage.useAddButton().get();

        //formularz zgloszenia (dodawanie zgloszenia)
        PublicReportGridPage publicReportGridPage = publicReportAddPage.addReportBySurfer(params).get();

        //weryfikacja czy wyslanie formularza odbylo sie bez bledow
        publicReportGridPage.checkSuccessFormPost();
    }

}
