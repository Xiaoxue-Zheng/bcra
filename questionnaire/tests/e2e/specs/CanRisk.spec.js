describe('CanRisk', () => {
    const getStore = () => cy.window().its('app.$store')
    const STRONG_PASSWORD = "hard2Crack!!"
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_1"
    const UNREGISTERED_EMAIL_ADDRESS = "test@test.com"

    before(function () {
        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()
        cy.completeParticipantDetailsPage()

        cy.saveLocalStorage()
        Cypress.Cookies.preserveOnce('JSESSIONID')

    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
        cy.removeParticipant(UNREGISTERED_STUDY_CODE)
    })

    afterEach(function() {
        cy.deleteCanRiskReportForParticipant(UNREGISTERED_STUDY_CODE)
    })

    function authenticateSelf() {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('input').last().clear().type(STRONG_PASSWORD)
        cy.get('button').contains('Sign in').click()
    }

    it('should not open the can risk report page if the can risk for this participant has yet to be uploaded', () => {
        authenticateSelf()

        cy.url().should('include', 'end')
        cy.contains('h1', 'Thank you for submitting your questionnaire.')
    })

    it('should not open the can risk report page if the can risk for this participant has yet to be uploaded', () => {
        cy.createCanRiskReportForParticipant(UNREGISTERED_STUDY_CODE)
        authenticateSelf()

        cy.url().should('include', 'canriskreport')
        cy.contains('h1', 'CanRisk Report')
    })

    it('should open the can risk report page with a message explaining the can risk report has yet to be uploaded', () => {
        authenticateSelf()

        cy.visit('/canriskreport')
        cy.contains('h1', 'CanRisk Report')
        cy.contains('p', 'Your risk report will be available to view here once it has been uploaded.')
    })

    it('should open the can risk report page with a message explaining the can risk report is loading', () => {
        cy.createCanRiskReportForParticipant(UNREGISTERED_STUDY_CODE)
        authenticateSelf()

        cy.contains('h1', 'CanRisk Report')
        cy.contains('div', 'Loading CanRisk report, please wait...')
    })
})
