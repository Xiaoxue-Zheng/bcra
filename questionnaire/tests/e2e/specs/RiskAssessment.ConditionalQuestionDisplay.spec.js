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
    cy.removeParticipant(UNREGISTERED_STUDY_CODE)
  })

  beforeEach(function () {
    Cypress.Cookies.preserveOnce('JSESSIONID')
    cy.restoreLocalStorage()

    cy.restartRiskAssessmentForStudyCode(UNREGISTERED_STUDY_CODE)
  })

  it('conditionally displays FAMILY_BREAST_HOW_MANY correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(false)

    let ALL_FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_MOTHER',
      'FAMILY_BREAST_AFFECTED_GRANDMOTHER', 'FAMILY_BREAST_AFFECTED_SISTER',
      'FAMILY_BREAST_AFFECTED_HALFSISTER', 'FAMILY_BREAST_AFFECTED_AUNT',
      'FAMILY_BREAST_AFFECTED_NIECE', 'FAMILY_BREAST_AFFECTED_FATHER',
      'FAMILY_BREAST_AFFECTED_BROTHER', 'FAMILY_BREAST_AFFECTED_UNKNOWN'
    ]
    cy.checkAnswerItemsAreNotChecked(ALL_FAMILY_BREAST_AFFECTED_ITEMS)

    let ALL_FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_MOTHER',
      'FAMILY_OVARIAN_AFFECTED_GRANDMOTHER', 'FAMILY_OVARIAN_AFFECTED_SISTER',
      'FAMILY_OVARIAN_AFFECTED_HALFSISTER', 'FAMILY_OVARIAN_AFFECTED_AUNT',
      'FAMILY_OVARIAN_AFFECTED_NIECE', 'FAMILY_OVARIAN_AFFECTED_UNKNOWN'
    ]
    cy.checkAnswerItemsAreNotChecked(ALL_FAMILY_OVARIAN_AFFECTED_ITEMS)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkElementVisibility(true, 'FAMILY_BREAST_HOW_MANY')
    let ALL_HOW_MANY_ITEMS = ['FAMILY_BREAST_HOW_MANY_ONE', 'FAMILY_BREAST_HOW_MANY_MORE']
    cy.checkAnswerItemsAreNotChecked(ALL_HOW_MANY_ITEMS)

    cy.backAndAssertSuccessfulNavToPath(path)

    FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkElementVisibility(false, 'FAMILY_BREAST_HOW_MANY')
  })

  it('conditionally displays FAMILY_BREAST_AGE correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_BREAST_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_BREAST_AGE', '')

    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(false, 'FAMILY_BREAST_AGE')

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')
    cy.checkElementVisibility(true, 'FAMILY_BREAST_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_BREAST_AGE', '')
  })

  it('conditionally displays FAMILY_OVARIAN_HOW_MANY correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)

    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(false, 'FAMILY_OVARIAN_HOW_MANY')
    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_OVARIAN_HOW_MANY')
    let ALL_HOW_MANY_ITEMS = ['FAMILY_OVARIAN_HOW_MANY_ONE', 'FAMILY_OVARIAN_HOW_MANY_MORE']
    cy.checkAnswerItemsAreNotChecked(ALL_HOW_MANY_ITEMS)
  })

  it('conditionally displays FAMILY_OVARIAN_AGE correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_MOTHER']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(true, 'FAMILY_OVARIAN_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_OVARIAN_AGE', '')
    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_GRANDMOTHER']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkElementVisibility(false, 'FAMILY_OVARIAN_AGE')
    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_ONE')
    cy.checkElementVisibility(true, 'FAMILY_OVARIAN_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_OVARIAN_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_OVARIAN_AGE', 10)
    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_MORE')
    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_ONE')
    cy.checkNumberDontKnowAnswer('FAMILY_OVARIAN_AGE', '')
  })

  it('conditionally displays FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE correctly', () => {
    cy.visitFamilyAffectedSection('grandmother')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_FATHER')
    cy.checkElementVisibility(false, 'FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER')
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', '')

    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', 10)
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE',10)
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', 'dontknow')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE','dontknow')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_FATHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_GRANDMOTHER_SIDE_MOTHER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_GRANDMOTHER_MOTHERS_AGE', '')
  })

  it('conditionally displays FAMILY_AFFECTED_AUNT_MOTHERS_AGE correctly', () => {
    cy.visitFamilyAffectedSection('aunt')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_FATHER')
    cy.checkElementVisibility(false, 'FAMILY_AFFECTED_AUNT_MOTHERS_AGE')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_MOTHER')
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_AUNT_MOTHERS_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_AUNT_MOTHERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_AUNT_MOTHERS_AGE', 10)
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_FATHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_MOTHER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_AUNT_MOTHERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_AUNT_MOTHERS_AGE', 'dontknow')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_FATHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_AUNT_SIDE_MOTHER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_AUNT_MOTHERS_AGE', '')
  })

  it('conditionally displays FAMILY_AFFECTED_NIECE_SISTERS_AGE correctly', () => {
    cy.visitFamilyAffectedSection('niece')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_BROTHER')
    cy.checkElementVisibility(false, 'FAMILY_AFFECTED_NIECE_SISTERS_AGE')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_SISTER')
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_NIECE_SISTERS_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_NIECE_SISTERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_NIECE_SISTERS_AGE', 10)
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_BROTHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_SISTER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_NIECE_SISTERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_NIECE_SISTERS_AGE', 'dontknow')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_BROTHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_NIECE_SIDE_SISTER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_NIECE_SISTERS_AGE', '')
  })

  it('conditionally displays FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE correctly', () => {
    cy.visitFamilyAffectedSection('halfsister')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_FATHER')
    cy.checkElementVisibility(false, 'FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER')
    cy.checkElementVisibility(true, 'FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE', 10)
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_FATHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE', '')
    cy.setNumberDontKnowAnswer('FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE', 'dontknow')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_FATHER')
    cy.setRadioAnswerItem('FAMILY_AFFECTED_HALFSISTER_SIDE_MOTHER')
    cy.checkNumberDontKnowAnswer('FAMILY_AFFECTED_HALFSISTER_MOTHERS_AGE', '')
  })

  it('conditionally displays SELF_PREGNANCIES_FIRST_AGE correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/yourhistorycontext'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNumberDropdownAnswer('SELF_PREGNANCIES', null) //- can't check null condition??
    //select Don't Know
    cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 'Don\'t know')
    cy.checkElementVisibility(false, 'SELF_PREGNANCY_FIRST_AGE')
    cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 0)
    cy.checkElementVisibility(false, 'SELF_PREGNANCY_FIRST_AGE')
    cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 1)
    cy.checkElementVisibility(true, 'SELF_PREGNANCY_FIRST_AGE')
    cy.setNumberAnswer('SELF_PREGNANCY_FIRST_AGE', 10)
    cy.checkNumberAnswer('SELF_PREGNANCY_FIRST_AGE', 10)
    cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 0)
    cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 1)
    cy.checkNumberAnswer('SELF_PREGNANCY_FIRST_AGE', null)
  })

  it('conditionally displays SELF_MENOPAUSAL_AGE correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/yourhistorycontext'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_YES')
    cy.checkElementVisibility(false, 'SELF_MENOPAUSAL_AGE')
    cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_UNKNOWN')
    cy.checkElementVisibility(false, 'SELF_MENOPAUSAL_AGE')
    cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_NO')
    cy.checkElementVisibility(true, 'SELF_MENOPAUSAL_AGE')
    cy.checkNumberAnswer('SELF_MENOPAUSAL_AGE', '')
    cy.setNumberAnswer('SELF_MENOPAUSAL_AGE', 20)
    cy.checkNumberAnswer('SELF_MENOPAUSAL_AGE', 20)
    cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_YES')
    cy.setRadioAnswerItem('SELF_PREMENOPAUSAL_NO')
    cy.checkNumberAnswer('SELF_MENOPAUSAL_AGE', '')
  })

  it('conditionally displays SELF_BREAST_BIOPSY_DIAGNOSIS_RISK  and SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES correctly', () => {
    let path = 'questionnaire/family'
    cy.visitFamilyAffectedPrimarySection(true)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    path = 'questionnaire/yourhistorycontext'
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_NO')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_UNKNOWN')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_YES')
    cy.checkElementVisibility(true, 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_NO')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_UNKNOWN')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES')
    cy.checkElementVisibility(true, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')

    let BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS = ['SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH']
    cy.setCheckboxAnswerItems('SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES', BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS)

    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_NO')
    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_YES')
    cy.checkElementVisibility(true, 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK')
    cy.checkElementVisibility(false, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')

    cy.checkRadioAnswerItemIsNotChecked('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES')
    cy.checkRadioAnswerItemIsNotChecked('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_NO')
    cy.checkRadioAnswerItemIsNotChecked('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_UNKNOWN')

    cy.setRadioAnswerItem('SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES')
    cy.checkElementVisibility(true, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES')

    BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS = []
    cy.checkCheckboxAnswerItems('SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES', BIOPSY_DIAGNOSIS_TYPE_CHECKED_ITEMS)
  })
})
