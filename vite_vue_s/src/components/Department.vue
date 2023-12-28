<template>
  <el-scrollbar>
    <el-space direction="vertical">

      <el-form inline>
        <el-form-item>
          <el-input v-model="search" size="default" placeholder="按名称搜索"/>
        </el-form-item>
        <el-form-item>
          <el-button @click="fetchData">查询</el-button>
        </el-form-item>
      </el-form>

      <el-table :data="departmentData" @row-click="handleRowClick" border width="160">
        <el-table-column prop="id" label="Id" width="40"/>
        <el-table-column prop="name" label="Name" width="120"/>
      </el-table>

      <el-dialog v-model="visitableUpdateForm" title="更新部门">
        <el-form inline>
          <el-form-item label=" 修改名称：">
            <el-input v-model="editData.name" placeholder="Name"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="changeDepartment">修改</el-button>
          </el-form-item>
          <el-form-item>
            <el-button type="danger" @click="deleteDepartment">Delete</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>


      <el-form inline>
        <el-form-item label=" 新建部门：">
          <el-input v-model="addData.name" placeholder="Name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="newDepartment">新建</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="danger" @click="">Delete</el-button>
        </el-form-item>
      </el-form>

    </el-space>

  </el-scrollbar>
</template>

<script>
import axios from "axios";
import {getDepartments} from "../fetchData.js";

export default {
  name: "Department",
  data() {
    return {
      visitableUpdateForm: false,
      departmentData: [],
      editData: {
        id: "",
        name: "",
      },
      addData: {
        name: '',
      },
      selectedRow: null,
      search: '',
    };
  },
  created() {
    this.fetchData();

  },
  methods: {
    async fetchData() {
      this.departmentData = await getDepartments(this.search)
    },
    handleRowClick(row) {
      this.visitableUpdateForm = true;
      this.selectedRow = row;
      this.editData.id = row.id;
      this.editData.name = row.name;
      console.log(this.selectedRow, this.editData);
    },
    newDepartment() {
      if (!this.addData.name) {
        this.$message.error("未输入名称");
        return;
      }

      axios.post(`http://localhost:8080/addDepartment/${this.addData.name}`,).then((respons) => {
        // 更新数据成功后重新获取数据并清空编辑数据
        if (respons.data === 'Success') {
          this.fetchData();
          this.addData.name = "";
        }
      });

    },
    changeDepartment() {
      console.log(!this.editData.name);
      if (!this.selectedRow && !this.editData.name) {
        this.$message.error("请先选择部门")
        return;
      }
      // const updatedData = {
      //   ...this.selectedRow,
      //   ...this.editData
      // };
      // console.log(updatedData);
      const id = !this.selectedRow ? '' : this.selectedRow.id;
      axios.put(`http://localhost:8080/departments/${id}`, this.editData).then(() => {
        // 更新数据成功后重新获取数据并清空编辑数据
        this.fetchData();
        this.editData.name = "";
        this.editData.address = "";
        this.selectedRow = null;
      });
    },
    deleteDepartment() {
      if (!this.selectedRow) {
        return;
      }
      const updatedData = {
        ...this.selectedRow,
        ...this.editData
      };
      axios.delete(`http://localhost:8080/deleteDepartment/${updatedData.id}`
      ).then(response => {
        this.fetchData();
        this.editData.name = '';
      }).catch(error => {
        console.log(error);
      }).finally(() => {
        console.log('delete finally');
      })
    }
  }
}
</script>

<style scoped>

</style>