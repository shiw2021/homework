<template>
  <div class="form-container">
    <el-form :model="form" :rules="rules" ref="form" label-width="120px">

      <el-row>

        <el-form-item label="姓名" prop="name">
          <el-input size="small" v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="M" @click="()=>(form.name=form.gender)">男</el-radio>
            <el-radio label="F">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="出生日期" prop="birthday">
          <el-date-picker type="date" v-model="form.birthday" placeholder="选择日期"
                          @change="logMessage(form.birthday)"></el-date-picker>
        </el-form-item>
        <el-form-item label="年龄" prop="age">
          <el-input size="small" v-model.number="form.age"></el-input>
        </el-form-item>
      </el-row>
      <el-row>

        <el-form-item label="民族" prop="ethnicGroup">
          <el-input size="small" v-model="form.ethnicGroup"></el-input>
        </el-form-item>
        <el-form-item label="身高（cm）" prop="height">
          <el-input size="small" v-model.number="form.height"></el-input>
        </el-form-item>
        <el-form-item label="户籍" prop="huJiProvince">
          <el-input size="small" placeholder="省" v-model="form.huJiProvince"></el-input>
        </el-form-item>
        <el-form-item label="" prop="huJiCity">
          <el-input size="small" placeholder="市" v-model="form.huJiCity"></el-input>
        </el-form-item>
        <el-form-item label="已婚" prop="married">
          <el-switch v-model="form.married"></el-switch>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="工龄" prop="workingYears">
          <el-input size="small" v-model.number="form.workingYears"></el-input>
        </el-form-item>
        <el-form-item label="期望薪资" prop="expectedSalary">
          <el-input size="small" v-model.number="form.expectedSalary"></el-input>
        </el-form-item>
        <el-form-item label="居住地省份" prop="juZhuProvince">
          <el-input size="small" v-model="form.juZhuProvince"></el-input>
        </el-form-item>
        <el-form-item label="居住地城市" prop="juZhuCity">
          <el-input size="small" v-model.number="form.juZhuCity"></el-input>
        </el-form-item>

      </el-row>
      <el-row>
        <el-form-item label="学历" prop="edu_background">
          <el-input size="small" v-model="form.eduBacground"></el-input>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input size="small" v-model="form.major"></el-input>
        </el-form-item>
        <el-form-item label="毕业院校" prop="school">
          <el-input size="small" v-model="form.school"></el-input>
        </el-form-item>
      </el-row>


      <el-row>

        <el-form-item label="求职状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="在职流动" value="在职流动"></el-option>
            <el-option label="在家待业" value="在家待业"></el-option>
            <el-option label="应届毕业" value="应届毕业"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="接受加班吗" prop="acceptOvertime">
          <el-switch v-model="form.acceptOvertime"></el-switch>
        </el-form-item>
        <el-form-item label="接受调动吗" prop="acceptTransfer">
          <el-switch v-model="form.acceptTransfer"></el-switch>
        </el-form-item>
        <el-form-item label="公司里有朋友或家人吗" label-width="auto" prop="hasRelationship">
          <el-switch v-model="form.hasRelationship"></el-switch>
        </el-form-item>
        <el-form-item label="相关人姓名" prop="relator">
          <el-input size="small" v-model="form.relator"></el-input>
        </el-form-item>
      </el-row>
      <el-row>

        <el-form-item label="身份证" prop="Card">
          <el-input size="small" v-model="form.Card"></el-input>
        </el-form-item>
        <el-form-item label="身份证地址" prop="Address">
          <el-input size="small" v-model="form.Address"></el-input>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="手机号" prop="phone">

          <el-input size="small" v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item label="座机" prop="landline">
          <el-input size="small" v-model="form.landline"></el-input>
        </el-form-item>
      </el-row>
      <!--      <el-table :data="tableDataUsers" @row-click="userDataClick" border>-->
      <!--        <el-table-column label="ID" prop="id"></el-table-column>-->
      <!--        <el-table-column label="邮箱" prop="email" min-width="200"></el-table-column>-->
      <!--        <el-table-column label="department" prop="depName"></el-table-column>-->
      <!--        <el-table-column label="部门id" prop="departmentId"></el-table-column>-->
      <!--      </el-table>-->
      <!--      <thead>-->
      <!--      <tr>-->
      <!--        <th>Date Start</th>-->
      <!--        <th>Date End</th>-->
      <!--        <th>School</th>-->
      <!--        <th>Major</th>-->
      <!--      </tr>-->
      <!--      </thead>-->
      <!--      <tbody>-->
      <!--      <tr v-for="(row, index) in eduform" :key="index">-->
      <!--        <td><input type="date" v-model="row.startenddate.start"/></td>-->
      <!--        <td><input type="date" v-model="row.startenddate.end"/></td>-->
      <!--        <td><input type="text" v-model="row.school"/></td>-->
      <!--        <td><input type="text" v-model="row.major"/></td>-->
      <!--      </tr>-->
      <!--      </tbody>-->
      <!--      <thead>-->
      <!--      <tr>-->
      <!--        <th>Date Start</th>-->
      <!--        <th>Date End</th>-->
      <!--        <th>company</th>-->
      <!--        <th>position</th>-->
      <!--        <th>left reason</th>-->
      <!--      </tr>-->
      <!--      </thead>-->
      <!--      <tbody>-->
      <!--      <tr v-for="(row, index) in workbacform" :key="index">-->
      <!--        <td><input type="date" v-model="row.startenddate.start"/></td>-->
      <!--        <td><input type="date" v-model="row.startenddate.end"/></td>-->
      <!--        <td><input type="text" v-model="row.company"/></td>-->
      <!--        <td><input type="text" v-model="row.position"/></td>-->
      <!--        <td><input type="text" v-model="row.leftreason"/></td>-->
      <!--      </tr>-->
      <!--      </tbody>-->

      <el-row class="list_row">
        <el-col class="list_title " :span="1">
          <span>教育</span>
          <br>
          <span>背景</span>
        </el-col>
        <el-col class="list_data" :span="23">
          <el-table :data="eduform">
            <el-table-column label="Date Start" prop="startenddate.start">
              <template #default="{row}">
                <el-date-picker type="date" v-model="row.start"></el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="Date End" prop="startenddate.end">
              <template #default="{row}">
                <el-date-picker type="date" v-model="row.end"></el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="School" prop="school">
              <template #default="{row}">
                <el-input v-model="row.school"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="Major" prop="major">
              <template #default="{row}">
                <el-input v-model="row.major"></el-input>
              </template>
            </el-table-column>
          </el-table>
          <el-button @click="addRelation(eduform)">添加</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="list_title" :span="1">
          <p>工作</p>
          <p>实习</p>
          <p>经历</p>
        </el-col>
        <el-col class="list_data" :span="23">
          <el-table :data="workbacform">
            <el-table-column label="Date Start" prop="startenddate.start">
              <template #default="{row}">
                <el-date-picker type="date" v-model="row.start"></el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="Date End" prop="startenddate.end">
              <template #default="{row}">
                <el-date-picker type="date" v-model="row.end"></el-date-picker>
              </template>
            </el-table-column>
            <el-table-column label="Company" prop="company">
              <template #default="{row}">
                <el-input v-model="row.company"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="Position" prop="position">
              <template #default="{row}">
                <el-input v-model="row.position"></el-input>
              </template>
            </el-table-column>
            <el-table-column label="Left Reason" prop="leftreason">
              <template #default="{row}">
                <el-input v-model="row.leftreason"></el-input>
              </template>
            </el-table-column>
          </el-table>
          <el-button @click="addRelation(workbacform)">添加</el-button>
        </el-col>
      </el-row>
      <el-row>
        <el-col class="list_title" :span="1">
          <p>家庭</p>
          <p>主要</p>
          <p>成员</p>
          <p>以及</p>
          <p>社会</p>
          <p>关系</p>
        </el-col>
        <el-col class="list_data" :span="23">
          <el-table :data="applicantRelationship">

            <el-table-column prop="name" label="姓名">
              <template #default="{row}">
                <el-input v-model="row.name"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="age" label="年龄">
              <template #default="{row}">
                <el-input v-model="row.age"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="relation" label="与本人关系">
              <template #default="{row}">
                <el-input v-model="row.relation"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="company" label="工作单位">
              <template #default="{row}">
                <el-input v-model="row.company"></el-input>
              </template>
            </el-table-column>
            <el-table-column prop="position" label="岗位或职务">
              <template #default="{row}">
                <el-input v-model="row.position"></el-input>
              </template>
            </el-table-column>

          </el-table>
          <el-button @click="addRelation(applicantRelationship)">添加</el-button>
        </el-col>
      </el-row>

      <el-form-item label="头像" prop="avatar">
        <el-upload
          class="avatar-uploader"
          :file-list="fileList"
          :before-upload="beforeUpload"
          :show-file-list="true"
        >
          <template #default>
            <img
              v-if="fileList[0]"
              :src="avatar"
              class="avatar"
              alt="avatar"
              @click="()=>{logMessage(avatar)}"
            >
            <el-icon v-else>
              <Picture/>
            </el-icon>

          </template>
          <template #trigger>
            <el-button size="small" type="primary">选择文件</el-button>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="申请人签名" prop="avatar">
        <el-upload
          class="avatar-uploader"
          :file-list="fileList"
          :before-upload="beforeUploadSig"
          :show-file-list="true"
        >
          <template #default>
            <img
              v-if="fileList[1]"
              :src="sig"
              class="avatar"
              alt="avatar"
              @click="()=>{logMessage(avatar)}"
            >
            <el-icon v-else>
              <Picture/>
            </el-icon>

          </template>
          <template #trigger>
            <el-button size="small" type="primary">选择文件</el-button>
          </template>
        </el-upload>
      </el-form-item>
      <el-form-item label="申请职位" prop="positionApplyingFor">
        <el-select v-model="form.positionApplyingFor" placeholder="请选择">
          <el-option
            v-for="item in positions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="申请部门" prop="departmentId">
        <el-select v-model="form.departmentId" placeholder="请选择">
          <el-option
            v-for="item in departments"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm">提交</el-button>
        <el-button @click="resetForm">重置</el-button>
        <el-button @click="fillForm">test</el-button>
      </el-form-item>
    </el-form>

<!--    <el-avatar :src="'/getavatar/'+this.form.portraitUrl"></el-avatar>-->
  </div>
</template>

<script>
import {ElMessage} from "element-plus";
import {submitApplicant, submitAvatar, submitSignature} from "../api/applicantForm.js";
import {randomDate} from "../utils/Date.js";
import AvatarUploader from "./AvatarUploader.vue";
import {reactive} from "vue";
import {getDepartments} from "../fetchData.js";
import {HttpStatusCode} from "axios";

export default {
  name: "ApplicantForm",
  components: {AvatarUploader},
  async mounted() {
    let departments = await getDepartments('')
    departments.forEach((item) => {
      this.departments.push({value: item.id, label: item.name})
    })
  },
  data() {
    return {
      departments: [],
      positions: [
        {value: '前端开发', label: '前端开发'},
        {value: '后端开发', label: '后端开发'},
        {value: '测试', label: '测试'},
        {value: '运维', label: '运维'},
        {value: '产品经理', label: '产品经理'},
        {value: 'UI设计', label: 'UI设计'},
        {value: '运营', label: '运营'},
        {value: '市场', label: '市场'},
        {value: '销售', label: '销售'},
        {value: '人事', label: '人事'},
        {value: '财务', label: '财务'},
        {value: '法务', label: '法务'},
        {value: '行政', label: '行政'},
        {value: '其他', label: '其他'},
      ],
      avatar: null,
      sig: null,
      fileList: [],
      uploadUrl: 'http:localhost:8080/',
      form: {
        name: '',
        gender: '',
        birthday: '',
        age: null,
        ethnicGroup: '',
        height: null,
        huJiProvince: '',
        huJiCity: '',
        juZhuProvince: '',
        juZhuCity: null,
        married: '',
        workingYears: null,
        expectedSalary: null,
        eduBacground: '',
        major: '',
        school: '',
        status: '',
        hasRelationship: 0,
        relator: '',
        acceptOvertime: null,
        acceptTransfer: null,
        Card: '',
        Address: '',
        phone: '',
        landline: '',
        portraitUrl: '',
        date: '',
        positionApplyingFor: '',
        departmentId: '',
        signatureUrl: '',
      },
      eduform: [
        {
          start: null,
          end: null,
          school: 'university',
          major: 'computer',
        },
      ],
      workbacform: [
        {
          start: null,
          end: null,
          company: '',
          position: '',
          leftreason: '',
        },
      ],
      applicantRelationship: [
        {
          name: '1',
          age: '2',
          relation: '3',
          company: '4',
          position: '5',
        }
      ],


    }
  },
  methods: {
    beforeUpload(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        ElMessage.error('仅支持上传 JPG/PNG 格式的图片！');
        return false;
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        ElMessage.error('头像大小不能超过 2MB！');
        return false;
      }
      this.fileList.push(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (event) => {
        this.avatar = event.target.result;
      };
      return false;
    },
    beforeUploadSig(file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        ElMessage.error('仅支持上传 JPG/PNG 格式的图片！');
        return false;
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        ElMessage.error('头像大小不能超过 2MB！');
        return false;
      }
      this.fileList.push(file);
      const reader = new FileReader();
      reader.readAsDataURL(file);
      reader.onload = (event) => {
        this.sig = event.target.result;
      };
      return false;
    },

    handlePreview(file) {
      window.open(file.url, '_blank');
    },
    handleRemove(file, fileList) {
      const index = fileList.indexOf(file);
      fileList.splice(index, 1);
      this.avatar = '';
    },

    addRelation(obj) {
      const newObj = {}
      for (const key in obj[0]) {
        if (obj[0].hasOwnProperty(key)) {
          newObj[key] = ''
        }
      }
      obj.push(newObj)
    },

    async submitForm() {
      // console.log(this.avatar)

      let filename = await submitAvatar(this.fileList[0])
      let filename2 = await submitSignature(this.fileList[1])
      if (filename === HttpStatusCode.InternalServerError) {
        ElMessage('头像上传失败,终止')
        return
      }
      if (filename2 === HttpStatusCode.InternalServerError) {
        ElMessage('签名上传失败,终止')
        return
      }
      this.form.portraitUrl = filename
      this.form.signatureUrl = filename2
      let result = await submitApplicant(this.form, this.eduform, this.workbacform, this.applicantRelationship)
      if (result === HttpStatusCode.InternalServerError) {
        ElMessage('提交表单失败，终止')
        return
      }
      ElMessage('提交成功')
    }
    ,
    logMessage(m) {
      ElMessage(m + '')
      console.log(m)
    },
    resetForm() {
      this.form.index = null;
      this.form.name = '';
      this.form.gender = '';
      this.form.birthday = '';
      this.form.age = null;
      this.form.ethnicGroup = '';
      this.form.height = null;
      this.form.huJiProvince = '';
      this.form.huJiCity = '';
      this.form.juZhuProvince = '';
      this.form.juZhuCity = null;
      this.form.married = 0;
      this.form.workingYears = null;
      this.form.expectedSalary = null;
      this.form.eduBacground = '';
      this.form.major = '';
      this.form.school = '';
      this.form.status = '';
      this.form.hasRelationship = 0;
      this.form.relator = '';
      this.form.acceptOvertime = 0;
      this.form.acceptTransfer = 0;
      this.form.Card = '';
      this.form.Address = '';
      this.form.phone = '';
      this.form.landline = '';
      this.form.portraitUrl = '';
      this.form.date = '';
      this.form.positionApplyingFor = '';
      this.form.signatureUrl = '';
    },
    fillForm() {
      {

        this.applicantRelationship.forEach((item) => {
          item.name = `Test family ${Math.floor(Math.random() * 1000)}`
          item.age = Math.floor(Math.random() * 50) + 20;
          item.relation = '父亲'
          item.company = 'xyz company'
          item.position = 'xyz job'
        })
        // 自动填充 eduform 数组中所有对象
        this.eduform.forEach((row, index) => {
          row.start = randomDate()
          row.end = randomDate()
          row.school = `xyz 学院 ${index + 1}`
          row.major = `xyz major ${index + 1}`
          row.leftreason = `xyz reason ${index + 1}`
        })

// 自动填充 workbacform 数组中所有对象
        this.workbacform.forEach((row, index) => {
          row.start = randomDate()
          row.end = randomDate()
          row.company = `xyz 公司 ${index + 1}`
          row.position = `xyz 职位 ${index + 1}`
          row.leftreason = `xyz 原因 ${index + 1}`
        })
      }
      this.form.name = `Test User ${Math.floor(Math.random() * 1000)}`;
      this.form.gender = Math.random() < 0.5 ? 'M' : 'F';
      this.form.birthday = '1990-01-01';
      this.form.age = Math.floor(Math.random() * 50) + 20;
      this.form.ethnicGroup = 'Han';
      this.form.height = Math.floor(Math.random() * 50) + 150;
      this.form.huJiProvince = 'Guangdong';
      this.form.huJiCity = 'Shenzhen';
      this.form.juZhuProvince = 'Guangdong';
      this.form.juZhuCity = 'Guangzhou';
      this.form.married = Math.random() < 0.5;
      this.form.workingYears = Math.floor(Math.random() * 20);
      this.form.expectedSalary = Math.floor(Math.random() * 100000);
      this.form.eduBacground = Math.random() < 0.5 ? '专科' : '本科';
      this.form.major = 'Computer Science';
      this.form.school = 'xyz大学';
      this.form.status = '在职流动';
      this.form.hasRelationship = Math.random() < 0.5;
      this.form.relator = `Test User ${Math.floor(Math.random() * 1000)}`;
      this.form.acceptOvertime = Math.random() < 0.5;
      this.form.acceptTransfer = Math.random() < 0.5;
      this.form.Card = '123456789012345678';
      this.form.Address = 'Guangdong Shenzhen';
      this.form.phone = '13800000000';
      this.form.landline = '1234-12345678';

      this.form.date = '2023-04-20';

    },
  }
  ,
  computed: {
    getUrl() {
      return 'getavatar/' + this.form.portraitUrl;
    }
  }
}
</script>

<style scoped>
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  display: block;
  margin: 0 auto;
  margin-top: 10px;
  text-align: center;
}

.avatar-uploader-icon:before {
  content: '\e62b';
  font-family: 'element-icons';
}

el-input {

}

.el-row {
  margin-bottom: 20px;
}

.el-col {
  border-radius: 4px;
}

.list_title {
  background: lightgrey;
}

.list_data {
  background: lightgrey;
}

.list_row {

}
</style>