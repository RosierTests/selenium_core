package site;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By: Brian Smith on 10/6/2014.
 * Package: pages
 */
public class BasePage extends LoadableComponent<BasePage> {
    public WebDriver localDriver;

    public BasePage(WebDriver driver) {
        initialize(driver);
        PageFactory.initElements(this.localDriver, this);
    }

    protected void load() {}
    protected void isLoaded() throws Error {}

    public void initialize(WebDriver driver) {
        this.localDriver = driver;
    }

    public void click(WebElement e) {
        e.click();
    }

    public Boolean elementDisplays(WebElement e) {
        try {
            return e.isDisplayed();
        }catch (NoSuchElementException err) {
            return false;
        }
    }

    public String getAttribute(WebElement e, String attribute) {
        return e.getAttribute(attribute);
    }

    public WebElement getElementBy(By locator) {
        return localDriver.findElement(locator);
    }

    public List<WebElement> getElementListBy(By locator) {
        return localDriver.findElements(locator);
    }

    public List<String> getSelectList(WebElement e) {
        Select s = new Select(e);
        List<WebElement> options = s.getOptions();
        List<String> optionStrings = new ArrayList<>();

        if (!options.isEmpty()) {
            for (WebElement option : options) optionStrings.add(option.getText());
        }
        else { optionStrings.add("No options available"); }

        return optionStrings;
    }

    public String getPageTitle() {
        return localDriver.getTitle();
    }

    public void input(String text, WebElement e) {
        click(e);
        e.clear();
        e.sendKeys(text);
    }

    public void navigate_to(String page) {
        localDriver.navigate().to("http://localhost:3000/" + page);
    }

    public void removeAllCookies() {
        localDriver.manage().deleteAllCookies();
    }

    public void selectByValue(WebElement e, String value) {
        Select selector = new Select(e);
        selector.selectByValue(value);
    }

    public void selectByIndex(WebElement e, Integer index) {
        Select selector = new Select(e);
        selector.selectByIndex(index);
    }

    public void selectByVisibleText(WebElement e, String text) {
        Select selector = new Select(e);
        selector.selectByVisibleText(text);
    }

    public void submitForm(WebElement e) {
        e.submit();
    }

    public void waitUntilClickable(WebElement e, Integer seconds) {
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.elementToBeClickable(e));
    }

    public void waitUntilNotClickable(WebElement e, Integer seconds) {
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.not(ExpectedConditions.elementToBeClickable(e)));
    }

    public void waitUntilNotVisible(By locator, Integer seconds) {
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void waitUntilTextDisplays(WebElement e, String text, Integer seconds) {
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.textToBePresentInElement(e, text));
    }

    public void waitUntilTitleIs(String title, Integer seconds){
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.titleIs(title));
    }

    public void waitUntilVisible(WebElement e, Integer seconds) {
        new WebDriverWait(localDriver, seconds).until(
                ExpectedConditions.visibilityOf(e));
    }
}
