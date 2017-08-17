package helpers;

import org.openqa.selenium.*;

import java.util.List;

public class MenuHelper {

    private static final String navlistLocator = "//ul[contains(@class, 'nav nav-list')]";

    public static boolean isNavlistCorrect (WebDriver driver, Integer expectedMenuElements, Integer timeoutInSeconds, boolean printMenuItems) {

        try {
            WebElement navlist = driver.findElement(By.xpath(navlistLocator));
            int menuItemsCount = navlist.findElements(By.tagName("li")).size();
            if(menuItemsCount == 0) {
                throw new Error("ERROR: Nie wyswietlil sie zaden element (li) na menu!");
            }
            if(menuItemsCount == expectedMenuElements) {
                System.out.println("INFO: Znaleziono " + menuItemsCount + " elementow (li) na menu");
            }
            if(printMenuItems) {
                System.out.println(menuToString(navlist));
            }
            if(menuItemsCount != expectedMenuElements) {
                throw new Error("ERROR: Znaleziono " +menuItemsCount+" spodziwana liczba "+expectedMenuElements);
            }
            return true;
        }
        catch (WebDriverException ex)
        {
            return false;
        }
    }

    private static String menuToString (WebElement navList) {

        String resultText = "";
        List<WebElement> menuItemList = navList.findElements(By.tagName("li"));
        for(WebElement item:menuItemList) {
            if(item.getText().equals("")) {
                //resultText += item.toString();
                //WebElement subListUL = item.findElement(By.tagName("ul"));
                //List<WebElement> subMenuList =  item.findElement(By.tagName("ul")).findElements(By.tagName("li"));
                /*for(WebElement subItem:subMenuList) {
                    resultText += "\n -> "+subItem.getText();
                }*/
                resultText += "\n -> "+item.getText();
                continue;
            }
            else {
                resultText += "\n"+item.getText();
                //item.findElement(By.tagName("a")).click();
            }
        }

        return resultText;
    }

}
