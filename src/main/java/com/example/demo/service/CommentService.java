package com.example.demo.service;

import com.example.demo.enmus.CommentTypeEnum;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionCustomMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionCustomMapper questionCustomMapper;
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
           Comment dbcomment =  commentMapper.selectByPrimaryKey(comment.getId());
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
}
