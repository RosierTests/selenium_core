package users;

import org.openqa.selenium.WebDriver;
import site.pages.authentication.CreateAccountPage;
import site.pages.authentication.LoginPage;
import drivers.DriverManager;

/**
 * Created By: bsmith on 3/25/14.
 * Package: None
 * Description: This class is used to simply create user accounts.
 */
public class Accounts{
    public static WebDriver localDriver;

    public static void createAccount(String firstName, String lastName, String email, String userName, String password) {
        localDriver = DriverManager.get("create account");

        // Create an account, verify it, then log out of it.
        LoginPage loginPage = new LoginPage(localDriver);
        CreateAccountPage createAccountPage = new CreateAccountPage(localDriver, loginPage);
        createAccountPage.get();
        createAccountPage.submitAccountCreationRequest(firstName, lastName, email, userName, password, password);

        if (localDriver.getTitle().equals("TestDriver - Gateway")) {
            localDriver.navigate().to("http://localhost:3000/logout");
        }

        localDriver.quit();
    }
}
