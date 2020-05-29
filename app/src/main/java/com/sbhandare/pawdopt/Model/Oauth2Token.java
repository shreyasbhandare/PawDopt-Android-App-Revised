package com.sbhandare.pawdopt.Model;

public class Oauth2Token {
    private String access_token;
    private String token_type;
    private float expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public float getExpires_in() {
        return expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public void setExpires_in(float expires_in) {
        this.expires_in = expires_in;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}