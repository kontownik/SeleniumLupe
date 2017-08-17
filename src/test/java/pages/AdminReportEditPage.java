package pages;

import helpers.PageLoad;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class AdminReportEditPage extends LoadableComponent<AdminReportEditPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/index/edit";

    private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";
    private static final String reportFormLocator = "mcity-edit-form";
    private static final String categoryListLocator = "form_field_fkDictDictionaryElement_chosen"; //kategoria
    private static final String categoryInsideListLocator = "form_field_fkDictDictionaryElementInside_chosen"; //kategoria wewnetrzna
    private static final String addressFieldLocator = "form-field-address";
    private static final String incidentMapLocator = "mcityIncidentEditMap";
    private static final String descriptionFieldLocator = "form-field-description";
    private static final String numberFieldLocator = "form-field-mcityNumber"; //nr sprawy
    private static final String statusListLocator = "form-field-status";

    private static final String assignToListLocator = "form_field_assignTo_chosen";
    private static final String assignToListExpandLocator = "//*[@id=\"form_field_assignTo_chosen\"]/a/span";
    private static final String assignToListDropdownLocator = "//*[@id=\"form_field_assignTo_chosen\"]/div/ul";

    private static final String institutionListLocator = "form_field_fkInstitution_chosen";
    private static final String institutionListExpandLocator = "//*[@id=\"form_field_fkInstitution_chosen\"]/a/span";
    private static final String institutionListDropdownLocator = "//*[@id=\"form_field_fkInstitution_chosen\"]/div/ul";

    private static final String responseFieldLocator = "form-field-comment"; // Odpowiedz
    private static final String submitButtonLocator = "//*[@class=\"btn btn-success no-border\"]";
    private static final String dateConfirmationFieldLocator = "dateConfirmationText";
    private static final String dateCompletedFieldLocator = "dateCompletedText";
    private static final String counterFieldLocator = "counter";
    private static final String counterTypeListLocator = "type";
    private static final String deleteButtonLocator = "//*[@class=\"btn btn-danger no-border mcity-delete-trigger\"]";
    private static final String incidentSearchInputLocator = "incident-search";
    private static final String incidentAddRelationLocator = "incident-add-relation";
    private static final String incidentCancelAddRelationLocator = "incident-cancel-add-relaction";
    private static final String commentFormLocator = "function=addComment"; //przez atrybut akcji
    private static final String commentTextAreaLocator = "content"; //name wewnatrz formularza
    private static final String commentSubmitLocator = ""; //submit od formularza


    //TODO: dodac slowniki, kategorii i statusow (jako enum?)

    //leaflet-draw na mapie
    private static final String drawMarkerLocator = "//a[@class=\"leaflet-draw-draw-marker\"]";

    @FindBy (xpath = pageHeaderLocator)
    WebElement pageHeader;
    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;
    @FindBy (id = reportFormLocator)
    WebElement reportForm;
    @FindBy (id = categoryListLocator)
    WebElement categoryList;
    @FindBy (id = categoryInsideListLocator)
    WebElement categoryInsideList;
    @FindBy (id = addressFieldLocator)
    WebElement addressField;
    @FindBy (id = incidentMapLocator)
    WebElement incidentMap;
    @FindBy (xpath = drawMarkerLocator)
    WebElement drawMarker;
    @FindBy (id = descriptionFieldLocator)
    WebElement descriptionField;
    @FindBy (id = numberFieldLocator)
    WebElement numberField;
    @FindBy (id = statusListLocator)
    WebElement statusList;
    @FindBy (id = assignToListLocator)
    WebElement assignToList;
    @FindBy (id = institutionListLocator)
    WebElement institutionList;
    @FindBy (id = responseFieldLocator)
    WebElement responseField;
    @FindBy (xpath = submitButtonLocator)
    WebElement submitButton;

    //widoczne tylko na statusie "potwierdzone"
    @FindBy (name = dateConfirmationFieldLocator)
    WebElement dateConfirmationField;
    @FindBy (name = dateCompletedFieldLocator)
    WebElement dateCompletedField;
    @FindBy (name = counterFieldLocator)
    WebElement counterField;
    @FindBy (name = counterTypeListLocator)
    WebElement counterTypeList;

    //tylko przy edycji zgloszenia
    @FindBy (xpath = deleteButtonLocator)
    WebElement deleteButton;
    @FindBy (id = incidentSearchInputLocator)
    WebElement incidentSearchInput;
    @FindBy (id = incidentAddRelationLocator)
    WebElement incidentAddRelation;
    @FindBy (id = incidentCancelAddRelationLocator)
    WebElement incidentCancelAddRelation;

    @FindBy(xpath = assignToListExpandLocator)
    WebElement assignToListExpand;
    @FindBy(xpath = assignToListDropdownLocator)
    WebElement assignToListDropdown;

    @FindBy(xpath = institutionListExpandLocator)
    WebElement institutionListExpand;
    @FindBy(xpath = institutionListDropdownLocator)
    WebElement institutionListDropdown;




    public AdminReportEditPage(WebDriver driver, int withDefinedReportID) {
        
        this.driver = driver;

        if(withDefinedReportID !=0){ //do poprawy?
            driver.get(baseUrl+"/mcity_id."+withDefinedReportID);
            System.out.println("INFO: Przejscie do istniejacego zgloszenia ID: "+withDefinedReportID);
            PageFactory.initElements(driver, this);
            PageLoad.validPageTitle(driver, "SmartSite - Lupe - Lista zgłoszeń - Edycja zgłoszenia #"+withDefinedReportID+" [Białystok]");
        }
        else {
            PageFactory.initElements(driver, this);

            if (!driver.getCurrentUrl().contains(baseUrl)) {
                driver.get(baseUrl);
                PageLoad.validPageTitle(driver, "SmartSite - Lupe - Dodaj zgłoszenie [Białystok]");
            }
        }

    }
    
    @Override
    protected void isLoaded() throws Error {

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(loggedUserPanel);
        elementList.add(reportForm);
        elementList.add(categoryList);
        elementList.add(categoryInsideList);
        elementList.add(addressField);
        elementList.add(incidentMap);
        elementList.add(descriptionField);
        elementList.add(numberField);
        elementList.add(statusList);
        elementList.add(assignToList);
        elementList.add(institutionList);
        elementList.add(responseField);
        elementList.add(submitButton);

        //strona jest edytowana a nie tworzona
        if(getPageID()!=0) {
            elementList.add(deleteButton);
            elementList.add(incidentSearchInput);

            if(PageLoad.isSelectedOnDropdownSelect(new Select(statusList), "Potwierdzone")){ //widoczne tylko przy statusie "potwierdzone"
                if(!dateConfirmationField.isDisplayed()) {
                    throw new Error("ERROR: Strona edycji zgloszenia '/mcity/admin/index/edit', pole 'Data potwierdzenia' jest niewidoczne");
                }
                if(!dateCompletedField.isDisplayed()) {
                    throw new Error("ERROR: Strona edycji zgloszenia '/mcity/admin/index/edit', pole 'Data zakonczenia' jest niewidoczne");
                }
            }
        }

        if(!PageLoad.elementsAreDisplayedEnabled(driver, elementList)) {
            throw new Error("ERROR: Strona edycji zgloszenia '/mcity/admin/index/edit', nie wszystkie elementy sa widoczne i dostepne");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminReportEditPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    /**
     *
     * @param parametersList
     * kategoria, kategoria wewnetrzna, opis, nr sprawy, status, przypisany do, instytucja, odpowiedz, data potwierdzenia, data skonczenia, ilosc dni, czy rysowac marker
     * @return AdminReportEditPage
     */
    public AdminReportEditPage editDataInReport(List<String> parametersList) { //TODO: do przepisania

        int paramNeedNumber = 13;
        if(parametersList.size() != paramNeedNumber) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: editDataInReport() potrzebuje "+paramNeedNumber+" parametrow wyslano "+parametersList.size());
        }

        if(parametersList.get(0) != "") {
            this.setCategoryList(parametersList.get(0));
        }
        if(parametersList.get(1) != "") {
            this.setCategoryInsideList(parametersList.get(1));
        }
        if(parametersList.get(2) != "") {
            this.setDescription(parametersList.get(2));
        }
        if(parametersList.get(3) != "") {
            this.setNumber(parametersList.get(3));
        }
        if(parametersList.get(4) != "") {
            this.setStatus(parametersList.get(4));

            if(parametersList.get(4).equals("Potwiedzone")) {
                //dodatkowe informacje przy statusie potwierdzonym
                if(parametersList.get(8) != "") {
                    this.setDateConfirmation(parametersList.get(8));
                }
                if(parametersList.get(9) != "") {
                    this.setDateCompleted(parametersList.get(9));
                }
                if(parametersList.get(10) != "") {
                    System.out.println("WARNING: Brak obsługi parametru counterField");
                }
                if(parametersList.get(11) != "") {
                    System.out.println("WARNING: Brak obsługi parametru counterTypeList");
                }

            }
        }
        if(parametersList.get(5) != "") {
            this.setAssignToList(parametersList.get(5));
        }
        if(parametersList.get(6) != "") {
            this.setInstitutionList(parametersList.get(6));
        }
        if(parametersList.get(7) != "") {
            this.setResponse(parametersList.get(7));
        }

        if(parametersList.get(12) != "") {
            if(parametersList.get(12) == "point-center") {
                this.setMarkerOnMap();
            }
            else {
                //TODO: ustawienie adresu? ksztaltu?
            }
        }

        //TODO: dodac obsluge dodawanie punktow na mapie za pomoca adresu
        PageLoad.moveToElement(driver, pageHeader, submitButton);
        submitButton.click();

        System.out.println("INFO: Wyslano formularz");
        return new AdminReportEditPage(driver, 0);
    }


    /*public boolean isInComment(String expectedText) {

        WebElement commentsForm = getCommentForm();
        if(commentsForm!=null) {
            WebElement componentContent = commentsForm.findElement(By.className("component-content"));
        }

        return false;
    }*/

    //AKCJE

    // znalezienie formularza z modulu forum
    public WebElement getCommentForm () {

        List<WebElement> formsList = driver.findElements(By.className("form-horizontal"));
        WebElement commentsForm = null;
        for (WebElement element:formsList){
            if(element.getAttribute("action").contains("function=addComment")){
                commentsForm = element;
            }

        }
        return commentsForm;
    }

    //dodanie komentarza przez modul forum (przeladowuje strone)
    public AdminReportEditPage addComment (String commentText) {

        WebElement commentsForm = getCommentForm();
        if(commentsForm!=null) {
            commentsForm.findElement(By.tagName("textarea")).sendKeys(commentText);
            commentsForm.submit();
        }

        return new AdminReportEditPage(driver, 0);
    }

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

    public void checkSuccessFormPost() {
        PageLoad.checkSuccessFormPost(driver);
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

    public AdminReportGridPage deleteByButton () {

        System.out.println("INFO: Akcja usuwania przyciskiem z formularza");
        PageLoad.moveToElement(driver, pageHeader, deleteButton);
        deleteButton.click();
        PageLoad.confirmModal(driver,10);

        return new AdminReportGridPage(driver);
    }

    public void setMarkerOnMap () {

        //Klika w "DRAW A MARKER" i sprawdza czy marker sie pojawil
        WebDriverWait wait = new WebDriverWait(driver, 10);
        PageLoad.moveToElement(driver, pageHeader, drawMarker);
        drawMarker.click();
        PageLoad.moveToElement(driver, pageHeader, incidentMap);
        incidentMap.click();
        //layout z markerami
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class=\"leaflet-pane leaflet-marker-pane\"]")));
        //marker
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//img[@class=\"leaflet-marker-icon leaflet-zoom-animated leaflet-interactive\"]")));

        //System.out.println("test: "+addressField.getAttribute("value"));
        //TODO: czy postawienie markera dodalo adres
        //wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id(addressFieldLocator), "Polska"));
        //if(this.addressField.getText().equals("")) {
        //    throw new Error("ERROR: Postawienie markera na mapie nie wygenerowało adresu!");
        //}
    }

    public void setCategoryList (String categoryText) {
        PageLoad.moveToElement(driver, pageHeader, categoryList);
        PageLoad.pickFromChosenSelect(driver, pageHeader, categoryList, "//li[. = '"+categoryText+"']");
    }

    public void setCategoryInsideList (String categoryText) { //kategoria wewnetrzna

        PageLoad.pickFromChosenSelect(driver, pageHeader, categoryInsideList, "//li[. = '"+categoryText+"']");
    }

    public void setAssignToList (String categoryText) {

        PageLoad.bootStrapDropDownValueSelection(assignToListExpand, assignToListDropdown, categoryText);

        //return;
        //PageLoad.pickFromChosenSelect(driver, pageHeader, assignToList, "//li[. = '"+categoryText+"']");
        //PageLoad.pickFromChosenSelect(driver, pageHeader, assignToList, "//li[contains(.,\""+categoryText+"\")]");

    }

    public void setInstitutionList (String categoryText) {

        PageLoad.bootStrapDropDownValueSelection(institutionListExpand, institutionListDropdown, categoryText);

        //return;
        //PageLoad.pickFromChosenSelect(driver, pageHeader, institutionList, "//li[. = '"+categoryText+"']");
        //PageLoad.pickFromChosenSelect(driver, pageHeader, institutionList, "//li[contains(.,\""+categoryText+"\")]");
    }

    public void setDescription (String valueText) {
        descriptionField.clear();
        descriptionField.sendKeys(valueText);
    }

    public void setNumber (String valueText) {
        numberField.clear();
        numberField.sendKeys(valueText);
    }

    public void setResponse (String valueText) {
        responseField.clear();
        responseField.sendKeys(valueText);
    }

    public void setDateConfirmation (String valueText) {
        dateConfirmationField.clear();
        dateConfirmationField.sendKeys(valueText);
    }

    public void setDateCompleted (String valueText) {
        dateCompletedField.clear();
        dateCompletedField.sendKeys(valueText);
    }

    public void setStatus (String statusText) {
        PageLoad.pickFromSelect(statusList,statusText);
    }


}