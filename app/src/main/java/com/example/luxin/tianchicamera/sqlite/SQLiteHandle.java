package com.example.luxin.tianchicamera.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by LuXin on 2017/2/28.
 */

public class SQLiteHandle {
    private Context mContext;

    private SQLiteDatabase db;

    public SQLiteHandle(Context context) {
        mContext = context;
        db = RssSqliteHelper.getInstance(context);
        Log.i("sqlite", "db打开");
    }

    public void insertItem(String roadName, String number,
                           String guanduan, String guanjing, String caizhi, String type,
                           String location, String wushuilaiyuan, String liuliang,
                           String beizhu) {
        Log.i("sqlite", "开始");

        ContentValues values = new ContentValues();
        values.put("Road", roadName);
        values.put("Number", number);
        values.put("GuanDuan", guanduan);
        values.put("GuanJing", guanjing);
        values.put("CaiZhi", caizhi);
        values.put("Type", type);
        values.put("Location", location);
        values.put("LaiYuan", wushuilaiyuan);
        values.put("LiuLiang", liuliang);
        values.put("PictureName", number);
        values.put("BeiZhu", beizhu);

        db.beginTransaction();

        db.insert("paishuiguanwang", null, values);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    //查询数据表中的所有数据项
    public Cursor queraAllItems() {

        Cursor cursor = db.query("paishuiguanwang", null, null, null, null, null, null);

        //打印数量
        Log.i("数据表数量", Integer.toString(cursor.getCount()));

        //返回的游标对象
        return cursor;
    }

    public void dbClose() {
        db.close();
        Log.i("sqlite", "关闭");
    }


}
