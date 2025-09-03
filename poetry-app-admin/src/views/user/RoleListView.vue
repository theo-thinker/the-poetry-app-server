<template>
  <div class="role-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="角色名称">
          <el-input v-model="searchForm.name" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="searchForm.code" placeholder="请输入角色编码" />
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
            <el-button type="primary" @click="handleAdd">新增角色</el-button>
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
        <el-table-column prop="name" label="角色名称" min-width="150" />
        <el-table-column prop="code" label="角色编码" width="150" />
        <el-table-column prop="description" label="描述" min-width="200" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="primary" link @click="handlePermission(row)">权限配置</el-button>
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
        ref="roleFormRef"
        :model="roleForm"
        :rules="roleRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="角色名称" prop="name">
              <el-input v-model="roleForm.name" placeholder="请输入角色名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="角色编码" prop="code">
              <el-input v-model="roleForm.code" placeholder="请输入角色编码" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="roleForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入描述"
          />
        </el-form-item>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="roleForm.sortOrder" :min="0" :max="999" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-switch
                v-model="roleForm.status"
                :active-value="1"
                :inactive-value="0"
                active-text="启用"
                inactive-text="禁用"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSave">保存</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 权限配置对话框 -->
    <el-dialog
      title="权限配置"
      v-model="permissionDialogVisible"
      width="60%"
    >
      <el-form label-width="80px">
        <el-form-item label="角色">
          {{ current_role.name }} ({{ current_role.code }})
        </el-form-item>
        <el-form-item label="权限">
          <el-tree
            ref="permissionTreeRef"
            :data="permissionTree"
            show-checkbox
            node-key="id"
            :props="{ label: 'name', children: 'children' }"
            default-expand-all
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="permissionDialogVisible = false">取消</el-button>
          <el-button type="primary" @click="handleSavePermissions">保存</el-button>
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
  name: '',
  code: '',
  status: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    name: '超级管理员',
    code: 'SUPER_ADMIN',
    description: '拥有系统所有权限的超级管理员',
    sortOrder: 1,
    status: 1,
    createTime: '2025-09-01 10:00:00'
  },
  {
    id: 2,
    name: '系统管理员',
    code: 'ADMIN',
    description: '系统管理员，拥有大部分系统管理权限',
    sortOrder: 2,
    status: 1,
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

// 权限对话框
const permissionDialogVisible = ref(false)
const current_role = ref<any>({})
const permissionTreeRef = ref()

// 权限树形数据
const permissionTree = ref([
  {
    id: 1,
    name: '系统管理',
    children: [
      {
        id: 101,
        name: '用户管理',
        children: [
          { id: 1011, name: '用户查询' },
          { id: 1012, name: '用户新增' },
          { id: 1013, name: '用户编辑' },
          { id: 1014, name: '用户删除' }
        ]
      },
      {
        id: 102,
        name: '角色管理',
        children: [
          { id: 1021, name: '角色查询' },
          { id: 1022, name: '角色新增' },
          { id: 1023, name: '角色编辑' },
          { id: 1024, name: '角色删除' }
        ]
      }
    ]
  },
  {
    id: 2,
    name: '内容管理',
    children: [
      {
        id: 201,
        name: '诗词管理',
        children: [
          { id: 2011, name: '诗词查询' },
          { id: 2012, name: '诗词新增' },
          { id: 2013, name: '诗词编辑' },
          { id: 2014, name: '诗词删除' }
        ]
      },
      {
        id: 202,
        name: '诗人管理',
        children: [
          { id: 2021, name: '诗人查询' },
          { id: 2022, name: '诗人新增' },
          { id: 2023, name: '诗人编辑' },
          { id: 2024, name: '诗人删除' }
        ]
      }
    ]
  }
])

// 角色表单
const roleFormRef = ref()
const roleForm = reactive({
  id: 0,
  name: '',
  code: '',
  description: '',
  sortOrder: 0,
  status: 1
})

// 表单验证规则
const roleRules = {
  name: [
    { required: true, message: '请输入角色名称', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入角色编码', trigger: 'blur' }
  ]
}

// 搜索
const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    name: '',
    code: '',
    status: ''
  })
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增角色'
  dialogVisible.value = true
  // 重置表单
  Object.assign(roleForm, {
    id: 0,
    name: '',
    code: '',
    description: '',
    sortOrder: 0,
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑角色'
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(roleForm, row)
}

// 权限配置
const handlePermission = (row: any) => {
  current_role.value = row
  permissionDialogVisible.value = true
  // 模拟设置默认选中权限
  setTimeout(() => {
    if (permissionTreeRef.value) {
      permissionTreeRef.value.setCheckedKeys([1011, 1012, 1021, 2011])
    }
  }, 100)
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该角色吗？', '提示', {
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

// 保存角色
const handleSave = async () => {
  if (!roleFormRef.value) return
  
  await roleFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 保存权限
const handleSavePermissions = () => {
  const checkedKeys = permissionTreeRef.value.getCheckedKeys()
  const halfCheckedKeys = permissionTreeRef.value.getHalfCheckedKeys()
  console.log('选中的权限:', checkedKeys)
  console.log('半选中的权限:', halfCheckedKeys)
  ElMessage.success('权限配置保存成功')
  permissionDialogVisible.value = false
}

// 对话框关闭
const handleDialogClose = () => {
  if (roleFormRef.value) {
    roleFormRef.value.resetFields()
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
.role-list-container {
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