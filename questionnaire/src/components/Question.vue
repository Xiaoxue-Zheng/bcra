<template>
  <div class="question">
    <div class="question-text">{{ text }}</div>
  </div>
</template>

<script>
export default {
  name: 'Question',
  props: {
    text: String
  }
}
</script>

<style scoped lang="scss">
.question {
  padding: 2em;
  border: 4px solid #aaa;
  margin: 4em;
}
.question-text {
  font-size: 1.5em;
  font-weight: bold;
}

</style>
