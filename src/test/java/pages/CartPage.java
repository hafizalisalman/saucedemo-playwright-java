package pages;

import com.microsoft.playwright.Page;

public class CartPage {

    private final Page page;

    public CartPage(Page page) {
        this.page = page;
    }

    public void proceedToCheckout() {
        page.locator("[data-test='checkout']").click();

        page.waitForURL("**/checkout-step-one.html");
    }
}