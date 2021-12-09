describe('Referral', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"

    before(function () {
        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessmentAndReferredWithoutSubmit()
        cy.restartRiskAssessmentForStudyCode(UNREGISTERED_STUDY_CODE)
        cy.saveLocalStorage()
    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
        cy.removeParticipant(UNREGISTERED_STUDY_CODE)
    })

    beforeEach(function() {
        Cypress.Cookies.preserveOnce('JSESSIONID')
        cy.restoreLocalStorage()
    })

    function authenticateSelf() {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('input').last().clear().type(STRONG_PASSWORD)
        cy.get('button').contains('Sign in').click()

        cy.visit('referral')
    }

    it('should load the referral page', () => {
        authenticateSelf()
        cy.get('.content').contains('You will be referred because...')
        cy.get('.pure-button').contains('Submit Questionnaire')
    })

    it('should explain what happens when a participant is referred', () => {
        authenticateSelf()

        cy.get('.pure-button').click()
        cy.contains('The answers you have provided')
    })
})
