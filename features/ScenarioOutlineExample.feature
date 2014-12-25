#encoding: utf-8

Feature: Specify by scenario outline

  In order to define specification collaboratively with business stakeholders
  As a BDD practitioner
  I want to clarify specification by using scenario outline


  @outline
  Scenario Outline: Add customer information and preview it by using WebView

    Given I press "button_add"

    When I enter text "<name>" into field with id "name"
    And  I enter text "<email>" into field with id "email"
    And  I press "<gender>"
    And choose age as <age>
    And go preview
    And wait for preview screen

    Then I should see name as "<name>"
    And I should see "<email>" at "mail"
    And I should see "<previewGender>" at "gender"
    And I should see "<age>" at "age"
    And I should see "<division>" at "division"

    Examples:
        |name        |email                    |gender      |age|previewGender|division|
        |暁美　ほむら|homura.akemi@example.com |genderFemale|20 |女性         |F1層    |
        |鹿目　まどか|madoka.kaname@example.com|genderFemale|34 |女性         |F1層    |
        |美樹　さやか|sayaka.miki@example.com  |genderFemale|35 |女性         |F2層    |
        |佐倉　杏子  |anko.sakura@example.com  |genderFemale|49 |女性         |F2層    |
        |百江　なぎさ|charlotte@example.com    |genderFemale|50 |女性         |F3層    |
        |巴　マミ    |mamiru@example.com       |genderFemale|19 |女性         |T層     |
        |椎名　まゆり|mayucy@example.com       |genderFemale|13 |女性         |T層     |
        |牧瀬　紅莉栖|christina@example.com    |genderFemale|12 |女性         |C層     |
        |桐生　萌郁  |shiningfinger@example.com|genderFemale|4  |女性         |C層     |
