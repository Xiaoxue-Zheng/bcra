describe('Referral Conditions Tests', () => {
  const getStore = () => cy.window().its('app.$store')

  const NHS_NUMBER = '7616551351'
  const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
  const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
  const STRONG_PASSWORD = "hard2Crack!!"

  let path = 'questionnaire/family'

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

    cy.restartRiskAssessmentForStudyCode(UNREGISTERED_STUDY_CODE)
  })

  function resetPage() {
    cy.visit(path)
    cy.resetCheckboxAnswerItems('FAMILY_BREAST_AFFECTED')
    cy.resetCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED')
  }

  it('works with rule: ITEMS_AT_LEAST ( FAMILY_BREAST_AFFECTED , 2 )', () => {
    resetPage()

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()

    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT', 'FAMILY_BREAST_AFFECTED_NIECE']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Two or more of your family members have been affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_FATHER )', () => {
    resetPage()

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_FATHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your father has been affected by breast cancer')
  })

  it('checks: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_BROTHER )', () => {
    resetPage()

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_BROTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your brother has been affected by breast cancer')
  })

  it('checks: ITEMS_AT_LEAST ( FAMILY_OVARIAN_AFFECTED , 2 )', () => {
    resetPage()

    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_AUNT']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()

    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_AUNT', 'FAMILY_OVARIAN_AFFECTED_NIECE']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Two or more of your family members have been affected by ovarian cancer')
  })

  it('works with rule: ITEMS_AT_LEAST ( FAMILY_BREAST_AFFECTED , 1 ) AND ITEMS_AT_LEAST ( FAMILY_OVARIAN_AFFECTED , 1 )', () => {
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_NIECE']
    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_NIECE']

    resetPage()
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('You have family members have affected by both breast and ovarian cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_HOW_MANY_MORE )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_GRANDMOTHER')
    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_MORE')
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('You have more than one family member affected by breast cancer')
  })

  it('works with rule:  ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_MOTHER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 60 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_MOTHER')
    path = 'questionnaire/breast'

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 60)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 59)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your mother was under 60 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_SISTER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 60 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_SISTER')
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 60)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 59)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your sister was under 60 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_GRANDMOTHER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_GRANDMOTHER', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your grandmother was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_HALFSISTER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_HALFSISTER', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your half-sister was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_AUNT ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_AUNT', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your aunt was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_NIECE ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    resetPage()

    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_NIECE', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('Your niece was under 40 when she was affected by breast cancer')
  })

  it('works with rule:  ITEM_SPECIFIC ( FAMILY_OVARIAN_HOW_MANY_MORE )', () => {
    resetPage()

    cy.navigateToFamilyOvarianSection('FAMILY_OVARIAN_AFFECTED_NIECE', path)
    path = 'questionnaire/ovarian'

    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_ONE')
    cy.setNumberDontKnowAnswer('FAMILY_OVARIAN_AGE', 10)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_MORE')
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('You have more than one family member affected by ovarian cancer')
  })
})
