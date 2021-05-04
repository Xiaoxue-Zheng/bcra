import { shallowMount } from '@vue/test-utils'
import SignIn from '@/views/SignIn.vue'

describe('SignIn.vue', () => {
  let signin = null

  beforeEach(() => {
    signin = shallowMount(SignIn, {stubs: ['router-link', 'router-view']})
  })

  describe('initialisation', () => {
    it('initialises data with default values', () => {
      expect(signin.vm.displayFailureMessage).toBe(false)
      expect(signin.vm.displayErrorMessage).toBe(false)
    })
  })

  describe('clearErrors and setErrorMessage', () => {
    it('clears any errors', () => {
      signin.vm.displayFailureMessage = true
      signin.vm.displayErrorMessage = true

      expect(typeof signin.vm.clearErrors).toBe('function')
      signin.vm.clearErrors()
      
      expect(signin.vm.displayFailureMessage).toBe(false)
      expect(signin.vm.displayErrorMessage).toBe(false)
    })

    it('displays the failure message when unauthorised', () => {
      signin.vm.clearErrors()

      expect(typeof signin.vm.setErrorMessage).toBe('function')
      signin.vm.setErrorMessage('UNAUTHORIZED')

      expect(signin.vm.displayFailureMessage).toBe(true)
      expect(signin.vm.displayErrorMessage).toBe(false)
    })

    it('displays an error message when there is an error', () => {
      signin.vm.clearErrors()

      expect(typeof signin.vm.setErrorMessage).toBe('function')
      signin.vm.setErrorMessage('ERROR')

      expect(signin.vm.displayFailureMessage).toBe(false)
      expect(signin.vm.displayErrorMessage).toBe(true)
    })
  })
})
