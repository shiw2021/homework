<template>
  <div class="app-container">
    <el-form :inline="true" :model="form">
      <el-row :gutter="20">
        <el-col :span="4" :offset="1">
          <el-form-item label="XXX">
            <el-input v-model="form.xxx" placeholder="XXX"></el-input>
          </el-form-item>
        </el-col>
        <el-col :span="5">
          <el-form-item label="XXX">
            <el-input v-model="form.xxx" placeholder="XXX"></el-input>
          </el-form-item>
        </el-col>

        <el-col :span="6" :offset="1">
          <el-form-item>
            <el-button type="primary" @click="getDataPage">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-popover placement="bottom" width="400" trigger="click">
              <el-form :model="insertData">
                <el-form-item label="XXX">
                  <el-input v-model="insertData.xxx"></el-input>
                </el-form-item>
                <el-form-item label="XXX">
                  <el-input v-model="insertData.xxx"></el-input>
                </el-form-item>
                <el-button type="primary" @click="addData">添加</el-button>
              </el-form>
              <template #reference>
                <el-button>添加XXX</el-button>
              </template>

            </el-popover>
          </el-form-item>

        </el-col>
      </el-row>
    </el-form>
    <el-table :data="xxxPage.records" stripe border style="width: 100%;" @cell-mouse-enter="handleCellEnter"
              @cell-mouse-leave="handleCellLeave" @cell-click="handleCellClick">
      <el-table-column prop="A" label="XXX" width="450" align="center">
        <template #default="scope">
          <div class="item">
            <el-input class="item__input" v-model="scope.row.A" placeholder="请输入内容"></el-input>
            <div class="item__txt">{{ scope.row.A }}</div>
          </div>

        </template>
      </el-table-column>
      <el-table-column prop="B" label="XXX" width="300" align="center">
        <template class="item" #default="scope">
          <div class="item">
            <el-input class="item__input" v-model="scope.row.B" placeholder="请输入内容"></el-input>
            <div class="item__txt">{{ scope.row.B }}</div>

          </div>
        </template>
      </el-table-column>
      <el-table-column prop="A" label="XXX" width="100" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.A === 0 ? 'success':'warning'">{{ scope.row.A }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="B" label="XXX" width="100" align="center">
        <template #default="scope">
          <el-tag :type="scope.row.B === 'ADMIN' ? 'danger':'success'">{{ scope.row.B }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <el-button type="primary" @click="toXXX(scope.row)">前往</el-button>
          <el-button type="primary" @click="saveEdit(scope.row)">修改</el-button>
          <el-button type="primary" @click="deleteData(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="block">

      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                     :current-page="xxxPage.current" :page-sizes="[10, 20, 30, 40]" :page-size="xxxPage.size"
                     layout="total, sizes, prev, pager, next, jumper" :total="xxxPage.total"
                     :hide-on-single-page="showPage">
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  name: 'XXX',
  data() {
    return {
      form: {
        xxx: ""
      },
      showPage: false,
      nowPage: 1,
      nowSize: 10,
      // 需要编辑的属性
      editProp: ['A'],
      // 保存进入编辑的cell
      clickCellMap: {},
      insertData: {
        xxx: ""
      },
      xxxPage: {
        current: 1,
        pages: 1,
        size: 10,
        total: 3,
        records: [{
          "A": "a",
          "B": "b"
        }, {
          "A": "a",
          "B": "b"
        }, {
          "A": "a",
          "B": "b"
        }]
      }
    }
  },
  created() {
    this.getDataPage()
  },
  methods: {
    /** 鼠标移入cell */
    handleCellEnter(row, column, cell, event) {
      const property = column.property
      if (this.editProp.includes(property)) {
        cell.querySelector('.item__txt')?.classList?.add('item__txt--hover')
      }
    },
    /** 鼠标移出cell */
    handleCellLeave(row, column, cell, event) {
      const property = column.property
      if (this.editProp.includes(property)) {
        cell.querySelector('.item__txt')?.classList?.remove('item__txt--hover')
      }
    },
    /** 点击cell */
    handleCellClick(row, column, cell, event) {
      const property = column.property
      if (this.editProp.includes(property)) {
        // 保存cell
        this.saveCellClick(row, cell)
        cell.querySelector('.item__txt').style.display = 'none'
        cell.querySelector('.item__input').style.display = 'block'
        cell.querySelector('input').focus()
      }
    },
    /** 取消编辑状态 */
    cancelEditable(cell) {
      cell.querySelector('.item__txt').style.display = 'block'
      cell.querySelector('.item__input').style.display = 'none'
    },
    /** 保存进入编辑的cell */
    saveCellClick(row, cell) {
      const id = row.id
      if (this.clickCellMap[id] !== undefined) {
        if (!this.clickCellMap[id].includes(cell)) {
          this.clickCellMap[id].push(cell)
        }
      } else {
        this.clickCellMap[id] = [cell]
      }
    },
    /** 保存修改数据数据 */
    saveEdit(row) {
      const id = row.id
      // 取消本行所有cell的编辑状态
      this.clickCellMap[id].forEach(cell => {
        this.cancelEditable(cell)
      })
      this.clickCellMap[id] = []
      console.log("当前修改行内容：");
      console.log(row);
      // TODO

    },
    /** 修改分页的条数*/
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.nowSize = val
      this.getDataPage()
    },
    /** 切换当前的页数*/
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.nowPage = val
      this.getDataPage()
    },
    /** 删除当前行*/
    deleteData(row) {
      console.log("当前删除的行内容：");
      console.log(row);
      // TODO

      this.getDataPage()

    },
    /** 添加数据*/
    addData() {
      console.log("当前删除的行内容：");
      console.log(this.insertData);
      // TODO

      this.getDataPage()
    },
    /** 跳转到XXX页面*/
    toXXX(row) {
      this.$router.push({
        name: 'XXX',
        params: {
          "A": "A",
          row: row
        }
      })
    },
    /** 获取分页数据 */
    getDataPage() {
      var that = this
      var params = {
        xxx: this.form.xxx
      }
      // this.$store.dispatch('xxx', params)
    }
  }
}
</script>

<style lang="scss">
.item {
  .item__input {
    display: none;
    width: 100px;

    /* 调整elementUI中样式 如果不需要调整请忽略 */
    .el-input__inner {
      height: 24px !important;
    }

    /* 调整elementUI中样式 如果不需要调整请忽略 */
    .el-input__suffix {
      i {
        font-size: 12px !important;
        line-height: 26px !important;
      }
    }
  }

  .item__txt {
    box-sizing: border-box;
    border: 1px solid transparent;
    width: 100px;
    line-height: 24px;
    padding: 0 8px;
  }

  .item__txt--hover {
    border: 1px solid #dddddd;
    border-radius: 4px;
    cursor: text;
  }
}
</style>
