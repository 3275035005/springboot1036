package com.cn.product.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.product.entity.Materials;
import com.cn.product.service.MaterialsService;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 原材料 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/materials")
public class MaterialsController {

    @Autowired
    private MaterialsService service;
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
            @RequestBody Materials data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Materials data){
        service.updateById(data);
        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody Materials data){
        service.save(data);
        return R.ok();
    }

    /**
     * 删除操作
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R delete(@PathVariable Integer id){
        service.removeById(id);
        return R.ok();
    }


    /**
     * 查询所有原材料
     * @return
     */
    @GetMapping("getMaterialsAll")
    public R getMaterialsAll(){
        List<Materials> list = service.list(new QueryWrapper<Materials>().orderByDesc("create_time"));
        return R.ok().data("data", list);
    }
}

