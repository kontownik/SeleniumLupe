package pages;

import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import java.util.ArrayList;
import java.util.List;

public class PublicMessagesMapPage extends LoadableComponent<PublicMessagesMapPage> {

    private WebDriver driver;
    private static String baseUrl = PageLoad.getProjectUrl()+"/mcity/messages/map";

    private static final String loggedUserPanelLocator = "//span[contains(@class, 'user-info')]";
    private static final String logoContainerLocator = "logoContainer";
    private static final String addNewReportButtonLocator = "addReport";
    private static final String leafletMarkerPaneLocator = "//div[@class=\"leaflet-pane leaflet-marker-pane\"]";
    private static final String popupPaneLocator = "//div[@class=\"leaflet-pane leaflet-popup-pane\"]";
    private static final String markerIconLocator = "//div[contains(concat(' ', normalize-space(@class), ' '), ' awesome-marker ')]";
    //dymek szczegółów
    private static final String buttonDetailsTriggerLocator = "//button[contains(concat(' ', normalize-space(@class), ' '), ' details-trigger ')]";

    @FindBy (xpath = loggedUserPanelLocator)
    WebElement loggedUserPanel;
    @FindBy (id = addNewReportButtonLocator)
    WebElement addNewReportButton;
    @FindBy (id = logoContainerLocator)
    WebElement logoContainer;
    @FindBy(xpath = leafletMarkerPaneLocator)
    WebElement leafletMarkerPane;
    @FindBy (xpath = popupPaneLocator)
    WebElement popupPane;

    public PublicMessagesMapPage(WebDriver driver) {
        
        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().equals(baseUrl)) {
            driver.get(baseUrl);
        }
    }
    
    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "Lupe - Mapa komunikatów - Białystok");
        PageLoad.hideCookieWindow(driver,"//*[@class=\"cb-enable\"]");
        System.out.println("INFO: Udalo sie zaladowac PublicIncidentsMapPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    public void openMarkerDetails(){

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
                if(!detailsButton.isDisplayed()) {
                    throw new Error("ERROR: Na 'dymku' brakuje przycisku 'Szczegóły'");
                }
                detailsButton.click();
                break; // w tym momencie powinny być otwarte 'Szczegóły zgłoszenia'
            }
        }
    }

    public void verifyReportPreview (int timeoutinSeconds) {
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
        PageLoad.verifyDetailsView(driver, timeoutinSeconds, previewTextList);
    }

}