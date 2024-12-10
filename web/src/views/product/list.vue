<template>
  <div class="app-container">
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="电子零件名称">
        <el-input v-model="dataVo.productName" placeholder="请输入电子零件名称"/>
      </el-form-item>
      <el-form-item label="电子零件分类">
        <el-select
          v-model="dataVo.categoryId"
          placeholder="请选择电子零件分类"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否推荐">
        <el-select v-model="dataVo.type" placeholder="请选择是否推荐">
          <el-option label="否" value="0"/>
          <el-option label="是" value="1"/>
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
      <el-table-column align="center" label="封面">
        <template slot-scope="scope">
          <img
            style="width: 80px; height: 80px"
            :src="scope.row.productPicture"/>
        </template>
      </el-table-column>
      <el-table-column prop="productName" label="电子零件名称"/>
      <el-table-column prop="productTitle" label="电子零件标题"/>
      <el-table-column prop="categoryName" label="电子零件分类"/>
      <el-table-column prop="productPrice" label="原价"/>
      <el-table-column prop="productSellingPrice" label="现价"/>
      <el-table-column prop="type" label="是否推荐">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type === '0'" type="warning">否</el-tag>
          <el-tag v-if="scope.row.type === '1'" >是</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="productSales" label="销量"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column label="操作" align="center" width="300">
        <template slot-scope="scope">
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
        <el-tabs v-model="activeName" type="card">
          <el-tab-pane label="电子零件信息" name="left">
            <el-form-item label="电子零件名称">
              <el-input v-model="formData.productName"  placeholder="请填写电子零件名称"/>
            </el-form-item>
            <el-form-item label="电子零件介绍">
              <el-input v-model="formData.productTitle"  type="textarea"
                        :rows="2" placeholder="请填写电子零件介绍"/>
            </el-form-item>
            <el-form-item label="电子零件号">
              <el-input v-model="formData.partNumber" placeholder="请填写电子零件号"/>
            </el-form-item>
            <el-form-item label="电子零件分类">
              <el-select
                v-model="formData.categoryId"
                placeholder="请选择电子零件分类"
              >
                <el-option
                  v-for="item in categoryList"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="是否推荐">
              <el-select v-model="formData.type" placeholder="请选择是否推荐">
                <el-option label="否" value="0"/>
                <el-option label="是" value="1"/>
              </el-select>
            </el-form-item>
            <el-form-item label="电子零件原价">
              <el-input v-model="formData.productPrice" placeholder="请填写电子零件原价"/>
            </el-form-item>
            <el-form-item label="电子零件现价">
              <el-input v-model="formData.productSellingPrice"  placeholder="请填写电子零件现价"/>
            </el-form-item>
            <el-form-item label="封面">
              <el-upload
                ref="upload"
                action="http://localhost:9002/files/file"
                :on-success="fileHandleSuccess"
                accept="image/*"
                class="avatar-uploader"
              >
                <img v-if="formData.productPicture" :src="formData.productPicture" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="封面详情">
              <div v-for="(item, idx) in formData.imageList" :key="item + idx" >
                <img class="img" :src="item" style="width: 178px;height: 178px"/>
                <i class="el-icon-circle-close img-close" @click="handleRemove(idx)"></i>
              </div>
              <el-upload
                v-if="formData.imageList.length < 5"
                action="http://localhost:9002/files/file"
                ref="imgUpload"
                accept="image/*"
                :on-success="fileHandleSuccess2"
                :before-upload="beforeAvatarUpload"
                multiple
                :show-file-list="false"
                :on-exceed="handleExceed"
                :limit="5 - formData.imageList.length"
                class="avatar-uploader"
              >
                <i class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-tab-pane>
          <el-tab-pane label="配置加工材料" name="right">
            <div class="filter-container">
              <el-button class="filter-item" type="primary" icon="el-icon-plus" size="small" plain @click="handleAdd">
                添加
              </el-button>

              <el-table
                :data="materials"
                :border="true"
                style="width: 100%;"
              >
                <el-table-column
                  label="材料名称"
                  width="250"
                  align="center"
                >
                  <template v-slot="scope">
                    <el-select
                      v-model="scope.row.id"
                      placeholder="请选择材料名称"
                    >
                      <el-option
                        v-for="item in materialsList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      />
                    </el-select>
                  </template>

                </el-table-column>

                <el-table-column
                  label="材料数量"
                  align="center"
                >
                  <template v-slot="scope">
                    <el-input-number v-model="scope.row.num" :min="1"/>
                  </template>
                </el-table-column>

                <el-table-column
                  label="操作"
                  align="center"
                  width="100px"
                >
                  <template v-slot="scope">
                    <el-button type="danger" icon="el-icon-delete" circle @click="removeItem(scope.$index)" />
                  </template>
                </el-table-column>

              </el-table>

            </div>

          </el-tab-pane>
        </el-tabs>
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
import { deleteById, update, insert, pageQuery} from '@/api/product'
import { getCategoryAll } from '@/api/category'
import { getMaterialsAll } from '@/api/materials'
import Tinymce from '@/components/Tinymce'
export default {
  components: { Tinymce }, // 注册组件
  data() { // 定义变量和初始值
    return {
      list: null, // 查询之后接口返回集合
      dataVo: {}, // 条件封装对象
      page: 1, // 当前页
      limit: 9, // 每页记录数
      total: 100, // 总页数
      dialogVisible: false,
      activeName: 'left',
      formData: {
        imageList: [],
        productPicture:'',
        productNum: 0,
        materials:'',
      },
      materials: [],
      title: '',
      categoryList:[],
      materialsList:[],
    }
  },
  created() { // 页面渲染之前执行，一般调用method定义方法
    this.getList()
    this.init();
  },
  methods: { // 创建具体的方法, 定义的方法
    init() {
      getCategoryAll().then(res=>{
        this.categoryList = res.data.data
      })
      getMaterialsAll().then(res=>{
        this.materialsList = res.data.data
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

    // 修改和更新功能
    updateAndSave(row) {
      if (row == null) {
        this.title = '新增电子零件信息'
        this.formData = {
          productPicture: '',
          imageList:[],
        }
        this.materials = []
      } else {
        this.title = '修改电子零件信息'
        this.formData = row
        this.materials = JSON.parse(row.materials)
        this.formData.materials = ''
      }
      this.dialogVisible = true
    },

    handleRemove(idx) {
      this.formData.imageList.splice(idx, 1);
    },
    // 上传封面成功
    fileHandleSuccess(response) {
      this.formData.productPicture = response.data.row;
      this.$refs.upload.clearFiles() //去掉文件列表
    },

    // 文件超出限制
    handleExceed() {
      this.$message.error("你最多能发布5张图片");
      this.$refs.imgUpload.clearFiles();
    },

    beforeAvatarUpload(file) {
      const isLt10M = file.size / 1024 / 1024 < 10;
      if (!isLt10M) {
        this.$message.error("上传图片大小不能超过 10MB!");
      }
      return isLt10M
    },
    // 上传图片成功
    fileHandleSuccess2(response) {
      this.formData.imageList.push(response.data.row);
    },

    // 删除材料
    removeItem(index) {
      this.materials.splice(index, 1)
    },

    // 添加材料
    handleAdd() {
      this.materials.push({ id: '', num: 1 })
    },


    // 确认按钮
    handleBtn() {
      if(this.materials.length === 0){
        this.$message({
          type: 'error',
          message: '请配置加工材料！'
        })
        return
      }
      this.formData.materials = JSON.stringify(this.materials);
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
    // 清空按按钮执行的方法
    resetData() {
      // 第一步清空条件数据
      this.dataVo = {};
      this.page = 1; // 当前页
      this.limit = 10; // 当前页
      this.getList()
    },
    cancelBtn(){
      this.dialogVisible = false;
      this.getList();
    },
  }
}
</script>
