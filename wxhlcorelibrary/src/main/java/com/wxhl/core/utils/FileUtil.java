package com.wxhl.core.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import com.wxhl.core.activity.CoreApplication;
import com.wxhl.core.utils.constants.CoreConstants;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	
	private static final int BUFFER = 8192;

	/**
	 * @Description: 检查目录是否存在,不存在则创建
	 * @param filePath
	 * @return
	 */
	public static boolean checkDir(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}

	/**
	 * @Description: 判断SD卡是否存在
	 * @return
	 */
	public static boolean isExistSD(){
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			return true;
		}
		return false;
	}

	/**
	 *  获取SD卡路径
	 */
	public static String getSDCardPath() {
		if (isExistSD()) {
			return Environment.getExternalStorageDirectory().toString() + "/";
		}
		return null;
	}

	/**
	 * @Description: 获取当前应用SD卡缓存目录
	 * @param context
	 * @return
	 */
	public static String getSDCacheDir(Context context) {
		//api大于8的版本
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			//目录为/mnt/sdcard/Android/data/com.mt.mtpp/cache
			return context.getExternalCacheDir().getPath();
		}
		String cacheDir = "/Android/data/" + context.getPackageName() + "/cache";
		return Environment.getExternalStorageDirectory().getPath() + cacheDir;

//		return getCacheDirectory(context,true);
	}

	public static File getCacheDirectory(Context context, boolean preferExternal) {
		File appCacheDir = null;

		String externalStorageState;
		try {
			externalStorageState = Environment.getExternalStorageState();
		} catch (NullPointerException var5) {
			externalStorageState = "";
		}

		if(preferExternal && "mounted".equals(externalStorageState)) {
			appCacheDir = getExternalCacheDir(context);
		}

		if(appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}

		if(appCacheDir == null) {
			String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
			L.w("Can\'t define system cache directory! \'%s\' will be used.");
			appCacheDir = new File(cacheDirPath);
		}

		return appCacheDir;
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if(!appCacheDir.exists()) {
			if(!appCacheDir.mkdirs()) {
				L.w("Unable to create external cache directory");
				return null;
			}

			try {
				(new File(appCacheDir, ".nomedia")).createNewFile();
			} catch (IOException var4) {
				L.i("Can\'t create \".nomedia\" file in application external cache directory");
			}
		}

		return appCacheDir;
	}

	/**
	 * @Description: 获取缓存文件目录
	 * @param type
	 * @return
	 */
	public static String getCacheFileDir(int type){
		if(CoreConstants.CACHE_DIR_SD==type){
			//缓存在SD卡中
			return CoreApplication.FILE_DIR;
		}else{
			//缓存在SYSTEM文件中
			return CoreApplication.CACHE_DIR_SYSTEM;
		}
	}

	/**
	 * @Description: 复制文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(String sourceFile, String targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2013-11-14 下午8:46:55
	 * @Description: 删除指定文件夹下所有文件
	 * @param path 文件夹绝对路径
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				boolean b = temp.delete();
				if (b) {
					System.out.println("删除成功");
				} else {
					System.out.println("删除失败：" + temp);
				}
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2013-11-14 下午8:46:10
	 * @Description: 删除文件,包括文件夹
	 * @param folderPath 文件夹绝对路径
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2013-11-22 下午2:28:45
	 * @Description: Java文件操作 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2014-1-23 上午11:04:52
	 * @Description: 删除文件
	 * @param path
	 * @return
	 */
	public static boolean deleteFile(String path){
		File f = new File(path);
		if (f.exists()) {
			return f.delete();
		}
		return false;
	}

	/**
	 * 缓存文件大小
	 * @return
	 */
	public static String getCacheSize(String filePath) {
		long size = 0;
		File f = new File(filePath);
		if (f.exists() && f.isDirectory()) {
			File flist[] = f.listFiles();
			for (int i = 0; flist != null && i < flist.length; i++) {
				size = size + flist[i].length();
			}

		}
		if (size < 1000) {
			return "0KB";
		} else if (size < 1000000) {
			return size / 1000 + "KB";
		} else {
			return size / 1000000 + "M";
		}
	}

	/**
	 * 转换文件大小
	 *
	 * @param fileS
	 * @return B/KB/MB/GB
	 */
	public static String formatFileSize(long fileS) {
		java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "KB";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "MB";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * @author caibing.zhang
	 * @createdate 2015年3月7日 下午5:44:18
	 * @Description: 获得某个文件的大小
	 * @param file
	 * @return
	 */
	public static double getDirSize(File file) {
		//判断文件是否存在
		if (file.exists()) {
			//如果是目录则递归计算其内容的总大小
			if (file.isDirectory()) {
				File[] children = file.listFiles();
				double size = 0;
				for (File f : children)
					size += getDirSize(f);
				return size;
			} else {//如果是文件则直接返回其大小,以“兆”为单位
				double size = (double) file.length() / 1024 / 1024;
				return size;
			}
		} else {
			System.out.println("文件或者文件夹不存在，请检查路径是否正确！");
			return 0.0;
		}
	}

	// 读取文件
	public static String readTextFile(File file) throws IOException {
		String text = null;
		InputStream is = null;
		try {
			is = new FileInputStream(file);
			text = IOUtil.inputStreamToString(is);
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return text;
	}



	// 将文本内容写入文件
	public static void writeTextFile(File file, String str) throws IOException {
		DataOutputStream out = null;
		try {
			out = new DataOutputStream(new FileOutputStream(file));
			out.write(str.getBytes());
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	// 复制文件
	public static void copyFile(File sourceFile, File targetFile)
			throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] buffer = new byte[BUFFER];
			int length;
			while ((length = inBuff.read(buffer)) != -1) {
				outBuff.write(buffer, 0, length);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}


	
}
