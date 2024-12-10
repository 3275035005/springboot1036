package com.cn.product.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.product.entity.User;
import com.cn.product.service.InformationService;
import com.cn.product.service.OrderService;
import com.cn.product.service.ProductService;
import com.cn.product.service.UserService;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import com.cn.product.utils.utils.AceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;


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
            @RequestBody User data){
        PageResult pageResult = userService.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }

    /**
     * 修改操作
     * @param data
     * @return
     */
    @PutMapping("update")
    public R update(@RequestBody User data){
        userService.updateById(data);
        return R.ok();
    }

    /**
     * 新增操作
     * @param data
     * @return
     */
    @PostMapping("insert")
    public R insert(@RequestBody  User data){
        userService.save(data);
        return R.ok();
    }

    /**
     * 删除操作
     * @param id
     * @return
     */
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable Integer id){
        userService.removeById(id);
        return R.ok();
    }


    /**
     * 平台登录
     * @param user
     * @return
     */
    @PostMapping("login")
    public R login(@RequestBody User user){

        QueryWrapper<User> qw = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = qw.lambda();
        lambda.eq(User::getUsername, user.getUsername());
        User user1 = userService.getOne(qw);
        if(user1 == null){
            return R.error("账号不存在");
        }
        if(!user.getPassword().equals(user1.getPassword())){
            return R.error("密码不正确");
        }
        if(!user1.getRole().equals("1") && !user1.getRole().equals("2")){
            return R.error("账号不存在");
        }
        return R.ok().data("token",user1.getId());
    }
    /**
     * 重置密码 默认密码666666
     * @param id
     * @return
     */
    @PostMapping("resetPassword/{id}")
    public R resetPassword(@PathVariable String id){
        User pUser = userService.getById(id);
        pUser.setPassword("666666");
        userService.updateById(pUser);
        return R.ok();
    }


    /**
     * 修改密码
     */
    @PostMapping("updatePassword")
    public R updatePassword(@RequestBody User data){
        User pUser = userService.getById(data.getId());



        if(!data.getPassword().equals(pUser.getPassword())){
            return R.error("旧密码不正确");
        }
        // 更新密码
        pUser.setPassword(data.getPassword());
        userService.updateById(pUser);
        return R.ok();
    }

    /**
     * 通过用户id获取信息
     * @return
     */
    @GetMapping("info")
    public R info(String token){
        User user = userService.getById(token);
        if("1".equals(user.getRole())){
            user.setRoles(new String[]{"admin"});
        } else  if("2".equals(user.getRole())){
            user.setRoles(new String[]{"project"});
        }

        return R.ok().data("data",user);
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping("getUserAll")
    public R getUserAll(){
        QueryWrapper<User> qw = new QueryWrapper<>();
        LambdaQueryWrapper<User> lambda = qw.lambda();
        lambda.in(User::getRole, "2");
        List<User> list = userService.list(qw);
        return R.ok().data("data",list);
    }

    /**
     * 查询首页统计数据
     * @return
     */
    @GetMapping("getHome")
    public R getHome(){
        Map<String, Integer> map = new HashMap<>();
        int userNumber = userService.count(new QueryWrapper<User>().eq("role", "3"));
        map.put("userNumber",userNumber);
        int projectNumber = userService.count(new QueryWrapper<User>().eq("role", "2"));
        map.put("projectNumber",projectNumber);
        int orderNumber = orderService.count(null);
        map.put("orderNumber", orderNumber);
        int  productNumber = productService.count(null);
        map.put("productNumber", productNumber);
        return R.ok().data("data",map);
    }
}

