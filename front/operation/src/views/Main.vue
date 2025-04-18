<template>
  <div style="height: 100%;">
      <el-container style="height: 100%;">
          <el-header>
              <h1>运维工具软件</h1>
              <span><el-button type="text" @click="loginOut">退出登录</el-button></span>
          </el-header>
          <el-container style="height: calc(100% - 60px);">
              <el-aside width="200px">
                  <el-menu :default-openeds="['1']">
                      <el-menu-item index="1">
                          <router-link to="/device">首页</router-link>
                      </el-menu-item>
                      <el-menu-item index="2">
                          <router-link to="/fileList">文件操作</router-link>
                      </el-menu-item>
                  </el-menu>
              </el-aside>
              <el-main>
                  <router-view />
              </el-main>
          </el-container>
      </el-container>
  </div>
</template>

<script>
  export default {
    name: "Main",
    data() {
      return {
      }
    },
    methods:{
      loginOut(){
        this.$confirm('确认退出？').then(_ => {
          this.doLoginout()
        }).catch(error => {console.error(error)})
      },
      doLoginout(){
        this.$api.login.loginout().then(res=>{
          if(res.code == '200'||res.code == '401'){
            this.$router.push("/login");
          }else{
            this.$message({message: '退出失败！'+res.msg,type: 'error'});
          }
        }).catch(error=>{
          console.error(error)
        })
      }
    },
    mounted(){
    },
  }
</script>

<style scoped lang="scss">
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    position: relative;
  }
  .el-header h1{
    text-align: center;
    font-size: 18px;
  }
  .el-header span{
    text-align: right;
    font-size: 12px;
    position: absolute;
    bottom: 10px;
    right: 20px;
  }

  .el-aside {
    color: #333;
  }
  .el-aside .el-menu{
    height: 100%;
  }
</style>
