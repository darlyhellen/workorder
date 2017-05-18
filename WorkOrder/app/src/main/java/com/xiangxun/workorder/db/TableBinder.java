package com.xiangxun.workorder.db;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Zhangyuhui/Darly on 2017/5/18.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:我们首先要声明一个@interface，也就是注解类：
 */
@Target(ElementType.FIELD)//表示用在字段s上
@Retention(RetentionPolicy.RUNTIME)//表示在生命周期是运行时
public @interface TableBinder {
    //@interface是用于自定义注解的，它里面定义的方法的声明不能有参数，也不能抛出异常，
    // 并且方法的返回值被限制为简单类型、String、Class、emnus、@interface，和这些类型的数组。
    int id()

            default -1;

    int value()

            default 0;

    String method()

            default "";

    String type()

            default "";

}
