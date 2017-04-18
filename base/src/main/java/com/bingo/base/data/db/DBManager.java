package com.bingo.base.data.db;

/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */

public class DBManager {
    private static DBManager mDBManager;
//    private static DaoMaster.DevOpenHelper mDevOpenHelper;
//    private static DaoMaster mDaoMaster;
//    private static DaoSession mDaoSession;
//
//    private Context mContext;
//
//    private DbManager(Context context) {
//        this.mContext = context;
//        // 初始化数据库信息
//        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME);
//        getDaoMaster(context);
//        getDaoSession(context);
//    }
//
//    public static DbManager getInstance(Context context) {
//        if (null == mDbManager) {
//            synchronized (DbManager.class) {
//                if (null == mDbManager) {
//                    mDbManager = new DbManager(context);
//                }
//            }
//        }
//        return mDbManager;
//    }
//
//    /**
//     * @desc 获取可读数据库
//     * @autor Tiany
//     * @time 2016/8/13
//     **/
//    public static SQLiteDatabase getReadableDatabase(Context context) {
//        if (null == mDevOpenHelper) {
//            getInstance(context);
//        }
//        return mDevOpenHelper.getReadableDatabase();
//    }
//
//    /**
//     * @desc 获取可写数据库
//     * @autor Tiany
//     * @time 2016/8/13
//     **/
//    public static SQLiteDatabase getWritableDatabase(Context context) {
//        if (null == mDevOpenHelper) {
//            getInstance(context);
//        }
//        return mDevOpenHelper.getWritableDatabase();
//    }
//
//    /**
//     * @desc 获取DaoMaster
//     * @autor Tiany
//     * @time 2016/8/13
//     **/
//    public static DaoMaster getDaoMaster(Context context) {
//        if (null == mDaoMaster) {
//            synchronized (DbManager.class) {
//                if (null == mDaoMaster) {
//                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
//                }
//            }
//        }
//        return mDaoMaster;
//    }
//
//    /**
//     * @desc 获取DaoSession
//     * @autor Tiany
//     * @time 2016/8/13
//     **/
//    public static DaoSession getDaoSession(Context context) {
//        if (null == mDaoSession) {
//            synchronized (DbManager.class) {
//                mDaoSession = getDaoMaster(context).newSession();
//            }
//        }
//
//        return mDaoSession;
//    }
}
