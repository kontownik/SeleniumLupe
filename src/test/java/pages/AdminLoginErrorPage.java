package pages;

import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

public class AdminLoginErrorPage extends LoadableComponent<AdminLoginErrorPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin";

    private static final String errorLocator = "//div[contains(@class, 'errors')]/ul/li";
    @FindBy(xpath = errorLocator)
    WebElement errorMessage;

    public AdminLoginErrorPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {
        if(!errorMessage.isDisplayed()){
            throw new Error("ERROR: Strona z bledem sie nie zaladowala prawidlowo");
        }
    }

    @Override
    protected void load() {
    }
    
    public String getErrorText() {
        return errorMessage.getText();
    }
}