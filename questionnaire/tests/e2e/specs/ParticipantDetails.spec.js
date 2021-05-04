describe('PartiicipantDetails', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"
    const UNUSED_NHS_NUMBER = 1111111111

    const REGISTERED_STUDY_CODE = "CYPRESS_TST_1"
    const REGISTERED_EMAIL = "test@test.com"
    const IN_USE_NHS_NUMBER = "1234512345"

    before(function () {
        cy.createStudyIdFromCode(REGISTERED_STUDY_CODE)
        cy.registerPatientWithStudyCodeAndEmail(REGISTERED_STUDY_CODE, REGISTERED_EMAIL)
        cy.updateParticipantDetails(REGISTERED_STUDY_CODE, IN_USE_NHS_NUMBER)

        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)

        cy.saveLocalStorage()
        Cypress.Cookies.preserveOnce('JSESSIONID')

    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE, REGISTERED_STUDY_CODE])
        cy.clearTables(['study_id', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
    })

    beforeEach(function() {
        clearFields()
    })

    function getFirstNameField() {
        return cy.get('input').eq(0)
    }

    function getSurnameField() {
        return cy.get('input').eq(1)
    }

    function getAddressField(number) {
        return cy.get('input').eq(2+number)
    }

    function getPostCodeField() {
        return cy.get('input').eq(7)
    }

    function getDateOfBirthField() {
        return cy.get('input').eq(8)
    }

    function getNhsNumberField() {
        return cy.get('input').eq(9)
    }

    function getGPNameField() {
        return cy.get('input').eq(10)
    }

    function clearFields() {
        for (let i = 0; i < 11; i++) {
            cy.get('input').eq(i).clear()
        }
    }

    function authenticateSelf() {
        cy.server()
        cy.visit('/signin')
        cy.get('input').first().clear().type(UNREGISTERED_EMAIL_ADDRESS)
        cy.get('input').last().clear().type(STRONG_PASSWORD)
        cy.get('button').click()

        cy.url().should('include', 'participant-details')
    }
    
    it('should open the participant details page', () => {
        authenticateSelf()

        cy.url().should('include', 'participant-details')
        cy.contains('h1', 'Participant details')
    })

    it('should remain on the current page when no details have been entered', () => {
        authenticateSelf()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should remain on the current page when only some required details have been entered', () => {
        authenticateSelf()

        getFirstNameField().type("Barry")
        getSurnameField().type("Smith")

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should remain on the current page when all details have been entered but the NHS number is in use', () => {
        authenticateSelf()

        getFirstNameField().type("Barry")
        getSurnameField().type("Smith")
        for (let i = 0; i < 5; i++) {
            getAddressField(i).type("Address line " + (i+1))
        }
        getPostCodeField().type("AA1 1AA")
        getDateOfBirthField().type("1990-01-01")
        getNhsNumberField().type(IN_USE_NHS_NUMBER)
        getGPNameField().type("GP name")

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should navigate to the risk assessment questionnaire if all details have been entered', () => {
        authenticateSelf()

        getFirstNameField().type("Barry")
        getSurnameField().type("Smith")
        for (let i = 0; i < 5; i++) {
            getAddressField(i).type("Address line " + (i+1))
        }
        getPostCodeField().type("AA1 1AA")
        getDateOfBirthField().type("1990-01-01")
        getNhsNumberField().type(UNUSED_NHS_NUMBER)
        getGPNameField().type("GP name")

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'questionnaire/familyhistorycontext')
    })
})
  