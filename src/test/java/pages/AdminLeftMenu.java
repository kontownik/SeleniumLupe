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

public class AdminLeftMenu extends LoadableComponent<AdminLeftMenu> {

    private WebDriver driver;

    public AdminLeftMenu(WebDriver driver) {
        
        this.driver = driver;
    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Boczne menu w trybie administracyjnym '/admin', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminLeftMenu na stronie ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    //TODO: przechodzenie po menu bocznym

    public boolean isMenuValid(int expectedMenuElements, int timeout, boolean printMenuItems) {
        return MenuHelper.isNavlistCorrect(driver, expectedMenuElements, timeout, printMenuItems);
    }

}