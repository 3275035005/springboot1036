<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="订单号">
        <el-input v-model="dataVo.orderId" placeholder="请输入订单号"/>
      </el-form-item>
      <el-form-item label="订单状态">
        <el-select v-model="dataVo.status" clearable placeholder="请选择订单状态">
          <el-option value="0" label="未开始"/>
          <el-option value="1" label="加工中"/>
          <el-option value="2" label="加工完成"/>
          <el-option value="3" label="订单已发货"/>
          <el-option value="4" label="订单完成"/>
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
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-table
            :data="props.row.orderList"
            border
            fit
            highlight-current-row
            empty-text="暂无数据"
          >
            <el-table-column align="center" label="封面">
              <template slot-scope="scope">
                <img
                  style="width: 80px; height: 80px"
                  :src="scope.row.productPicture"/>
              </template>
            </el-table-column>
            <el-table-column prop="productName" label="电子零件名称"/>
            <el-table-column prop="productNum" label="加工数量"/>
            <el-table-column prop="number" label="完工数量"/>
            <el-table-column prop="productPrice" label="小计"/>
          </el-table>
        </template>
      </el-table-column>
      <el-table-column prop="orderNo" label="加工单号"/>
      <el-table-column prop="userName" label="下单用户"/>
      <el-table-column prop="status" label="订单进度">
        <template slot-scope="scope">
          <el-tag type="warning" v-if="scope.row.status === '0'">未开始</el-tag>
          <el-tag type="primary" v-if="scope.row.status === '1'">加工中</el-tag>
          <el-tag type="success" v-if="scope.row.status === '2'">加工完成</el-tag>
          <el-tag type="danger" v-if="scope.row.status === '3'">订单已发货</el-tag>
          <el-tag type="danger" v-if="scope.row.status === '4'">订单完成</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="下单时间"/>
      <el-table-column prop="processUserName" label="项目经理"/>
      <el-table-column prop="complete" label="完工日期"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" icon="el-icon-edit" v-if="scope.row.status === '0'" @click="startDeliver(scope.row)">开始加工</el-button>
          <el-button type="primary" size="mini" icon="el-icon-edit" v-if="scope.row.status === '2' || scope.row.status === '3'" @click="sendDeliver(scope.row.id)">确认发货</el-button>

        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="配置项目经理"
      :visible.sync="dialogVisible"
      width="30%"
    >

      <el-form :model="formData" label-position="left" label-width="100px">
        <el-form-item label="加工单号">
          <el-input v-model="formData.orderNo" placeholder="请填写加工单号" :disabled="true"/>
        </el-form-item>
        <el-form-item label="项目经理">
          <el-select v-model="formData.processUserId"  placeholder="请选择项目经理" size="large" >
            <el-option
              v-for="item in userList"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
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
import { pageQuery, startBuilding, sendOrder} from '@/api/order'
import { getUserAll } from '@/api/user'
export default {
  data() { // 定义变量和初始值
    return {
      list: [], // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      formData:{},
      userList:[],
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList();
    this.init();
  },
  methods: { // 创建具体的方法, 定义的方法
    init(){
      getUserAll().then(res=>{
        this.userList = res.data.data
      })
    },
    // 查询
    getList(page = 1) {
      this.page = page // 获取用户点击的页码赋值
      pageQuery(this.page, this.limit, this.dataVo)
        .then(response => { // 请求成功
          this.list = response.data.rows.records
          this.total = response.data.rows.total
        })
    },

    sendDeliver(id){
      this.$confirm('此操作将电子零件发货, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'success'

      }).then(() => {
        sendOrder(id)
          .then(response => {
            this.$message({
              type: 'success',
              message: '发货成功！'
            })

            this.getList()
          })
      })

    },


    startDeliver(row){
      this.formData = row;
      this.dialogVisible = true;
    },
    cancelBtn(){
      this.dialogVisible = false;
      this.getList();
    },
    // 确认按钮
    handleBtn() {
      // 关闭弹窗
      this.dialogVisible = false
      startBuilding({
        id: this.formData.id,
        processUserId: this.formData.processUserId
      })
        .then(response => { // 请求成功
          this.$message({
            type: 'success',
            message: '提交订单成功！'
          })
          this.getList()
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
