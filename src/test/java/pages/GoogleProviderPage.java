package pages;

import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class GoogleProviderPage extends LoadableComponent<GoogleProviderPage> {

    private WebDriver driver;

    private static final String identifierLocator = "identifier";
    private static final String nextButtonLocator = "//span[text()='Dalej']";
    private static final String passwordLocator = "//div[text()='Wpisz hasło']";
    private static final String profileIdentifierLocator = "profileIdentifier"; //div po wybraniu konta

    @FindBy(name = identifierLocator)
    WebElement emailInput;
    @FindBy(xpath = nextButtonLocator)
    WebElement nextButton;
    @FindBy(xpath = passwordLocator)
    WebElement passwordInput;
    @FindBy(id = profileIdentifierLocator)
    WebElement profileIdentifier;

    public GoogleProviderPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(emailInput);
        elementList.add(nextButton);

        PageLoad.validPageTitle(driver, "Logowanie – Konta Google");
        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona logowania 'Google', nie wszystkie elementy sa widoczne i dostepne");
        }

        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");

        System.out.println("INFO: Udalo sie zaladowac GoogleProviderPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public PublicIncidentsMapPage doGoogleLogin(String email, String password)
    {
        emailInput.sendKeys(email);
        nextButton.click();
        WebElement elementPassword = new WebDriverWait(this.driver, 10).until(ExpectedConditions.elementToBeClickable(By.name("password")));
        elementPassword.sendKeys(password);
        WebElement elebutton= new WebDriverWait(this.driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath(nextButtonLocator)));
        elebutton.click();

        //TODO: przepisać oczekiwanie na przeładowanie sie formularza na cos lepszego
        try {
            Thread.sleep(7000);
        }catch (InterruptedException e) {}

        return new PublicIncidentsMapPage(driver);
    }

}