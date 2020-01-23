<template>
    <router-link
        class="pure-button pure-button-primary"
        type="submit"
        :to="to"
    >
        <slot></slot>
    </router-link>
</template>
<script>
import ButtonBase from '@/components/ButtonBase.vue'
export default {
  extends: ButtonBase,
  inheritAttrs: false,
  props: [
    'sdf'
  ]
}
</script>
<style scoped>
.pure-button-primary {
    background: rgb(34, 119, 204);
}

.pure-button-primary-active, .pure-button-primary:active {
    box-shadow: none;
    background: white;
    color: rgb(34, 119, 204);
}
</style>
