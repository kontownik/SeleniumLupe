package pages;

import helpers.PageLoad;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class AdminSummaryPage extends LoadableComponent<AdminSummaryPage> {

    private WebDriver driver;
    private static final String baseUrl = PageLoad.getProjectUrl()+"/mcity/admin/index/index";

    private int expectedWindowsCount = 8;

    private static final String chart1Locator = "myChart1"; //Liczba zgłoszeń wg. statusu
    private static final String chart2Locator = "myChart2"; //Liczba zgłoszeń wg. statusu
    private static final String chart3Locator = "myChart3"; //Liczba zgłoszeń wg. kategorii zewnętrznej
    private static final String chart5Locator = "myChart5"; //Liczba zgłoszeń wg. kategorii wewnętrznej
    private static final String chart4Locator = "myChart4"; //Liczba zgłoszeń wg. dni

    private static final String windowLocator = "//div[@class=\"widget-box widget-color-blue\"]";


    public AdminSummaryPage(WebDriver driver) {

        this.driver = driver;
        PageFactory.initElements(driver, this);
        if(!driver.getCurrentUrl().contains(baseUrl)) {
            driver.get(baseUrl);
        }
    }

    @Override
    protected void isLoaded() throws Error {

        PageLoad.validPageTitle(driver, "SmartSite - Lupe - Podsumowanie [Białystok]");

        System.out.println("INFO: Rozpoczete sprawdzanie istniejacych Okien dla wykresow");
        List<WebElement> windowList;
        windowList = driver.findElements(By.xpath(windowLocator));

        if(!PageLoad.elementsAreDisplayed(driver, windowList)) {
            throw new Error("ERROR: Okna dla wykresów '/mcity/admin/index/index', nie wszystkie elementy sa widoczne");
        }

        if(windowList.size() != expectedWindowsCount) {
            throw new Error("ERROR: Zaladowaly sie tylko '"+windowList.size()+"' Okna dla wykresow, spodziewane '"+expectedWindowsCount+"'");
        }

        System.out.println("INFO: Udalo sie zaladowac AdminSummaryPage ("+driver.getCurrentUrl()+")");
    }

    @Override
    protected void load() {
    }

    /* public String getPageHeaderText () {
        return pageHeader.getText();
    } */

    private static void waitForChart (WebDriver driver, String locatorString, Integer timeoutInSeconds) {
        try
        {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorString)));
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie wyswietlic '"+locatorString+"' w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public void verifyCharts (int timeoutForEachChart){

        this.waitForChart(driver, chart1Locator, timeoutForEachChart);
        this.waitForChart(driver, chart2Locator, timeoutForEachChart);
        this.waitForChart(driver, chart3Locator, timeoutForEachChart);
        this.waitForChart(driver, chart4Locator, timeoutForEachChart);
        this.waitForChart(driver, chart5Locator, timeoutForEachChart);

        //nazwy ramek
        List<WebElement> elementList;
        System.out.println("INFO: Nazwy okien/ramek:");
        elementList = driver.findElements(By.xpath(windowLocator));
        for(WebElement element:elementList) {
            System.out.println(element.findElement(By.tagName("h5")).getText());
        }

        System.out.println("INFO: Udalo sie zaladowac wszystkie wykresy");
    }

}