package com.java.filter;

import com.java.accesstokenclient.FacebookAccessTokenClient;
import com.java.accesstokenclient.GitAccessTokenClient;
import com.java.util.CookieForClient;
import com.java.util.RedirectUrlToGetCode;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebFilter(urlPatterns = "/*")
public class OauthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("**filter initialized***");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        if (((HttpServletRequest) servletRequest).getServletPath().equals("/login")) {
            //System.out.println("login url");
            String state = servletRequest.getParameter("state");
            String[] states = state.split("-");

//            for (int i=0;i<states.length;i++){
            if (states[0].equalsIgnoreCase("git")) {
                GitAccessTokenClient.getGitAccessToken(servletRequest);
                GitAccessTokenClient.getGitUserInfo();
                CookieForClient.createCookieAndRedirectUrlToEndPoint(servletResponse);
                // break;
            } else if (states[0].equalsIgnoreCase("facebook")) {
                FacebookAccessTokenClient.getFaceBookAccessToken(servletRequest);
                FacebookAccessTokenClient.getFacebookUserInfo();
                CookieForClient.createCookieAndRedirectUrlToEndPoint(servletResponse);
                // break;
            } else {
                System.out.println("invalid");
            }
            System.out.println("/login");
        } else if (CookieForClient.isCookieExist(servletRequest)) {
            System.out.println("/*");
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            System.out.println("/redirect");
            RedirectUrlToGetCode.getCode(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
