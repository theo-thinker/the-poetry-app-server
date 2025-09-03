import request from '@/utils/request'
import type { LoginRequest, LoginResponse, UserInfo } from '@/types/auth'

// 登录
export function loginApi(data: LoginRequest) {
  return request<LoginResponse>({
    url: '/api/auth/login',
    method: 'post',
    data,
  })
}

// 获取用户信息
export function getUserInfoApi() {
  return request<UserInfo>({
    url: '/api/auth/info',
    method: 'get',
  })
}

// 登出
export function logoutApi() {
  return request({
    url: '/api/auth/logout',
    method: 'post',
  })
}
