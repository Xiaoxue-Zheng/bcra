<template>
  <fieldset>
    <div class="pure-u-1">
      <label>{{ question.text }}</label>
      <div class="select-box">
        <select :id="question.identifier" v-model="answer.number" class="select-css">
            <option value="null">Don't know</option>
            <option
              v-for="index in parseInt((question.maximum + 1) - question.minimum)"
              :key="index"
              :value="question.minimum + index - 1"
            >{{ getSelectText(question.minimum + index - 1, question.maximum) }}</option>
        </select>
      </div>
      <QuestionHint :hint="question.hint" :text="question.hintText"></QuestionHint>
    </div>
  </fieldset>
</template>
<script>
import DropdownQuestionBase from '@/components/DropdownQuestionBase.vue'

export default {
  extends: DropdownQuestionBase,
  inheritAttrs: false,
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
