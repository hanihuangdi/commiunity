package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class Hello {
    @GetMapping("/hello")
    public String hello(@RequestParam(name="name",required=false,defaultValue = "word")String name, ModelAndView mv){
        mv.addObject("name",name);
        return "hello";

    }
}
