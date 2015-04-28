package site.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Parameter;
import site.BasePage;

import static junit.framework.TestCase.assertTrue;

/**
 * Created By: Brian Smith on 4/28/15.
 * Package: site.pages
 * Description: This child page class details an example where one page recursively loads another.
 */

public class _ChildPageTemplate extends BasePage {

    /* This is a stripped-down example intended to show you how child page classes can attempt to load() pages and
     * interact with them in order to display this page. Think of an authentication page as the parent and home page
     * as a child since authentication is required in order to display it.
     *
     * View the _PageTemplate class for general page class details.
     */

    @Parameter("Add parameter name")
    private String s;

    @Parameter("Add parameter name")
    private String s2;

    @FindBy(xpath = "")
    public WebElement e1;

    @FindBy(xpath = "")
    public WebElement e2;

    // Input the values necessary for use within the load() method.
    public _ChildPageTemplate(WebDriver driver, String s, String s2) {
        super(driver);
        this.s = s;
        this.s2 = s2;
    }

    /* The load() method of a child page class attempts to load its' parent before loading itself. This kind of logic
     * can be chained together several times so that a call to load one page can recursively load several other pages
     * in a sequence. This leads to greater reuse and less headache when dealing with a complicated connected page
     * structure.
     */
    @Override
    protected void load() {
        // Create an object of the parent page and attempt to load it.
        _PageTemplate parentPage = new _PageTemplate(localDriver);
        parentPage.get();

        // Execute action on the parent page to display this page and wait until an element on this page displays.
        parentPage.userAction2(this.s, this.s2);
        waitUntilClickable(e1, 30);
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
        assertTrue("Page is not displayed.", elementDisplays(e1));
    }
}
