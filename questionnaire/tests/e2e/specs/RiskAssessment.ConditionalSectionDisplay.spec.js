describe('Risk Assessment - testing conditional display of sections', () => {
  let path = null

  before(function () {
    cy.registerDefaultParticipant()
    cy.signInAndConsentDefaultParticipant()
  })

  beforeEach(function () {
    cy.resetQuestionnaireForDefaultParticipant()
    Cypress.Cookies.preserveOnce('JSESSIONID')
    path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
  })

  it('conditionally displays the FAMILY_BREAST section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(false, 'FAMILY_BREAST')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_BREAST')
  })

  it('conditionally displays the FAMILY_OVARIAN section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(false, 'FAMILY_OVARIAN')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_OVARIAN')
  })

  it('conditionally displays the FAMILY_AFFECTED_GRANDMOTHER section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'PERSONAL_HISTORY_CONTEXT')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/breast'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_GRANDMOTHER')
  })

  it('conditionally displays the FAMILY_AFFECTED_AUNT section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'PERSONAL_HISTORY_CONTEXT')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/breast'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_AUNT')
  })

  it('conditionally displays the FAMILY_AFFECTED_NIECE section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'PERSONAL_HISTORY_CONTEXT')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_NIECE']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/breast'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_NIECE')
  })

  it('conditionally displays the FAMILY_AFFECTED_HALF_SISTER section correctly', () => {
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'PERSONAL_HISTORY_CONTEXT')
    cy.backAndAssertSuccessfulNavToPath(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_HALFSISTER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/breast'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_HALF_SISTER')
  })
})
