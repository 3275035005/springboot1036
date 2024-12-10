<template>
  <div class="dashboard-container">
    <el-row class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('newVisitis')">
          <div class="card-panel-icon-wrapper icon-people">
            <svg-icon icon-class="user" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              普通用户
            </div>
            <count-to :start-val="0" :end-val="indexData.userNumber" :duration="1000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-message">
            <svg-icon icon-class="user" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              项目经理
            </div>
            <count-to :start-val="0" :end-val="indexData.projectNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              订单量
            </div>
            <count-to :start-val="0" :end-val="indexData.orderNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel" @click="handleSetLineChartData('purchases')">
          <div class="card-panel-icon-wrapper icon-money">
            <svg-icon icon-class="form" class-name="card-panel-icon"/>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              电子零件量
            </div>
            <count-to :start-val="0" :end-val="indexData.productNumber" :duration="5000" class="card-panel-num"/>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-card class="box-card" style="margin: 10px">
        <div slot="header" class="clearfix">
          <span>订单变化统计图</span>
          <el-radio-group v-model="type"  style="float: right;"  @change="init">
            <el-radio-button label="1">按月</el-radio-button>
            <el-radio-button label="2">按日</el-radio-button>
          </el-radio-group>
        </div>
        <!-- 统计图容器 -->
        <div id="main" style="width: 100%;height: 270px;" />
      </el-card>
    </el-row>
    <el-row >
      <el-col :span="12">
        <el-card class="box-card" style="margin: 10px">
          <div slot="header" class="clearfix">
            <span>原材料库存占比</span>
          </div>
          <!-- 统计图容器 -->
          <div id="main1" style="width: 100%;height: 350px;" />
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card class="box-card" style="margin: 10px">
          <div slot="header" class="clearfix">
            <span>已加工零件占比</span>
          </div>
          <!-- 统计图容器 -->
          <div id="main2" style="width: 100%;height: 350px;" />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {mapGetters} from 'vuex'
import CountTo from 'vue-count-to'
import { getHome } from '@/api/user'
import echarts from "echarts";
import { getZzt } from '@/api/order'
export default {
  name: 'Dashboard',
  components: {
    CountTo
  },
  computed: {

    ...mapGetters([
      'name',
      'roles'
    ]),
  },
  created() {
    this.init()
  },
  methods: {
    init(){
      getHome().then(res=>{
        this.indexData = res.data.data
      })
      this.getZzt();
    },
    getZzt(){
      getZzt(this.type).then(res=>{
        var echarts = require('echarts')
        // 初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'))
        // 配置参数
        var option = {
          xAxis: {
            type: 'category',
            axisLine: {
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisTick: {
              alignWithLabel: true,
              lineStyle: {
                color: '#D7DDE4'
              }
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: false,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            data:  res.data.data.day,
          },
          yAxis: {
            axisLine: {
              show: false
            },
            axisTick: {
              show: false
            },
            axisLabel: {
              textStyle: {
                color: '#7F8B9C'
              }
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: '#F5F7F9'
              }
            },
            type: 'value'
          },
          series: [{
            name: '订单量',
            data:  res.data.data.number,
            type: 'bar',
            tooltip: true,
            smooth: true,
            symbol: 'none',
            itemStyle: {
              normal: {
                barBorderRadius: [3, 3, 0, 0],
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {
                    offset: 0,
                    color: '#00CACA'
                  },
                  {
                    offset: 0.5,
                    color: '#00CACA'
                  },
                  {
                    offset: 1,
                    color: '#00CACA'
                  }
                ])
              }
            }
          }],
          grid: {
            left: 25,
            right: 25,
            bottom: 5,
            top: 30,
            containLabel: true
          },
          tooltip: {
            trigger: 'axis'
          }
        }
        myChart.setOption(option)


        // 初始化echarts实例
        var myChart1 = echarts.init(document.getElementById('main1'))
        // 配置参数
        var option1 = {
          title: {
            text: '原材料库存占比',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '库存数量',
              type: 'pie',
              radius: '50%',
              data: res.data.data.materialsList,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  color:function(params) {
                    //自定义颜色
                    var colorList = [
                      '#2f9bff',
                      '#00FFFF',
                      '#00FF00',
                      '#FFFF00',
                      '#FF8C00',
                      '#FF0000',
                      '#FE8463',
                      '#ff4d4f',
                      '#77e19d',
                      '#a3d3ff'
                    ];
                    return colorList[params.dataIndex]
                  }
                }
              }
            }
          ]
        }
        myChart1.setOption(option1)

        // 初始化echarts实例
        var myChart2 = echarts.init(document.getElementById('main2'))
        // 配置参数
        var option2 = {
          title: {
            text: '已加工零件占比',
            left: 'center'
          },
          tooltip: {
            trigger: 'item'
          },
          legend: {
            orient: 'vertical',
            left: 'left'
          },
          series: [
            {
              name: '加工数量',
              type: 'pie',
              radius: '50%',
              data: res.data.data.orderList,
              emphasis: {
                itemStyle: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                }
              },
              itemStyle: {
                emphasis: {
                  shadowBlur: 10,
                  shadowOffsetX: 0,
                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                },
                normal:{
                  color:function(params) {
                    //自定义颜色
                    var colorList = [
                      '#2f9bff',
                      '#00FFFF',
                      '#00FF00',
                      '#FFFF00',
                      '#FF8C00',
                      '#FF0000',
                      '#FE8463',
                      '#ff4d4f',
                      '#77e19d',
                      '#a3d3ff'
                    ];
                    return colorList[params.dataIndex]
                  }
                }
              }
            }
          ]
        }
        myChart2.setOption(option2)
      })
    },
    handleSetLineChartData(type) {
      this.$emit('handleSetLineChartData', type)
    }
  },
  data() {
    return {
      indexData:{
        userNumber: 0,
        projectNumber:0,
        productNumber:0,
        orderNumber:0
      },
      type: '1',
      activeNames: ['1', '2', '3', '4']
    };
  },
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }

  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}

.panel-group {
  margin-top: 18px;

  .card-panel-col {
    margin-bottom: 32px;
  }

  .card-panel {
    height: 108px;
    cursor: pointer;
    font-size: 12px;
    position: relative;
    overflow: hidden;
    color: #666;
    background: #fff;
    box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
    border-color: rgba(0, 0, 0, .05);

    &:hover {
      .card-panel-icon-wrapper {
        color: #fff;
      }

      .icon-people {
        background: #40c9c6;
      }

      .icon-message {
        background: #36a3f7;
      }

      .icon-money {
        background: #f4516c;
      }

      .icon-shopping {
        background: #34bfa3
      }
    }

    .icon-people {
      color: #40c9c6;
    }

    .icon-message {
      color: #36a3f7;
    }

    .icon-money {
      color: #f4516c;
    }

    .icon-shopping {
      color: #34bfa3
    }

    .card-panel-icon-wrapper {
      float: left;
      margin: 14px 0 0 14px;
      padding: 16px;
      transition: all 0.38s ease-out;
      border-radius: 6px;
    }

    .card-panel-icon {
      float: left;
      font-size: 48px;
    }

    .card-panel-description {
      float: right;
      font-weight: bold;
      margin: 26px;
      margin-left: 0px;

      .card-panel-text {
        line-height: 18px;
        color: rgba(0, 0, 0, 0.45);
        font-size: 16px;
        margin-bottom: 12px;
      }

      .card-panel-num {
        font-size: 20px;
      }
    }
  }
}

@media (max-width: 550px) {
  .card-panel-description {
    display: none;
  }

  .card-panel-icon-wrapper {
    float: none !important;
    width: 100%;
    height: 100%;
    margin: 0 !important;

    .svg-icon {
      display: block;
      margin: 14px auto !important;
      float: none !important;
    }
  }
}
</style>
