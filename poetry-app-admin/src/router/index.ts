import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

// 布局组件
const BasicLayout = () => import('@/layouts/BasicLayout.vue')
const LoginView = () => import('@/views/login/LoginView.vue')

// 仪表盘
const DashboardView = () => import('@/views/dashboard/DashboardView.vue')

// 内容管理
const PoetryListView = () => import('@/views/content/poetry/PoetryListView.vue')
const PoetListView = () => import('@/views/content/poet/PoetListView.vue')
const DynastyListView = () => import('@/views/content/dynasty/DynastyListView.vue')
const CategoryListView = () => import('@/views/content/category/CategoryListView.vue')

// 用户管理
const UserListView = () => import('@/views/user/UserListView.vue')
const RoleListView = () => import('@/views/user/RoleListView.vue')

// 系统管理
const SystemConfigView = () => import('@/views/system/ConfigView.vue')
const SystemLogView = () => import('@/views/system/LogView.vue')

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: LoginView,
    meta: { title: '登录' },
  },
  {
    path: '/',
    component: BasicLayout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: DashboardView,
        meta: { title: '仪表盘', icon: 'odometer' },
      },
      {
        path: '/poetry/list',
        name: 'PoetryList',
        component: PoetryListView,
        meta: { title: '诗词管理', icon: 'document' },
      },
      {
        path: '/poet/list',
        name: 'PoetList',
        component: PoetListView,
        meta: { title: '诗人管理', icon: 'user' },
      },
      {
        path: '/dynasty/list',
        name: 'DynastyList',
        component: DynastyListView,
        meta: { title: '朝代管理', icon: 'calendar' },
      },
      {
        path: '/category/list',
        name: 'CategoryList',
        component: CategoryListView,
        meta: { title: '分类管理', icon: 'folder' },
      },
      {
        path: '/user/list',
        name: 'UserList',
        component: UserListView,
        meta: { title: '用户列表', icon: 'user' },
      },
      {
        path: '/role/list',
        name: 'RoleList',
        component: RoleListView,
        meta: { title: '角色管理', icon: 'lock' },
      },
      {
        path: '/system/config',
        name: 'SystemConfig',
        component: SystemConfigView,
        meta: { title: '系统配置', icon: 'setting' },
      },
      {
        path: '/system/log',
        name: 'SystemLog',
        component: SystemLogView,
        meta: { title: '操作日志', icon: 'document' },
      },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

// 路由守卫
router.beforeEach((to, from, next) => {
  const token = localStorage.getItem('token')

  // 如果访问登录页且已登录，直接跳转到首页
  if (to.path === '/login' && token) {
    next('/')
    return
  }

  // 如果访问其他页面但未登录，跳转到登录页
  if (to.path !== '/login' && !token) {
    next('/login')
    return
  }

  next()
})

export default router
