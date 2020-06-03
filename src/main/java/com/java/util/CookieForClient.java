package com.java.util;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CookieForClient {
    public static final String COOKIENAME="auth_cookie";
    public static boolean flag=false;
    public static boolean isCookieExist(ServletRequest request){
        HttpServletRequest httpServletRequest=(HttpServletRequest)request;
        Cookie[] cookies=((HttpServletRequest) request).getCookies();
        if (cookies==null){
            return flag;
        }
        for (Cookie cookie:cookies) {
            if (cookie.getName().equals(COOKIENAME)&&cookie.getValue()!=null){
                flag=true;
            }
        }
        return flag;
    }
    public static void createCookieAndRedirectUrlToEndPoint(ServletResponse response){
        Cookie cookie=new Cookie(COOKIENAME,"auth_cookie");
        HttpServletResponse httpServletResponse=(HttpServletResponse)response;
        httpServletResponse.addCookie(cookie);
        try {
            httpServletResponse.sendRedirect("http://localhost:8080/oauth-integration-example/hello");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
