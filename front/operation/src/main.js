// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import VueRouter from 'vue-router'
import router from './router'
// 导入 ElementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
//集中封装的api
import api from './api'

import App from './App'

// 安装路由
Vue.use(VueRouter);
//安装 ElementUI
Vue.use(ElementUI);
//集中封装的api
Vue.use(api);

Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  // 启用路由
  router,
  // 启用 ElementUI
  render: h => h(App)
  // components: { App },
  // template: '<App/>'
})
