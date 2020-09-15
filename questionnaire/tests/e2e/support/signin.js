const NHS_NUMBER = '7616551351'
const EMAIL_ADDRESS = 'consent-test@example.com'
const PASSWORD = 'testpassword'
const PASSWORD_HASH = '$2a$10$xfxxf5eZbLo0S70V55c8FO6R.741QpR4Lkh84749m/B7kP6/XIFc2'

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

Cypress.Commands.add('registerDefaultParticipant', () => {
  cy.registerParticipant(NHS_NUMBER, EMAIL_ADDRESS, PASSWORD_HASH)
})

Cypress.Commands.add('resetQuestionnaireForDefaultParticipant', () => {
  cy.resetQuestionnaire(NHS_NUMBER)
})

Cypress.Commands.add('signInAndConsentDefaultParticipant', () => {
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
