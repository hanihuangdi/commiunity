package com.example.demo.contorller;

import com.example.demo.dto.NotifiDTO;
import com.example.demo.enmus.NotifiTypeEnum;
import com.example.demo.model.User;
import com.example.demo.service.NotifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotifcationController {
    @Autowired
    NotifiService notifiService;
    @GetMapping("notification/{id}")
    public String notification(@PathVariable(name="id")Long id, HttpServletRequest request){
            User user = (User)request.getSession().getAttribute("user");
            if(user==null){
                return "redirect:/";
            }
          NotifiDTO notifiDTO =  notifiService.read(id,user);
        if (NotifiTypeEnum.COMMENTTYPE.getTypeName() == notifiDTO.getTypeName()
                || NotifiTypeEnum.QUESTIONTYPE.getTypeName() == notifiDTO.getTypeName()) {
            return "redirect:/question/" + notifiDTO.getOuterid();
        } else {
            return "redirect:/";
        }

    }
}
