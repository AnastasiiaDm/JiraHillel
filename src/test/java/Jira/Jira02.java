package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.System.setProperty;

public class Jira02 {
    static WebDriver browser;
    static String newIssuePath;
    static Helper h;
    static String newIssueSummary = "AutoTest " + Helper.timeStamp();


    @BeforeTest
    public static void openBrowser() {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get(TestData.baseURL);
        browser.manage().window().maximize();
        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        h = new Helper(browser);
    }

    //        @AfterTest
//    public static void quit() {
//        browser.quit();
//    }
    private static void login(boolean correctPass) {

        String pass = (correctPass ? TestData.pass : TestData.badPass) + "\n";
        h.findAndFill(By.cssSelector("input#login-form-username"), TestData.username);
        h.findAndFill(By.cssSelector("input#login-form-password"), pass);
    }


    @Test(priority = 1)
    public static void loginSuccess() throws InterruptedException {
        login(true);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Thread.sleep(5000);
        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
        System.out.println("Login Success");
    }

    @Test(priority = -1)
    public static void loginFail() {
        login(false);

        // Some error message is present
        List<WebElement> errorMessages = browser.findElements(By.cssSelector("div#usernameerror"));
        Assert.assertTrue(errorMessages.size() > 0);
        System.out.println("Login failed, error message present");

        // Logged-in UI is not present
        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertFalse(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
        System.out.println("Login failed, username not present");
    }

    @Test(priority = 2)
    public static void createIssue() throws InterruptedException {
        browser.findElement(By.cssSelector("a#create_link")).click();
        h.findAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");
        Thread.sleep(3000);

        h.findAndFill(By.cssSelector("input#summary"), newIssueSummary).submit();

        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a[class='issue-created-key issue-link']"));

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");
    }

    @Test(priority = 3)
    public static void openIssue() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));
    }

    @Test(priority = 4)
    public static void uploadFile(String s) throws InterruptedException, AWTException {
        WebElement uploadButton = browser.findElement(By.cssSelector("button.issue-drop-zone__button"));
        Thread.sleep(3000);
        uploadButton.click();
//        Robot robot = new Robot();
//        robot.keyRelease(Integer.parseInt("C:\\Users\\ASUS\\Desktop\\JIRAlogo.jpg"));

//        uploadFile("C:\\Users\\ASUS\\Desktop\\JIRAlogo.jpg");



    }


}
