package pages;

import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.List;

public class AdminReportMapPage extends LoadableComponent<AdminReportMapPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/index/map";
    private static int markerCount = 0;

    private static final String mapLocator = "mcityIncidentPreviewMap";
    //private static final String pageHeaderLocator = "//div[contains(@class, 'page-header')]";
    private static final String leafletMarkerPaneLocator = "//div[@class=\"leaflet-pane leaflet-marker-pane\"]";
    private static final String popupPaneLocator = "//div[@class=\"leaflet-pane leaflet-popup-pane\"]";
    private static final String markerIconLocator = "//div[contains(concat(' ', normalize-space(@class), ' '), ' awesome-marker ')]";
    private static final String addReportButtonLocator = "//a[@href='/mcity/admin/index/add']";
    //dymek szczegółów
    private static final String buttonDetailsTriggerLocator = "//button[contains(concat(' ', normalize-space(@class), ' '), ' details-trigger ')]";
    private static final String buttonEditTriggerLocator = "//button[contains(concat(' ', normalize-space(@class), ' '), ' edit-trigger ')]";
    //modal McityPreview
    private static final String editButtonModalLocator = "preview-edit-button";


    //@FindBy (xpath = pageHeaderLocator)
    //WebElement pageHeader;
    @FindBy(id = mapLocator)
    WebElement map;
    @FindBy(xpath = leafletMarkerPaneLocator)
    WebElement leafletMarkerPane;
    @FindBy (xpath = popupPaneLocator)
    WebElement popupPane;
    @FindBy(xpath = addReportButtonLocator)
    WebElement addReportButton;


    public AdminReportMapPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Lupe - Mapa zgłoszeń [Białystok]");

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList.add(map);
        elementList.add(leafletMarkerPane);
        elementList.add(addReportButton);

        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Strona mapy Komunikatow w czesci administracyjnej '/mcity/admin/index/map', nie wszystkie elementy sa widoczne");
        }

        //MARKERY
        /*System.out.println("INFO: Rozpoczete sprawdzanie istniejacych markerow");
        elementList = new ArrayList<WebElement>();
        elementList = leafletMarkerPane.findElements(By.xpath(markerIconLocator));

        if(!PageLoad.elementsAreDisplayed(driver, elementList)) {
            throw new Error("ERROR: Markery na Mapie '/mcity/admin/index/map', nie wszystkie elementy sa widoczne");
        }*/

        System.out.println("INFO: Udalo sie zaladowac AdminReportMapPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    /* public String getPageHeaderText () {
        return pageHeader.getText();
    } */

    public void calculateMarkerCount (){

        List<WebElement> elementList = new ArrayList<WebElement>();
        markerCount = 0;

        elementList = leafletMarkerPane.findElements(By.xpath(markerIconLocator));
        for(WebElement element:elementList) {
            markerCount++;
        }
    }

    public int getMarkerCount() {
        return this.markerCount;
    }

    public void openMarkerDetails  (){

        List<WebElement> elementList = new ArrayList<WebElement>();
        elementList = leafletMarkerPane.findElements(By.xpath(markerIconLocator));

        for(WebElement element:elementList) {
            if(element.isDisplayed()) {
                Actions action = new Actions(driver);
                action.moveToElement(element).click(element);
                action.perform();
                WebElement popupContent = driver.findElement(By.xpath("//div[@class=\"leaflet-popup-content\"]"));
                if(!popupContent.getText().contains("Kategoria")) {
                    throw new Error("ERROR: Widoczny marker nie ma zdefiniowanej Kategorii (lub jest problem z jej wyświetleniem. | "+popupContent.getText());
                }
                WebElement detailsButton = popupContent.findElement(By.xpath(buttonDetailsTriggerLocator));
                WebElement editButton = popupContent.findElement(By.xpath(buttonEditTriggerLocator));
                if(!editButton.isDisplayed()) {
                    throw new Error("ERROR: Na 'dymku' brakuje przycisku 'Edytuj'");
                }
                if(!detailsButton.isDisplayed()) {
                    throw new Error("ERROR: Na 'dymku' brakuje przycisku 'Szczegóły'");
                }
                detailsButton.click();
                break; // w tym momencie powinny być otwarte 'Szczegóły zgłoszenia'
            }
        }
    }

    public void verifyReportPreviewModal (int timeoutinSeconds) {
        List<String> previewTextList = new ArrayList<String>();
        previewTextList.add("Opis:");
        previewTextList.add("Adres:");
        previewTextList.add("Data zgłoszenia:");
        previewTextList.add("Kategoria:");
        previewTextList.add("Status:");
        previewTextList.add("Oddano głosów:");
        previewTextList.add("Jednostka odpowiedzialna:");
        previewTextList.add("Odpowiedź:");
        previewTextList.add("Nr sprawy:");
        PageLoad.verifyMcityPreviewModal(driver, timeoutinSeconds, previewTextList);
    }

}