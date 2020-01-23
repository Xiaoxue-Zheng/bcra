import { AnswerResponseService } from '@/api/answer-response.service'

const state = {
  answers: {}
}

const getters = {}

const actions = {
  async saveAnswers ({ commit }, answers) {
    const savedAnswers = await AnswerResponseService.create(answers)
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
