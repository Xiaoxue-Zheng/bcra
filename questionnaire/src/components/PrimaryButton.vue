<template>
    <div>
        <router-link
            v-if="to"
            :to="to"
            class="pure-button pure-button-primary"
            type="submit"
        >
            <slot></slot>
        </router-link>
        <div
            v-if="clickEvent"
            @click="clickEvent"
            class="pure-button pure-button-primary"
            type="submit"
        >
            <slot></slot>
        </div>
    </div>
</template>
<script>
import ButtonBase from '@/components/ButtonBase.vue'
export default {
  extends: ButtonBase,
  inheritAttrs: false,
  props: []
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

.pure-button-primary:hover {
    background: rgba(34, 119, 204, 0.8);
    transition: all 0.4s ease;
}
</style>
