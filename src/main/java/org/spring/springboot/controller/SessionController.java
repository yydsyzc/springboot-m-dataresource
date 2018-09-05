package org.spring.springboot.controller;

import org.spring.springboot.domain.User;
import org.spring.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/3 16:56
 */
@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/setSession")
    public Map<String, Object> setSession (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        request.getSession().setAttribute("message", request.getRequestURL());
        map.put("request Url", request.getRequestURL());
        return map;
    }

    @RequestMapping(value = "/getSession")
    public Object getSession (HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        map.put("sessionId", request.getSession().getId());
        map.put("message", request.getSession().getAttribute("message"));return map;
    }



    //模拟登陆后，打开其他页面继续保持登陆状态
    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request,String userName,Long id){
        String msg="logon failure!";
        User user= userService.findByName(userName);
        if (user!=null && user.getId()==id){
            request.getSession().setAttribute("user",user);
            msg="login successful!";
        }
        return msg;
    }
    //模拟登陆后，打开其他页面继续保持登陆状态
    @RequestMapping(value = "/index")
    public String index (HttpServletRequest request){
        String msg="index content";
        Object user= request.getSession().getAttribute("user");
        if (user==null){
            msg="please login first！";
        }
        return msg;
    }
}
