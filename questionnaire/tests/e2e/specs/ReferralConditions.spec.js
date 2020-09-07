describe('Referral Conditions Tests', () => {
  const getStore = () => cy.window().its('app.$store')

  const NHS_NUMBER = '7616551351'
  const EMAIL_ADDRESS = 'consent-test@example.com'
  const PASSWORD_HASH = '$2a$10$xfxxf5eZbLo0S70V55c8FO6R.741QpR4Lkh84749m/B7kP6/XIFc2'

  let path = 'questionnaire/family'

  before(function () {
    cy.registerParticipant(NHS_NUMBER, EMAIL_ADDRESS, PASSWORD_HASH)
    cy.signInAndConsent()
  })

  beforeEach(function () {
    cy.resetQuestionnaire(NHS_NUMBER)
    // questionnaire setting asynchronous and not yet handled through callback or await mechanism (it's a little complicated). Until then, I am imposing a
    // 'wait' in order to allow the query to finish. I'll figure out a better way, but this will do for now. Doesn't seem to be a problem in other tests
    // probably just due to timing of them, so adding the wait here instead of inside the resetQuestionnaire method
    cy.wait(300)
    Cypress.Cookies.preserveOnce('JSESSIONID')
    path = 'questionnaire/family'
  })

  it('works with rule: ITEMS_AT_LEAST ( FAMILY_BREAST_AFFECTED , 2 )', () => {
    cy.visit(path)
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()

    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_AUNT', 'FAMILY_BREAST_AFFECTED_NIECE']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('two or more of your family members have been affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_FATHER )', () => {
    cy.visit(path)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_FATHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your father has been affected by breast cancer')
  })

  it('checks: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_BROTHER )', () => {
    cy.visit(path)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_BROTHER']
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your brother has been affected by breast cancer')
  })

  it('checks: ITEMS_AT_LEAST ( FAMILY_OVARIAN_AFFECTED , 2 )', () => {
    cy.visit(path)
    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_AUNT']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)
    FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_AUNT', 'FAMILY_OVARIAN_AFFECTED_NIECE']
    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('two or more of your family members have been affected by ovarian cancer')
  })

  it('works with rule: ITEMS_AT_LEAST ( FAMILY_BREAST_AFFECTED , 1 ) AND ITEMS_AT_LEAST ( FAMILY_OVARIAN_AFFECTED , 1 )', () => {
    let FAMILY_BREAST_AFFECTED_ITEMS = ['FAMILY_BREAST_AFFECTED_NIECE']
    let FAMILY_OVARIAN_AFFECTED_ITEMS = ['FAMILY_OVARIAN_AFFECTED_NIECE']

    cy.visit(path)
    cy.setCheckboxAnswerItems('FAMILY_BREAST_AFFECTED', FAMILY_BREAST_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setCheckboxAnswerItems('FAMILY_OVARIAN_AFFECTED', FAMILY_OVARIAN_AFFECTED_ITEMS)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('you have family members have affected by both breast and ovarian cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_HOW_MANY_MORE )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_GRANDMOTHER')
    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_MORE')
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('you have more than one family member affected by breast cancer')
  })

  it('works with rule:  ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_MOTHER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 60 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_MOTHER')
    path = 'questionnaire/breast'
    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 60)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 59)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your mother was under 60 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_SISTER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 60 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_SISTER')
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 60)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 59)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your sister was under 60 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_GRANDMOTHER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_GRANDMOTHER', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your grandmother was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_HALFSISTER ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_HALFSISTER', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your half-sister was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_AUNT ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_AUNT', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your aunt was under 40 when she was affected by breast cancer')
  })

  it('works with rule: ITEM_SPECIFIC ( FAMILY_BREAST_AFFECTED_NIECE ) AND VALUE_UNDER ( FAMILY_BREAST_AGE , 40 )', () => {
    cy.visit(path)
    cy.navigateToFamilyBreastSection('FAMILY_BREAST_AFFECTED_NIECE', path)
    path = 'questionnaire/breast'

    cy.setRadioAnswerItem('FAMILY_BREAST_HOW_MANY_ONE')

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 40)

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setNumberDontKnowAnswer('FAMILY_BREAST_AGE', 39)
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('your niece was under 40 when she was affected by breast cancer')
  })

  it('works with rule:  ITEM_SPECIFIC ( FAMILY_OVARIAN_HOW_MANY_MORE )', () => {
    cy.visit(path)
    cy.navigateToFamilyOvarianSection('FAMILY_OVARIAN_AFFECTED_NIECE', path)
    path = 'questionnaire/ovarian'

    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_ONE')

    cy.submitAndAssertSuccessfulNavAwayFromPath(path)
    cy.checkNotReferred()
    cy.backAndAssertSuccessfulNavToPath(path)

    cy.setRadioAnswerItem('FAMILY_OVARIAN_HOW_MANY_MORE')
    cy.submitAndAssertSuccessfulNavAwayFromPath(path)

    cy.checkReferredWithCorrectReason('you have more than one family member affected by ovarian cancer')
  })
})
