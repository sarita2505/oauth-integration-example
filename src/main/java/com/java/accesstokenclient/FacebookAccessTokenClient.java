package com.java.accesstokenclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.provider.OAuthFacebookConfig;
import com.java.provider.OAuthGitConfig;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletRequest;
import java.io.IOException;

public class FacebookAccessTokenClient {
    static ObjectMapper objectMapper = new ObjectMapper();
    static String body=null;
    static String user_info=null;
    public static void getFaceBookAccessToken(ServletRequest request){
        String code=request.getParameter("code");
        String state=request.getParameter("state");
                UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(OAuthFacebookConfig.ACCESS_TOKEN_URL)
                .queryParam("redirect_uri",OAuthFacebookConfig.CALLBACK_URL)
                .queryParam("client_id",OAuthFacebookConfig.CLIENT_ID)
                .queryParam("client_secret",OAuthFacebookConfig.CLIENT_SECRET)
                .queryParam("code",code).queryParam("state",state);
        UriComponents components = builder.build();
        RestTemplate restTemplate = new RestTemplate();
         body = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
                String.class).getBody();
        System.out.println("access token for fb"+body);
    }
    public static void getFacebookUserInfo(){
        String accessToken = null;
        try {
            accessToken=objectMapper.readTree(body).at("/access_token").asText();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if(body.contains("access_token:"))
//        {
//            accessToken = body.split(":")[1].split(",")[0];
//        }
        UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(OAuthFacebookConfig.user_info_url)
                .queryParam("access_token",accessToken);
        UriComponents components=builder.build();

//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization","Bearer "+accessToken);
//        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate=new RestTemplate();
        user_info=restTemplate.exchange(builder.toUriString(),HttpMethod.GET,null,String.class).getBody();
        System.out.println("userInfo :"+user_info);
    }

}
