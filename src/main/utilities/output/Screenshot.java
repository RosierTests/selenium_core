package utilities.output;

import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.ashot.AShot;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created By: bsmith on 7/16/14.
 * Package: utilities.output
 * Description: This class is used to create screen shots.
 */
public class Screenshot{
    private WebDriver localDriver;

    public Screenshot(WebDriver driver){ this.localDriver = driver; }

    @Attachment(value = "Page")
    public byte[] getScreenShot() {
        ru.yandex.qatools.ashot.Screenshot s = new AShot().takeScreenshot(localDriver);

        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            ImageIO.write(s.getImage(), "png", stream);
            stream.flush();
            byte[] image = stream.toByteArray();
            stream.close();
            return image;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
