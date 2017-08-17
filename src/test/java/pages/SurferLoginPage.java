package pages;

import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class SurferLoginPage extends LoadableComponent<SurferLoginPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/surfer/index/login";

    private static final String emailInputLocator = "form-field-email";
    private static final String passwordInputLocator = "form-field-passwd";
    private static final String submitButtonLocator = "//button[contains(@class, 'btn btn-info btn-raised')]";
    private static final String surferLoginFormLocator = "surfer-login-form";
    private static final String googleButtonLocator = "socialBtnGoogle";
    private static final String facebookButtonLocator = "socialBtnFacebook";

    @FindBy (id = surferLoginFormLocator)
    WebElement surferLoginForm;
    @FindBy (id = emailInputLocator)
    WebElement emailInput;
    @FindBy (id = passwordInputLocator)
    WebElement passwordInput;
    @FindBy (xpath = submitButtonLocator)
    WebElement submitButton;
    @FindBy (id = googleButtonLocator)
    WebElement googleButton;
    @FindBy (id = facebookButtonLocator)
    WebElement facebookButton;


    public SurferLoginPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "Logowanie - Białystok");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(emailInput);
        elementList.add(passwordInput);
        elementList.add(submitButton);
        elementList.add(googleButton);
        elementList.add(facebookButton);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona logowania '/surfer/index/login', nie wszystkie elementy sa widoczne i dostepne");
        }

        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");
        System.out.println("INFO: Udalo sie zaladowac SurferLoginPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }
    
    public AdminPage adminCorrectLogin(String username, String password) {

        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
        return new AdminPage(driver);
    }

    public PublicIncidentsMapPage surferCorrectLogin(String username, String password) {

        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
        return new PublicIncidentsMapPage(driver);
    }


    public GoogleProviderPage surferGoogleLogin() {

        googleButton.click();
        return new GoogleProviderPage(driver);
    }

    public FacebookProviderPage surferFacebookLogin() {

        facebookButton.click();
        return new FacebookProviderPage(driver);
    }

}