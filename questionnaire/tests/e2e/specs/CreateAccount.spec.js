describe('CreateAccount', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const REGISTERED_STUDY_CODE = "CYPRESS_TST_1"
    const REGISTERED_EMAIL = "test1@test.com"

    const STRONG_PASSWORD = "hard2Crack!!"
    const WEAK_PASSWORD = "password"

    before(function () {
        cy.createStudyIdFromCode(REGISTERED_STUDY_CODE)
        cy.registerPatientWithStudyCodeAndEmail(REGISTERED_STUDY_CODE, REGISTERED_EMAIL)

        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()

        cy.saveLocalStorage()
    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE, REGISTERED_STUDY_CODE])
        cy.clearTables(['study_id', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
    })

    beforeEach(function() {
        cy.restoreLocalStorage()
        clearFields()
    })

    function getEmailField() {
        return cy.get('input').first()
    }

    function getPasswordField() {
        return cy.get('input').eq(1)
    }

    function getPasswordConfirmationField() {
        return cy.get('input').eq(2)
    }

    function clearFields() {
        getEmailField().clear()
        getPasswordField().clear()
        getPasswordConfirmationField().clear()
    }

    it('should open the account page', () => {
        cy.url().should('include', 'account')
        cy.contains('h1', 'Create your account')
    })

    it('should not allow the user to continue without entering in their email address and a strong password', () => {
        cy.get('button').contains('Create account').should('be.disabled')
    })

    it('should not allow the user to continue without entering in a password', () => {
        getEmailField().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('button').contains('Create account').should('be.disabled')
    })

    it('should not allow the user to continue without an email', () => {
        getPasswordField().type(STRONG_PASSWORD)
        getPasswordConfirmationField().type(STRONG_PASSWORD)
        cy.get('button').contains('Create account').should('be.disabled')
    })

    it('should not allow the user to continue without a strong password', () => {
        getEmailField().type(UNREGISTERED_EMAIL_ADDRESS)
        getPasswordField().type(WEAK_PASSWORD)
        getPasswordConfirmationField().type(WEAK_PASSWORD)
        cy.get('button').contains('Create account').should('be.disabled')
    })

    it('should not allow the user to continue without matching password and password confirmation fields', () => {
        getEmailField().type(UNREGISTERED_EMAIL_ADDRESS)
        getPasswordField().type(STRONG_PASSWORD)
        getPasswordConfirmationField().type(WEAK_PASSWORD)
        cy.get('button').contains('Create account').should('be.disabled')
    })

    it('should allow the user to continue when an email address and strong password have been entered', () => {
        getEmailField().type(UNREGISTERED_EMAIL_ADDRESS)
        getPasswordField().type(STRONG_PASSWORD)
        getPasswordConfirmationField().type(STRONG_PASSWORD)
        cy.get('button').contains('Create account').should('not.be.disabled')
    })

    it('should not allow registration with an email address that is tied to an existing account', () => {
        getEmailField().type(REGISTERED_EMAIL)
        getPasswordField().type(STRONG_PASSWORD)
        getPasswordConfirmationField().type(STRONG_PASSWORD)
        cy.get('button').contains('Create account').click()
        cy.get('.error-message').contains('Something went wrong. Please try again.')
    })

    it('should register the user and navigate them to the participant details page', () => {
        getEmailField().type(UNREGISTERED_EMAIL_ADDRESS)
        getPasswordField().type(STRONG_PASSWORD)
        getPasswordConfirmationField().type(STRONG_PASSWORD)
        cy.get('button').contains('Create account').click()
        cy.url().should('include', '/questionnaire/familyhistorycontext')
    })

})
