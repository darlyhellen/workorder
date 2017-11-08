package com.xiangxun.workorder.bean;

/**
 * Created by Darly on 2017/11/8.
 */
public class NotifactionData {
    private String id;
    private String title;
    private String content;

    public NotifactionData() {
    }

    public NotifactionData(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
