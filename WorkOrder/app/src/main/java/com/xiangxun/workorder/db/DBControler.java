package com.xiangxun.workorder.db;

import android.content.Context;

import com.hellen.baseframe.common.dlog.DLog;
import com.xiangxun.workorder.base.APP;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;

/**
 * Created by Zhangyuhui/Darly on 2017/5/17.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO: 通过自身包架构，获取所有类，然后判断父类是否是DateBaseHelper，是的话，进行数据库建立。否则不进行数据库建立。
 * 这样直接初始化数据库。
 */
public class DBControler {

    public static DBControler instance;

    public Context context;

    private DBControler(Context context) {
        this.context = context;
    }

    public static DBControler getInstance(Context context) {
        if (instance == null) {
            instance = new DBControler(context);
        }
        return instance;
    }

    public void init() {
        //在这里查找类
        Package name = this.getClass().getPackage();
        List<String> lists = getClasses(APP.getInstance(), name.getName());
        List<String> sql = new ArrayList<>();
        List<String> upSql = new ArrayList<>();
        for (String pack : lists) {
            try {
                Class s = Class.forName(pack);
                if ((DateBaseHelper.class).isAssignableFrom(s) && !DateBaseHelper.class.getSimpleName().equals(s.getSimpleName())) {
                    DLog.i(s.getSuperclass() + "====" + s);
                    //过滤出来后，进行初始化遍历建立数据库
                    Object o = s.newInstance();
                    String createSql = (String) getFieldValue(o, "createSql");
                    sql.add(createSql);
                    String updateSql = (String) getFieldValue(o, "updateSql");
                    upSql.add(updateSql);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

//生成表格语句完成后，进行初始化表格操作。
        for (String pack : lists) {
            try {
                Class s = Class.forName(pack);
                if ((DateBaseHelper.class).isAssignableFrom(s) && !DateBaseHelper.class.getSimpleName().equals(s.getSimpleName())) {
                    DLog.i(s.getSuperclass() + "====" + s);

                    Object o = s.newInstance();
                    setFieldValue(o, "cSqls", sql);
                    setFieldValue(o, "uSqls", upSql);
                    List<String> createSql = (List<String>) getFieldValue(o, "cSqls");
                    DLog.i("获取设置的值，看看是否设置正常" + createSql);
                    invokeMethod(o,"open",null,null);
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    /* 直接调用对象方法, 而忽略修饰符(private, protected, default)
    * @param object : 子类对象
    * @param methodName : 父类中的方法名
    * @param parameterTypes : 父类中的方法参数类型
    * @param parameters : 父类中的方法参数
    * @return 父类中方法的执行结果
    */

    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes,
                                      Object[] parameters) {
        //根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object, methodName, parameterTypes);

        //抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        try {
            if (null != method) {

                //调用object 的 method 所代表的方法，其方法的参数是 parameters
                return method.invoke(object, parameters);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     *
     * @param object         : 子类对象
     * @param methodName     : 父类中的方法名
     * @param parameterTypes : 父类中的方法参数类型
     * @return 父类中的方法对象
     */

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>... parameterTypes) {
        Method method = null;

        for (Class<?> clazz = object.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了

            }
        }

        return null;
    }


    /**
     * @param object
     * @param fieldName
     * @return 根据对象，获取父类参数名
     */
    public static Field getDeclaredField(Object object, String fieldName) {
        Field field = null;
        Class<?> clazz = object.getClass();
        for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
            try {
                field = clazz.getDeclaredField(fieldName);
                return field;
            } catch (Exception e) {
                //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
                //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
            }
        }

        return null;
    }

    /**
     * @param object
     * @param fieldName
     * @return 根据对象获取参数值
     */
    public static Object getFieldValue(Object object, String fieldName) {

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //获取 object 中 field 所代表的属性值
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    : 子类对象
     * @param fieldName : 父类中的属性名
     * @param value     : 将要设置的值
     */

    public static void setFieldValue(Object object, String fieldName, Object value) {

        //根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object, fieldName);
        //抑制Java对其的检查
        field.setAccessible(true);
        try {
            //将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }


    /**
     * @param mContext
     * @param packageName
     * @return 获取包里面所有类文件
     */
    private List<String> getClasses(Context mContext, String packageName) {
        ArrayList classes = new ArrayList<>();
        try {
            String packageCodePath = mContext.getPackageCodePath();
            DexFile df = new DexFile(packageCodePath);
            String regExp = "^" + packageName + ".\\w+$";
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String className = (String) iter.nextElement();
                if (className.matches(regExp)) {
                    classes.add(className);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classes;
    }
}
