package com.java.provider;

public class OAuthFacebookConfig {

        public static final String AUTHORIZE_URL="https://graph.facebook.com/oauth/authorize";
        public static final String ACCESS_TOKEN_URL="https://graph.facebook.com/oauth/access_token";
        public static final String CALLBACK_URL="http://localhost:8080/oauth-integration-example/login";
        public static final String CLIENT_ID="226962665180223";
        public static final String CLIENT_SECRET="fed4c4826a796b00efb6279873f4a5cb";
        public static final String REQUEST_CODE_URL=AUTHORIZE_URL+"?client_id="+CLIENT_ID+"&redirect_uri="+CALLBACK_URL+"&state=%s";
public static final String user_info_url="https://graph.facebook.com/me";

}
