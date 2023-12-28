<template>
  <div class="login">
    <el-form class="form" :model="form" :rules="rules" ref="loginForm">
      <h1 class="title">用户登录</h1>
      <el-form-item prop="userName">
        <el-input
          class="text"
          v-model="form.userName"
          prefix-icon="User"
          clearable
          placeholder="username"
        />
      </el-form-item>
      <el-form-item prop="password">
        <el-input
          class="text"
          v-model="form.password"
          prefix-icon="Lock"
          show-password
          clearable
          :placeholder="pwdholder"
        />
      </el-form-item>
      <div class="mb-2 flex items-center text-sm" >
        <el-radio-group v-model="userType" class="ml-4" v-if="false">
          <el-radio label="1" size="large">admin</el-radio>
          <el-radio label="2" size="large">hr</el-radio>
          <el-radio label="3" size="large">department</el-radio>
        </el-radio-group>
      </div>
      <el-form-item>
        <el-button
          :loading="loading"
          type="primary"
          class="btn"
          size="large"
          @click="normalLogin"
        >
          {{ btnText }}
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
// import {User} from '@element-plus/icons-vue'
import {computed} from "vue";
// import {Login} from "../api/login.js" 同名冲突
import axios, {HttpStatusCode} from "axios";
import store from "../store/store.js";

export default {
  name: "Login",
  data() {
    return {
      userType: '1',
      form: {
        userName: '222222@qq.com',
        password: '111111',
      },
      loading: false,
      btnText: computed(() =>
        this.loading ? '登陆中' : '登录'
      ),
      rules: {
        userName: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          },
        ],
        password: [
          {required: true, message: "请输入密码", trigger: 'blur'},
          {min: 6, max: 12, message: "长度是6-12位", trigger: 'blur',},
        ]
      }
    }
  },
  methods: {
    logMessage(message) {
      console.log(message);
    },
    normalLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          axios.post('http://localhost:8080/userLogin', {
            email: this.form.userName,
            pwd: this.form.password,
          }).then(response => {
            if (response.status === HttpStatusCode.Ok) {
              localStorage.setItem('isLoggedIn', 'true')
              localStorage.setItem('userinfo',JSON.stringify(response.data) )
              switch (response.data.departmentId) {
                case 1:
                  this.$router.push('/admin')
                  break
                // case 2:
                //   this.$router.push('/hr')
                //   break
                default:
                  this.$router.push('/recorder')
                  break
              }
            } else {
              this.$message.error("login failed:" + response.status)
            }
          }).catch(error => {
            if (error.response.status === HttpStatusCode.Unauthorized) {
              this.$message.error('用户名或密码错误');
            }
          }).finally(() => {
            this.loading = false
          })
        } else {
          this.$message.error('请正确填写用户名和密码！');
        }
      })
    }
    ,
    submit() {
      // const { code, data, message } =  Login(this.form)

      this.$refs.loginForm.validate(valid => {
        if (valid) {
          // 验证通过，执行登录请求
          this.loading = true;
          axios.post('http://localhost:8080/adminLogin', {
            email: this.form.userName,
            pwd: this.form.password,
          }).then(response => {
            console.log(response.data.msg);
            if (response.data.msg === 'Success') {
              localStorage.setItem('isLoggedIn', 'true')
              this.$router.push('/')
              console.log('Success')
            } else {
              this.$message.error("用户名或密码错误")
            }
          }).catch(error => {
            if (error.response.data.code === 401) {
              this.$message.info("username or password error")
            }
          }).finally(() => {
            this.loading = false;
          });
        } else {
          // 验证未通过，显示错误提示
          this.$message.error('请正确填写用户名和密码！');
        }
      });
      // console.log(code, data, message)
    }
    ,
  },
  computed: {
    pwdholder() {
      return 'password(' + this.form.userName + ')';
    },
  }
}

</script>
<style lang="scss" scoped>
.login {
  transition: transform 1s;
  transform: scale(1);
  width: 100%;
  height: 100%;
  overflow: hidden;
  background: #2d3a4b;

  .form {
    width: 520px;
    max-width: 100%;
    padding: 0 24px;
    box-sizing: border-box;
    margin: 160px auto 0;

    :deep {
      .el-input__wrapper {
        box-shadow: 0 0 0 1px rgba(255, 255, 255, 0.1) inset;
        background: rgba(0, 0, 0, 0.1);
      }

      .el-input-group--append > .el-input__wrapper {
        border-top-right-radius: 0;
        border-bottom-right-radius: 0;
      }

      .el-input-group--prepend > .el-input__wrapper {
        border-top-left-radius: 0;
        border-bottom-left-radius: 0;
      }
    }

    .title {
      color: #fff;
      text-align: center;
      font-size: 24px;
      margin: 0 0 24px;
    }

    .text {
      font-size: 16px;

      :deep(.el-input__inner) {
        color: #fff;
        height: 48px;
        line-height: 48px;

        &::placeholder {
          color: rgba(255, 255, 255, 0.2);
        }
      }
    }

    .btn {
      width: 100%;
    }
  }
}

.change-lang {
  position: fixed;
  right: 20px;
  top: 20px;

  :deep {
    .change-lang {
      height: 24px;

      &:hover {
        background: none;
      }

      .icon {
        color: #fff;
      }
    }
  }
}
</style>