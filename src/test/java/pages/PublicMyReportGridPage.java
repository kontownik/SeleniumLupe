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

public class PublicMyReportGridPage extends LoadableComponent<PublicMyReportGridPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/incidents/list/user";

    private static final String gridTableNameLocator = "mcityPublicGrid";

    @FindBy (id = gridTableNameLocator)
    WebElement gridTable;


    public PublicMyReportGridPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        // ten grid jest wykorzystywany w paru miejscach (moje zgloszenia i lista zgloszen)
        PageLoad.validPageTitle(driver, "Lupe - Zgłoszenia - Białystok");

        List<WebElement> elementList = new ArrayList<WebElement>();

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida Zgloszen w czesci publicznej '/mcity/incidents/list/user', nie wszystkie elementy sa widoczne i dostepne");
        }
        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");

        System.out.println("INFO: Udalo sie zaladowac PublicMyReportGridPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkSuccessFormPost () {
        PageLoad.checkSuccessFormPost(driver);
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
        GridHelper.useGridIconAction (driver, gridTableNameLocator, "Szczegóły");
    }

}