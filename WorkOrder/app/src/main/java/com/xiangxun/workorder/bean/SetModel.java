package com.xiangxun.workorder.bean;

/**
 * Created by Zhangyuhui/Darly on 2017/5/22.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 设置页面的Model类
 */
public class SetModel {

    private int title;
    private int decls;
    private String decl;
    private int res;
    private boolean loginOut;

    public SetModel() {
    }

    public SetModel(int title, int decls, String decl, int res, boolean loginOut) {
        this.title = title;
        this.decls = decls;
        this.decl = decl;
        this.res = res;
        this.loginOut = loginOut;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getDecls() {
        return decls;
    }

    public void setDecls(int decls) {
        this.decls = decls;
    }

    public String getDecl() {
        return decl;
    }

    public void setDecl(String decl) {
        this.decl = decl;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public boolean isLoginOut() {
        return loginOut;
    }

    public void setLoginOut(boolean loginOut) {
        this.loginOut = loginOut;
    }

    @Override
    public String toString() {
        return "SetModel{" +
                "title=" + title +
                ", decls=" + decls +
                ", decl='" + decl + '\'' +
                ", res=" + res +
                ", loginOut=" + loginOut +
                '}';
    }
}
