describe('Risk Assessment - testing conditional display of questons', () => {
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
  })

  it('should fail when have not chose how many', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.get('[type="submit"]').click()
    cy.url().should('include', path)
    cy.contains('div', 'Please select one option for question: How many grandmothers were affected by breast cancer?')

  })

  it('should fail when have fill age', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.get('[type="submit"]').click()
    cy.url().should('include', path)
    cy.contains('div', 'Please fill question: At what age was your grandmother affected by breast cancer?')

  })

  it('should fail when have not chose side', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/grandmother'
    cy.get('[type="submit"]').click()
    cy.url().should('include', path)
    cy.contains('div', 'Please select one option for question: Was your affected grandmother on your:')

  })

  it('should fail when have not filled mother age', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/grandmother'
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER')

    cy.get('[type="submit"]').click()
    cy.url().should('include', path)
    cy.contains('div', 'Please fill question: How old is your mother (or if your mother is not alive, how old was she when she died)?')

  })

  it('should success when complete all fields', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/grandmother'
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', 50)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

  })

  it('should success when complete all fields include dont know', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 50)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    path = 'questionnaire/grandmother'
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', 'dontknow')
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

  })

})
