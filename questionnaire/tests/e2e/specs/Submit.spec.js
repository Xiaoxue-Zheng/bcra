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
        cy.completeParticipantDetailsPage()
        cy.completeRiskAssessment()

        cy.saveLocalStorage()
    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
        cy.clearTables(['study_id', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
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

    it('should send the risk assessment questionnaire to the server backend', () => {
        authenticateSelf()

        cy.get('.pure-button').contains('Submit Questionnaire').click()
        cy.wait('@submitRiskAssessment').its('status').should('be', 200)
    })

    it('should navigate the user to the WIP page - NOTE: TO BE ALTERED IN LATER TICKETS', () => {
        authenticateSelf()

        cy.get('.pure-button').contains('Submit Questionnaire').click()
        cy.wait('@submitRiskAssessment').its('status').should('be', 200)
        cy.url().should('include', 'wip')
    })
})
