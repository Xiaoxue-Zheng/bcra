describe('Sign In', () => {
  const getStore = () => cy.window().its('app.$store')

  const NHS_NUMBER = '7616551351'
  const EMAIL_ADDRESS = 'sign-in-test@example.com'
  const PASSWORD_HASH = '$2a$10$xfxxf5eZbLo0S70V55c8FO6R.741QpR4Lkh84749m/B7kP6/XIFc2'
  const VALID_PASSWORD = 'testpassword'
  const INCORRECT_PASSWORD = 'wrongpassword'

  before(function () {
    cy.registerParticipant(NHS_NUMBER, EMAIL_ADDRESS, PASSWORD_HASH)
  })

  it('opens the sign-in page', () => {
    cy.server()
    cy.route({
      method: 'GET',
      url: '/api/account'
    })
    .as('getAccount')

    cy.visit('/signin')
    cy.contains('h1', 'Sign in')

    cy.wait('@getAccount')
  })

  it('fails with wrong credentials', () => {
    cy.server()
    cy.route({
        method: 'POST',
        url: '/api/authentication'
    })
    .as('postLogin')

    cy.get('input').first().type(EMAIL_ADDRESS)
    cy.get('input').last().type(INCORRECT_PASSWORD)
    cy.get('button').click()
    
    cy.wait('@postLogin').its('status').should('equal', 401)

    cy.contains('username or password were not recognised').should('be.visible')
    cy.contains('Sign in').should('be.visible')
    cy.contains('Sign out').should('not.be.visible')
    cy.contains('Register').should('be.visible')

    cy.url().should('equal', Cypress.config().baseUrl + 'signin')
  })

  it('logs in', () => {
    cy.server()
    cy.route({
        method: 'POST',
        url: '/api/authentication'
    })
    .as('postLogin')

    cy.get('input').first().clear().type(EMAIL_ADDRESS)
    cy.get('input').last().clear().type(VALID_PASSWORD)
    cy.get('button').click()
    
    cy.wait('@postLogin').its('status').should('equal', 200)

    cy.contains('Bad username or password.').should('not.be.visible')
    cy.contains('Sign in').should('not.be.visible')
    cy.contains('Sign out').should('be.visible')
    cy.contains('Register').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl + 'consent')
  })
})
