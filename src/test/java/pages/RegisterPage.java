package pages;

import helpers.PageLoad;
import helpers.CaptchaHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class RegisterPage extends LoadableComponent<RegisterPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/surfer/index/register";
    private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    List<WebElement> formStandardElementList = new ArrayList<WebElement>();

    private static final String registerFormLocator = "surfer-register-form";
    private static final String nameFieldLocator = "form-field-name";
    private static final String surnameFieldLocator = "form-field-surname";
    private static final String emailFieldLocator = "form-field-email";
    private static final String passwordFieldLocator = "form-field-passwd";
    private static final String passwordVerifyFieldLocator = "form-field-passwd-verify";
    private static final String phoneFieldLocator = "form-field-phone";
    private static final String captchaFieldLocator = "form-field-captcha";
    private static final String captchaQuestionLocator = "formStandardElement-form-field-captcha";
    private static final String acceptCheckboxLocator = "form-field-accepted";
    private static final String submitButtonLocator = "//button[@class=\"btn btn-info btn-raised\"]";

    //formFieldStandard
    private static final String stnameFieldLocator = "formStandardElement-form-field-name";
    private static final String stsurnameFieldLocator = "formStandardElement-form-field-surname";
    private static final String stemailFieldLocator = "formStandardElement-form-field-email";
    private static final String stpasswordFieldLocator = "formStandardElement-form-field-passwd";
    private static final String stpasswordVerifyFieldLocator = "formStandardElement-form-field-passwd-verify";
    private static final String stphoneFieldLocator = "formStandardElement-form-field-phone";

    @FindBy(xpath = pageHeaderLocator)
    WebElement pageHeader;
    @FindBy(id = registerFormLocator)
    WebElement registerForm;
    @FindBy(id = nameFieldLocator)
    WebElement nameField;
    @FindBy(id = surnameFieldLocator)
    WebElement surnameField;
    @FindBy(id = emailFieldLocator)
    WebElement emailField;
    @FindBy(id = passwordFieldLocator)
    WebElement passwordField;
    @FindBy(id = passwordVerifyFieldLocator)
    WebElement passwordVerifyField;
    @FindBy(id = phoneFieldLocator)
    WebElement phoneField;
    @FindBy(id = captchaFieldLocator)
    WebElement captchaField;
    @FindBy(id = captchaQuestionLocator)
    WebElement captchaQuestion;
    @FindBy(id = acceptCheckboxLocator)
    WebElement acceptCheckbox;
    @FindBy (xpath = submitButtonLocator)
    WebElement submitButton;

    //formFieldStandard
    @FindBy(id = stnameFieldLocator)
    WebElement stnameField;
    @FindBy(id = stsurnameFieldLocator)
    WebElement stsurnameField;
    @FindBy(id = stemailFieldLocator)
    WebElement stemailField;
    @FindBy(id = stpasswordFieldLocator)
    WebElement stpasswordField;
    @FindBy(id = stpasswordVerifyFieldLocator)
    WebElement stpasswordVerifyField;
    @FindBy(id = stphoneFieldLocator)
    WebElement stphoneField;

    public RegisterPage(WebDriver driver) {
        
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
        elementList.add(nameField);
        elementList.add(surnameField);
        elementList.add(emailField);
        elementList.add(passwordField);
        elementList.add(passwordVerifyField);
        elementList.add(phoneField);
        elementList.add(captchaField);
        elementList.add(captchaQuestion);
        elementList.add(acceptCheckbox);
        elementList.add(submitButton);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona rejestracji '/surfer/index/register', nie wszystkie elementy sa widoczne i dostepne");
        }

        //List<WebElement> formStandardElementList = new ArrayList<WebElement>();
        formStandardElementList.add(stnameField);
        formStandardElementList.add(stsurnameField);
        formStandardElementList.add(stemailField);
        formStandardElementList.add(stpasswordField);
        formStandardElementList.add(stpasswordVerifyField);
        formStandardElementList.add(stphoneField);

        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");
        System.out.println("INFO: Udalo sie zaladowac RegisterPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkSuccessFormPost () {
        PageLoad.checkSuccessFormPost(driver);
    }

    //imie, nazwisko, email, haslo, telefon
    public RegisterSuccessPage registerAccount(List<String> parametersList) {

        int paramNeedNumber = 5;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: registerAccount() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            this.setName(parametersList.get(0));
        }

        if(parametersList.get(1) != "") {
            this.setSurname(parametersList.get(1));
        }

        if(parametersList.get(2) != "") {
            this.setEmail(parametersList.get(2));
        }

        if(parametersList.get(3) != "") {
            this.setPassword(parametersList.get(3));
            this.setPasswordVerify(parametersList.get(3));
        }

        if(parametersList.get(4) != "") {
            this.setPhone(parametersList.get(4));
        }

        this.setAcceptCheckbox();
        this.setCaptcha(this.solveTextCaptcha());
        PageLoad.moveToElement(driver, pageHeader, submitButton);
        submitButton.click();
        System.out.println("INFO: Wyslano formularz");

        //czy po wyslaniu formularzu nie widac bledow
        this.checkSuccessFormPost();

        //sprawdzenie czy udalo sie przejsc na nowa strone
        if(!driver.getCurrentUrl().contains("status.success")){
            //bledy wypelnienie pol weryfikowane przed POSTem
            if(this.checkFieldsGetErrorClass()) {
                throw new Error("ERROR: Pola formularza zostaly zle wypelnione");
            }
        }

        return new RegisterSuccessPage(driver);
    }

    public void setName (String valueText) {
        nameField.clear();
        nameField.sendKeys(valueText);
    }

    public void setSurname (String valueText) {
        surnameField.clear();
        surnameField.sendKeys(valueText);
    }

    public void setEmail (String valueText) {
        emailField.clear();
        emailField.sendKeys(valueText);
    }

    public void setPassword (String valueText) {
        passwordField.clear();
        passwordField.sendKeys(valueText);
    }

    public void setPasswordVerify (String valueText) {
        passwordVerifyField.clear();
        passwordVerifyField.sendKeys(valueText);
    }

    public void setPhone (String valueText) {
        phoneField.clear();
        phoneField.sendKeys(valueText);
    }

    public void setCaptcha (String valueText) {
        captchaField.clear();
        captchaField.sendKeys(valueText);
    }

    public void setAcceptCheckbox () {
        PageLoad.selectTheCheckbox(driver, acceptCheckbox, "ACTIVATE");
    }

    public String solveTextCaptcha() {
        WebElement captchaText = captchaQuestion.findElement(By.tagName("b"));
        return CaptchaHelper.doTextCaptcha(captchaText.getText());
    }

    public boolean checkFieldsGetErrorClass() {
        return PageLoad.checkFieldsGetErrorClass(driver, formStandardElementList);
    }




}