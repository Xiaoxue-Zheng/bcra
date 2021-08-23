<template>
  <fieldset>
    <div class="pure-u-1">
      <QuestionText
        :question="question"
        :questionVariables="questionVariables"
      />
      <div class="select-box">
        <select :disabled="readOnly" :id="question.identifier" v-model="answerItemValue" class="select-css" required>
            <option value="" disabled selected>Please select a response</option>
            <option :value="$getConst('DONT_KNOW')">Don't know</option>
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
        if (this.answer.dontKnow) {
          return 'dontknow'
        } else {
          return this.answer.number
        }
      },
      set (val) {
        if (val === 'dontknow') {
          this.answer.dontKnow = true
          this.answer.number = null
        } else {
          this.answer.dontKnow = null
          this.answer.number = val
        }
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
