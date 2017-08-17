package pages;

import helpers.GridHelper;
import helpers.PageLoad;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class AdminPersonAddPage extends LoadableComponent<AdminPersonAddPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/admin/person/add";

    private static final String personEditFormLocator = "personEdit";

    private static final String descriptionsTabLocator = "//li/a[@href='#dane']";
    private static final String settingsTabLocator = "//li/a[@href='#ustawienia']";
    private static final String privilegesTabLocator = "//li/a[@href='#uprawnienia']";
    private static final String breadcrumbLocator = "//ul[@class='breadcrumb']";
    private static final String submitButtonLocator = "//*[@class=\"btn btn-success no-border\"]";

    //#dane
    private static final String nameFieldLocator = "form-field-name";
    private static final String name2FieldLocator = "form-field-name2";
    private static final String surnameFieldLocator = "form-field-surname";
    private static final String emailFieldLocator = "form-field-email";
    private static final String guidFieldLocator = "form-field-guid";
    private static final String addressFieldLocator = "form-field-address";
    private static final String phoneFieldLocator = "form-field-phone";
    private static final String faxFieldLocator = "form-field-fax";
    private static final String insitutionListLocator = "form_field_fk_institution_chosen";
    private static final String insideCellListLocator = "form_field_cell_chosen";

    //#ustawienia
    private static final String activeCheckboxLocator = "//label/input[@id='form-field-status']/following-sibling::span[1]";
    private static final String autoPasswordCheckboxLocator = "//label/input[@id='form-field-generatePassword']/following-sibling::span[1]";
    private static final String passwordFieldLocator = "passwd";
    private static final String passwordVerifyFieldLocator = "passwdVerify";

    //#uprawnienia
    private static final String institutionListExpandLocator = "//select[@name='roles[0][institution]']/following-sibling::div[1]/a/span";
    private static final String institutionListDropdownLocator = "//select[@name='roles[0][institution]']/following-sibling::div[1]/div/ul";
    private static final String projectListExpandLocator = "//select[@name='roles[0][project]']/following-sibling::div[1]/a/span";
    private static final String projectListDropdownLocator = "//select[@name='roles[0][project]']/following-sibling::div[1]/div/ul";
    private static final String roleListExpandLocator = "//select[@name='roles[0][role]']/following-sibling::div[1]/a/span";
    private static final String roleListDropdownLocator = "//select[@name='roles[0][role]']/following-sibling::div[1]/div/ul";


    @FindBy(xpath = breadcrumbLocator)
    WebElement breadcrumb;

    @FindBy(id = personEditFormLocator)
    WebElement personEditForm;
    @FindBy(xpath = descriptionsTabLocator)
    WebElement descriptionsTab;
    @FindBy(xpath = settingsTabLocator)
    WebElement settingsTab;
    @FindBy(xpath = privilegesTabLocator)
    WebElement privilegesTab;
    @FindBy(xpath = submitButtonLocator)
    WebElement submitButton;

    //dane
    @FindBy(id = nameFieldLocator)
    WebElement nameField;
    @FindBy(id = name2FieldLocator)
    WebElement name2Field;
    @FindBy(id = surnameFieldLocator)
    WebElement surnameField;
    @FindBy(id = emailFieldLocator)
    WebElement emailField;
    @FindBy(id = guidFieldLocator)
    WebElement guidField;
    @FindBy(id = addressFieldLocator)
    WebElement addressField;
    @FindBy(id = phoneFieldLocator)
    WebElement phoneField;
    @FindBy(id = faxFieldLocator)
    WebElement faxField;
    @FindBy(id = insitutionListLocator)
    WebElement insitutionList;
    @FindBy(id = insideCellListLocator)
    WebElement insideCellList;

    //#ustawienia
    @FindBy(xpath = activeCheckboxLocator)
    WebElement activeCheckbox;
    @FindBy(xpath = autoPasswordCheckboxLocator)
    WebElement autoPasswordCheckbox;
    @FindBy(name = passwordFieldLocator)
    WebElement passwordField;
    @FindBy(name = passwordVerifyFieldLocator)
    WebElement passwordVerifyField;

    //#uprawnienia
    @FindBy(xpath = institutionListExpandLocator)
    WebElement institutionListExpand;
    @FindBy (xpath = institutionListDropdownLocator)
    WebElement institutionListDropdown;
    @FindBy(xpath = projectListExpandLocator)
    WebElement projectListExpand;
    @FindBy (xpath = projectListDropdownLocator)
    WebElement projectListDropdown;
    @FindBy(xpath = roleListExpandLocator)
    WebElement roleListExpand;
    @FindBy (xpath = roleListDropdownLocator)
    WebElement roleListDropdown;


    public AdminPersonAddPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Administracja - Użytkownicy - Dodaj użytkownika lokalnego [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(descriptionsTab);
        elementList.add(settingsTab);
        elementList.add(privilegesTab);
        elementList.add(nameField);
        elementList.add(name2Field);
        elementList.add(surnameField);
        elementList.add(emailField);
        elementList.add(addressField);
        elementList.add(phoneField);
        elementList.add(faxField);
        elementList.add(insitutionList);
        elementList.add(insideCellList);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida uzytkownikow '/admin/person/index', nie wszystkie elementy sa widoczne i dostepne");
        }

        elementList.add(personEditForm);
        elementList.add(breadcrumb);
        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Strona grida uzytkownikow '/admin/person/index', nie wszystkie elementy sa widoczne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminPersonAddPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkActionSuccess() {
        PageLoad.checkActionSuccess(driver);
    }

    public void setName(String valueText) {
        nameField.clear();
        nameField.sendKeys(valueText);
    }

    public void setSecondName(String valueText) {
        name2Field.clear();
        name2Field.sendKeys(valueText);
    }

    public void setSurname(String valueText) {
        surnameField.clear();
        surnameField.sendKeys(valueText);
    }

    public void setEmail(String valueText) {
        emailField.clear();
        emailField.sendKeys(valueText);
    }

    public void setActiveCheckbox(String action) {
        PageLoad.selectTheCheckbox(driver, activeCheckbox, action);
    }

    public void setAutoPasswordCheckbox(String action) {
        PageLoad.selectTheCheckbox(driver, autoPasswordCheckbox, action);
    }

    public void setPassword (String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
        passwordVerifyField.clear();
        passwordVerifyField.sendKeys(password);
    }

    public void changeTabToDescriptions() {

        PageLoad.moveToElement(driver, breadcrumb, descriptionsTab);
        descriptionsTab.click();
        /*if (!descriptionsTab.getAttribute("class").equals("active")){
            throw new Error("ERROR: Nie udalo sie przelaczyc zakladki '#dane'");
        }*/

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(nameField);
        elementList.add(name2Field);
        elementList.add(surnameField);
        elementList.add(emailField);
        elementList.add(addressField);
        elementList.add(phoneField);
        elementList.add(faxField);
        elementList.add(insitutionList);
        elementList.add(insideCellList);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona grida uzytkownikow '/admin/person/index', zakladka 'Dane', nie wszystkie elementy sa widoczne i dostepne");
        }
        System.out.println("INFO: Zakladka 'Dane' zaladowala sie poprawnie");
    }

    public void changeTabToSettings() {

        PageLoad.moveToElement(driver, breadcrumb, settingsTab);
        settingsTab.click();
        /*if (!settingsTab.getAttribute("class").equals("active")){
            throw new Error("ERROR: Nie udalo sie przelaczyc zakladki '#ustawienia");
        }*/
    }

    public void changeTabToPrivileges() {

        PageLoad.moveToElement(driver, breadcrumb, privilegesTab);
        privilegesTab.click();
        /*if (!privilegesTab.getAttribute("class").equals("active")){
            throw new Error("ERROR: Nie udalo sie przelaczyc zakladki '#uprawnienia");
        }*/
    }

    public void setValuesToDescriptions(List<String> parametersList) {

        int paramNeedNumber = 3;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: setValuesToDescriptions() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
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

    }

    public void setValuesToSettings(List<String> parametersList) {

        int paramNeedNumber = 3;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: setValuesToSettings() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            this.setActiveCheckbox(parametersList.get(0));
        }
        if(parametersList.get(1) != "") {
            this.setAutoPasswordCheckbox(parametersList.get(1));
        }
        if(parametersList.get(2) != "") {
            this.setPassword(parametersList.get(2));
        }

    }

    public void setMainPrivilege(List<String> parametersList) {

        int paramNeedNumber = 3;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR:  setMainPrivilege() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            this.setMainInstitution(parametersList.get(0));
        }
        if(parametersList.get(1) != "") {
            this.setMainProject(parametersList.get(1));
        }
        if(parametersList.get(2) != "") {
            this.setMainRole(parametersList.get(2));
        }

    }

    //pierwszy select z wyborem uprawnien jest zawsze (nie trzeba go tworzyc)
    //instytucja
    public void setMainInstitution(String valueText) {
        PageLoad.bootStrapDropDownValueSelection(institutionListExpand, institutionListDropdown, valueText);
    }
    //projekt
    public void setMainProject(String valueText) {
        PageLoad.bootStrapDropDownValueSelection(projectListExpand, projectListDropdown, valueText);
    }
    //rola
    public void setMainRole(String valueText) {
        PageLoad.bootStrapDropDownValueSelection(roleListExpand, roleListDropdown, valueText);
    }

    public AdminPersonGridPage submitForm() {
        PageLoad.moveToElement(driver, breadcrumb, submitButton);
        submitButton.click();

        return new AdminPersonGridPage(driver);
    }


}