package com.cn.product.controller.admin;


import com.cn.product.entity.Message;
import com.cn.product.service.MessageService;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 留言建议 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService service;

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
            @RequestBody Message data){
        PageResult pageResult = service.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }


    /**
     * 留言建议回复
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody Message data){
        service.updateById(data);
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
}

