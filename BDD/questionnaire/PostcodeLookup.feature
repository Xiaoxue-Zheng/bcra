Feature: Postcode Lookup

Scenario: Viewing the Postcode Lookup
    Given the User is a entering a form containing the Postcode Lookup
    When the form has loaded
    Then the Postcode Lookup will initially display only a text field and associated label prompting them to enter a postcode
    And next to this text field there will be a button reading "Search postcode"

Scenario: Querying a valid and existing postcode
    Given the User is viewing a form containing the Postcode Lookup
    And the User has entered a valid and existing postcode into the Postcode fields
    When the "Select postcode" button is pressed
    Then a dropdown box will appear displaying multiple addresses for the entered postcode

Scenario: Querying a invalid or non-existent postcode
    Given the User is viewing a form containing the Postcode Lookup
    And the User has entered an invalid or non-existent postcode into the Postcode fields
    When the "Select postcode" button is pressed
    Then a message will appear telling the user that "Sorry, we were unable to find that postcode"

Scenario: Selecting an address
    Given the User is viewing a form containing the Postcode Lookup
    And the User has already entered a postcode and selected "Search postcode"
    When an address from the dropdown is selected
    Then that address will be provided to the form containing the Postcode Lookup control

Scenario: Turning on manual address entry
    Given the User is viewing a form containing the Postcode Lookup
    When the User selects the "Manually Enter Address" button
    Then the address select dropdown disappears
    And five fields appear allowing the manual entry of address data
    And the previously selected address is persisted and will appear in the manual entry textboxes

Scenario: Turning off manual address entry
    Given the User is viewing a form containing the Postcode Lookup
    And they have previously selected the "Manually Enter Address" button
    When the user selects to "Automatically find address"
    Then the Postcode Lookup form is reset to its default state
    And all data from the form is cleared down