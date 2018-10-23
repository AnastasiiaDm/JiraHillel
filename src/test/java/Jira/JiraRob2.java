package Jira;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static java.lang.System.setProperty;

public class JiraRob2 {

    static WebDriver browser;
    static String newIssueSummary = "AutoTest " + new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date());
    static String newIssuePath;

    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");

        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    @Test
    public static void login() throws InterruptedException {
        browser.get("http://jira.hillel.it:8080/");

        findAndFill(By.cssSelector("input#login-form-username"), "autorob");
        findAndFill(By.cssSelector("input#login-form-password"), "forautotests\n");

        Thread.sleep(4000);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));

        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
    }

    @Test
    public static void createIssue() throws InterruptedException {
        browser.findElement(By.cssSelector("a#create_link")).click();
        Thread.sleep(8000);
        findAndFill(By.cssSelector("input#project-field"), "General Robert QA (GQR)\n");
        Thread.sleep(5000);

        findAndFill(By.cssSelector("input#summary"), newIssueSummary).submit(); // Add current timestamp to the Summary

        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a.issue-created-key"));

        if (linkNewIssues.size() != 0) {
            System.out.println("Create Issue Passed");
        } else {
            System.out.println("Create Issue Failed");
        }

        newIssuePath = linkNewIssues.get(0).getAttribute("href");
    }

    @Test
    public static void openIssue() {
        browser.get(newIssuePath);

        if (browser.getTitle().contains(newIssueSummary)) {
            System.out.println("Open Issue Passed");
        } else {
            System.out.println("Open Issue Failed");
        }
    }

    @AfterTest
    public static void closeBrowser() throws InterruptedException {
        Thread.sleep(4500);
        browser.quit();
    }

    private static WebElement findAndFill(By selector, String value) {
        WebElement elem = browser.findElement(selector);

        elem.sendKeys(value);
        return elem;
    }
}
