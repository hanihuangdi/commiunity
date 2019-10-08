package com.example.demo.advice;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomizeErroCode;
import com.example.demo.exception.CustomizeException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
//通用的异常处理
@ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException(HttpServletRequest request, Throwable throwable, Model mv, HttpServletResponse resp) {
        HttpStatus status = getStatus(request);
        String contentType = request.getContentType();
        /*针对需要对错误信息进行提示返回json数据*/
        if("application/json;charset=UTF-8".equals(contentType)){
            ResultDTO resultDTO;
            if (throwable instanceof CustomizeException) {
             resultDTO =    ResultDTO.errOf((CustomizeException) throwable);
            }
            else{
               resultDTO =    ResultDTO.errOf(CustomizeErroCode.SERVICE_ERROR);
            }
            resp.setContentType("application/json;charset=utf-8");
            String json = JSON.toJSONString(resultDTO);
            try {
                resp.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
        else {/*针对需要做页面跳转的错误*/
            if (throwable instanceof CustomizeException) {
                mv.addAttribute("message", throwable.getMessage());
            } else {
                mv.addAttribute("message", "服务器崩溃了");
            }
            return new ModelAndView("error");
        }
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
