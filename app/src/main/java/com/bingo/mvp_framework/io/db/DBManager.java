package com.bingo.mvp_framework.io.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.bingo.base.constant.GlobalConfig;
import com.bingo.mvp_framework.io.db.dao.DaoMaster;
import com.bingo.mvp_framework.io.db.dao.DaoSession;


/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */

public class DBManager {
    private static DBManager mDBManager;
    private SqliteOpenHelper mDevOpenHelper;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;

    private Context mContext;

    private DBManager(Context context) {
        mContext = context.getApplicationContext();
        // 初始化数据库信息
        mDevOpenHelper = new SqliteOpenHelper(context, GlobalConfig.DB_NAME);
        getDaoMaster();
        getDaoSession();
    }

    public static DBManager getInstance(Context context) {
        if (null == mDBManager) {
            synchronized (DBManager.class) {
                if (null == mDBManager) {
                    mDBManager = new DBManager(context);
                }
            }
        }
        return mDBManager;
    }

    /**
     * @desc 获取可读数据库
     **/
    public SQLiteDatabase getReadableDatabase() {
        if (null == mDevOpenHelper) {
            getInstance(mContext);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * @desc 获取可写数据库
     **/
    public SQLiteDatabase getWritableDatabase() {
        if (null == mDevOpenHelper) {
            getInstance(mContext);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * @desc 获取DaoMaster
     * @autor Tiany
     * @time 2016/8/13
     **/
    public DaoMaster getDaoMaster() {
        if (null == mDaoMaster) {
            synchronized (DBManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase());
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * @desc 获取DaoSession
     * @autor Tiany
     * @time 2016/8/13
     **/
    public DaoSession getDaoSession() {
        if (null == mDaoSession) {
            synchronized (DBManager.class) {
                mDaoSession = getDaoMaster().newSession();
            }
        }
        return mDaoSession;
    }
}
