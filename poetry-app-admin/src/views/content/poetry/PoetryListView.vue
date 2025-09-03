<template>
  <div class="poetry-list-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="诗词标题">
          <el-input v-model="searchForm.title" placeholder="请输入诗词标题" />
        </el-form-item>
        <el-form-item label="作者">
          <el-input v-model="searchForm.author" placeholder="请输入作者" />
        </el-form-item>
        <el-form-item label="朝代">
          <el-select v-model="searchForm.dynastyId" placeholder="请选择朝代" clearable>
            <el-option label="唐朝" value="1" />
            <el-option label="宋朝" value="2" />
            <el-option label="元朝" value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="分类">
          <el-select v-model="searchForm.categoryId" placeholder="请选择分类" clearable>
            <el-option label="诗" value="1" />
            <el-option label="词" value="2" />
            <el-option label="曲" value="3" />
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
            <el-button type="primary" @click="handleAdd">新增诗词</el-button>
            <el-button type="danger" @click="handleBatchDelete" :disabled="selectedRows.length === 0">批量删除</el-button>
          </div>
          <div class="header-right">
            <el-button @click="handleExport">导出</el-button>
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
        <el-table-column prop="title" label="诗词标题" min-width="150" />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="dynasty" label="朝代" width="100" />
        <el-table-column prop="category" label="分类" width="100" />
        <el-table-column prop="wordCount" label="字数" width="80" />
        <el-table-column prop="viewCount" label="浏览量" width="100" />
        <el-table-column prop="likeCount" label="点赞数" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '已发布' : '草稿' }}
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
        ref="poetryFormRef"
        :model="poetryForm"
        :rules="poetryRules"
        label-width="100px"
      >
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="诗词标题" prop="title">
              <el-input v-model="poetryForm.title" placeholder="请输入诗词标题" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="作者" prop="authorId">
              <el-select v-model="poetryForm.authorId" placeholder="请选择作者" filterable>
                <el-option label="李白" value="1" />
                <el-option label="杜甫" value="2" />
                <el-option label="白居易" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="朝代" prop="dynastyId">
              <el-select v-model="poetryForm.dynastyId" placeholder="请选择朝代">
                <el-option label="唐朝" value="1" />
                <el-option label="宋朝" value="2" />
                <el-option label="元朝" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类" prop="categoryId">
              <el-select v-model="poetryForm.categoryId" placeholder="请选择分类">
                <el-option label="诗" value="1" />
                <el-option label="词" value="2" />
                <el-option label="曲" value="3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="诗词内容" prop="content">
          <el-input
            v-model="poetryForm.content"
            type="textarea"
            :rows="6"
            placeholder="请输入诗词内容"
          />
        </el-form-item>
        
        <el-form-item label="译文" prop="translation">
          <el-input
            v-model="poetryForm.translation"
            type="textarea"
            :rows="4"
            placeholder="请输入译文"
          />
        </el-form-item>
        
        <el-form-item label="注释" prop="annotation">
          <el-input
            v-model="poetryForm.annotation"
            type="textarea"
            :rows="4"
            placeholder="请输入注释"
          />
        </el-form-item>
        
        <el-form-item label="赏析" prop="appreciation">
          <el-input
            v-model="poetryForm.appreciation"
            type="textarea"
            :rows="4"
            placeholder="请输入赏析"
          />
        </el-form-item>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="难度等级" prop="difficultyLevel">
              <el-rate v-model="poetryForm.difficultyLevel" :max="3" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-switch
                v-model="poetryForm.status"
                :active-value="1"
                :inactive-value="0"
                active-text="发布"
                inactive-text="草稿"
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
  title: '',
  author: '',
  dynastyId: '',
  categoryId: ''
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    title: '静夜思',
    author: '李白',
    dynasty: '唐朝',
    category: '诗',
    wordCount: 20,
    viewCount: 1200,
    likeCount: 85,
    status: 1,
    createTime: '2025-09-01 10:00:00'
  },
  {
    id: 2,
    title: '春晓',
    author: '孟浩然',
    dynasty: '唐朝',
    category: '诗',
    wordCount: 20,
    viewCount: 980,
    likeCount: 72,
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

// 诗词表单
const poetryFormRef = ref()
const poetryForm = reactive({
  id: 0,
  title: '',
  authorId: '',
  dynastyId: '',
  categoryId: '',
  content: '',
  translation: '',
  annotation: '',
  appreciation: '',
  difficultyLevel: 1,
  status: 1
})

// 表单验证规则
const poetryRules = {
  title: [
    { required: true, message: '请输入诗词标题', trigger: 'blur' }
  ],
  authorId: [
    { required: true, message: '请选择作者', trigger: 'change' }
  ],
  dynastyId: [
    { required: true, message: '请选择朝代', trigger: 'change' }
  ],
  categoryId: [
    { required: true, message: '请选择分类', trigger: 'change' }
  ],
  content: [
    { required: true, message: '请输入诗词内容', trigger: 'blur' }
  ]
}

// 搜索
const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    title: '',
    author: '',
    dynastyId: '',
    categoryId: ''
  })
}

// 新增
const handleAdd = () => {
  dialogTitle.value = '新增诗词'
  dialogVisible.value = true
  // 重置表单
  Object.assign(poetryForm, {
    id: 0,
    title: '',
    authorId: '',
    dynastyId: '',
    categoryId: '',
    content: '',
    translation: '',
    annotation: '',
    appreciation: '',
    difficultyLevel: 1,
    status: 1
  })
}

// 编辑
const handleEdit = (row: any) => {
  dialogTitle.value = '编辑诗词'
  dialogVisible.value = true
  // 填充表单数据
  Object.assign(poetryForm, row)
}

// 查看
const handleView = (row: any) => {
  ElMessage.info('查看功能待实现')
}

// 删除
const handleDelete = (row: any) => {
  ElMessageBox.confirm('确定要删除该诗词吗？', '提示', {
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

// 导出
const handleExport = () => {
  ElMessage.info('导出功能待实现')
}

// 保存
const handleSave = async () => {
  if (!poetryFormRef.value) return
  
  await poetryFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('保存成功')
      dialogVisible.value = false
    }
  })
}

// 对话框关闭
const handleDialogClose = () => {
  if (poetryFormRef.value) {
    poetryFormRef.value.resetFields()
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
.poetry-list-container {
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