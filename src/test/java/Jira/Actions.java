package Jira;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public class Actions extends TestBase {
    static String attachmentLink;
    static String newIssuePath;
    static String newIssueSummary = "AutoTest " + Helper.timeStamp();


    static void login(boolean correctPass) {

        String pass = (correctPass ? TestData.pass : TestData.badPass) + "\n";
        TestBase.browser.get(TestData.baseURL);

        h.findAndFill(By.cssSelector("input#login-form-username"), TestData.username);
        h.findAndFill(By.cssSelector("input#login-form-password"), pass);
    }

    static void waitSummarySubmit() {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.findAndFill(By.cssSelector("input#summary"), TestData.newIssueSummary))
                .submit();
    }

    static void waitLinkAttachment() {
        WebElement linkAttachment =
                new FluentWait<>(browser)
                        .withTimeout(Duration.ofSeconds(10))
                        .pollingEvery(Duration.ofSeconds(2))
                        .ignoring(NoSuchElementException.class)
                        .until(browser ->
                                browser.findElement(By.cssSelector("a.attachment-title")));

        Assert.assertEquals(TestData.attachmentFileName, linkAttachment.getText());

        attachmentLink = linkAttachment.getAttribute("href");
    }

    static void loginFailCheck() {
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

    static void loginSuccessCheck() {
//        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));
//        Assert.assertTrue(
//                buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob"));
//        System.out.println("Profile.size: " + buttonProfile.size() + " Login Success");
    }

    static void projectSelect()  {
        System.out.println("projectSelect start");
       WebElement createButton = browser.findElement(By.cssSelector("a#create_link"));
        System.out.println("createButton size: " + createButton.getText());
        createButton.click();

        h.findAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");
    }
    static void createIssueCheck() {
        List<WebElement> linkNewIssues = browser.findElements(By.cssSelector("a[class='issue-created-key issue-link']"));

        Assert.assertTrue(linkNewIssues.size() != 0);

        newIssuePath = linkNewIssues.get(0).getAttribute("href");

        System.out.println("New issue Summary: " + newIssueSummary + "\n" + "createIssue success");
    }
    static void openIssueCheck(){
        browser.get(newIssuePath);
        Assert.assertTrue(browser.getTitle().contains(newIssueSummary));

        System.out.println("openIssue success");
    }
    static void uploadFileCheck(){
        browser
                .findElement(By.cssSelector("input.issue-drop-zone__file"))
                .sendKeys(TestData.attachmentFileLocation + TestData.attachmentFileName);
    }
    }
