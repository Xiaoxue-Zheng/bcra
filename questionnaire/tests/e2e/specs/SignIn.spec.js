describe('Sign In', () => {
  const getStore = () => cy.window().its('app.$store')

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

  it('logs in', () => {
    cy.server()
    cy.route({
        method: 'POST',
        url: '/api/authentication'
      })
      .as('postLogin')

    getStore().then(store => {
      store.dispatch('security/login', { username: 'admin', password: 'admin' })
    })

    cy.wait('@postLogin').its('status').should('equal', 200)
  })
})
