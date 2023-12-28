<template>
  <el-container class="layout-container-demo" style="">
    <el-aside width="200px">
      <el-scrollbar>
        <el-menu :default-openeds="['1']">
          <el-sub-menu index="1">
            <template #title>
              <el-icon>
                <message/>
              </el-icon>
              菜单
            </template>
            <el-menu-item-group>
              <el-menu-item index="1-1">
                <router-link to="/admin/department">部门管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-2">
                <router-link to="/admin/account">账号管理</router-link>
              </el-menu-item>
              <el-menu-item index="1-3"  v-if="false"   >
                <router-link to="">管理</router-link>
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-scrollbar>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px">
        <div class="toolbar">
          <el-dropdown>
            <el-icon style="margin-right: 8px; margin-top: 1px">
              <setting/>
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>View</el-dropdown-item>
                <el-dropdown-item>Add</el-dropdown-item>
                <el-dropdown-item>Delete</el-dropdown-item>
                <el-dropdown-item @click="logout">logout</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
          <span>您好，{{user.departmentId===1?'管理员':'普通用户'}}，{{user.name}}</span>

        </div>
      </el-header>

      <el-main>
        <router-view/>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>

import {Message, Setting} from "@element-plus/icons-vue";
import {useRouter} from "vue-router";

export default {
  components: {Setting, Message},
  setup() {
    const router = useRouter();
    const user=JSON.parse(localStorage.getItem('userinfo'));
    const logout = () => {
      localStorage.setItem('isLoggedIn', 'false');
      router.push('/login');
    };
    const userName = user.username;
    return {
      logout, user, userName
    };
  },
  methods: {
    // logout() {
    //   localStorage.setItem("isLoggedIn", "false");
    //   useRouter().push('/');
    //
    // }
  },
}
</script>

<style scoped>
.layout-container-demo .el-header {
  position: relative;
  background-color: var(--el-color-primary-light-7);
  color: var(--el-text-color-primary);
}

.layout-container-demo .el-aside {
  color: var(--el-text-color-primary);
  background: var(--el-color-primary-light-8);
}

.layout-container-demo .el-menu {
  border-right: none;
}

.layout-container-demo .el-main {
  padding: 0;
}

.layout-container-demo .toolbar {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  right: 20px;
}
</style>
