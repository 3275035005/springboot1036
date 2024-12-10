<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="材料名称">
        <el-input v-model="dataVo.name" placeholder="请输入材料名称" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)" v-if="roles.includes('admin')" >新增材料</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column
        label="序号"
        width="60"
        align="center"
      >
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}  <!--序号,算法-->
        </template>
      </el-table-column>
      <el-table-column prop="name" label="材料名称" />
      <el-table-column prop="num" label="库存" />
      <el-table-column prop="deposit" label="存放位置" />
      <el-table-column prop="introduce" label="材料介绍" />
      <el-table-column prop="createTime" label="创建时间" />
      <el-table-column label="操作" align="center" v-if="roles.includes('admin')">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">补库存</el-button>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="材料名称">
          <el-input v-model="formData.name" placeholder="请填写材料名称" :disabled="dialogVisibleFlag"/>
        </el-form-item>
        <el-form-item label="库存">
          <el-input-number v-model="formData.num" :min="0" placeholder="请填写库存"/>
        </el-form-item>
        <el-form-item label="存放位置"  v-if="!dialogVisibleFlag">
          <el-input v-model="formData.deposit" placeholder="请填写存放位置"/>
        </el-form-item>
        <el-form-item label="材料介绍"  v-if="!dialogVisibleFlag">
          <el-input
            type="textarea"
            :rows="4"
            placeholder="请输入材料介绍"
            v-model="formData.introduce">
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelBtn">取 消</el-button>
        <el-button type="primary" @click="handleBtn">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      style="padding: 30px 0; text-align: center;"
      :page-size="limit"
      layout="total, prev, pager, next, jumper"
      :total="total"
      @current-change="getList"
    />
  </div>
</template>
<script>
import {pageQuery, update, insert, deleteById} from '@/api/materials'
import { mapGetters } from 'vuex'

export default {
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dataVo: {}, // 条件封装对象
      dialogVisible: false,
      formData: {},
      title: '',
      dialogVisibleFlag: false,
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: {

    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },

    // 修改和更新功能
    updateAndSave(row) {
      this.dialogVisibleFlag = false
      if (row == null) {
        this.title = '新增材料信息';
        this.formData = {
          num: 0
        }
      } else {
        this.title = '补库存';
        this.formData = row;
        this.dialogVisibleFlag = true
      }
      this.dialogVisible = true
    },

    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      if (this.formData.id) {
        update(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '补仓成功！'
            })
            this.getList()
          })
      } else {
        insert(this.formData)
          .then(response => { // 请求成功
            this.$message({
              type: 'success',
              message: '新增成功！'
            })
            this.getList()
          })
      }
    },

    // 删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'

      }).then(() => { // 确定执行的方法
        deleteById(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '删除成功！'
              })
              // 刷新表格
              this.getList()
            } else {
              this.$message({
                type: 'error',
                message: '删除失败！'
              })
            }
          })
      })
    },
    cancelBtn(){
      this.dialogVisible = false;
      this.getList();
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {}
      this.getList()
    }
  }
}
</script>
