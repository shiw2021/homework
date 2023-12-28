<template>
  <p>situations:</p>
  <slot name="who"></slot>
  <label> has </label>
  <slot name="number"></slot>
  <slot name="thing"></slot>
</template>

<script>
import {MessageBox} from "@element-plus/icons-vue";

export default {
  name: "inforSlot",
  components: {MessageBox}
}
</script>

<style scoped>

</style>