import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;
import static java.lang.System.setOut;
import static java.lang.System.setProperty;

public class Jira02 {
    private static WebDriver browser;


    public static void main(String[] args) throws InterruptedException {

        openBrowser();
        login();
        createIssue();

        quit();
    }

    public static void openBrowser() throws InterruptedException {
        setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("http://jira.hillel.it:8080/");
        browser.manage().window().maximize();
        Thread.sleep(1500);
    }

    public static void quit() {
        browser.quit();
    }

    public static void login() throws InterruptedException {

        findAndFill(By.cssSelector("input#login-form-username"), "autorob");
        findAndFill(By.cssSelector("input#login-form-password"), "forautotests\n");

        Thread.sleep(5000);

        List<WebElement> buttonProfile = browser.findElements(By.cssSelector("a#header-details-user-fullname"));

        if (buttonProfile.size() > 0 && buttonProfile.get(0).getAttribute("data-username").equals("autorob")) {
            out.println("Login Passed");
        } else {
            out.println("Login Failed");
        }
    }

    public static void createIssue() throws InterruptedException {
        browser.findElement(By.cssSelector("a#create_link")).click();
        Thread.sleep(5000);
        findAndFill(By.cssSelector("input#project-field"), "General QA Robert (GQR)\n");
        Thread.sleep(2000);

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);

        String taskName = "Auto Test " + localDateTime;

                findAndFill(By.cssSelector("input#summary"), taskName).submit();
        Thread.sleep(2000);


        List<WebElement> popupSuccess = browser.findElements(By.cssSelector("a[class='issue-created-key issue-link']"));

        if (popupSuccess.size() > 0 && popupSuccess.get(0).isDisplayed()) {
            out.println("Popup Passed");

            WebElement popup = browser.findElement(By.cssSelector("a[class='issue-created-key issue-link']"));
            popup.click();

            WebElement issueSummary = browser.findElement(By.cssSelector("h1#summary-val"));

            if (taskName  == issueSummary.getText()){
                System.out.println("Summary names match:" + "\n" + taskName + "\n" + issueSummary.getText());;
            }
            else{
                System.out.println("Summary names don't match:" + "\n" + taskName + "\n" + issueSummary.getText());;

            }
        } else {
            out.println("Popup Failed");
        }
    }

    public static WebElement findAndFill(By selector, String value) {

        WebElement elem = browser.findElement(selector);
        elem.sendKeys(value);
        return elem;
    }


}
