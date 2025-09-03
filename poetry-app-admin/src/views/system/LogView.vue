<template>
  <div class="log-container">
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px" inline>
        <el-form-item label="操作人员">
          <el-input v-model="searchForm.username" placeholder="请输入操作人员" />
        </el-form-item>
        <el-form-item label="操作类型">
          <el-select v-model="searchForm.operation" placeholder="请选择操作类型" clearable>
            <el-option label="新增" value="add" />
            <el-option label="修改" value="update" />
            <el-option label="删除" value="delete" />
            <el-option label="查询" value="query" />
            <el-option label="登录" value="login" />
            <el-option label="登出" value="logout" />
          </el-select>
        </el-form-item>
        <el-form-item label="操作状态">
          <el-select v-model="searchForm.status" placeholder="请选择操作状态" clearable>
            <el-option label="成功" value="1" />
            <el-option label="失败" value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间">
          <el-date-picker
            v-model="searchForm.timeRange"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            format="YYYY-MM-DD HH:mm:ss"
          />
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
        <el-table-column prop="username" label="操作人员" width="120" />
        <el-table-column prop="operation" label="操作类型" width="120" />
        <el-table-column prop="method" label="请求方法" width="100" />
        <el-table-column prop="params" label="请求参数" min-width="200" />
        <el-table-column prop="time" label="执行时长(ms)" width="120" />
        <el-table-column prop="ip" label="IP地址" width="150" />
        <el-table-column prop="location" label="操作地点" width="150" />
        <el-table-column prop="status" label="操作状态" width="100">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '成功' : '失败' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="操作时间" width="180" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleView(row)">详情</el-button>
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

    <!-- 日志详情对话框 -->
    <el-dialog
      title="日志详情"
      v-model="detailDialogVisible"
      width="60%"
    >
      <el-form label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="ID">
              {{ current_log.id }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作人员">
              {{ current_log.username }}
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="操作类型">
              {{ current_log.operation }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="请求方法">
              {{ current_log.method }}
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="执行时长">
              {{ current_log.time }} ms
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作状态">
              <el-tag :type="current_log.status === 1 ? 'success' : 'danger'">
                {{ current_log.status === 1 ? '成功' : '失败' }}
              </el-tag>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="IP地址">
              {{ current_log.ip }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作地点">
              {{ current_log.location }}
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="操作时间">
          {{ current_log.createTime }}
        </el-form-item>
        
        <el-form-item label="请求参数">
          <el-input
            v-model="current_log.params"
            type="textarea"
            :rows="4"
            readonly
          />
        </el-form-item>
        
        <el-form-item label="User-Agent">
          <el-input
            v-model="current_log.userAgent"
            type="textarea"
            :rows="3"
            readonly
          />
        </el-form-item>
        
        <el-form-item v-if="current_log.status === 0" label="错误信息">
          <el-input
            v-model="current_log.errorMsg"
            type="textarea"
            :rows="3"
            readonly
          />
        </el-form-item>
      </el-form>
      
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="detailDialogVisible = false">关闭</el-button>
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
  operation: '',
  status: '',
  timeRange: []
})

// 表格数据
const tableData = ref([
  {
    id: 1,
    username: 'admin',
    operation: '登录',
    method: 'POST',
    params: '{"username":"admin","password":"******"}',
    time: 120,
    ip: '192.168.1.100',
    location: '本地',
    userAgent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36',
    status: 1,
    errorMsg: '',
    createTime: '2025-09-03 10:00:00'
  },
  {
    id: 2,
    username: 'admin',
    operation: '查询诗词',
    method: 'GET',
    params: '{"page":1,"size":10}',
    time: 85,
    ip: '192.168.1.100',
    location: '本地',
    userAgent: 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36',
    status: 1,
    errorMsg: '',
    createTime: '2025-09-03 10:05:00'
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

// 详情对话框
const detailDialogVisible = ref(false)
const current_log = ref<any>({})

// 搜索
const handleSearch = () => {
  ElMessage.info('搜索功能待实现')
}

// 重置
const handleReset = () => {
  Object.assign(searchForm, {
    username: '',
    operation: '',
    status: '',
    timeRange: []
  })
}

// 查看详情
const handleView = (row: any) => {
  current_log.value = row
  detailDialogVisible.value = true
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
.log-container {
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