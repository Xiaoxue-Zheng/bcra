import { shallowMount, createLocalVue } from '@vue/test-utils'
import Register from '@/views/Register.vue'
import { SignUpHelperService } from '@/services/sign-up-helper.service'
import { StudyService } from '@/api/study.service'
import VueRouter from "vue-router";

describe('Register.vue', () => {
    let register = null

    const localVue = createLocalVue()
    localVue.use(VueRouter)
    const router = new VueRouter()

    let studyCodeAvailable = false
    let isStudyCodeAvailableMock = async (studyCode) => {
        return studyCodeAvailable
    }

    beforeEach(() => {
        register = shallowMount(Register, { localVue, router, stubs: ['router-link', 'router-view'] })
        StudyService.isStudyCodeAvailable = isStudyCodeAvailableMock

        jest.spyOn(register.vm.$router, 'push')
        jest.spyOn(register.vm, 'clearMessages')
        jest.spyOn(StudyService, 'isStudyCodeAvailable')
        jest.spyOn(SignUpHelperService, 'setStudyCode')
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(register.vm.studyCode).toBe(null)
            expect(register.vm.failure).toBe(false)
        })
    })

    describe('register', () => {
        it('should clear all error flags', async (done) => {
            await register.vm.register()
            expect(register.vm.clearMessages).toHaveBeenCalled()
            done()
        })

        it('should check if the entered study code is available', async (done) => {
            register.vm.studyCode = "STU_1234"
            await register.vm.register()
            expect(StudyService.isStudyCodeAvailable).toHaveBeenCalled()
            done()
        })

        it('should display an error message if the study code is not available', async(done) => {
            register.vm.studyCode = "STU_1234"
            studyCodeAvailable = false
            await register.vm.register()
            expect(register.vm.failure).toBe(true)
            done()
        })

        it('should store the study code if the entered code is available', async(done) => {
            register.vm.studyCode = "STU_1234"
            studyCodeAvailable = true
            await register.vm.register()
            expect(SignUpHelperService.setStudyCode).toHaveBeenCalled()
            done()
        })

        it('should navigate to the consent page if the entered code is available', async(done) => {
            register.vm.studyCode = "STU_1234"
            studyCodeAvailable = true
            await register.vm.register()
            expect(register.vm.$router.push).toHaveBeenCalledWith('/consent')
            done()
        })
    })

    describe('clearMessages', () => {
        it('it should set error flags to false', () => {
            register.vm.clearMessages()

            expect(register.vm.notFound).toBe(false)
            expect(register.vm.alreadyRegistered).toBe(false)
            expect(register.vm.failure).toBe(false)
        })

        it('it should set error flags to false regardless of their content', () => {
            register.vm.notFound = "ANY_DATA"
            register.vm.alreadyRegistered = "ANY_DATA"
            register.vm.failure = "ANY_DATA"

            register.vm.clearMessages()

            expect(register.vm.notFound).toBe(false)
            expect(register.vm.alreadyRegistered).toBe(false)
            expect(register.vm.failure).toBe(false)
        })
    })
})
