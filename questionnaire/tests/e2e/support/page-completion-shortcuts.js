Cypress.Commands.add('completeRegisterPage', (studyId) => {
    cy.visit('/register')
    cy.contains('h1', 'Register')

    cy.get('input').first().check()
    cy.get('input').eq(1).clear().type(studyId)
    cy.get('input').eq(2).clear().type('1990-01-01')
    cy.get('button').contains('Next').click()
})

Cypress.Commands.add('completeConsentPage', (studyId) => {
    clickConsentOption("CONSENT_INFO_SHEET", 'yes')
    for (let i = 2; i < 10; i++) {
        clickConsentCheckbox(i)
    }
    clickConsentOption("CONSENT_FUTURE_RESEARCH", 'yes')
    clickConsentOption("CONSENT_OPTIONAL_INTERVIEW", 'yes')
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
    enterHomePhoneNumber('07700123123')
    enterMobilePhoneNumber('07700123123')
    enterPostCodeAndSelectAddress('AA1 1AA', '1 HIGH STREET, CRAFTY VALLEY')
    selectPreferredContactMethods(true, true, true)

    cy.get('.pure-button').contains('Save details').click()
    cy.url().should('include', 'end')
})

function enterFirstName(firstname) {
    return cy.get('input').eq(0).type(firstname)
}

function enterSurname(surname) {
    return cy.get('input').eq(1).type(surname)
}

function enterPostCodeAndSelectAddress(postcode, address) {
    cy.get('input').eq(2).type(postcode)
    cy.get('a').contains('Search postcode').click()
    cy.get('#postcodeSelect').select(address)
}

function enterHomePhoneNumber(phoneNumber) {
  return cy.get('input').eq(3).type(phoneNumber)
}

function enterMobilePhoneNumber(phoneNumber) {
  return cy.get('input').eq(4).type(phoneNumber)
}

function selectPreferredContactMethods(email, call, mail) {
  if (email) cy.get('label').contains('Email').click()
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

Cypress.Commands.add('completeRiskAssessmentAndReferred', () => {
    continueToRiskAssessment()
    fillFamilyHistoryWithReferredCondition()
    cy.url().should('include', 'referral')
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

function fillFamilyHistoryWithReferredCondition() {
  let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT', 'FAMILY_BREAST_AFFECTED_NIECE']
  cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
  cy.get('.pure-button').contains('Save and continue').click()
  cy.checkReferredWithCorrectReason('Two or more of your family members have been affected by breast cancer')
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

Cypress.Commands.add('restartRiskAssessmentForStudyCode', (studyCode) => {
    return getRiskAssessmentFromStudyCode(studyCode).then((riskAssessmentId) => {
        return resetRiskAssessmentState(riskAssessmentId)
    })
})

function getRiskAssessmentFromStudyCode(studyCode) {
    return cy.task('query', {
        sql: "SELECT risk_assessment_response_id FROM study_id WHERE code = '" + studyCode + "'"
    }).then((response) => {
        return response.rows[0].risk_assessment_response_id
    })
}

function resetRiskAssessmentState(riskAssessmentId) {
    return cy.task('query', {
        sql: "UPDATE answer_response SET state = 'NOT_STARTED' WHERE id = " + riskAssessmentId
    })
}


function getFirstNameField() {
    return cy.get('input').eq(0)
}

function getSurnameField() {
    return cy.get('input').eq(1)
}

function getPostCodeField() {
    return cy.get('input').eq(2)
}

function getHomePhoneNumberField() {
    return cy.get('input').eq(3)
}

function getMobilePhoneNumberField() {
    return cy.get('input').eq(4)
}

function getPreferWayToContactField(fieldName) {
    return cy.get('label').contains(fieldName)
}

Cypress.Commands.add('fillMandatoryFields', () => {
    getFirstNameField().type("Barry")
    getSurnameField().type("Smith")
    getPostCodeField().type("AA1 1AA")
    cy.get('a').contains('Search postcode').click()
    cy.get('#postcodeSelect').select('1 HIGH STREET, CRAFTY VALLEY')
    getPreferWayToContactField('Email').click()
})

Cypress.Commands.add('fillNonMandatoryFields', () => {
    getHomePhoneNumberField("0123912390")
    getMobilePhoneNumberField("0123912390")
})

Cypress.Commands.add('clearFields', () => {
    getFirstNameField().clear();
    getSurnameField().clear();
    getPostCodeField().clear();
})

Cypress.Commands.add('signOut', () => {
  cy.get('a').contains('Sign out').click()
  cy.url().should('include', '/signin')
})

Cypress.Commands.add('signIn', (email, password) => {
  cy.get('a').contains('Sign in').click()
  cy.get('input').first().clear().type(email)
  cy.get('input').last().clear().type(password)
  cy.get('button').contains('Sign in').click()
})

Cypress.Commands.add('checkCanRiskReport', (uploaded) => {
  if(uploaded){
    cy.contains('h1', 'CanRisk Report')
    cy.url().should('include', '/canriskreport')
    cy.contains('div', 'Loading CanRisk report, please wait...')
  }else{
    cy.url().should('include', 'end')
    cy.contains('h1', 'Thank you for submitting your questionnaire.')
  }
})

Cypress.Commands.add('clickAndCheckCanRiskReport', (uploaded) => {
  cy.get('a').contains('CanRisk report').click()
  cy.url().should('include', '/canriskreport')
  cy.contains('h1', 'CanRisk Report')
  if(uploaded){
    cy.contains('div', 'Loading CanRisk report, please wait...')
  }else {
    cy.contains('p', 'Your risk report will be available to view here once it has been uploaded.')
  }
})
