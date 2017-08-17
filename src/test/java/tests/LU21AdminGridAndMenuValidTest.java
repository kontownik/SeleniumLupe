package tests;

import org.junit.Test;
import pages.*;

public class LU21AdminGridAndMenuValidTest extends BaseTest {

    @Test
    public void adminGridAndMenuValidTest() throws Exception {

        System.out.println("Rozpoczeto test: "+getClass().toString()+"."+name.getMethodName());

        //logowanie sie do systemu
        AdminLoginPage loginPage = new AdminLoginPage(driver).get();
        AdminPage adminPage = loginPage.correctLogin(properties.getPropValue("admin_login"), properties.getPropValue("admin_password")).get();

        //sprawdzenie poprawnosci bocznego menu (z wypisaniem menuitemow na konsole)
        adminPage.isMenuValid(36, 45, true);

        //sprawdzenie gridów
        //Lupe -> Lista zgłoszeń
        AdminReportGridPage reportGridPage = new AdminReportGridPage(driver).get();
        reportGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        // Lupe -> Przypisane do mnie
        AdminAssignedReportGridPage assignedReportGridPage = new AdminAssignedReportGridPage(driver).get();
        assignedReportGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        // Lupe -> Komunikaty -> Lista komunikatów
        AdminMessageGridPage adminMessageGridPage = new AdminMessageGridPage(driver).get();
        adminMessageGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        //Administracja -> Użytkownicy -> Lista użytkowników
        AdminPersonGridPage adminPersonGridPage = new AdminPersonGridPage(driver).get();
        adminPersonGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        //Administracja -> Witryny -> Lista witryn
        AdminProjectGridPage adminProjectGridPage = new AdminProjectGridPage(driver).get();
        adminProjectGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        //Administracja -> Słowniki -> Lista
        AdminDictionaryGridPage adminDictionaryGridPage = new AdminDictionaryGridPage(driver).get();
        adminDictionaryGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));
        //Administracja -> Przeglądaj logi
        AdminSystemLogGridPage adminSystemLogGridPage = new AdminSystemLogGridPage(driver).get();
        adminSystemLogGridPage.doGridSearch(properties.getPropValue("grid_search_test_input"));

        //przejscie na strone Podsumowanie
        AdminSummaryPage summaryPage = new AdminSummaryPage(driver).get();

        //sprawdzenie czy wykresy sie wyswietlily
        summaryPage.verifyCharts(15);
    }

}
