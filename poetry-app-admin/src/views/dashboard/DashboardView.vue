<template>
  <div class="dashboard-container">
    <el-row :gutter="20" class="dashboard-header">
      <el-col :span="6">
        <el-card class="dashboard-card">
          <div class="card-content">
            <div class="card-icon">
              <el-icon><Document /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">诗词总数</div>
              <div class="card-number">{{ statistics.poetryCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <div class="card-content">
            <div class="card-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">用户总数</div>
              <div class="card-number">{{ statistics.userCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <div class="card-content">
            <div class="card-icon">
              <el-icon><View /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">今日访问</div>
              <div class="card-number">{{ statistics.todayVisits }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="dashboard-card">
          <div class="card-content">
            <div class="card-icon">
              <el-icon><Star /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">收藏次数</div>
              <div class="card-number">{{ statistics.collectionCount }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="dashboard-content">
      <el-col :span="16">
        <el-card class="chart-card">
          <template #header>
            <div class="card-header">
              <span>访问统计</span>
            </div>
          </template>
          <div class="chart-container">
            <div ref="visitChartRef" class="chart"></div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="recent-card">
          <template #header>
            <div class="card-header">
              <span>最新诗词</span>
            </div>
          </template>
          <div class="recent-list">
            <div 
              v-for="poetry in recentPoetry" 
              :key="poetry.id" 
              class="recent-item"
              @click="viewPoetry(poetry.id)"
            >
              <div class="item-title">{{ poetry.title }}</div>
              <div class="item-author">{{ poetry.author }}</div>
              <div class="item-time">{{ poetry.createTime }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { Document, User, View, Star } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

// 统计数据
const statistics = ref({
  poetryCount: 1280,
  userCount: 245,
  todayVisits: 120,
  collectionCount: 890
})

// 最新诗词
const recentPoetry = ref([
  { id: 1, title: '静夜思', author: '李白', createTime: '2025-09-01' },
  { id: 2, title: '春晓', author: '孟浩然', createTime: '2025-09-01' },
  { id: 3, title: '登鹳雀楼', author: '王之涣', createTime: '2025-08-30' },
  { id: 4, title: '望庐山瀑布', author: '李白', createTime: '2025-08-29' },
  { id: 5, title: '江雪', author: '柳宗元', createTime: '2025-08-28' }
])

// 图表引用
const visitChartRef = ref<HTMLDivElement | null>(null)
let visitChart: echarts.ECharts | null = null

// 查看诗词详情
const viewPoetry = (id: number) => {
  console.log('查看诗词:', id)
}

// 初始化访问统计图表
const initVisitChart = () => {
  if (!visitChartRef.value) return
  
  visitChart = echarts.init(visitChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'line',
        smooth: true
      }
    ]
  }
  
  visitChart.setOption(option)
}

// 窗口大小改变时重置图表
const handleResize = () => {
  if (visitChart) {
    visitChart.resize()
  }
}

onMounted(() => {
  initVisitChart()
  window.addEventListener('resize', handleResize)
})

// 组件卸载时清理
// onUnmounted(() => {
//   window.removeEventListener('resize', handleResize)
//   if (visitChart) {
//     visitChart.dispose()
//   }
// })
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}

.dashboard-header {
  margin-bottom: 20px;
}

.dashboard-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-content {
  display: flex;
  align-items: center;
}

.card-icon {
  width: 60px;
  height: 60px;
  background-color: #ecf5ff;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 20px;
}

.card-icon .el-icon {
  font-size: 24px;
  color: #409eff;
}

.card-info {
  flex: 1;
}

.card-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.card-number {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.chart-card,
.recent-card {
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-header {
  font-weight: bold;
  color: #303133;
}

.chart-container {
  height: 300px;
}

.chart {
  width: 100%;
  height: 100%;
}

.recent-list {
  max-height: 300px;
  overflow-y: auto;
}

.recent-item {
  padding: 15px 0;
  border-bottom: 1px solid #f0f0f0;
  cursor: pointer;
  transition: background-color 0.3s;
}

.recent-item:last-child {
  border-bottom: none;
}

.recent-item:hover {
  background-color: #f5f7fa;
}

.item-title {
  font-weight: bold;
  color: #303133;
  margin-bottom: 5px;
}

.item-author {
  font-size: 14px;
  color: #606266;
  margin-bottom: 5px;
}

.item-time {
  font-size: 12px;
  color: #909399;
}
</style>