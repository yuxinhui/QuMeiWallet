package com.gdyd.qmwallet.utils;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.gdyd.qmwallet.config.APPConfig;
import com.gdyd.qmwallet.config.HandleConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

/**
 * 跟App相关的辅助类
 * 
 * @author Anonymous
 * 
 * @version v1.2
 * 
 */
public class AppUtils {

	private static File oldfile;

	private AppUtils() {
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 正在运行的线程数
	 */
	private static int runningThread = 0;

	/**
	 * 同步锁对象,用于同步进度条,值无意义
	 */
	private static String syon = "";

	/**
	 * 是否监听下载进度
	 */
	private static boolean isListen = true;

	public static void Download(Context context, Handler handler) {
		AppUtils.Download(context, APPConfig.APK_URL,
				APPConfig.DOWNLOAD_THREAD, handler);
	}

	private static final int CONNECT_TIMEOUT = 5000;

	/**
	 * 多线程下载数据包,文件大小不超过2147483647B(约等于2GB)没有异常
	 *
	 * @param context
	 *            上下文
	 * @param path
	 *            网络路径
	 * @param ThreadCount
	 *            线程总数
	 */
	public static void Download(Context context, String path, int ThreadCount,
								final Handler handler) {
		AppUtils.runningThread = ThreadCount;
		try {
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(CONNECT_TIMEOUT);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
			int code = conn.getResponseCode();
			// 返回码200
			if (code == HttpURLConnection.HTTP_OK) {
				// 服务器返回的数据的长度，实际上就是文件的长度
				int length = conn.getContentLength();
				Log.d("zanZQ", "Download:length "+length);
				oldfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
						APPConfig.APK_NAME);
				// 如果要下载的文件大于已有的文件大小时才重新或继续下载
				if (oldfile.exists() && oldfile.isFile()
						) {
					// 安装包已经下载过了,开始安装
					//AppUtils.installApp(context, new File(context.getFilesDir(), AppConfig.APK_NAME));
					oldfile.delete();

				}
				// 记录总大小
				APPConfig.countLength = length;
				// 平均每一个线程下载的文件的大小
				int blockSize = length / ThreadCount;
				for (int threadId = 1; threadId <= ThreadCount; threadId++) {
					// 线程下载的开始位置
					int startIndex = (threadId - 1) * blockSize;
					// 线程下载的结束位置
					int endIndex = threadId * blockSize - 1;
					if (threadId == ThreadCount) // 如果是最后一个线程，下载的长度要稍微大点
					{
						endIndex = length; // 等于最大长度，即剩余的部分
					}
					// 开启线程
					new Thread(new DemoloadThread(ThreadCount, threadId,
							startIndex, endIndex, path, context, handler))
							.start();
				}
				new Thread() {
					public void run() {
						// 记录开始时间,用于计算消耗的时间
						long startTime = System.currentTimeMillis() - 1;
						float lastSpeed = 0;
						// 记录当前的下载量
						int startLen = APPConfig.currentProgress;
						int currentProgress;
						// 要下载的文件总长度
						int countLength = APPConfig.countLength;
						// 转换float格式
						DecimalFormat fnum = new DecimalFormat("##0.0");
						// 用于反馈是否超过速度1mb/s
						String ismb = "false";
						if (null != handler) {
							while (AppUtils.isListen) {
								//上锁防止线程混乱
								synchronized (AppUtils.syon) {
									currentProgress = APPConfig.currentProgress;
								}
								if (APPConfig.countLength > currentProgress) {
									float speed = (float) ((currentProgress - startLen) * 0.9765625f)
											/ (System.currentTimeMillis() - startTime);
									if (speed > 1024f) {
										speed *= 0.9765625f;
										ismb = "true";
									} else {
										ismb = "false";
									}
									float percent = (float) currentProgress
											/ (float) (countLength / 100);
									Message mess = Message.obtain();
									mess.what = HandleConfig.NOTIFICATION_DOWNLOAD;
									mess.obj = new String[] {
											fnum.format((speed + lastSpeed) / 2),
											fnum.format(percent), ismb };
									handler.sendMessage(mess);
									startTime = System.currentTimeMillis();
									startLen = currentProgress;
									lastSpeed = speed;
									try {
										Thread.sleep(APPConfig.LISTEN_INTERVAL_TIME);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
							}
						}
					};
				}.start();

			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class DemoloadThread implements Runnable {
		private int ThreadCount;
		private int threadId;
		private int startIndex;
		private int endIndex;
		private String path;
		private Context context;
		private Handler handler;

		public DemoloadThread(int ThreadCount, int threadId, int startIndex,
							  int endIndex, String path, Context context, Handler handler) {
			super();
			this.ThreadCount = ThreadCount;
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.path = path;
			this.context = context;
			this.handler = handler;
		}

		@Override
		public void run() {
			try {
				File file = new File(context.getFilesDir(), threadId + ".txt");
				// 检查是否存在记录下载长度的文件，如果存在读取这个文件的数据
				if (file.exists() && file.length() > 0) {
					FileInputStream fis = new FileInputStream(file);
					//创建
					byte[] temp = new byte[2048];
					int length = fis.read(temp);
					int downloaadLen = Integer.parseInt(new String(temp, 0,
							length));
					synchronized (AppUtils.syon) {
						// 计算上次下载的总长度,修改下载总进度
						APPConfig.currentProgress += (downloaadLen - startIndex);
					}
					startIndex = downloaadLen;// 修改下载的真实的开始位置
					fis.close();
				}
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(5000);
				conn.setRequestMethod("GET");
				// 请求服务器下载部分的文件指定的位置
				conn.setRequestProperty("Range", "bytes=" + startIndex + "-"
						+ endIndex);
				int code = conn.getResponseCode();// 从服务器请求全部资源 200 ok
				// 从服务器请求部分资源 206 ok
				// 206
				if (code == HttpURLConnection.HTTP_PARTIAL) {
					InputStream is = conn.getInputStream();// 返回当前位置对应的输入流
					RandomAccessFile raf = new RandomAccessFile(new File(
							Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), APPConfig.APK_NAME), "rwd");
					// 随机写文件的时候从那个位置开始写
					raf.seek(startIndex);// 定位文件
					int len = 0;
					int totallen = 0; // 已经下载的数据长度
					byte[] buffer = new byte[1024];
					while ((len = is.read(buffer)) != -1) {
						RandomAccessFile totalfile = new RandomAccessFile(file,
								"rwd");
						totallen += len;
						raf.write(buffer, 0, len);
						totalfile.write(String.valueOf(totallen + startIndex)
								.getBytes());
						totalfile.close();
						synchronized (AppUtils.syon) {
							// 记录下载总进度
							APPConfig.currentProgress += len;
						}

					}
					is.close();
					raf.close();
					Log.d("zanZQ", "run: "+threadId+"从"+startIndex+"到："+endIndex);
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				AppUtils.runningThread--;
				if (AppUtils.runningThread == 0)// 所有的线程都执行完了
				{
					for (int i = 1; i <= ThreadCount; i++) {
						File delfile = new File(context.getFilesDir(), i
								+ ".txt");
						if (delfile.exists() && delfile.length() > 0) {
							delfile.delete();
						}
					}
					if (null != handler) {
						Message mess = Message.obtain();
						mess.what = HandleConfig.NOTIFICATION_DOWNLOAD_FINISH;
						handler.sendMessage(mess);
					}
					AppUtils.isListen = false;
					if (APPConfig.isInstall) {
						// 下载完毕,开始安装
						AppUtils.installApp(context,
								oldfile.getAbsoluteFile());
					}
				}
			}
		}
	}

	/**
	 * 安装应用程序,没有权限时只能安装本应用的更新包
	 *
	 * @param context
	 *            上下文
	 * @param path
	 *            安装包路径
	 */
	public static void installApp(Context context, File path) {
		// 如果文件存在
		if (path.exists() && path.isFile()) {
			// 调用系统安装
			Intent intent = new Intent();
			intent.setAction("android.intent.action.VIEW");
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setDataAndType(Uri.fromFile(path),
					"application/vnd.android.package-archive");
			// 完成后提示打开
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			// 关闭本应用
			//	android.os.Process.killProcess(android.os.Process.myPid());
//			Uri installUri = Uri. fromParts ( "package" , path.getAbsolutePath(),
//					null );
//			Intent returnIt = new Intent(Intent. ACTION_PACKAGE_ADDED ,
//					installUri);
//			context.startActivity(returnIt);
//			Intent intent = new Intent(Intent.ACTION_VIEW);
//			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////filePath为文件路径
//			intent.setDataAndType(Uri.parse("file://"+filePath), "application/vnd.android.packagearchive");
//			startActivity(intent);
		}
	}

	/**
	 * 获取应用程序名称
	 *
	 * @param context
	 *
	 * @return 当前应用的版本名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * [获取应用程序版本名称信息]
	 *
	 * @param context
	 *
	 * @return 当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 关闭本应用程序
	 */
	public static void killProcess() {
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public class DownLoadThread extends
			Thread {

		private String downLoadUrl;

		private Context context;

		private FileOutputStream out = null;

		private File downLoadFile = null;

		private File sdCardFile = null;

		private InputStream in = null;

		public DownLoadThread(String downLoadUrl, Context context) {

			super();

			this.downLoadUrl = downLoadUrl;

			this.context = context;

		}

		@Override

		public void run() {

			try {

				URL httpUrl = new URL(downLoadUrl);

				HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
				conn.setDoInput(true);// 如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 true。

				conn.setDoOutput(true);// 如果打算使URL 连接进行输出，则将 DoOutput 标志设置为 true；如果不打算使用，则设置为 false。默认值为 false。

				in = conn.getInputStream();

				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					Toast.makeText(context, "SD卡不可用！", Toast.LENGTH_SHORT).show();

					return;

				}

				downLoadFile = Environment.getExternalStorageDirectory();

				sdCardFile = new File(downLoadFile, "download.apk");

				out = new FileOutputStream(sdCardFile);

				byte[] b = new byte[1024];

				int len;

				while ((len = in.read(b)) != -1) {

					out.write(b, 0, len);

				}

				if (out != null) {

					out.close();

				}

				if (in != null) {

					in.close();

				}

			}

			catch (Exception e) {

				e.printStackTrace();

			}
		}

	}
	public static boolean isBackground(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(context.getPackageName())) {
                /*
                BACKGROUND=400 EMPTY=500 FOREGROUND=100
                GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
                 */
				Log.i(context.getPackageName(), "此appimportace ="
						+ appProcess.importance
						+ ",context.getClass().getName()="
						+ context.getClass().getName());
				if (appProcess.importance != ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
					Log.i(context.getPackageName(), "处于后台"
							+ appProcess.processName);
					return true;
				} else {
					Log.i(context.getPackageName(), "处于前台"
							+ appProcess.processName);
					return false;
				}
			}
		}
		return false;
	}
	/**
	 *判断当前应用程序处于前台还是后台
	 */
	public static boolean isApplicationBroughtToBackground(final Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				Log.d("zanZQ", "isApplicationBroughtToBackground: true");
				return true;
			}
		}
		Log.d("zanZQ", "isApplicationBroughtToBackground: false");
		return false;

	}
}
