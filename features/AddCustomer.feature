Feature: Add customer

  In order to know the basics of Calabash-Android
  As a trial user of HelloTesting
  I want to add customer data and watch it


  Scenario: Add customer information and preview it

    Given I press "button_add"

    When I enter text "ほむら" into field with id "name"
    And  I enter text "homumado@madoka.com" into field with id "email"
    And  I press "genderFemale"

    Then I should see "ほむら"
