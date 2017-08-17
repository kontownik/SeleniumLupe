package pages;

import helpers.CaptchaHelper;
import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class ContactFormPage extends LoadableComponent<ContactFormPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/pl/pomoc/kontakt.html";
    private static final String contactFormLocator = "ContactForm";
    private static final String fullnameFieldLocator = "form-field-fullname";
    private static final String emailFieldLocator = "form-field-email";
    private static final String contentFieldLocator = "form-field-content";
    private static final String mathEquationFieldLocator = "captcha-math"; //by classname
    private static final String captchaInputFieldLocator = "mathCaptcha"; //by name

    @FindBy(id = contactFormLocator)
    WebElement contactForm;
    @FindBy(id = fullnameFieldLocator)
    WebElement fullnameField;
    @FindBy(id = emailFieldLocator)
    WebElement emailField;
    @FindBy(id = contentFieldLocator)
    WebElement contentField;
    @FindBy(className = mathEquationFieldLocator)
    WebElement mathEquationField;
    @FindBy(name = captchaInputFieldLocator)
    WebElement captchaInputField;


    public ContactFormPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "Kontakt - Białystok");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(fullnameField);
        elementList.add(emailField);
        elementList.add(contentField);
        elementList.add(captchaInputField);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona z formularzem kontaktowym '/pl/pomoc/kontakt.html', nie wszystkie elementy sa widoczne");
        }

        System.out.println("INFO: Udalo sie zaladowac ContactFormPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkSuccessFormPost () {
        PageLoad.checkSuccessFormPost(driver);
    }

    //fullname, email, content
    public ContactFormPage doSendContactForm(List<String> params) {
        int paramNeedNumber = 3;
        if(params.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: doSendContactForm() potrzebuje "+paramNeedNumber+" parametrow wyslano "+params.size());
        }

        setFullname(params.get(0));
        setEmail(params.get(1));
        setContent(params.get(2));
        setCaptchaInput(this.solveMathCaptcha(mathEquationField.getText()));

        contactForm.submit();
        System.out.println("INFO: Wyslano formularz");

        //czy po wyslaniu formularzu nie widac bledow
        this.checkSuccessFormPost();

        return new ContactFormPage(driver);
    }

    //set
    public void setFullname(String valueText) {
        fullnameField.clear();
        fullnameField.sendKeys(valueText);
    }

    public void setEmail(String valueText) {
        emailField.clear();
        emailField.sendKeys(valueText);
    }

    public void setContent(String valueText) {
        contentField.clear();
        contentField.sendKeys(valueText);
    }

    public void setCaptchaInput(String valueText) {
        captchaInputField.clear();
        captchaInputField.sendKeys(valueText);
    }

    public String solveMathCaptcha (String equationString) {
        return CaptchaHelper.doMathCaptcha(equationString);
    }
}