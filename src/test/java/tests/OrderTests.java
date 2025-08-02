package tests;

import base.BaseTest;
import pages.LoginPage;
import pages.OrderPage;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class OrderTests extends BaseTest {

    @Test(groups = {"functional"})
    public void testAgregarProductoAlCarrito() {
        LoginPage loginPage = new LoginPage(getDriver());
        OrderPage orderPage = new OrderPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("correo_valido@dominio.com", "Password123");
        getDriver().get(BASE_URL + "driven-backpack.html");
        orderPage.addSingleProductToCart();
        orderPage.openCart();
        assertTrue(getDriver().getPageSource().contains("Driven Backpack"), "El producto no aparece en el carrito");
    }

    @Test(groups = {"functional"})
    public void testEliminarProductoDelCarrito() {
        LoginPage loginPage = new LoginPage(getDriver());
        OrderPage orderPage = new OrderPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("correo_valido@dominio.com", "Password123");
        getDriver().get(BASE_URL + "driven-backpack.html");
        orderPage.addSingleProductToCart();
        orderPage.openCart();
        orderPage.removeProductFromCart();
        assertTrue(getDriver().getPageSource().contains("You have no items in your shopping cart."), "El producto no fue eliminado correctamente");
    }

    @Test(groups = {"functional", "regression"})
    public void testModificarCantidadProductoEnCarrito() {
        LoginPage loginPage = new LoginPage(getDriver());
        OrderPage orderPage = new OrderPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("correo_valido@dominio.com", "Password123");
        getDriver().get(BASE_URL + "driven-backpack.html");
        orderPage.addSingleProductToCart();
        orderPage.openCart();
        orderPage.updateQuantity("2");
        String pageSource = getDriver().getPageSource();
        assertTrue(pageSource.contains("Driven Backpack"), "El producto no está en el carrito");
        assertTrue(pageSource.contains("2") || pageSource.contains("Qty: 2"), "La cantidad no se actualizó correctamente");
    }

    @Test(groups = {"functional", "integration"})
    public void testCreacionOrdenExitosa() {
        LoginPage loginPage = new LoginPage(getDriver());
        OrderPage orderPage = new OrderPage(getDriver());
        getDriver().get(BASE_URL + "customer/account/login/");
        loginPage.login("correo_valido@dominio.com", "Password123");
        getDriver().get(BASE_URL + "driven-backpack.html");
        orderPage.addSingleProductToCart();
        orderPage.openCart();
        orderPage.proceedToCheckout();
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
        wait.until(ExpectedConditions.urlContains("/checkout"));
        assertTrue(getDriver().getCurrentUrl().contains("/checkout"), "No se llegó a la página de checkout correctamente");
    }
}
