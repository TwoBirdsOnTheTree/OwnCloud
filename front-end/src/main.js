import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Axios from 'axios'

Vue.config.productionTip = false;

const instance = Axios.create({
    baseURL: 'http://localhost:8188/'
});
Vue.prototype.$axios = instance;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
