package com.bingo.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import timber.log.Timber;

/**
 * 文件工具
 */
public class FileUtil {
    private static final String TAG = FileUtil.class.getSimpleName();
    /**
     * APP 的主存储上的目录
     */
    public enum AppDirTypeInMain {
        CACHE("cache"),
        USR("usr"),
        DB("db");
        String dir;

        AppDirTypeInMain(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return dir;
        }
    }

    /**
     * APP 在SD卡上的目录
     */
    public enum AppDirTypeInExt {
        CACHE("cache"),
        IMAGE("image"),
        UPLOAD("upload"),
        PATCH("patch"),
        INSTALL("install");
        String dir;

        AppDirTypeInExt(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return dir;
        }
    }

    /**
     * 在主存上，用户私有的目录
     */
    public enum AppUsrDirType {
        DB("db");
        String dir;

        AppUsrDirType(String dir) {
            this.dir = dir;
        }

        public String getDir() {
            return dir;
        }
    }

    /**
     * 清空某个文件下所有的文件
     */
    public static void deleteDir(String filePath) {
        try {
            if (filePath == null) {
                return;
            }
            File file = new File(filePath);
            if (file.exists() && file.isDirectory()) {
                //若目录下没有文件则直接删除
                if (file.listFiles().length == 0) {
                    file.delete();
                } else {
                    //若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = file.listFiles();
                    int i = file.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            //递归调用del方法并取得子目录路径
                            deleteDir(delFile[j].getAbsolutePath());
                        }
                        //删除文件
                        delFile[j].delete();
                    }
                    file.delete();
                }
            }
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
        }
    }


    /**
     * 获取APP的主目录，在主存卡
     */
    public static String getAppDirInMain(Context context, AppDirTypeInMain appDirTypeInMain) {
        return getAppFileInMain(context, appDirTypeInMain).getAbsolutePath();
    }

    public static File getAppFileInMain(Context context, AppDirTypeInMain appDirTypeInMain) {
        //创建 Main SD 卡上，关于该App的 ROOT 目录
        File appRootDir = context.getDir("bucket", Context.MODE_PRIVATE);
        if (!appRootDir.exists()) {
            appRootDir.mkdirs();
        }
        //创建具体的类型目录
        File dir = new File(appRootDir, appDirTypeInMain.getDir());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    /**
     * 获取用户的私有目录，在主存上
     */
    public static String getAppUsrDir(Context context, String usrId, AppUsrDirType appUsrDirType) {
        //检测USR_ID
        if (usrId == null) {
            throw new NullPointerException("USR ID IS NULL");
        }
        //创建私有用户目录
        File usrDir = new File(getAppDirInMain(context, AppDirTypeInMain.USR), usrId);
        if (!usrDir.exists())
            usrDir.mkdirs();
        //创建具体的类型目录
        File dir = new File(usrDir, appUsrDirType.getDir());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir.getAbsolutePath();
    }

    /**
     * 获取app sd 卡上的目录,  如果SD卡出问题, 则返回null
     */
    public static String getAppDirInExt(Context context, AppDirTypeInExt type) throws NotFoundExternalSD {
        return getAppFileInExt(context, type).getAbsolutePath();
    }

    public static File getAppFileInExt(Context context, AppDirTypeInExt type) throws NotFoundExternalSD {

        try {
            //如果没有挂载，或者没有写入外部磁盘权限，则抛出异常
            if (!"mounted".equals(Environment.getExternalStorageState())
                    || context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                throw new RuntimeException("NO SD MOUNTED");
            }
            //外部存储路劲
            String externalStorePath = Environment.getExternalStorageDirectory().getAbsolutePath();
            //如果找不到外部存储, 则抛出异常
            if (TextUtils.isEmpty(externalStorePath)) {
                throw new NullPointerException("externalStorePath is null");
            }
            //在SD卡上建立APP主目录
            File appDirInExt = new File(externalStorePath, context.getPackageName());
            if (!appDirInExt.exists()) {
                appDirInExt.mkdirs();
            }
            //创建具体的TYPE目录
            File dir = new File(appDirInExt, type.getDir());
            if (!dir.exists()) {
                dir.mkdirs();
            }
            return dir;
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
            throw new NotFoundExternalSD(e);
        }
    }

    /**
     * 获取缓存文件夹地址, 依次读取一下的目录
     * 1. 外部 app_home->cache目录
     * 2. 内部 app_home->cache目录
     */
    public static String getAppCacheDir(Context context) {
        //读取APP 在 SD卡上主要的CACHE目录
        try {
            return getAppDirInExt(context, AppDirTypeInExt.CACHE);
        } catch (NotFoundExternalSD e) {
            Timber.e(e, e.getMessage());
        }
        //读取 Android 在Main Store 上创建的CACHE目录
        return getAppDirInMain(context, AppDirTypeInMain.CACHE);
    }

    /**
     * 获取图片缓存路径
     * @param context
     * @return
     */
    public static File getAppImageFile(Context context) {
        //读取APP 在 SD卡上主要的CACHE目录
        try {
            return getAppFileInExt(context, AppDirTypeInExt.IMAGE);
        } catch (NotFoundExternalSD e) {
            Timber.e(e, e.getMessage());
        }
        //读取 Android 在Main Store 上创建的CACHE目录
        return getAppFileInMain(context, AppDirTypeInMain.CACHE);
    }

    public static boolean existExternalSD(Context context) {
        //如果没有挂载，或者没有写入外部磁盘权限，返回fasle
        if (!"mounted".equals(Environment.getExternalStorageState())
                || context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            return false;
        }
        return true;
    }

    /**
     * 没有外部存储
     */
    public static class NotFoundExternalSD extends Exception {
        public NotFoundExternalSD(Exception e) {
            super(e);
        }
    }

    /**
     * byte 转 file
     * @param bfile
     * @param filePath
     * @param fileName
     * @return
     */
    public static File byte2File(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if(!dir.exists() && dir.isDirectory()){
                //判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

}
