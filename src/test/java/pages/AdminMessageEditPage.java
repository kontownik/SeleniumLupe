package pages;

import helpers.GridHelper;
import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class AdminMessageEditPage extends LoadableComponent<AdminMessageEditPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/messages/";
    private static String actionName;

    private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";
    private static final String messageFormLocator = "mcity-message-form";
    private static final String addressFieldLocator = "form-field-address";
    private static final String categoryListLocator = "form_field_fkDictDictionaryElement_chosen"; //kategoria
    private static final String messageEditMapLocator = "mcityMessageEditMap";
    private static final String messageAddMapLocator = "mcityMessageAddMap";
    private static final String titleFieldLocator = "form-field-title";
    private static final String descriptionFieldLocator = "form-field-description";
    private static final String detailsUrlFieldLocator = "form-field-details-url";
    private static final String publicateDateFieldLocator = "form-field-dateFrom";
    private static final String closeDateFieldLocator  = "form-field-dateTo";
    private static final String statusListLocator = "form-field-status"; //select(name)
    private static final String priorityListLocator = "priority"; //select(name)
    private static final String personCreateFieldLocator  = "fkPersonCreate"; // name
    private static final String personModifyFieldLocator  = "fkPersonModify"; // name
    private static final String submitButtonLocator = "//*[@class=\"btn btn-success no-border\"]";
    private static final String deleteButtonLocator = "//*[@class=\"btn btn-danger no-border mcity-delete-trigger\"]";
    private static final String restoreButtonLocator = "//*[@class=\"btn btn-primary no-border mcity-restore-trigger\"]";
    private static final String uploadFileLocator = "fa-upload"; //classname
    private static final String colorBoxItemLocator = "//a[@class=\"cboxElement\"]";

    //TODO: dodac slowniki, kategorii i statusow (jako enum?)

    //leaflet-draw na mapie
    private static final String drawMarkerLocator = "//a[@class=\"leaflet-draw-draw-marker\"]";

    @FindBy (xpath = pageHeaderLocator)
    WebElement pageHeader;
    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;
    @FindBy (id = messageFormLocator)
    WebElement messageForm;
    @FindBy (id = categoryListLocator)
    WebElement categoryList;
    @FindBy (id = addressFieldLocator)
    WebElement addressField;
    @FindBy (id = messageAddMapLocator)
    WebElement messageAddMap;
    @FindBy (id = messageEditMapLocator)
    WebElement messageEditMap;
    @FindBy (xpath = drawMarkerLocator)
    WebElement drawMarker;
    @FindBy (id = descriptionFieldLocator)
    WebElement descriptionField;
    @FindBy (id = statusListLocator)
    WebElement statusList;
    @FindBy (xpath = submitButtonLocator)
    WebElement submitButton;

    @FindBy (id = titleFieldLocator)
    WebElement titleField;
    @FindBy (id = detailsUrlFieldLocator)
    WebElement detailsUrlField;
    @FindBy (id = publicateDateFieldLocator)
    WebElement publicateDateField;
    @FindBy (id = closeDateFieldLocator)
    WebElement closeDateField;
    @FindBy (name = priorityListLocator)
    WebElement priorityList;
    @FindBy (name = personCreateFieldLocator)
    WebElement personCreateField;
    @FindBy (name = personModifyFieldLocator)
    WebElement personModifyField;

    //upload zalacznikow
    @FindBy(className = uploadFileLocator)
    WebElement uploadFile;
    @FindBy (xpath = colorBoxItemLocator)
    WebElement colorBoxItem;

    //tylko przy edycji komunikatu
    @FindBy (xpath = deleteButtonLocator)
    WebElement deleteButton;
    @FindBy (xpath = restoreButtonLocator)
    WebElement restoreButtton;


    public AdminMessageEditPage(WebDriver driver, int withDefinedID, String actionName) {
        
        this.driver = driver;
        this.actionName = actionName;

        if(withDefinedID !=0){ //do poprawy?
            driver.get(baseUrl+actionName+"/mcity_id."+withDefinedID);
            System.out.println("INFO: Przejscie do istniejacego komunikatu ID: "+withDefinedID);
            PageFactory.initElements(driver, this);
            //PageLoad.validPageTitle(driver, "SmartSite - Lupe - Lista zgłoszeń - Edycja zgłoszenia #"+withDefinedID+" [Białystok]");
        }
        else {
            PageFactory.initElements(driver, this);

            if (!driver.getCurrentUrl().contains(baseUrl+actionName)) {
                driver.get(baseUrl+actionName);
                //PageLoad.validPageTitle(driver, "SmartSite - Lupe - Dodaj zgłoszenie [Białystok]");
            }
        }

    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(loggedUserPanel);
        elementList.add(messageForm);
        elementList.add(categoryList);
        elementList.add(addressField);
        if(actionName == "add") {
            elementList.add(messageAddMap);
        }
        if(actionName == "edit") {
            elementList.add(messageEditMap);
            elementList.add(submitButton);
            //elementList.add(deleteButton);
            if(!personCreateField.isDisplayed()){
                throw new Error("ERROR: "+personCreateField.toString() + " nie przeszedl testu widoczny + aktywny"); //debug
            }
            if(!personModifyField.isDisplayed()){
                throw new Error("ERROR: "+personCreateField.toString() + " nie przeszedl testu widoczny + aktywny"); //debug
            }
        }
        elementList.add(descriptionField);
        elementList.add(statusList);
        elementList.add(titleField);
        elementList.add(detailsUrlField);
        elementList.add(priorityList);
        elementList.add(publicateDateField);
        elementList.add(closeDateField);

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona edycji komunikatu '/mcity/admin/messages/edit/', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminMessageEditPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void checkSuccessFormPost () {
        PageLoad.checkSuccessFormPost(driver);
    }

    /**
     *
     * @param parametersList
     * adres (lub marker), kategoria, tytul, opis, adresURL, data publikacji, data wygasniecia, status, waznosc, zalacznik
     * @return AdminReportEditPage
     */
    public void editDataInMessage(List<String> parametersList) { //TODO: do przepisania

        int paramNeedNumber = 10;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: editDataInMessage() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            if(parametersList.get(0) == "point-center") {
                this.setMarkerOnMap();
            }
            else {
                this.setAddress(parametersList.get(0));
            }
        }
        if(parametersList.get(1) != "") {
            this.setCategoryList(parametersList.get(1));
        }
        if(parametersList.get(2) != "") {
            this.setTitle(parametersList.get(2));
        }
        if(parametersList.get(3) != "") {
            this.setDescription(parametersList.get(3));
        }
        if(parametersList.get(4) != "") {
            this.setDetailsUrl(parametersList.get(4));
        }
        if(parametersList.get(5) != "") {
            this.setPublicateDate(parametersList.get(5));
        }
        if(parametersList.get(6) != "") {
            this.setCloseDate(parametersList.get(6));
        }
        if(parametersList.get(7) != "") {
            this.setStatus(parametersList.get(7));
        }
        if(parametersList.get(8) != "") {
            this.setPriority(parametersList.get(8));
        }
        if(parametersList.get(9) != "") {
            this.addAttachment(parametersList.get(9));
        }

        //TODO: dodac obsluge dodawanie punktow na mapie za pomoca adresu
        if(actionName == "add") {
            PageLoad.moveToElement(driver, pageHeader, submitButton);
            submitButton.click();
        }
        if(actionName == "edit") {
            PageLoad.moveToElement(driver, pageHeader, submitButton);
            submitButton.click();
        }

        System.out.println("INFO: Wyslano formularz");
        this.checkSuccessFormPost();
    }

    public AdminMessageGridPage addMessage(List<String> parametersList) {
        this.editDataInMessage(parametersList);
        return new AdminMessageGridPage(driver);
    }

    public AdminMessageEditPage editMessage(List<String> parametersList) {
        this.editDataInMessage(parametersList);
        return new AdminMessageEditPage(driver, 0, "edit");
    }

    //AKCJE

    // == Weryfikacja 'historii' zgloszenia
    public boolean isInChangelog(String expectedText) {

        WebElement timeline = driver.findElement(By.id("history"));
        List<WebElement> timelineItems = timeline.findElements(By.cssSelector("div.timeline-items"));
        Boolean textChangeLogged = false;

        for(WebElement element:timelineItems){
            if(element.getText().contains(expectedText)){
                textChangeLogged = true;
            }
        }

        return textChangeLogged;
    }

    public String getPageHeaderText () {
        return pageHeader.getText();
    }

    public int getPageID () {

        String currentURL = driver.getCurrentUrl();
        String[] parts = currentURL.split("\\.");
        int pageID = 0;
        try {
            pageID = Integer.parseInt(parts[parts.length - 1]);
        } catch (NumberFormatException e) {
            return 0;
        }
        if(pageID > 0){
            return pageID;
        }

        return pageID;
    }

    public AdminMessageGridPage deleteByButton () {

        System.out.println("INFO: Akcja usuwania przyciskiem z formularza");
        PageLoad.moveToElement(driver, pageHeader, deleteButton);
        deleteButton.click();
        PageLoad.confirmModalWithoutClass(driver,10);

        return new AdminMessageGridPage(driver);
    }

    public AdminMessageGridPage restoreByButton () {

        System.out.println("INFO: Akcja pryzwracania przyciskiem z formularza");
        PageLoad.moveToElement(driver, pageHeader,restoreButtton);
        restoreButtton.click();
        PageLoad.confirmModalWithoutClass(driver,10);

        return new AdminMessageGridPage(driver);
    }

    public void setMarkerOnMap () {

        //Klika w "DRAW A MARKER" i sprawdza czy marker sie pojawil
        WebDriverWait wait = new WebDriverWait(driver, 10);
        PageLoad.moveToElement(driver, pageHeader, drawMarker);
        drawMarker.click();
        // dwie rozne mapy w zaleznosci czy jest do /add czy /edit
        if(actionName == "add") {
            PageLoad.moveToElement(driver, pageHeader, messageAddMap);
            messageAddMap.click();
        }
        if(actionName == "edit") {
            PageLoad.moveToElement(driver, pageHeader, messageEditMap);
            messageEditMap.click();

        }
        //layout z markerami
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"leaflet-pane leaflet-marker-pane\"]")));
        //marker
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class=\"leaflet-marker-icon leaflet-zoom-animated leaflet-interactive\"]")));

        //TODO: czy postawienie markera dodalo adres
        /*if(this.addressField.getText().equals("")) {
            throw new Error("ERROR: Postawienie markera na mapie nie wygenerowało adresu!");
        }*/
    }

    public void setDetailsUrl (String valueText) {
        detailsUrlField.clear();
        detailsUrlField.sendKeys(valueText);
    }

    public void setCategoryList (String categoryText) {

        PageLoad.pickFromChosenSelect(driver, pageHeader, categoryList, "//li[. = '"+categoryText+"']");
    }

    public void setAddress (String valueText) {
        addressField.clear();
        addressField.sendKeys(valueText);
    }

    public void setTitle (String valueText) {
        titleField.clear();
        titleField.sendKeys(valueText);
    }

    public void setPublicateDate (String valueText) {
        publicateDateField.clear();
        publicateDateField.sendKeys(valueText);
    }

    public void setCloseDate (String valueText) {
        closeDateField.clear();
        closeDateField.sendKeys(valueText);
    }

    public void setDescription (String valueText) {
        descriptionField.clear();
        descriptionField.sendKeys(valueText);
    }

    public void setStatus (String valueText) {
        PageLoad.pickFromSelect(statusList,valueText);
    }

    public void setPriority (String valueText) {
        PageLoad.pickFromSelect(priorityList,valueText);
    }

    public void isAttachmentPresent () {
        System.out.println("INFO: Sprawdzanie czy istnieje zalacznik(zdjecie)");
        try {
            colorBoxItem.isDisplayed();
        }
        catch (NoSuchElementException ex) {
            throw new Error("ERROR: Miniatura nie istnieje |"+ex);
        }
    }

    public void addAttachment (String path) {
        URL urlToFile = this.getClass().getResource(path);
        String filepath = urlToFile.toString();
        PageLoad.setClipboardData(filepath);
        System.out.println("INFO: Plik '"+filepath+"' dodany do schowka systemowego");
        //dodawanie załącznika
        try {
            PageLoad.useUploadWindow(driver, this.uploadFile);
        } catch (Exception ex) {
            throw new Error("INFO: Nie udało się załadować pliku ze schowka jako załącznika");
        }
    }


}