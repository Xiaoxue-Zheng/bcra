Cypress.Commands.add('completeRegisterPage', (studyId) => {
    cy.visit('/register')
    cy.contains('h1', 'Register')

    cy.get('input').first().clear().type(studyId)
    cy.get('button').first().click()
})

Cypress.Commands.add('completeConsentPage', (studyId) => {
    clickConsentOption("CONSENT_INFO_SHEET", 'yes')
    for (let i = 2; i < 10; i++) {
        clickConsentCheckbox(i)
    }
    clickConsentOption("CONSENT_FUTURE_RESEARCH", 'yes')
    clickConsentButton()
})

function clickConsentButton() {
    cy.get('.pure-button').first().click()
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
    cy.url().should('include', 'participant-details')
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
    enterDateOfBirth('1990-01-01')
    enterNhsNumber('1111111111')
    enterGPName('TEST')

    cy.get('.pure-button').contains('Save details').click()
    cy.url().should('include', 'questionnaire/familyhistorycontext')
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

function enterDateOfBirth(dateOfBirth) {
    return cy.get('input').eq(8).type(dateOfBirth)
}

function enterNhsNumber(nhsNumber) {
    return cy.get('input').eq(9).type(nhsNumber)
}

function enterGPName(gpName) {
    return cy.get('input').eq(10).type(gpName)
}

Cypress.Commands.add('completeRiskAssessment', () => {
    continueToRiskAssessment()
    selectMotherForBreastAndOvarian()
    saveAndContinue()
    cy.url().should('include', 'submit')
})

function continueToRiskAssessment() {
    cy.get('.pure-button').contains('Continue').click() 
}

function selectMotherForBreastAndOvarian() {
    cy.get('#FAMILY_BREAST_AFFECTED').contains('Mother').click()
    cy.get('#FAMILY_OVARIAN_AFFECTED').contains('Mother').click()
}

function saveAndContinue() {
    cy.get('.pure-button').contains('Save and continue').click() 
}