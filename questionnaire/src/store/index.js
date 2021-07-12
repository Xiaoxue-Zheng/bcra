import Vue from 'vue'
import Vuex from 'vuex'
import security from './security.module'
import submit from './submit.module'
import referral from './referral.module'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    security,
    submit,
    referral
  },
  strict: debug
})
