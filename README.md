# SauceDemo Playwright Java Automation

Java + Playwright E2E automation for the SauceDemo checkout flow using JUnit 5 and Page Object Model.

## Automated Flow

This project automates one complete customer purchase journey:

1. Login with a valid user
2. Select Sauce Labs Backpack
3. Add the product to the cart
4. Verify the product and price in the cart
5. Proceed to checkout
6. Enter customer information
7. Verify the checkout overview, subtotal, tax and total
8. Complete the order
9. Verify successful order completion

## Tech Stack

- Java 21
- Playwright 1.56.0
- JUnit 5
- Maven
- Page Object Model

## Project Structure

src/test/java/
├── pages/
│   ├── LoginPage.java
│   ├── ProductsPage.java
│   ├── CartPage.java
│   └── CheckoutPage.java
└── tests/
    └── SauceDemoCheckoutTest.java

## Prerequisites

- Java 21
- Git
- Internet connection

The project includes the Maven Wrapper, so Maven does not need to be installed separately.

## Run the Test

### Windows

mvnw.cmd test

### macOS / Linux

./mvnw test

The test launches Chromium, executes the checkout flow, and validates the expected results.

## Test Credentials

The assessment-provided SauceDemo test account is used:

Username: standard_user
Password: secret_sauce

## AI Assistance

AI was used to assist with test-flow design, Playwright code structure, locator suggestions, assertions, and debugging. The generated output was manually reviewed, adjusted and validated by running the test successfully.
