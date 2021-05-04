import { shallowMount } from '@vue/test-utils'
import Accordion from '@/components/Accordion.vue'

describe('Accordion.vue', () => {
    let component = null

    beforeEach(() => {
        component = shallowMount(Accordion, {stubs: ['router-link', 'router-view']})
    })

    describe('initialisation', () => {
        it('initialises data with default values', () => {
            expect(component.vm.show).toBe(false)
        })
    })

    describe('toggleItem', () => {
        it('should change the show property to true', () => {
            component.vm.toggleItem()
            expect(component.vm.show).toBe(true)
        })

        it('should change the show property to true and then back to false', () => {
            component.vm.toggleItem()
            component.vm.toggleItem()
            expect(component.vm.show).toBe(false)
        })
    })
})
