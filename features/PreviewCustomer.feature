Feature: Preview customer

  In order to know the basics of Calabash-Android
  As a trial user of HelloTesting
  I want to add customer data and preview it on preview screen


  @advanced
  Scenario: Add customer information and preview it by using WebView

    Given I press "button_add"

    When I enter text "ほむら" into field with id "name"
    And  I enter text "homumado@example.com" into field with id "email"
    And  I press "genderFemale"
    And choose age as 14
    And go preview
    And wait for preview screen

    Then I should see name as "ほむら"
    And I should see "homumado@example.com" at "mail"
    And I should see "女性" at "gender"
    And I should see "14" at "age"
    And I should see "T層" at "division"
