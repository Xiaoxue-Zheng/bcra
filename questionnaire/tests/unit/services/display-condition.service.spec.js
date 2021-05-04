import { DisplayConditionService } from '@/services/display-condition.service'
import { AnswerHelperService } from '@/services/answer-helper.service'

describe('DisplayConditionService', () => {
    let service = null

    beforeEach(() => {
        service = DisplayConditionService
    })

    describe('isDisplayed', () => {
        it('should return true if a section or question contains no display conditions', () => {
            let question = { displayConditions: [] }
            let result = service.isDisplayed(question)
            expect(result).toBe(true)
        })

        it('should return false if a section or question contains display conditions which are not met', () => {
            let question = { displayConditions: [ { } ] }

            let displayConditionMet = service.displayConditionMet
            service.displayConditionMet = function(condition) { return false }
            let result = service.isDisplayed(question)
            service.displayConditionMet = displayConditionMet

            expect(result).toBe(false)
        })

        it('should return true if a section or question contains display conditions which are met', () => {
            let question = { displayConditions: [ { } ] }
            
            let displayConditionMet = service.displayConditionMet
            service.displayConditionMet = function(condition) { return true }
            let result = service.isDisplayed(question)
            service.displayConditionMet = displayConditionMet

            expect(result).toBe(true)
        })
    })

    describe('displayConditionMet', () => {
        it('should return true if the condition has neither a question nor an item identifier', () => {
            let condition = {}
            let result = service.displayConditionMet(condition)
            expect(result).toBe(true)
        })

        it('should return true if the condition references a questionItem which is selected', () => {
            let condition = { itemIdentifier: "DEFINED" }
            
            let answerItemIsSelected = service.answerItemIsSelected
            service.answerItemIsSelected = function(itentifier) { return true }
            let result = service.displayConditionMet(condition)
            service.answerItemIsSelected = answerItemIsSelected

            expect(result).toBe(true)
        })

        it('should return false if the condition references a questionItem which is not selected', () => {
            let condition = { itemIdentifier: "DEFINED" }
            
            let answerItemIsSelected = service.answerItemIsSelected
            service.answerItemIsSelected = function(itentifier) { return false }
            let result = service.displayConditionMet(condition)
            service.answerItemIsSelected = answerItemIsSelected

            expect(result).toBe(false)
        })

        it('should return true if the condition references a question answer which has a value which is not null and not 0', () => {
            let condition = { questionIdentifier: "DEFINED" }

            let answerIsNotNullOrZero = service.answerIsNotNullOrZero
            service.answerIsNotNullOrZero = function(itentifier) { return true }
            let result = service.displayConditionMet(condition)
            service.answerIsNotNullOrZero = answerIsNotNullOrZero

            expect(result).toBe(true)
        })

        it('should return false if the condition references a question answer which has a value which is null or 0', () => {
            let condition = { questionIdentifier: "DEFINED" }
            
            let answerIsNotNullOrZero = service.answerIsNotNullOrZero
            service.answerIsNotNullOrZero = function(itentifier) { return false }
            let result = service.displayConditionMet(condition)
            service.answerIsNotNullOrZero = answerIsNotNullOrZero

            expect(result).toBe(false)
        })
    })

    describe('answerIsNotNullOrZero', () => {
        it('should return false if question answer does not exist', () => {
            AnswerHelperService.getAnswer = function(questionId) {
                return null
            }

            var result = service.answerIsNotNullOrZero("NON_EXISTENT")
            expect(result).toBe(false)
        })

        it('should return false if question answer has a null value', () => {
            AnswerHelperService.getAnswer = function(questionId) {
                return { number: null }
            }

            var result = service.answerIsNotNullOrZero("ANSWER_WITH_NULL_DATA")
            expect(result).toBe(false)
        })

        it('should return false if question answer value is 0', () => {
            AnswerHelperService.getAnswer = function(questionId) {
                return { number: 0 }
            }

            var result = service.answerIsNotNullOrZero("ANSWER_WITH_VALUE_OF_0")
            expect(result).toBe(false)
        })

        it('should return true if the question answer value is greater than 0', () => {
            AnswerHelperService.getAnswer = function(questionId) {
                return { number: 10 }
            }

            var result = service.answerIsNotNullOrZero("ANSWER_WITH_VALUE_OF_10")
            expect(result).toBe(true)
        })
    })
})
