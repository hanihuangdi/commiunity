package com.example.demo.provider;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.AccessTokenDTO;
import com.example.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType md
                = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON.toJSONString(accessTokenDTO),md);
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String msg = response.body().string();
                msg = msg.split("&")[0].split("=")[1];
                System.out.println(msg);
                return msg;//a3f804507a107ddb95b8c5eb95779a3263842bd4
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }



    public GithubUser getUser(String access_token) throws IOException {
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url("https://api.github.com/user?access_token="+access_token+"")
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String json = response.body().string();
                GithubUser githubUser = JSON.parseObject(json,GithubUser.class);
                return  githubUser;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
        }



}
