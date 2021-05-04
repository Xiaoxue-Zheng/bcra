import { AnswerHelperService } from '@/services/answer-helper.service'
import { QuestionnaireTestHelper } from '../mocks/questionnaire-test-helper'

describe('AnswerHelperService', () => {
    let service = null
    let questionnaire = null
    let answerResponse = null

    function countKeys(jsonObj) {
        var count = 0;
        for(var key in jsonObj)
            if(jsonObj.hasOwnProperty(key))
                count++;
        return count
     }

    beforeEach(() => {
        service = AnswerHelperService
        questionnaire = QuestionnaireTestHelper.generateQuestionnaire()
        answerResponse = QuestionnaireTestHelper.generateAnswerResponse()
    })

    describe('initialise', () => {
        it('should create various questionnaire json objects', () => {
            service.initialise(questionnaire, answerResponse)
            expect(countKeys(service.questions)).toBe(10)
            expect(countKeys(service.questionItems)).toBe(10)
            expect(countKeys(service.answers)).toBe(20)
            expect(countKeys(service.answerItems)).toBe(20)
            expect(countKeys(service.sectionAnswers)).toBe(10)
        })
    })

    describe('getAnswer', () => {
        it('should return an answer if the provided identifier matches a stored answer', () => {
            let answer = service.getAnswer("0ID")
            expect(answer).toBeDefined()
        })

        it('should return null if the provided identifier does not match a stored answer', () => {
            let answer = service.getAnswer("NON_EXISTENT_ID")
            expect(answer).not.toBeDefined()
        })
    })

    describe('getAnswerItem', () => {
        it('should return an answerItem if the provided identifier matches a stored answerItem', () => {
            let answerItem = service.getAnswerItem("0ID")
            expect(answerItem).toBeDefined()
        })

        it('should return null if the provided identifier does not match a stored answerItem', () => {
            let answerItem = service.getAnswerItem("NON_EXISTENT_ID")
            expect(answerItem).not.toBeDefined()
        })
    })

    describe('getSectionAnswers', () => {
        it('should return a sectionAnswer if the provided identifier matches a stored sectionAnswer', () => {
            let sectionAnswer = service.getSectionAnswers(0)
            expect(sectionAnswer).toBeDefined()
        })

        it('should return null if the provided identifier does not match a stored sectionAnswer', () => {
            let sectionAnswer = service.getAnswer("NON_EXISTENT_ID")
            expect(sectionAnswer).not.toBeDefined()
        })
    })
})
