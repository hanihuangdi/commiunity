package com.example.demo.contorller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name",required=false,defaultValue = "word")String name, Model mv){
        mv.addAttribute("name",name);
        return "hello";

    }
    @GetMapping("/")
    public String index(){
       return "index";
    }
}
