package com.cn.product.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cn.product.entity.Order;
import com.cn.product.entity.OrderDetail;
import com.cn.product.service.MaterialsService;
import com.cn.product.service.OrderDetailService;
import com.cn.product.service.OrderService;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import com.cn.product.utils.utils.AceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private MaterialsService materialsService;

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
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 订单发货
     * @param id
     * @return
     */
    @PostMapping("sendOrder/{id}")
    public R update(@PathVariable Integer id){
        UpdateWrapper<Order> uw = new UpdateWrapper<>();
        uw.eq("id", id);
        service.update(new Order().setStatus("2"), uw);
        return R.ok();
    }

    /**
     * 开始加工
     * @param order
     * @return
     */
    @PutMapping("startBuilding")
    public R startBuilding(@RequestBody Order order){

        order.setStatus("1");
        service.updateById(order);

        // 更新订单明细订单状态
        List<OrderDetail> orderDetailList = orderDetailService.list(new QueryWrapper<OrderDetail>().eq("order_id", order.getId()));
        for (OrderDetail orderDetail : orderDetailList) {
            orderDetail.setStatus("1");
            orderDetailService.updateById(orderDetail);
        }
        return R.ok();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R delete(@PathVariable Integer id){
        service.remove(new QueryWrapper<Order>().eq("order_id", id));
        return R.ok();
    }


    /**
     * 查询首页
     *  1. 统计柱状图
     *  2. 原材料库存占比
     *  3. 已加工零件占比
     * @return
     */
    @GetMapping("getZzt/{type}")
    public R getZzt(@PathVariable String type){

        Map<String, Object> map = new HashMap<>();
        if(type.equals("1")){
            List<String> day = new ArrayList<>(12);
            List<Integer> number = new ArrayList<>(12);
            // 获取月份天数
            for(int i=1;i<=12;i++){
                day.add(i + "月");
                number.add(0);
            }
            List<Map<String, Object>> datas = service.getZzt(AceUtils.getYear(), 7);
            if(datas != null && datas.size()>0){
                for (Map<String, Object> data : datas) {
                    if(data != null){
                        System.out.println(data);
                        if(StringUtils.isNotEmpty(String.valueOf(data.get("day")))){
                            int ss = AceUtils.getDateOfMonth((String) data.get("day"));
                            number.remove(ss-1);
                            number.add(ss-1, Integer.parseInt(String.valueOf(data.get("number"))));
                        }
                    }
                }
            }

            map.put("day", day);
            map.put("number", number);
        }else if(type.equals("2")){

            List<String> day = new ArrayList<>(31);
            List<Integer> number = new ArrayList<>(31);
            // 获取月份天数
            int month = AceUtils.getYesterday(AceUtils.getThisMonth());
            System.out.println(month);
            for(int i=1;i<=month;i++){
                day.add(i+"号");
                number.add(0);
            }
            List<Map<String, Object>> datas = service.getZzt(AceUtils.getThisMonth(), 10);
            if(datas != null && datas.size()>0){
                for (Map<String, Object> data : datas) {
                    if(data != null){
                        if(StringUtils.isNotEmpty(String.valueOf(data.get("day")))){
                            int ss = AceUtils.getDateOfDay((String) data.get("day"));
                            number.remove(ss-1);
                            number.add(ss-1, Integer.parseInt(String.valueOf(data.get("number"))));
                        }
                    }
                }
            }

            map.put("day", day);
            map.put("number", number);
        }
        // 2. 原材料库存占比
        List<Map<String, Object>> materialsList = materialsService.getZb();
        map.put("materialsList", materialsList);
        // 3. 已加工零件占比
        List<Map<String, Object>> orderList = orderDetailService.getZb();
        map.put("orderList", orderList);

        return R.ok().data("data", map);
    }
}

