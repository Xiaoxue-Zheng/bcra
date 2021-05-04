import { QuestionSectionService } from '@/services/question-section.service'

describe('QuestionSectionService', () => {
    let service = null

    beforeEach(() => {
        service = QuestionSectionService
    })

    describe('getSectionInfoComponent', () => {
        it('should return "QuestionSectionFamilyHistoryInfo" if the sectionIdentifier is "FAMILY_AFFECTED_CONTEXT"', () => {
            let result = service.getSectionInfoComponent('FAMILY_AFFECTED_CONTEXT')
            expect(result).toBe('QuestionSectionFamilyHistoryInfo')
        })

        it('should return "QuestionSectionYourHistoryInfo" if the sectionIdentifier is "PERSONAL_HISTORY_CONTEXT"', () => {
            let result = service.getSectionInfoComponent('PERSONAL_HISTORY_CONTEXT')
            expect(result).toBe('QuestionSectionYourHistoryInfo')
        })

        it('should return null if the sectionIdentifier is neither "FAMILY_AFFECTED_CONTEXT" or "PERSONAL_HISTORY_CONTEXT"', () => {
            let result = service.getSectionInfoComponent('INVALID_CONTEXT')
            expect(result).toBe(null)
        }) 
    })
})
