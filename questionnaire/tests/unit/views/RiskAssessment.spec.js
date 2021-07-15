import {createLocalVue, shallowMount} from '@vue/test-utils'
import RiskAssessment from '@/views/RiskAssessment.vue'
import {QuestionSectionService} from '@/services/question-section.service'
import {AnswerResponseService} from '@/api/answer-response.service'
import VueRouter from "vue-router";

describe('RiskAssessment.vue', () => {
    let riskAssessment = null

    const localVue = createLocalVue()
    localVue.use(VueRouter)
    const router = new VueRouter()


    let saveRiskAssessmentSuccessResult = { data: 'SAVED', status: 200 }
    let saveRiskAssessmentUnauthorizedResult = { status: 401 }
    let saveRiskAssessmentResult = saveRiskAssessmentSuccessResult

    let saveRiskAssessmentMock = async function () {
      if(saveRiskAssessmentResult.status === 401) {
        router.push('/signin')
      }
      return saveRiskAssessmentResult
    }

    function populateQuestionnaire() {
        const questionnaire = {
            questionSections: [
                { url: '/page1', order: 1, displayConditions: [], referralConditions: [] },
                { url: '/page2', order: 2, displayConditions: [], referralConditions: [] },
                { url: '/page3', order: 3, displayConditions: [], referralConditions: [] },
                { url: '/page4', order: 4, displayConditions: [], referralConditions: [] },
                { url: '/page5', order: 5, displayConditions: [], referralConditions: [] }
            ]
        }

        riskAssessment.vm.questionnaire = questionnaire
        riskAssessment.vm.questionSection = questionnaire.questionSections[0]
    }

    beforeEach(() => {
        riskAssessment = shallowMount(RiskAssessment,{ localVue, router, stubs: ['router-link', 'router-view'] })
        riskAssessment.vm.proceedToNextRoute = () => {} // prevent calls to $router service

        AnswerResponseService.saveRiskAssessment = saveRiskAssessmentMock

        jest.spyOn(riskAssessment.vm, 'configureButtonPropertiesForQuestionSection')
        jest.spyOn(riskAssessment.vm, 'saveQuestionnaire')
        jest.spyOn(riskAssessment.vm, 'proceedToNextRoute')
        jest.spyOn(QuestionSectionService, 'clearUntakenSectionAnswers')
        jest.spyOn(AnswerResponseService, 'saveRiskAssessment')
        jest.spyOn(QuestionSectionService, 'getSectionInfoComponent')
        jest.spyOn(riskAssessment.vm.$router, 'push')
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(riskAssessment.vm.questionnaire).toBe(null)
            expect(riskAssessment.vm.answerResponse).toBe(null)
            expect(riskAssessment.vm.studyCode).toBe(null)
            expect(riskAssessment.vm.questionSection).toBe(null)
            expect(riskAssessment.vm.answerSection).toBe(null)
            expect(riskAssessment.vm.progressStage).toBe(null)
            expect(riskAssessment.vm.saveError).toBe(false)
            expect(riskAssessment.vm.buttonText).toBe(null)
            expect(riskAssessment.vm.buttonAction).toBe(null)
            expect(riskAssessment.vm.infoComponent).toBe(null)
            expect(riskAssessment.vm.questionVariables).toEqual({})
            expect(riskAssessment.vm.referralConditions).toEqual({})
            expect(riskAssessment.vm.readOnly).toBe(null)
        })
    })

    describe('initialiseQuestionSectionProperties', () => {
        it('should call getSectionInfoComponent from the QuestionSectionService', () => {
            populateQuestionnaire()
            riskAssessment.vm.initialiseQuestionSectionProperties('/page1')
            expect(QuestionSectionService.getSectionInfoComponent).toHaveBeenCalled()
        })

        it('should call configureButtonPropertiesForQuestionSection', () => {
            populateQuestionnaire()
            riskAssessment.vm.initialiseQuestionSectionProperties('/page1')
            expect(riskAssessment.vm.configureButtonPropertiesForQuestionSection).toHaveBeenCalled()
        })
    })

    describe('configureButtonPropertiesForQuestionSection', () => {
        it('should set the button text to "Continue review" if the info component is undefined and the page is read only', () => {
            riskAssessment.vm.infoComponent = null
            riskAssessment.vm.readOnly = true
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonText).toBe("Continue review")
        })

        it('should set the button text to "Save and continue" if the info component is undefined and the page is not read only', () => {
            riskAssessment.vm.infoComponent = null
            riskAssessment.vm.readOnly = false
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonText).toBe("Save and continue")
        })

        it('should set the button text to "Continue review" if the info component is defined and the page is read only', () => {
            riskAssessment.vm.infoComponent = "DEFINED"
            riskAssessment.vm.readOnly = true
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonText).toBe("Continue review")
        })

        it('should set the button text to "Continue" if the info component is defined and the page is not read only', () => {
            riskAssessment.vm.infoComponent = "DEFINED"
            riskAssessment.vm.readOnly = false
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonText).toBe("Continue")
        })

        it('should set the button action to "continue()" if the page is read only', () => {
            riskAssessment.vm.readOnly = true
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonAction).toEqual(riskAssessment.vm.continue)
        })

        it('should set the button action to "saveAndContinue()" if the page is not read only', () => {
            riskAssessment.vm.readOnly = false
            riskAssessment.vm.configureButtonPropertiesForQuestionSection()
            expect(riskAssessment.vm.buttonAction).toEqual(riskAssessment.vm.saveAndContinue)
        })
    })

    describe('saveAndContinue', () => {
        it('should clear the untaken section answers', () => {
            populateQuestionnaire()
            riskAssessment.vm.saveAndContinue()
            expect(QuestionSectionService.clearUntakenSectionAnswers).toHaveBeenCalled()
        })

        it('should proceed to the saveQuestionnaire function', () => {
            populateQuestionnaire()
            riskAssessment.vm.saveAndContinue()
            expect(riskAssessment.vm.saveQuestionnaire).toHaveBeenCalled()
        })
    })

    describe('continue', () => {
        it('should call the proceedToNextRoute function', () => {
            populateQuestionnaire()
            riskAssessment.vm.continue()
            expect(riskAssessment.vm.proceedToNextRoute).toHaveBeenCalled()
        })
    })

    describe('saveQuestionnaire', () => {
        it('should save the riskAssessment to cache memory', () => {
            populateQuestionnaire()
            riskAssessment.vm.saveQuestionnaire()
            expect(AnswerResponseService.saveRiskAssessment).toHaveBeenCalled()
        })

        it('should call proceed to next route if the risk assessment is saved successfully', async (done) => {
            populateQuestionnaire()
            saveRiskAssessmentResult.data = "SAVED"
            await riskAssessment.vm.saveQuestionnaire()
            expect(riskAssessment.vm.proceedToNextRoute).toHaveBeenCalled()
            done()
        })

        it('should show an error if the risk assessment fails to save', async (done) => {
            populateQuestionnaire()
            saveRiskAssessmentResult.data = "FAILED"
            await riskAssessment.vm.saveQuestionnaire()
            expect(riskAssessment.vm.saveError).toBe(true)
            done()
        })

        it('should redirect to sign in if session time out', async (done) => {
          populateQuestionnaire()
          saveRiskAssessmentResult = saveRiskAssessmentUnauthorizedResult
          await riskAssessment.vm.saveQuestionnaire()
          expect(riskAssessment.vm.$router.push).toHaveBeenCalledWith('/signin')
          done()
        })
    })

})
