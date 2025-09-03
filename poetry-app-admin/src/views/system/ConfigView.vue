<template>
  <div class="config-container">
    <el-tabs v-model="activeTab" type="card">
      <el-tab-pane label="基本配置" name="basic">
        <el-card>
          <el-form
            ref="basicFormRef"
            :model="basicConfig"
            label-width="120px"
            :rules="basicRules"
          >
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="系统名称" prop="systemName">
                  <el-input v-model="basicConfig.systemName" placeholder="请输入系统名称" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="系统版本" prop="systemVersion">
                  <el-input v-model="basicConfig.systemVersion" placeholder="请输入系统版本" />
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="作者" prop="author">
                  <el-input v-model="basicConfig.author" placeholder="请输入作者" />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="版权信息" prop="copyright">
                  <el-input v-model="basicConfig.copyright" placeholder="请输入版权信息" />
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-form-item label="系统描述" prop="description">
              <el-input
                v-model="basicConfig.description"
                type="textarea"
                :rows="4"
                placeholder="请输入系统描述"
              />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="saveBasicConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
      
      <el-tab-pane label="安全配置" name="security">
        <el-card>
          <el-form
            ref="securityFormRef"
            :model="securityConfig"
            label-width="150px"
            :rules="securityRules"
          >
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="JWT过期时间(秒)" prop="jwtExpiration">
                  <el-input-number
                    v-model="securityConfig.jwtExpiration"
                    :min="3600"
                    :max="2592000"
                    placeholder="请输入JWT过期时间"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="刷新令牌过期时间(秒)" prop="refreshExpiration">
                  <el-input-number
                    v-model="securityConfig.refreshExpiration"
                    :min="86400"
                    :max="7776000"
                    placeholder="请输入刷新令牌过期时间"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-form-item label="JWT密钥" prop="jwtSecret">
              <el-input
                v-model="securityConfig.jwtSecret"
                type="password"
                placeholder="请输入JWT密钥"
                show-password
              />
            </el-form-item>
            
            <el-form-item>
              <el-button type="primary" @click="saveSecurityConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
      
      <el-tab-pane label="文件配置" name="file">
        <el-card>
          <el-form
            ref="fileFormRef"
            :model="fileConfig"
            label-width="120px"
            :rules="fileRules"
          >
            <el-form-item label="上传路径" prop="uploadPath">
              <el-input v-model="fileConfig.uploadPath" placeholder="请输入上传路径" />
            </el-form-item>
            
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="最大文件大小" prop="maxFileSize">
                  <el-input-number
                    v-model="fileConfig.maxFileSize"
                    :min="1048576"
                    :max="104857600"
                    placeholder="请输入最大文件大小"
                  />
                  <div class="form-item-tip">单位：字节</div>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="允许的文件类型" prop="allowedTypes">
                  <el-select
                    v-model="fileConfig.allowedTypes"
                    multiple
                    placeholder="请选择允许的文件类型"
                    style="width: 100%"
                  >
                    <el-option label="jpg" value="jpg" />
                    <el-option label="jpeg" value="jpeg" />
                    <el-option label="png" value="png" />
                    <el-option label="gif" value="gif" />
                    <el-option label="pdf" value="pdf" />
                    <el-option label="doc" value="doc" />
                    <el-option label="docx" value="docx" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-form-item>
              <el-button type="primary" @click="saveFileConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
      
      <el-tab-pane label="诗词配置" name="poetry">
        <el-card>
          <el-form
            ref="poetryFormRef"
            :model="poetryConfig"
            label-width="150px"
            :rules="poetryRules"
          >
            <el-row :gutter="20">
              <el-col :span="12">
                <el-form-item label="每日推荐诗词数量" prop="dailyLimit">
                  <el-input-number
                    v-model="poetryConfig.dailyLimit"
                    :min="1"
                    :max="1000"
                    placeholder="请输入每日推荐诗词数量"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="热门诗词阈值" prop="hotThreshold">
                  <el-input-number
                    v-model="poetryConfig.hotThreshold"
                    :min="1"
                    :max="100000"
                    placeholder="请输入热门诗词阈值"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            
            <el-form-item>
              <el-button type="primary" @click="savePoetryConfig">保存配置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'

// 活动标签页
const activeTab = ref('basic')

// 基本配置表单
const basicFormRef = ref()
const basicConfig = reactive({
  systemName: '企业级诗词APP后端服务',
  systemVersion: '1.0.0',
  author: 'Sakura Huang',
  copyright: 'Copyright © 2025 Sakura Huang. All rights reserved.',
  description: '基于Spring Boot 3.5.5的企业级诗词应用后端服务'
})

const basicRules = {
  systemName: [
    { required: true, message: '请输入系统名称', trigger: 'blur' }
  ],
  systemVersion: [
    { required: true, message: '请输入系统版本', trigger: 'blur' }
  ]
}

// 安全配置表单
const securityFormRef = ref()
const securityConfig = reactive({
  jwtExpiration: 604800,
  refreshExpiration: 1209600,
  jwtSecret: 'the-poetry-app-server-jwt-secret-key-sakura-huang-2025-09-03'
})

const securityRules = {
  jwtExpiration: [
    { required: true, message: '请输入JWT过期时间', trigger: 'blur' }
  ],
  refreshExpiration: [
    { required: true, message: '请输入刷新令牌过期时间', trigger: 'blur' }
  ],
  jwtSecret: [
    { required: true, message: '请输入JWT密钥', trigger: 'blur' }
  ]
}

// 文件配置表单
const fileFormRef = ref()
const fileConfig = reactive({
  uploadPath: './uploads/',
  maxFileSize: 10485760,
  allowedTypes: ['jpg', 'jpeg', 'png', 'gif', 'pdf', 'doc', 'docx']
})

const fileRules = {
  uploadPath: [
    { required: true, message: '请输入上传路径', trigger: 'blur' }
  ],
  maxFileSize: [
    { required: true, message: '请输入最大文件大小', trigger: 'blur' }
  ]
}

// 诗词配置表单
const poetryFormRef = ref()
const poetryConfig = reactive({
  dailyLimit: 100,
  hotThreshold: 1000
})

const poetryRules = {
  dailyLimit: [
    { required: true, message: '请输入每日推荐诗词数量', trigger: 'blur' }
  ],
  hotThreshold: [
    { required: true, message: '请输入热门诗词阈值', trigger: 'blur' }
  ]
}

// 保存基本配置
const saveBasicConfig = async () => {
  if (!basicFormRef.value) return
  
  await basicFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('基本配置保存成功')
    }
  })
}

// 保存安全配置
const saveSecurityConfig = async () => {
  if (!securityFormRef.value) return
  
  await securityFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('安全配置保存成功')
    }
  })
}

// 保存文件配置
const saveFileConfig = async () => {
  if (!fileFormRef.value) return
  
  await fileFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('文件配置保存成功')
    }
  })
}

// 保存诗词配置
const savePoetryConfig = async () => {
  if (!poetryFormRef.value) return
  
  await poetryFormRef.value.validate((valid: boolean) => {
    if (valid) {
      ElMessage.success('诗词配置保存成功')
    }
  })
}

// 初始化
onMounted(() => {
  // 加载配置数据
})
</script>

<style scoped>
.config-container {
  padding: 20px;
}

.form-item-tip {
  font-size: 12px;
  color: #909399;
  margin-top: 5px;
}
</style>