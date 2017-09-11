package test.automation.school;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import test.automation.school.core.DriverFactory;
import test.automation.school.listeners.ScreenshotListener;
import test.automation.school.utils.PropertyLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Listeners(ScreenshotListener.class)
public class BaseTest {

    private static final String APPLICATION_URL = PropertyLoader.loadProperty("application.url");
    private static List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<DriverFactory>());
    private static ThreadLocal<DriverFactory> driverFactory;

    @BeforeSuite(alwaysRun = true)
    public static void instantiateDriverObject() {
        driverFactory = new ThreadLocal<DriverFactory>() {
            @Override
            protected DriverFactory initialValue() {
                DriverFactory driverFactory = new DriverFactory();
                webDriverThreadPool.add(driverFactory);
                return driverFactory;
            }
        };
    }

    public static WebDriver getDriver() throws Exception {
        return driverFactory.get().getDriver();
    }

    @AfterMethod(alwaysRun = true)
    public static void clearCookies() throws Exception {
        getDriver().manage().deleteAllCookies();
    }

    @AfterSuite(alwaysRun = true)
    public static void closeDriverObjects() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitDriver();
        }
    }

    @BeforeMethod(alwaysRun = true)
    public void setup() throws Exception {
        getDriver().get(APPLICATION_URL);
        getDriver().manage().window().maximize();
    }
}