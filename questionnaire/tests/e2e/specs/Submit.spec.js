describe('Submit', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"

    before(function () {
        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()
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
        cy.route('PUT', '/api/answer-responses/risk-assessment/submit').as('submitRiskAssessment')
        cy.route('POST', '/api/authentication').as('authentication')

        cy.visit('/signin')
        cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('input').last().clear().type(STRONG_PASSWORD)
        cy.get('button').contains('Sign in').click()
        cy.wait('@authentication').its('status').should('be', 200)

        cy.visit('/submit')
    }

    it('should load the submit page', () => {
        authenticateSelf()
        cy.get('.pure-button').contains('Submit Questionnaire')
    })

    it('should send the risk assessment questionnaire to the server backend and navigate to the detail page', () => {
        authenticateSelf()

        cy.get('.pure-button').contains('Submit Questionnaire').click()
        cy.wait('@submitRiskAssessment').its('status').should('be', 200)
        cy.url().should('include', '/participant-details')
    })
})
