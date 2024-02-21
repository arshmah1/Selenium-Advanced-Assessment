import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WaitConditions {
    public static void waitForAvailabilityOfAllElements(WebDriver driver , Integer seconds){
        WebDriverWait wait = new WebDriverWait(driver , Duration.ofSeconds(seconds));
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));

    }
}
