import { shallowMount } from '@vue/test-utils'
import Modal from '@/components/Modal.vue'

describe('Accordion.vue', () => {
  let component = null

  beforeEach(() => {
    component = shallowMount(Modal, {stubs: ['router-link', 'router-view']})
  })

  describe('initialisation', () => {
    it('initialises data with default values', () => {
      expect(component.vm.show).toBe(false)
    })
  })

  describe('cancel', () => {
    it('should change the show property to false', () => {
      component.vm.show = true
      component.vm.hideModal()
      expect(component.vm.show).toBe(false)
    })
  })

  describe('submit', () => {
    it('should change the show property to false', () => {
      component.vm.show = true
      component.vm.submit()
      expect(component.vm.show).toBe(false)
    })
  })
})
