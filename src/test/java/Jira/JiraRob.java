package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

public class JiraRob
{
    static WebDriver browser;

    public static void openBrowser() {
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    public static void login() throws InterruptedException {
        browser.get("http://jira.hillel.it:8080");

        findAndFill(By.cssSelector("input#login-form-username"), "autorob");
        findAndFill(By.cssSelector("input#login-form-password"), "forautotests\n");

        Thread.sleep(4000);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));

        if (buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob")) {
            System.out.println("Login Passed");
        } else {
            System.out.println("Login Failed");
        }
    }

    public static void createIssue() throws InterruptedException {
        browser.findElement(By.cssSelector("a#create_link")).click();
        Thread.sleep(8000);
        findAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");
        Thread.sleep(2000);

        findAndFill(By.cssSelector("input#summary"), "Auto Test").submit(); // Add current timestamp to the
        // Summary

    }

    @Test
    public static void test() throws InterruptedException {
        // System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        openBrowser();
        login();
        createIssue();

        Thread.sleep(4500);
        browser.quit();
    }

    public static WebElement findAndFill(By selector, String value) {

        WebElement elem = browser.findElement(selector);

        elem.sendKeys(value);
        return elem;
    }
}
