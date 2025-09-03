<template>
  <div class="user-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="用户名">
          <el-input v-model="searchForm.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="searchForm.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="searchForm.status" placeholder="请选择状态" clearable>
            <el-option label="启用" value="1" />
            <el-option label="禁用" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <template #header>
        <div class="table-header">
          <div class="header-left">
            <el-button type="primary" @click="handleAdd">新增用户</el-button>
            <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
          </div>
        </div>
      </template>

      <el-table
        :data="tableData"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        border
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="username" label="用户名" min-width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="email" label="邮箱" width="150" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="roles" label="角色" width="150">
          <template #default="{ row }">
            <el-tag v-for="role in row.roles" :key="role" style="margin-right: 5px;">
              {{ role }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastLoginTime" label="最后登录时间" width="180" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="primary" link @click="handleResetPassword(row)">重置密码</el-button>
            <el-button type="primary" link @click="handleAssignRole(row)">分配角色</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-container">
        <el-pagination
          v-model:current-page="pagination.currentPage"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="50%"
      @close="handleDialogClose"
    >
      <el-form
        ref="userFormRef"
        :model="userForm"
        :rules="userRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="username">
              <el-input v-model="userForm.username" placeholder="请输入用户名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="昵称" prop="nickname">
              <el-input v-model="userForm.nickname" placeholder="请输入昵称" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userForm.email" placeholder="请输入邮箱" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号" prop="phone">
              <el-input v-model="userForm.phone" placeholder="请输入手机号" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item v-if="!isEdit" label="密码" prop="password">
          <el-input v-model="userForm.password" type="password" placeholder="请输入密码" show-password />
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="userForm.status"
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="禁用"
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 分配角色对话框 -->
    <el-dialog
      title="分配角色"
      v-model="roleDialogVisible"
      width="40%"
    >
      <el-form label-width="80px">
        <el-form-item label="用户">
          {{ current_user.username }} ({{ current_user.nickname }})
        </el-form-item>
        <el-form-item label="角色">
          <el-checkbox-group v-model="selectedRoles">
            <el-checkbox v-for="role in allRoles" :key="role.id" :label="role.id">
              {{ role.name }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="roleDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSaveRoles">保存</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  username: '',
  nickname: '',
  status: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    username: 'admin',
    nickname: '系统管理员',
    email: 'admin@poetry.com',
    phone: '13800138000',
    roles: ['超级管理员'],
    status: 1,
    lastLoginTime: '2025-09-03 10:00:00',
    createTime: '2025-09-01 10:00:00'
  },
  {
    id: 2,
    username: 'sakura',
    nickname: '樱花诗人',
    email: 'sakura@poetry.com',
    phone: '13800138001',
    roles: ['系统管理员'],
    status: 1,
    lastLoginTime: '2025-09-02 15:30:00',
    createTime: '2025-09-01 09:30:00'
  }
])

// 分页
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 选中行
const selectedRows = ref<any[]>([])

// 加载状态
const loading = ref(false)

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')
const isEdit = ref(false)

// 角色对话框
const roleDialogVisible = ref(false)
const current_user = ref<any>({})
const selectedRoles = ref<number[]>([])
const allRoles = ref([
  { id: 1, name: '超级管理员' },
  { id: 2, name: '系统管理员' },
  { id: 3, name: '内容管理员' },
  { id: 4, name: '普通用户' }
])

// 用户表单
const userFormRef = ref()
const userForm = reactive({
  id: 0,
  username: '',
  nickname: '',
  email: '',
  phone: '',
  password: '',
  status: 1
})

// 表单验证规则
const userRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

// 搜索
const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    username: '',
    nickname: '',
    status: ''
  })
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增用户'
  isEdit.value = false
  dialogVisible.value = true
  // 重置表单
  Object.assign(userForm, {
    id: 0,
    username: '',
    nickname: '',
    email: '',
    phone: '',
    password: '',
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑用户'
  isEdit.value = true
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(userForm, row)
}

// 重置密码
const handleResetPassword = (row: any) => {
  ElMessageBox.prompt('请输入新密码', '重置密码', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    inputPattern: /^.{6,20}$/,
    inputErrorMessage: '密码长度应在6-20个字符之间'
  }).then(({ value }) => {
    ElMessage.success('密码重置成功')
  }).catch(() => {
    // 取消操作
  })
}

// 分配角色
const handleAssignRole = (row: any) => {
  current_user.value = row
  // 模拟获取用户已有角色
  selectedRoles.value = [1, 2]
  roleDialogVisible.value = true
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该用户吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 批量删除
const handleBatchDelete = () => {
  ElMessageBox.confirm(`确定要删除选中的 ${selectedRows.value.length} 项吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    ElMessage.success('批量删除成功')
  }).catch(() => {
    // 取消删除
  })
}

// 保存用户
const handleSave = async () => {
  if (!userFormRef.value) return
  
  await userFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 保存角色
const handleSaveRoles = () => {
  ElMessage.success('角色分配成功')
  roleDialogVisible.value = false
}

// 对话框关闭
const handleDialogClose = () => {
  if (userFormRef.value) {
    userFormRef.value.resetFields()
  }
}

// 选择行变化
const handleSelectionChange = (rows: any[]) => {
  selectedRows.value = rows
}

// 分页大小改变
const handleSizeChange = (val: number) => {
  pagination.pageSize = val
  // 重新加载数据
}

// 当前页改变
const handleCurrentChange = (val: number) => {
  pagination.currentPage = val
  // 重新加载数据
}

// 初始化
onMounted(() => {
  // 加载数据
})
</script>

<style scoped>
.user-list-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>