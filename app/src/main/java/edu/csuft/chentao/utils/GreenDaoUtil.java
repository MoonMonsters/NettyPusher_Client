package edu.csuft.chentao.utils;

import android.database.sqlite.SQLiteDatabase;

import edu.csuft.chentao.base.MyApplication;
import edu.csuft.chentao.dao.DaoMaster;
import edu.csuft.chentao.dao.DaoSession;

public class GreenDaoUtil {
    private static GreenDaoUtil mGreenDao;
    private SQLiteDatabase mDatabase;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper mDevOpenHelper;

    public static GreenDaoUtil getInstance() {
        if (mGreenDao == null) {
            mGreenDao = new GreenDaoUtil();
        }
        return mGreenDao;
    }

    private void initGreenDao() {
        mDevOpenHelper = new DaoMaster.DevOpenHelper(MyApplication.getInstance(), "NettyPusher.db", null);
        mDatabase = mDevOpenHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(mDatabase);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            initGreenDao();
        }
        return mDaoSession;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        if (mDatabase == null) {
            initGreenDao();
        }
        return mDatabase;
    }
}