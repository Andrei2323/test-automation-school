package test.automation.school.classexamples.pageobjectexample.loginform;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FuncTest {

    private WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterMethod
    public void cleanUp() {
        driver.quit();
    }

    @Test
    public void test() {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = loginPage.loginAs("user", "pwd");

    }
}
