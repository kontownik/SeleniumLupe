package pages;

import helpers.PageLoad;
import helpers.MenuHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class AdminPage extends LoadableComponent<AdminPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin";
    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";

    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;

    public AdminPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(loggedUserPanel);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            //fail("ERROR: Strona logowania '/admin', nie wszystkie elementy sa widoczne i dostepne");
            throw new Error("ERROR: Strona logowania '/admin', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    //TODO: przechodzenie po menu bocznym
    //TODO: rozwinac LeftMenu i TopBar jako osobne obiekty

    public boolean isMenuValid(int expectedMenuElements, int timeout, boolean printMenuItems) {
        return MenuHelper.isNavlistCorrect(driver, expectedMenuElements, timeout, printMenuItems);
    }

}