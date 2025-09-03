export interface LoginRequest {
  username: string
  password: string
}

export interface LoginResponse {
  token?: string
  expireTime?: number
  userId?: number
  username: string
  nickname: string
}

export interface UserInfo {
  userId: number
  username: string
  nickname: string
  email: string
  phone: string
  avatar: string
  roles: string[]
  permissions: string[]
}
