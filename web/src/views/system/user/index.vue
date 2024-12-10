<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="用户名">
        <el-input v-model="dataVo.username" placeholder="请输入用户名"/>
      </el-form-item>
      <el-form-item label="姓名">
        <el-input v-model="dataVo.name" placeholder="请输入姓名"/>
      </el-form-item>
      <el-form-item label="账号角色">
        <el-select v-model="dataVo.role" clearable placeholder="请选择账号角色">
          <el-option value="1" label="管理员"/>
          <el-option value="2" label="项目经理"/>
          <el-option value="3" label="普通用户"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
      <el-button type="primary" @click="updateAndSave(null)">新增</el-button>
    </el-form>
    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      empty-text="暂无数据"
    >

      <el-table-column prop="username" label="用户名"/>
      <el-table-column prop="name" label="姓名"/>
      <el-table-column prop="phone" label="手机号"/>
      <el-table-column prop="address" label="地址"/>
      <el-table-column prop="role" label="账号权限">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.role === '1'">管理员</el-tag>
          <el-tag v-if="scope.row.role === '2'" type="success">项目经理</el-tag>
          <el-tag v-if="scope.row.role === '3'" type="warning">普通用户</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="注册时间"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="resetPasswordInfo(scope.row.id)">重置密码</el-button>
          <el-button type="primary" size="mini" icon="el-icon-edit" @click="updateAndSave(scope.row)">修改</el-button>
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
        <el-form-item label="用户名">
          <el-input v-model="formData.username" :disabled="disabledFlag" placeholder="请填写用户名"/>
        </el-form-item>
        <el-form-item v-if="!disabledFlag" label="用户密码">
          <el-input v-model="formData.password" type="password" placeholder="请填写密码"/>
        </el-form-item>
        <el-form-item label="账号角色">
          <el-select v-model="formData.role" clearable placeholder="请选择账号角色">
            <el-option value="1" label="管理员"/>
            <el-option value="2" label="项目经理"/>
            <el-option value="3" label="普通用户"/>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="formData.name" placeholder="请填写姓名"/>
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="formData.phone" placeholder="请填写手机号"/>
        </el-form-item>
        <el-form-item label="地址">
          <el-input v-model="formData.address" placeholder="请填写地址"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
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
import {deleteById, update, insert, pageQuery, resetPassword} from '@/api/user'

export default {
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      disabledFlag: false,
      formData: {},
      title: '',
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
  },
  methods: { // 创建具体的方法, 定义的方法
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
      this.disabledFlag = false
      if (row == null) {
        this.title = '新增用户信息'
        this.formData = {
          role : '3'
        }
      } else {
        this.title = '修改用户信息'
        this.formData = row
        this.disabledFlag = true
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
              message: '修改成功！'
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

    // 重置密码
    resetPasswordInfo(id) {
      this.$confirm('此操作重置密码为666666, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'

      }).then(() => { // 确定执行的方法
        resetPassword(id)
          .then(response => { // 删除成功执行的方法
            if (response.success) {
              this.$message({
                type: 'success',
                message: '重置密码成功！'
              })
              // 刷新表格
              this.getList()
            }
          })
      })
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
            }
          })
      })
    },
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.page = 1; // 当前页
      this.limit = 10; // 当前页
      this.getList()
    }
  }
}
</script>
