@market
  Feature: Marketing application test suite

    @market1
    Scenario: Required fields end-to-end
      Given I navigate to "quote" page
      When I fill out "required" fields
      And I fill out optional fields
      Then I verify "required" fields
