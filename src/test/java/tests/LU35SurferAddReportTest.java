package tests;

import helpers.PageLoad;
import org.junit.Assert;
import org.junit.Test;
import pages.*;

import java.util.Arrays;
import java.util.List;

public class LU35SurferAddReportTest extends BaseTest {

    @Test
    public void suferAddReportTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        String desc = "po burzy na chodnikach zalegają gałęzie";
        List<String> params = Arrays.asList("true", "Porządek i śmieci", desc, "", "true");

        SurferLoginPage loginPage = new SurferLoginPage(driver).get();
        FacebookProviderPage facebookProviderPage = loginPage.surferFacebookLogin().get();

        //zalogowanie sie do strony za pomoca facebooka
        PublicIncidentsMapPage publicIncidentsMapPage = facebookProviderPage.doFacebookLogin(properties.getPropValue("facebook_login"),properties.getPropValue("facebook_password")).get();

        //przycisk dodaj zgloszenie
        PublicReportAddPage publicReportAddPage = publicIncidentsMapPage.useAddButton().get();

        //potwierzenie czy user jest zalogowany
        Assert.assertTrue("ERROR: Uzytkownik nie jest zalogowany (surfer)", publicReportAddPage.isSurfer());

        //formularz zgloszenia (dodawanie zgloszenia)
        PublicReportGridPage publicReportGridPage = publicReportAddPage.addReportBySurfer(params).get();

        //weryfikacja czy wyslanie formularza odbylo sie bez bledow
        publicReportGridPage.checkSuccessFormPost();

        //przejscie do "Moje zgloszenia" widok publiczny
        PublicMyReportGridPage myReportsPage = new PublicMyReportGridPage(driver);

        //wyszukanie zgloszenia na gridzie
        myReportsPage.doGridSearch(desc);

        Thread.sleep(10000);

        //weryfikacja tresci i statusu
        myReportsPage.isGridColumnContainsText(2, desc);
        myReportsPage.isGridColumnContainsText(5, "Nowe");

        //próba przejścia do części administracyjnej (bez uprawnien)
        String adminPageUrl = PageLoad.getProjectUrl()+"/admin";
        driver.get(adminPageUrl);
        Assert.assertFalse("ERROR: Udało się przejść na stronę administracyjna mimo braku uprawnien ", driver.getCurrentUrl().equals(adminPageUrl));
        System.out.println("INFO: Próba przejścia na stronę "+adminPageUrl+" skończyła się przekierowaniem na stronę "+driver.getCurrentUrl());
    }

}
