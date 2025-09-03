<template>
  <div class="category-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="分类名称">
          <el-input v-model="searchForm.name" placeholder="请输入分类名称" />
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
            <el-button type="primary" @click="handleAdd">新增分类</el-button>
            <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
          </div>
        </div>
      </template>

      <el-table
        :data="tableData"
        v-loading="loading"
        @selection-change="handleSelectionChange"
        border
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="name" label="分类名称" min-width="150" />
        <el-table-column prop="code" label="编码" width="120" />
        <el-table-column prop="level" label="层级" width="80" />
        <el-table-column prop="sortOrder" label="排序" width="80" />
        <el-table-column prop="description" label="描述" min-width="200" />
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
            <el-button type="primary" link @click="handleAddChild(row)">添加子类</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="50%"
      @close="handleDialogClose"
    >
      <el-form
        ref="categoryFormRef"
        :model="categoryForm"
        :rules="categoryRules"
        label-width="100px"
      >
        <el-form-item label="父级分类" prop="parentId">
          <el-tree-select
            v-model="categoryForm.parentId"
            :data="categoryTree"
            node-key="id"
            :props="{ label: 'name', children: 'children' }"
            check-strictly
            clearable
            placeholder="请选择父级分类"
            style="width: 100%"
          />
        </el-form-item>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类名称" prop="name">
              <el-input v-model="categoryForm.name" placeholder="请输入分类名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编码" prop="code">
              <el-input v-model="categoryForm.code" placeholder="请输入编码" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="categoryForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入描述"
          />
        </el-form-item>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="categoryForm.sortOrder" :min="0" :max="999" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-switch
                v-model="categoryForm.status"
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  name: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    name: '诗',
    code: 'SHI',
    parentId: 0,
    level: 1,
    sortOrder: 1,
    description: '古代诗歌总类',
    status: 1,
    createTime: '2025-09-01 10:00:00',
    children: [
      {
        id: 5,
        name: '五言绝句',
        code: 'WYJJ',
        parentId: 1,
        level: 2,
        sortOrder: 1,
        description: '五言四句诗',
        status: 1,
        createTime: '2025-09-01 10:05:00'
      },
      {
        id: 6,
        name: '七言绝句',
        code: 'QYJJ',
        parentId: 1,
        level: 2,
        sortOrder: 2,
        description: '七言四句诗',
        status: 1,
        createTime: '2025-09-01 10:06:00'
      }
    ]
  },
  {
    id: 2,
    name: '词',
    code: 'CI',
    parentId: 0,
    level: 1,
    sortOrder: 2,
    description: '古代词作总类',
    status: 1,
    createTime: '2025-09-01 09:30:00'
  }
])

// 分类树形数据
const categoryTree = ref([
  {
    id: 0,
    name: '顶级分类',
    children: [
      {
        id: 1,
        name: '诗',
        children: [
          {
            id: 5,
            name: '五言绝句'
          },
          {
            id: 6,
            name: '七言绝句'
          }
        ]
      },
      {
        id: 2,
        name: '词'
      }
    ]
  }
])

// 加载状态
const loading = ref(false)

// 对话框
const dialogVisible = ref(false)
const dialogTitle = ref('')

// 选中行
const selectedRows = ref<any[]>([])

// 分类表单
const categoryFormRef = ref()
const categoryForm = reactive({
  id: 0,
  parentId: 0,
  name: '',
  code: '',
  description: '',
  sortOrder: 0,
  status: 1
})

// 表单验证规则
const categoryRules = {
  name: [
    { required: true, message: '请输入分类名称', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入编码', trigger: 'blur' }
  ]
}

// 搜索
const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    name: ''
  })
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增分类'
  dialogVisible.value = true
  // 重置表单
  Object.assign(categoryForm, {
    id: 0,
    parentId: 0,
    name: '',
    code: '',
    description: '',
    sortOrder: 0,
    status: 1
  })
}

// 添加子类
const handleAddChild = (row: any) => {
  dialogTitle.value = '新增子分类'
  dialogVisible.value = true
  // 重置表单
  Object.assign(categoryForm, {
    id: 0,
    parentId: row.id,
    name: '',
    code: '',
    description: '',
    sortOrder: 0,
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑分类'
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(categoryForm, row)
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该分类吗？', '提示', {
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

// 保存
const handleSave = async () => {
  if (!categoryFormRef.value) return
  
  await categoryFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (categoryFormRef.value) {
    categoryFormRef.value.resetFields()
  }
}

// 选择行变化
const handleSelectionChange = (rows: any[]) => {
  selectedRows.value = rows
}

// 初始化
onMounted(() => {
  // 加载数据
})
</script>

<style scoped>
.category-list-container {
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
</style>