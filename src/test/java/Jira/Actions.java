package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.NoSuchElementException;

public class Actions extends TestBase {
    static String attachmentLink;

    static void login(boolean correctPass)   {

        String pass = (correctPass ? TestData.pass : TestData.badPass) + "\n";
        TestBase.browser.get(TestData.baseURL);

        h.findAndFill(By.cssSelector("input#login-form-username"), TestData.username);
        h.findAndFill(By.cssSelector("input#login-form-password"), pass);
    }

    static void summarySubmit () {
        new FluentWait<>(browser)
                .withTimeout(Duration.ofSeconds(7))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(InvalidElementStateException.class)
                .until(browser -> h.findAndFill(By.cssSelector("input#summary"), TestData.newIssueSummary))
                .submit();
    }
    static void linkAttachment(){
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

}
