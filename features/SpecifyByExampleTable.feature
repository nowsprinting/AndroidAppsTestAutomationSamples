#encoding: utf-8

Feature: Specify by example table

  In order to define specification collaboratively with business stakeholders
  As a BDD practitioner
  I want to clarify specification by using example tables


  @example
  Scenario: Add customer information and preview it by using WebView

    Given I press "button_add"

    When I enter customer information and preview it as follows:
        |name        |email                    |gender|age|previewGender|division|
        |漆原　るか  |rukako@example.com       |female|   |女性         |分類外  |
        |暁美　ほむら|homura.akemi@example.com |female|20 |女性         |F1層    |
        |鹿目　まどか|madoka.kaname@example.com|female|34 |女性         |F1層    |
        |美樹　さやか|sayaka.miki@example.com  |female|35 |女性         |F2層    |
        |佐倉　杏子  |anko.sakura@example.com  |female|49 |女性         |F2層    |
        |百江　なぎさ|charlotte@example.com    |female|50 |女性         |F3層    |
        |巴　マミ    |mamiru@example.com       |female|19 |女性         |T層     |
        |椎名　まゆり|mayucy@example.com       |female|13 |女性         |T層     |
        |牧瀬　紅莉栖|christina@example.com    |female|12 |女性         |C層     |
        |桐生　萌郁  |shiningfinger@example.com|female|4  |女性         |C層     |
