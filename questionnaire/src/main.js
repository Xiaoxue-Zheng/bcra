// eslint-disable-next-line
import Purecss from 'purecss-sass'

import Vue from 'vue'
import App from './App.vue'
import router from './router/'
import store from './store/'
import constPlugin from './components/ConstPlugin'

import ApiService from './api/api.service'
import Vidle from 'v-idle'

import VueMoment from 'vue-moment'
import moment from 'moment-timezone'

Vue.config.productionTip = false

ApiService.init()

store.dispatch('security/fetchAuthenticated')

const app = new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')

Vue.use(Vidle)
Vue.use(constPlugin)
Vue.use(VueMoment)
Vue.use(moment)

if (window.Cypress) {
  window.app = app
}
