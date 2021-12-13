describe('SignIn', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTER_EMAIL_ADDRESS = 'unregister@localhost'
    const TO_REGISTER_STUDY_CODE = "CYPRESS_TST_2"
    const TO_REGISTER_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"

    after(function () {
      cy.deleteParticipants([TO_REGISTER_STUDY_CODE])
      cy.removeParticipant(TO_REGISTER_STUDY_CODE)
    })

    function authenticateSelf(email, password) {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(email)
        cy.get('input').last().clear().type(password)
        cy.get('button').contains('Sign in').click()
    }

    it('should sign in fail when the user is not participant', () => {
        authenticateSelf('admin@localhost', 'admin')
        cy.contains('p', 'It appears you have tried to access this questionnaire page in error. If you think this is a mistake, please contact the study team')
    })

    it('should load reset password',() => {
      cy.server()
      cy.visit('/signin')
      cy.contains('p', 'Did you forget your password?')
    })

    it('should show error message if the email did not register when reset password',() => {
      cy.server()
      cy.visit('/signin')
      cy.get('p').contains('Did you forget your password?').click()
      cy.get('input').first().type(UNREGISTER_EMAIL_ADDRESS)
      cy.get('.pure-button').contains('Reset').click()
      cy.contains('p','Email address not registered')
    })

    it('should success if the email registered when reset password',() => {
      cy.createStudyCodeAndRegister(TO_REGISTER_STUDY_CODE, TO_REGISTER_EMAIL_ADDRESS, STRONG_PASSWORD)
      cy.server()
      cy.visit('/signin')
      cy.get('p').contains('Did you forget your password?').click()
      cy.get('input').first().type(TO_REGISTER_EMAIL_ADDRESS)
      cy.get('.pure-button').contains('Reset').click()
      cy.contains('p','Check your emails for details on how to reset your password.')
    })
})
