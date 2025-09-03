<template>
  <div class="login-container">
    <div class="login-form">
      <div class="login-header">
        <div class="login-logo-placeholder">
          <svg width="60" height="60" viewBox="0 0 60 60" xmlns="http://www.w3.org/2000/svg">
            <circle cx="30" cy="25" r="15" fill="#409EFF"></circle>
            <path d="M30 45 L15 60 L45 60 Z" fill="#409EFF"></path>
          </svg>
        </div>
        <h2>诗词管理系统</h2>
        <p>Enterprise Poetry Management System</p>
      </div>

      <el-form
        ref="loginFormRef"
        :model="loginForm"
        :rules="loginRules"
        class="login-form-content"
        @submit.prevent="handleLogin"
      >
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            placeholder="请输入用户名"
            type="text"
            tabindex="1"
            size="large"
            clearable
          >
            <template #prefix>
              <el-icon><User></User></el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            :type="passwordVisible ? 'text' : 'password'"
            placeholder="请输入密码"
            tabindex="2"
            size="large"
            @keyup.enter="handleLogin"
          >
            <template #prefix>
              <el-icon><Lock></Lock></el-icon>
            </template>
            <template #suffix>
              <el-icon @click="togglePasswordVisible">
                <View v-if="passwordVisible"></View>
                <Hide v-else></Hide>
              </el-icon>
            </template>
          </el-input>
        </el-form-item>

        <el-form-item>
          <el-button
            :loading="loading"
            type="primary"
            size="large"
            class="login-button"
            @click="handleLogin"
            native-type="submit"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, View, Hide } from '@element-plus/icons-vue'
import type { FormInstance } from 'element-plus'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)
const passwordVisible = ref(false)

const loginForm = reactive({
  username: 'admin',
  password: '123456',
})

const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度应在3-20个字符之间', trigger: 'blur' },
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度应在6-20个字符之间', trigger: 'blur' },
  ],
}

const togglePasswordVisible = () => {
  passwordVisible.value = !passwordVisible.value
}

const handleLogin = async () => {
  if (!loginFormRef.value) return

  await loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await userStore.login({
          username: loginForm.username,
          password: loginForm.password,
        })
        ElMessage.success('登录成功')
        await router.push('/')
      } catch (error: any) {
        ElMessage.error(error.message || '登录失败')
      } finally {
        loading.value = false
      }
    }
  })
}
</script>

<style scoped lang="scss">
.login-container {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-form {
  width: 400px;
  padding: 40px;
  background: rgba(255, 255, 255, 0.95);
  border-radius: 10px;
  box-shadow: 0 15px 35px rgba(0, 0, 0, 0.1);
}

.login-header {
  text-align: center;
  margin-bottom: 30px;
}

.login-logo {
  width: 60px;
  height: 60px;
  margin-bottom: 15px;
}

.login-header h2 {
  margin: 0 0 10px 0;
  font-size: 24px;
  color: #333;
}

.login-header p {
  margin: 0;
  color: #666;
  font-size: 14px;
}

.login-form-content {
  margin-top: 30px;
}

.login-button {
  width: 100%;
  margin-top: 20px;
}
</style>
