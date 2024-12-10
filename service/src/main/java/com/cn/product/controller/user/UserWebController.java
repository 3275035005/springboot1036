package com.cn.product.controller.user;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cn.product.entity.*;
import com.cn.product.service.*;
import com.cn.product.utils.page.PageResult;
import com.cn.product.utils.response.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * 对用户平台提供接口
 */
@RestController
@RequestMapping("/web")
public class UserWebController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ShoppingcartService shoppingcartService;

    @Autowired
    private UserService userService;

    @Autowired
    private InformationService informationService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private OrderDetailService orderDetailService;
    /**
     * 登录
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
        if(!user1.getRole().equals("3")){
            return R.error("账号不存在");
        }
        return R.ok().data("user",user1);
    }


    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("register")
    public R register(@RequestBody User user){
        User user1 = userService.getOne(new QueryWrapper<User>().eq("username", user.getUsername()));
        if(user1 != null){
            return R.error("账号已被注册");
        }
        user.setRole("3");
        userService.save(user);

        return R.ok("注册成功");
    }

    /**
     * 找回密码
     * @param user
     * @return
     */
    @PostMapping("retrievePassword")
    public R retrievePassword(@RequestBody User user){
        User user1 = userService.getOne(new QueryWrapper<User>()
                .eq("username", user.getUsername())
                .eq("name", user.getName())
                .eq("phone", user.getPhone()));
        if(user1 == null){
            return R.error("找回失败");
        }
        user1.setPassword(user.getPassword());
        userService.updateById(user1);

        return R.ok("注册成功");
    }

    /**
     * 个人信息查询
     */
    @GetMapping("getUserById/{id}")
    public R getUserById(@PathVariable Integer id){
        User user = userService.getById(id);
        return R.ok().data("row", user);
    }

    /**
     * 个人信息修改
     */
    @PostMapping("updateUser")
    public R updateUser(@RequestBody User user){
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 查询首页数据
     *      1、轮播图 banner
     *      2、新闻 information
     *      3、推荐电子零件
     */
    @GetMapping("getHome")
    public R getHome(){
        Map<String, Object> map = new HashMap<>();
        List<Information> information =  informationService.getList();
        List<Banner> banner =  bannerService.getList();
        List<Product> product =  productService.getList(new Product().setType("1"));
        map.put("banner", banner);
        map.put("information", information);
        map.put("product", product);
        return R.ok().data("data",map);
    }

    /**
     * 查询通知
     * @return
     */
    @GetMapping("getNotice")
    public R getNotice(){
        List<Notice> notice = noticeService.list(new QueryWrapper<Notice>().orderByDesc("create_time"));
        return R.ok().data("list", notice);
    }


    /**
     * 查询新闻详情
     * @param id
     * @return
     */
    @GetMapping("getInformationById/{id}")
    public R getInformationById(@PathVariable Integer id) {
        Information information = informationService.getById(id);
        return R.ok().data("data", information);
    }

    /**
     * 查询电子零件详情
     * @param id
     * @return
     */
    @GetMapping("getProductById/{id}")
    public R getProductById(@PathVariable Integer id) {
        Product product = productService.getListById(id);
        return R.ok().data("data", product);
    }

    /**
     * 查询轮播图详情
     * @param id
     * @return
     */
    @GetMapping("getBannerById/{id}")
    public R getBannerById(@PathVariable Integer id) {
        Banner banner = bannerService.getById(id);
        return R.ok().data("data", banner);
    }


    /**
     * 查询我的购物车
     */
    @GetMapping("getShoppingCart/{id}")
    public R getShoppingCart(@PathVariable Integer id){
        List<Shoppingcart> list = shoppingcartService.getShoppingCart(id);
        return R.ok().data("list", list);
    }

    /**
     * 加入购物车
     */
    @PostMapping("addShoppingCart")
    public R addShoppingCart(@RequestBody Shoppingcart shoppingcart){
        Shoppingcart shoppingcartNew = shoppingcartService.getOne(new QueryWrapper<Shoppingcart>()
                .eq("user_id", shoppingcart.getUserId())
                .eq("product_id", shoppingcart.getProductId()));
        Product product = productService.getById(shoppingcart.getProductId());
        if(shoppingcartNew == null){
            shoppingcart.setNum(1);
            shoppingcartService.save(shoppingcart);
            Shoppingcart row = shoppingcartService.getShoppingCartById(shoppingcart.getId());
            return R.ok("加入购物车成功", 10001).data("row", row);
        }else{

            shoppingcartNew.setNum(shoppingcartNew.getNum() + 1);

            shoppingcartService.updateById(shoppingcartNew);
            return R.ok("该电子零件已在购物车，数量 +1",10002);

        }
    }

    /**
     * 修改我的购物车
     */
    @PostMapping("updateShoppingCart")
    public R updateShoppingCart(@RequestBody Shoppingcart shoppingcart){
        Shoppingcart shoppingcartNew = shoppingcartService.getOne(new QueryWrapper<Shoppingcart>()
                .eq("user_id", shoppingcart.getUserId())
                .eq("product_id", shoppingcart.getProductId()));

        shoppingcartNew.setNum(shoppingcart.getNum());

        shoppingcartService.updateById(shoppingcartNew);
        return R.ok();
    }
    /**
     * 删除购物车
     */
    @PostMapping("deleteShoppingCart")
    public R deleteShoppingCart(@RequestBody Shoppingcart shoppingcart){
        Shoppingcart shoppingcartNew = shoppingcartService.getOne(new QueryWrapper<Shoppingcart>()
                .eq("user_id", shoppingcart.getUserId())
                .eq("product_id", shoppingcart.getProductId()));

       shoppingcartService.removeById(shoppingcartNew);
        return R.ok();
    }

    /**
     * 下单
     */
    @PostMapping("addOrder/{userId}")
    public R addOrder(@PathVariable Integer userId, @RequestBody List<Shoppingcart> shoppingcarts){

        // 获取订单号
        Long orderNo = System.currentTimeMillis();
        // 新增订单
        Order order = new Order();
        order.setStatus("0");
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        orderService.save(order);
        // 生成订单
        for (Shoppingcart shoppingcart : shoppingcarts) {

            if(shoppingcart.isCheck()){
                // 扣减商品库存和加销量
                Product product = productService.getById(shoppingcart.getProductId());

                product.setProductSales(product.getProductSales()+ shoppingcart.getNum() );
                productService.updateById(product);

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setStatus("0");
                orderDetail.setOrderId(order.getId());
                orderDetail.setProductId(shoppingcart.getProductId());
                orderDetail.setProductNum(shoppingcart.getNum());
                orderDetail.setProductPrice(shoppingcart.getPrice());
                orderDetail.setNumber(0);
                orderDetailService.save(orderDetail);
                shoppingcartService.removeById(shoppingcart.getId());
            }

        }
        return R.ok("下单成功");
    }


    /**
     * 查询我的订单
     */
    @GetMapping("getOrder/{id}")
    public R getOrder(@PathVariable Integer id){
        List<Order> list = orderService.getOrder(id);
        return R.ok().data("data", list);
    }



    /**
     * 电子零件分页条件查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @param data 封装查询条件数据
     * @return
     */
    @PostMapping("getProductByCategory/{page}/{limit}")
    public R getPageData(
            @PathVariable int page,
            @PathVariable int limit,
            @RequestBody Product data){
        PageResult pageResult = productService.pageQuery(page, limit, data);
        return R.ok().data("rows",pageResult);
    }


    /**
     * 电子零件所有查询
     * @param page   当前页码
     * @param limit 每页的大小
     * @return
     */
    @PostMapping("getAllProduct/{page}/{limit}")
    public R getAllProduct(
            @PathVariable int page,
            @PathVariable int limit){
        PageResult pageResult = productService.pageQuery(page, limit, new Product());
        return R.ok().data("rows",pageResult);
    }

    /**
     * 所有电子零件分类查询
     */
    @GetMapping("getCategory")
    public R getCategory(){
        List<Category> list = categoryService.list(new QueryWrapper<Category>().orderByDesc("create_time"));
        return R.ok().data("rows",list);
    }


    /**
     * 删除订单
     */
    @DeleteMapping("deleteOrder/{orderId}")
    public R deleteOrder(@PathVariable String orderId){
        orderService.remove(new QueryWrapper<Order>().eq("order_id", orderId));
        return R.ok("删除成功");
    }

    /**
     * 确认收货
     */
    @PostMapping("sendOrder/{orderId}")
    public R sendOrder(@PathVariable String orderId){
        orderService.update(new Order().setStatus("3") ,new QueryWrapper<Order>().eq("order_id", orderId));
        return R.ok("确认收货成功");
    }

    /**
     * 查询我的留言建议
     * @param userId
     * @return
     */
    @GetMapping("getMessage/{userId}")
    public R getMessage(@PathVariable Integer userId){
       List<Message> list =  messageService.getMessageListByUserId(userId);
       return R.ok().data("list", list);
    }
    /**
     * 发布留言建议
     * @param message
     * @return
     */
    @PostMapping("sendMessage")
    public R getMessage(@RequestBody Message message){
        message.setState("0");
        messageService.save(message);
        return R.ok();
    }

    /**
     * 删除留言建议
     */
    @DeleteMapping("messageDeleteById/{id}")
    public R messageDeleteById(@PathVariable Integer id){
        messageService.removeById(id);
        return R.ok();
    }
}
