<template>
    <a v-if="hint" href="#">{{ hint }}</a>
</template>
<script>
export default {
  props: [
    'hint',
    'text'
  ]
}
</script>
<style scoped>
</style>
