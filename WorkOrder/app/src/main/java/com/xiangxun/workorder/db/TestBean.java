package com.xiangxun.workorder.db;

/**
 * Created by Zhangyuhui/Darly on 2017/5/16.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:
 */
public class TestBean extends DateBaseHelper {
    public TestBean() {
        super();
    }

    @TableBinder
    private int id;

    private String name;

    private String gender;

    private int age;

    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "int id=" + id + "\tString name=" + name + "\tString gender=" + gender + "\tint age=" + age + "\tString url=" + url;
    }
}
