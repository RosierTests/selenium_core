package site.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Parameter;
import ru.yandex.qatools.allure.annotations.Step;
import site.BasePage;

import static junit.framework.TestCase.assertTrue;

/**
 * Created By: Brian Smith on 4/28/15.
 * Package: site.pages
 * Description: This page class can be cloned and used to create new test suites more quickly.
 */

public class _PageTemplate extends BasePage {

    /* Variables annotated with @Parameter will display its' value in the Allure report for each test in this suite
     * assigning a value to it.
     */
    @Parameter("Add parameter name")
    private String parameter;

    /* Elements annotated with @FindBy will be initialized with the page object.
     * Five different methods exist to do so: name, id, class, xpath, and css.
     */
    @FindBy(name = "")
    public WebElement elementByName;

    @FindBy(id = "")
    public WebElement elementByID;

    @FindBy(className = "")
    public WebElement elementByClass;

    @FindBy(xpath = "")
    public WebElement elementByXPath;

    @FindBy(css = "")
    public WebElement elementByCSS;

    /* Some elements may not be available immediately after a page load, such as dynamic AJAX elements.
     * For those elements @FindBy is not appropriate and use should instantiate those elements using examples below.
     */

    // Single statement
    WebElement e = getElementBy(By.cssSelector(""));

    // Multiple statements
    String s = "";
    By xpath = By.xpath(s);
    WebElement e2 = getElementBy(xpath);

    /* This page constructor calls the constructor of the BasePage.
     * Any additional logic can be placed after the super() declaration.
     */
    public _PageTemplate (WebDriver driver) {
        super(driver);
    }

    // The load() method will execute once if isLoaded() throws an initial error when executing .get() with this class.
    @Override
    protected void load() {
        navigate_to("http://appdomain.com"); // Navigate to this page.

        // Wait until element on this page to be clickable before proceeding, or throw an error if 30 seconds as past.
        waitUntilClickable(elementByName, 30);
    }

    /* This method is used to check if the page is available, or loaded.
     * If an error is thrown the first time, the load() method above executes and then this will execute a second time.
     * If a second error is thrown, the test will be in error.
     */
    @Override
    protected void isLoaded() throws Error {
        /* This method requires a simple assertion to determine if the page successfully loads. You can use any of the
         * available jUnit assertions. Generally, you will assert against an element
         */
        assertTrue("Page is not displayed.", elementDisplays(elementByXPath));
    }

    @Step
    public void userAction() {

    }

    @Step("You can set custom text and include first input value \"{0}\", second input value \"{1}\", etc.")
    public void userAction2(String s, String s2) {
        this.parameter = s; // You can set a @Parameter variable and the assignment will display in Allure results.
        input(s, elementByID);
        input(s2, elementByCSS);
        click(elementByClass);
    }

    // You can obviously include reusable methods for each class to increase ease of maintenance.
    private void pageMethod() {

    }
}
