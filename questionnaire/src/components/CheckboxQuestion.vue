<template>
  <fieldset>
    <div class="pure-u-1">
      <QuestionText
        :question="question"
        :questionVariables="questionVariables"
      />
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
  props: [
    'questionVariables'
  ],
  data () {
    return {
      previousCheckedIds: []
    }
  },
  computed: {
    answerItemValue: {
      get () {
        let checkedIds = []
        for (const answerItem of this.answer.answerItems) {
          if (answerItem.selected) {
            checkedIds.push(answerItem.questionItemId)
          }
        }
        this.setPreviousCheckedIds(checkedIds)
        return checkedIds
      },
      set (checkedIds) {
        const newlyCheckedId = this.getNewlyCheckedId(checkedIds, this.previousCheckedIds)
        if (newlyCheckedId) {
          if (this.isExclusive(newlyCheckedId)) {
            checkedIds = [newlyCheckedId]
          } else {
            checkedIds = this.removeExclusive(checkedIds)
          }
        }
        for (const answerItem of this.answer.answerItems) {
          answerItem.selected = checkedIds.includes(answerItem.questionItemId)
        }
        this.setPreviousCheckedIds(checkedIds)
      }
    },
    questionText: function () {
      return this.question.text
    }
  },
  methods: {
    setPreviousCheckedIds (checkedIds) {
      this.previousCheckedIds = checkedIds
    },
    sortQuestionItems (questionItems) {
      let itemClone = questionItems.slice()
      return itemClone.sort((itemA, itemB) => {
        return itemA.order - itemB.order
      })
    },
    getNewlyCheckedId (checkedIds, previousCheckedIds) {
      const newlyCheckedIds = checkedIds.filter(
        id => !previousCheckedIds.includes(id)
      )
      if (newlyCheckedIds.length === 1) {
        return newlyCheckedIds[0]
      }
      return null
    },
    isExclusive (questionItemId) {
      return this.question.questionItems.find(
        questionItem => questionItem.id === questionItemId
      ).exclusive
    },
    removeExclusive (questionItemIds) {
      return questionItemIds.filter(questionItemId => {
        return !this.isExclusive(questionItemId)
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
