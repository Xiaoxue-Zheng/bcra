import {
  AnswerService
} from '@/common/api.service'

// initial state
const state = {
  answers: {}
}

// getters
const getters = {}

// actions
const actions = {
  async saveAnswers ({ commit }, answers) {
    const savedAnswers = await AnswerService.create(answers)
    commit('setAnswers', savedAnswers.data)
    return savedAnswers
  }
}

// mutations
const mutations = {
  setAnswers (state, answers) {
    state.answers = answers
  }
}

export default {
  namespaced: true,
  state,
  getters,
  actions,
  mutations
}
