<template>
  <el-upload
    ref="uploadRef"
    class="upload-demo"
    action="http://localhost:8080/avatar"
    :auto-upload="false"
  >
    <template #trigger>
      <el-button type="primary">select file</el-button>
    </template>

    <el-button class="ml-3" type="success" @click="submitUpload">
      upload to server
    </el-button>

    <template #tip>
      <div class="el-upload__tip">
        jpg/png files with a size less than 500kb
      </div>
    </template>
  </el-upload>
</template>

<script>
import { ref } from 'vue';

export default {
  setup() {
    const uploadRef = ref(null);

    const submitUpload = () => {
      uploadRef.value.submit();
    };

    return {
      uploadRef,
      submitUpload
    };
  },
};
</script>

