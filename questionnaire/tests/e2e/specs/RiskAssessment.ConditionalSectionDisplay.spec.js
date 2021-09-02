describe('Risk Assessment - testing conditional display of sections', () => {
  let path = null

  const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
  const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
  const STRONG_PASSWORD = "hard2Crack!!"

  before(function () {
    cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
    cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
    cy.completeConsentPage()
    cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)

    cy.saveLocalStorage()
  })

  after(function() {
    cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
    cy.clearTables(['study_id','participant', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
  })

  beforeEach(function () {
    Cypress.Cookies.preserveOnce('JSESSIONID')
    cy.restoreLocalStorage()
    path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    cy.restartRiskAssessmentForStudyCode(UNREGISTERED_STUDY_CODE)
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
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
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
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
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
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
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
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
    path = 'questionnaire/breast'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_HALF_SISTER')
  })

  it('should direct ot questionnaire page if not completed', () => {
    cy.server()
    cy.visit('/signin')
    cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
    cy.get('input').last().clear().type(STRONG_PASSWORD)
    cy.get('button').contains('Sign in').click()
    cy.url().should('include', '/questionnaire')
  })

  })
