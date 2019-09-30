import Vue from 'vue'
import Vuex from 'vuex'
import questionnaire from './questionnaire.module'
import answer from './answer.module'

Vue.use(Vuex)

const debug = process.env.NODE_ENV !== 'production'

export default new Vuex.Store({
  modules: {
    questionnaire,
    answer
  },
  strict: debug
})
