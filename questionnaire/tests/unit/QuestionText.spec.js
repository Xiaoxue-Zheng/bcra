import { shallowMount } from '@vue/test-utils'
import QuestionText from '@/components/QuestionText.vue'

describe('QuestionText.vue', () => {

const PLAIN_QUESTION_TEXT = 'What food does your relative enjoy eating?'
const VARIABLE_QUESTION_TEXT = 'What food does your {{relative}} enjoy eating?'
const CHANGED_QUESTION_TEXT = 'What food does your grandmother enjoy eating?'
const QUESTION_VARIABLES = {relative: 'grandmother'}

  it('does not update plain question text', () => {
    const questionText = shallowMount(QuestionText, {
        propsData: {
            question: {text: PLAIN_QUESTION_TEXT},
            questionVariables: QUESTION_VARIABLES
        }
    })    
    expect(questionText.vm.questionText).toEqual(PLAIN_QUESTION_TEXT)
  })

  it('does updates variable question text', () => {
    const questionText = shallowMount(QuestionText, {
        propsData: {
            question: {text: VARIABLE_QUESTION_TEXT},
            questionVariables: QUESTION_VARIABLES
        }
    })    
    expect(questionText.vm.questionText).toEqual(CHANGED_QUESTION_TEXT)
  })
})
