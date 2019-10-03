package com.example.demo.service;

import com.example.demo.mapper.Usermapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    Usermapper usermapper;
    public void saveUser(User user){
       User user1 = usermapper.findByUserId(user.getAccountId());
       if(user1!=null){
           usermapper.update(user);
       }
       else{
           usermapper.insert(user);
       }
    }
}
