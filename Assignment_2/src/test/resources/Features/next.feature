Feature: Web categories and products

  In order to make sure the website categories and search function work
  As a user of the website
  I want to be able to reach the product details of the first product
  in their corresponding 5 categories
  and be able to search for a product with at least 5 results.

  Scenario Outline: Reachability of the category
    Given I am a user of the website
    When I visit the Next website
    And I click on the <category> category
    Then I should be taken to <category> category
    And the category should show at least <num> products
    When I click on the first product in the results
    Then I should be taken to the details page for that product

    Examples:
      | category              | num  |
      | New In                | 7700 |
      | Blazers               | 400  |
      | Shirts & Blouses      | 2900 |
      | Hoodies & Sweatshirts | 1600 |
      | Dresses               | 6300 |



  Scenario: Searching for nike shoes
    Given I am a user of the website
    When I search for a product using the term "Nikes"
    Then I should see the search results
    And there should be at least 5 products in the search results
    When I click on the first product in the results
    Then I should be taken to the details page for that product