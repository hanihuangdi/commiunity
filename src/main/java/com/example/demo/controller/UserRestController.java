package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 
* Title: UserRestController
* Description: 
* 用户控制层
* Version:1.0.0  
* @author pancm
* @date 2018年3月19日
 */
@Controller
public class UserRestController {

	    @RequestMapping("/user")
	    public String list(@RequestParam(name="name",required = false,defaultValue = "world")String name, Model model) {
	        model.addAttribute("name", name);
	        return "hello";
	    }


}
