import {
  SecurityService
} from '@/api/security.service'

import { createHelpers, getField, updateField } from 'vuex-map-fields'

const { isAuthenticated } = createHelpers({
  getterType: 'isAuthenticated'
})

const state = {
  authenticated: false,
  activation: {
    nhsNumber: '',
    dateOfBirth: ''
  }
}

const getters = {
  isAuthenticated,
  getActivationField (state) {
    return getField(state.activation)
  }
}

const mutations = {
  setAuthenticated (state, authenticated) {
    state.authenticated = authenticated
  },
  setActivationField (state, field) {
    updateField(state.activation, field)
  }
}

const actions = {
  async fetchAuthenticated ({ commit }) {
    // This will also set the CSRF token.
    SecurityService.getUser().then(() => {
      commit('setAuthenticated', true)
    }).catch(() => {
      commit('setAuthenticated', false)
    })
  },

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
  }
}

export default {
  namespaced: true,
  state,
  getters,
  mutations,
  actions
}
