describe('Sign In', () => {
    const getStore = () => cy.window().its('app.$store')

    const NHS_NUMBER = '7616551351'
    const EMAIL_ADDRESS = 'consent-test@example.com'
    const PASSWORD = 'testpassword'
    const PASSWORD_HASH = '$2a$10$xfxxf5eZbLo0S70V55c8FO6R.741QpR4Lkh84749m/B7kP6/XIFc2'

    const CONSENT_QUESTION_INPUTS = [
        '#CONSENT_INFO_SHEET_YES',
        '#CONSENT_WITHDRAWAL',
        '#CONSENT_DATA_TRUST',
        '#CONSENT_DATA_RESEARCH',
        '#CONSENT_DATA_UOM',
        '#CONSENT_DATA_COMMERCIAL',
        '#CONSENT_INFORM_GP',
        '#CONSENT_TAKE_PART',
        '#CONSENT_BY_LETTER',
        '#CONSENT_FUTURE_RESEARCH_YES'
    ]

    before(function () {
      cy.registerParticipant(NHS_NUMBER, EMAIL_ADDRESS, PASSWORD_HASH)
      cy.resetConsent(NHS_NUMBER)
    })

    beforeEach(function(){
        Cypress.Cookies.preserveOnce('JSESSIONID')
    })

    it('opens the consent form after first sign in', () => {
      cy.server()
      cy.visit('/signin')
      cy.get('input').first().clear().type(EMAIL_ADDRESS)
      cy.get('input').last().clear().type(PASSWORD)
      cy.get('button').click()

      cy.url().should('equal', Cypress.config().baseUrl + 'consent')

      cy.contains('Consent').should('be.visible')

      cy.get('.progress').contains('1').should('have.class', 'current')
      cy.get('.progress').contains('1').should('not.have.class', 'complete')
      cy.get('.progress').contains('2').should('not.have.class', 'complete')
      cy.get('.progress').contains('2').should('not.have.class', 'current')
      cy.get('.progress').contains('3').should('not.have.class', 'complete')
      cy.get('.progress').contains('3').should('not.have.class', 'current')
      cy.get('.progress').contains('4').should('not.have.class', 'complete')
      cy.get('.progress').contains('4').should('not.have.class', 'current')
      cy.get('.progress').contains('5').should('not.have.class', 'complete')
      cy.get('.progress').contains('5').should('not.have.class', 'current')

      cy.get('form').find('fieldset').should('have.length', CONSENT_QUESTION_INPUTS.length)

      cy.contains('I give my consent').should('be.visible')
    })

    it('will not submit with a required yes/no question unchecked', () => {
        for (let inputs of CONSENT_QUESTION_INPUTS){
            cy.get(inputs).check({force: true})
        }

        cy.get('#CONSENT_INFO_SHEET_NO').check({force: true})

        cy.get('[type="submit"]').click()
        cy.contains('Please complete all of the questions above to continue.').should('be.visible')
    })

    it('will not submit with a required tickbox question unchecked', () => {
        for (let inputs of CONSENT_QUESTION_INPUTS){
            cy.get(inputs).check({force: true})
        }

        cy.get('#CONSENT_INFORM_GP').uncheck({force: true})

        cy.get('[type="submit"]').click()
        cy.contains('Please complete all of the questions above to continue.').should('be.visible')
    })

    it('submits the consent form with all fields checked', () => {
        cy.server()
        cy.route({
            method: 'PUT',
            url: '/api/answer-responses/consent/submit'
        })
        .as('submitConsent')

        for (let inputs of CONSENT_QUESTION_INPUTS){
            cy.get(inputs).check({force: true})
        }

        cy.get('[type="submit"]').click()
        cy.wait('@submitConsent').its('status').should('equal', 200)

        cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/familyhistorycontext')
        cy.contains('Family History').should('be.visible')
      })

      it('submits the consent form with a non-required question unchecked', () => {
        cy.visit('/consent')

        cy.server()
        cy.route({
            method: 'PUT',
            url: '/api/answer-responses/consent/submit'
        })
        .as('submitConsent')

        for (let inputs of CONSENT_QUESTION_INPUTS){
            cy.get(inputs).check({force: true})
        }

        cy.get('#CONSENT_FUTURE_RESEARCH_NO').check({force: true})

        cy.get('[type="submit"]').click()
        cy.wait('@submitConsent').its('status').should('equal', 200)

        cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/familyhistorycontext')
        cy.contains('Family History').should('be.visible')
      })
  })
