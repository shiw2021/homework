<template>
  <el-row class="mb-4">
    <el-button>Default</el-button>
    <el-button type="primary">Primary</el-button>
    <el-button type="success">Success</el-button>
    <el-button-group>
      <el-button type="info">Info</el-button>
      <el-button type="warning">Warning</el-button>

    </el-button-group>
    <el-button type="danger">Danger</el-button>
  </el-row>

  <el-row class="mb-4">
    <el-button plain>Plain</el-button>
    <el-button type="primary" plain>Primary</el-button>
    <el-button type="success" plain>Success</el-button>
    <el-button type="info" plain>Info</el-button>
    <el-button type="warning" plain>Warning</el-button>
    <el-button type="danger" plain>Danger</el-button>
  </el-row>

  <el-row class="mb-4">
    <el-button round>Round</el-button>
    <el-button type="primary" round>Primary</el-button>
    <el-button type="success" round>Success</el-button>
    <el-button-group>
      <el-button type="info" round>Info</el-button>
      <el-button type="warning" round>Warning</el-button>

    </el-button-group>
    <el-button type="danger" round>Danger</el-button>
  </el-row>

  <el-row>
    <el-button :icon="Search" circle/>
    <el-button type="primary" :icon="Edit" circle/>
    <el-button type="success" :icon="Check" circle/>
    <el-button-group>
      <el-button type="info" :icon="Message" circle/>
      <el-button type="warning" :icon="Star" circle/>

    </el-button-group>
    <el-button type="danger" :icon="Delete" circle/>
  </el-row>

  <div class="flex">
    <el-button type="primary" :icon="Edit"/>
    <el-button type="primary" :icon="Share"/>
    <el-button type="primary" :icon="Delete"/>
    <el-button type="primary" :icon="Search">Search</el-button>
    <el-button type="primary">
      Upload
      <el-icon class="el-icon--right">
        <Upload/>
      </el-icon>
    </el-button>
    <el-row>
      <el-button :icon="Search" size="large" circle />
      <el-button :icon="Search" circle />
      <el-button :icon="Search" size="small" circle />
    </el-row>
  </div>
</template>
<script lang="ts" setup>
import {
  Check,
  Delete,
  Edit,
  Message,
  Search, Share,
  Star,
} from '@element-plus/icons-vue'
</script>
