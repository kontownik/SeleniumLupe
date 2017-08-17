package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GridHelper {

    public static void waitForGridReload (WebDriver driver, String gridTableId, int timeout) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        //customowy wait
        if (!wait.until(new GridLoadedCondition(driver, gridTableId))) {
            throw new RuntimeException("ERROR: Grid nie skonczyl sie ladowac w wyznaczonym czasie "+timeout+"s");
        }
    }

    public static boolean isDataTablesError () { //TODO: napisac
        return false;
    }

    public static void useGridSearch (WebDriver driver, String searchText, String gridTableId, int timeout) { //TODO: dodac tez konkretne wyszukiwanie po kolumnie np po opisie

        if(isDataTablesError ()) {
            throw new Error("ERROR: Wyswietlil sie blad DataTables!");
        }
        WebElement searchInput = driver.findElement(By.cssSelector("[type='search']"));

        //test widocznosci inputa
        Actions build = new Actions(driver);
        build.moveToElement(searchInput).build().perform();
        if(!searchInput.isDisplayed() || !searchInput.isEnabled()){
            throw new Error("ERROR: "+searchInput.toString() + " nie przeszedl testu widoczny + aktywny");
        }

        //wyszukiwanie wartosci
        searchInput.sendKeys(searchText);
        searchInput.sendKeys(Keys.ENTER);

        //wait
        waitForGridReload(driver, gridTableId, timeout);
    }

    public static boolean isColumnContainsText (WebDriver driver, String gridIdText, int columnNumber, String expectedText) {  //TODO: zrobic iteracje po wierszach

        try
        {
            //obsluga "pustego" grida
            WebElement gridEmptyCell = driver.findElement(By.xpath("//*[@id='"+gridIdText+"']/tbody/tr/td[1]"));
            if(gridEmptyCell.getText().contains("Brak danych") && columnNumber > 1) {
                System.out.println("INFO: Grid jest pusty, 'Brak danych'");
                return false;
            }

            WebElement expectedCell = driver.findElement(By.xpath("//*[@id='"+gridIdText+"']/tbody/tr/td["+columnNumber+"]"));
            System.out.println("INFO: Znaleziono tekst '"+expectedCell.getText()+"'");
            //jezeli w pierwszym wierszu nie znalazlo tzn. ze jest "brak" albo pokazalo sie cos innego
            if(expectedCell.getText().contains(expectedText)) {
                return true;
            }

            //TODO: jezeli powyzsze nie zaszlo iteracja po calej tabeli (jezeli jest wiecej niz 1 row)

            return false;
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie znalesc tekstu | "+ex);
        }

    }

    public static String getTextFromColumn (WebDriver driver, String gridIdText, String columnNumber) {

        try {
            WebElement expectedCell = driver.findElement(By.xpath("//*[@id='" + gridIdText + "']/tbody/tr/td[" + columnNumber + "]"));
            return expectedCell.getText();
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie znalesc tekstu | "+ex);
        }

    }

    public static void useGridIconAction (WebDriver driver, String gridIdText, String iconTitle) {

        try {
            String firstRowActionCell = "//*[@id='"+gridIdText+"']/tbody/tr[1]/td[position()=last()]";
            WebElement lastCell = driver.findElement(By.xpath(firstRowActionCell));
            WebElement button = lastCell.findElement(By.xpath("//a[@title = '"+iconTitle+"']"));

            button.click();

        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie uzyc akcji na gridzie | "+ex);
        }

    }

}
