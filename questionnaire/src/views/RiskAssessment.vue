<template>
  <div>
    <QuestionSection
      :progressStage="progressStage"
      :questionSection="questionSection"
      :answerSection="answerSection"
      buttonText="Save and continue"
      :buttonAction="saveButtonClicked"
      :buttonError="saveError"
      :questionVariables="questionVariables"
      :questionnaire="questionnaire"
      :answerResponse="answerResponse"
    >
    </QuestionSection>
  </div>
</template>

<script>
import router from '../router/'
import QuestionSection from '@/components/QuestionSection.vue'
import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'
import { QuestionSectionService } from '@/services/question-section.service.js'
import { QuestionVariableService } from '@/services/question-variable.service.js'
import { ReferralConditionService } from '@/services/referral-condition.service.js'
import { AnswerHelperService } from '@/services/answer-helper.service.js'

export default {
  name: 'riskAssessment',
  components: {
    'QuestionSection': QuestionSection
  },
  data () {
    return {
      questionnaire: null,
      answerResponse: null,
      questionSection: null,
      answerSection: null,
      progressStage: null,
      saveError: false,
      questionVariables: {},
      referralConditions: {}
    }
  },
  async created () {
    this.questionnaire = await QuestionnaireService.getRiskAssessment()
    this.answerResponse = await AnswerResponseService.getRiskAssessment()
    AnswerHelperService.initialise(this.questionnaire, this.answerResponse)
    this.initialiseSection()
  },
  watch: {
    $route (to, from) {
      this.initialiseSection()
    }
  },
  methods: {
    initialiseSection () {
      this.questionSection = this.getCurrentSection()
      this.progressStage = this.questionSection.progress
      this.answerSection = this.answerResponse.answerSections.find(
        answerSection => (answerSection.questionSectionId === this.questionSection.id)
      )
      this.questionVariables = QuestionVariableService.getQuestionVariables(this.questionnaire)
    },
    getCurrentSection () {
      const routeLocation = router.history.current.params.section
      return this.questionnaire.questionSections.find(
        questionSection => (questionSection.url === routeLocation)
      )
    },
    saveButtonClicked () {
      QuestionSectionService.clearUntakenSectionAnswers(this.questionSection, this.questionnaire)
      this.saveQuestionnaire()
    },
    async saveQuestionnaire () {
      this.saveError = false
      const saveResult = await AnswerResponseService.saveRiskAssessment(this.answerResponse)
      if (saveResult.data === 'SAVED') {
        this.proceedToNextRoute()
      } else {
        this.saveError = true
      }
    },
    proceedToNextRoute () {
      this.referralConditions = ReferralConditionService.getNewReferralConditions(this.questionSection)
      if (this.referralConditions.length > 0) {
        this.proceedToSubmit()
      } else {
        const nextSection = QuestionSectionService.getNextSection(this.questionSection, this.questionnaire)
        if (!nextSection) {
          this.proceedToSubmit()
        } else {
          this.$router.push('/questionnaire/' + nextSection.url)
        }
      }
    },
    proceedToSubmit () {
      this.$store.commit('submit/setQuestionnaire', this.questionnaire)
      this.$store.commit('submit/setAnswerResponse', this.answerResponse)
      this.$router.push('/submit')
    }
  }
}
</script>
