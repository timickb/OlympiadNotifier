package com.timickb.olympiadnotifier;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserUpdatePayload {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("flag")
    @Expose
    private boolean flag;

    public UserUpdatePayload(String key, String token, String subject, boolean flag) {
        this.key = key;
        this.token = token;
        this.subject = subject;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
