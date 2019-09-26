package com.example.demo.contorller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AuthorizeController {
    @Autowired
    GithubProvider gp;
    @Value("${git.client.id}")
    String Client_id;
    @Value("${git.client.secret}")
    String Client_secret;
    @Value("${git.client.uri}")
    String Redirect_uri;
    @RequestMapping("/callback")
    public String Authorize(@RequestParam(name="code") String code,@RequestParam(name="state") String state) throws IOException {
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        accessTokenDTO.setState(state);
        String access_token = gp.getAccessToken(accessTokenDTO);
        GithubUser user = gp.getUser(access_token);
        String name = user.getName();
        System.out.println(name);//小富
        return "index";
    }
}
