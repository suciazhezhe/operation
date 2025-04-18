<template>
    <div>
        设备信息
    </div>
</template>

<script>
    export default {
        name: "DeviceInfo",
        data() {
            return {
                
            }
        },
        methods:{
            getDeviceInfo(){
                this.$api.device.testStart().then(res=>{
                    console.log(res)
                }).catch(error=>{
                    console.error(error)
                })
            }
        },
        mounted(){
            this.getDeviceInfo()
        }
    }

</script>

<style scoped>

</style>
