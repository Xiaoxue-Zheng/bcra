<template>
  <div v-if='questionSection && answerSection' class="content">
    <h1>{{ questionSection.title }}<ProgressState :progressStage="progressStage"></ProgressState></h1>
    <slot></slot>
    <form class="pure-form">
      <div v-for="question in questions" v-bind:key='question.id'>
        <component
          :is="getComponentType(question)"
          :question="question"
          :answer="getAnswer(question)"
          :questionVariables="questionVariables"
        ></component>
      </div>
      <PrimaryButton :clickEvent="buttonClick">{{ buttonText }}</PrimaryButton>
      <div v-if="buttonError">There was an error. Please try again or contact the study team.</div>
      <div v-if="invalid">Please complete all of the questions above to continue.</div>
    </form>
  </div>
</template>

<script>
import ProgressState from '@/components/ProgressState.vue'
import PrimaryButton from '@/components/PrimaryButton.vue'
import TickboxQuestion from '@/components/TickboxQuestion.vue'
import CheckboxQuestion from '@/components/CheckboxQuestion.vue'
import RadioQuestion from '@/components/RadioQuestion.vue'
import NumberQuestion from '@/components/NumberQuestion.vue'
import NumberUnknownQuestion from '@/components/NumberUnknownQuestion.vue'
import HeightWeightQuestion from '@/components/HeightWeightQuestion.vue'
import DropdownNumberQuestion from '@/components/DropdownNumberQuestion.vue'
import { DisplayConditionService } from '@/services/display-condition.service.js'
import { QuestionnaireSyncService } from '@/services/questionnaire-sync.service.js'

export default {
  components: {
    'ProgressState': ProgressState,
    'PrimaryButton': PrimaryButton,
    'TickboxQuestion': TickboxQuestion,
    'CheckboxQuestion': CheckboxQuestion,
    'RadioQuestion': RadioQuestion,
    'NumberQuestion': NumberQuestion,
    'NumberUnknownQuestion': NumberUnknownQuestion,
    'HeightWeightQuestion': HeightWeightQuestion,
    'DropdownNumberQuestion': DropdownNumberQuestion
  },
  props: [
    'progressStage',
    'questionSection',
    'answerSection',
    'buttonText',
    'buttonAction',
    'buttonError',
    'questionVariables',
    'questionnaire',
    'answerResponse'
  ],
  data () {
    return {
      invalid: false
    }
  },
  computed: {
    questions: function (state) {
      let questions = state.questionSection.questionGroup.questions

      if (this.questionnaire && this.answerResponse) {
        QuestionnaireSyncService.negateAnswersNotDisplayedInSection(this.questionSection.id, this.answerResponse, this.questionnaire)
      }

      return questions.sort((questionA, questionB) => {
        return questionA.order - questionB.order
      }).filter(function (q) {
        return !DisplayConditionService.noDisplayConditionsMet(q.displayConditions, this.answerResponse, this.questionnaire)
      }, this)
    }
  },
  methods: {
    getComponentType (question) {
      return {
        'TICKBOX_CONSENT': 'TickboxQuestion',
        'CHECKBOX': 'CheckboxQuestion',
        'RADIO': 'RadioQuestion',
        'NUMBER': 'NumberQuestion',
        'NUMBER_UNKNOWN': 'NumberUnknownQuestion',
        'NUMBER_HEIGHT': 'HeightWeightQuestion',
        'NUMBER_WEIGHT': 'HeightWeightQuestion',
        'DROPDOWN_NUMBER': 'DropdownNumberQuestion'
      }[question.type]
    },
    getAnswer (question) {
      let answers = this.$props.answerSection.answerGroups[0].answers
      return answers.find(answer => answer.questionId === question.id)
    },
    buttonClick () {
      this.invalid = false
      if (this.valid()) {
        this.buttonAction()
      } else {
        this.invalid = true
      }
    },
    valid () {
      let questions = this.questionSection.questionGroup.questions
      for (const question of questions) {
        const answer = this.getAnswer(question)
        if (
          ((question.type === 'TICKBOX_CONSENT') && this.tickboxInvalid(answer)) ||
          ((question.type === 'RADIO') && this.radioInvalid(question, answer))
        ) {
          return false
        }
      }
      return true
    },
    tickboxInvalid (answer) {
      return !answer.ticked
    },
    radioInvalid (question, answer) {
      let necessaryQuestionItem =
        question.questionItems.find(questionItem => questionItem.necessary)
      if (necessaryQuestionItem) {
        let necessaryAnswerItem =
          answer.answerItems.find(answerItem => answerItem.questionItemId === necessaryQuestionItem.id)
        return !necessaryAnswerItem.selected
      }
      return false
    }
  }
}
</script>

<style scoped>
h1 {
  padding-top: 1em;
  padding-bottom: 2.5em;
  border-bottom: 1px solid rgba(34, 51, 68, 0.15);
  font-size: 1.4em;
}

.introduction {
  border-bottom: 1px solid rgba(34, 51, 68, 0.15);
  padding: 1.5em 0;
  margin: 0;
  font-weight: 700;
}

form {
  margin: 0
}
</style>
