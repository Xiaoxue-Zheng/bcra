import {
  AnswerService
} from '@/common/api.service'

const state = {
  answers: {}
}

const getters = {}

const actions = {
  async saveAnswers ({ commit }, answers) {
    const savedAnswers = await AnswerService.create(answers)
    commit('setAnswers', savedAnswers.data)
    return savedAnswers
  }
}

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
