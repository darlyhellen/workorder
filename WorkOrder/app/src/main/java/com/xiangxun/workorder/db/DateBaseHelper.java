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
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        DLog.i(getSql());
        return getSql();
    }

    protected String getUpdateSql() {
        DLog.i(getSql());
        return getSql();
    }


    private String getSql() {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE ");
        builder.append(getClass().getSimpleName());
        builder.append("(id integer PRIMARY KEY NOT NULL,");
        try {
            //得到类对象
            Class userCla = (Class) getClass();
            //得到类中的所有属性集合
            Field[] fs = userCla.getDeclaredFields();
            for (int i = 0; i < fs.length; i++) {
                Field f = fs[i];
                f.setAccessible(true); //设置些属性是可以访问的
                String type = f.getType().toString();
                DLog.i(f.getName() + "---" + f.get(this) + "---" + type);
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
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

    public DateBaseHelper(Context context) {
        this.dbName = this.getDbName(context);
        this.version = this.getVersion(context);
        this.createSql = this.getCreateSql();
        this.updateSql = this.getUpdateSql();
        this.dbHelper = new DBHelporer(context, this.dbName, null, this.version);
    }


    protected void open() {
        db = dbHelper.getWritableDatabase();
    }

    protected SQLiteDatabase getDb() {
        return this.db;
    }

    protected void close() {
        this.db.close();
        this.dbHelper.close();
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
            db.execSQL(DateBaseHelper.this.createSql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //执行更新语句
            db.execSQL(DateBaseHelper.this.updateSql);
        }
    }


    /**
     * @param values
     * @param key
     * @param value  统一对ContentValues处理
     */
    private void ContentValuesPut(ContentValues values, String key, Object value) {
        if (value == null) {
            values.put(key, "");
        } else {
            String className = value.getClass().getName();
            if (className.equals("java.lang.String")) {
                values.put(key, value.toString());
            } else if (className.equals("java.lang.Integer")) {
                values.put(key, Integer.valueOf(value.toString()));
            } else if (className.equals("java.lang.Float")) {
                values.put(key, Float.valueOf(value.toString()));
            } else if (className.equals("java.lang.Double")) {
                values.put(key, Double.valueOf(value.toString()));
            } else if (className.equals("java.lang.Boolean")) {
                values.put(key, Boolean.valueOf(value.toString()));
            } else if (className.equals("java.lang.Long")) {
                values.put(key, Long.valueOf(value.toString()));
            } else if (className.equals("java.lang.Short")) {
                values.put(key, Short.valueOf(value.toString()));
            }
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
                DLog.i(f.getName() + "---" + f.get(this) + "---" + type);
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

    /**
     * @return 根据数组的列和值进行insert
     */
    public boolean insert() {
        long rowId = this.db.insert(getClass().getSimpleName(), null, getContentValues());
        return rowId != -1;
    }

    /**
     * 统一对数组where条件进行拼接
     *
     * @param whereColumns
     * @return
     */
    private String initWhereSqlFromArray(String[] whereColumns) {
        StringBuffer whereStr = new StringBuffer();
        for (int i = 0; i < whereColumns.length; ++i) {
            whereStr.append(whereColumns[i]).append(" = ? ");
            if (i < whereColumns.length - 1) {
                whereStr.append(" and ");
            }
        }
        return whereStr.toString();
    }

    /**
     * 统一对map的where条件和值进行处理
     *
     * @param whereParams
     * @return
     */
    private Map<String, Object> initWhereSqlFromMap(Map<String, String> whereParams) {
        Set set = whereParams.keySet();
        String[] temp = new String[whereParams.size()];
        int i = 0;
        Iterator iterator = set.iterator();
        StringBuffer whereStr = new StringBuffer();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            whereStr.append(key).append(" = ? ");
            temp[i] = whereParams.get(key);
            if (i < set.size() - 1) {
                whereStr.append(" and ");
            }
            i++;
        }
        HashMap result = new HashMap();
        result.put("whereSql", whereStr);
        result.put("whereSqlParam", temp);
        return result;
    }


    /**
     * 根据数组条件来update
     *
     * @param whereColumns
     * @param whereArgs
     * @return
     */
    public boolean update(String[] whereColumns, String[] whereArgs) {
        String whereClause = this.initWhereSqlFromArray(whereColumns);
        int rowNumber = this.db.update(getClass().getSimpleName(), getContentValues(), whereClause, whereArgs);
        return rowNumber > 0;
    }

    /**
     * 根据map值来进行update
     *
     * @param whereParam
     * @return
     */
    public boolean update(Map<String, String> whereParam) {
        Map map = this.initWhereSqlFromMap(whereParam);
        int rowNumber = this.db.update(getClass().getSimpleName(), getContentValues(), (String) map.get("whereSql"), (String[]) map.get("whereSqlParam"));
        return rowNumber > 0;
    }

    /**
     * 根据数组条件进行delete
     *
     * @param whereColumns
     * @param whereParam
     * @return
     */
    public boolean delete(String[] whereColumns, String[] whereParam) {
        String whereStr = this.initWhereSqlFromArray(whereColumns);
        int rowNumber = this.db.delete(getClass().getSimpleName(), whereStr, whereParam);
        return rowNumber > 0;
    }


    /**
     * 根据map来进行delete
     *
     * @param whereParams
     * @return
     */
    public boolean delete(Map<String, String> whereParams) {
        Map map = this.initWhereSqlFromMap(whereParams);
        int rowNumber = this.db.delete(getClass().getSimpleName(), map.get("whereSql").toString(), (String[]) map.get("whereSqlParam"));
        return rowNumber > 0;
    }


    /**
     * 查询返回List
     *
     * @param sql
     * @param params
     * @return
     */
    public List<Map> queryListMap(String sql, String[] params) {
        ArrayList list = new ArrayList();
        Cursor cursor = this.db.rawQuery(sql, params);
        int columnCount = cursor.getColumnCount();
        while (cursor.moveToNext()) {
            HashMap item = new HashMap();
            for (int i = 0; i < columnCount; ++i) {
                int type = cursor.getType(i);
                switch (type) {
                    case 0:
                        item.put(cursor.getColumnName(i), null);
                        break;
                    case 1:
                        item.put(cursor.getColumnName(i), cursor.getInt(i));
                        break;
                    case 2:
                        item.put(cursor.getColumnName(i), cursor.getFloat(i));
                        break;
                    case 3:
                        item.put(cursor.getColumnName(i), cursor.getString(i));
                        break;
                }
            }
            list.add(item);
        }
        cursor.close();
        return list;
    }

    /**
     * 查询单条数据返回map
     *
     * @param sql
     * @param params
     * @return
     */
    public Map queryItemMap(String sql, String[] params) {
        Cursor cursor = this.db.rawQuery(sql, params);
        HashMap map = new HashMap();
        if (cursor.moveToNext()) {
            for (int i = 0; i < cursor.getColumnCount(); ++i) {
                int type = cursor.getType(i);
                switch (type) {
                    case 0:
                        map.put(cursor.getColumnName(i), null);
                        break;
                    case 1:
                        map.put(cursor.getColumnName(i), cursor.getInt(i));
                        break;
                    case 2:
                        map.put(cursor.getColumnName(i), cursor.getFloat(i));
                        break;
                    case 3:
                        map.put(cursor.getColumnName(i), cursor.getString(i));
                        break;
                }
            }
        }
        cursor.close();
        return map;
    }

    public void execSQL(String sql) {
        this.db.execSQL(sql);
    }

    public void execSQL(String sql, Object[] params) {
        this.db.execSQL(sql, params);
    }

}