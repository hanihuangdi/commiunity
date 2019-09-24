package com.example.demo.contorller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider gp;
    @RequestMapping("/callback")
    public String Authorize(@RequestParam(name="code") String code,@RequestParam(name="state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
        accessTokenDTO.setClient_id("7e518a40f1fdb0465463");
        accessTokenDTO.setClient_secret("9758643b90fb8ab2e60cf9aa25e65f0aa9933496");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8085/callback");
        accessTokenDTO.setState(state);
        String access_token = gp.getAccessToken(accessTokenDTO);
        GithubUser user = gp.getUser(access_token);
        String name = user.getName();
        System.out.println(name);//小富
        return "index";
    }
}
