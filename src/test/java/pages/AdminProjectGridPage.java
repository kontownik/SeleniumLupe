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

public class AdminProjectGridPage extends LoadableComponent<AdminProjectGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin/project/index";

    private static final String gridTableNameLocator = "projectGrid";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;


    public AdminProjectGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Administracja - Witryny - Lista witryn [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida witryn '/admin/project/index', nie wszystkie elementy sa widoczne i dostepne");
        }

        GridHelper.waitForGridReload(driver,gridTableNameLocator, 15);
        if(GridHelper.isDataTablesError()) {
            throw new Error("ERROR: Wyswietlil sie blad DataTables!");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminProjectGridPage ("+driver.getCurrentUrl()+")");
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