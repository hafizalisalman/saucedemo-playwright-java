package pages;

import com.microsoft.playwright.Page;

public class CheckoutPage {

    private final Page page;

    public CheckoutPage(Page page) {
        this.page = page;
    }

    public void enterCustomerInformation(
            String firstName,
            String lastName,
            String postalCode) {

        page.locator("[data-test='firstName']").fill(firstName);
        page.locator("[data-test='lastName']").fill(lastName);
        page.locator("[data-test='postalCode']").fill(postalCode);
    }

    public void continueCheckout() {

        // Make sure all checkout fields contain the expected values
        page.locator("[data-test='firstName']").waitFor();
        page.locator("[data-test='lastName']").waitFor();
        page.locator("[data-test='postalCode']").waitFor();

        // Click Continue
        page.locator("[data-test='continue']").click();

        // Wait for the overview page
        page.locator("[data-test='title']")
                .filter(new com.microsoft.playwright.Locator.FilterOptions()
                        .setHasText("Checkout: Overview"))
                .waitFor();
    }

    public void finishOrder() {

        page.locator("[data-test='finish']").click();

        page.locator("[data-test='title']")
                .filter(new com.microsoft.playwright.Locator.FilterOptions()
                        .setHasText("Checkout: Complete!"))
                .waitFor();
    }
}