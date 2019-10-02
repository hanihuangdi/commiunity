package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question(title,description,creator,gmt_create,gmt_modify,tag)  values (#{title},#{description},#{creator},#{gmt_create},#{gmt_modify},#{tag})")
    void insert(Question question );

    @Select("select * from question")
    @Results({@Result(id=true,property ="id",column = "id"),
            @Result(property ="title",column = "title"),
            @Result(property ="description",column = "description"),
            @Result(property ="gmt_create",column = "gmt_create"),
            @Result(property ="gmt_modify",column = "gmt_modify"),
            @Result(property ="creator",column = "creator"),
            @Result(property ="comment_count",column = "comment_count"),
            @Result(property ="view_count",column = "view_count"),
            @Result(property ="like_count",column = "like_count"),
            @Result(property ="tag",column = "tag"),
            @Result(property ="user",column = "creator",javaType = User.class,one=@One(select="com.example.demo.mapper.Usermapper.findById"))

    })
    List<Question> findAll();
    @Select("select count(1) from question;")
    int account();
    @Select("select count(1) from question where creator=#{id};")
    int accountById(Integer id);
    @Select("select * from question limit #{star},#{size}")
    @Results({@Result(id=true,property ="id",column = "id"),
            @Result(property ="title",column = "title"),
            @Result(property ="description",column = "description"),
            @Result(property ="gmt_create",column = "gmt_create"),
            @Result(property ="gmt_modify",column = "gmt_modify"),
            @Result(property ="creator",column = "creator"),
            @Result(property ="comment_count",column = "comment_count"),
            @Result(property ="view_count",column = "view_count"),
            @Result(property ="like_count",column = "like_count"),
            @Result(property ="tag",column = "tag"),
            @Result(property ="user",column = "creator",javaType = User.class,one=@One(select="com.example.demo.mapper.Usermapper.findById"))

    })
    //select * from question where CREATOR=65 limit 1,5;
    List<Question> findPage(@Param("star") int star, @Param("size")int size);
    @Select("select * from question where creator =#{creator}  limit #{star},#{size}")
    @Results({@Result(id=true,property ="id",column = "id"),
            @Result(property ="title",column = "title"),
            @Result(property ="description",column = "description"),
            @Result(property ="gmt_create",column = "gmt_create"),
            @Result(property ="gmt_modify",column = "gmt_modify"),
            @Result(property ="creator",column = "creator"),
            @Result(property ="comment_count",column = "comment_count"),
            @Result(property ="view_count",column = "view_count"),
            @Result(property ="like_count",column = "like_count"),
            @Result(property ="tag",column = "tag"),
            @Result(property ="user",column = "creator",javaType = User.class,one=@One(select="com.example.demo.mapper.Usermapper.findById"))

    })
    List<Question> findIdPage(@Param("star") int star, @Param("size")int size, @Param("creator") Integer creator);
    @Select("select * from question where id=#{id}")
    @Results({@Result(id=true,property ="id",column = "id"),
            @Result(property ="title",column = "title"),
            @Result(property ="description",column = "description"),
            @Result(property ="gmt_create",column = "gmt_create"),
            @Result(property ="gmt_modify",column = "gmt_modify"),
            @Result(property ="creator",column = "creator"),
            @Result(property ="comment_count",column = "comment_count"),
            @Result(property ="view_count",column = "view_count"),
            @Result(property ="like_count",column = "like_count"),
            @Result(property ="tag",column = "tag"),
            @Result(property ="user",column = "creator",javaType = User.class,one=@One(select="com.example.demo.mapper.Usermapper.findById"))

    })
    Question findById(int id);
}
