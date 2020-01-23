// eslint-disable-next-line
import Purecss from 'purecss-sass'

import Vue from 'vue'
import App from './App.vue'
import router from './router/'
import store from './store/'

import ApiService from './api/api.service'

Vue.config.productionTip = false

ApiService.init()

store.dispatch('security/fetchAuthenticated')

const app = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

if (window.Cypress) {
  window.app = app
}
