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
    cy.clearTables(['study_id', 'participant','answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
  })

  beforeEach(function () {
    Cypress.Cookies.preserveOnce('JSESSIONID')
    cy.restoreLocalStorage()

    cy.restartRiskAssessmentForStudyCode(UNREGISTERED_STUDY_CODE)
  })


  it('should success when complete all fields', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/yourhistorycontext'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.setWeightHeightNumberAnswer('SELF_HEIGHT', 200)
    cy.setWeightHeightNumberAnswer('SELF_WEIGHT', 50)
    cy.setNumberDontKnowAnswer('SELF_FIRST_PERIOD', 20)
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
  })

  it('should success when complete all fields includes dont know', () => {
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
  })

})
