describe('Register', () => {
  const getStore = () => cy.window().its('app.$store')

  const EXISTING_NHSNUMBER = '5236394309'
  const EXISTING_DATEOFBIRTH = '1987-07-13'

  const VALID_NHSNUMBER = '8348860339'
  const VALID_DATEOFBIRTH = '1983-09-02'

  const INCORRECT_NHSNUMBER = '0000000000'
  const INCORRECT_DATEOFBIRTH = '1000-01-01'

  before(function () {
    cy.registerParticipant(EXISTING_NHSNUMBER, 'register-test-existing@example.com', 'existing-password-hash')
    cy.unregisterParticipant(VALID_NHSNUMBER)
  })
  
  it('opens the register page', () => {
    cy.server()
    cy.route({
      method: 'GET',
      url: '/api/account'
    })
    .as('getAccount')

    cy.visit('/register')
    cy.contains('h1', 'Register')

    cy.get('input').first().should('have.value', '')
    cy.get('input').last().should('have.value', '')

    cy.wait('@getAccount')
  })

  it('fails with registered details', () => {
    cy.server()
    cy.route({
        method: 'GET',
        url: '/api/participants/exists?nhsNumber=*&dateOfBirth=*'
    })
    .as('getExists')

    cy.get('input').first().clear().type(EXISTING_NHSNUMBER)
    cy.get('input').last().clear().type(EXISTING_DATEOFBIRTH)
    cy.get('button').click()
    
    cy.wait('@getExists').its('status').should('equal', 200)

    cy.contains('already have an account').should('be.visible')
    cy.contains('not found').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl + 'register')
  })

  it('fails with wrong nhs number', () => {
    cy.server()
    cy.route({
        method: 'GET',
        url: '/api/participants/exists?nhsNumber=*&dateOfBirth=*'
    })
    .as('getExists')

    cy.get('input').first().clear().type(INCORRECT_NHSNUMBER)
    cy.get('input').last().clear().type(VALID_DATEOFBIRTH)
    cy.get('button').click()
    
    cy.wait('@getExists').its('status').should('equal', 200)

    cy.contains('not found').should('be.visible')
    cy.contains('already have an account').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl + 'register')
  })

  it('fails with wrong date of birth', () => {
    cy.server()
    cy.route({
        method: 'GET',
        url: '/api/participants/exists?nhsNumber=*&dateOfBirth=*'
    })
    .as('getExists')

    cy.get('input').first().clear().type(VALID_NHSNUMBER)
    cy.get('input').last().clear().type(INCORRECT_DATEOFBIRTH)
    cy.get('button').click()
    
    cy.wait('@getExists').its('status').should('equal', 200)

    cy.contains('not found').should('be.visible')
    cy.contains('already have an account').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl + 'register')
  })

  it('proceeds with valid details', () => {
    cy.server()
    cy.route({
        method: 'GET',
        url: '/api/participants/exists?nhsNumber=*&dateOfBirth=*'
    })
    .as('getExists')

    cy.get('input').first().clear().type(VALID_NHSNUMBER)
    cy.get('input').last().clear().type(VALID_DATEOFBIRTH)
    cy.get('button').click()
    
    cy.wait('@getExists').its('status').should('equal', 200)

    cy.url().should('equal', Cypress.config().baseUrl + 'account')
    cy.contains('h1', 'Create your account')
  })
})
