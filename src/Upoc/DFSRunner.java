package Upoc;

import io.appium.java_client.AppiumDriver;

public class DFSRunner implements Runnable{
	ViewTreeNode node;
	int startNum;
	AppiumDriver driver;
	public DFSRunner(int num,ViewTreeNode node,AppiumDriver driver){
		this.startNum=num;
		this.node=node;
		this.driver=driver;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		new UpocTest().DFS(startNum,node,driver);
	}

}
