//导入所以接口
import Vue from 'vue'
import api from './api'

 const install = Vue =>{
    if(install.installed) return
    install.installed = true
    Object.defineProperties(Vue.prototype,{
        $api:{
            get(){
                return api
            }
        }
    })
 }

 export default install