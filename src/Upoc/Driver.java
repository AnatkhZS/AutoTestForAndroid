package Upoc;


import java.io.IOException;
import java.util.ArrayList;

import com.sinaapp.msdxblog.apkUtil.entity.ApkInfo;
import com.sinaapp.msdxblog.apkUtil.utils.ApkUtil;

import nju.edu.bean.Device;

public class Driver {
	
	public static void main(String args[]){
		ApkInfo apkInfo=null;
		String path="apps\\UpocStudent.apk";
		try {
			apkInfo = new ApkUtil().getApkInfo(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String packageName=apkInfo.getPackageName();
		String activityName=apkInfo.getLaunchableActivity();
		
		ArrayList deviceList=new ArrayList();
		
		Device d1=new Device();
		d1.setUdid("3300453fcf978255");
		d1.setPort("4726");
		d1.setAppPackage(packageName);
		d1.setAppActivity(activityName);
		d1.setAppPath(path);
		
		Device d2=new Device();
		d2.setUdid("4C3ETK157B117566");
		d2.setPort("4727");
		d2.setAppPackage(packageName);
		d2.setAppActivity(activityName);
		d2.setAppPath(path);
		
		Device d3=new Device();
		d3.setUdid("fa5fbcb0");
		d3.setPort("4725");
		d3.setAppPackage(packageName);
		d3.setAppActivity(activityName);
		d3.setAppPath(path);

		Device d4=new Device();
		d4.setUdid("K21GAMN5C2300145");
		d4.setPort("4750");
		d4.setAppPackage(packageName);
		d4.setAppActivity(activityName);
		d4.setAppPath(path);
		
		//deviceList.add(d1);
		deviceList.add(d2);
		//deviceList.add(d3);
		//deviceList.add(d4);
		try {
			Runtime.getRuntime().exec("cmd /c taskkill /f /t /im node.exe");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new Driver().start(deviceList);
		
	}
	
	public void start(ArrayList deviceList){
		for(int i=0;i<deviceList.size();i++){
			
			Device d=(Device)deviceList.get(i);
			String udid=d.getUdid();
			String port=d.getPort();
			String appPackage=d.getAppPackage();
			String appActivity=d.getAppActivity();
			String appPath=d.getAppPath();
			
//			Runner r=new Runner(udid, port, appPackage, appActivity, appPath);
//			Thread t=new Thread(r);
//			t.start();		
//			try {
//				Thread.sleep(1000*60*5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			ViewTreeNode node=new UpocTest().run(udid, port, appPackage, appActivity, appPath);
			traverse(node);
		}
	}
	
	void traverse(ViewTreeNode node){
		if(node.getList().size()==0){
			System.out.println(node.getName());
			return;
		}else{
			System.out.println(node.getName());
			for(int i=0;i<node.getList().size();i++){
				traverse(node.getList().get(i));
			}
		}
	}
}

class Runner implements Runnable{
	private String udid;
	private String port;
	private String appPackage;
	private String appActivity;
	private String appPath;
	
	public Runner(String udid, String port, String appPackage, String appActivity, String appPath){
		this.udid=udid;
		this.port=port;
		this.appPackage=appPackage;
		this.appActivity=appActivity;
		this.appPath=appPath;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new UpocTest().run(udid, port, appPackage, appActivity, appPath);
		
		
	}
	
}
