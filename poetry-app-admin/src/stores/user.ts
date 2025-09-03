import { defineStore } from 'pinia'
import { loginApi, getUserInfoApi } from '@/api/auth'
import type { LoginRequest, UserInfo } from '@/types/auth'

interface UserState {
  token: string | null
  userInfo: UserInfo | null
}

export const useUserStore = defineStore('user', {
  state: (): UserState => ({
    token: localStorage.getItem('token') || null,
    userInfo: null,
  }),

  getters: {
    isLoggedIn: (state) => !!state.token,
    username: (state) => state.userInfo?.username || '',
    roles: (state) => state.userInfo?.roles || [],
  },

  actions: {
    // 登录
    async login(loginData: LoginRequest) {
      try {
        const response = await loginApi(loginData)
        const { token } = response.data

        this.token = token
        localStorage.setItem('token', token)

        // 获取用户信息
        await this.getUserInfo()

        return response
      } catch (error) {
        throw error
      }
    },

    // 获取用户信息
    async getUserInfo() {
      try {
        const response = await getUserInfoApi()
        this.userInfo = response.data
        return response
      } catch (error) {
        throw error
      }
    },

    // 登出
    logout() {
      this.token = null
      this.userInfo = null
      localStorage.removeItem('token')
    },

    // 设置token
    setToken(token: string) {
      this.token = token
      localStorage.setItem('token', token)
    },
  },
})
