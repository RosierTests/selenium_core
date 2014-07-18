package utilities.output;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Attachment;

/**
 * Created By: bsmith on 7/16/14.
 * Package: utilities.output
 * Description: This class is used to create screen shots.
 */
public class Screenshot{
    private WebDriver localDriver;

    public Screenshot(WebDriver driver){ this.localDriver = driver; }

    public void getScreenShot() {
        TakesScreenshot shooter = (TakesScreenshot)localDriver;
        byte[] rawShot = shooter.getScreenshotAs(OutputType.BYTES);
        saveScreenShot(rawShot);
    }

    @Attachment(type = "image/png")
    public byte[] saveScreenShot(byte[] screenShot) {
        return screenShot;
    }
}
