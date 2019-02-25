import Vue from 'vue'
import App from './App.vue'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import Axios from 'axios'

Vue.config.productionTip = false;

Vue.use(ElementUI);

const instance = Axios.create({
    baseURL: 'http://localhost:8188/'
});
Vue.prototype.$axios = instance;

new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
