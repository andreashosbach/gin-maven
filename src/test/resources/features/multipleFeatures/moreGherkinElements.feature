Feature: Using more Gherkin elements
  With a feature description

  @Tag1 @Tag2
  Scenario: Scenario with tags
    Given a precondition
    When an action happened
    Then an assertion holds

  #Comment of the Scenario
  Scenario: Scenario with comments
    Given a precondition
    #Comment between steps
    When an action happened
    Then an assertion holds

  Scenario: Scenario with a description
    This Scenario has a description
    Given a precondition
    When an action happened
    Then an assertion holds