package com.example.demo.contorller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    CommentMapper commentMapper;

    @RequestMapping(value ="/comment",method= RequestMethod.POST)
    public @ResponseBody Object comment(@RequestBody CommentDTO commentDTO, HttpServletResponse resp, HttpServletRequest req){
        User user=(User) req.getSession().getAttribute("user");
        if(user==null){
          return ResultDTO.errOf(201,"账户未登录");
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(1);
        commentMapper.insert(comment);
        Map<Object,Object> map = new HashMap<>();
        map.put("message","成功");
        resp.setContentType("application/json;charset=utf-8");
        return map;
    }
}
