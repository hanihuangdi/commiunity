package com.example.demo.service;


import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
   @Autowired
    UserMapper userMapper;
    public void saveUser(User user){

       //User user1 = usermapper.findByUserId(user.getAccountId());
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
         List<User> users = userMapper.selectByExample(example);

       if(users.size()!=0){
          // usermapper.update(user);
           UserExample example1 = new UserExample();
           user.setGmtCreat(null);
           user.setGmtCreat(System.currentTimeMillis());
           userMapper.updateByExampleSelective(user, example1);

       }
       else{
           //usermapper.insert(user);
           userMapper.insert(user);
       }
    }
}
