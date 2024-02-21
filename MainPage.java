import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainPage {
    WebDriver driver;
    Set<String> allHandlesSet;
    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    public void goToLambdaTestPage(WebDriver driver){
        //1.Navigate to lambdatest.com using provided URL
        driver.get(Locators.MainPageURL);
        //2.perform explicit wait till all elements in the dom are avail
        WaitConditions.waitForAvailabilityOfAllElements(driver , 5);
    }
    public void scrollToSeeAllIntegrations(WebDriver driver){
        //3.scroll to the webelement 'see all integrations' using scrollintoview() method
        WebElement seeAllIntegrations = driver.findElement(By.xpath(Locators.SeeAllIntegrationsXpath));
        Actions actions = new Actions(driver);
        actions.scrollToElement(seeAllIntegrations);
        //close cookie bar first that keeps interrupting me
        driver.findElement(By.xpath(Locators.closeCookieBarXpath)).click();
        //4.Click on link and ensure it opens in new tab(ensuring in my test case)
        actions.keyDown(Keys.CONTROL).click(seeAllIntegrations).keyUp(Keys.CONTROL).perform();
    }

    public List<String> getAllWindowHandles(WebDriver driver){
        //5. Save the window handles in a List (or array). Print the window handles
        //of the opened windows (now there are two windows open)
        allHandlesSet = driver.getWindowHandles();
        List<String> allHandlesList = new ArrayList<>(allHandlesSet);
        return allHandlesList;
        //6.assert with expected URL(Cannot access what the expected URL is given)
    }

    public void goToCodelessAutomation(WebDriver driver){
        //7.On that page, scroll to the page where the WebElement
        //(Codeless Automation) is present.
        WebElement codelessAutomationHeading = driver.findElement(By.xpath(Locators.codelessAutomationHeading));
        Actions actions = new Actions(driver);
        actions.scrollToElement(codelessAutomationHeading);
    }
    public void goToLearnMoreTestingWhiz(WebDriver driver){
        //8. Click the ‘LEARN MORE’ link for Testing Whiz. The page should open
        //in the same window.
        WebElement integrateTestingWhiz = driver.findElement(By.xpath(Locators.integrateTestingWhiz));
        Actions actions = new Actions(driver);
        actions.scrollToElement(integrateTestingWhiz);
        integrateTestingWhiz.click();
    }

    public void goToLambdaTestBlog(WebDriver driver){
        //12. On the current window, set the URL to https://www.lambdatest.com/blog.
        driver.get(Locators.lambdaTestBlogURL);
    }
    public void clickOnCommunity(WebDriver driver){
        //13. Click on the ‘Community’ link
        driver.findElement(By.xpath(Locators.communityLink)).click();
    }

}
