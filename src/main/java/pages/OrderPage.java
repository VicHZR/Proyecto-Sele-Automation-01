package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class OrderPage extends BasePage {

    public OrderPage(WebDriver driver) {
        super(driver);
    }

    // Selectores
    private By addToCartButton = By.cssSelector("li.product-item:first-of-type button.tocart");
    private By addToCartSingleProductButton = By.id("product-addtocart-button");
    private By cartIcon = By.cssSelector("a.showcart");
    private By viewCartButton = By.cssSelector("a.action.viewcart");
    private By quantityInput = By.cssSelector("input.input-text.qty");
    private By updateCartButton = By.cssSelector("button.update");
    private By removeItemButton = By.cssSelector("a.action.action-delete");
    private By proceedToCheckoutButton = By.cssSelector("button.action.primary.checkout");
    private By successMessage = By.cssSelector("div.message-success div");

    // Métodos

    // Para productos en lista (no recomendado para Driven Backpack)
    public void addFirstProductToCart() {
        waitAndClick(addToCartButton);
    }

    // ✅ Para productos individuales como Driven Backpack
    public void addSingleProductToCart() {
        waitAndClick(addToCartSingleProductButton);
    }

    // ✅ Obtener mensaje de confirmación al agregar producto
    public String getAddToCartConfirmationMessage() {
        return waitForElementText(successMessage);
    }

    public void openCart() {
        waitAndClick(cartIcon);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(viewCartButton));
            waitAndClick(viewCartButton);
        } catch (TimeoutException e) {
            driver.get("https://magento.softwaretestingboard.com/checkout/cart/");
        }
    }

    public void updateQuantity(String quantity) {
        writeText(quantityInput, quantity);
        click(updateCartButton);
    }

    public void removeProductFromCart() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(removeItemButton));
        waitAndClick(removeItemButton);
    }

    public void forceRemoveProductFromCart() {
        WebElement element = driver.findElement(removeItemButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void proceedToCheckout() {
        waitAndClick(proceedToCheckoutButton);
    }
}
