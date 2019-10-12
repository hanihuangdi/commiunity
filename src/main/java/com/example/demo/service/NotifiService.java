package com.example.demo.service;

import com.example.demo.dto.NotifiDTO;
import com.example.demo.dto.PageBean;
import com.example.demo.enmus.NotifiTypeEnum;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Notification;
import com.example.demo.model.NotificationExample;
import com.example.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Service
public class NotifiService {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationMapper notificationMapper;
    //获取通知分页信息
    public PageBean findPageNotifi(int currentPage, int size, Long creator){
        PageBean pageBean = new PageBean();
        int account;
        int totalPage;
        int star;
        int starPage=0;
        int endPage=0;
        pageBean.setCurrentPage(currentPage);
        pageBean.setSize(size);
        // star=(currentPage-1)*size;
        List<Notification> notifications;

        User user=null;
        List<NotifiDTO> notificationList=new ArrayList<NotifiDTO>();
        if(creator==null){

            account= (int) notificationMapper.countByExample(new NotificationExample());
            notifications =   notificationMapper.selectByExampleWithRowbounds(new NotificationExample(),new RowBounds(account-size*currentPage,size));

        }
        else {
            NotificationExample example = new NotificationExample();
            example.createCriteria().andReceiverEqualTo(creator);
            account=(int) notificationMapper.countByExample(example);
            notifications =   notificationMapper.selectByExampleWithRowbounds(example,new RowBounds(account-size*currentPage,size));

        }
        //颠倒数组
        Collections.reverse(notifications);
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
        for(Notification notification:notifications){
            NotifiDTO notifiDTO = new NotifiDTO();
            notifiDTO.setGmtCreate(notification.getGmtCreate());
            notifiDTO.setId(notification.getId());
            notifiDTO.setNotifier(notification.getNotifier());
            notifiDTO.setNotifierName(notification.getNotifierName());
            notifiDTO.setOuterid(notification.getOuterid());
            notifiDTO.setOuterTitle(notification.getOuterTitle());
            notifiDTO.setStatus(notification.getStatus());
            notifiDTO.setType(notification.getType());
            notifiDTO.setTypeName(NotifiTypeEnum.nameOfType(notification.getType()));
            notificationList.add(notifiDTO);
        }
        pageBean.setData(notificationList);
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

    public NotifiDTO read(Long id, User user) {
    }
}
