import { shallowMount } from '@vue/test-utils'
import SignIn from '@/views/SignIn.vue'

describe('SignIn.vue', () => {

  it('initialises with no errors showing', () => {
    expect(SignIn.data().displayFailureMessage).toBe(false)
    expect(SignIn.data().displayErrorMessage).toBe(false)
  })

   it('clears any errors', () => {
      const signIn = shallowMount(SignIn, {})
      signIn.vm.displayFailureMessage = true
      signIn.vm.displayErrorMessage = true

      expect(typeof signIn.vm.clearErrors).toBe('function')
      signIn.vm.clearErrors()
      
      expect(signIn.vm.displayFailureMessage).toBe(false)
      expect(signIn.vm.displayErrorMessage).toBe(false)
    })

    it('displays the failure message when unauthorised', () => {
      const signIn = shallowMount(SignIn, {})
      signIn.vm.clearErrors()

      expect(typeof signIn.vm.setMessage).toBe('function')
      signIn.vm.setMessage('UNAUTHORIZED')

      expect(signIn.vm.displayFailureMessage).toBe(true)
      expect(signIn.vm.displayErrorMessage).toBe(false)
    })

    it('displays an error message when there is an error', () => {
      const signIn = shallowMount(SignIn, {})
      signIn.vm.clearErrors()

      expect(typeof signIn.vm.setMessage).toBe('function')
      signIn.vm.setMessage('ERROR')

      expect(signIn.vm.displayFailureMessage).toBe(false)
      expect(signIn.vm.displayErrorMessage).toBe(true)
    })
})
