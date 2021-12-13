describe('Register', () => {
    const getStore = () => cy.window().its('app.$store')
    const REGISTERED_STUDY_CODE = "CYPRESS_TST_1"
    const REGISTERED_EMAIL = "test@test.com"
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const NON_EXISTENT_STUDY_CODE = "CYPRESS_FAKE"

    before(function () {
      cy.createStudyIdFromCode(REGISTERED_STUDY_CODE)
      cy.registerPatientWithStudyCodeAndEmail(REGISTERED_STUDY_CODE, REGISTERED_EMAIL)
      cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
    })

    after(function () {
      cy.deleteParticipants([REGISTERED_STUDY_CODE, UNREGISTERED_STUDY_CODE])
      cy.removeParticipant(UNREGISTERED_STUDY_CODE)
    })

    it('should open the register page', () => {
      cy.server()
      cy.route({
        method: 'GET',
        url: '/api/account'
      })
      .as('getAccount')

      cy.visit('/register')
      cy.contains('h1', 'Register')

      cy.get('input').first().should('not.be.checked')
      cy.get('input').eq(1).should('have.value', '')
      cy.get('input').eq(2).should('have.value', '')

      cy.wait('@getAccount')
    })

    it('should display an error on already registered study code', () => {
      cy.server()
      cy.route({
        method: 'GET',
        url: '/api/account'
      })
      .as('getAccount')

      cy.completeRegisterPage(REGISTERED_STUDY_CODE)

      cy.get('div').contains('This study code is either in use or otherwise not available. Please double check code or contact the study team.').should('exist')

      cy.wait('@getAccount')
    })

    it('should display an error when study code entered does not exist', () => {
      cy.server()
      cy.route({
        method: 'GET',
        url: '/api/account'
      })
      .as('getAccount')

      cy.completeRegisterPage(NON_EXISTENT_STUDY_CODE)

      cy.get('div').contains('This study code is either in use or otherwise not available. Please double check code or contact the study team.').should('exist')

      cy.wait('@getAccount')
    })

    it('should display an error when a the page is incomplete', () => {
      cy.server()
      cy.route({
        method: 'GET',
        url: '/api/account'
      })
      .as('getAccount')

      cy.get('input').first().uncheck()
      cy.get('input').eq(1).type(UNREGISTERED_STUDY_CODE)
      cy.get('input').eq(2).type('1990-01-01')
      cy.get('button').contains('Next').click()

      cy.url().should('include', 'register')
    })

    it('should navigate to the consent page when a valid study code is entered', () => {
      cy.server()
      cy.route({
        method: 'GET',
        url: '/api/account'
      })
      .as('getAccount')

      cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)

      cy.wait('@getAccount')

      cy.url().should('include', 'consent')
    })
  })
