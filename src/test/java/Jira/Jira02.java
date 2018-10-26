package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.*;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class Jira02 extends TestBase{
    static String newIssuePath;
    static String newIssueSummary = "AutoTest " + Helper.timeStamp();
    static String attachmentLink;
    static String downloadedFileName;

    private static void login(boolean correctPass) {

        String pass = (correctPass ? TestData.pass : TestData.badPass) + "\n";
        browser.get(TestData.baseURL);

        h.findAndFill(By.cssSelector("input#login-form-username"), TestData.username);
        h.findAndFill(By.cssSelector("input#login-form-password"), pass);
    }

    @Test()
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

    @Test()
    public static void loginSuccess() {
        login(true);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
        Assert.assertTrue(
                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
        System.out.println("Login Success");
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void createIssue() {
       WebElement createButton = browser.findElement(By.cssSelector("a#create_link"));
       createButton.click();
        h.findAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");

        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.findAndFill(By.cssSelector("input#summary"), newIssueSummary))
                .submit();

        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a[class='issue-created-key issue-link']"));

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }

    @Test(dependsOnMethods = {"createIssue"})
    public static void openIssue() {
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));

        System.out.println("openIssue success");
    }

    @Test(dependsOnMethods = {"openIssue"})
    public  void uploadFile() {
        browser
                .findElement(By.cssSelector("input.issue-drop-zone__file"))
                .sendKeys(TestData.attachmentFileLocation + TestData.attachmentFileName);

        WebElement linkAttachment =
                new FluentWait<>(browser)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofSeconds(2))
                        .ignoring(NoSuchElementException.class)
                        .until(browser ->
                                browser.findElement(By.cssSelector("a.attachment-title")));

        Assert.assertEquals(TestData.attachmentFileName,  linkAttachment.getText());

        attachmentLink = linkAttachment.getAttribute("href");
    }

    @Test (dependsOnMethods = {"uploadFile"})
    static void downloadFile(){
        browser.findElement(By.cssSelector("a.attachment-title")).click();
        browser.findElement(By.cssSelector("a#cp-control-panel-download")).click();

//        downloadedFileName =
//
//        Assert.assertEquals(TestData.downloadedFileLocation, );
    }


}
