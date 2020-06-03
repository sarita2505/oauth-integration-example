package com.java.util;

import com.java.oauth.SelectOauth;
import com.java.provider.OAuthFacebookConfig;
import com.java.provider.OAuthGitConfig;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class RedirectUrlToGetCode {
    public static void getCode(ServletRequest request,ServletResponse response) {
        String faceBookUrl = OAuthFacebookConfig.REQUEST_CODE_URL;
        String gitUrl= OAuthGitConfig.REQUEST_CODE_URL;
        String faceBookState="facebook-"+UUID.randomUUID().toString();
        String gitstate = "git-"+UUID.randomUUID().toString();
        faceBookUrl = String.format(faceBookUrl,faceBookState);
        gitUrl=String.format(gitUrl,gitstate);
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                String param=request.getParameter("p1");
        try {
            if (param.equalsIgnoreCase("facebook"))
            httpServletResponse.sendRedirect(faceBookUrl);
            else if (param.equalsIgnoreCase("git")){
                httpServletResponse.sendRedirect(gitUrl);
            }
            else {
                response.getWriter().write("not valid");
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
