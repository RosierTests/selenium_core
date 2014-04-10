package utilities;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.authentication.CreateAccountPage;
import pages.authentication.LoginPage;
import utilities.output.MessageLogger;

/**
 * Created By: bsmith on 3/25/14.
 * Package: None
 * Description: This class is used to simply create user accounts.
 */
public class Accounts {
    static WebDriver localDriver;

    public static void createAccount(String firstName, String lastName, String email, String userName, String password) {
        // Open a new browser window.
        localDriver = DriverManager.get();

        // Set the size of the browser window.
        Dimension browserWindow = new Dimension(1050, 768);
        localDriver.manage().window().setSize(browserWindow);

        // Create an account, verify it, then log out of it.
        LoginPage loginPage = new LoginPage(localDriver);
        CreateAccountPage createAccountPage = new CreateAccountPage(localDriver, loginPage);
        createAccountPage.get();
        createAccountPage.submitAccountCreationRequest(firstName, lastName, email, userName, password, password);

        try {
            new WebDriverWait(localDriver, 15).until(
                    ExpectedConditions.titleIs("TestDriver - Gateway"));
        }catch (WebDriverException e) {
            MessageLogger.logError("Accounts", "createAccount()", "Flash Error: " +
                        localDriver.findElement(createAccountPage.flashError).getText());
        }

        if (localDriver.getTitle().equals("TestDriver - Gateway")) {
            localDriver.navigate().to("http://localhost:3000/logout");
            MessageLogger.logAction("Accounts", "createAccount()", "Created account.");
        }

        // Close browser.
        localDriver.quit();
    }
}
