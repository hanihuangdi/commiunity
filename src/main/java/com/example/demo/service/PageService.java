package com.example.demo.service;

import com.example.demo.dto.PageBean;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PageService {
    @Autowired
    QuestionMapper questionMapper;

    public PageBean findPage(int currentPage, int size,Integer creator) {

        PageBean pageBean = new PageBean();
        int account;
        int totalPage;
        int star;
        int starPage=0;
        int endPage=0;
        pageBean.setCurrentPage(currentPage);
        pageBean.setSize(size);
       star=(currentPage-1)*size;
        List<Question> questions;
        if(creator==null){
        questions = questionMapper.findPage(star,size);
        account=questionMapper.account();
        }
        else {
         questions = questionMapper.findIdPage(star,size,creator);
            account=questionMapper.accountById(creator);
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
        pageBean.setQuestions(questions);
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

    public Question findById(int id) {
        Question question = questionMapper.findById(id);
        return question;
    }
}
