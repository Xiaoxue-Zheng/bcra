<template>
  <div v-if="questionSection != null && answerSection != null">
    <QuestionSection
      :progressStage="progressStage"
      :questionSection="questionSection"
      :answerSection="answerSection"
      :buttonText="buttonText"
      :buttonAction="buttonAction"
      :buttonError="saveError"
      :questionVariables="questionVariables"
      :questionnaire="questionnaire"
      :answerResponse="answerResponse"
      :readOnly="readOnly"
    >
      <component
      :is="infoComponent">
      </component>
    </QuestionSection>

  </div>
</template>

<script>
import router from '../router/'
import QuestionSection from '@/components/QuestionSection.vue'
import QuestionSectionFamilyHistoryInfo from '@/components/QuestionSectionFamilyHistoryInfo.vue'
import QuestionSectionYourHistoryInfo from '@/components/QuestionSectionYourHistoryInfo.vue'
import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'
import { QuestionSectionService } from '@/services/question-section.service.js'
import { QuestionVariableService } from '@/services/question-variable.service.js'
import { ReferralConditionService } from '@/services/referral-condition.service.js'
import { AnswerHelperService } from '@/services/answer-helper.service.js'
import { StudyService } from '@/api/study.service'

export default {
  name: 'riskAssessment',
  components: {
    'QuestionSection': QuestionSection,
    'QuestionSectionFamilyHistoryInfo': QuestionSectionFamilyHistoryInfo,
    'QuestionSectionYourHistoryInfo': QuestionSectionYourHistoryInfo
  },
  data () {
    return {
      questionnaire: null,
      answerResponse: null,
      studyCode: null,
      questionSection: null,
      answerSection: null,
      progressStage: null,
      saveError: false,
      buttonText: null,
      buttonAction: null,
      infoComponent: null,
      questionVariables: {},
      referralConditions: {},
      readOnly: null
    }
  },
  async created () {
    this.studyCode = await StudyService.getStudyCode()
    this.questionnaire = await QuestionnaireService.getRiskAssessment()
    this.answerResponse = await AnswerResponseService.getRiskAssessment(this.studyCode)
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
      this.readOnly = false
      this.questionSection = this.getCurrentSection()
      this.progressStage = this.questionSection.progress
      this.answerSection = this.answerResponse.answerSections.find(
        answerSection => (answerSection.questionSectionId === this.questionSection.id)
      )

      this.initialiseQuestionSectionProperties(this.questionSection.identifier)

      this.questionVariables = QuestionVariableService.getQuestionVariables(this.questionnaire)
    },
    initialiseQuestionSectionProperties (questionSectionIdentifier) {
      this.infoComponent = QuestionSectionService.getSectionInfoComponent(questionSectionIdentifier)
      this.configureButtonPropertiesForQuestionSection()
    },
    configureButtonPropertiesForQuestionSection () {
      let buttonText = null
      if (this.infoComponent !== null) {
        buttonText = this.readOnly === true ? 'Continue review' : 'Continue'
      } else {
        buttonText = this.readOnly === true ? 'Continue review' : 'Save and continue'
      }

      this.buttonText = buttonText

      if (this.readOnly === true) {
        this.buttonAction = this.continue
      } else {
        this.buttonAction = this.saveAndContinue
      }
    },
    getCurrentSection () {
      const routeLocation = router.history.current.params.section

      return this.questionnaire.questionSections.find(
        questionSection => (questionSection.url === routeLocation)
      )
    },
    saveAndContinue () {
      QuestionSectionService.clearUntakenSectionAnswers(this.questionSection, this.questionnaire)
      this.saveQuestionnaire()
    },
    continue () {
      this.proceedToNextRoute()
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
