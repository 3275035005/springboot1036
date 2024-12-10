<template>
  <div class="app-container">
    <template>
      <div class="app-container">
        <el-form label-width="120px">
          <el-form-item label="用户名">
            <el-input v-model="personageVo.username" :disabled="true"/>
          </el-form-item>
          <el-form-item label="姓名">
            <el-input v-model="personageVo.name"/>
          </el-form-item>
          <el-form-item label="手机号">
            <el-input v-model="personageVo.phone" placeholder="请填写手机号"/>
          </el-form-item>
          <el-form-item label="地址">
            <el-input v-model="personageVo.address" placeholder="请填写地址"/>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="isSaveAndAlter">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>
  </div>
</template>

<script>
import {update, getInfo} from '@/api/user'
import { getToken } from '@/utils/auth'
export default {
  data() {

    return {
      personageVo: {},
    }

  },
  created() {
    this.init();
  },

  methods: {
    init() {
      this.findById(getToken())
    },

    // 修改ger
    isSaveAndAlter() {
      update(this.personageVo)
        .then(response => {
          // 提示信息
          this.$message({
            type: 'success',
            message: '修改成功'
          });
        })
    },

    // 通过id查询
    findById(id) {
      getInfo(id)
        .then(response => {
          console.log(response)
          this.personageVo = response.data.data;
        })
    }
  },

}
</script>
