import {
  QuestionnaireService
} from '@/common/api.service'

// initial state
const state = {
  questionnaire: {}
}

// getters
const getters = {}

// actions
const actions = {
  async getQuestionnaire ({ commit }) {
    const questionnaire = await QuestionnaireService.get()
    commit('setQuestionnaire', questionnaire.data)
    return questionnaire
  }
}

// mutations
const mutations = {
  setQuestionnaire (state, questionnaire) {
    state.questionnaire = questionnaire
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
