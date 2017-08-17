package pages;

import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class RegisterSuccessPage extends LoadableComponent<RegisterSuccessPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/surfer/index/register/status.success#";
    private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    private static final String alertSuccessLocator = "//*[@class=\"alert alert-success\"]";

    @FindBy(xpath = pageHeaderLocator)
    WebElement pageHeader;
    @FindBy(xpath = alertSuccessLocator)
    WebElement alertSuccess;

    public RegisterSuccessPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "Rejestracja konta - Białystok");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(pageHeader);
        elementList.add(alertSuccess);

        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Strona po pomyslnej rejestracji '/surfer/index/register/status.success#', nie wszystkie elementy sa widoczne");
        }

        System.out.println("INFO: Udalo sie zaladowac RegisterSuccessPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

}