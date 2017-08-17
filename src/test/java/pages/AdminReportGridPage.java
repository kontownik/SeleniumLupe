package pages;

import helpers.PageLoad;
import helpers.GridHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class AdminReportGridPage extends LoadableComponent<AdminReportGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/index/list";

    private static final String gridTableNameLocator = "mcityGrid";
    private static final String showDeletedCheckboxLocator = "lbl";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;
    @FindBy(className = showDeletedCheckboxLocator)
    WebElement showDeletedCheckbox;


    public AdminReportGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        // ten grid jest wykorzystywany w paru miejscach
        PageLoad.validPageTitle(driver, "SmartSite - Lupe - Lista zgłoszeń [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida Zgloszen w czesci administracyjnej '/mcity/admin/index/list', nie wszystkie elementy sa widoczne i dostepne");
        }

        GridHelper.waitForGridReload(driver,gridTableNameLocator, 15);
        if(GridHelper.isDataTablesError()) {
            throw new Error("ERROR: Wyswietlil sie blad DataTables!");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminReportGridPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkActionSuccess() {
        PageLoad.checkActionSuccess(driver);
    }

    public void doGridSearch(String searchText) {
        System.out.println("INFO: Proba wyszukania tekstu '"+searchText+"'");
        GridHelper.useGridSearch(driver,  searchText, gridTableNameLocator, 10);
        System.out.println("INFO: Wykonano akcje wyszukiwania na gridzie");
    }

    public void waitForGridReload () {
        GridHelper.waitForGridReload(driver, gridTableNameLocator, 10);
    }

    public boolean isGridColumnContainsText (int columnNumber, String expectedText) {
        return GridHelper.isColumnContainsText(driver, gridTableNameLocator, columnNumber, expectedText);
    }

    public void doGridPreviewFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Podgląd");
    }

    public void doGridDeleteFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Usuń");
        PageLoad.confirmModal(driver, 5);
    }

    public void doGridEditFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Edycja");
    }

    public void doGridRestoreFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Przywróć");
        PageLoad.confirmModal(driver, 5);
    }

    //checkbox "Pokaz usuniete"
    public void doShowDeleted()
    {
        PageLoad.selectTheCheckbox(driver, showDeletedCheckbox, "ACTIVATE");
    }

}