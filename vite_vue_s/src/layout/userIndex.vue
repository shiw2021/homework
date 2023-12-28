<template>
  <el-container>
    <el-header>
      <el-menu

        :default-active="activeIndex2"
        class="el-menu-demo"
        mode="horizontal"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
        @select="handleSelect"
        style="position: relative"
      >
        <el-menu-item index="1">
          <router-link id="link" :to="{name:'RecordWork'}" style="text-underline: none">处理中心</router-link>
        </el-menu-item>
        <el-sub-menu index="2" >
          <template #title>工作中心</template>
          <el-menu-item index="2-1" v-if="department==='人事部'">
            <router-link :to="{name:'ApplicantForm'}">录入应聘人</router-link>
          </el-menu-item>
<!--          <el-menu-item index="2-2">-->
<!--            <router-link :to="{name:'ApplicantTable'}">applicant table</router-link>-->
<!--          </el-menu-item>-->
<!--          <el-menu-item index="2-3">item three</el-menu-item>-->
<!--          <el-sub-menu index="2-4">-->
<!--            <template #title>item four</template>-->
<!--            <el-menu-item index="2-4-1">item one</el-menu-item>-->
<!--            <el-menu-item index="2-4-2">item two</el-menu-item>-->
<!--            <el-menu-item index="2-4-3">item three</el-menu-item>-->
<!--          </el-sub-menu>-->
        </el-sub-menu>
        <el-menu-item index="3" disabled>Info</el-menu-item>
        <el-menu-item index="4" style="position: absolute;right: 0px;color: white">
          <div class="user-info"
               style="display: flex;justify-content: space-evenly;align-content: center;align-items: center">
            <el-text style="color: #ffffff">您好, {{ department }} 的 {{ userName }}</el-text>
            <el-button style="margin-left: 10px" type="danger" @click="logout">logout</el-button>
          </div>
        </el-menu-item>
      </el-menu>
    </el-header>
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
import {Setting} from "@element-plus/icons-vue";
import {getDepartments} from "../fetchData.js";

export default {
  name: "userIndex.vue",
  components: {Setting},
  data() {
    return {
      activeIndex2: '1',
      userName: '',
      department: '',
      departments: [],
    }
  },
  async mounted() {
    let user = JSON.parse(localStorage.getItem('userinfo'))
    this.userName = user.name
    this.departments =await getDepartments('')
    this.department = this.departments.find(item => item.id === user.departmentId).name
  },
  methods: {
    logout() {
      localStorage.setItem('isLoggedIn', 'false');
      localStorage.removeItem('userinfo');
      this.$router.push('/login');
    }
  }
}
</script>

<style scoped>
/*.toolbar {*/
/*  display: inline-flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  height: 100%;*/
/*  right: 20px;*/
/*}*/
</style>