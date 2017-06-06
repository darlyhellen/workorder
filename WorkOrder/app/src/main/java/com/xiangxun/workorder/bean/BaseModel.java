/**
 * 上午11:38:28
 *
 * @author zhangyh2
 * $
 * BaseModel.java
 * TODO
 */
package com.xiangxun.workorder.bean;

import java.io.Serializable;

/**
 * @author zhangyh2 BaseModel $ 上午11:38:28 TODO 所有请求的基础类。
 */
public class BaseModel<T> {

    private int code;

    private String msg;

    private T data;

    public BaseModel(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static class Type implements Serializable {
        private static final long serialVersionUID = 3884612945322844153L;
        public String code;
        public String name;
        public String type;
        public String id;
        public String product;
        public String dept;
        public String levelCode;
        public String productName;
        public String status;
        public String disabled;
        public String operator;
        public String statusHtml;
    }
}
