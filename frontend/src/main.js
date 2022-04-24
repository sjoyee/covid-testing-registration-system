import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import axios from 'axios'
import router from './router/index'

Vue.config.productionTip = false

window.axios = axios
axios.defaults.baseURL = 'http://localhost:8085/api/v1'

new Vue({
  router,
  axios,
  vuetify,
  render: h => h(App)
}).$mount('#app')
