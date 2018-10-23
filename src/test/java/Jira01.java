import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Jira01 {

    private static WebDriver browser;

    public static void main(String[] args) throws InterruptedException {

        openBrowser();

        enterUserName();

        boolean isExistCreateButton = createButtonExistFail();
        if (isExistCreateButton) {
            createButtonExistSuccess();
            createIssue();
            clickCreateIssue();
        }

        boolean isIssueCreate = issueCreateFail();
        if (isIssueCreate) {
            issueCreateSuccess();
        }

        quit();
    }

    public static void openBrowser() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\Nasik\\для Java\\chromedriver.exe");
        browser = new ChromeDriver();
        browser.get("http://jira.hillel.it:8080/");
        browser.manage().window().maximize();
        Thread.sleep(1500);
    }

    public static void quit() {
        browser.quit();
    }

    public static void enterUserName() throws InterruptedException {

        browser.findElement(By.cssSelector("input[id='login-form-username']")).sendKeys("autorob");
        WebElement inputPass =  browser.findElement(By.cssSelector("input[id='login-form-password']"));

//       ввести текст + Enter
        inputPass.sendKeys("forautotests\n");

//        нажать на активную кнопку
//        inputPass.submit();

//        browser.findElement(By.cssSelector("input[id='login']")).click();
//        Thread.sleep(3000);
    }

    public static boolean createButtonExistFail() {

        try {
            browser.findElement(By.cssSelector("a[id='create_link']")).isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("\n" + "Login failed");
            return false;
        }
    }

    public static void createButtonExistSuccess() {
        if (browser.findElement(By.cssSelector("a[id='create_link']")).isEnabled()) {
            System.out.println("\n" + "Login Success");
        }
    }

    public static void createIssue() throws InterruptedException {
        browser.findElement(By.cssSelector("a[id='create_link']")).click();
        Thread.sleep(3000);

        WebElement summaryEnter = browser.findElement(By.cssSelector("input[id='summary']"));
        summaryEnter.sendKeys("Test Nastya");
    }

    public static void clickCreateIssue() throws InterruptedException {

        WebElement createClick = browser.findElement(By.cssSelector("input[id='create-issue-submit']"));
        createClick.click();
        Thread.sleep(3000);
    }

    public static boolean issueCreateFail() {
        try {
            WebElement popupSuccess = browser.findElement(By.cssSelector("div[class='aui-message aui-message-success success closeable shadowed aui-will-close']"));
            popupSuccess.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            System.out.println("\n" + "Create issue Failed");
            return false;
        }
    }

    public static void issueCreateSuccess() {

        WebElement popupSuccess = browser.findElement(By.cssSelector("div[class='aui-message aui-message-success success closeable shadowed aui-will-close']"));

        if (popupSuccess.isDisplayed()) {
            System.out.println("\n" + "Issue created Successfully");
        }
    }
}