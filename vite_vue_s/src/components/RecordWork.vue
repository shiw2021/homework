<template>
  <el-button-group>
    <el-button :type="currentFilter === 'all' ? 'primary' : ''" @click="currentFilter = 'all'">所有</el-button>
    <el-button :type="currentFilter === 'office' ? 'primary' : ''" @click="currentFilter = 'office'">办公室
    </el-button>
    <el-button :type="currentFilter === 'department' ? 'primary' : ''" @click="currentFilter = 'department'">用人部门
    </el-button>
    <el-button :type="currentFilter === 'manager' ? 'primary' : ''" @click="currentFilter = 'manager'">总经理
    </el-button>
    <el-button :type="currentFilter === 'rejected' ? 'primary' : ''" @click="currentFilter = 'rejected'">未通过
    </el-button>
    <el-button :type="currentFilter==='passed'? 'primary':''" @click="currentFilter = 'passed'">已通过

    </el-button>
  </el-button-group>
  <div class="candidate-list">
    <el-card class="candidate-card" v-for="candidate in filteredCandidates" :key="candidate.id">
      <el-avatar size="large" class="avatar" :src="'/getavatar/'+candidate.portraitUrl" alt="avatar"/>
      <el-button type="primary" @click="downloadFile(candidate.id,candidate.name)">下载简历</el-button>
      <el-button type="primary" @click="handleCreateRecord(candidate)">添加记录
      </el-button>
      <div class="info">
        <p class="name">姓名：{{ candidate.name }}</p>
        <p class="gender">性别：{{ candidate.gender === 'M' ? '男' : '女' }}</p>
        <p class="age">年龄：{{ candidate.age }} 岁</p>
        <p class="position">申请岗位： {{ candidate.positionApplyingFor }}</p>
        <p class="department">申请部门: {{ candidate.departmentName }}</p>
        <p class="status">当前阶段：{{ candidate.status }}</p>
      </div>
    </el-card>
    <el-dialog v-model="dialogFormVisible" title="记录查看与更新">
      <el-table :data="curruntOldRecords" border>
        <el-table-column label="序号" width="180" v-if="false">
          <template #default="scope">

          </template>
        </el-table-column>
        <el-table-column prop="signerName" label="创建人" width="180"></el-table-column>

        <el-table-column prop="stage" label="阶段名称" width="180">
          <template #default="scope">
            {{
              scope.row.stage == 1 ? "录入完毕" : scope.row.stage == 2 ? "办公室审核" : scope.row.stage == 3 ? "用人部门面试" : scope.row.stage == 4 ? "总经理面试" : scope.row.stage == 5 ? "录用" : "未录用"
            }}
          </template>
        </el-table-column>

        <el-table-column prop="passed" label="是否通过" width="180">
          <template #default="scope">
            {{ scope.row.passed ? "通过" : "未通过" }}
          </template>
        </el-table-column>
        <el-table-column prop="opinion" label="意见" width="180"></el-table-column>
        <!--        <el-table-column prop="updateTime" label="更新时间" width="180"></el-table-column>-->
      </el-table>
      <el-form :model="newRecord" ref="newRecord" label-width="120px">
        <el-form-item label="当前阶段" prop="status">
          <el-input v-model="curruntStatus" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否通过" prop="passed">
          <el-switch v-model="newRecord.passed"></el-switch>
        </el-form-item>
        <el-form-item label="意见" prop="opinion">
          <el-input :autosize="{ minRows: 2, maxRows: 4 }"
                    type="textarea" v-model="newRecord.opinion"></el-input>
        </el-form-item>
        <el-form-item label="您的密码" prop="password">
          <el-input v-model="userinfo.pwd" type="password" @change="ElMessage(JSON.stringify(userinfo))"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submit()">Submit</el-button>
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">
          Confirm
        </el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import axios, {HttpStatusCode} from 'axios';
import {downloadFile, getDepartments} from "../fetchData.js";
import {ElMessage} from "element-plus";

export default {
  data() {
    return {
      userinfo: JSON.parse(localStorage.getItem('userinfo')),
      newRecord: {
        appId: '',
        stage: '',
        passed: null,
        opinion: '',
      },
      curruntOldRecords: [],
      curruntStatus: '',
      dialogFormVisible: false,
      candidates: [],
      departments: [],
      currentFilter: 'all'
    }
  },
  name: 'RecordWork',
  mounted() {
    this.fetchCandidates();
  },
  methods: {
    ElMessage,
    handleCreateRecord(candidate) {
      this.dialogFormVisible = true;
      this.curruntStatus = candidate.status;


      let candidateDepartmentId = candidate.departmentId
      this.curruntOldRecords = candidate.records;
      this.newRecord.appId = candidate.id;
      this.newRecord.stage = candidate.stage + 1;

      axios.get(`http://localhost:8080/record/${candidate.id}`).then(response => {
        this.curruntOldRecords = response.data;
        console.log(this.curruntOldRecords)
      })
      if (this.curruntStatus === '已通过') {
        this.$message({
          message: '该候选人已录用，无法添加记录',
          type: 'warning'
        });
      }
    },
    submit() {
      //未通过
      if (this.curruntStatus === '未通过') {
        this.$message({
          message: '该候选人以被淘汰，无法添加记录',
          type: 'warning'
        });
        return;
      }
      if (this.curruntStatus === '已通过') {
        this.$message({
          message: '该候选人已录用，无法添加记录',
          type: 'warning'
        });
        return;
      }
      console.log(this.newRecord);
      axios.post(`http://localhost:8080/record/${this.newRecord.appId}`, {
        newRecord: this.newRecord,
        userinfo: this.userinfo,
      })
        .then(response => {
          if (response.status === HttpStatusCode.Created) {
            this.$message({
              message: '添加成功',
              type: 'success'
            });
            this.dialogFormVisible = false;
            this.fetchCandidates()
          }
        })
        .catch(error => {
          console.error(error);
          this.$message({
            message: JSON.stringify(error.response.data),
            type: 'error'
          });
        });
    },
    downloadFile,
    async fetchCandidates() {
      this.departments = await getDepartments('')
      let userinfo = JSON.parse(localStorage.getItem('userinfo'))
      await axios.post('http://localhost:8080/applicant/filtered', userinfo)
        .then(response => {
          this.candidates = response.data;
        })
        .catch(error => {
          console.error(error);
        });
      let can = this.candidates
      can.forEach((c) => {
        let dep = this.departments.find((d) => d.id === c.departmentId)
        if (dep) {
          c.departmentName = dep.name
        } else {
          c.depName = 'error'
          console.log(`No department found for user with id ${c.id}`)
        }

        let isForEach = false
        c.records.forEach((r) => {
          let stage = 0
          if (r.passed === 0) {
            isForEach = true
          } else {
            c.status = stage < r.stage ? r.stage : stage
          }
        })
        if ((c.records.find((r) => r.stage === c.status)).passed === false) {
          console.log(123123)
          c.status = 0
        }
        switch (c.status) {
          case 0:
            c.stage = 0;
            c.status = '未通过'
            break
          case 1:
            c.stage = 1;
            c.status = '办公室审核'
            break
          case 2:
            c.stage = 2;
            c.status = '用人部门面试'
            break
          case 3:
            c.stage = 3;
            c.status = '总经理审核'
            break
          case 4:
            c.stage = 4;
            c.status = '已通过'
            break
          default:
            c.status = '未知状态'
        }
      })
    },
    // downloadFile(id) {
    //   axios({
    //     url: 'http://localhost:8080/applicant/download/' + id,
    //     method: 'GET',
    //     responseType: 'blob'
    //   }).then((res) => {
    //     const link = document.createElement('a')
    //     const blob = new Blob([res.data])
    //     link.style.display = 'none'
    //     link.href = URL.createObjectURL(blob)
    //     link.download = '简历.docx'
    //     document.body.appendChild(link)
    //     link.click()
    //     document.body.removeChild(link)
    //   })
    // }
  },
  computed: {
    getStatus() {
      //获得当前候选人的状态
      return this.candidates[0].status
    },
    filteredCandidates() {
      switch (this.currentFilter) {
        case 'initial':
          return this.candidates.filter(candidate => candidate.status === 'initial');
        case 'office':
          return this.candidates.filter(candidate => candidate.status === '办公室审核');
        case 'department':
          console.log(this.candidates)
          return this.candidates.filter(candidate => candidate.status === '用人部门面试' && candidate.records[1].passed === true);
        case 'manager':
          return this.candidates.filter(candidate => candidate.status === '总经理审核' && candidate.records[2].passed === true);
        case 'rejected':
          return this.candidates.filter(candidate => candidate.status === '未通过');
        case 'passed':
          return this.candidates.filter(candidate => candidate.status === '已通过');
        default:
          return this.candidates;
      }
    }
  }
}
</script>

<style scoped>

.candidate-list {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
}

.candidate-card {
  width: 300px;
  /*height: 360px;*/
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.avatar {
  /*width: 100%;*/
  /*height: 200px;*/
  /*object-fit: cover;*/
  /*border: #2d3a4b;*/
}

.info {
  padding: 20px;
  flex-grow: 1;
}

.name {
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 10px;
}

</style>