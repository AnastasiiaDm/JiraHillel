package Jira;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

public class TestBase {
    public static WebDriver browser;
    public static Helper h;

    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        browser = new ChromeDriver();
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

//    @AfterTest
//    public static void closeBrowser() {
//        browser.quit();
//
//    }
}
