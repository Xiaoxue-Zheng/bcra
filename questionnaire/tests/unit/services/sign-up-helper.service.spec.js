import { SignUpHelperService } from '@/services/sign-up-helper.service'

describe('SignUpHelperService', () => {
    let service = null

    beforeEach(() => {
        service = SignUpHelperService
        service.clearSignUpInfo()
    })

    describe('setStudyCode and getStudyCode', () => {
        it('should store a study code in memory and then return this value', () => {
            let studyCode = "TST_1234"
            service.setStudyCode(studyCode)
            let result = service.getStudyCode()
            expect(result).toBe(studyCode)
        })
    })

    describe('saveConsentResponse and getConsentResponse', () => {
        it('should store a consent response in memory and then return this information ', () => {
            let consentResponse = { key1: "value1", key2: "value2", key3: "value3", key4: { key4a: 'value4a' }}
            service.saveConsentResponse(consentResponse)
            let result = service.getConsentResponse()
            expect(result).toEqual(consentResponse)
        })
    })

    describe('clearSignUpInfo', () => {
        it('should clear any study code and consent response information from memory', () => {
            let studyCode = "TST_1234"
            let consentResponse = { key1: "value1", key2: "value2", key3: "value3", key4: { key4a: 'value4a' }}

            service.setStudyCode(studyCode)
            let storedStudyCode = service.getStudyCode()
            service.saveConsentResponse(consentResponse)
            let storedConsentResponse = service.getConsentResponse()
            expect(storedStudyCode).toBe(studyCode)
            expect(storedConsentResponse).toEqual(consentResponse)

            service.clearSignUpInfo()

            let clearedStudyCode = service.getStudyCode()
            let clearedConsentResponse = service.getConsentResponse()
            expect(clearedStudyCode).toEqual(null)
            expect(clearedConsentResponse).toEqual(null)
        })
    })
})
