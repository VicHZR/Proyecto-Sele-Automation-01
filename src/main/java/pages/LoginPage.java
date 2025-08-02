package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private By emailField = By.id("email");
    private By passwordField = By.id("pass");
    private By signInButton = By.id("send2");
    private By errorMessage = By.cssSelector("div.message-error div");

    public void login(String email, String password) {
        writeText(emailField, email);
        writeText(passwordField, password);
        click(signInButton);
    }

    public boolean isRequiredFieldErrorDisplayed() {
        return driver.findElements(By.cssSelector("div.mage-error")).size() > 0;
    }
    public String getErrorMessage() {
        return readText(By.cssSelector("div.message-error div"));
    }
    
    private By forgotPasswordLink = By.linkText("Forgot Your Password?");
    private By emailRecoveryField = By.id("email_address");
    private By submitRecoveryButton = By.cssSelector("button.action.submit.primary");
    private By recoveryConfirmationMessage = By.cssSelector("div.message-success div");

    public void recoverPassword(String email) {
        click(forgotPasswordLink);
        writeText(emailRecoveryField, email);
        click(submitRecoveryButton);
    }

    public String getRecoveryConfirmationMessage() {
        return waitForElementText(recoveryConfirmationMessage);
    }

    
    
    

}
