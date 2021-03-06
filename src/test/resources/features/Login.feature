Feature: Login

  @simpletag
  Scenario: valid admin login
    When user enters valid admin username and password
    And user clicks on login button
    Then admin user is successfully logged in

  @production
  Scenario: valid ess employee login
    When user enters valid ess username and password
    And user clicks on login button
    Then ess user is successfully logged in

  @regression
  Scenario: login with valid username and invalid password
    When user enters valid username and invalid password
    And user clicks on login button
    Then user see invalid credentials text on login page

  @example
  Scenario Outline: login with multiple data
    When user enters "<username>" and "<password>"
    And user clicks on login button
    And "<firstname>" is successfully logged in
    Examples:
      |username         |password   |firstname|
      |Admin            |Hum@nhrm123|  Admin  |
      |william1236000000|Syntax12!!!!|William |

  @error
  Scenario: Login with valid username and invalid password
    When user enters valid username and invalid password and verify the error
      | username          | password | errormessage        |
      | Admin             | Human    | Invalid credentials |
      | william1236000000 | Syntax   | Invalid credentials |