<template>
  <fieldset>
    <div class="pure-u-1">
      <label>{{ question.text }}</label>
      <div class="items checkboxes" :id="question.identifier">
        <div v-for="questionItem in sortQuestionItems(question.questionItems)" v-bind:key="questionItem.id">
          <input
            type="checkbox"
            :id="questionItem.identifier"
            :value="questionItem.id"
            v-model="answerItemValue"
          />
          <label :for="questionItem.identifier">{{ questionItem.label }}</label>
        </div>
      </div>
    </div>
    <QuestionHint :hint="question.hint" :text="question.hintText"></QuestionHint>
  </fieldset>
</template>
<script>
import ItemQuestionBase from '@/components/ItemQuestionBase.vue'
export default {
  extends: ItemQuestionBase,
  inheritAttrs: false,
  computed: {
    answerItemValue: {
      get () {
        let checks = []
        for (const answerItem of this.answer.answerItems) {
          if (answerItem.selected) {
            checks.push(answerItem.questionItemId)
          }
        }
        return checks
      },
      set (checks) {
        for (const answerItem of this.answer.answerItems) {
          answerItem.selected = checks.includes(answerItem.questionItemId)
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
.checkboxes label {
  position: relative;
  padding-left: 3em;
}

.checkboxes label::before,
.checkboxes label::after {
  position: absolute;
  margin: 0.75em 1em;
  content: "";
  display: inline-block;
}

/*Outer box of the fake checkbox*/
.checkboxes label:before {
  border-radius: 0.15em;
  top: 0.2em;
}

/*Checkmark of the fake checkbox*/
.checkboxes label::after {
  height: 0.3em;
  width: 0.6em;
  border-left: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(-45deg);
  left: 0.2em;
  top: 0.4em;
}

/*Hide the checkmark by default*/
.checkboxes input[type="checkbox"] + label::after {
  content: none;
}

/*Unhide on the checked state*/
.checkboxes input[type="checkbox"]:checked + label::after {
  content: "";
}

.checkboxes input[type="checkbox"]:checked + label::before {
  background-color: #2277CC;
  transition: all 0.5s ease;
}
</style>
