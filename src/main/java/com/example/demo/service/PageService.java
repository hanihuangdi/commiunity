package com.example.demo.service;

import com.example.demo.dto.PageBean;
import com.example.demo.dto.QuestionDTO;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import com.example.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.util.resources.ga.LocaleNames_ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PageService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    public PageBean findPage(int currentPage, int size, Long creator) {

        PageBean pageBean = new PageBean();
        int account;
        int totalPage;
        int star;
        int starPage=0;
        int endPage=0;
        pageBean.setCurrentPage(currentPage);
        pageBean.setSize(size);
      // star=(currentPage-1)*size;
        List<Question> questions;

        User user=null;
        List<QuestionDTO> questionDTOList=new ArrayList<QuestionDTO>();
        if(creator==null){
            account= (int) questionMapper.countByExample(new QuestionExample());
         questions =   questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(account-size*currentPage,size));

        }
        else {
            QuestionExample example = new QuestionExample();
            example.createCriteria().andCreatorEqualTo(creator);
            account=(int) questionMapper.countByExample(example);
            questions =   questionMapper.selectByExampleWithRowbounds(example,new RowBounds(account-size*currentPage,size));

        }
        //颠倒数组
        Collections.reverse(questions);
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
        pageBean.setQuestionDTOs(questionDTOList);
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
      return   findPage(currentPage,size,null);

    }

    public Question findbyid(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErroCode.QUESTION_NOT_FOUND);
        }
        return question;
    }
}
