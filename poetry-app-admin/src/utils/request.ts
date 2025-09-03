import axios, { type AxiosRequestConfig, type AxiosResponse, type AxiosError } from 'axios'
import { ElMessage } from 'element-plus'
import { useUserStore } from '@/stores/user'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8080', // 后端API地址
  timeout: 15000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    const userStore = useUserStore()
    if (userStore.token) {
      config.headers.Authorization = `Bearer ${userStore.token}`
    }
    return config
  },
  (error) => {
    console.error('请求错误:', error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    const { code, message } = response.data
    
    // 如果有自定义code
    if (code !== undefined) {
      switch (code) {
        case 200:
          return response.data
        case 401:
          // 未授权，跳转到登录页
          ElMessage.error('登录已过期，请重新登录')
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          return Promise.reject(new Error(message || '登录已过期'))
        case 403:
          ElMessage.error('权限不足')
          return Promise.reject(new Error(message || '权限不足'))
        case 500:
          ElMessage.error('服务器内部错误')
          return Promise.reject(new Error(message || '服务器内部错误'))
        default:
          ElMessage.error(message || '请求失败')
          return Promise.reject(new Error(message || '请求失败'))
      }
    }
    
    return response.data
  },
  (error: AxiosError) => {
    console.error('响应错误:', error)
    
    if (error.response) {
      const { status, data } = error.response
      switch (status) {
        case 400:
          ElMessage.error('请求参数错误')
          break
        case 401:
          ElMessage.error('未授权，请重新登录')
          const userStore = useUserStore()
          userStore.logout()
          window.location.href = '/login'
          break
        case 403:
          ElMessage.error('权限不足')
          break
        case 404:
          ElMessage.error('请求资源不存在')
          break
        case 500:
          ElMessage.error('服务器内部错误')
          break
        default:
          ElMessage.error('请求失败')
      }
    } else if (error.request) {
      ElMessage.error('网络错误，请检查网络连接')
    } else {
      ElMessage.error('请求失败')
    }
    
    return Promise.reject(error)
  }
)

// 定义请求方法类型
interface RequestConfig<T = any> extends AxiosRequestConfig {
  data?: T
}

// 封装请求方法
const request = <T = any>(config: RequestConfig<T>): Promise<AxiosResponse<T>> => {
  return service(config) as unknown as Promise<AxiosResponse<T>>
}

export default request