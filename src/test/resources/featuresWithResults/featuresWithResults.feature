Feature: Some Scenarios with results

  Scenario: Successful Scenario
    Given a correct precondition
    When an action happened
    Then an assertion holds

  Scenario: Failed Scenario
    Given a correct precondition
    When an action happened
    Then an assertion fails

  Scenario Outline: Successful Scenario Outline
    Given a correct precondition
    When an action happened
    Then an assertion <result>
    Examples:
      | result |
      | holds  |

  Scenario Outline: Failed Scenario Outline
    Given a correct precondition
    When an action happened
    Then an assertion <result>
    Examples:
      | result |
      | holds  |
      | fails  |
