package com.lhjitem.community.provider;

import com.alibaba.fastjson.JSON;
import com.lhjitem.community.dto.AccessTokenDTO;
import com.lhjitem.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @auther lhj
 * @date 2022/1/23 18:19
 * 这里简单解释一下component的作用，他的作用就是将这个类初始化到spring的容器（上下文）中，而controller则是将当前类作为路由API的承载者
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        //ctrl+p：显示所需参数
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String msg = response.body().string();
            System.out.println(msg);
            //access_token=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&scope=user&token_type=bearer
            String token = msg.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token="+accessToken)
                //记得加这一句，因为现在github不让明文传输令牌了
                .header("Authorization","token "+accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String msg = response.body().string();
            GithubUser githubUser = JSON.parseObject(msg, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
