@API
Feature: Verify the OK response for google

  Scenario Outline: Do a get call to employee
    Given Get call to "<url>"
    Then Response is "<statusCode>"
    And The Employee "<name>"
    Examples:
      | url        | statusCode | name        |
      | employee/1 | 200        | Tiger Nixon |