<template>
  <div class="poet-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="诗人姓名">
          <el-input v-model="searchForm.name" placeholder="请输入诗人姓名" />
        </el-form-item>
        <el-form-item label="朝代">
          <el-select v-model="searchForm.dynastyId" placeholder="请选择朝代" clearable>
            <el-option label="唐朝" value="1" />
            <el-option label="宋朝" value="2" />
            <el-option label="元朝" value="3" />
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
            <el-button type="primary" @click="handleAdd">新增诗人</el-button>
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
        <el-table-column prop="name" label="诗人姓名" min-width="120" />
        <el-table-column prop="alias" label="别名" width="150" />
        <el-table-column prop="dynasty" label="朝代" width="100" />
        <el-table-column prop="birthYear" label="出生年份" width="100" />
        <el-table-column prop="deathYear" label="逝世年份" width="100" />
        <el-table-column prop="birthplace" label="出生地" width="150" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="likeCount" label="点赞数" width="100" />
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
      width="60%"
      @close="handleDialogClose"
    >
      <el-form
        ref="poetFormRef"
        :model="poetForm"
        :rules="poetRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="诗人姓名" prop="name">
              <el-input v-model="poetForm.name" placeholder="请输入诗人姓名" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="别名" prop="alias">
              <el-input v-model="poetForm.alias" placeholder="请输入别名/字号" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="朝代" prop="dynastyId">
              <el-select v-model="poetForm.dynastyId" placeholder="请选择朝代">
                <el-option label="唐朝" value="1" />
                <el-option label="宋朝" value="2" />
                <el-option label="元朝" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生年份" prop="birthYear">
              <el-input-number v-model="poetForm.birthYear" :min="0" :max="2025" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="逝世年份" prop="deathYear">
              <el-input-number v-model="poetForm.deathYear" :min="0" :max="2025" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出生地" prop="birthplace">
              <el-input v-model="poetForm.birthplace" placeholder="请输入出生地" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="生平简介" prop="biography">
          <el-input
            v-model="poetForm.biography"
            type="textarea"
            :rows="4"
            placeholder="请输入生平简介"
          />
        </el-form-item>
        
        <el-form-item label="主要成就" prop="achievements">
          <el-input
            v-model="poetForm.achievements"
            type="textarea"
            :rows="4"
            placeholder="请输入主要成就"
          />
        </el-form-item>
        
        <el-form-item label="代表作品" prop="representativeWorks">
          <el-input
            v-model="poetForm.representativeWorks"
            type="textarea"
            :rows="4"
            placeholder="请输入代表作品"
          />
        </el-form-item>
        
        <el-form-item label="头像URL" prop="avatar">
          <el-input v-model="poetForm.avatar" placeholder="请输入头像URL" />
        </el-form-item>
        
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="poetForm.status"
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  name: '',
  dynastyId: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    name: '李白',
    alias: '字太白，号青莲居士',
    dynasty: '唐朝',
    birthYear: 701,
    deathYear: 762,
    birthplace: '陇西成纪',
    viewCount: 2500,
    likeCount: 180,
    status: 1,
    createTime: '2025-09-01 10:00:00'
  },
  {
    id: 2,
    name: '杜甫',
    alias: '字子美，号少陵野老',
    dynasty: '唐朝',
    birthYear: 712,
    deathYear: 770,
    birthplace: '河南巩义',
    viewCount: 2300,
    likeCount: 165,
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

// 诗人表单
const poetFormRef = ref()
const poetForm = reactive({
  id: 0,
  name: '',
  alias: '',
  dynastyId: '',
  birthYear: undefined,
  deathYear: undefined,
  birthplace: '',
  biography: '',
  achievements: '',
  representativeWorks: '',
  avatar: '',
  status: 1
})

// 表单验证规则
const poetRules = {
  name: [
    { required: true, message: '请输入诗人姓名', trigger: 'blur' }
  ],
  dynastyId: [
    { required: true, message: '请选择朝代', trigger: 'change' }
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
    dynastyId: ''
  })
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增诗人'
  dialogVisible.value = true
  // 重置表单
  Object.assign(poetForm, {
    id: 0,
    name: '',
    alias: '',
    dynastyId: '',
    birthYear: undefined,
    deathYear: undefined,
    birthplace: '',
    biography: '',
    achievements: '',
    representativeWorks: '',
    avatar: '',
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑诗人'
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(poetForm, row)
}

// 查看
const handleView = (row: any) => {
  ElMessage.info('查看功能待实现')
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该诗人吗？', '提示', {
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
  if (!poetFormRef.value) return
  
  await poetFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (poetFormRef.value) {
    poetFormRef.value.resetFields()
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
.poet-list-container {
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