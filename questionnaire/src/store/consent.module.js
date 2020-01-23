import { QuestionnaireService } from '@/api/questionnaire.service'
import { AnswerResponseService } from '@/api/answer-response.service'

// initial state
const state = {
  questionnaire: {},
  answerResponse: {},
  saveResult: null,
  submitResult: null
}

// getters
const getters = {
  answerResponse: state => {
    return state.answerResponse
  }
}

// actions
const actions = {
  async getQuestionnaire ({ commit }) {
    const questionnaire = await QuestionnaireService.getConsent()
    commit('setQuestionnaire', questionnaire.data)
    return questionnaire
  },
  async getAnswerResponse ({ commit }) {
    const answerResponse = await AnswerResponseService.getConsent()
    commit('setAnswerResponse', answerResponse.data)
    return answerResponse
  },
  async saveAnswerResponse ({ commit }, answerResponse) {
    const saveResult = await AnswerResponseService.saveConsent(answerResponse)
    commit('setSaveResult', saveResult.data)
    return saveResult
  },
  async submitAnswerResponse ({ commit }, answerResponse) {
    const submitResult = await AnswerResponseService.submitConsent(answerResponse)
    commit('setSubmitResult', submitResult.data)
    return submitResult
  }
}

// mutations
const mutations = {
  setQuestionnaire (state, questionnaire) {
    state.questionnaire = questionnaire
  },
  setAnswerResponse (state, answerResponse) {
    state.answerResponse = answerResponse
  },
  setSaveResult (state, saveResult) {
    state.saveResult = saveResult
  },
  setSubmitResult (state, submitResult) {
    state.submitResult = submitResult
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
