<template>
  <div class="accordion-wrapper" @click="toggleItem" v-bind:class="{ expand: show }">
    <div class="accordion">
        <h2 class="title">
          <slot name="title"></slot><i class="chevron" v-bind:class="{ expand: show }"></i>
        </h2>
    </div>
    <transition name="fade">
      <div v-show="show">
        <p><slot name="text"></slot></p>
      </div>
    </transition>
  </div>
</template>
<script>
export default {
  components: {},
  data: function () {
    return {
      show: false
    }
  },
  methods: {
    toggleItem: function () {
      this.show = !this.show
    }
  }
}
</script>
<style scoped>
.blue {
  background: rgba(34, 119, 204, 0.1);
}
.blue.expand {
    background: rgba(34, 119, 204, 0.15);
    transition: all 0.25s ease;
}
.yellow {
  background: rgba(255, 238, 85, 0.4);
}
.yellow.expand {
    background: rgba(255, 238, 85, 0.6);
    transition: all 0.25s ease;
}
.accordion-wrapper {
    padding: 1em;
    border-radius: 0.2em;
    margin-top: 1em;
    cursor: pointer;
}
.accordion h2 {
    margin-top: 0;
    margin-bottom: 0;
    padding-right: 2em;
}
.accordion-wrapper p {
  margin-top: 0.5em;
  margin-bottom: 0;
}
.accordion {
    position: relative;
}
i {
    position: absolute;
    top: 25%;
    transform: translateY(-25%);
    left: 100%;
    margin-left: -1em;
}
.chevron {
  border: solid #223344;
  border-width: 0 3px 3px 0;
  display: inline-block;
  padding: 4px;
  transform: rotate(45deg);
  transition: transform 0.25s ease;
}
.chevron.expand {
    transform: rotate(-135deg);
}
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.25s ease;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
