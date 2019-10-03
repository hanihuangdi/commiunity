package com.example.demo.contorller;

import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.Usermapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PublishController {
    @Autowired
    QuestionService questionService;
    @Autowired
    Usermapper usermapper;
    @Autowired
    QuestionMapper questionMapper;
    @GetMapping("edit/{id}")
    public String edit(@PathVariable(name="id")int id,Model md){
        Question question = questionMapper.findById(id);
        md.addAttribute("title",question.getTitle());
        md.addAttribute("description",question.getDescription());
        md.addAttribute("tag",question.getTag());
        md.addAttribute("id",question.getId());
    return "publish";
    }
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }
    @PostMapping("/publish")
    public String question(@RequestParam(name="title",required=false)String title,
                           @RequestParam(name="description",required=false)String description,
                           @RequestParam(name="tag",required=false)String tag,
                           @RequestParam(name="id",required=false,defaultValue = "-1")int  id,
                           Model md, HttpServletRequest req
                           ){
        //会写数据
        md.addAttribute("title",title);
        md.addAttribute("description",description);
        md.addAttribute("tag",tag);
        md.addAttribute("id",id);

        //登录验证

        Cookie[] cookies = req.getCookies();
//        User user =null;
//        if(cookies!=null){
//        for(Cookie ele:cookies){
//            if("token".equals(ele.getName())){
//                String token = ele.getValue();
//                user = usermapper.findByToken(token);
//                if(user==null){
//                    md.addAttribute("msg","您尚未登录，请"+"登录后重试");
//                    return "publish";
//                }else{
//                    req.getSession().setAttribute("user",user);
//                }
//                break;
//            }
//        }
//        }
//        else{
//            md.addAttribute("msg","您尚未登录，请"+"登录后重试");
//            return "publish";
//        }
        if(cookies==null){
            md.addAttribute("msg","您尚未登录，请"+"登录后重试");
//            return "publish";
        }
        User user = (User) req.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        //表单验证
        if(title==null||"".equals(title.trim())){
            md.addAttribute("msg","请输入标题");
            return "publish";
        }
        if(description==null||"".equals(description.trim())){
            md.addAttribute("msg","请输入问题内容");
            return "publish";
        }
        if(tag==null||"".equals(tag.trim())){
            md.addAttribute("msg","请输入标签");
          return "publish";
        }
        Question question=questionMapper.findById(id);
        if(question!=null){
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setGmt_modify(System.currentTimeMillis());
            questionService.update(question);
        }else{
        question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setGmt_create(System.currentTimeMillis());
        question.setGmt_modify(question.getGmt_create());
        question.setCreator(user.getId());
        questionService.insert(question);
        }
        return "redirect:/";
    }


}
