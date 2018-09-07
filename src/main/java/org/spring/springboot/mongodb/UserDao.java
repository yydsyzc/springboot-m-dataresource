package org.spring.springboot.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @Author: yangzhicheng
 * @Date: 2018/9/7 8:50
 */
public interface UserDao{
    public void saveUser(UserEntity user);
    public UserEntity findUserByUserName(String userName);
    public void updateUser(UserEntity user);
    public void deleteUserById(Long id);
}
