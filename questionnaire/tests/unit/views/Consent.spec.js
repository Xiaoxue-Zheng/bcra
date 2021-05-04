import { shallowMount } from '@vue/test-utils'
import Consent from '@/views/Consent.vue'
import { SignUpHelperService } from '@/services/sign-up-helper.service'

describe('Consent.vue', () => {
    let consent = null

    let routerMock = {
        push: (ref) => {}
    }

    beforeEach(() => {
        consent = shallowMount(Consent, {stubs: ['router-link', 'router-view']})
        consent.vm.$router = routerMock

        jest.spyOn(SignUpHelperService, 'saveConsentResponse')
        jest.spyOn(consent.vm.$router, 'push')
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(Consent.data().questionSection).toBe(null)
            expect(Consent.data().answerResponse).toBe(null)
            expect(Consent.data().answerSection).toBe(null)
            expect(Consent.data().submitError).toBe(false)
        })
    })

    describe('submitConsent', () => {
        it('should save completed answer response to SignUpHelperService', () => {
            consent.vm.submitConsent()
            expect(SignUpHelperService.saveConsentResponse).toHaveBeenCalled()
        })
        
        it('should navigate the user to the /account page', () => {
            consent.vm.submitConsent()
            expect(consent.vm.$router.push).toHaveBeenCalledWith('/account')
        })
    })
})
