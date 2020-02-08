Feature: Examples of Scenarios

  Scenario: The most basic scenario
    Given a precondition
    When an action happened
    Then an assertion holds

  Scenario: Scenario with all step keywords
    Given a precondition
    And another precondition
    But a third precondition
    When an action happened
    Then an assertion holds

  Scenario: Scenario with parameters
    Given a parameter "X" and another "Y"



  Scenario: Scenario with multiline parameter
    Given a precondition with
    """
    First line
    Second line
    """

  Scenario: Scenario with table parameter
    Given a precondition with
    | col1   | col2 |
    | "val1" | 1    |
    | "val2" | 2    |

