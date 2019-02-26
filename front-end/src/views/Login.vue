<template>
    <div>
        <el-main class="maxWidth">
            <el-form ref="form" :model="form" label-width="80px">
                <el-input v-model="form.userName"
                          ref="userName"
                          placeholder="账号"
                          class="inputNoBorderBottomAndRadius"
                />
                <el-input v-model="form.password"
                          ref="password"
                          type="password"
                          placeholder="密码"
                          class="inputNoBorderTopRadius"
                />
                <el-button type="primary"
                           plain
                           @click="login"
                           class="marginTop10"
                           :loading="loading"
                >
                    登录
                </el-button>
            </el-form>
        </el-main>
    </div>
</template>

<script>
    import ElMain from '../element/packages/main/index.js';
    import ElForm from '../element/packages/form/index.js';
    import ElInput from '../element/packages/input/index.js';
    import ElButton from '../element/packages/button/index.js';

    export default {
        name: "login",

        components: {
            'el-main': ElMain,
            'el-form': ElForm,
            'el-input': ElInput,
            'el-button': ElButton,
        },

        mounted() {
            this.initInputBorder();
        },

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
            /**
             * 点击登录
             */
            login() {
                let form = this.form;
                if (!form.userName || form.userName.trim().length === 0) {
                    this.loginErrorShowMessage('请输入账号');
                    return;
                }
                if (!form.password || form.password.trim().length === 0) {
                    this.loginErrorShowMessage('请输入密码');
                    return;
                }
                // 请求
                // ~~~
                this.loading = true;
                setTimeout(() => {
                    this.loading = false;
                }, 500);

                //TODO 待
                if ('1' == '1')
                    return;

                fetch('')
                    .then((response) => {

                    });
            },

            /**
             * 显示通知
             * @param message
             */
            loginErrorShowMessage(message) {
                this.$notify({
                    title: '登录',
                    message: message,
                    type: 'error'
                });
            },

            /**
             * "账号"焦点变化时设置边框显示
             */
            initInputBorder() {
                let userNameRef = this.$refs.userName;
                let passwordRef = this.$refs.password;
                let userNameInputDom = userNameRef.$el.querySelector('input');
                let passwordInputDom = passwordRef.$el.querySelector('input');
                userNameRef.$on('focus', () => {
                    userNameInputDom.style.borderBottom = '';
                    passwordInputDom.style.borderTop = 'unset'
                });
                userNameRef.$on('blur', () => {
                    userNameInputDom.style.borderBottom = 'unset';
                    passwordInputDom.style.borderTop = '';
                });
                // 默认不显示"账号"的下边框
                userNameInputDom.style.borderBottom = 'unset';
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
        border-bottom-left-radius: unset;
        border-bottom-right-radius: unset;
    }
</style>

<style scoped>
    .marginTop10 {
        margin-top: 10px;
    }

    .maxWidth {
        display: inline-table;
        max-width: 350px;
    }
</style>

<style lang="sass" scoped>
    @import '../element/packages/theme-chalk/src/main.scss'
    @import '../element/packages/theme-chalk/src/form.scss'
    @import '../element/packages/theme-chalk/src/input.scss'
    @import '../element/packages/theme-chalk/src/button.scss'
</style>