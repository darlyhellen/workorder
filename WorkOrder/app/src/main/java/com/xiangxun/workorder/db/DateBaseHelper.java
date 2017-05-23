package com.xiangxun.workorder.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import com.hellen.baseframe.common.dlog.DLog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhangyuhui/Darly on 2017/5/15.
 * Copyright by [Zhangyuhui/Darly]
 * ©2017 XunXiang.Company. All rights reserved.
 *
 * @TODO:假设现在我们什么都没有，我们要去搞一个对象来给我们干这件事情，它需要有什么才可以干呢？
 * @TODO:先想想要做的事情：管理数据库的操作
 * @TODO:那要做数据库的操作需要什么就很简单了吧？
 * @TODO:1、要操作数据库，所以需要一个SQLiteDataBase对象，可以通过SQLiteOpenHelper的子类来获取。
 * @TODO:2、此外数据库要创建，还需要数据库信息吧？那就直接变量引入。
 * @TODO:3、有了数据库信息，创建了数据库，你要操作，怎么也得告诉我操作哪个表。所以还得包含创建表和更新表的信息，
 * @TODO:由于表一般会有多张，所以这里用一个数组变量。
 * @TODO:有了信息还得交互，不然我怎么知道你要怎么创建表，所以我们在构造方法中直接获取这些信息。
 */
public abstract class DateBaseHelper {
    /**
     * 用来创建和获取数据库的SQLiteOpenHelper
     */
    protected DBHelporer dbHelper;
    /**
     * 数据库对象
     */
    protected SQLiteDatabase db;

    /**
     * 数据库信息
     */
    private String dbName;
    private int version;

    /**
     * 创建表语句
     */
    private String createSql;
    private String updateSql;


    private List<String> cSqls;
    private List<String> uSqls;


    protected int getVersion(Context context) {
        ApplicationInfo info = findMeta(context);
        if (info != null) {
            int ver = info.metaData.getInt("VERSION");
            return ver == 0 ? 1 : ver;
        } else {
            return 1;
        }
    }

    protected String getDbName(Context context) {
        ApplicationInfo info = findMeta(context);
        if (info != null) {
            String name = info.metaData.getString("DATABASE");
            return TextUtils.isEmpty(name) ? "default.db" : name;
        } else {
            return "default.db";
        }
    }

    private ApplicationInfo findMeta(Context context) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager()
                    .getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getCreateSql() {
        return getSql();
    }

    protected String getUpdateSql() {
        return getSql();
    }

    private String getSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(getClass().getSimpleName());
        builder.append("(");
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String type = f.getType().toString();
                TableBinder binder = f.getAnnotation(TableBinder.class);
                if (binder != null) {
                    //过渡ID
                    builder.append(f.getName());
                    builder.append(" integer PRIMARY KEY NOT NULL,");
                } else {
                    if (type.endsWith("String")) {
                        builder.append(f.getName());
                        builder.append(" varchar(255) NOT NULL,");
                    } else if (type.endsWith("Integer") || type.endsWith("int")) {
                        builder.append(f.getName());
                        builder.append(" integer NOT NULL,");
                    } else if (type.endsWith("Float") || type.endsWith("float")) {
                        builder.append(f.getName());
                        builder.append(" float NOT NULL,");
                    } else if (type.endsWith("Double") || type.endsWith("double")) {
                        builder.append(f.getName());
                        builder.append(" double NOT NULL,");
                    } else if (type.endsWith("Boolean") || type.endsWith("boolean")) {
                        builder.append(f.getName());
                        builder.append(" boolean NOT NULL,");
                    } else if (type.endsWith("Long") || type.endsWith("long")) {
                        builder.append(f.getName());
                        builder.append(" long NOT NULL,");
                    } else if (type.endsWith("Short") || type.endsWith("short")) {
                        builder.append(f.getName());
                        builder.append(" short NOT NULL,");
                    } else if (type.endsWith("Byte") || type.endsWith("byte")) {
                        builder.append(f.getName());
                        builder.append(" byte NOT NULL,");
                    }
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public DateBaseHelper() {
        this.dbName = this.getDbName(DBControler.instance.context);
        this.version = this.getVersion(DBControler.instance.context);
        this.createSql = this.getCreateSql();
        this.updateSql = this.getUpdateSql();
        this.dbHelper = new DBHelporer(DBControler.instance.context, this.dbName, null, this.version);
    }


    protected void open() {
        db = dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getDb() {
        return this.db;
    }

    public void close() {
        if (db != null) {
            this.db.close();
            this.dbHelper.close();
        }
    }


    private class DBHelporer extends SQLiteOpenHelper {

        public DBHelporer(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        public DBHelporer(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            //执行创建表语句
            DLog.i("onCreate" + cSqls);
            if (cSqls != null) {
                for (String sql : cSqls) {
                    db.execSQL(sql);
                }
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //执行更新语句
            DLog.i("onCreate" + uSqls);
            if (uSqls != null) {
                for (String sql : uSqls) {
                    db.execSQL(sql);
                }
            }
        }
    }


    public void save() {
        if (db == null) {
            open();
        }
        //首先查询数据库中，是否存在此数据。当然不考虑ID
        Cursor c = findOne();
        if (c != null && c.getCount() != 0) {
            //有东西
            DLog.i("更新数据");
            //可以更新
            while (c.moveToNext()) {
                c.getCount();
                int id = c.getInt(c.getColumnIndex("id"));
                db.update(getClass().getSimpleName(), getContentValues(), "id = ?", new String[]{id + ""});
            }
            c.close();
        } else {
            db.insert(getClass().getSimpleName(), null, getContentValues());
            DLog.i("直接插入数据");
            //插入完成后，进行修改ID
            Cursor t = findOne();
            if (t != null) {
                while (t.moveToNext()) {
                    int sid = t.getInt(t.getColumnIndex("id"));
                    setID(sid);
                }
                t.close();
            }
        }
        DLog.i("更新或插入完成后" + selectUrl());
    }

    public Cursor findOne() {
        Cursor c = null;
        String buffer = "id = " + findID();
        DLog.i("findOne---" + buffer);
        c = db.query(getClass().getSimpleName(), null, buffer, null, null, null, null);
        if (c != null && c.getCount() != 0) {
            return c;
        } else {
            Map<String, Object> map = getContentMap();
            StringBuilder builder = new StringBuilder();
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                builder.append(entry.getKey());
                builder.append(" = '");
                builder.append(entry.getValue());
                if (i != map.size() - 1) {
                    builder.append("' and ");
                } else {
                    builder.append("'");
                }
                i++;
            }
            c = db.query(getClass().getSimpleName(), null, builder.toString(), null, null, null, null);
        }
        return c;
    }

    private int findID() {
        int ob = 0;
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                if ("id".equals(f.getName())) {
                    ob = Integer.valueOf(f.get(this).toString());
                }
            }
            return ob;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ob;
    }

    private void setID(int sid) {
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                if ("id".equals(f.getName())) {
                    f.set(this, sid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String type = f.getType().toString();
                TableBinder binder = f.getAnnotation(TableBinder.class);
                if (binder != null) {
                    //id过渡
                    continue;
                }
                if (type.endsWith("String")) {
                    contentValues.put(f.getName(), String.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Integer") || type.endsWith("int")) {
                    contentValues.put(f.getName(), Integer.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Float") || type.endsWith("float")) {
                    contentValues.put(f.getName(), Float.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Double") || type.endsWith("double")) {
                    contentValues.put(f.getName(), Double.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Boolean") || type.endsWith("boolean")) {
                    contentValues.put(f.getName(), Boolean.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Long") || type.endsWith("long")) {
                    contentValues.put(f.getName(), Long.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Short") || type.endsWith("short")) {
                    contentValues.put(f.getName(), Short.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Byte") || type.endsWith("byte")) {
                    contentValues.put(f.getName(), Byte.valueOf(f.get(this).toString()));
                }
            }
            return contentValues;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentValues;
    }

    private Map<String, Object> getContentMap() {
        Map<String, Object> contentValues = new HashMap<String, Object>();
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String type = f.getType().toString();
                TableBinder binder = f.getAnnotation(TableBinder.class);
                if (binder != null) {
                    //id过渡
                    continue;
                }
                if (type.endsWith("String")) {
                    contentValues.put(f.getName(), String.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Integer") || type.endsWith("int")) {
                    contentValues.put(f.getName(), Integer.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Float") || type.endsWith("float")) {
                    contentValues.put(f.getName(), Float.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Double") || type.endsWith("double")) {
                    contentValues.put(f.getName(), Double.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Boolean") || type.endsWith("boolean")) {
                    contentValues.put(f.getName(), Boolean.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Long") || type.endsWith("long")) {
                    contentValues.put(f.getName(), Long.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Short") || type.endsWith("short")) {
                    contentValues.put(f.getName(), Short.valueOf(f.get(this).toString()));
                } else if (type.endsWith("Byte") || type.endsWith("byte")) {
                    contentValues.put(f.getName(), Byte.valueOf(f.get(this).toString()));
                }
            }
            return contentValues;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentValues;
    }


    public void select() {
        if (db == null) {
            open();
        }
        //获取到了查询信息。
        Cursor c = db.rawQuery(null, new String[]{});
    }

    /**
     * @return
     */
    public boolean deleteAll() {
        if (db == null) {
            open();
        }
        int rowNumber = db.delete(getClass().getSimpleName(), null, null);
        return rowNumber > 0;
    }

    /**
     * 根据数组条件来update
     *
     * @return
     */
    public boolean delete() {
        if (db == null) {
            open();
        }
        int rowNumber = db.delete(getClass().getSimpleName(), "id = ?", new String[]{findID() + ""});
        return rowNumber > 0;
    }


    public List selectUrl() {
        if (db == null) {
            open();
        }
        Object ob = null;
        try {
            Field id = getClass().getDeclaredField("url");
            id.setAccessible(true); //设置些属性是可以访问的
            ob = id.get(this);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Cursor cursor = db.query(getClass().getSimpleName(), null, "url = ?", new String[]{ob + ""}, null, null, null);
        //通过反射机制，设置每项参数值。
        List<Object> lis = new ArrayList<>();
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            while (cursor.moveToNext()) {
                Object b = userCla.newInstance();
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    f.setAccessible(true); //设置些属性是可以访问的
                    String type = f.getType().toString();
                    if (type.endsWith("String")) {
                        f.set(this, cursor.getString(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Integer") || type.endsWith("int")) {
                        f.set(this, cursor.getInt(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Float") || type.endsWith("float")) {
                        f.set(this, cursor.getFloat(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Double") || type.endsWith("double")) {
                        f.set(this, cursor.getDouble(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Boolean") || type.endsWith("boolean")) {
                        f.set(this, cursor.getBlob(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Long") || type.endsWith("long")) {
                        f.set(this, cursor.getLong(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Short") || type.endsWith("short")) {
                        f.set(this, cursor.getShort(cursor.getColumnIndex(f.getName())));
                    }
                }
                lis.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return lis;
    }

    public List findAll() {
        if (db == null) {
            open();
        }
        Cursor cursor = db.query(getClass().getSimpleName(), null, null, null, null, null, null);
        //通过反射机制，设置每项参数值。
        List<Object> lis = new ArrayList<>();
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            while (cursor.moveToNext()) {
                Object b = userCla.newInstance();
                for (int i = 0; i < fs.length; i++) {
                    Field f = fs[i];
                    f.setAccessible(true); //设置些属性是可以访问的
                    String type = f.getType().toString();
                    if (type.endsWith("String")) {
                        f.set(b, cursor.getString(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Integer") || type.endsWith("int")) {
                        f.set(b, cursor.getInt(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Float") || type.endsWith("float")) {
                        f.set(b, cursor.getFloat(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Double") || type.endsWith("double")) {
                        f.set(b, cursor.getDouble(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Boolean") || type.endsWith("boolean")) {
                        f.set(b, cursor.getBlob(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Long") || type.endsWith("long")) {
                        f.set(b, cursor.getLong(cursor.getColumnIndex(f.getName())));
                    } else if (type.endsWith("Short") || type.endsWith("short")) {
                        f.set(b, cursor.getShort(cursor.getColumnIndex(f.getName())));
                    }
                }
                lis.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return lis;
    }

}