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
      cy.resetQuestionnaire(NHS_NUMBER)
    })

    beforeEach(function(){
        Cypress.Cookies.preserveOnce('JSESSIONID')
    })

    it('opens the Your History questionnaire after the Consent Form is submitted', () => {
      cy.server()
      cy.visit('/signin')
      cy.get('input').first().clear().type(EMAIL_ADDRESS)
      cy.get('input').last().clear().type(PASSWORD)
      cy.get('button').click()
      
      cy.url().should('equal', Cypress.config().baseUrl + 'consent')
      for (let inputs of CONSENT_QUESTION_INPUTS){
        cy.get(inputs).check({force: true})
      }
      cy.get('[type="submit"]').click()

      cy.visit('/questionnaire/history')

      cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/history')
      cy.contains('Your History').should('be.visible')

      cy.get('.progress').contains('1').should('have.class', 'complete')
      cy.get('.progress').contains('1').should('not.have.class', 'current')
      cy.get('.progress').contains('2').should('have.class', 'complete')
      cy.get('.progress').contains('2').should('not.have.class', 'current')
      cy.get('.progress').contains('3').should('have.class', 'complete')
      cy.get('.progress').contains('3').should('not.have.class', 'current')
      cy.get('.progress').contains('4').should('have.class', 'complete')
      cy.get('.progress').contains('4').should('not.have.class', 'current')
      cy.get('.progress').contains('5').should('not.have.class', 'complete')
      cy.get('.progress').contains('5').should('have.class', 'current')

      // Check number of questions

      cy.contains('Save and continue').should('be.visible')
    })

    it('submits all null answers if there is no change', () => {
      cy.visit('/questionnaire/history')

      cy.get('[type="submit"]').click()

      cy.getQuestionnaireAnswers(NHS_NUMBER).then(response => {
        for (const answer of response.rows) {
          expect(answer.number).to.be.null
        }
      })

      cy.getQuestionnaireAnswerItems(NHS_NUMBER).then(response => {
        for (const answerItem of response.rows) {
          expect(answerItem.selected).to.equal(false)
        }
      })
    })

    const SELF_FIRST_PERIOD_NUMBER = 12;
    const SELF_PREMENOPAUSAL_ITEM = ['SELF_PREMENOPAUSAL_NO']
    const SELF_MENOPAUSAL_AGE_NUMBER = 51;
    const SELF_PREGNANCIES_NUMBER = 3;
    const SELF_PREGNANCY_FIRST_AGE_NUMBER = 25;
    const SELF_HEIGHT_CENTIMETERS = 165;
    const SELF_WEIGHT_KILOS = 63;
    const SELF_BREAST_BIOPSY_ITEM = ['SELF_BREAST_BIOPSY_NO']
    const SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_ITEM = ['SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_YES']
    const SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ITEMS = [
      'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ADH','SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ALH'
    ]
    const SELF_ASHKENAZI_ITEM = ['SELF_ASHKENAZI_UNKNOWN']
    
    it('submits correct answer values (metric)', () => {
      cy.visit('/questionnaire/history')

      cy.setNumberDontKnowAnswer('SELF_FIRST_PERIOD', SELF_FIRST_PERIOD_NUMBER)
      cy.setRadioAnswerItem(SELF_PREMENOPAUSAL_ITEM)
      cy.setNumberAnswer('SELF_MENOPAUSAL_AGE', SELF_MENOPAUSAL_AGE_NUMBER)
      cy.setNumberDropdownAnswer('SELF_PREGNANCIES', SELF_PREGNANCIES_NUMBER)
      cy.setNumberAnswer('SELF_PREGNANCY_FIRST_AGE', SELF_PREGNANCY_FIRST_AGE_NUMBER)
      cy.setNumberHeightWeight('SELF_HEIGHT','CENTIMETERS', SELF_HEIGHT_CENTIMETERS)
      cy.setNumberHeightWeight('SELF_WEIGHT','KILOS', SELF_WEIGHT_KILOS)
      cy.setRadioAnswerItem(SELF_BREAST_BIOPSY_ITEM)
      cy.setRadioAnswerItem(SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_ITEM)
      cy.setCheckboxAnswerItems('SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES',SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ITEMS)
      cy.setRadioAnswerItem(SELF_ASHKENAZI_ITEM)
      
      cy.get('[type="submit"]').click()
      cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/submit')

      cy.getQuestionnaireAnswers(NHS_NUMBER).then(response => {
        const answers = response.rows
        cy.checkNumberAnswerValue(answers, 'SELF_FIRST_PERIOD', SELF_FIRST_PERIOD_NUMBER)
        cy.checkNumberAnswerValue(answers, 'SELF_MENOPAUSAL_AGE', SELF_MENOPAUSAL_AGE_NUMBER)
        cy.checkNumberAnswerValue(answers, 'SELF_PREGNANCIES', SELF_PREGNANCIES_NUMBER)
        cy.checkNumberAnswerValue(answers, 'SELF_PREGNANCY_FIRST_AGE', SELF_PREGNANCY_FIRST_AGE_NUMBER)
        cy.checkNumberAnswerValue(answers, 'SELF_HEIGHT', SELF_HEIGHT_CENTIMETERS, 'CENTIMETERS')
        cy.checkNumberAnswerValue(answers, 'SELF_WEIGHT', SELF_WEIGHT_KILOS, 'KILOS')
      })

      cy.getQuestionnaireAnswerItems(NHS_NUMBER).then(response => {
        const answerItems = response.rows
        cy.checkAnswerItemValues(answerItems, 'SELF_PREMENOPAUSAL', SELF_PREMENOPAUSAL_ITEM)
        cy.checkAnswerItemValues(answerItems, 'SELF_BREAST_BIOPSY', SELF_BREAST_BIOPSY_ITEM)
        cy.checkAnswerItemValues(answerItems, 'SELF_BREAST_BIOPSY_DIAGNOSIS_RISK', SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_ITEM)
        cy.checkAnswerItemValues(answerItems, 'SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES', SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ITEMS)
        cy.checkAnswerItemValues(answerItems, 'SELF_ASHKENAZI', SELF_ASHKENAZI_ITEM)
      })
      
    })

    it('displays saved answers', () => {
      cy.visit('/questionnaire/history')
      cy.checkNumberDontKnowAnswer('SELF_FIRST_PERIOD', SELF_FIRST_PERIOD_NUMBER)
      cy.checkRadioAnswerItem(SELF_PREMENOPAUSAL_ITEM)
      cy.checkNumberAnswer('SELF_MENOPAUSAL_AGE', SELF_MENOPAUSAL_AGE_NUMBER)
      cy.checkNumberDropdownAnswer('SELF_PREGNANCIES', SELF_PREGNANCIES_NUMBER)
      cy.checkNumberAnswer('SELF_PREGNANCY_FIRST_AGE', SELF_PREGNANCY_FIRST_AGE_NUMBER)
      cy.checkNumberHeightWeight('SELF_HEIGHT','CENTIMETERS', SELF_HEIGHT_CENTIMETERS)
      cy.checkNumberHeightWeight('SELF_WEIGHT','KILOS', SELF_WEIGHT_KILOS)
      cy.checkRadioAnswerItem(SELF_BREAST_BIOPSY_ITEM)
      cy.checkRadioAnswerItem(SELF_BREAST_BIOPSY_DIAGNOSIS_RISK_ITEM)
      cy.checkCheckboxAnswerItems('SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES',SELF_BREAST_BIOPSY_DIAGNOSIS_TYPES_ITEMS)
      cy.checkRadioAnswerItem(SELF_ASHKENAZI_ITEM)
    })

    const SELF_HEIGHT_FEET = 5;
    const SELF_HEIGHT_INCHES = 10;
    const SELF_HEIGHT_INCHES_TOTAL = (SELF_HEIGHT_FEET * 12) + SELF_HEIGHT_INCHES

    const SELF_WEIGHT_STONES = 9;
    const SELF_HEIGHT_POUNDS = 13;
    const SELF_HEIGHT_POUNDS_TOTAL = (SELF_WEIGHT_STONES * 14) + SELF_HEIGHT_POUNDS


    it('submits correct answer values in imperial units', () => {
      cy.visit('/questionnaire/history')
      cy.setNumberHeightWeight('SELF_HEIGHT','INCHES', SELF_HEIGHT_FEET, SELF_HEIGHT_INCHES)
      cy.setNumberHeightWeight('SELF_WEIGHT','POUNDS', SELF_WEIGHT_STONES, SELF_HEIGHT_POUNDS)

      cy.get('[type="submit"]').click()
      cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/submit')

      cy.getQuestionnaireAnswers(NHS_NUMBER).then(response => {
        const answers = response.rows
        cy.checkNumberAnswerValue(answers, 'SELF_HEIGHT', SELF_HEIGHT_INCHES_TOTAL, 'INCHES')
        cy.checkNumberAnswerValue(answers, 'SELF_WEIGHT', SELF_HEIGHT_POUNDS_TOTAL, 'POUNDS')
      })
    })
    
    it('displays saved imperial values', () => {
      cy.visit('/questionnaire/history')
      cy.checkNumberHeightWeight('SELF_HEIGHT','INCHES', SELF_HEIGHT_FEET, SELF_HEIGHT_INCHES)
      cy.checkNumberHeightWeight('SELF_WEIGHT','POUNDS', SELF_WEIGHT_STONES, SELF_HEIGHT_POUNDS)
    })

    it('submits all null answers if don\'t know is selected', () => {
      cy.visit('/questionnaire/history')

      cy.setNumberDontKnowAnswer('SELF_FIRST_PERIOD', 'dontknow')
      cy.setNumberAnswer('SELF_MENOPAUSAL_AGE', '')
      cy.setNumberDropdownAnswer('SELF_PREGNANCIES', 'Don\'t know')
      cy.setNumberAnswer('SELF_PREGNANCY_FIRST_AGE', '')
      cy.setNumberHeightWeight('SELF_HEIGHT','CENTIMETERS', '')
      cy.setNumberHeightWeight('SELF_WEIGHT','KILOS', '')
      
      cy.get('[type="submit"]').click()
      cy.url().should('equal', Cypress.config().baseUrl + 'questionnaire/submit')

      cy.getQuestionnaireAnswers(NHS_NUMBER).then(response => {
        const answers = response.rows
        cy.checkNumberAnswerValue(answers, 'SELF_FIRST_PERIOD', null)
        cy.checkNumberAnswerValue(answers, 'SELF_MENOPAUSAL_AGE', null)
        cy.checkNumberAnswerValue(answers, 'SELF_PREGNANCIES', null)
        cy.checkNumberAnswerValue(answers, 'SELF_PREGNANCY_FIRST_AGE', null)
        cy.checkNumberAnswerValue(answers, 'SELF_HEIGHT', null, 'CENTIMETERS')
        cy.checkNumberAnswerValue(answers, 'SELF_WEIGHT', null, 'KILOS')
      })
    })
  })
  