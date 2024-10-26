Feature: Inventory Manager Application Frame
  Specifications of the behavior of the Inventory Manager Application Frame

  Scenario: The initial state of the view
    Given The database contains an item with id "1", name "Laptop", quantity 10, price 999.9, and description "Simple Laptop"
    When The Inventory View is shown
    Then The list contains an element with id "1", name "Laptop", quantity 10, price 999.9, and description "Simple Laptop"
