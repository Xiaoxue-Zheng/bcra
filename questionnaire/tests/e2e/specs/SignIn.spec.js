describe('Sign In', () => {
  const getStore = () => cy.window().its('app.$store')

  const VALID_EMAILADDRESS = 'cypressexisting@example.com'
  const VALID_PASSWORD = 'testpassword'
  const INCORRECT_PASSWORD = 'wrongpassword'

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

    cy.get('input').first().type(VALID_EMAILADDRESS)
    cy.get('input').last().type(INCORRECT_PASSWORD)
    cy.get('button').click()
    
    cy.wait('@postLogin').its('status').should('equal', 401)

    cy.contains('Bad username or password.').should('be.visible')
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

    cy.get('input').first().clear().type(VALID_EMAILADDRESS)
    cy.get('input').last().clear().type(VALID_PASSWORD)
    cy.get('button').click()
    
    cy.wait('@postLogin').its('status').should('equal', 200)

    cy.contains('Bad username or password.').should('not.be.visible')
    cy.contains('Sign in').should('not.be.visible')
    cy.contains('Sign out').should('be.visible')
    cy.contains('Register').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl)
  })
})
