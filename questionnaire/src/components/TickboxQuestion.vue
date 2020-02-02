<template>
  <fieldset>
    <div class="pure-u-1">
      <input type="checkbox" :id="question.identifier" v-model="answerValue"/>
      <label :for="question.identifier">{{ question.text }}</label>
    </div>
</fieldset>
</template>
<script>
import QuestionBase from '@/components/QuestionBase.vue'
export default {
  extends: QuestionBase,
  inheritAttrs: false,
  computed: {
    answerValue: {
      get () {
        return this.answer.ticked
      },
      set (value) {
        this.answer.ticked = value
      }
    }
  },
  created () {
    this.answer.ticked = false
  }
}
</script>
<style scoped>
input[type="checkbox"] {
  display: none;
}

label {
  position: relative;
  display: inline-block;
  cursor: pointer;
  font-weight: 400;
  padding-left: 2em;
}

label::before,
label::after {
  position: absolute;
  content: "";
  display: inline-block;
}

/*Outer box of the fake checkbox*/
label::before{
  height: 1em;
  width: 1em;
  border-radius: 0.15em;
  border: 1px solid #2277CC;
  left: 0px;
  top: 0.2em;
  transition: all 0.5s ease;
}

/*Checkmark of the fake checkbox*/
label::after {
  height: 0.3em;
  width: 0.6em;
  border-left: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(-45deg);
  left: 0.2em;
  top: 0.4em;
}

/*Hide the checkmark by default*/
input[type="checkbox"] + label::after {
  content: none;
}

/*Unhide on the checked state*/
input[type="checkbox"]:checked + label::after {
  content: "";
}

input[type="checkbox"]:checked + label::before {
  background-color: #2277CC;
  transition: all 0.5s ease;
}

div {
  border-bottom: 1px solid rgba(34, 51, 68, 0.15);
  padding-top: 1.5em;
  padding-bottom: 1.5em;
}

div:first-of-type {
  padding-top: 0;
}

div:last-of-type {
  border-width: 0;
  padding-bottom: 0;
}
</style>
