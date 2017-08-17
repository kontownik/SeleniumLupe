package tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pages.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MessageTests extends BaseTest {

    private static int messageID;
    private static Timestamp timestamp;

    @Test
    public void T_01_ADMIN_newMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //parametry nowego komunikatu
        timestamp = new Timestamp(System.currentTimeMillis());
        List<String> params = Arrays.asList("obszar całego miasta", "Wydarzenia", "Wydarzenie wygenerowane automatycznie", "komunikat testowy do usuniecia "+timestamp, "", "", "", "Nowy", "Normalny");

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        AdminPage adminPage = loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone tworzenia nowego Komunikatu
        AdminMessageEditPage addMessagePage = new AdminMessageEditPage(driver, messageID, "add").get();
        //Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header", addMessagePage.getPageHeaderText().contains("Nowy komunikat"));


        Thread.sleep(7000);

    }

    @Test
    public void T_02_ADMIN_editMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //reset drivera z zachowaniem zmiennych wewnatrz klasy
        afterClass();
        beforeClass();

        //parametry edycji komunikatu
        messageID = 252; //DEBUG
        List<String> params = Arrays.asList("obszar całego miasta z wyłączeniem os. Piasta", "", "Wydarzenie wygenerowane automatycznie, opublikowane", "", "", "", "", "Opublikowany", "Promocja", "");

        //asercja czy jest co edytowac
        Assert.assertTrue("ERROR: Brak okreslonego ID komunikatu (MessageID: "+ messageID +") do edycji. Wykonaj test dodajacy komunikat", messageID != 0);

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        AdminPage adminPage = loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na strone dodawania Komunikatu
        AdminMessageEditPage editMessagePage = new AdminMessageEditPage(driver, messageID, "edit").get();
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header", editMessagePage.getPageHeaderText().contains("Edycja komunikatu"));

        //edycja Komunikatu
        AdminMessageEditPage successAddMessagePage = editMessagePage.editMessage(params);

        //sprawdzenie czy nie ma bledow
        successAddMessagePage.checkSuccessFormPost(); //sprawdzenie czy strona nie ma komunikatu błędu
        Assert.assertTrue("ERROR: Nie ma spodziwanego fragmentu tekstu w page-header",successAddMessagePage.getPageHeaderText().contains("Edycja komunikatu #"+ messageID));

    }

}
