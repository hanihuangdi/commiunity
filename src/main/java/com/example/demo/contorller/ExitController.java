package com.example.demo.contorller;

import com.example.demo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ExitController {
    @GetMapping("/exit")
    public String exit(HttpServletRequest req, HttpServletResponse resp
    ){
       req.getSession().invalidate();
        Cookie cookie =new Cookie("token",null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
        return "redirect:/";
    }
}
