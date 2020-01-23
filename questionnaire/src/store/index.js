import Vue from 'vue'
import Vuex from 'vuex'
import security from './security.module'
import consent from './consent.module'
import answer from './answer.module'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    security,
    consent,
    answer
  },
  strict: debug
})
