package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.enmus.CommentTypeEnum;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionCustomMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionCustomMapper questionCustomMapper;
    @Autowired
    UserMapper userMapper;
    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErroCode.TAGET_PRAME_NOT_FOUND);
        }
        if(comment.getType()==null||!CommentTypeEnum.isType(comment.getType())){
            throw  new CustomizeException(CustomizeErroCode.TAGET_PRAME_ERROR);
        }
        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
           Comment dbcomment =  commentMapper.selectByPrimaryKey(comment.getParentId());
           if(dbcomment==null){
               throw  new CustomizeException(CustomizeErroCode.COMMENT_NOT_FIND);
           }
           else{
               commentMapper.insert(comment);
           }
        }else{
            Question dbquestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbquestion==null){
                throw  new CustomizeException(CustomizeErroCode.QUESTION_NOT_FOUND);
            }
            else{
                commentMapper.insert(comment);
                questionCustomMapper.updateComment(comment.getParentId());
            }
            //回复问题


        }

    }

    public List<CommentDTO> findByIdComment(Long id, Integer type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type);
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);
        if(comments.size()==0){
            return new ArrayList<>();
        }
        /*去除用户ID*/
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> usersId = new ArrayList<>();
        usersId.addAll(commentators);
        //获取评论人转为Map
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(usersId);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));

        //转comment为commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
            return commentDTOS;

    }
}
