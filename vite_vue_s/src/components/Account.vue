<template>
  <el-space direction="vertical">


    <el-dialog v-model="visitableCreateUser" title="新建用户">
      <el-form :model="createUserForm" label-width="120px">
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="createUserForm.email"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
          <el-input v-model="createUserForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input type="password" v-model="createUserForm.pwd"></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="department">
          <el-select v-model="createUserForm.departmentId" placeholder="Please select">
            <el-option v-for="dept in departments" :key="dept.id" :label="dept.name" :value="dept.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="createUser">创建账号</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <el-form :model="searchUserForm" inline>
      <el-form-item label="搜索邮箱">
        <el-input v-model="searchUserForm.email" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="搜索部门">
        <el-select v-model="searchUserForm.departmentId" placeholder="请选择部门">
          <el-option v-for="department in departments" :key="department.id" :label="department.name"
                     :value="department.id"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchUser()">搜索</el-button>
        <el-button type="primary" @click="updateUsers()">reset</el-button>
        <el-button type="primary" @click="() =>{this.visitableCreateUser=true} ">新建用户</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="tableDataUsers" @row-click="userDataClick" border>
      <el-table-column label="ID" prop="id"></el-table-column>

      <el-table-column label="姓名" prop="name" min-width="120"></el-table-column>
      <el-table-column label="邮箱" prop="email" min-width="200"></el-table-column>
      <el-table-column label="部门" prop="depName"></el-table-column>
      <el-table-column label="部门id" prop="departmentId" v-if="false">


      </el-table-column>
    </el-table>
    <el-dialog v-model="visitableUpdateUser" title="更新用户">
      <el-form v-model="updateUserForm">
        <el-form-item label="邮箱：">
          <el-input v-model="updateUserForm.email" placeholder="修改邮箱"/>
        </el-form-item>
        <el-form-item label="姓名：">
          <el-input v-model="updateUserForm.name" placeholder="修改姓名"/>
        </el-form-item>
        <el-form-item label="部门：">
          <el-select v-model="updateUserForm.departmentId" placeholder="请选择部门">
            <el-option v-for="department in departments" :key="department.id" :label="department.name"
                       :value="department.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button @click="updateUser">更新</el-button>
          <el-tooltip content="重置密码为111111" placement="top">
            <el-button @click="resetPwd" type="warning">重置密码</el-button>
          </el-tooltip>
          <el-button @click="deleteUser" type="danger">删除用户</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </el-space>
</template>

<script>
import axios, {HttpStatusCode} from "axios";
import {getUserData} from "../fetchData.js";

export default {
  name: "Account.vue",
  data() {
    return {
      visitableCreateUser: false,
      visitableUpdateUser: false,
      searchUserForm: {email: '', departmentId: null},
      tableDataUsers: [],
      createUserForm: {
        email: '111111@qq.com',
        pwd: '111111',
        departmentId: '',
      },
      updateUserForm: {
        id: '',
        email: '',
        name: '',
        // pwd: '',
        departmentId: '',
      },
      departments: [],

    }

  },
  mounted() {
    (async () => {
      await this.updateDepartments()
      await this.updateUsers()
    })()
  },
  methods: {
    preDataWork() {
      let users = this.tableDataUsers;
      // await this.updateDepartments()
      // console.log(this.departments, users)
      users.forEach((u) => {
        let dep = this.departments.find((d) => d.id === u.departmentId)
        if (dep) {
          u.depName = dep.name
        } else {
          u.depName = 'error'
          console.log(`No department found for user with id ${u.id}`)
        }
      })
      this.tableDataUsers = users
    },
    async updateUsers() {
      this.clearSearchForm();
      try {
        let users = await getUserData()
        // await this.updateDepartments()
        // console.log(this.departments, users)
        users.forEach((u) => {
          let dep = this.departments.find((d) => d.id === u.departmentId)
          if (dep) {
            u.depName = dep.name
          } else {
            u.depName = 'error'
            console.log(`No department found for user with id ${u.id}`)
          }
        })
        this.tableDataUsers = users
        // console.log(users)
      } catch (error) {
        console.log(error)
        this.$message.error(error.message);
      }
    }
    ,
    updateDepartments() {
      axios.get('http://localhost:8080/getDepartments').then(response => {
        this.departments = response.data.Departments;
        console.log(this.departments)
      })
    }
    ,
    userDataClick(row) {
      this.visitableUpdateUser = true
      this.updateUserForm.id = row.id;
      this.updateUserForm.email = row.email;
      this.updateUserForm.name = row.name;
      this.updateUserForm.departmentId = row.departmentId;
    }
    ,
    clearSearchForm() {
      // this.createUserForm.departmentId = ''
      // this.createUserForm.email = ''
      // this.updateUserForm.departmentId = ''
      // this.updateUserForm.email = ''
      // this.updateUserForm.id = ''
      this.searchUserForm.email = ''
      this.searchUserForm.departmentId = null
    }
    ,
    createUser() {
      axios.post('http://localhost:8080/createUser', this.createUserForm).then(response => {
        if (response.status === 200) {
          this.$message.info('create user success')
          this.updateUsers();
        } else {
          console.log('create user failed', response.status);
          this.updateUsers()
        }
      }).catch(error => {
        this.$message.error("create user error:" + error.response.status + error.response.data)
        this.updateUsers()
      })
    }//c
    ,
    searchUser() {
      let query = '';
      if (this.searchUserForm.email.trim().length > 0) {
        query += `email=${this.searchUserForm.email}`;
      }
      if (this.searchUserForm.departmentId != null) {
        if (query.length > 0) {
          query += '&';
        }
        query += `departmentId=${this.searchUserForm.departmentId}`;
      }
      const url = `http://localhost:8080/getUsers?${query}`;
      this.$message.info(url)
      axios.get(url).then(response => {
        if (response.status === 200) {
          this.$message.info('search success')
          this.tableDataUsers = response.data;
          this.preDataWork()
        } else {
          this.$message.error("更新用户失败");
          console.log('update usersss failed', response.status, response.data);
        }
      })
    }//r
    ,
    updateUser() {
      if (this.updateUserForm.id === '') return
      const user = {...this.updateUserForm}
      axios.put(`http://localhost:8080/putUser/${user.id}`, user).then(response => {
        if (response.status === 204) {
          this.$message.info("user information updated.")
          this.updateUsers()
        } else {
          this.$message.error("update failed" + response.status);
        }
      }).catch(error => {
        if (error.response && error.response.status === 404) {
          this.$message.error(error.response.data)
        }
      })
    }//u
    ,
    deleteUser() {
      if (this.updateUserForm.id === '') return
      axios.delete(`http://localhost:8080/user/${this.updateUserForm.id}`).then(response => {
        if (response.status === HttpStatusCode.NoContent) {
          this.$message.info('delete user success.')
          this.updateUsers()
          this.updateUserForm.email = ''
          this.updateUserForm.departmentId = null
        } else {
          this.$message.error('delete user failed')
        }
      })
    }//d
    ,
    resetPwd() {
      if (this.updateUserForm.id === '') return
      axios.put(`http://localhost:8080/resetPwd/${this.updateUserForm.id}`).then(respons => {
        if (respons.status === HttpStatusCode.NoContent) {
          this.$message.info('reset pwd success: 204')
        }
      }).catch(error => {
        this.$message.error('reset pwd error:' + error.response.status)
      })

    }
  }
}

</script>

<style scoped>

</style>