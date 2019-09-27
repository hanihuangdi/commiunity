package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,gmt_creat,gmt_modify) values(#{name},#{accountId},#{token},#{gmtCreat},#{gmtModify})")
    void insert(User user);

}

