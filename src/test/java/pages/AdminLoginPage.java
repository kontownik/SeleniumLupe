package pages;

import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class AdminLoginPage extends LoadableComponent<AdminLoginPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin";

    private static final String emailInputLocator = "email";
    private static final String passwordInputLocator = "password";
    private static final String submitButtonLocator = "submit";

    @FindBy (id = "authForm")
    WebElement loginForm;
    @FindBy (name = emailInputLocator)
    WebElement emailInput;
    @FindBy (name = passwordInputLocator)
    WebElement passwordInput;
    @FindBy (name = submitButtonLocator)
    WebElement submitButton;
    @FindBy (id = "btn-login-local")
    WebElement localLoginButton;

    public AdminLoginPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Administracja");

        //wlaczone logowanie SSO i logowanie lokalne
       try {
            if(localLoginButton.isDisplayed() && localLoginButton.isEnabled()){
                localLoginButton.click();
            }
       } catch (NoSuchElementException e) {
           System.out.println("INFO: Logowanie SSO wyłączone");
       }

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(emailInput);
        elementList.add(passwordInput);
        elementList.add(submitButton);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona logowania '/admin', nie wszystkie elementy sa widoczne i dostepne");
        }
        if(!PageLoad.myElementIsClickable(this.driver, By.name(emailInputLocator), 5)) {
            throw new Error("ERROR: Pole 'email' jest nieklikalne");
        }
        if(!PageLoad.myElementIsClickable(this.driver, By.name(passwordInputLocator), 5)) {
            throw new Error("ERROR: Pole 'password' jest nieklikalne");
        }
        if(!PageLoad.myElementIsClickable(this.driver, By.name(submitButtonLocator), 5)) {
            throw new Error("ERROR: Przycisk 'submit' jest nieklikalny");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminLoginPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }
    
    public AdminLoginErrorPage incorrectLogin(String username, String password) {

        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
        return new AdminLoginErrorPage(driver);
    }
    
    public AdminPage correctLogin(String username, String password) {

        emailInput.sendKeys(username);
        passwordInput.sendKeys(password);
        submitButton.click();
        return new AdminPage(driver);
    }

}