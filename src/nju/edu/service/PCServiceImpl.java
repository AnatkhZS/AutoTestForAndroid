package nju.edu.service;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;




import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;
import com.sinaapp.msdxblog.apkUtil.utils.ApkUtil;

import Upoc.Driver;
import nju.edu.bean.Device;

public class PCServiceImpl extends UnicastRemoteObject implements PCService {
	

	public PCServiceImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public static String runCommand(String command) {
		BufferedReader br = null;
		String line = null;
		InputStream is = null;
		InputStreamReader isReader = null;
		try {
			Process proc = Runtime.getRuntime().exec(command);
			is = proc.getInputStream();
			isReader = new InputStreamReader(is, "utf-8");
			br = new BufferedReader(isReader);
			String result = "";
			while ((line = br.readLine()) != null) {
				result += (line + "\n");
			}
			return result;
		} catch (IOException e) {
			return line;
		} finally {
			if (isReader != null) {
				try {
					isReader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}

			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO
				}
			}
		}
	}

	@Override
	public ArrayList<String> getDeviceList() throws RemoteException {
		// TODO Auto-generated method stub
		String[] tmp = runCommand("adb devices").split("\n");
		ArrayList<String> deviceList = new ArrayList<String>();
		for (int i = 1; i < tmp.length; ++i) {
			String udid = tmp[i].split("\t")[0];
			deviceList.add(udid);
		}
		return deviceList;
	}
	@Override
	public void coverageTest(String filename) throws RemoteException {
		ApkInfo apkInfo = null;
		String path = "apk/"+filename;
		int port = 4726;
		try {
			apkInfo = new ApkUtil().getApkInfo(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] tmp = runCommand("adb devices").split("\n");
		int cPort = port;
		ArrayList<Device> deviceList = new ArrayList<Device>();
		for (int i = 1; i < tmp.length; ++i, ++cPort) {
			String udid = tmp[i].split("\t")[0];
			System.out.println(udid + ";" + apkInfo.getPackageName() + ";" + apkInfo.getLaunchableActivity());
			Device device = new Device();
			device.setUdid(udid);
			device.setPort(String.valueOf(cPort));
			device.setAppPackage(apkInfo.getPackageName());
			device.setAppActivity(apkInfo.getLaunchableActivity());
			device.setAppPath(path);
			deviceList.add(device);
		}
		new Driver().start(deviceList);
	}

	@Override
	public boolean upload(String filename, byte[] fileContent) throws RemoteException {
		String path="apk/"+filename;
		File file = new File(path);
		try {
			if (!file.exists()){
				file.createNewFile();
			}else{
				file.delete();
				file.createNewFile();
			}
			BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(file));
			os.write(fileContent);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
