<template>
  <fieldset>
    <div class="pure-u-1" :id="question.identifier">
      <QuestionText
        :question="question"
        :questionVariables="questionVariables"
      />
      <div>
        <div class="input-group">
          <div class="input-group-area">
            <input
              type="number"
              class="pure-input-1"
              v-model="answer.number"
              :min="question.minimum"
              :max="question.maximum"
              step="1"
              :disabled="readOnly"
            /></div>
            <div class="input-group-unit">Years</div>
        </div>
        <div class="items radios">
          <input
            type="radio"
            :id="questionIdentifier()"
            :name="questionIdentifier()"
            :value="null"
            v-model="answer.number"
            :disabled="readOnly"
          />
          <label :for="questionIdentifier()">Don't know</label>
        </div>
      </div>
    </div>
    <QuestionHint :question="question" :id="'MODAL_'+ question.identifier"></QuestionHint>
  </fieldset>
</template>
<script>
import RadioQuestion from '@/components/RadioQuestion.vue'

export default {
  extends: RadioQuestion,
  inheritAttrs: false,
  props: [
    'questionVariables'
  ],
  methods: {
    questionIdentifier () {
      return this.question.identifier + '_dk'
    }
  }

}
</script>
<style scoped>
.input-group {
  display: inline-table;
  margin: 1em 0;
}

.radios {
  display: inline-block;
  margin: 1em 0 0 2em;
}

.input-group-unit {
  display: table-cell;
  vertical-align: middle;
  background: rgba(34, 51, 68, 0.1);
  border-radius: 0em 0.2em 0.2em 0em !important;
  padding: 0em 0.75em;
  border: 1px solid #ccc;
  border-left: 0px;
}

.input-group-area {
  width:100%;
}
.input-group input {
  display: block;
  width: 100%;
  border-radius: 0.2em 0em 0em 0.2em !important;
}

.inline {
  display: inline-block;
}

.radios input:checked + label:before {
  background-color: #2277CC;
  border-color: #2277CC;
  -moz-box-shadow: inset 0 0 0 0.25em white;
  -webkit-box-shadow: inset 0 0 0 0.25em white;
  box-shadow: inset 0 0 0 0.25em white;
}
</style>
