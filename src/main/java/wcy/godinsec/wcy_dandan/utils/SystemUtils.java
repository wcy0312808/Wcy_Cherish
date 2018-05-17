package wcy.godinsec.wcy_dandan.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.WindowManager;

//import com.godinsec.virtual.BuildConfig;
//import com.godinsec.virtual.client.core.VirtualCore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by alan on 16-10-13.
 * 系统工具类
 */
public class SystemUtils {

	private static String versionName;
	private static int versionCode;

	private static final String TAG = SystemUtils.class.getSimpleName();

	public static String getDeviceNumber() {
//		if (!TextUtils.isEmpty(getDeviceId())) {
//			return getDeviceId();
//		}
//		if (!TextUtils.isEmpty(getMacAddress())) {
//			return getMacAddress();
//		}
		return "";
	}

//	public static String getDeviceId() {
//		TelephonyManager telephonyManager = (TelephonyManager) VirtualCore.getCore().getContext().getSystemService(Context.TELEPHONY_SERVICE);
		/*if (telephonyManager != null) {
			if (VirtualCore.get().getPhoneInfoDelegate() != null) {
				if(VirtualCore.get().isVAppProcess()){
					return telephonyManager.getDeviceId();
				}else{
					DelegateResult<String> o = VirtualCore.get().getPhoneInfoDelegate().getDeviceId((String) telephonyManager.getDeviceId());
					if (o != null) {
						return o.getValue();
					}
				}

			}

		}*/
//
//		if(telephonyManager != null){
//			return telephonyManager.getDeviceId();
//		}
//
//		return null;
//	}

	public static String getMacAddress() {
//		WifiManager wifiManager = (WifiManager) VirtualCore.getCore().getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//		if (wifiManager != null) {
//			WifiInfo wifiInfo = wifiManager.getConnectionInfo();
//			if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getMacAddress()))
//				return wifiInfo.getMacAddress();
//		}
		return null;
	}

	/**
	 * retrieve the app version name
	 *
	 * @return the app version name
	 */
	public static String getAppVersionName() {
		/*PackageManager packageManager = VirtualCore.get().getPackageManager();
		try {
			PackageInfo packageInfo = packageManager.getPackageInfo(VirtualCore.getCore().getHostPkg(), PackageManager.GET_CONFIGURATIONS);
			if (packageInfo != null) {
				return packageInfo.versionName;
			}
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}*/
		return versionName;
	}

	public static void setVersionName(String m_versionName){
		versionName = m_versionName;
	}

	public static int[] getScreenDispaly(Context context) {
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		int width = windowManager.getDefaultDisplay().getWidth();
		int height = windowManager.getDefaultDisplay().getHeight();
		int result[] = {width, height};
		return result;
	}

	/**
	 * retrieve the app version code
	 *
	 * @param mContext
	 * @return the app version code
	 */
	public static int getVersionCode(Context mContext) {
		/*int versionCode = 1;
		if (mContext == null) {
			return versionCode;
		}
		try {
			PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(
					mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode = packageInfo.versionCode;
            if(BuildConfig.DEBUG) {
                VLog.i(TAG, "versionCode =s " + versionCode);
            }
		} catch (PackageManager.NameNotFoundException e) {
			if(BuildConfig.DEBUG) {
				VLog.e(TAG, "get versioncode error.");
				e.printStackTrace();
			}
		}*/
		return versionCode;
	}

	public static void setVersionCode(int m_versionCode){
		versionCode = m_versionCode;
	}

	/**
	 * 把Bitmap存到指定路径的文件
	 *
	 * @return
	 */
	public static void storeBitmap2File(Bitmap bitmap, String path) {
		if (TextUtils.isEmpty(path) || bitmap == null)
			return;
		try {
			File file = new File(path);
			FileOutputStream fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
//			if(BuildConfig.DEBUG) {
//				e.printStackTrace();
//				VLog.e(TAG, "convert bitmap to file error");
//			}
		}
	}

	/**
	 * 检查下载的插件是否是国鼎应用插件
	 *
//	 * @param path
	 */
//	public static boolean checkSignature(String path){
//
//		PackageInfo godinSignatures = null;
//		try {
////			PackageInfo packageInfo = VirtualCore.get().getUnHookPackageManager().getPackageArchiveInfo(path, PackageManager.GET_SIGNATURES);
////			godinSignatures = VirtualCore.get().getUnHookPackageManager().getPackageInfo(VirtualCore.get().getHostPkg(), PackageManager.GET_SIGNATURES);
////			if( packageInfo != null && godinSignatures != null ){
////				Signature[] s1 = packageInfo.signatures;
////				Signature[] s2 = godinSignatures.signatures;
////				/**
////				 * 在魅族4 Pro上面，有时候会获取失败，导致服务进程无法启动
////				 */
////				if( s1 == null || s2 == null ){
////					return false;
////				}
////
////
////				if((IsSignaturesSame.getPublicKey(s1[0].toByteArray())).equals(IsSignaturesSame.getPublicKey(s2[0].toByteArray()))){
////					return true;
////				}
////			}
//
////		} catch (PackageManager.NameNotFoundException e) {
////
////		}
//
//
//		return false;
//	}

	public static class NetInfo{
		private String access;
		private String networkType;
		private String subtype;

		public String getAccess() {
			return access;
		}

		public void setAccess(String access) {
			this.access = access;
		}

		public String getNetworkType() {
			return networkType;
		}

		public void setNetworkType(String networkType) {
			this.networkType = networkType;
		}

		public String getSubtype() {
			return subtype;
		}

		public void setSubtype(String subtype) {
			this.subtype = subtype;
		}
	}

	/**
	 * 获取网络类型
	 * @param context
	 * @return
	 */
	public static NetInfo getNetWorkType(Context context){
		NetInfo netInfo = new NetInfo();
		NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo != null && networkInfo.isConnected()){
			if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
				netInfo.setAccess("wifi");
				netInfo.setNetworkType("wifi");
				netInfo.setSubtype("wifi");
			}else if(networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
//				netInfo.setNetworkType("mobile");
				netInfo.setSubtype(networkInfo.getSubtypeName());
				int networkType = networkInfo.getSubtype();
				switch (networkType) {
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
						netInfo.setNetworkType("2G");
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
					case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
					case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
						netInfo.setNetworkType("3G");
						break;
					case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
						netInfo.setNetworkType("4G");
						break;
					default:
						// http://baike.baidu.com/item/TD-SCDMA 中国移动 联通 电信 三种3G制式
						if (netInfo.getSubtype().equalsIgnoreCase("TD-SCDMA") || netInfo.getSubtype().equalsIgnoreCase("WCDMA") || netInfo.getSubtype().equalsIgnoreCase("CDMA2000"))
						{
							netInfo.setNetworkType("3G");
						}
						else
						{
							netInfo.setNetworkType(netInfo.getSubtype());
						}

						break;
				}
			}
		}
		return netInfo;
	}

	/**
	 * 获取运营商编号
	 * @param context
	 * @return
	 */
	public static String getOperator(Context context){
		TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telManager.getSimOperator();
		return operator;
	}

	public static String getCpuName(){
		try{
			FileReader fr = new FileReader("/proc/cpuinfo");
			BufferedReader br = new BufferedReader(fr);
			String text = br.readLine();
			String[] array = text.split(":\\s+",2);
			for(int i = 0; i < array.length; i++){
			}
			br.close();
			return array[1];
		}catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
}
