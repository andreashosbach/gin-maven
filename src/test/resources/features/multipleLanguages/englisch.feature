#language: en

Feature: In englisch

  Scenario: A Scenario in englisch
    Given a precondition
    And another precondition
    But a third precondition
    When an action happened
    Then an assertion holds

  Scenario Outline: A Scenario Outline in englisch
    Given a precondition with parameter <param1>
    When an action happened
    Then an assertion <param2> holds
    Examples:
      | param1  | param2 |
      | "true"  | 1      |
      | "false" | 0     |
