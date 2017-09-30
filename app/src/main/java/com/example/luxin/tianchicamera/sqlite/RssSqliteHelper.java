package com.example.luxin.tianchicamera.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LuXin on 2017/2/28.
 * 该类使用单例模式
 */
public class RssSqliteHelper extends SQLiteOpenHelper {

    private Context mContext;

    private static RssSqliteHelper rssSqliteHelper;

    public RssSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public static SQLiteDatabase getInstance(Context context) {

        //单例模式之懒汉模式
        if (rssSqliteHelper == null) {

            synchronized (RssSqliteHelper.class) {
                if (rssSqliteHelper == null) {
                    rssSqliteHelper = new RssSqliteHelper(context, "LieDe", null, 1);
                }
            }
        }
        return rssSqliteHelper.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table paishuiguanwang" + "(" + "_id integer primary key,"
                + "Road varchar,"
                + "Number varchar,"
                + "GuanDuan varchar,"
                + "GuanJing varchar,"
                + "CaiZhi varchar,"
                + "Type varchar,"
                + "Location varchar,"
                + "LaiYuan varchar,"
                + "LiuLiang varchar,"
                + "PictureName varchar,"
                + "BeiZhu varchar)");
        Log.i("创建数据表", "创建排水管网表成功！");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
