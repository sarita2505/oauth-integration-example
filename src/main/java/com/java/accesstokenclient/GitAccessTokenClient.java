package com.java.accesstokenclient;

import com.java.provider.OAuthGitConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;

public class GitAccessTokenClient {
   static String body=null;
   static String user_info=null;
    public static void getGitAccessToken(ServletRequest request){
        String code=request.getParameter("code");
        String state=request.getParameter("state");
        UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(OAuthGitConfig.ACCESS_TOKEN_URL)
                .queryParam("redirect_uri",OAuthGitConfig.CALLBACK_URL)
                .queryParam("client_id",OAuthGitConfig.CLIENT_ID)
                .queryParam("client_secret",OAuthGitConfig.CLIENT_SECRET)
                .queryParam("code",code)
                .queryParam("state",state);
        UriComponents components=builder.build();
        RestTemplate restTemplate=new RestTemplate();
         body=restTemplate.exchange(builder.toUriString(), HttpMethod.POST,null,String.class).getBody();
        System.out.println("Accesstoken for git :"+body);
    }
    public static void getGitUserInfo(){
        String accessToken = null;
        if(body.contains("access_token="))
        {
            accessToken = body.split("=")[1].split("&")[0];
        }
        UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(OAuthGitConfig.user_info_url);
        UriComponents components=builder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer "+accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate=new RestTemplate();
        user_info=restTemplate.exchange(builder.toUriString(),HttpMethod.GET,httpEntity,String.class).getBody();
        System.out.println("userInfo :"+user_info);
    }
}
