import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login'
import Main from '../views/Main'
import DeviceInfo from '../views/device/DeviceInfo'
import FileList from '../views/file/FileList'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Main',
      component: Main,
      redirect: '/device',
      // 配置嵌套路由
      children: [
        {path: '/device', component: DeviceInfo},
        {path: '/fileList', component: FileList},
      ]
    },
    {
      // 登录页
      path: '/login',
      name: 'Login',
      component: Login
    },
  ]
})
