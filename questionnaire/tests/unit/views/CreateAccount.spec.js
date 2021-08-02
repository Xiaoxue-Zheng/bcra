import { shallowMount, createLocalVue } from '@vue/test-utils'
import CreateAccount from '@/views/CreateAccount.vue'
import { SignUpHelperService } from '@/services/sign-up-helper.service'
import { SecurityService } from '@/api/security.service'
import VueRouter from "vue-router";

describe('CreateAccount.vue', () => {
    let createAccount = null

    let dispatchResult = 'FAILURE'
    let storeMock = {
        dispatch: async () => {
            return dispatchResult
        }
    }

    const localVue = createLocalVue()
    localVue.use(VueRouter)
    const router = new VueRouter()

    beforeEach(() => {
        createAccount = shallowMount(CreateAccount, { localVue, router, stubs: ['router-link', 'router-view'] })
        createAccount.vm.$store = storeMock

        SecurityService.createAccount = async function() { return 0 }

        jest.spyOn(SignUpHelperService, 'getStudyCode')
        jest.spyOn(SignUpHelperService, 'getConsentResponse')
        jest.spyOn(SignUpHelperService, 'clearSignUpInfo')
        jest.spyOn(SecurityService, 'createAccount')
        jest.spyOn(createAccount.vm.$router, 'push')
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(createAccount.vm.emailAddress).toBe('')
            expect(createAccount.vm.password).toBe('')
            expect(createAccount.vm.repeatPassword).toBe('')
            expect(createAccount.vm.passwordScore).toBe(0)
            expect(createAccount.vm.displayErrorMessage).toBe(false)
            expect(createAccount.vm.MINIMUM_PASSWORD_SCORE).toBe(4)
        })
    })

    describe('createAccount', () => {
        it('should not do anything if the form is invalid', () => {
            createAccount.vm.passwordScore = 0 // invalidates form
            createAccount.vm.createAccount()

            expect(SecurityService.createAccount).not.toHaveBeenCalled()
        })

        it('should create an account using the provided information', async (done) => {
            createAccount.vm.emailAddress = "test@email.com"
            createAccount.vm.password = "PASSWORD"
            createAccount.vm.repeatPassword = "PASSWORD"
            createAccount.vm.passwordScore = 5
            await createAccount.vm.createAccount()
            createAccount.vm.autoLogin = () => { "disable this function" }

            const signUpInformation = {
                'emailAddress': createAccount.vm.emailAddress,
                'password': createAccount.vm.password,
                'studyCode': SignUpHelperService.getStudyCode(),
                'consentResponse': SignUpHelperService.getConsentResponse()
            }

            expect(SecurityService.createAccount).toHaveBeenCalledWith(signUpInformation)
            done()
        })
    })

    describe('setScore', () => {
        it('should update passwordScore', () => {
            expect(createAccount.vm.passwordScore).toBe(0)

            const score = 10
            createAccount.vm.setScore(score)

            expect(createAccount.vm.passwordScore).toBe(score)
        })
    })

    describe('formValid', () => {
        it('should return false if the password and repeat password do not match', () => {
            createAccount.vm.password = "A_PASSWORD"
            createAccount.vm.repeatPassword = "A_DIFFERENT_PASSWORD"

            expect(createAccount.vm.formValid()).toBe(false)
        })

        it('should return false if the password has a score of less than 4', () => {
            createAccount.vm.password = "PSW"
            createAccount.vm.repeatPassword = "PSW"
            createAccount.vm.passwordScore = 2

            expect(createAccount.vm.formValid()).toBe(false)
        })

        it('should return true if the password has a score of 4', () => {
            createAccount.vm.password = "PSWD"
            createAccount.vm.repeatPassword = "PSWD"
            createAccount.vm.passwordScore = 4

            expect(createAccount.vm.formValid()).toBe(true)
        })

        it('should return true if the password has a score of more than 4', () => {
            createAccount.vm.password = "PASSWORD"
            createAccount.vm.repeatPassword = "PASSWORD"
            createAccount.vm.passwordScore = 10

            expect(createAccount.vm.formValid()).toBe(true)
        })
    })

    describe('autoLogin', () => {
        it('should display an error message on failure to login', async (done) => {
            dispatchResult = 'FAILURE'
            await createAccount.vm.autoLogin()
            expect(createAccount.vm.displayErrorMessage).toBe(true)
            done()
        })

        it('should not display an error message on successful login', async (done) => {
            dispatchResult = 'SUCCESS'
            await createAccount.vm.autoLogin()
            expect(createAccount.vm.displayErrorMessage).toBe(false)
            done()
        })

        it('should clear cached sign up information on successful login', async (done) => {
            dispatchResult = 'SUCCESS'
            await createAccount.vm.autoLogin()
            expect(SignUpHelperService.clearSignUpInfo).toHaveBeenCalled()
            done()
        })

        it('should navigate the user to participant-details on successful login', async (done) => {
            dispatchResult = 'SUCCESS'
            await createAccount.vm.autoLogin()
            expect(createAccount.vm.$router.push).toHaveBeenCalledWith('/questionnaire/familyhistorycontext')
            done()
        })
    })
})
