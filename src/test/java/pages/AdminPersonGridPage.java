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

public class AdminPersonGridPage extends LoadableComponent<AdminPersonGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin/person/index";

    private static final String gridTableNameLocator = "personGrid";
    private static final String addLocalUserButtonLocator = "//a[@href='/admin/person/add']";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;
    @FindBy (xpath = addLocalUserButtonLocator)
    WebElement addLocalUserButton;


    public AdminPersonGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Administracja - Użytkownicy - Lista użytkowników [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(gridTable);
        elementList.add(addLocalUserButton);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida uzytkownikow '/admin/person/index', nie wszystkie elementy sa widoczne i dostepne");
        }

        GridHelper.waitForGridReload(driver,gridTableNameLocator, 15);
        if(GridHelper.isDataTablesError()) {
            throw new Error("ERROR: Wyswietlil sie blad DataTables!");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminPersonGridPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkActionSuccess() {
        PageLoad.checkActionSuccess(driver);
    }

    public void doGridSearch(String searchText) {
        System.out.println("INFO: Proba wyszukania tekstu '"+searchText+"'");
        GridHelper.useGridSearch(driver,  searchText, gridTableNameLocator, 25);
        System.out.println("INFO: Wykonano akcje wyszukiwania na gridzie");
    }

    public void waitForGridReload () {
        GridHelper.waitForGridReload(driver, gridTableNameLocator, 25);
    }

    public boolean isGridColumnContainsText (int columnNumber, String expectedText) {
        return GridHelper.isColumnContainsText(driver, gridTableNameLocator, columnNumber, expectedText);
    }

    public void doGridPreviewFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Podgląd");
    }

    public void doGridEditFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Edycja");
    }

    public void doGridActivateFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Aktywacja");
    }

    public void doGridDeactivateFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Dezaktywacja");
    }

    public AdminPersonAddPage addLocalUserByButton() {
        System.out.println("INFO: Akcja dodawania uzytkownika lokalnego przyciskiem nad gridem ");
        addLocalUserButton.click();
        return new AdminPersonAddPage(driver);
    }

}