package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface Usermapper {
    @Insert("insert into user(name,account_id,token,GMT_CREAT,GTM_MODIFY,avatar_url) values(#{name},#{accountId},#{token},#{gmtCreat},#{gmtModify},#{avatar_url})")
    void insert(User user);
    @Select("select * from user where token =#{token}")
    User findByToken(String token);
    @Select("select * from user where id =#{id}")
    User findById(String id);
    @Select("select * from user where account_id =#{accountId}")
    User findByUserId(String accountId);
    @Update("update user set gtm_modify=#{gmtModify},avatar_url=#{avatar_url},name=#{name},token=#{token} where account_id=#{accountId}")
    void update(User user);
}

