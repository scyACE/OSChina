package com.wuxianedu.oschina.utils;

import android.content.Context;

import com.wxhl.core.activity.CoreApplication;
import com.wxhl.core.utils.SDCardUtil;

import java.io.File;
import java.math.BigDecimal;

/**
 * 应用数据管理器:主要功能有清除内/外缓存，清除数据库，清除sharedPreference，清除files和清除自定义目录
 * Created by sks on 2016/4/9.
 */
public class AppDataManager {

    /**
     * 获取缓存大小
     * @param context
     * @return
     * @throws Exception
     */
    public static String getCacheSize(Context context,String... filepath){
        int size = 0;
            try {
                size += getFolderSize(context.getCacheDir());

            size +=  getFolderSize(context.getFilesDir());
            size +=  getFolderSize(new File("/data/data/"
                    + context.getPackageName() + "/databases"));
            size +=  getFolderSize(new File("/data/data/"
                    + context.getPackageName() + "/shared_prefs"));
            size +=  getFolderSize(context.getExternalCacheDir());

            if (filepath == null) {
                return getFormatSize(size);
            }

            for (String file : filepath) {
                size +=  getFolderSize(new File(file));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getFormatSize(size);
    }

    /**
     * 清除本应用所有缓存数据
     * @param context
     * @param filepath
     */
    public static void cleanCache(Context context, String... filepath) {
        cleanInternalCache(context);
        cleanFiles(context);
        cleanDatabases(context);
        cleanSharedPreference(context);
        cleanExternalCache(context);
        if (filepath == null) {
            return;
        }
        for (String filePath : filepath) {
            cleanCustomCache(filePath);
        }
    }

    /**
     * 清除自定义路径下的缓存文件，这里只会删除某个文件夹下的文件
     * @param filePath
     */
    public static void cleanCustomCache(String filePath) {
        deleteFolderFile(new File(filePath),false);
    }

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
     * @param context
     */
    private static void cleanInternalCache(Context context) {
        deleteFolderFile(context.getCacheDir(), false);
    }

    /**
     * 清除/data/data/com.xxx.xxx/files下的文件
     * @param context
     */
    private static void cleanFiles(Context context) {
        deleteFolderFile(context.getFilesDir(), false);
    }

    /**
     * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
     * @param context
     */
    private static void cleanDatabases(Context context) {
        deleteFolderFile(new File("/data/data/"
                + context.getPackageName() + "/databases"), false);
    }

    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     * @param context
     */
    private static void cleanSharedPreference(Context context) {
        deleteFolderFile(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"), false);
    }

    /**
     * 清除外部cache下的文件(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     * @param context
     */
    private static void cleanExternalCache(Context context) {
        if (SDCardUtil.isSDCardEnable()) {
            deleteFolderFile(new File(CoreApplication.IMAGE_DIR),false);
            deleteFolderFile(new File(CoreApplication.IMAGE_UPLOAD_TEMP),false);
            deleteFolderFile(new File(CoreApplication.FILE_DIR),false);
            deleteFolderFile(new File(CoreApplication.LOG_DIR),false);
        }
    }

    /**
     * 删除指定目录下文件及目录
     * @param file
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(File file, boolean deleteThisPath) {
        if(file==null || !file.exists()){
            return;
        }

        try {
            if (file.isDirectory()) {
                // 如果是目录
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFolderFile(new File(files[i].getAbsolutePath()), true);
                }
            }

            if (deleteThisPath) {
                if (!file.isDirectory()) {// 如果是文件则删除
                    file.delete();
                } else {
                    // 目录
                    if (file.listFiles().length == 0) {
                        // 目录下没有文件或者目录则删除
                        file.delete();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取指定目录的大小
     * @param file
     * @return
     * @throws Exception
     */
    private static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if(fileList!=null) {
                for (int i = 0; i < fileList.length; i++) {
                    // 如果下面还有文件
                    if (fileList[i].isDirectory()) {
                        size = size + getFolderSize(fileList[i]);
                    } else {
                        size = size + fileList[i].length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     * @param size
     * @return
     */
    private static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0KB";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

}
