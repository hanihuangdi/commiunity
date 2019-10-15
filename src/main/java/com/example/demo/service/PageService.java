package com.example.demo.service;

import com.example.demo.dto.NotifiDTO;
import com.example.demo.dto.PageBean;
import com.example.demo.dto.PageDTO;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.enmus.NotifiTypeEnum;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionCustomMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PageService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    QuestionCustomMapper customMapper;
    public PageBean findPage(int currentPage, int size, Long creator,String search) {
        if(StringUtils.isNotBlank(search)){
            String[] search1 = search.split(" ");
            String newSearch  = StringUtils.join(search1,"|");
        }

        PageBean pageBean = new PageBean();
        int account;
        int totalPage;
        int star;
        int starPage=0;
        int endPage=0;
        star=(currentPage-1)*size;
        pageBean.setCurrentPage(currentPage);
        pageBean.setSize(size);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setCurrentPage(star);
        pageDTO.setSearch(search);
        pageDTO.setSize(size);
        List<Question> questions;
        User user=null;
        List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
        if(creator==null){

            account=  customMapper.countBySearch(pageDTO);
            questions =   customMapper.selectBySearchWithRowbounds(pageDTO);

        }
        else {
            QuestionExample example = new QuestionExample();
            example.createCriteria().andCreatorEqualTo(creator);
            example.setOrderByClause("gmt_create desc");
            account=(int) questionMapper.countByExample(example);
            questions =   questionMapper.selectByExampleWithRowbounds(example,new RowBounds(star,size));

        }
        pageBean.setCount(account);
        totalPage=account%size==0?account/size:(account/size)+1;
        //防止越界
        if(currentPage>totalPage){
            currentPage=totalPage;
        }
        if(currentPage<1){
            currentPage=1;
        }
        pageBean.setTotalPage(totalPage);
        //questionDTO 赋值
        for(Question queston:questions){
           user = userMapper.selectByPrimaryKey(queston.getCreator());
           QuestionDTO questionDTO = new QuestionDTO();
           questionDTO.setQuestion(queston);
           questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageBean.setData(questionDTOList);
        //判断是否展示首页上一下末页下一页
        if(currentPage==1){
            pageBean.setFistPage(false);
            pageBean.setPrePage(false);
            pageBean.setNextPage(true);
            pageBean.setLastPage(true);
            starPage=currentPage;
            if(currentPage+6<=totalPage){
            endPage=currentPage+6;
            }else{
                endPage=totalPage;
            }

        }
       if(currentPage==totalPage){
            pageBean.setNextPage(false);
            pageBean.setLastPage(false);
           pageBean.setFistPage(true);
           pageBean.setNextPage(true);
           endPage=totalPage;
            if(totalPage-6>=1){
                starPage=totalPage-6;
            }else {
                starPage=1;
            }
        }
       if(currentPage>1&&currentPage<totalPage){
            pageBean.setFistPage(true);
            pageBean.setNextPage(true);
            pageBean.setNextPage(true);
            pageBean.setLastPage(true);
            if(currentPage-3>=1){
                starPage=currentPage-3;
                if(currentPage+3<=totalPage){
                    endPage=currentPage+3;
                }
                else{
                    starPage=totalPage-6;
                    endPage=totalPage;
                }
            }
            else{
                starPage=1;
                if(7<=totalPage){
                    endPage=7;
                }
                else{
                    endPage=totalPage;
                }
            }

        }
       if(totalPage==1){
           pageBean.setFistPage(false);
           pageBean.setNextPage(false);
           pageBean.setNextPage(false);
           pageBean.setLastPage(false);
       }
       //设置分页展示的页面集合
       List<Integer> list = new ArrayList<Integer>();
       for(int i=starPage;i<=endPage;i++){
        list.add(i);
       }
       pageBean.setList(list);
        return pageBean;
    }
    public PageBean findPage(int currentPage, int size) {
      return   findPage(currentPage,size,null,null);

    }

    public Question findbyid(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErroCode.QUESTION_NOT_FOUND);
        }
        return question;
    }

}
