Feature: Test Automation Web UI

  @web
  Scenario: Sign up with valid username and password
    Given user is on sign up page
    When user input username with "randomusername"
    And user input password with "mei2025"
    And user click sign up button
    Then user will redirect to homepage after sign up

  @web
  Scenario: Login with valid username and password
    Given user is on login page
    When user input to username with "bulanmei"
    And user input to password with "282025"
    And user click login button
    Then user will redirect to homepage after login

  @web
  Scenario: Login with invalid username and password
    Given user is on login page
    When user input to username with "user123"
    And user input to password with "wrongpass"
    And user click login button
    Then A message appears "Wrong password."

  @web
  Scenario: End to end test from login to checkout
    Given user is on login page
    When user input to username with "annanur2025"
    And user input to password with "mei2025"
    And user click login button
    Then user will redirect to homepage
    Given user is on the product page
    When user choose product "Nexus 6"
    And user click Add to cart button
    Then user should be redirected to Cart page with title "STORE"
    And user click "Cart" button
    And User should be redirected to "https://www.demoblaze.com/cart.html"
    And user click "Place Order" button
    And User fills the order form with:
      | Name         | Country   | City      | Credit card | Month | Year |
      | anna     | Indonesia | Bandung   | 1234567890  | 11    | 2025 |
    And user click "Purchase" button
    Then "Thank you for your purchase!" message should appear

  @web
  Scenario: Checkout with empty shipping information
    Given user is on login page
    When user input to username with "putri_salju"
    And user input to password with "lezat123"
    And user click login button
    Then user will redirect to homepage
    Given user is on the product page
    When user choose product "Nexus 6"
    And user click Add to cart button
    Then user should be redirected to Cart page with title "STORE"
    And user click "Cart" button
    And User should be redirected to "https://www.demoblaze.com/cart.html"
    And user click "Place Order" button
    When User attempts to checkout without filling required fields
    Then an alert with text "Please fill out Name and Creditcard." should appear
    Then I close the browser securely
