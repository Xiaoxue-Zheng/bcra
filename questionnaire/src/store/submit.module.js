import { AnswerResponseService } from '@/api/answer-response.service'
import { QuestionnaireService } from '@/api/questionnaire.service'

const state = {
  questionnaire: null,
  answerResponse: null
}

const getters = {
  getQuestionnaire (state) {
    return state.questionnaire
  },
  getAnswerResponse (state) {
    return state.answerResponse
  }
}

const mutations = {
  setQuestionnaire (state, questionnaire) {
    state.questionnaire = questionnaire
  },
  setAnswerResponse (state, answerResponse) {
    state.answerResponse = answerResponse
  }
}

const actions = {
  async getQuestionnaire ({ commit, getters }) {
    let questionnaire = getters.getQuestionnaire
    if (questionnaire === null) {
      questionnaire = await QuestionnaireService.getRiskAssessment()
      commit('setQuestionnaire', questionnaire)
    }
    return questionnaire
  },
  async getAnswerResponse ({ commit, getters }) {
    let answerResponse = getters.getAnswerResponse
    if (answerResponse === null) {
      answerResponse = await AnswerResponseService.getRiskAssessment()
      commit('setAnswerResponse', answerResponse)
    }
    return answerResponse
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
