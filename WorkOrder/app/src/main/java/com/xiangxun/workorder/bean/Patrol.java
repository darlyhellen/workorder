package com.xiangxun.workorder.bean;

import java.io.Serializable;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:首页model类
 */
public class Patrol implements Serializable {
    private int name;
    private int id;
    private int listId;
    private int newOrder;

    public Patrol(int listId, int name, int id, int newOrder) {
        this.listId = listId;
        this.id = id;
        this.name = name;
        this.newOrder = newOrder;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(int newOrder) {
        this.newOrder = newOrder;
    }

    @Override
    public String toString() {
        return "Patrol{" +
                "name=" + name +
                ", id=" + id +
                ", listId=" + listId +
                ", newOrder=" + newOrder +
                '}';
    }


}
