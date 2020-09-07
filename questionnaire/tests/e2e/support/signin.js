const EMAIL_ADDRESS = 'consent-test@example.com'
const PASSWORD = 'testpassword'
const CONSENT_QUESTION_INPUTS = [
  '#CONSENT_INFO_SHEET_YES',
  '#CONSENT_WITHDRAWAL',
  '#CONSENT_DATA_TRUST',
  '#CONSENT_DATA_RESEARCH',
  '#CONSENT_DATA_UOM',
  '#CONSENT_DATA_COMMERCIAL',
  '#CONSENT_INFORM_GP',
  '#CONSENT_TAKE_PART',
  '#CONSENT_BY_LETTER',
  '#CONSENT_FUTURE_RESEARCH_YES'
]

Cypress.Commands.add('signInAndConsent', () => {
  cy.server()
    cy.visit('/signin')
    cy.get('input').first().clear().type(EMAIL_ADDRESS)
    cy.get('input').last().clear().type(PASSWORD)
    cy.get('button').click()

    for (let inputs of CONSENT_QUESTION_INPUTS){
      cy.get(inputs).check({force: true})
    }
    cy.get('[type="submit"]').click()
})
