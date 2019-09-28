package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,GMT_CREAT,GMT_MODIFY) values(#{name},#{accountId},#{token},#{gmtCreat},#{gmtModify})")
    void insert(User user);
    @Select("select * from user where token =#{token}")
    User findByToken(String token);
}

