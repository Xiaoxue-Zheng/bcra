import {
  SecurityService
} from '@/common/api.service'

import { createHelpers } from 'vuex-map-fields'

const { isAuthenticated } = createHelpers({
  getterType: 'isAuthenticated'
})

const state = {
  authenticated: false
}

const getters = {
  isAuthenticated
}

const mutations = {
  setAuthenticated (state, authenticated) {
    state.authenticated = authenticated
  }
}

const actions = {
  async login ({ commit }, fields) {
    const loginOutcome = await SecurityService.login(fields.username, fields.password)
    if (loginOutcome === 'SUCCESS') {
      commit('setAuthenticated', true)
    } else {
      commit('setAuthenticated', false)
    }
    return loginOutcome
  },
  async logout ({ commit }) {
    await SecurityService.logout().then(() => {
      commit('setAuthenticated', false)
    })
  },
  // This will also set the CSRF token.
  async fetchAuthenticated ({ commit }) {
    SecurityService.getUser().then(() => {
      commit('setAuthenticated', true)
    }).catch(() => {
      commit('setAuthenticated', false)
    })
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
