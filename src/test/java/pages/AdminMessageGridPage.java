package pages;

import helpers.GridHelper;
import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class AdminMessageGridPage extends LoadableComponent<AdminMessageGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/messages/index";

    private static final String gridTableNameLocator = "mcityMessagesGrid";
    private static final String showDeletedCheckboxLocator = "lbl";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;
    @FindBy(className = showDeletedCheckboxLocator)
    WebElement showDeletedCheckbox;


    public AdminMessageGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        // ten grid jest wykorzystywany w paru miejscach
        PageLoad.validPageTitle(driver, "SmartSite - Lupe - Komunikaty - Lista komunikatów [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(gridTable);
        elementList.add(showDeletedCheckbox);

        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Strona grida Komunikatow w czesci administracyjnej '/mcity/admin/messages/index', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminMessageGridPage ("+driver.getCurrentUrl()+")");
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

    public String getTextFromGridColumn (String columnNumber) {
        return GridHelper.getTextFromColumn(driver, gridTableNameLocator, columnNumber);
    }

    public void doGridDeleteFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Usuń");
        PageLoad.confirmModal(driver, 5);
    }

    public AdminMessageEditPage doGridEditFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Edycja");
        return new AdminMessageEditPage(driver, 0, "edit");
    }

    public void doGridRestoreFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Przywróć");
        PageLoad.confirmModal(driver, 5);
    }

    //checkbox "Pokaz usuniete"
    public void doShowDeleted()
    {
        System.out.println("INFO: Wlączono 'Pokazywanie usuniętych' komunikatów");
        PageLoad.selectTheCheckbox(driver, showDeletedCheckbox, "ACTIVATE");
    }

    public void doHideDeleted()
    {
        System.out.println("INFO: Wylączono 'Pokazywanie usuniętych' komunikatów");
        PageLoad.selectTheCheckbox(driver, showDeletedCheckbox, "DEACTIVATE");
    }

}