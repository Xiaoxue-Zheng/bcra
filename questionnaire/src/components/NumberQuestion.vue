<template>
    <fieldset>
        <label>{{ question.text }}</label>
        <div class="input-group">
            <div class="input-group-area">
                <input
                    type="number"
                    class="pure-input-1"
                    v-model="answer.number"
                    :min="question.minimum"
                    :max="question.maximum"
                    step="1"
                /></div>
                <div class="input-group-unit">Years</div>
        </div>
        <QuestionHint :hint="question.hint" :text="question.hintText"></QuestionHint>
    </fieldset>
</template>
<script>
import QuestionBase from '@/components/QuestionBase.vue'

export default {
  extends: QuestionBase,
  inheritAttrs: false
}
</script>
<style scoped>
.input-group {
  display: table;
  margin: 1em 0;
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
</style>
