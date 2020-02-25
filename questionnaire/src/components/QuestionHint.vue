<template>
  <div class="modal-example" v-if="question.hint">
    <div class="pure-g">
      <div class="pure-u-1">
        <a class="info-link" href="#" @click="showModal">{{question.hint}}</a>
      </div>
    </div>
    <div class="pure-g">
      <div class="pure-u-1">
        <QuestionHintModal v-show="isModalVisible" @close="closeModal" :question="question"/>
      </div>
    </div>
  </div>
</template>
<script>
import QuestionHintModal from '@/components/QuestionHintModal.vue'

export default {
  props: ['question'],
  components: {
    QuestionHintModal
  },
  data () {
    return {
      isModalVisible: false
    }
  },
  methods: {
    showModal () {
      this.isModalVisible = true
    },
    closeModal () {
      this.isModalVisible = false
    }
  }
}
</script>
<style scoped>
</style>
