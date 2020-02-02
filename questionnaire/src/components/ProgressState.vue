<template>
    <div class="progress">
        <div
          v-for="index in parseInt(PROGRESS_STAGE_COUNT)"
          :key="index"
          v-bind:class="{
            badge: true,
            complete: (index < progressStage),
            current: (index == progressStage)
          }"
        >
          {{ index }}
        </div>
    </div>
</template>

<style scoped>
.progress {
    float: right;
}

.badge {
    display: inline-block;
    min-width: 1.55em;
    padding: 0.3em;
    border-radius: 50%;
    color: #2277CC;
    text-align: center;
    margin-left: 0.5em;
    margin-top: -0.3em;
    -moz-box-shadow: inset 0 0 0 2px #2277CC;
    -webkit-box-shadow: inset 0 0 0 2px #2277CC;
    box-shadow: inset 0 0 0 2px #2277CC;
    opacity: 0.4;
    position: relative;
}

.current {
    opacity: 1;
}

.complete {
    background-color: #22CC77;
    color: white;
    -moz-box-shadow: inset 0 0 0 2px #22CC77;
    -webkit-box-shadow: inset 0 0 0 2px #22CC77;
    box-shadow: inset 0 0 0 2px #22CC77;
    opacity: 1;
}

.complete::before,
.complete::after {
  position: absolute;
  content: "";
}

.complete::before {
  width: 1em;
  height: 1em;
  background-color: #22CC77;
  border: 1px solid white;
  border-radius: 50%;
  bottom: 0em;
  left: -0.5em;
}

.complete::after {
  height: 0.2em;
  width: 0.4em;
  border-left: 2px solid #fff;
  border-bottom: 2px solid #fff;
  transform: rotate(-45deg);
  bottom: 0.45em;
  left: -0.25em;
}
</style>

<script>
const PROGRESS_STAGE_COUNT = 4

export default {
  props: [
    'progressStage'
  ],
  data () {
    return {
      PROGRESS_STAGE_COUNT: PROGRESS_STAGE_COUNT
    }
  }
}
</script>
