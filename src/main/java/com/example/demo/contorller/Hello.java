package com.example.demo.contorller;

import com.example.demo.mapper.Usermapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Hello {
    @Autowired
    Usermapper usermapper;
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name",required=false,defaultValue = "word")String name, Model mv){
        mv.addAttribute("name",name);
        return "hello";

    }
    @GetMapping("/")
    public String index(HttpServletRequest req){
        Cookie[] cookies = req.getCookies();
        for(Cookie ele:cookies){
        if("token".equals(ele.getName())){
        String token = ele.getValue();
        User user = usermapper.findByToken(token);
            if(user!=null){
               req.getSession().setAttribute("user",user);
              }
            break;
           }
        }
        return "index";
    }
}
