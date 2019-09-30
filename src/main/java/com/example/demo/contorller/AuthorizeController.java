package com.example.demo.contorller;

import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import com.example.demo.mapper.Usermapper;
import com.example.demo.model.User;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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
    @Autowired
    Usermapper usermapper;
    @RequestMapping("/callback")
    public String Authorize(@RequestParam(name="code") String code, @RequestParam(name="state") String state, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO();
        accessTokenDTO.setClient_id(Client_id);
        accessTokenDTO.setClient_secret(Client_secret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(Redirect_uri);
        accessTokenDTO.setState(state);
        String access_token = gp.getAccessToken(accessTokenDTO);
        GithubUser githubUser = gp.getUser(access_token);
        if(githubUser!=null){
            User user= new User();
            String token =UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId());
            user.setGmtCreat(System.currentTimeMillis());
            user.setGmtModify(user.getGmtCreat());
            user.setAvatar_url(githubUser.getAvatar_url());
            usermapper.insert(user);
            Cookie cookie = new Cookie("token",token);
            resp.addCookie(cookie);
            //req.getSession().setAttribute("githubUser",githubUser);
            return "redirect:/";
        }
        return "redirect:/";
    }
}
