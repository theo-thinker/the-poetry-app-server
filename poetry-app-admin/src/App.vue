<script setup lang="ts">
// App.vue 不再需要额外的导入，路由会处理页面显示
</script>

<template>
  <router-view />
</template>

<style lang="scss">
/* 全局样式 */
html,
body,
#app {
  height: 100%;
  margin: 0;
  padding: 0;
}

/* 滚动条样式 */
::-webkit-scrollbar {
  width: 6px;
  height: 6px;
}

::-webkit-scrollbar-thumb {
  background-color: #c1c1c1;
  border-radius: 4px;
}

::-webkit-scrollbar-track {
  background-color: #f1f1f1;
}
</style>
