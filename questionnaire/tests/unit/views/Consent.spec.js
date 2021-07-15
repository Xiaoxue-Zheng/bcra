import { shallowMount, createLocalVue } from '@vue/test-utils'
import Consent from '@/views/Consent.vue'
import VueRouter from 'vue-router'
import { SignUpHelperService } from '@/services/sign-up-helper.service'

describe('Consent.vue', () => {
    let consent = null

    const localVue = createLocalVue()
    localVue.use(VueRouter)
    const router = new VueRouter()

    beforeEach(() => {
        consent = shallowMount(Consent, { localVue, router, stubs: ['router-link', 'router-view'] })

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
