package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.LoginPage;
import utilities.output.MessageLogger;

import static junit.framework.TestCase.assertTrue;

/**
 * Created By: Brian Smith on 3/18/14.
 * Package: none
 * Description: This page represents the home page.
 */
public class GatewayPage extends LoadableComponent<GatewayPage>{
    private WebDriver localDriver;
    private LoadableComponent parent;
    private String userName;
    private String password;

    @FindBy(how= How.CSS, using=".row p")
    public WebElement welcomeText;

    @FindBy(how= How.CSS, using="button")
    public WebElement logoutButton;

    public GatewayPage(WebDriver driver, LoadableComponent parent, String userName, String password) {
        this.localDriver = driver;
        this.parent = parent;
        this.userName = userName;
        this.password = password;
        PageFactory.initElements(this.localDriver, this);
    }

    /**
     * Load the home page.
     */
    @Override
    protected void load() {
        parent.get();

        LoginPage page = new LoginPage(localDriver);
        page.submitLogin(this.userName, this.password, false);

        new WebDriverWait(localDriver, 30).until(
                ExpectedConditions.titleIs("TestDriver - Gateway"));

        MessageLogger.logAction("GatewayPage", "load()", "Login to Gateway.");
    }

    /**
     * Throw an error if the home page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertTrue("The home page does not display.", localDriver.getTitle().equals("TestDriver - Gateway"));
    }

    /**
     * Logout.
     */
    public void logout() {
        logoutButton.click();
        MessageLogger.logAction("GatewayPage", "logout()", "Click the logout button.");
    }

    /**
     * Get welcome message text.
     * @return welcome message text
     */
    public String getWelcomeMessage() {
        MessageLogger.logAction("GatewayPage", "getWelcomeMessage()", welcomeText.getText());
        return welcomeText.getText();
    }
}
