package site.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.LoadableComponent;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import site.BasePage;
import site.pages.authentication.LoginPage;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

/**
 * Created By: Brian Smith on 3/18/14.
 * Package: none
 * Description: This page represents the home page.
 */
public class GatewayPage extends BasePage{
    private LoadableComponent parent;
    private String userName;
    private String password;

    @Parameter("Welcome Message")
    private String welcomeMessage;

    @FindBy(css = ".row p")
    public WebElement welcomeText;

    @FindBy(css = "button")
    public WebElement logoutButton;

    public GatewayPage(WebDriver driver, LoadableComponent parent, String userName, String password) {
        super(driver);
        this.parent = parent;
        this.userName = userName;
        this.password = password;
    }

    /**
     * Load the home page.
     */
    @Override
    protected void load() {
        parent.get();

        LoginPage page = new LoginPage(localDriver);
        page.submitLogin(this.userName, this.password, false);

        waitUntilTitleIs("TestDriver - Gateway", 30);
    }

    /**
     * Throw an error if the home page cannot load.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertThat("The home page does not display.", getPageTitle(), is(equalTo("TestDriver - Gateway")));
    }

    /**
     * Logout.
     */
    @Step
    public void logout() {
        click(logoutButton);
        waitUntilTitleIs("TestDriver - Login", 30);
    }

    /**
     * Get welcome message text.
     * @return welcome message text
     */
    @Step
    public String getWelcomeMessage() {
        welcomeMessage = welcomeText.getText();
        return welcomeMessage;
    }
}
