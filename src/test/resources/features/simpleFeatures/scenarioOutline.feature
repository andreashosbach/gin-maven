Feature: Scenario Outlines

  Scenario Outline: A Scenario Outline
    Given a precondition with parameter <param1>
    When an action happened
    Then an assertion <param2> holds
    Examples:
    | param1  | param2 |
    | "true"  | 1      |
    | "false" | 0     |
