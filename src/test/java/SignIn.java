import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.util.List;
import java.util.Set;

public class SignIn {
    private static WebDriver browser;

    public static void main(String[] args) throws InterruptedException, java.lang.NullPointerException {

       openBrowser();

        enterUserName();


        quit();
    }
    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\user\\Downloads\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("http://jira.hillel.it:8080/");
        browser.manage().window().maximize();
        Thread.sleep(1500);
    }
    public static void quit(){
        browser.quit();


    }
    public static void enterUserName() throws java.lang.NullPointerException, InterruptedException {

        browser.findElement(By.cssSelector("input[id='login-form-username']")).sendKeys("autorob");
//        Thread.sleep(1500);

        browser.findElement(By.cssSelector("input[id='login-form-password']")).sendKeys("forautotests");
//        Thread.sleep(1500);

        browser.findElement(By.cssSelector("input[id='login']")).click();
        Thread.sleep(1500);

    }

}