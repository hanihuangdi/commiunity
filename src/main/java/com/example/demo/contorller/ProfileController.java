package com.example.demo.contorller;

import com.example.demo.dto.PageBean;
import com.example.demo.mapper.Usermapper;
import com.example.demo.model.User;
import com.example.demo.service.PageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {
    @Autowired
    PageService service;
    @Autowired
    Usermapper usermapper;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest req, Model md, @RequestParam(name="currentPage",defaultValue = "1")int currentPage,
                          @RequestParam(name="size",defaultValue ="5")int size, @PathVariable(name="action")String action){
//        Cookie[] cookies = req.getCookies();
//        User user=null;
//        if(cookies!=null){
//            for(Cookie ele:cookies){
//                if("token".equals(ele.getName())){
//                    String token = ele.getValue();
//                    user = usermapper.findByToken(token);
//                    if(user!=null){
//                        req.getSession().setAttribute("user",user);
//                    }
//                    break;
//                }
//            }
//        }
        User user = (User)req.getSession().getAttribute("user");
        PageBean pageBean;
        pageBean = service.findPage(currentPage,size,user.getId());
        md.addAttribute("pageBean",pageBean);
        if("question".equals(action)){
            md.addAttribute("action",action);
            md.addAttribute("actionName","我的提问");
        }
        if("data".equals(action)){
            md.addAttribute("action",action);
            md.addAttribute("actionName","个人资料");
        }
        return "profile";
    }
}
