<template>
  <infor-slot>
    <template #who>shiw</template>
    <template #number>10</template>
    <template #thing>grilfriends</template>
  </infor-slot>
</template>

<script>
import InforSlot from "./inforSlot.vue";

export default {
  name: "information",
  components: {InforSlot}
}
</script>

<style scoped>

</style>