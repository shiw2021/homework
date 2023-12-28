<template>
  <el-button-group>
    <el-button :type="currentFilter === 'all' ? 'primary' : ''" @click="currentFilter = 'all'">所有</el-button>
    <el-button :type="currentFilter === 'initial' ? 'primary' : ''" @click="currentFilter = 'initial'">办公室
    </el-button>
    <el-button :type="currentFilter === 'secondary' ? 'primary' : ''" @click="currentFilter = 'secondary'">用人部门
    </el-button>
    <el-button :type="currentFilter === 'accepted' ? 'primary' : ''" @click="currentFilter = 'accepted'">总经理
    </el-button>
    <el-button :type="currentFilter === 'rejected' ? 'primary' : ''" @click="currentFilter = 'rejected'">未通过
    </el-button>
  </el-button-group>
  <div class="candidate-list">
    <el-card class="candidate-card" v-for="candidate in filteredCandidates" :key="candidate.id">
      <el-avatar size="large" class="avatar" :src="'/getavatar/'+candidate.portraitUrl" alt="avatar"/>
      <div class="info">
        <p class="name">姓名：{{ candidate.name }}</p>
        <p class="gender">性别：{{ candidate.gender }}</p>
        <p class="age">年龄：{{ candidate.age }} years old</p>
        <p class="position">申请岗位： {{ candidate.positionApplyingFor }}</p>
        <p class="department">申请部门: {{ candidate.departmentName }}</p>
        <p class="status">目前状况：{{ candidate.status }}</p>
      </div>
    </el-card>
<!--    <img src="http://localhost:8080/f269bc18-9b36-4944-a7f3-e21ac74e1e41.png">-->
<!--    <img src="/getavatar/f269bc18-9b36-4944-a7f3-e21ac74e1e41.png">-->
<!--    <img :src="'/getavatar'+'f269bc18-9b36-4944-a7f3-e21ac74e1e41.png'">-->
<!--    <img :src="'/getavatar/'+this.candidates[1].portraitUrl">-->



  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'CandidateList',
  data() {
    return {
      candidates: [
        {
          portraitUrl:'',
          name: 'spc',
          gender: 'M',
          age: 12,
          positionApplyingFor: 'work',
          department: 'dep',
          status: 'initial'
        },
      ],
      currentFilter: 'all'
    }
  },
  mounted() {
    this.fetchCandidates();
  },
  methods: {
    fetchCandidates() {
      axios.get('http://localhost:8080/applicant')
        .then(response => {
          this.candidates = response.data;
        })
        .catch(error => {
          console.error(error);
        });
    }
  },
  computed: {
    filteredCandidates() {
      switch (this.currentFilter) {
        case 'initial':
          return this.candidates.filter(candidate => candidate.status === 'initial');
        case 'secondary':
          return this.candidates.filter(candidate => candidate.status === 'secondary');
        case 'accepted':
          return this.candidates.filter(candidate => candidate.status === 'accepted');
        case 'rejected':
          return this.candidates.filter(candidate => candidate.status === 'rejected');
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