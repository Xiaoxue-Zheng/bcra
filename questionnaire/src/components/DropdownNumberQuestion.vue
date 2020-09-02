<template>
  <fieldset>
    <div class="pure-u-1">
      <QuestionText
        :question="question"
        :questionVariables="questionVariables"
      />
      <div class="select-box">
        <select :id="question.identifier" v-model="answerItemValue" class="select-css">
            <option value="null">Don't know</option>
            <option
              v-for="index in parseInt((question.maximum + 1) - question.minimum)"
              :key="index"
              :value="question.minimum + index - 1"
            >{{ getSelectText(question.minimum + index - 1, question.maximum) }}</option>
        </select>
      </div>
      <QuestionHint :question="question" :id="'MODAL_'+ question.identifier"></QuestionHint>
    </div>
  </fieldset>
</template>
<script>
import DropdownQuestionBase from '@/components/DropdownQuestionBase.vue'

export default {
  extends: DropdownQuestionBase,
  inheritAttrs: false,
  props: [
    'questionVariables'
  ],
  computed: {
    answerItemValue: {
      get () {
        return this.answer.number
      },
      set (questionItemId) {
        this.answer.number = questionItemId
      }
    }
  },
  methods: {
    getSelectText (number, maximum) {
      if (number === 0) {
        return 'None'
      } else if (number === maximum) {
        return number + '+'
      } else {
        return number
      }
    }
  }
}
</script>
<style scoped>
.select-box {
  margin-top: 0.5em;
}
</style>
