<template>
  <fieldset>
    <div class="pure-u-1">
      <QuestionText
        :question="question"
        :questionVariables="questionVariables"
      />
      <div class="items radios">
        <div v-for="questionItem in sortQuestionItems(question.questionItems)" v-bind:key="questionItem.id">
          <input
            type="radio"
            :id="questionItem.identifier"
            :name="question.identifier"
            :value="questionItem.id"
            v-model="answerItemValue"
          />
          <label :for="questionItem.identifier">{{ questionItem.label }}</label>
        </div>
      </div>
     </div>
    <QuestionHint :question="question" :id="'MODAL_'+ question.identifier"></QuestionHint>
  </fieldset>
</template>
<script>
import ItemQuestionBase from '@/components/ItemQuestionBase.vue'
export default {
  extends: ItemQuestionBase,
  inheritAttrs: false,
  props: [
    'questionVariables'
  ],
  computed: {
    answerItemValue: {
      get () {
        let answerItem = this.answer.answerItems.find(
          answerItem => answerItem.selected
        )
        if (answerItem) {
          return answerItem.questionItemId
        } else {
          return false
        }
      },
      set (questionItemId) {
        for (let answerItem of this.answer.answerItems) {
          answerItem.selected = (answerItem.questionItemId === questionItemId)
        }
      }
    }
  },
  methods: {
    sortQuestionItems (questionItems) {
      let itemClone = questionItems.slice()
      return itemClone.sort((itemA, itemB) => {
        return itemA.order - itemB.order
      })
    }
  }
}
</script>
<style scoped>
.radios input:checked + label:before {
  background-color: #2277CC;
  border-color: #2277CC;
  -moz-box-shadow: inset 0 0 0 0.25em white;
  -webkit-box-shadow: inset 0 0 0 0.25em white;
  box-shadow: inset 0 0 0 0.25em white;
}
</style>
