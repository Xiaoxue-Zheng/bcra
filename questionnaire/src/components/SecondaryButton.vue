<template>
    <router-link
        class="pure-button pure-button-secondary"
        type="submit"
        :to="to"
    >
        <slot></slot>
    </router-link>
</template>
<script>
export default {
  props: [
    'to'
  ]
}
</script>
<style scoped>
.pure-button-secondary {
    background: white;
    border: 2px solid rgb(34, 119, 204);
    color: rgb(34, 119, 204);
}

.pure-button-secondary-active, .pure-button-secondary:active {
    box-shadow: none;
    background: rgb(34, 119, 204);
    color: white;
}

.pure-button-secondary:hover {
    background: rgba(34, 119, 204, 0.2);
    transition: all 0.4s ease;
}
</style>
