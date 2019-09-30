package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,GMT_CREAT,GTM_MODIFY,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreat},#{gmtModify},#{avatar_url})")
    void insert(User user);
    @Select("select * from user where token =#{token}")
    User findByToken(String token);
    @Select("select * from user where id =#{id}")
    User findById(String id);
}

