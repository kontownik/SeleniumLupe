package tests;

import org.junit.Test;
import pages.ContactFormPage;

import java.util.Arrays;
import java.util.List;

public class LU28GuestContactFormTest extends BaseTest {

    @Test
    public void guestContactFormTest() throws Exception {

        List<String> params = Arrays.asList("Jan Selenium", "dummydata@dummydata", "testowe pytanie dotyczace dzialania aplikacji Lupe");

        //przejscie na formularz kontaktowy
        ContactFormPage contactFormPage = new ContactFormPage(driver).get();

        //wypelnienie formularza kontaktowego danymi i wyslanie
        contactFormPage.doSendContactForm(params);
    }

}
