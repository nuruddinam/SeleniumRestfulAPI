Feature: End to End simulation test

# Scenario Outline: As a user I can add new data with some data
#     Given A list of products are available
#     When I add new "<payload>" to etalase
#     Then The product is available

# Examples:
#     |payload  |
#     |addItem  |
#     |addItem2 | 

Scenario Outline: As a user I can add new data
    Given A list of item are available 
    When I add item to list "<payload>" 
    # When I add item to list
    Then The item is available 

    Examples:
    |payload  |
    |addItem  |
    |addItem2 |



