describe('PasswordResetFinish', () => {
  const getStore = () => cy.window().its('app.$store')
  const NOT_EXIST_RESET_KEY = "12345"
  const EXIST_RESET_KEY = "11111111111111111111"

  const TO_REGISTER_STUDY_CODE = "CYPRESS_TST_2"
  const TO_REGISTER_EMAIL_ADDRESS = "test2@test.com"
  const STRONG_PASSWORD = "hard2Crack!!"
  before(function () {

  })

  after(function() {

  })

  beforeEach(function() {

  })

  it('should show error message if the confirmation password did not match',() => {
    cy.server()
    cy.visit('/reset/finish?key='+EXIST_RESET_KEY)
    cy.get('input').first().type('12345')
    cy.get('input').eq(1).type('123456')
    cy.get('.pure-button').contains('Reset').click()
    cy.contains('p','The password and its confirmation do not match!')
  })

  it('should show error message if the key did not exist',() => {
    cy.server()
    cy.visit('/reset/finish?key='+NOT_EXIST_RESET_KEY)
    cy.get('input').first().type('12345')
    cy.get('input').eq(1).type('12345')
    cy.get('.pure-button').contains('Reset').click()
    cy.contains('p','No user was found for this reset key')
  })

  it('should success if the key exist',() => {
    cy.createStudyCodeAndRegister(TO_REGISTER_STUDY_CODE, TO_REGISTER_EMAIL_ADDRESS, STRONG_PASSWORD)
    cy.updateAccountResetKey(TO_REGISTER_EMAIL_ADDRESS, EXIST_RESET_KEY)
    cy.server()
    cy.visit('/reset/finish?key='+EXIST_RESET_KEY)
    cy.get('input').first().type('12345')
    cy.get('input').eq(1).type('12345')
    cy.get('.pure-button').contains('Reset').click()
    cy.contains('p','Your password has been reset. Please')
    cy.contains('a','Sign in')
    cy.removeParticipant(TO_REGISTER_STUDY_CODE)
  })
})
