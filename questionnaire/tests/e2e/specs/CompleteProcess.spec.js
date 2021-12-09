describe('CompleteProcess', () => {
    const getStore = () => cy.window().its('app.$store')
    const UNREGISTERED_STUDY_CODE = "CYPRESS_TST_2"
    const UNREGISTERED_EMAIL_ADDRESS = "test2@test.com"
    const STRONG_PASSWORD = "hard2Crack!!"
    const UNUSED_NHS_NUMBER = 1111111111

    beforeEach(function () {
        cy.createStudyIdFromCode(UNREGISTERED_STUDY_CODE)
    })

    afterEach(function() {
        cy.deleteParticipants([UNREGISTERED_STUDY_CODE])
        cy.removeParticipant(UNREGISTERED_STUDY_CODE)
    })

    it('should success when sign in after complete questionnaire', () => {
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()
        cy.completeParticipantDetailsPage()
        cy.signOut()
        cy.signIn(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.checkCanRiskReport(false)
        cy.clickAndCheckCanRiskReport(false)
        cy.signOut()
        cy.signIn(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.url().should('include', 'end')
        cy.contains('h1', 'Thank you for submitting your questionnaire.')
    })

    it('should success when sign in after complete questionnaire and upload can risk report', () => {
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessment()
        cy.completeParticipantDetailsPage()
        cy.signOut()
        cy.createCanRiskReportForParticipant(UNREGISTERED_STUDY_CODE)
        cy.signIn(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.checkCanRiskReport(true)
        cy.signOut()
        cy.signIn(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.url().should('include', 'canriskreport')
    })

    it('should success when sign in after questionnaire referred', () => {
        cy.completeRegisterPage(UNREGISTERED_STUDY_CODE)
        cy.completeConsentPage()
        cy.completeCreateAccountPage(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.completeRiskAssessmentAndReferred()
        cy.signOut()
        cy.signIn(UNREGISTERED_EMAIL_ADDRESS, STRONG_PASSWORD)
        cy.url().should('include', 'referral')
    })
})
