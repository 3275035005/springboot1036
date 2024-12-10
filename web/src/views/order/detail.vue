<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="加工单号">
        <el-input v-model="dataVo.orderNo" placeholder="请填写加工单号"/>
      </el-form-item>
      <el-form-item label="加工状态">
        <el-select v-model="dataVo.status" placeholder="请选择加工状态">
          <el-option label="加工中" value="0"/>
          <el-option label="加工完成" value="1"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">重置</el-button>
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
      <el-table-column prop="orderNo" label="加工单号" />
      <el-table-column prop="productName" label="电子零件名称"/>
      <el-table-column prop="productNum" label="加工数量" />
      <el-table-column prop="number" label="完工数量" />
      <el-table-column prop="status" label="加工状态">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status == '1'" type="warning">加工中</el-tag>
          <el-tag v-if="scope.row.status == '2'" type="primary">加工完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="accomplishTime" label="加工完成时间"/>

      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column label="操作" align="center" width="200">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" v-if="scope.row.status === '1'" @click="updateAndSave(scope.row)">完工</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 新增修改弹框 -->
    <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="预计完工时间">
          <el-date-picker v-model="formData.predictTime" type="date"  value-format="yyyy-MM-dd" placeholder="请选择预计完工时间"/>
        </el-form-item>
        <el-form-item label="完工数量">
          <el-input-number v-model="formData.number" :min="0" :max="maxNum"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelBtn">取 消</el-button>
        <el-button type="primary" @click="handleBtn">提交</el-button>
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
import { complete, pageQuery } from '@/api/orderDetail'
import { getToken } from '@/utils/auth'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 10, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      formData: {},
      title: '',
      maxNum: 0,
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList();
  },
  methods: { // 创建具体的方法, 定义的方法
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      this.dataVo.processUserId = getToken();
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records;
          this.total = response.data.rows.total;
        })
    },

    cancelBtn(){
      this.dialogVisible = false;
      this.getList();
    },

    // 修改和更新功能
    updateAndSave(row) {
      this.maxNum = (row.productNum -  row.number)

      this.formData = row;
      this.formData.number = 0;
      this.title = '提交完工'
      this.dialogVisible = true
    },

    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      complete(this.formData)
        .then(response => { // 请求成功
          this.$message({
            type: 'success',
            message: '完工成功！'
          })
          this.getList()
        })
        .catch(res=>{
          this.getList()
      })
    },

    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.page = 1;// 当前页
      this.limit = 10; // 每页记录数
      this.dataVo = {};
      this.getList();
    }
  }
}
</script>
