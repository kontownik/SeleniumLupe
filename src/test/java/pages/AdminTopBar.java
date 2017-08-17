package pages;

import helpers.MenuHelper;
import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class AdminTopBar extends LoadableComponent<AdminTopBar> {

    private WebDriver driver;

    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";
    private static final String dropdownButtonLocator = "//i[@class='ace-icon fa fa-caret-down']"; //xpath
    private static final String logoutButtonLocator = "//a[@href='/admin/auth/logout']"; //xpath

    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;
    @FindBy (xpath = dropdownButtonLocator)
    WebElement dropdownButton;
    @FindBy (xpath = logoutButtonLocator)
    WebElement logoutButton;

    public AdminTopBar(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(loggedUserPanel);
        elementList.add(dropdownButton);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Gorna belka w trybie '/admin', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminTopBar na stronie ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void logoutByTopBar () {
        dropdownButton.click();
        logoutButton.click();
    }


}