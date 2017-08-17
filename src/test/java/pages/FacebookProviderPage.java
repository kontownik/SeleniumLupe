package pages;

import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class FacebookProviderPage extends LoadableComponent<FacebookProviderPage> {

    private WebDriver driver;

    private static final String emailInputLocator = "email";
    private static final String passwordInputLocator = "pass";
    private static final String loginFormLocator = "login_form";

    @FindBy(name = emailInputLocator)
    WebElement emailInput;
    @FindBy(name = passwordInputLocator)
    WebElement passwordInput;
    @FindBy(id = loginFormLocator)
    WebElement loginForm;

    public FacebookProviderPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(emailInput);
        elementList.add(passwordInput);

        PageLoad.validPageTitle(driver, "Zaloguj się do Facebooka | Facebook");
        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona logowania 'Facebook', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac FacebookProviderPage (...)");
    }

    @Override
    protected void load() {
    }

    public PublicIncidentsMapPage doFacebookLogin(String email, String password)
    {

        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginForm.submit();

        return new PublicIncidentsMapPage(driver);
    }

}