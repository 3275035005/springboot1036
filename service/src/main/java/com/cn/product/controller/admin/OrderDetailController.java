package com.cn.product.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.product.entity.Materials;
import com.cn.product.entity.Order;
import com.cn.product.entity.OrderDetail;
import com.cn.product.entity.Product;
import com.cn.product.service.MaterialsService;
import com.cn.product.service.OrderDetailService;
import com.cn.product.service.OrderService;
import com.cn.product.service.ProductService;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/orderDetail")
public class OrderDetailController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MaterialsService materialsService;

    @Autowired
    private ProductService productService;

    /**
     * 分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("pageQuery/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody Order data){
        PageResult pageResult = orderDetailService.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }



    /**
     * 完成处理
     * @param data
     * @return
     */
    @PostMapping("complete")
    public R complete(@RequestBody OrderDetail data){

        OrderDetail orderDetail = orderDetailService.getById(data.getId());
        if(data.getNumber() > (orderDetail.getProductNum()- orderDetail.getNumber())){
            return R.error("完工数量不能大于加工数量");
        }else if(orderDetail.getProductNum().equals((data.getNumber()+orderDetail.getNumber()))){

            Product product = productService.getById(orderDetail.getProductId());
            String materialsStr = product.getMaterials();

            boolean flagMaterials = true;
            // 判断原材料库存是否充足
            List<Materials> materialsList = JSONObject.parseArray(materialsStr, Materials.class);
            for (Materials materials : materialsList) {
                Materials materialsNew = materialsService.getById(materials.getId());
                if((materials.getNum() *  data.getNumber()) > materialsNew.getNum()){
                    return R.error("原材料"+materialsNew.getName()+"不足，请联系管理员。");
                }
            }

            for (Materials materials : materialsList) {
                Materials materialsNew = materialsService.getById(materials.getId());
                materialsNew.setNum(materialsNew.getNum() - (materials.getNum() *  data.getNumber()));
                materialsService.updateById(materialsNew);
            }


            orderDetail.setStatus("2");
            orderDetail.setNumber((orderDetail.getNumber() + data.getNumber()));
            orderDetail.setAccomplishTime(new Date());
            orderDetail.setPredictTime(data.getPredictTime());
            orderDetailService.updateById(orderDetail);

            // 查询整个订单是否完工
            List<OrderDetail> orderDetailList = orderDetailService.list(new QueryWrapper<OrderDetail>().eq("order_id", orderDetail.getOrderId()));
            boolean flag = true;
            for (OrderDetail detail : orderDetailList) {
                if(!"2".equals(detail.getStatus())){
                    flag = false;
                }
            }

            // 更新订单状态
            if(flag){
                Order order = service.getById(orderDetail.getOrderId());
                order.setComplete(new Date());
                order.setStatus("2");
                service.updateById(order);
            }

        }else{

            Product product = productService.getById(orderDetail.getProductId());
            String materialsStr = product.getMaterials();
            // 判断原材料库存是否充足
            List<Materials> materialsList = JSONObject.parseArray(materialsStr, Materials.class);
            for (Materials materials : materialsList) {
                Materials materialsNew = materialsService.getById(materials.getId());
                if((materials.getNum() *  data.getNumber()) > materialsNew.getNum()){
                    return R.error("原材料"+materialsNew.getName()+"不足，请联系管理员。");
                }

            }

            for (Materials materials : materialsList) {
                Materials materialsNew = materialsService.getById(materials.getId());
                materialsNew.setNum(materialsNew.getNum() - (materials.getNum() *  data.getNumber()));
                materialsService.updateById(materialsNew);
            }

            orderDetail.setPredictTime(data.getPredictTime());
            orderDetail.setNumber((orderDetail.getNumber() + data.getNumber()));
            orderDetailService.updateById(orderDetail);
        }

        return R.ok();
    }

}

