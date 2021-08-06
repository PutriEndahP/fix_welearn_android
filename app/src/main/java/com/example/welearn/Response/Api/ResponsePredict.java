package com.example.welearn.Response.Api;

import com.example.welearn.Retrofit.Message;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePredict {
    @SerializedName("success")
    @Expose
    private String success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("text")
    @Expose
    private String text;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) { this.success = success; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
