describe('PartiicipantDetails', () => {
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
        cy.updateParticipantDetails(REGISTERED_STUDY_CODE)

        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()

        cy.saveLocalStorage()
        Cypress.Cookies.preserveOnce('JSESSIONID')

    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE, REGISTERED_STUDY_CODE])
        cy.clearTables(['study_id','participant', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
    })

    beforeEach(function() {
        cy.visit('participant-details')
        cy.removeIdentifiableDataForParticipant(UNREGISTERED_STUDY_CODE)
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

    function getHomePhoneNumberField() {
        return cy.get('input').eq(8)
    }

    function getMobilePhoneNumberField() {
        return cy.get('input').eq(9)
    }

    function getPreferWayToContactField() {
        return cy.get('[type="checkbox"]').first() // Check checkbox element
    }

    function fillMandatoryFields() {
        getFirstNameField().type("Barry")
        getSurnameField().type("Smith")
        getAddressField(0).type("Address line 1")
        getPostCodeField().type("AA1 1AA")
        getPreferWayToContactField().check({force: true})
    }

    function fillNonMandatoryFields() {
        getAddressField(1).type("Address line 2")
        getAddressField(2).type("Address line 3")
        getAddressField(3).type("Address line 4")
        getAddressField(4).type("Address line 5")
        getHomePhoneNumberField("0123912390")
        getMobilePhoneNumberField("0123912390")
    }

    function clearFields() {
        for (let i = 0; i < 10; i++) {
            cy.get('input').eq(i).clear()
        }
        for (let i = 10; i < 14; i++) {
            cy.get('input').eq(i).uncheck({force: true})
        }
    }

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

        fillNonMandatoryFields()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'participant-details')
    })

    it('should navigate to the risk assessment questionnaire if all mandatory and non-mandatory details have been entered', () => {
        authenticateSelf()

        fillMandatoryFields()
        fillNonMandatoryFields()

        cy.get('.pure-button').contains('Save details').click()
        cy.url().should('include', 'end')
    })
})
