package tests;

import base.BaseTest;
import pages.RegisterPage;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import java.util.Locale;
import static org.testng.Assert.assertTrue;

public class ResgisterTests extends BaseTest {
    Faker faker = new Faker(new Locale("en-US"));

    @Test(groups = {"functional"})
    public void testRegistroExistoso() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 10, true, true, true);
        registerPage.fillRegistrationForm("Victor", "Guzman", email, password, password);
        registerPage.submitForm();
        assertTrue(!getDriver().getPageSource().contains("There is already an account"), "Registro falló");
    }

    @Test(groups = {"functional", "regression"})
    public void testCamposObligatoriosFaltantes() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        registerPage.fillRegistrationForm("", "", "", "", "");
        registerPage.submitForm();
        assertTrue(registerPage.isRequiredFieldErrorDisplayed(), "No se mostraron errores de campos obligatorios");
    }

    @Test(groups = {"functional", "regression"})
    public void testFormatoCorreoInvalido() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        String invalidEmail = "correo-invalido";
        String password = faker.internet().password(8, 10, true, true, true);
        registerPage.fillRegistrationForm("Victor", "Guzman", invalidEmail, password, password);
        registerPage.submitForm();
        assertTrue(registerPage.isRequiredFieldErrorDisplayed(), "No se mostró error por formato de correo inválido");
    }

    @Test(groups = {"functional"})
    public void testContrasenaDebil() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        String email = faker.internet().emailAddress();
        String weakPassword = "12345678";
        registerPage.fillRegistrationForm("Victor", "Guzman", email, weakPassword, weakPassword);
        registerPage.submitForm();
        assertTrue(registerPage.isRequiredFieldErrorDisplayed(), "No se mostró error por contraseña débil");
    }

    @Test(groups = {"functional", "regression"})
    public void testConfirmacionContrasenaIncorrecta() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        String email = faker.internet().emailAddress();
        String password = faker.internet().password(8, 10, true, true, true);
        String wrongConfirmPassword = "wrongPassword";
        registerPage.fillRegistrationForm("Victor", "Guzman", email, password, wrongConfirmPassword);
        registerPage.submitForm();
        assertTrue(registerPage.isRequiredFieldErrorDisplayed(), "No se mostró error por confirmación de contraseña incorrecta");
    }

    @Test(groups = {"functional", "integration"})
    public void testCorreoElectronicoDuplicado() {
        RegisterPage registerPage = new RegisterPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/create/");
        String email = faker.internet().emailAddress();
        String password = "Password123!";
        registerPage.fillRegistrationForm("Victor", "Guzman", email, password, password);
        registerPage.submitForm();

        getDriver().get(BASE_URL + "customer/account/logout/");
        getDriver().get(BASE_URL + "customer/account/create/");
        registerPage.fillRegistrationForm("Otro", "Usuario", email, password, password);
        registerPage.submitForm();

        String error = registerPage.getGlobalErrorMessage();
        assertTrue(error.contains("There is already an account"), "No se mostró mensaje de error por correo duplicado");
    }
}
