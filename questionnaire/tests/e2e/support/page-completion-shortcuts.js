Cypress.Commands.add('completeRegisterPage', (studyId) => {
    cy.visit('/register')
    cy.contains('h1', 'Register')

    cy.get('input').first().clear().type(studyId)
    cy.get('input').eq(1).clear().type('1990-01-01')
    cy.get('button').contains('Next').click()
})

Cypress.Commands.add('completeConsentPage', (studyId) => {
    clickConsentOption("CONSENT_INFO_SHEET", 'yes')
    for (let i = 2; i < 10; i++) {
        clickConsentCheckbox(i)
    }
    clickConsentOption("CONSENT_FUTURE_RESEARCH", 'yes')
    clickConsentButton()
})

Cypress.Commands.add('createStudyCodeAndRegister', (studyCode, email, password) =>{
  cy.createStudyIdFromCode(studyCode)
  cy.completeRegisterPage(studyCode)
  cy.completeConsentPage()
  cy.completeCreateAccountPage(email, password)
})

function clickConsentButton() {
    cy.get('.pure-button').contains('I give my consent').click()
}

function clickConsentOption(groupId, option) {
    if (option.toLowerCase() == 'yes')
        cy.get('#' + groupId + ' > .items > :nth-child(1) > label').click()
    else
        cy.get('#' + groupId + ' > .items > :nth-child(2) > label').click()
}

function clickConsentCheckbox(checkboxId) {
    cy.get(':nth-child(' + checkboxId + ') > fieldset > .pure-u-1 > label').click()
}

Cypress.Commands.add('completeCreateAccountPage', (email, password) => {
    getEmailField().type(email)
    getPasswordField().type(password)
    getPasswordConfirmationField().type(password)
    cy.get('button').contains('Create account').click()
    cy.url().should('include', '/questionnaire/familyhistorycontext')
})

function getEmailField() {
    return cy.get('input').first()
}

function getPasswordField() {
    return cy.get('input').eq(1)
}

function getPasswordConfirmationField() {
    return cy.get('input').eq(2)
}

Cypress.Commands.add('completeParticipantDetailsPage', () => {
    enterFirstName('TEST')
    enterSurname('TEST')
    enterAddress(['TEST','TEST','TEST','TEST','TEST'])
    enterPostCode('AA1 1AA')
    enterHomePhoneNumber('07700123123')
    enterMobilePhoneNumber('07700123123')
    selectPreferredContactMethods(true, true, true, true)

    cy.get('.pure-button').contains('Save details').click()
    cy.url().should('include', 'end')
})

function enterFirstName(firstname) {
    return cy.get('input').eq(0).type(firstname)
}

function enterSurname(surname) {
    return cy.get('input').eq(1).type(surname)
}

function enterAddress(addressLines) {
    for (let i = 0; i < addressLines.length; i++) {
        cy.get('input').eq(2+i).type(addressLines[i])
    }
}

function enterPostCode(postcode) {
    return cy.get('input').eq(7).type(postcode)
}

function enterHomePhoneNumber(phoneNumber) {
  return cy.get('input').eq(8).type(phoneNumber)
}

function enterMobilePhoneNumber(phoneNumber) {
  return cy.get('input').eq(9).type(phoneNumber)
}

function selectPreferredContactMethods(email, sms, call, mail) {
  if (email) cy.get('label').contains('Email').click()
  if (sms) cy.get('label').contains('SMS').click()
  if (call) cy.get('label').contains('Call').click()
  if (mail) cy.get('label').contains('Mail').click()
}

Cypress.Commands.add('completeRiskAssessment', () => {
    continueToRiskAssessment()
    fillFamilyHistory()
    continueToRiskAssessment()
    fillPersonalHistory()
    cy.url().should('include', 'submit')
    cy.get('.pure-button').contains('Submit Questionnaire').click()
})

function continueToRiskAssessment() {
    cy.get('.pure-button').contains('Continue').click()
}

function saveAndContinue() {
    cy.get('.pure-button').contains('Save and continue').click()
}

function fillFamilyHistory() {
  cy.get('.pure-button').contains('Save and continue').click()
}

function fillPersonalHistory(){
  let path = 'questionnaire/family'
  cy.visitFamilyAffectedPrimarySection(true)
  cy.submitAndAssertSuccessfulNavAwayFromPath(path)
  path = 'questionnaire/yourhistorycontext'
  cy.submitAndAssertSuccessfulNavAwayFromPath(path)
  cy.setWeightHeightNumberAnswer('SELF_HEIGHT', 200)
  cy.setWeightHeightNumberAnswer('SELF_WEIGHT', 50)
  cy.setNumberDontKnowAnswer('SELF_FIRST_PERIOD', 'dontknow')
  cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 1)
  cy.setNumberAnswer('SELF_PREGNANCY_FIRST_AGE', 10)

  cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_NO')
  cy.setNumberAnswer('SELF_MENOPAUSAL_AGE', 20)
  cy.setRadioAnswerItem('SELF_ASHKENAZI_YES')

  cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_YES')
  cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES')
  let BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS = ['SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH']
  cy.setCheckboxAnswerItems('SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES', BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS)
  path = 'questionnaire/history'
  cy.submitAndAssertSuccessfulNavAwayFromPath(path)
}
