<script>
import QuestionText from '@/components/QuestionText.vue'
import QuestionHint from '@/components/QuestionHint.vue'
export default {
  props: [
    'question',
    'answer',
    'readOnly'
  ],
  components: {
    'QuestionHint': QuestionHint,
    'QuestionText': QuestionText
  }
}
</script>
<style>

</style>
