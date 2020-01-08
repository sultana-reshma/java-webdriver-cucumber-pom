@rest
  Feature: CRUD operations for Careers

    @rest1
    Scenario: REST API Positions CRUD
      Given I login via REST as "recruiter"
      When I create via REST "automation" position
      Then I verify via REST new position is in the list
      When I update via REST "automation" position
      Then I verify via REST new position is updated
      When I delete via REST new position
      Then I verify via REST new position is deleted

    @rest2
    Scenario: REST API Candidates CRUD
      Given I login via REST as "recruiter"
      When I create via REST "sdet" candidate
      And I add via REST "pdf" resume to a new candidate
      Then I verify via REST that "pdf" resume has been added
#      Then I verify via REST new candidate is in the list
#      When I update via REST "sdet" candidate
#      Then I verify via REST new candidate is updated
#      When I delete via REST new candidate
#      Then I verify via REST new candidate is deleted

    @rest3
    Scenario: REST API Applications
      Given I login via REST as "recruiter"
      When I create via REST "automation" position
      And I create via REST "sdet" candidate
      And I add via REST "pdf" resume to a new candidate
      Then I verify via REST that "pdf" resume has been added
      And I apply via REST new candidate to a new position
      Then I verify via REST new application is submitted
      When I delete via REST new application
      Then I verify via REST new application is deleted
