package tests;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import pages.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LU16AdminEditMessageTest extends BaseTest {

    @Test
    public void adminEditMessageTest() throws Exception {

        System.out.println("Rozpoczeto test: " + getClass().toString() + "." + name.getMethodName());

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //przejscie na nipubliczny grid komunikatow
        AdminMessageGridPage adminMessageGridPage = new AdminMessageGridPage(driver);
        //sortuje komunikaty tylko tworzone przez testy
        adminMessageGridPage.doGridSearch("Wydarzenie wygenerowane automatycznie");

        //pobieram z grida
        String priority = adminMessageGridPage.getTextFromGridColumn("7");
        String status = adminMessageGridPage.getTextFromGridColumn("8");
        String newPriority = "";
        String newStatus = "";
        String attachment = "report.jpg";

        //zaleznosci priorytetow
        if(priority.equals("Normalny")) {
            newPriority = "Ważny";
        }
        else if (priority.equals("Ważny")) {
            newPriority = "Alarmowy";
        }
        else if (priority.equals("Alarmowy")) {
            newPriority = "Promocja";
        }
        else if (priority.equals("Promocja")) {
            newPriority = "Normalny";
        }

        //zaleznosci statusow
        if(status.equals("Nowy")) {
            newStatus = "Opublikowany";
        }
        else if (status.equals("Opublikowany")) {
            newStatus = "Archiwalny";
        }
        else if (status.equals("Archiwalny")) {
            newStatus = "Nowy";
        }

        //generowanuie daty dzisiejszej i jutrzejszej
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar cal = Calendar.getInstance();
        dateFormat.setCalendar(cal);
        String todayDateText = dateFormat.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String tomorrowDateText = dateFormat.format(cal.getTime());

        //adres (lub marker), kategoria, tytul, opis, adresURL, data publikacji, data wygasniecia, status, waznosc, zalacznik
        List<String> params = Arrays.asList("point-center", "Wydarzenia", "Tytuł testowy komunikatu", "Opis testowy komunikatu", "http://lupe.bit-sa.pl", todayDateText, tomorrowDateText, newStatus, newPriority, "/"+attachment);

        //ikona edycji z gridu komunikatow
        AdminMessageEditPage adminMessageEditPage = adminMessageGridPage.doGridEditFirstRow();

        //id strony
        int pageID = adminMessageEditPage.getPageID();

        //edycja formularza komunikatu
        adminMessageEditPage.editMessage(params);

        //Thread.sleep(5000);

        //przejscie do edytowanego przed chwila zgloszenia
        AdminMessageEditPage editedMessagePage = new AdminMessageEditPage(driver, pageID, "edit");

        //Thread.sleep(5000);

        //sprawdzenie historii zmian
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Status'", editedMessagePage.isInChangelog("Zmieniono Status z "+status+" na "+newStatus));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Priorytet'", editedMessagePage.isInChangelog("Zmieniono Priorytet z "+priority+" na "+newPriority));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Załącznik'", editedMessagePage.isInChangelog("Zmieniono Załącznik z brak na "+attachment));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Data publikacji'", editedMessagePage.isInChangelog("Zmieniono Data od "));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Data wygaśnięcia'", editedMessagePage.isInChangelog("Zmieniono Data do "));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Adres URL'", editedMessagePage.isInChangelog("Zmieniono Adres URL"));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Tytul'", editedMessagePage.isInChangelog("Zmieniono Tytuł"));
        Assert.assertTrue("Changelog nie wyświetlił zmiany pola 'Opis'", editedMessagePage.isInChangelog("Zmieniono Opis"));

        //sprawdzenie poprawnosci miniatury
        editedMessagePage.isAttachmentPresent();

        //wylogowanie sie z wykorzystaniem gornego paska
        AdminTopBar adminTopBar = new AdminTopBar(driver);
        adminTopBar.logoutByTopBar();
    }

}
