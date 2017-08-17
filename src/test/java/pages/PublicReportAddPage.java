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

public class PublicReportAddPage extends LoadableComponent<PublicReportAddPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/incidents/add";

    private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";
    private static final String reportFormLocator = "mcity-edit-form";
    private static final String incidentMapLocator = "mcityAddMap";
    private static final String fullscreenIconLocator = "//a[@class=\"leaflet-control-zoom-fullscreen fullscreen-icon\"]";
    private static final String zoomInIconLocator = "//a[@class=\"leaflet-control-zoom-in\"]";
    private static final String zoomOutIconLocator = "//a[@class=\"leaflet-control-zoom-out\"]";
    private static final String addressFieldLocator = "form-field-address";
    private static final String categoryListLocator = "form_field_fkDictDictionaryElement_chosen"; //kategoria
    private static final String descriptionFieldLocator = "form-field-description";
    //TODO: upload plikow
    private static final String notifyCheckboxLocator = "form-field-notify";
    private static final String submitButtonLocator = "//button[@class=\"btn btn-raised btn-info\"]";

    @FindBy (xpath = pageHeaderLocator)
    WebElement pageHeader;
    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;
    @FindBy (id = reportFormLocator)
    WebElement reportForm;
    @FindBy (id = incidentMapLocator)
    WebElement incidentMap;
    @FindBy (id = addressFieldLocator)
    WebElement addressField;
    @FindBy (id = categoryListLocator)
    WebElement categoryList;
    @FindBy (id = descriptionFieldLocator)
    WebElement descriptionField;
    @FindBy (id = notifyCheckboxLocator)
    WebElement notifyCheckbox;
    @FindBy (xpath = submitButtonLocator)
    WebElement submitButton;

    @FindBy (xpath = fullscreenIconLocator)
    WebElement fullscreenIcon;
    @FindBy (xpath = zoomInIconLocator)
    WebElement zoomInIcon;
    @FindBy (xpath = zoomOutIconLocator)
    WebElement zoomOutIcon;


    public PublicReportAddPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "Lupe - Dodaj zgłoszenie - Białystok");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(pageHeader);
        //elementList.add(loggedUserPanel); //widoczne tylko przy zalogowanym userze
        elementList.add(reportForm);
        elementList.add(incidentMap);
        elementList.add(fullscreenIcon);
        elementList.add(zoomInIcon);
        elementList.add(zoomOutIcon);
        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Strona formularza dodawania zgloszen w czesci publicznej '/mcity/incidents/add', nie wszystkie elementy sa widoczne");
        }

        elementList = new ArrayList<WebElement>();
        elementList.add(addressField);
        elementList.add(categoryList);
        elementList.add(descriptionField);
        //elementList.add(notifyCheckbox); ////widoczne tylko przy zalogowanym userze
        elementList.add(submitButton);
        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona formularza dodawania zgloszen w czesci publicznej '/mcity/incidents/add', nie wszystkie elementy sa widoczne i dostepne");
        }

        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");
        System.out.println("INFO: Udalo sie zaladowac PublicReportAddPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkSuccessFormPost () {
        PageLoad.checkSuccessFormPost(driver);
    }

    public String getPageHeaderText () {
        return pageHeader.getText();
    }

    public void setMarkerOnMap () {

        //Klika w Mape i sprawdza czy marker sie pojawil
        WebDriverWait wait = new WebDriverWait(driver, 5);
        PageLoad.moveToElement(driver, pageHeader, incidentMap);
        incidentMap.click();
        //layout z markerami
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"leaflet-pane leaflet-marker-pane\"]")));
        //marker
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class=\"leaflet-marker-icon leaflet-zoom-animated leaflet-interactive leaflet-marker-draggable\"]")));
    }

    public void setCategoryList (String categoryText) {
        PageLoad.moveToElement(driver, pageHeader, categoryList);
        PageLoad.pickFromChosenSelect(driver, pageHeader, categoryList, "//li[. = '"+categoryText+"']");
    }

    public void setDescription (String valueText) {
        descriptionField.clear();
        descriptionField.sendKeys(valueText);
    }

    //checkbox "Chcę dostać powiadomienie o zmianie statusu"
    public void useNotifyCheckbox()
    {
        PageLoad.selectTheCheckbox(driver, notifyCheckbox, "ACTIVATE");
    }

    public String getAddressText () {
        String addressText = "";
        addressText = addressField.getAttribute("value");
        return addressText;
    }

    //TODO: dodawanie zalacznikow
    public void addPicture(String pictureLink) {
        return;
    }

    public boolean isSurfer() {
        return loggedUserPanel.isDisplayed();
    }

    //marker true/false, kategoria, opis, załącznik, notyfikacja
    public void addReportAction (List<String> parametersList) {

        int paramNeedNumber = 5;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: addReport() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            this.setMarkerOnMap();
        }
        if(parametersList.get(1) != "") {
            this.setCategoryList(parametersList.get(1));
        }
        if(parametersList.get(2) != "") {
            this.setDescription(parametersList.get(2));
        }
        if(parametersList.get(3) != "") {
            this.addPicture(parametersList.get(3));
        }
        if(parametersList.get(4) != "") {
            this.useNotifyCheckbox();
        }

        PageLoad.moveToElement(driver, pageHeader, submitButton);
        submitButton.click();
        PageLoad.confirmPublicModal(driver, 5);
        System.out.println("INFO: Wyslano formularz");
    }

    /*public PublicReportAddPage addReportByGuest(List<String> parametersList) {
        addReportAction(parametersList);
        return new PublicReportAddPage(driver);
    }*/

    public PublicReportGridPage addReportBySurfer(List<String> parametersList) {
        addReportAction(parametersList);
        return new PublicReportGridPage(driver);
    }

}