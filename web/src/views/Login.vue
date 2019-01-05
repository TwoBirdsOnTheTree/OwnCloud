<template>
    <div>
        <el-main>
            <el-form ref="form" :model="form" label-width="80px">
                <el-input v-model="form.userName" placeholder="用户名"></el-input>
                <el-input v-model="form.password" type="password" placeholder="密码"></el-input>
                <el-button type="primary" @click="login">登录</el-button>
            </el-form>
        </el-main>
    </div>
</template>

<script>
    export default {
        name: "login",
        data() {
            return {
                form: {
                    userName: '',
                    password: ''
                }
            }
        },

        methods: {
            login() {
                let form = this.form;
                if (!form.userName || form.userName.trim().length == 0) {
                    this.loginErrorShowMessage('请输入用户名');
                    return;
                }
                if (!form.password || form.password.trim().length == 0) {
                    this.loginErrorShowMessage('请输入密码');
                    return;
                }
                // 请求
                // ~~~
            },

            loginErrorShowMessage(message) {
                this.$notify({
                    title: '登录',
                    message: message,
                    type: 'error'
                });
            }
        }
    }
</script>

<style scoped>

</style>