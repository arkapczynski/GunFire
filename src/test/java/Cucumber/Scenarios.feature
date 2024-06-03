Feature: Sorting in different pages.

  Background:
    Given Customer lands on Main Page

@mostExpensiveProductOfProducerPromotion
  Scenario Outline:
    Given Customer clicks Promotion Button
    When Customer choose weapon types <weaponTypes> and manufacturers <manufacturers> then confirm filters
    Then 5 most expensive products print in output.
    And One of most expensive products is "Vintorez".

    Examples:
      | weaponTypes                          | manufacturers       |
      | Karabiny/Karabinki,Karabiny wyborowe | ARES,LCT,CYMA,Bolle |

  @theCheapestProductOfProducer
  Scenario Outline:
    Given Customer clicks manufacturer button.
    When Customer choose manufacturer <manufacturer>.
    Then 5 cheapest products print in output.

    Examples:
      |manufacturer      |
      | ARES |


    @mostExpensiveProductsPromotion
  Scenario Outline:
    Given Customer clicks Promotion Button
    When Customer choose weapon types <weaponTypes> and manufacturers <manufacturers> then confirm filters
    Then 5 most expensive products print in output.
    And One of most expensive products is "M110".

    Examples:
      | weaponTypes                          | manufacturers       |
      | Karabiny/Karabinki,Karabiny wyborowe | ARES,LCT,CYMA,Bolle |

    @languageAndCurrency
    Scenario Outline:
      Given Customer changes language on <language>
      And Customer changes currency on <currency>
      When Customer search for product <searchProduct> and click it.
      Then Price is in currency <currency>
      And Add to cart button is in <language>

Examples:
    | language  | currency  | searchProduct|
    |   nl      | HUF       | CM028        |
    |   es      | CZK       | LCK-19       |
#  pl/cs/es/en/fr/nl |PLN/CZK/EUR/GBP/HUF|


  @compareReplicas
  Scenario Outline:
    Given Customer search for product <searchProduct1> and click it.
    And Customer click add to comparison button.
    And Customer search for product <searchProduct2> and click it.
    And Customer click add to comparison button.
    And Customer search for product <searchProduct3> and click it.
    And Customer click add to comparison button.
    When Customer go to Compare Page
    And Click on differences button
    Then <comparisonParameters> are unique.
#    można array przerobić na set i sprawdzić czy ilość unikatowych jest taka sama jak ilość wartości.

    Examples:
      | searchProduct1|searchProduct2|searchProduct3|comparisonParameters|
      | CM028        | LCK-12      | SA-J01 EDGE| Kod produktu     |


@CheckIfReplicaIsCheaperThan
  Scenario Outline:
    Given Customer changes language on <language>
    And Customer changes currency on <currency>
    When Customer search for product <searchProduct> and click it.
    Then Price is in currency <currency>
    And Add to cart button is in <language>

    Examples:
      | searchProduct  | Price  |
      |   es      | HUF       |
      |  en       | CZK       |