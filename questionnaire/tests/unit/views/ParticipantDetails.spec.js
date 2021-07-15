import {createLocalVue, shallowMount} from '@vue/test-utils'
import ParticipantDetails from '@/views/ParticipantDetails.vue'
import { ParticipantDetailsService } from '@/api/participant-details.service'
import VueRouter from "vue-router";

describe('ParticipantDetails.vue', () => {
    let participantDetails = null

    const localVue = createLocalVue()
    localVue.use(VueRouter)
    const router = new VueRouter()

    let updateParticipantDetailsSuccessResponse = { status: 200 }
    let updateParticipantDetailsFailureResponse = { status: 0 }
    let updateParticipantDetailsSessionTimeoutResponse = { status: 401 }
    let updateParticipantDetailsResponse = updateParticipantDetailsFailureResponse
    let updateParticipantDetailsMock = async function () {
        if (updateParticipantDetailsResponse.status === 401) {
          router.push('/signin')
        }
        return updateParticipantDetailsResponse
    }

    function setParticipantDetails() {
        participantDetails.vm.forename = "FORENAME"
        participantDetails.vm.surname = "SURNAME"
        participantDetails.vm.addressLine1 = "ADDRESS1"
        participantDetails.vm.addressLine2 = "ADDRESS2"
        participantDetails.vm.addressLine3 = "ADDRESS3"
        participantDetails.vm.addressLine4 = "ADDRESS4"
        participantDetails.vm.addressLine5 = "ADDRESS5"
        participantDetails.vm.postCode = "POSTCODE"
        participantDetails.vm.dateOfBirth = "DATEOFBIRTH"
        participantDetails.vm.nhsNumber = "NHSNUMBER"
        participantDetails.vm.practiceName = "PRACTICENAME"
    }

    beforeEach(() => {
        participantDetails = shallowMount(ParticipantDetails, { localVue, router, stubs: ['router-link', 'router-view'] })

        ParticipantDetailsService.updateParticipantDetails = updateParticipantDetailsMock
        jest.spyOn(participantDetails.vm, 'clearMessages')
        jest.spyOn(participantDetails.vm.$router, 'push')
        jest.spyOn(ParticipantDetailsService, 'updateParticipantDetails')
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(participantDetails.vm.forename).toBe(null)
            expect(participantDetails.vm.surname).toBe(null)
            expect(participantDetails.vm.addressLine1).toBe(null)
            expect(participantDetails.vm.addressLine2).toBe(null)
            expect(participantDetails.vm.addressLine3).toBe(null)
            expect(participantDetails.vm.addressLine4).toBe(null)
            expect(participantDetails.vm.addressLine5).toBe(null)
            expect(participantDetails.vm.postCode).toBe(null)
            expect(participantDetails.vm.dateOfBirth).toBe(null)
            expect(participantDetails.vm.nhsNumber).toBe(null)
            expect(participantDetails.vm.practiceName).toBe(null)
            expect(participantDetails.vm.failure).toBe(false)
        })
    })

    describe('register', () => {
        it('should clear the error messages', async (done) => {
            await participantDetails.vm.register()
            expect(participantDetails.vm.clearMessages).toHaveBeenCalled()
            done()
        })

        it('should send the participant details to the api', async (done) => {
            setParticipantDetails()
            await participantDetails.vm.register()
            expect(ParticipantDetailsService.updateParticipantDetails).toHaveBeenCalled()
            done()
        })

        it('should show an error message if the participant details fail to send', async (done) => {
            setParticipantDetails()
            updateParticipantDetailsResponse = updateParticipantDetailsFailureResponse
            await participantDetails.vm.register()
            expect(participantDetails.vm.failure).toBe(true)
            done()
        })

        it('should not show an error message if the participant details send successfully', async (done) => {
            setParticipantDetails()
            updateParticipantDetailsResponse = updateParticipantDetailsSuccessResponse
            await participantDetails.vm.register()
            expect(participantDetails.vm.failure).toBe(false)
            done()
        })

        it('should navigate to the risk assessment questionnaire when details have been successfully sent', async (done) => {
            setParticipantDetails()
            updateParticipantDetailsResponse = updateParticipantDetailsSuccessResponse
            await participantDetails.vm.register()
            expect(participantDetails.vm.$router.push).toHaveBeenCalledWith('/questionnaire/familyhistorycontext')
            done()
        })

        it('should navigate to the sign in when session has been timeout', async (done) => {
          setParticipantDetails()
          updateParticipantDetailsResponse = updateParticipantDetailsSessionTimeoutResponse
          await participantDetails.vm.register()
          expect(participantDetails.vm.$router.push).toHaveBeenCalledWith('/signin')
          done()
        })
    })

    describe('clearMessages', () => {
        it('should reset the "show errors" flag', () => {
            participantDetails.vm.failure = true
            participantDetails.vm.clearMessages()

            expect(participantDetails.vm.failure).toBe(false)
        })

        it('should reset the "show errors" flag regardless of its value', () => {
            participantDetails.vm.failure = "ANY_VALUE"
            participantDetails.vm.clearMessages()

            expect(participantDetails.vm.failure).toBe(false)
        })
    })
})
