package authentication;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static junit.framework.Assert.assertTrue;

/**
 * Created By: bsmith on 2/24/14.
 * Package: authentication
 * Description: This is a template of a fictitious page object representing the first page displayed when navigating
 * to an application. This class utilizes loadable components.
 */
public class LoginPage extends LoadableComponent<LoginPage>{
    //The local object of the driver passed in to the constructor.
    private WebDriver localDriver;

    //Examples of several ways to find elements using PageFactory.
    @FindBy(how= How.NAME, using="user")
    @CacheLookup //Indicates the element will not be looked up constantly in the DOM.
    public WebElement userName;

    @FindBy(how= How.NAME, using="user-error")
    public WebElement userNameErrorMessage;

    @FindBy(how= How.ID, using="password")
    @CacheLookup
    public WebElement password;

    @FindBy(how= How.ID, using="password-error")
    public WebElement passwordErrorMessage;

    @FindBy(how= How.XPATH, using="//input[@name='submit']")
    @CacheLookup
    public WebElement submitButton;

    @FindBy(how= How.CSS, using="")
    @CacheLookup
    public WebElement nextButton;

    //Constructor accepting the driver only as it is the root page in the application structure.
    public LoginPage(WebDriver driver) {
        this.localDriver = driver;
        //Initialize all elements found in the above examples.
        PageFactory.initElements(this.localDriver, this);
    }

    /**
     * Method attempts to load the page if isLoaded() throws an error
     * when an object of this class is initially created.
     */
    @Override
    protected void load() {
        localDriver.navigate().to("http://www.yoursite.com");
    }

    /**
     * Method initially called when using the get() method on the object. If an error is thrown, the load()
     * method is called. isLoaded() is called again once loaded and a second failure will cause an error.
     * @throws Error
     */
    @Override
    protected void isLoaded() throws Error {
        //Assert if the title does not match.
        assertTrue("The login page does not display.", localDriver.getTitle().equals("Title"));
    }

    /**
     * Enter your account credentials when attempting to log into the application.
     * @param userName your account user name
     * @param password your account password
     */
    public void enterUserNameAndPassword(String userName, String password) {
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
    }

    /**
     * Return error messages displayed for user names and passwords.
     * @return a concatenated string of both user name and password errors, or an empty string if none.
     */
    public String getErrorMessages() {
        String concatenatedErrorMessage = "";
        if (userNameErrorMessage.isDisplayed()) {concatenatedErrorMessage += userNameErrorMessage.getText();}
        if (!concatenatedErrorMessage.equals("")) {concatenatedErrorMessage += "|";}
        if (passwordErrorMessage.isDisplayed()) {concatenatedErrorMessage += passwordErrorMessage.getText();}
        return concatenatedErrorMessage;
    }

    /**
     * Submit your credentials using the submit button.
     */
    public void submitLogin() {
        submitButton.click();
    }
}
