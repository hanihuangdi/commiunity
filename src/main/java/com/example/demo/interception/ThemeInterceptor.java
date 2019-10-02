package com.example.demo.interception;

import com.example.demo.mapper.Usermapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Component
public class ThemeInterceptor implements HandlerInterceptor {
    @Autowired
    Usermapper usermapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
        for(Cookie ele:cookies){
        if("token".equals(ele.getName())){
        String token = ele.getValue();
        User user = usermapper.findByToken(token);
            if(user!=null){
               request.getSession().setAttribute("user",user);
              }
            break;
           }
        }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
