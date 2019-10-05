package com.example.demo.contorller;

import com.example.demo.dto.PageBean;
import com.example.demo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletRequest;

@Controller
public class Hello {
    @Autowired
    PageService service;
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name",required=false,defaultValue = "word")String name, Model mv){
        mv.addAttribute("name",name);

        return "hello";

    }
    @GetMapping("/")
    public String index(HttpServletRequest req,Model md,@RequestParam(name="currentPage",defaultValue = "1")int currentPage,
                        @RequestParam(name="size",defaultValue ="5")int size){
//        Cookie[] cookies = req.getCookies();
//        if(cookies!=null){
//        for(Cookie ele:cookies){
//        if("token".equals(ele.getName())){
//        String token = ele.getValue();
//        User user = usermapper.findByToken(token);
//            if(user!=null){
//               req.getSession().setAttribute("user",user);
//              }
//            break;
//           }
//        }
//        }
        PageBean pageBean;
        pageBean = service.findPage(currentPage,size);
        md.addAttribute("pageBean",pageBean);
        return "index";

    }
}
