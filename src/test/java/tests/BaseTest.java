package tests;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.junit.rules.TestName;
import org.openqa.selenium.remote.DesiredCapabilities;
import utility.PropertyValues;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.fail;

public class BaseTest {
    protected static WebDriver driver = null;
    public PropertyValues properties = new PropertyValues();
    private static String browserName = System.getProperty("browserName"); //parametr przekazany z jenkinsa
    //private static String browserName = "";

    @Rule
    public TestName name = new TestName();

    @Rule
    public TestRule testWatcher = new TestWatcher() {
        @Override
        public void failed(Throwable t, Description test) {
            takeScreenshot(name.getMethodName());
        }
    };


    private static void setWebDriverProperty () {

        try {
            if (browserName.equals("Firefox")) {
                FirefoxDriverManager.getInstance().setup();
                driver = new FirefoxDriver();
            }
            if (browserName.equals("Internet Explorer 11")) {
                InternetExplorerDriverManager.getInstance().setup();

                DesiredCapabilities dc = DesiredCapabilities.internetExplorer();
                dc.setJavascriptEnabled(true);
                dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                dc.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                dc.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
                dc.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                dc.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
                dc.setCapability("ignoreZoomSetting", true);

                driver = new InternetExplorerDriver(dc);
            }
            if (browserName.equals("Edge")) {
                EdgeDriverManager.getInstance().setup();
                driver = new EdgeDriver();
            }
        /*if(browserName.equals("OperaChromium")) {
            System.setProperty("webdriver.chrome.driver", operaPath);
            driver = new ChromeDriver();
        }*/
            if (browserName.equals("Chrome")) {
                ChromeDriverManager.getInstance().setup();
                driver = new ChromeDriver();
            }
        }
        catch(NullPointerException ex) {
            ChromeDriverManager.getInstance().setup();
            driver = new ChromeDriver();
        }
    }

    public void takeScreenshot(String screenshotName) {
        if (driver instanceof TakesScreenshot) {
            File tempFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(tempFile, new File("screenshots/" + screenshotName + ".png"));
            } catch (IOException e) {
                // TODO handle exception
            }
        }
    }


    @BeforeClass
    public static void beforeClass() {
        setWebDriverProperty();
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
    }


    @AfterClass
    public static void afterClass() throws Exception {
        try {
            driver.quit();
        } catch (Exception e) {
            fail("Blad zamkniecia Webdrivera: "+e);
        }
    }


}
