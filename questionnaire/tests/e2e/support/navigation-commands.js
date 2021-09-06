Cypress.Commands.add('submitAndAssertSuccessfulNavAwayFromPath', (currentPath) => {
  cy.get('[type="submit"]').click()
  cy.location('pathname', { timeout: 10000 }).should('not.include', currentPath)
})

Cypress.Commands.add('backAndAssertSuccessfulNavToPath', (currentPath) => {
  cy.go('back')
  cy.location('pathname', { timeout: 10000 }).should('include', currentPath)
})

Cypress.Commands.add('navigateToFamilyBreastSection', (precondition, currentPath) => {
  let FAMILY_BREAST_AFFECTED_ITEMS = [precondition]
  cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
  cy.submitAndAssertSuccessfulNavAwayFromPath(currentPath)
})

Cypress.Commands.add('visitFamilyAffectedPrimarySection', (cleanSlate) => {
  let path = 'questionnaire/family'
  cy.visit(path)
  if (cleanSlate === true) {
    let EMPTY_ITEMS = []
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', EMPTY_ITEMS)
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', EMPTY_ITEMS)
  }
})

Cypress.Commands.add('visitFamilyAffectedSection', (sectionName) => {
  let path = 'questionnaire/' + sectionName
  cy.visit(path)
})

Cypress.Commands.add('navigateToFamilyOvarianSection', (precondition, currentPath) => {
  let FAMILY_OVARIAN_AFFECTED_ITEMS = [precondition]
  cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
  cy.submitAndAssertSuccessfulNavAwayFromPath(currentPath)
})
