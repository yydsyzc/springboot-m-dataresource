package org.spring.springboot.controller;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/2 10:27
 */

import org.spring.springboot.domain.User;
import org.spring.springboot.redis.CommonCacheUtil;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户控制层
 *
 * Created by bysocket on 07/02/2017.
 */
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommonCacheUtil commonCacheUtil;

    /**
     * 根据用户名获取用户信息，包括从库的地址信息
     *
     * @param userName
     * @return
     */
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public User findByName(@RequestParam(value = "userName", required = true) String userName) {
        System.out.println(userName);
        System.out.println(userName);
        return userService.findByName(userName);
    }


    @RequestMapping(value = "/redis",method = RequestMethod.GET)
    public Object getRedis(){
        User user1=new User(21l,"张正1","讲鬼故事1");
        User user2=new User(22l,"张正2","讲鬼故事2");
        User user3=new User(23l,"张正3","讲鬼故事3");
//        commonCacheUtil.setObject("kb",user);
//        List<User> list=new ArrayList<>();
//        list.add(user1);
//        list.add(user2);
//        list.add(user3);
//        commonCacheUtil.setList("userList",list);
        Map<String,User> map=new HashMap<>();
        map.put("u1",user1);
        map.put("u2",user2);
        map.put("u3",user3);
        commonCacheUtil.setMap("userMap",map);
        return commonCacheUtil.getMap("userMap");
    }

    /**
     * @Cacheable(value="condition",condition="#name.length() <= 4")
     * http://localhost:8080/condition?name=ne
     * 第一次访问 会走下面的方法 存入缓存
     * 第二次会直接走缓存
     *
     * http://localhost:8080/condition?name=ne6666
     * 永远不会走缓存  不满足condition="#name.length() <= 4 只会执行方法
     *
     * PS（注意：使用@Cacheable注解必须要再main方法上加@EnableCaching注解
     * 需要注意的是当⼀个⽀持缓存的⽅法在对象内部被调⽤时是不会触发缓存功能的。）
     * @param name
     * @return
     */
    @RequestMapping("/condition")
    @Cacheable(value="condition",condition="#name.length() <= 4")
    public String hello(String name) {
        System.out.println("没有⾛缓存！");
        return "hello "+name;
    }

    /**
     * 同上操作
     * 执行数据库第一次查库
     * 第二次直接缓存取
     * @param userName
     * @return
     */
    @RequestMapping("/cacheable")
    @Cacheable(value="usersCache",key="#userName",condition="#userName.length() <= 6")
    public User getUserByCacheable(String userName){
        User byName = userService.findByName(userName);
        System.out.println("执行了数据库的操作！");
        return byName;
    }


    /**
     *  @CachePut 会⾃动执⾏⽅法，并将结果存⼊缓存。
     * @param userName
     * @return
     */
    @RequestMapping("/cacheput")
    @CachePut(value = "usersCache",key = "#userName")
    public User getPutUser(String userName){
        User byName = userService.findByName(userName);
        return byName;
    }

    @RequestMapping("/allEntries")
    @CacheEvict(value="usersCache", allEntries=true)
    public User allEntries(String userName) {
        User users=userService.findByName(userName);
        System.out.println("执⾏了数据库操作");
        return users;
    }
}