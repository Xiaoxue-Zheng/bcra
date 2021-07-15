<template>
  <div class="modal-bg" v-show="modalShow">
    <div class="modal-container">
      <div class="modal-header">
        {{ title }}
      </div>
      <div class="modal-main">
        <slot></slot>
      </div>
      <div class="modal-footer">
        <button class="modal-button-secondary"  @click="hideModal">{{ cancelButtonText}}</button>
        <button class="modal-button-primary" @click="submit">{{ submitButtonText }}</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Modal',
  props: {
    title: {
      type: String,
      default: ''
    },
    cancelButtonText: {
      type: String,
      default: 'cancel'
    },
    submitButtonText: {
      type: String,
      default: 'confirm'
    },
    modalShow: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      x: 0,
      y: 0,
      node: null,
      isCanMove: false,
      show: this.modalShow
    }
  },
  mounted () {
    this.node = document.querySelector('.modal-container')
  },
  methods: {

    hideModal () {
      this.show = false
      this.$emit('hideModal', this.show)
    },

    submit () {
      this.show = false
      this.$emit('submit', this.show)
    }
  }
}
</script>

<style scoped>
  .modal-bg {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0,0,0,.80);
    z-index: 10;
  }
  .modal-container {
    background: #fff;
    border-radius: 10px;
    overflow: hidden;
    position: fixed;
    top: 50%;
    left: 50%;
    width: 50%;
    transform: translate(-50%,-80%);
  }
  .modal-header {
    height: 30px;
    background: #409EFF;
    color: #fff;
    display: flex;
    align-items: center;
    justify-content: center;
    cursor: move;
  }
  .modal-footer {
    display: flex;
    align-items: center;
    justify-content: center;
    height: 60px;
    border-top: 1px solid #ddd;
  }
  .modal-main {
    padding: 5px 20px;
  }
  .modal-button-secondary {
    display: inline-block;
    border-radius: 0.2em;
    margin: 2em 0.75em 2em 0;
    border: 2px solid rgb(34, 119, 204);
    padding: 2px 4px;
  }
  .modal-button-primary {
    border-radius: 0.2em;
    margin: 2em 0.75em 2em 0;
    border: 2px solid rgb(34, 119, 204);
    background: rgba(34, 119, 204, 0.8);
    color: white;
    padding: 2px 4px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

</style>
