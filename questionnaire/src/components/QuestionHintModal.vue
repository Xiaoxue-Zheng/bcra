<template>
  <transition name="modal">
    <div class="modal-background" @click="close">
      <div class="modal-container" role="dialog" aria-labelledby="modalTitle" aria-describedby="modalDescription">
        <h2 class="modal-header" id="modalTitle">{{question.hint}}</h2>
        <p class="modal-body" id="modalDescription" v-html="question.hintText"/>
        <footer class="modal-footer">
          <slot name="modal-footer">
            <a href="javascript:void(0);" aria-label="Close modal" onclick="close">
              Close
            </a>
          </slot>
        </footer>
      </div>
    </div>
  </transition>
</template>
<script>
export default {
  name: 'QuestionHintModal',
  props: ['question'],
  methods: {
    close () {
      this.$emit('close')
    }
  }
}
</script>
<style>
  .modal-background {
    position: fixed;
    z-index: 9998;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(34, 51, 68, 0.5);
    transition: opacity .25s ease;
  }
  .modal-container {
    width: calc(100vw - 4em);
    max-width: calc(1024px - 2em);
    top: 50%;
    left: 50%;
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
    margin: 0;
    position: absolute;
    background: #fff6aa;
    padding: 1em;
    border-radius: 4px;
    cursor: pointer;
    box-shadow: 0 2px 8px rgba(34, 51, 68, 0.5);
  }

  .modal-container p {
  margin-top: 0.5em;
  margin-bottom: 0.5em;
}

.modal-container h2 {
  margin: 0;
}

.modal-fade-enter, .modal-leave-active {
  opacity: 0;
}

.modal-fade-enter-active,
.modal-fade-leave-active {
  transition: opacity 0.5s ease;
}
</style>
