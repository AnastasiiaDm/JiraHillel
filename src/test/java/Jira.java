import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Jira {
    private static WebDriver browser;

    public static void main(String[] args) throws InterruptedException {

       openBrowser();

        enterUserName();

        checkTrue();

        createIssue();

        checkCreateSuccess ();

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

    public static void enterUserName() throws InterruptedException {

        browser.findElement(By.cssSelector("input[id='login-form-username']")).sendKeys("autorob");

        browser.findElement(By.cssSelector("input[id='login-form-password']")).sendKeys("forautotests");

        browser.findElement(By.cssSelector("input[id='login']")).click();
        Thread.sleep(3000);
    }

     public static void checkTrue() {
         WebElement createButton = browser.findElement(By.cssSelector("a[id='create_link']"));

         if (createButton.isEnabled()) {
             System.out.println("Login Success");
         } else {
             System.out.println("не может быть !!!");
         }

     }

     public static void createIssue() throws InterruptedException {
         browser.findElement(By.cssSelector("a[id='create_link']")).click();
         Thread.sleep(3000);

         WebElement summaryEnter = browser.findElement(By.cssSelector("input[id='summary']"));
         summaryEnter.sendKeys("Test Nastya");
     }

     public static void checkCreateSuccess () throws InterruptedException {

         WebElement createClick = browser.findElement(By.cssSelector("input[id='create-issue-submit']"));
         createClick.click();
         Thread.sleep(3000);

         WebElement popupSuccess = browser.findElement(By.cssSelector("div[class='aui-message aui-message-success success closeable shadowed aui-will-close']"));

         if (popupSuccess.isDisplayed()){
             System.out.println("\n" + "Issue created Successfully");
         }
     }

}