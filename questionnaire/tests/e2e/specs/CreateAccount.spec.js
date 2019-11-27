describe('Register', () => {
  const getStore = () => cy.window().its('app.$store')

  const NHS_NUMBER = '2244668899'
  const DATE_OF_BIRTH = '1995-06-07'

  const NEW_EMAIL_ADDRESS = 'cypressnew@example.com'
  
  const SHORT_PASSWORD = 'cat'
  const WEAK_PASSWORD = 'catdogcat'
  const REPETATIVE_PASSWORD = 'catcatcat123123123'
  const STRONG_PASSWORD = 'zebra9456other3118leave2249'

  before(() =>{
    cy.exec('psql -U bcra  -d bcra -c "UPDATE jhi_user SET password_hash=null where login=\'cypressnew\'"')
    cy.exec('psql -U bcra  -d bcra -c "UPDATE identifiable_data set email=null where email=\'cypressnew@example.com\'"')
  })
      
  it('starts with the register page', () => {
    cy.server()
    cy.route({
      method: 'GET',
      url: '/api/account'
    })
    .as('getAccount')
  
    cy.visit('/register')
    cy.wait('@getAccount')
  })
    
  it('proceeds to create account from register', () => {
    cy.server()
    cy.route({
      method: 'GET',
      url: '/api/participants/exists?nhsNumber=*&dateOfBirth=*'
    })
    .as('getExists')
  
    cy.get('input').first().type(NHS_NUMBER)
    cy.get('input').last().type(DATE_OF_BIRTH)
    cy.get('button').click()
      
    cy.wait('@getExists').its('status').should('equal', 200)
  
    cy.url().should('equal', Cypress.config().baseUrl + 'account')
    cy.contains('h1', 'Create your account')
  })

  it('warns about a short password', () => {
    cy.get('input[type="text"]').clear().type(NEW_EMAIL_ADDRESS)
    cy.get('input[type="password"]').first().clear().type(SHORT_PASSWORD)
    cy.get('input[type="password"]').last().clear().type(SHORT_PASSWORD)

    cy.contains('Add another word')
  })
      
  it('warns about a weak password', () => {
    cy.get('input[type="text"]').clear().type(NEW_EMAIL_ADDRESS)
    cy.get('input[type="password"]').first().clear().type(WEAK_PASSWORD)
    cy.get('input[type="password"]').last().clear().type(WEAK_PASSWORD)

    cy.contains('similar to a commonly used password')
  })

  it('warns about a repetative password', () => {
    cy.get('input[type="text"]').clear().type(NEW_EMAIL_ADDRESS)
    cy.get('input[type="password"]').first().clear().type(REPETATIVE_PASSWORD)
    cy.get('input[type="password"]').last().clear().type(REPETATIVE_PASSWORD)
  
    cy.contains(/Repeats like .+ are only slightly harder to guess than/)
  })

  it('warns about miss-matching passwords', () => {
    cy.get('input[type="text"]').clear().type(NEW_EMAIL_ADDRESS)
    cy.get('input[type="password"]').first().clear().type(WEAK_PASSWORD)
    cy.get('input[type="password"]').last().clear().type(REPETATIVE_PASSWORD)

    cy.contains('don\'t match')
  })    

  it('succeeds and logs in for new email and strong password', () => {
    cy.server()
    cy.route({
      method: 'POST',
      url: '/api/participants/activate'
    })
    .as('activateParticipant')

    cy.route({
      method: 'POST',
      url: '/api/authentication'
    })
    .as('postLogin')
    
    cy.get('input[type="text"]').clear().type(NEW_EMAIL_ADDRESS)
    cy.get('input[type="password"]').first().clear().type(STRONG_PASSWORD)
    cy.get('input[type="password"]').last().clear().type(STRONG_PASSWORD)
    cy.get('button').click()

    cy.wait('@activateParticipant').then((activateParticipant) => {
      cy.wrap(activateParticipant).its('status').should('equal', 201)
      cy.wrap(activateParticipant).its('xhr.statusText').should('equal', 'Created')
    })

    cy.wait('@postLogin').its('status').should('equal', 200)
    cy.contains('Sign in').should('not.be.visible')
    cy.contains('Sign out').should('be.visible')
    cy.contains('Register').should('not.be.visible')

    cy.url().should('equal', Cypress.config().baseUrl)
  })  
})
  