describe('Consent', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const INFO_SHEET_RADIO_GROUP = "CONSENT_INFO_SHEET"
    const FUTURE_RESEARCH_RADIO_GROUP = "CONSENT_FUTURE_RESEARCH"

    function clickConsentButton() {
        cy.get('.pure-button').first().click()
    }

    function clickConsentOption(groupId, option) {
        if (option.toLowerCase() == 'yes')
            cy.get('#' + groupId + ' > .items > :nth-child(1) > label').click()
        else
            cy.get('#' + groupId + ' > .items > :nth-child(2) > label').click()
    }

    function clickConsentCheckbox(checkboxId) {
        cy.get(':nth-child(' + checkboxId + ') > fieldset > .pure-u-1 > label').click()
    }

    function clickAllConsentCheckboxes() {
        for (let i = 2; i < 10; i++) {
            clickConsentCheckbox(i)
        }
    }

    before(function () {
        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
    })

    after(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
        cy.clearTables(['study_id', 'answer_item', 'answer', 'answer_group', 'answer_section', 'answer_response'])
    })
    
    it('should open the consent page', () => {
        cy.url().should('include', 'questionnaire/consent')
        cy.contains('h1', 'Consent')
    })

    it('should not allow the user to continue when no questions have been answered', () => {
        clickConsentButton()
        cy.contains('Please complete all of the questions above to continue.')
    })

    it('should not allow the user to continue when some questions have been answered', () => {
        clickAllConsentCheckboxes()
        clickConsentButton()
        cy.contains('Please complete all of the questions above to continue.')
    })

    it('should not allow the user to continue when all questions have been answered but consent has not been given', () => {
        clickConsentOption(INFO_SHEET_RADIO_GROUP, 'no')
        clickAllConsentCheckboxes()
        clickConsentOption(FUTURE_RESEARCH_RADIO_GROUP, 'no')

        clickConsentButton()
        cy.contains('Please complete all of the questions above to continue.')
    })

    it('should navigate the user to the register page when all questions have been answered and consent has been given', () => {
        clickConsentOption(INFO_SHEET_RADIO_GROUP, 'yes')
        clickAllConsentCheckboxes()
        clickConsentOption(FUTURE_RESEARCH_RADIO_GROUP, 'yes')

        clickConsentButton()
        cy.url().should('include', 'account')
    })
})
  