package nju.edu.bean;

import java.io.Serializable;

public class Device implements Serializable{
	private String udid;
	private String port;
	private String appPackage;
	private String appActivity;
	private String appPath;
	
	public void setUdid(String udid){
		this.udid=udid;
	}
	public void setPort(String port){
		this.port=port;
	}
	public void setAppPackage(String appPackage){
		this.appPackage=appPackage;
	}
	public void setAppActivity(String appActivity){
		this.appActivity=appActivity;
	}
	public void setAppPath(String appPath){
		this.appPath=appPath;
	}
	
	public String getUdid(){
		return udid;
	}
	public String getPort(){
		return port;
	}
	public String getAppPackage(){
		return appPackage;
	}
	public String getAppActivity(){
		return appActivity;
	}
	public String getAppPath(){
		return appPath;
	}
}
