package com.example.demo.contorller;

import com.example.demo.dto.CommentDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.enmus.CommentTypeEnum;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService commentService;
    @RequestMapping(value ="/comment",method= RequestMethod.POST)
    public @ResponseBody Object comment(@RequestBody CommentDTO commentDTO, HttpServletResponse resp, HttpServletRequest req){
        User user=(User) req.getSession().getAttribute("user");
        if(user==null){
          return ResultDTO.errOf(CustomizeErroCode.NOT_lOGIN);
        }
        if(commentDTO==null||commentDTO.getContent()==null||commentDTO.getContent()==""){
            return ResultDTO.errOf(CustomizeErroCode.COMMENT_NULL);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());//问题或者评论的ID
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());//答复者的ID
        comment.setCommentCount(0L);
        commentService.insert(comment);
        return ResultDTO.okOf();
    }
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public @ResponseBody Object subComment(@PathVariable(name="id")Long id){
        List<CommentDTO> commentDTOS = commentService.findByIdComment(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.errOf(commentDTOS);
    }
}
