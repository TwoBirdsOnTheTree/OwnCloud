<template>
    <div>
        <el-main class="maxWidth">
            <el-form ref="form" :model="form" label-width="80px">
                <el-input v-model="form.userName"
                          placeholder="账号"
                          :class="'inputNoBorderBottomAndRadius'"
                />
                <el-input v-model="form.password"
                          type="password"
                          placeholder="密码"
                          :class="'inputNoBorderTopRadius'"
                />
                <el-button type="primary"
                           plain
                           @click="login"
                           :class="'marginTop50'"
                           :loading="loading"
                >
                    登录
                </el-button>
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
                },
                loading: false
            }
        },

        methods: {
            login() {
                let form = this.form;
                if (!form.userName || form.userName.trim().length == 0) {
                    this.loginErrorShowMessage('请输入账号');
                    return;
                }
                if (!form.password || form.password.trim().length == 0) {
                    this.loginErrorShowMessage('请输入密码');
                    return;
                }
                // 请求
                // ~~~
                this.loading = true;
                setTimeout(() => {
                    this.loading = false;
                }, 500);
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

<style>
    .inputNoBorderTopRadius input {
        border-top-left-radius: unset;
        border-top-right-radius: unset;
    }

    .inputNoBorderBottomAndRadius input {
        border-bottom: none;
        border-bottom-left-radius: unset;
        border-bottom-right-radius: unset;
    }
</style>

<style scoped>
    .marginTop50 {
        margin-top: 10px;
    }

    .maxWidth {
        display: inline-table;
        max-width: 350px;
    }
</style>