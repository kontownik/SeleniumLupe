package helpers;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import utility.PropertyValues;

public class PageLoad {

    public static PropertyValues properties = new PropertyValues();
    
    public static boolean myElementIsClickable (WebDriver driver, By locator, Integer timeoutInSeconds) {
        
        try
        {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
        }
        catch (WebDriverException ex)
        {
            return false;
        }
        return true;        
    }

    public static void confirmModal (WebDriver driver, Integer timeoutInSeconds) {

        try
        {
            //List<WebElement> modalConfirmsList = driver.findElements(By.xpath("//button[@class='btn no-border btn-sm btn-primary']")); //pare modali?
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn no-border btn-sm btn-primary']")));
            WebElement confirmButton = driver.findElement(By.xpath("//button[@class='btn no-border btn-sm btn-primary']"));
            confirmButton.click();
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie potwierdzic przycisku okna modalnego w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public static void confirmModalWithoutClass (WebDriver driver, Integer timeoutInSeconds) { //TODO: do wywalenia w przyszlosci

        try
        {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary']")));
            WebElement confirmButton = driver.findElement(By.xpath("//button[@class='btn btn-primary']"));
            confirmButton.click();
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie potwierdzic przycisku okna modalnego w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public static void confirmPublicModal (WebDriver driver, Integer timeoutInSeconds) { //TODO: do wywalenia w przyszlosci

        try
        {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn btn-primary']")));
            WebElement confirmButton = driver.findElement(By.xpath("//button[@class='btn btn btn-primary']"));
            confirmButton.click();
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie potwierdzic przycisku okna modalnego w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public static void verifyMcityPreviewModal (WebDriver driver, Integer timeoutInSeconds, List<String> expectedTextList) {    //TODO: mozna to poprawic i rozszerzyc

        try
        {
            new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='btn btn-primary no-border']")));
            //dodatkowa weryfikacja pól
            String previewTableLocator = "//table[contains(concat(' ', normalize-space(@class), ' '), ' mcity-preview-table ')]";
            WebElement previewTable = driver.findElement(By.xpath(previewTableLocator));
            for(String expectedText:expectedTextList) {
                if(previewTable.getText().contains(expectedText)) {
                    System.out.println("INFO: Znaleziono tekst '"+expectedText+"' w tabeli.");
                }
                else {
                    throw new Error("ERROR: Brak tekstu '"+expectedText+"' w tabeli.");
                }
            }
            //WebElement editButton = driver.findElement(By.xpath("//button[@class='btn btn-primary no-border']"));
            //editButton.click();
            WebElement editButton = driver.findElement(By.id("preview-edit-button"));
            if(!editButton.isDisplayed() || !editButton.isEnabled()) {
                throw new Error("ERROR: Przycisk 'Edytuj' na 'Podgląd zgłoszenia' jest niewidoczny lub nieaktywny");
            }
            else {
                System.out.println("INFO: Przycisk 'Edytuj' na 'Podgląd zgłoszenia' jest widoczny i aktywny)");
            }
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie potwierdzic przycisku okna modalnego w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public static void verifyDetailsView (WebDriver driver, Integer timeoutInSeconds, List<String> expectedTextList) {

        try {
            //Panel boczny 'Szczegoly'
            WebElement detailsViewPanel = new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(By.id("detailsView")));
            System.out.println("INFO: Panel boczny 'Szczegoly' sie wyswietlil");
            //Zawartosc
            WebElement detailsMap = new WebDriverWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(By.id("detailsMap")));
            if(!detailsMap.isDisplayed()) {
                throw new Error("ERROR: Nie udalo sie wyswietlic malej mapy na panelu bocznym 'Szczegoly' "+timeoutInSeconds+"s");
            }
            //elementy
            for(String expectedText:expectedTextList) {
                if(detailsViewPanel.getText().contains(expectedText)) {
                    System.out.println("INFO: Znaleziono tekst '"+expectedText+"' w tabeli.");
                }
                else {
                    throw new Error("ERROR: Brak tekstu '"+expectedText+"' w tabeli.");
                }
            }
        }
        catch (WebDriverException ex)
        {
            throw new Error("ERROR: Nie udalo sie potwierdzic przycisku okna modalnego w czasie "+timeoutInSeconds+"s | "+ex);
        }
    }

    public static boolean elementsAreDisplayedEnabled (WebDriver driver, List<WebElement> elementList) {
        for (WebElement element:elementList)
        {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();

            if(!element.isDisplayed() || !element.isEnabled()){
                System.out.println("ERROR: "+element.toString() + " nie przeszedl testu widoczny + aktywny"); //debug
                return false;
            }
        }

        return true;
    }

    public static boolean elementsAreDisplayed (WebDriver driver, List<WebElement> elementList) {
        for (WebElement element:elementList)
        {
            Actions action = new Actions(driver);
            action.moveToElement(element).build().perform();

            if(!element.isDisplayed()){
                System.out.println("ERROR: "+element.toString() + " nie przeszedl testu widocznosci");
                return false;
            }
        }

        return true;
    }

    public static void selectTheCheckbox(WebDriver driver, WebElement checkbox, String action) {
        try {
            if(action.equals("ACTIVATE")) {
                if (checkbox.isSelected()) {
                    System.out.println("WARNING: Element " + checkbox + " jest już zaznaczony");
                } else {
                    checkbox.click();
                }
            }
            if(action.equals("DEACTIVATE")) {
                if (!checkbox.isSelected()) {
                    System.out.println("WARNING: Element " + checkbox + " jest już odznaczony");
                } else {
                    checkbox.click();
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR: Checkbox nie może zostać zaznaczony | "+e);
        }
    }

    public static void checkSuccessFormPost(WebDriver driver) {
        if(PageLoad.errorContainerIsVisible(driver)) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: Wystapił niespodziewany błąd po wysłaniu formularza");
        }

        if(PageLoad.gritterIsVisible(driver, "gritter-error")) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: Wystapił niespodziewany błąd (gritter-error)");
        }

        if(PageLoad.gritterIsVisible(driver, "gritter-success")) { //komunikat o sukcesie
            System.out.println("INFO: Wyświetlił się komunikat o sukcesie akcji (gritter-success)");
        }
        else {
            System.out.println("WARNING: Nie wyświetlił się spodziewany komunikat o sukcesie akcji (gritter-success)");
        }

        System.out.println("INFO: Po wyslaniu formularza nie stwierdzono bledu");
    }

    public static void checkActionSuccess(WebDriver driver) {

        if(PageLoad.gritterIsVisible(driver, "gritter-error")) { //wyświetlił się błąd który nie był spodziewany
            throw new Error("ERROR: Wystapił niespodziewany błąd (gritter-error)");
        }

        if(PageLoad.gritterIsVisible(driver, "gritter-success")) { //komunikat o sukcesie
            System.out.println("INFO: Wyświetlił się komunikat o sukcesie akcji (gritter-success)");
        }
        else {
            System.out.println("WARNING: Nie wyświetlił się spodziewany komunikat o sukcesie akcji (gritter-success)");
        }

        System.out.println("INFO: Po wykonaniu akcji nie stwierdzono bledu");
    }

    public static boolean errorContainerIsVisible (WebDriver driver) {
        try {
            WebElement alertContainer = driver.findElement(By.xpath("//*[@class=\"alert alert-danger\"]"));
            if(alertContainer.isDisplayed()){
                System.out.println("INFO: Wyswietlil sie komunikat bledu 'alert alert-danger':\n" +alertContainer.getText());
                return true;
            }
            else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static boolean checkFieldsGetErrorClass (WebDriver driver, List<WebElement> elementList) {
        for (WebElement element:elementList)
        {
            String elementString = element.getAttribute("class");
            System.out.println(elementString);
            if(elementString.equals("form-group form-field-required has-error") || elementString.equals("form-group form-field-required has-error is-focused")) {
                System.out.println("ERROR: "+element.toString() + " pole zostalo zle uzupelnione");
                return true;
            }
        }

        return false;
    }


    public static boolean gritterIsVisible (WebDriver driver, String gritterType) {

        try {
            WebElement alertContainer = driver.findElement(By.xpath("//*[@class=\""+gritterType+"\"]"));
            if(alertContainer.isDisplayed()){
                System.out.println("INFO: Wyswietlil sie komunikat '"+gritterType+"':\n" +alertContainer.getText());
                return true;
            }
            else {
                return false;
            }
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public static void validPageTitle (WebDriver driver, String expectedTitle) {
        String actualTitle = driver.getTitle();
        if(!actualTitle.equals(expectedTitle)){
            throw new Error("ERROR: Niepoprawy atrybut HTML Title, spodziewany: '"+expectedTitle+"' otrzymany: '"+actualTitle+"'");
        }
    }

    public static void hideCookieWindow (WebDriver driver, String xpath) {
        try {
            driver.findElement(By.xpath(xpath)).click();
        } catch (NoSuchElementException e) {
            System.out.println("WARNING: Próba ukrycia okna Cookie się niepowiodła (NoSuchElementException)"); //debug
        }
    }

    public static void moveToElement(WebDriver driver, WebElement pageHeader, WebElement element) {
        Actions action = new Actions(driver);
        action.moveToElement(pageHeader).build().perform();
        action.moveToElement(element).build().perform();
    }

    public static void pickFromChosenSelect (WebDriver driver, WebElement pageHeader, WebElement listElement, String itemXpath) {
        moveToElement(driver, pageHeader, listElement);
        listElement.click();
        //System.out.println(itemXpath + ">>>>>\n"+listElement.getText());
        WebElement categoryList = listElement.findElement(By.xpath("//*[@class=\"chosen-results\"]"));

        //WebElement selectItem = categoryList.findElement(By.xpath(itemXpath));
        //System.out.println(categoryList.getText());
        //moveToElement(driver, pageHeader, categoryList);
        categoryList.findElement(By.xpath(itemXpath)).click();
    }

    public static void bootStrapDropDownValueSelection(WebElement elementExpand, WebElement elementDropdown, String value)
    {
        elementExpand.click();
        List<WebElement> organizationType = elementDropdown.findElements(By.className("active-result"));
        for (WebElement orgType : organizationType)
        {
            if (orgType.getText().contains(value))
            {
                orgType.click();
                break;
            }
        }
    }


    public static void pickFromSelect (WebElement listElement, String itemName) {
        try {
            Select dropdown = new Select(listElement);
            dropdown.selectByVisibleText(itemName);
            if (!dropdown.getFirstSelectedOption().getText().contains(itemName)) {
                throw new Error("ERROR: Spodziewany select z wartoscia '" + itemName + "', nie został wybrany");
            }
        } catch (NoSuchElementException e) {
            throw new Error("ERROR: Spodziewany select nie został znaleziony (NoSuchElementException)");
        }
    }

    public static boolean isSelectedOnDropdownSelect (Select dropdown, String itemName) {
        if(dropdown.getFirstSelectedOption().getText().contains(itemName)){
            return true;
        }
        else
            return false;
    }

    public static String getProjectUrl(){
        try {
            return properties.getPropValue("basic_url");
        } catch (IOException ex) {
            throw new Error("ERROR: Blad wczytywania parametru adresu URL strony (IOException)");
        }
    }

    public static void setClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(stringSelection, null);
    }

    public static void useUploadWindow(WebDriver driver, WebElement fileInput) throws Exception{
        //Dodawanie zalacznika do formularza
        fileInput.click();
        Thread.sleep(2000);
        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    public static void checkPageIsReady(WebDriver driver) { //testowo

        JavascriptExecutor js = (JavascriptExecutor)driver;


        //Initially bellow given if condition will check ready state of page.
        if (js.executeScript("return document.readyState").toString().equals("complete")){
            System.out.println("Page Is loaded.");
            return;
        }

        //This loop will rotate for 25 times to check If page Is ready after every 1 second.
        //You can replace your value with 25 If you wants to Increase or decrease wait time.
        for (int i=0; i<25; i++){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e) {}
            //To check page ready state.
            if (js.executeScript("return document.readyState").toString().equals("complete")){
                break;
            }
        }
    }



}
