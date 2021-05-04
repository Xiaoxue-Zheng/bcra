import Home from '@/views/Home.vue'

describe('Home.vue', () => {
    describe('initialisation', () => {
        it('initialises data with default values', () => {
            const accordions = Home.data().accordions
            expect(accordions[0].title).toBe('1. Questionnaire')
            expect(accordions[1].title).toBe('2. Spit sample')
            expect(accordions[2].title).toBe('3. Mammogram')
        })
    })
})
