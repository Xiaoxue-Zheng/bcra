describe('SignIn', () => {
    const getStore = () => cy.window().its('app.$store')

    before(function () {

    })

    after(function() {

    })

    beforeEach(function() {

    })

    function authenticateSelf(email, password) {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(email)
        cy.get('input').last().clear().type(password)
        cy.get('button').contains('Sign in').click()
    }

    it('should open the participant details page', () => {
        authenticateSelf('admin@localhost', 'admin')
        cy.contains('p', 'It appears you have tried to access this questionnaire page in error. If you think this is a mistake, please contact the study team')
    })


})
