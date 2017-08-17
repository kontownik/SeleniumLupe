package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.PublicMessagesGridPage;

public class LU52GuestCheckMessagesListTest extends BaseTest {

    @Test
    public void guestCheckMessagesListTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());
        String desc = "Smog nad miastem";

        // publiczny grid komunikatow
        PublicMessagesGridPage publicMessagesGridPage = new PublicMessagesGridPage(driver).get();

        //wyszukanie byle jakiego komunikatu
        publicMessagesGridPage.doGridSearch(desc);
        Assert.assertTrue("ERROR: Nie znaleziono tekstu", publicMessagesGridPage.isGridColumnContainsText(3, desc));
    }

}
