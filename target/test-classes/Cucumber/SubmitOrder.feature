
@tag
Feature: Purchase the order from Ecommerce website
  I want to use this template for my feature file

	Background:
	Given I landed on the Ecommerce page

  @Regression
  Scenario Outline: Positive Test of Submitting the order
    Given Logged in with username <name> and password <password>
    When I add the product <productName> from cart
    And Checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed.

    Examples: 
      | name                  | password   | productName |
      | bhargavi123@gmail.com | Bhargavi!1 | ZARA COAT 3 |
