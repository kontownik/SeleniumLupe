package helpers;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 * Created by Pawel on 2017-07-14.
 */
public class GridLoadedCondition implements ExpectedCondition<Boolean> {
    private String elementId;
    //private WebDriver driver;

    public GridLoadedCondition(WebDriver driver, String gridTableId) {
        //this.driver = driver;
        this.elementId = gridTableId+"_processing";
    }

    //@Override
    public Boolean apply(WebDriver driver) {

        try {
            String visibility = driver.findElement(By.id(elementId)).getCssValue("display");
            return visibility.contains("none"); //'block' wyswietla sie przy zakonczonym wyszukiwaniu
        }
        catch (Exception e) {
            throw new RuntimeException("ERROR: Grid nie skonczyl sie ladowac w wyznaczonym czasie");
        }
    }
}
