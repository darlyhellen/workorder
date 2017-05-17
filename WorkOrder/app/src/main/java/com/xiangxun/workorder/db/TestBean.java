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


    private String name;

    private String gender;

    private int age;


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
}
