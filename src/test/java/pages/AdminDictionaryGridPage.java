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

public class AdminDictionaryGridPage extends LoadableComponent<AdminDictionaryGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin/dictionary";

    private static final String gridTableNameLocator = "dictionaryGrid";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;


    public AdminDictionaryGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Administracja - Słowniki - Lista [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida slownikow '/admin/dictionary', nie wszystkie elementy sa widoczne i dostepne");
        }

        GridHelper.waitForGridReload(driver,gridTableNameLocator, 15);
        if(GridHelper.isDataTablesError()) {
            throw new Error("ERROR: Wyswietlil sie blad DataTables!");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminDictionaryGridPage ("+driver.getCurrentUrl()+")");
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
    public void doGridEditFirstRow() {
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Edycja");
    }

}