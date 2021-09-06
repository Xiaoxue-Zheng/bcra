describe('ParticipantDetails', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"
    const UNUSED_NHS_NUMBER = 1111111111

    const REGISTERED_STUDY_CODE = "CYPRESS_TST_1"
    const REGISTERED_EMAIL = "test@test.com"

    before(function () {
        cy.createStudyIdFromCode(REGISTERED_STUDY_CODE)
        cy.registerPatientWithStudyCodeAndEmail(REGISTERED_STUDY_CODE, REGISTERED_EMAIL)

        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()

        cy.saveLocalStorage()
        Cypress.Cookies.preserveOnce('JSESSIONID')
        Cypress.Cookies.preserveOnce('CSRF')

    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE, REGISTERED_STUDY_CODE])
        cy.removeParticipant(UNREGISTERED_STUDY_CODE)
    })

    beforeEach(function() {
        cy.visit('participant-details')
        cy.removeIdentifiableDataForParticipant(UNREGISTERED_STUDY_CODE)
        cy.clearFields()
    })


    function authenticateSelf() {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('input').last().clear().type(STRONG_PASSWORD)
        cy.get('button').contains('Sign in').click()

        cy.url().should('include', 'participant-details')
    }

    it('should open the participant details page', () => {
        authenticateSelf()

        cy.url().should('include', 'participant-details')
        cy.contains('h1', 'Thank you for completing the questionnaire.')
    })

    it('should remain on the current page when no details have been entered', () => {
        authenticateSelf()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should remain on the current page when mandatory details have not been entered', () => {
        authenticateSelf()

        cy.fillNonMandatoryFields()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should navigate to the risk assessment questionnaire if all mandatory and non-mandatory details have been entered', () => {
        authenticateSelf()

        cy.fillMandatoryFields()
        cy.fillNonMandatoryFields()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'end')
    })
})
