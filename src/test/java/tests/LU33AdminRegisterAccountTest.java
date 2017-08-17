package tests;

import org.junit.Assert;
import org.junit.Test;
import pages.AdminLoginPage;
import pages.AdminPersonAddPage;
import pages.AdminPersonGridPage;;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class LU33AdminRegisterAccountTest extends BaseTest {

    @Test
    public void adminRegisterAccountTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        String clearTimestamp = new Timestamp(System.currentTimeMillis()).toString();
        clearTimestamp = clearTimestamp.replace(":","");
        clearTimestamp = clearTimestamp.replace(".","");
        clearTimestamp = clearTimestamp.replace(" ","");
        String newAccountEmail = "dummydata@dummydata";

        //imie, nazwisko, email
        List<String> params = Arrays.asList("Jan", "Kowalski", newAccountEmail);
        //aktywne: active/inactive, generowane: active/inactive, haslo (jezeli puste to automatyczne generowanie)
        List<String> settingsParams = Arrays.asList("ACTIVATE", "ACTIVATE", "Tester123");
        //instytucja, projekt, rola
        List<String> privsParams = Arrays.asList("dummydata", "Białystok", "Internauta");

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na grida uzytkownikow
        AdminPersonGridPage personGridPage = new AdminPersonGridPage(driver);

        //dodanie lokalnego uzytkownika przez przycisk
        AdminPersonAddPage addPersonPage = personGridPage.addLocalUserByButton();

        //zakladka 'Dane'
        addPersonPage.changeTabToDescriptions();
        addPersonPage.setValuesToDescriptions(params);

        //zakladka 'Ustawienia'
        addPersonPage.changeTabToSettings();
        addPersonPage.setValuesToSettings(settingsParams);

        // zakladka 'Uprawnienia'
        addPersonPage.changeTabToPrivileges();
        addPersonPage.setMainPrivilege(privsParams);

        //potwierdzenie formularza (przejscie na grid)
        AdminPersonGridPage gridAfterAdd = addPersonPage.submitForm();

        //wyszukanie uzytkownika na gridzie za pomoca maila
        gridAfterAdd.doGridSearch(newAccountEmail);
        Assert.assertTrue("ERROR: Nie znaleziono uzytkownika na gridzie", gridAfterAdd.isGridColumnContainsText(3,newAccountEmail));
    }

}
