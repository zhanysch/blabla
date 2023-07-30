Feature: Slack API integreation


  Scenario: Send a message to Slack channel

    Given user has slack endpoint
    When user sends a message to api channel
    Then status code is 200
    And message i s succesfully sent