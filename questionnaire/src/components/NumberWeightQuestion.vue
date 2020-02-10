<template>
    <fieldset>
        {{ question.type }}
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
</style>
