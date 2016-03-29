package Upoc;

import io.appium.java_client.AppiumDriver;

class ScreenShotRunner implements Runnable{
	AppiumDriver driver;
	String udid;
	public ScreenShotRunner(AppiumDriver driver,String udid){
		this.driver=driver;
		this.udid=udid;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new UpocTest().takeScreenShot(driver,udid);
	}
	
}