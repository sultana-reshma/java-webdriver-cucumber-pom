@careers
Feature: Careers scenarios

  @careers1
  Scenario: Recruiter creates position
    Given I navigate to "careers" page
    And I login as "recruiter"
    Then I verify "recruiter" login
    When I create new "automation" position
    And I verify "automation" position created

  @careers2
  Scenario: Careers candidate scenario
    Given I navigate to "careers" page
    And I apply to "automation" position as "sdet"
    Then I verify new profile is created
    And I see "automation" position in my jobs

  @careers3
  Scenario: Careers adds new job
    Given I open "careers" page
    And I login as "sdet"
    Then I verify "sdet" login
    When I apply for a new job
    Then I see position marked as applied
    And I see position in my jobs
