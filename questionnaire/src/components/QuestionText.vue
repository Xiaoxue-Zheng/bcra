<template>
    <label>{{ questionText }}</label>
</template>
<script>
export default {
  props: [
    'question',
    'questionVariables'
  ],
  computed: {
    questionText: function () {
      let questionText = this.question.text
      for (const variableName in this.questionVariables) {
        questionText = questionText.replace(
          new RegExp('{{' + variableName + '}}', 'g'),
          this.questionVariables[variableName]
        )
      }
      return questionText
    }
  }
}
</script>
<style scoped>
</style>
