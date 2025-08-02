package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class RegisterPage extends BasePage {
	
	public RegisterPage(WebDriver driver) {
		super(driver);
	}
    /*Definicion de los localizadores*/
	
	private By firstNameField = By.id("firstname");
	private By lastNameField = By.id("lastname");
	private By emailField = By.xpath("//input[@type='email']");
	private By passwordField = By.id("password");
	private By confirmPasswordField = By.xpath("//input[@id='password-confirmation']");
	private By createAccountButton = By.xpath("//button[@title='Create an Account']");
	private By requiredFieldMessages = By.cssSelector("div.mage-error");


	
	//Metodo para rellenar el formulario de registro
	
	public void fillRegistrationForm(String firstName, String lastName, String email, String password, String confirmPassword) {
	    writeText(firstNameField, firstName);
	    writeText(lastNameField, lastName);
	    writeText(emailField, email);
	    writeText(passwordField, password);
	    writeText(confirmPasswordField, confirmPassword);		
	}
	
	//Metodo para enviar el formulario de registro
	
	public void submitForm() {
	    click(createAccountButton);
	}
	
	//Metodo para obtener el mensaje de error

	public boolean isRequiredFieldErrorDisplayed() {
	    return driver.findElements(requiredFieldMessages).size() > 0;
	}
	// Selector para mensaje global de error
	private By globalErrorMessage = By.cssSelector("div.message-error div");

	// Método para obtener el texto del mensaje global
	public String getGlobalErrorMessage() {
	    return readText(globalErrorMessage);
	}



	

}
