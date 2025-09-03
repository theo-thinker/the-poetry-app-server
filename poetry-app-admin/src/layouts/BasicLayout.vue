<template>
  <el-container class="layout-container">
    <!-- 侧边栏 -->
    <el-aside width="200px" class="sidebar">
      <div class="logo">
        <img src="@/assets/logo.svg" alt="Logo" />
        <span>诗词管理系统</span>
      </div>
      <el-menu
        :default-active="activeMenu"
        class="sidebar-menu"
        background-color="#304156"
        text-color="#bfcbd9"
        active-text-color="#409eff"
        router
        :collapse="isCollapse"
      >
        <el-menu-item index="/dashboard">
          <el-icon><Odometer /></el-icon>
          <span>仪表盘</span>
        </el-menu-item>
        <el-sub-menu index="content">
          <template #title>
            <el-icon><Document /></el-icon>
            <span>内容管理</span>
          </template>
          <el-menu-item index="/poetry/list">诗词管理</el-menu-item>
          <el-menu-item index="/poet/list">诗人管理</el-menu-item>
          <el-menu-item index="/dynasty/list">朝代管理</el-menu-item>
          <el-menu-item index="/category/list">分类管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="user">
          <template #title>
            <el-icon><User /></el-icon>
            <span>用户管理</span>
          </template>
          <el-menu-item index="/user/list">用户列表</el-menu-item>
          <el-menu-item index="/role/list">角色管理</el-menu-item>
        </el-sub-menu>
        <el-sub-menu index="system">
          <template #title>
            <el-icon><Setting /></el-icon>
            <span>系统管理</span>
          </template>
          <el-menu-item index="/system/config">系统配置</el-menu-item>
          <el-menu-item index="/system/log">操作日志</el-menu-item>
        </el-sub-menu>
      </el-menu>
    </el-aside>

    <!-- 主体区域 -->
    <el-container>
      <!-- 头部 -->
      <el-header class="header">
        <div class="header-left">
          <el-button @click="toggleCollapse" class="collapse-btn" type="primary" link>
            <el-icon><Expand v-if="isCollapse" /><Fold v-else /></el-icon>
          </el-button>
        </div>
        <div class="header-right">
          <el-dropdown @command="handleCommand">
            <span class="user-info">
              <el-avatar
                :size="30"
                src="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png"
              />
              <span class="username">{{ username }}</span>
            </span>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">个人中心</el-dropdown-item>
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </el-header>

      <!-- 主要内容 -->
      <el-main class="main-content">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/user'
import { Odometer, Document, User, Setting, Expand, Fold } from '@element-plus/icons-vue'

const route = useRoute()
const router = useRouter()
const userStore = useUserStore()

const isCollapse = ref(false)
const username = computed(() => userStore.userInfo?.username || '管理员')

const activeMenu = computed(() => {
  const { meta, path } = route
  // if set path, the sidebar will highlight the path you set
  if (meta.activeMenu) {
    return meta.activeMenu as string
  }
  return path
})

const toggleCollapse = () => {
  isCollapse.value = !isCollapse.value
}

const handleCommand = (command: string) => {
  if (command === 'logout') {
    userStore.logout()
    router.push('/login')
  } else if (command === 'profile') {
    router.push('/profile')
  }
}
</script>

<style scoped lang="scss">
.layout-container {
  height: 100vh;
}

.sidebar {
  background-color: #304156;
  transition: width 0.3s;
}

.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  background-color: #2d3d4d;
  color: white;
  font-size: 16px;
  font-weight: bold;
}

.logo img {
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.sidebar-menu {
  border-right: none;
  height: calc(100% - 60px);
}

.sidebar-menu :deep(.el-menu-item),
.sidebar-menu :deep(.el-sub-menu__title) {
  height: 50px;
  line-height: 50px;
}

.sidebar-menu :deep(.el-menu-item.is-active) {
  background-color: #1f2d3d !important;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ffffff;
  border-bottom: 1px solid #dcdfe6;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.header-left {
  display: flex;
  align-items: center;
}

.collapse-btn {
  font-size: 20px;
  margin-right: 20px;
}

.user-info {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.username {
  margin-left: 10px;
  font-size: 14px;
  color: #606266;
}

.main-content {
  background-color: #f0f2f5;
  padding: 20px;
}
</style>
