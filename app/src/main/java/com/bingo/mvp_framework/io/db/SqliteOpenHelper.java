package com.bingo.mvp_framework.io.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bingo.base.BuildConfig;
import com.bingo.base.util.MigrationHelper;
import com.bingo.mvp_framework.io.db.dao.DaoMaster;
import com.bingo.mvp_framework.io.db.dao.NoteDao;

/**
 * Created by tuyx on 2017/4/18.
 * Description : 数据库升级帮助类
 */

public class SqliteOpenHelper extends DaoMaster.DevOpenHelper {

    public SqliteOpenHelper(Context context, String name) {
        super(context, name);
        init();
    }

    public SqliteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
        init();
    }

    void init() {
        MigrationHelper.DEBUG = BuildConfig.DEBUG;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo upgrade operate
        for (int ver = oldVersion + 1; ver <= newVersion; ver++) {
            switch (ver) {
                default: {
                    onCreate(db);
                }
            }
        }
    }
}
