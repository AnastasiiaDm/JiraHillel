package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

public class Jira02 extends TestBase {
    static String downloadedFileName;

   @Test(priority = -1)
    public static void loginFail() {
        Actions.login(false);

        Actions.loginFailCheck();
    }

    @Test(priority = 1)
    public static void loginSuccess() {
        Actions.login(true);

        Actions.loginSuccessCheck();
    }

    @Test(dependsOnMethods = {"loginSuccess"})
    public static void createIssue() {

        Actions.projectSelect();

        Actions.waitSummarySubmit();

        Actions.createIssueCheck();
    }

    @Test(dependsOnMethods = {"createIssue"})
    public static void openIssue() {
        Actions.openIssueCheck();
    }

    @Test(dependsOnMethods = {"openIssue"})
    public void uploadFile() {
        Actions.uploadFileCheck();
        Actions.waitLinkAttachment();
    }

    @Test(dependsOnMethods = {"uploadFile"})
    static void downloadFile() {

        browser.findElement(By.cssSelector("a.attachment-title")).submit();
//        browser.findElement(By.cssSelector("a#cp-control-panel-download")).click();

//        downloadedFileName =
//
//        Assert.assertEquals(TestData.downloadedFileLocation, );
    }


}
