import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.logging.Log;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Test(timeOut = 20000)
public class Tests {
    WebDriver driver;
    MainPage mainPage = new MainPage(driver);
    List<String> allHandlesList = new ArrayList<>();
    Logger log = Logger.getLogger(Tests.class.getName());
    ExtentReports extentReports;
    ExtentTest test;

    @BeforeTest
    @Parameters({"browser"})
    public void setUp(String browser){

        if ("chrome".equalsIgnoreCase(browser)){
            ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("AllTests.html");
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter_all);
            test = extentReports.createTest(getClass().getSimpleName());

            test.log(Status.INFO,"setting browsers up");
            log.info("setting browser up");
//            WebDriverManager.chromedriver().setup();
//            driver = new ChromeDriver();
           // chromeOptions.setCapability("platformName","Windows 10");
            DesiredCapabilities desiredCapabilities= new DesiredCapabilities();
            desiredCapabilities.setBrowserName("chrome");
            //desiredCapabilities.setVersion(String.valueOf(86.0));
            desiredCapabilities.setPlatform(Platform.WIN10);
            try {
                driver = new RemoteWebDriver(new URL("http://192.168.0.121:4444"),desiredCapabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }else if("edge".equalsIgnoreCase(browser)){
            ExtentSparkReporter sparkReporter_all = new ExtentSparkReporter("AllTests2.html");
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter_all);
            test = extentReports.createTest(getClass().getSimpleName());

            test.log(Status.INFO,"setting browsers up");
            log.info("setting browser up");
//            WebDriverManager.firefoxdriver().setup();
//            driver = new FirefoxDriver();
            DesiredCapabilities desiredCapabilities= new DesiredCapabilities();
            desiredCapabilities.setBrowserName("firefox");
            //desiredCapabilities.setVersion(String.valueOf(87.0));
            desiredCapabilities.setPlatform(Platform.WIN10);
            try {
                driver = new RemoteWebDriver(new URL("http://192.168.0.121:4444"),desiredCapabilities);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
        driver.manage().window().maximize();

    }
    @Test(priority = 1)
    public void openLambdaTestPageTest(){
        test.log(Status.INFO,"going to Lambda Test HomePage and waiting for it to load");
        log.info("going to Lambda Test HomePage and waiting for it to load");
        mainPage.goToLambdaTestPage(driver);
        log.info("test passed");
        test.log(Status.INFO,"test passed");

    }
    @Test(priority = 2)
    public void openAllIntegrationsInNewTabTest(){
        test.log(Status.INFO,"scrolling to all integrations element and opening it in a new tab");
        log.info("scrolling to all integrations element and opening it in a new tab");
        mainPage.scrollToSeeAllIntegrations(driver);
        test.log(Status.INFO,"getting all handles");
        log.info("getting all handles");
        allHandlesList = mainPage.getAllWindowHandles(driver);
        System.out.println("Handles: " + allHandlesList);
        Assert.assertEquals(allHandlesList.size(),2,"Link not opened in new Page, There is only one open tab");
        log.info("Test passsed");
        test.log(Status.INFO,"Test passsed");
    }
    @Test(priority = 3)
    public void OpenCodelessAutomationTest(){
        test.log(Status.INFO,"switching to tab 2");
        log.info("switching to tab 2");
        driver.switchTo().window(allHandlesList.get(1));
        test.log(Status.INFO,"scrolling to codeless automation header and then to testing whiz");
        log.info("scrolling to codeless automation header and then to testing whiz");
        mainPage.goToCodelessAutomation(driver);
        test.log(Status.INFO,"selecting testing whiz more info");
        log.info("selecting testing whiz more info");
        mainPage.goToLearnMoreTestingWhiz(driver);
        Assert.assertEquals(driver.getTitle(),"Running Automation Tests Using TestingWhiz LambdaTest | LambdaTest","Current Page Title is not the same as expected");
        test.log(Status.INFO,"test passed");
        log.info("test passed");
    }
    @Test(priority = 4)
    public void openNewURLTest(){
        test.log(Status.INFO,"closing tab 2");
        log.info("closing tab 2");
        driver.close();
        test.log(Status.INFO,"getting handles");
        log.info("getting handles");
        allHandlesList = mainPage.getAllWindowHandles(driver);
        System.out.println("Handles: " + allHandlesList);
        test.log(Status.INFO,"switching to tab 1");
        log.info("switching to tab 1");
        driver.switchTo().window(allHandlesList.get(0));
        test.log(Status.INFO,"opening lamba testblg using url");
        log.info("opening lamba testblg using url");
        mainPage.goToLambdaTestBlog(driver);
        test.log(Status.INFO,"test passed");
        log.info("test passed");
    }
    @Test(priority = 5)
    public void clickOnCommunityTest(){
        test.log(Status.INFO,"going to community section");
        log.info("going to community section");
        mainPage.clickOnCommunity(driver);
        Assert.assertEquals(driver.getCurrentUrl(),"https://community.lambdatest.com/" , "Current URL is not matched with expected URL");
        test.log(Status.INFO,"test passed");
        log.info("test passed");
    }
    @AfterTest
    public void tearDown(){
        driver.quit();
        test.log(Status.INFO,"closed browser");
        log.info("closed browser");
        extentReports.flush();
        try {
            Desktop.getDesktop().browse(new File("AllTests.html").toURI());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
//    @BeforeTest
//    public void initialiseExtentReports(){
//
//    }
//
//    @AfterTest
//    public void generateExtentReports(){
//
//    }
//    @BeforeMethod
//    public void beforeMethod(Method method){
//
//    }
}
