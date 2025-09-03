<template>
  <div class="dynasty-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="朝代名称">
          <el-input v-model="searchForm.name" placeholder="请输入朝代名称" />
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
            <el-button type="primary" @click="handleAdd">新增朝代</el-button>
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
        <el-table-column prop="name" label="朝代名称" min-width="120" />
        <el-table-column prop="code" label="编码" width="100" />
        <el-table-column prop="startYear" label="开始年份" width="100" />
        <el-table-column prop="endYear" label="结束年份" width="100" />
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
            <el-button type="primary" link @click="handleView(row)">查看</el-button>
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
        ref="dynastyFormRef"
        :model="dynastyForm"
        :rules="dynastyRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="朝代名称" prop="name">
              <el-input v-model="dynastyForm.name" placeholder="请输入朝代名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="编码" prop="code">
              <el-input v-model="dynastyForm.code" placeholder="请输入编码" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="开始年份" prop="startYear">
              <el-input-number v-model="dynastyForm.startYear" :min="-5000" :max="2025" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="结束年份" prop="endYear">
              <el-input-number v-model="dynastyForm.endYear" :min="-5000" :max="2025" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="描述" prop="description">
          <el-input
            v-model="dynastyForm.description"
            type="textarea"
            :rows="4"
            placeholder="请输入描述"
          />
        </el-form-item>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序" prop="sortOrder">
              <el-input-number v-model="dynastyForm.sortOrder" :min="0" :max="999" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-switch
                v-model="dynastyForm.status"
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
    name: '唐朝',
    code: 'TANG',
    startYear: 618,
    endYear: 907,
    description: '中国诗歌的黄金时代',
    sortOrder: 1,
    status: 1,
    createTime: '2025-09-01 10:00:00'
  },
  {
    id: 2,
    name: '宋朝',
    code: 'SONG',
    startYear: 960,
    endYear: 1279,
    description: '词的全盛时期',
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

// 朝代表单
const dynastyFormRef = ref()
const dynastyForm = reactive({
  id: 0,
  name: '',
  code: '',
  startYear: undefined,
  endYear: undefined,
  description: '',
  sortOrder: 0,
  status: 1
})

// 表单验证规则
const dynastyRules = {
  name: [
    { required: true, message: '请输入朝代名称', trigger: 'blur' }
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
  dialogTitle.value = '新增朝代'
  dialogVisible.value = true
  // 重置表单
  Object.assign(dynastyForm, {
    id: 0,
    name: '',
    code: '',
    startYear: undefined,
    endYear: undefined,
    description: '',
    sortOrder: 0,
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑朝代'
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(dynastyForm, row)
}

// 查看
const handleView = (row: any) => {
  ElMessage.info('查看功能待实现')
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该朝代吗？', '提示', {
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
  if (!dynastyFormRef.value) return
  
  await dynastyFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (dynastyFormRef.value) {
    dynastyFormRef.value.resetFields()
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
.dynasty-list-container {
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