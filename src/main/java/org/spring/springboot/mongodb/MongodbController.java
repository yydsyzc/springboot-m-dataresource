package org.spring.springboot.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/7 8:53
 */
@RestController
@RequestMapping("/db")
public class MongodbController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/get")
    public String getMongo(){
        UserEntity userEntity=new UserEntity();
        userEntity.setId(20l);
        userEntity.setUserName("大话西游");
        userEntity.setPassWord("111111");
        userDao.saveUser(userEntity);

        UserEntity u = userDao.findUserByUserName("大话西游");
        System.out.println(u.getPassWord()+u.getId());

        u.setUserName("功夫");
        u.setPassWord("222222");

        userDao.updateUser(u);
        System.out.println(u.getPassWord()+u.getUserName());
        return null;
    }


}
