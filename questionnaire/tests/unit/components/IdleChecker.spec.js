import IdleChecker from '@/components/IdleChecker.vue'
import { createLocalVue, shallowMount } from '@vue/test-utils'
import VueRouter from 'vue-router'
import { SecurityService } from '@/api/security.service'

import store from '../../../src/store'

describe('IdleChecker.vue', () => {
  let idleChecker = null

  const localVue = createLocalVue()
  localVue.use(VueRouter)

  const router = new VueRouter()

  let userSuccessResult = { status: 200 }
  let userUnAuthorizedResult = { status: 401 }
  let userResult = userSuccessResult

  let getUserMock = async function () {
    if (userResult.status === 401) {
      throw new Error(`BCRA ApiService unauthorized`)
    }
    return userResult
  }

  let logoutMock = async function () {
  }

  beforeEach(() => {
    idleChecker = shallowMount(IdleChecker, { store, localVue, router, stubs: ['router-link', 'router-view'] })
    SecurityService.getUser = getUserMock
    SecurityService.logout = logoutMock
    jest.spyOn(SecurityService, 'getUser')
    jest.spyOn(idleChecker.vm.$store, 'dispatch')
    jest.spyOn(idleChecker.vm.$router, 'push')
  })

  describe('initialisation', () => {
    it('initialises data with default values', () => {
      idleChecker.vm.idleModalShow = false
    })
  })

  describe('Idle', () => {
    it('should pop up modal if user inactive for long time', async (done) => {
      idleChecker.vm.onIdle()
      expect(idleChecker.vm.idleModalShow).toBe(true)
      done()
    })

    it('should continue assessment if user click \'I am still using this page\' on the ContinueAssessmentModal and session have not timeout', async (done) => {
      idleChecker.vm.onIdle()
      expect(idleChecker.vm.idleModalShow).toBe(true)
      idleChecker.vm.continueOperation()
      expect(SecurityService.getUser).toHaveBeenCalled()
      expect(idleChecker.vm.idleModalShow).toBe(false)
      done()
    })

    it('should redirect to sign in if user click \'I am still using this page\' button on the IdleModal ' +
      'and session has timeout', async (done) => {
      idleChecker.vm.onIdle()
      expect(idleChecker.vm.idleModalShow).toBe(true)
      userResult = userUnAuthorizedResult
      await idleChecker.vm.continueOperation()
      await expect(SecurityService.getUser).toHaveBeenCalled()
      expect(idleChecker.vm.idleModalShow).toBe(false)
      expect(idleChecker.vm.$router.push).toHaveBeenCalledWith('/signin')
      done()
    })

    it('should redirect to sign in if user click \'cancel\' button on the IdleModal', async (done) => {
      idleChecker.vm.onIdle()
      expect(idleChecker.vm.idleModalShow).toBe(true)
      await idleChecker.vm.stopOperationAndRedirect()
      expect(idleChecker.vm.idleModalShow).toBe(false)
      expect(idleChecker.vm.$store.dispatch).toHaveBeenCalledWith('security/logout')
      expect(idleChecker.vm.$router.push).toHaveBeenCalledWith('/signin')
      done()
    })
  })
})
