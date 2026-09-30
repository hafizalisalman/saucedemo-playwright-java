package tests;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductsPage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SauceDemoCheckoutTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();

        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions()
                        .setHeadless(false)
        );
        context = browser.newContext();
        page = context.newPage();
    }

    @Test
    void userCanCompletePurchase() {

        String username = "standard_user";
        String password = "secret_sauce";

        String firstName = "Ali";
        String lastName = "Salman";
        String postalCode = "000";

        LoginPage loginPage = new LoginPage(page);
        ProductsPage productsPage = new ProductsPage(page);
        CartPage cartPage = new CartPage(page);
        CheckoutPage checkoutPage = new CheckoutPage(page);

        // 1. Login
        loginPage.open();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        assertEquals(
                "Products",
                page.locator("[data-test='title']").innerText()
        );

        // 2. Select product and add to cart
        productsPage.addBackpackToCart();

        assertEquals(
                "1",
                page.locator("[data-test='shopping-cart-badge']").innerText()
        );

        // 3. Open cart
        productsPage.openCart();

        assertTrue(
                page.url().contains("/cart.html")
        );

        // 4. Validate product in cart
        Locator backpackInCart = page.locator("[data-test='inventory-item']")
                .filter(new Locator.FilterOptions()
                        .setHasText("Sauce Labs Backpack"));

        assertEquals(
                "Sauce Labs Backpack",
                backpackInCart
                        .locator("[data-test='inventory-item-name']")
                        .innerText()
        );

        assertEquals(
                "$29.99",
                backpackInCart
                        .locator("[data-test='inventory-item-price']")
                        .innerText()
        );

        // 5. Proceed to checkout
        cartPage.proceedToCheckout();

        assertEquals(
                "Checkout: Your Information",
                page.locator("[data-test='title']").innerText()
        );

        // 6. Enter customer information
        checkoutPage.enterCustomerInformation(
                firstName,
                lastName,
                postalCode
        );

        assertEquals(
                firstName,
                page.locator("[data-test='firstName']").inputValue()
        );

        assertEquals(
                lastName,
                page.locator("[data-test='lastName']").inputValue()
        );

        assertEquals(
                postalCode,
                page.locator("[data-test='postalCode']").inputValue()
        );

        checkoutPage.continueCheckout();

// 7. Validate checkout overview
        assertEquals(
                "Checkout: Overview",
                page.locator("[data-test='title']").innerText()
        );

        Locator backpackInOverview = page.locator("[data-test='inventory-item']")
                .filter(new Locator.FilterOptions()
                        .setHasText("Sauce Labs Backpack"));

        assertEquals(
                "Sauce Labs Backpack",
                backpackInOverview
                        .locator("[data-test='inventory-item-name']")
                        .innerText()
        );

        assertEquals(
                "$29.99",
                backpackInOverview
                        .locator("[data-test='inventory-item-price']")
                        .innerText()
        );

        assertEquals(
                "Item total: $29.99",
                page.locator("[data-test='subtotal-label']").innerText()
        );

        assertEquals(
                "Tax: $2.40",
                page.locator("[data-test='tax-label']").innerText()
        );

        assertEquals(
                "Total: $32.39",
                page.locator("[data-test='total-label']").innerText()
        );

        // 8. Complete order
        checkoutPage.finishOrder();

        // Explicitly wait for completion page
        page.waitForURL("**/checkout-complete.html");

        // 9. Validate successful completion
        assertEquals(
                "Checkout: Complete!",
                page.locator("[data-test='title']").innerText()
        );

        assertEquals(
                "Thank you for your order!",
                page.locator("[data-test='complete-header']").innerText()
        );
    }

    @AfterEach
    void tearDown() {

        if (context != null) {
            context.close();
        }

        if (browser != null) {
            browser.close();
        }

        if (playwright != null) {
            playwright.close();
        }
    }
}