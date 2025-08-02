package tests;

import base.BaseTest;
import pages.LoginPage;
import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import static org.testng.Assert.assertTrue;

public class LoginTests extends BaseTest {
    Faker faker = new Faker();

    @Test(groups = {"functional", "regression"})
    public void testLoginExitoso() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("correo_valido@dominio.com", "Password123");
        assertTrue(!getDriver().getPageSource().contains("The account sign-in was incorrect"), "Login falló con credenciales válidas");
    }

    @Test(groups = {"functional", "regression"})
    public void testLoginCredencialesIncorrectas() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("usuario@inexistente.com", "ClaveIncorrecta123");
        String mensajeError = loginPage.getErrorMessage();
        assertTrue(mensajeError.contains("The account sign-in was incorrect"), "No se mostró mensaje de error por credenciales incorrectas");
    }

    @Test(groups = {"functional", "regression"})
    public void testLoginCamposEnBlanco() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("", "");
        assertTrue(loginPage.isRequiredFieldErrorDisplayed(), "No se mostró mensaje de error por campos vacíos");
    }

    @Test(groups = {"functional", "regression"})
    public void testCorreoNoRegistrado() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        String correoNoRegistrado = "noexiste" + System.currentTimeMillis() + "@mail.com";
        loginPage.login(correoNoRegistrado, "Password123");
        String mensajeError = loginPage.getErrorMessage();
        assertTrue(mensajeError.contains("The account sign-in was incorrect"), "No se mostró mensaje de error por correo no registrado");
    }

    @Test(groups = {"functional", "integration"})
    public void testRecuperacionContrasena() {
        LoginPage loginPage = new LoginPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.recoverPassword("correo_valido@dominio.com");
        String mensajeConfirmacion = loginPage.getRecoveryConfirmationMessage();
        assertTrue(mensajeConfirmacion.contains("If there is an account associated"), "No se mostró mensaje de confirmación de recuperación");
    }
}
