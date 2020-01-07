package com.example.demo.scheduled;

import com.example.demo.cache.HostTagCache;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.Host;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
/**
 * 热门标签获取
 * **/
@Slf4j
@Component
public class HostTag {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    HostTagCache hostTag;
    /*
     * @Description:获取所有问题中的标签，和计算其中的排序值
     * @Author:hcf
     * @Date: 2020/1/7 10:29
     * @param: []
     * @return: void
     */
    //@Scheduled(fixedRate = 1000 * 60 * 60 * 3)
    @Scheduled(fixedDelay = 1000 * 60)
    public void hotTagSchedule() {

        int offset = 0;
        int limit = 20;
        log.info("hotTagSchedule start {}", new Date());
        List<Question> list = new ArrayList<>();
        Map<String, Integer> priorities = new HashMap<>();
        while (offset == 0 || list.size() == limit) {
            list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, limit));
            for (Question question : list) {
                String[] tags = StringUtils.split(question.getTag(), "，");
                for (String tag : tags) {
                    Integer priority = priorities.get(tag);
                    if (priority != null) {
                        priorities.put(tag, priority + 5 + question.getCommentCount());
                    } else {
                        priorities.put(tag, 5 + question.getCommentCount());
                    }
                }
            }
            offset += limit;
        }
       /* for (Map.Entry<String,Integer> entry :priorities.entrySet()){
                System.out.println("热门标签:"+entry.getKey()+"  排序分数"+entry.getValue());
        }*/
       // priorities.forEach((k,v)->{ System.out.println("热门标签:"+k+"  排序分数"+v);});

        List<Map.Entry<String, Integer>> list1 = new ArrayList<Map.Entry<String, Integer>>(priorities.entrySet());

      /*  Collections.sort(list1,new Comparator<Map.Entry<String,Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });*/
        Collections.sort(list1,(o1, o2) -> { return o2.getValue().compareTo(o1.getValue());});
      // list1.forEach(stringIntegerEntry -> System.out.println(stringIntegerEntry.getKey()+":"+stringIntegerEntry.getValue()));
        hostTag.setHostTag(list1);
        log.info("hotTagSchedule stop {}", new Date());
    }


}
