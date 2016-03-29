package Upoc;

import io.appium.java_client.AppiumDriver;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.exec.ExecuteException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class UpocTest {
	private AppiumDriver driver; 
	List ViewList=new ArrayList();
	int size=0;
	int judge=0;
	int count=0;
	String fatherActivity;
	WebElement e;
	File log;
	boolean isEnd=false;
	ViewTreeNode root=new ViewTreeNode();

	
	ViewTreeNode run(String udid, String port, String appPackage, String appActivity, String appPath){
		
		//prepare(udid,port);
		
		try {
			stopAppiumServer(port);
		} catch (ExecuteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		System.out.println("Waiting for port "+port+" release...");

		while(isLocalPortUsing(port)){
		}
		System.out.println("Port "+port+" is released");
		
		log=new File("TestLogs\\"+udid+"_Log.txt");
		try{
			if(!log.exists()){
				log.createNewFile();
				}else{
				   log.delete();
				   log.createNewFile();
				   }
			}catch(Exception e){
				e.printStackTrace();
			}
		
		Runtime r=Runtime.getRuntime();
		Process p=null;
		try {
			r.exec("cmd /c echo appium -a 127.0.0.1 -p "+port+" -U "+udid+">bat\\"+udid+".bat");
			p=r.exec("bat\\"+udid+".bat"+" >"+"TestLogs\\"+udid+"_Log.txt");
			//Desktop.getDesktop().open(new File("bat\\"+udid+".bat"));
			//Thread.sleep(10000);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			FileReader fr=new FileReader(new File("TestLogs\\"+udid+"_Log.txt"));
			BufferedReader br=new BufferedReader(fr);
			String line=br.readLine();
			System.out.println(line);
			boolean judge=false;
			if(line==null){
				judge=true;
			}else if(line.equals("")){
				judge=true;
			}else if(line.split(" ")[1].equals("-a")){
				judge=true;
			}
			System.out.println("Waiting for server "+udid+" openning...");
			while(judge){
				line=br.readLine();
				if(line==null){
					judge=true;
				}else if(line.equals("")){
					judge=true;
				}else if(line.split(" ").length<=1){
					judge=true;
				}else if(line.split(" ")[1].equals("-a")){
					judge=true;
				}else{
					judge=false;
				}
			}
			System.out.println("Server "+udid+" is open");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File app = new File(appPath);
        
        //璁剧疆鑷姩鍖栫浉鍏冲弬鏁�
        DesiredCapabilities capabilities = new DesiredCapabilities();
        
        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "Android Emulator");
        
        //璁剧疆瀹夊崜绯荤粺鐗堟湰
        capabilities.setCapability("platformVersion", "4.3");
        //璁剧疆apk璺緞
        capabilities.setCapability("app", app.getAbsolutePath()); 
        
        //璁剧疆app鐨勪富鍖呭悕鍜屼富绫诲悕
        capabilities.setCapability("appPackage", appPackage);
        capabilities.setCapability("appActivity", appActivity);
        //璁剧疆udid
        capabilities.setCapability("udid",udid);
        
        //鍒濆鍖�
        //driver =new AppiumDriver();
		try {
			driver = new AppiumDriver(new URL("http://127.0.0.1:"+port+"/wd/hub"), capabilities);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e2){
			System.out.println("No more port...");
		}
        try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//      ScreenShotRunner s=new ScreenShotRunner(driver,udid);
//    	Thread t=new Thread(s);
//    	t.start();
        
        DFSRunner dfs=new DFSRunner(0,root,driver);
        Thread dfsThread=new Thread(dfs);
        dfsThread.start();
        try {
			Thread.sleep(1000*60*6);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			dfsThread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return root;
    	//DFS(0);
//    	System.out.println("DFS has finished");
//    	p.destroy();
//    	t.interrupt();
//    	try {
//			t.join();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
	}
	
	void prepare(String udid,String port){

	}
	
	 void DFS(int i,ViewTreeNode node,AppiumDriver driver){
		 ViewTreeNode currentNode=node;
		 
	    	String page=driver.getPageSource();
	    	String curA=driver.currentActivity();
	    	
	    	int hash=page.hashCode();
	    	getPageInfo(page,i);
	    	XmlParser xp=new XmlParser();
	    	String path="PageSource\\"+i+".xml";
	    	List l=xp.run(path);
	    	for(int k=0;k<l.size();k++){
	    		System.out.println((String)l.get(k));
	    	} 

	    	int view_list_point=isInclude(curA);
	    	if(view_list_point>=0){
	    		
	    		((ViewInfo)ViewList.get(view_list_point)).setFatherActivity(fatherActivity);
	    		int oldHash=((ViewInfo)ViewList.get(view_list_point)).getHash();
	    		if(oldHash==hash){
	    			
	    		}else{
	    			((ViewInfo)ViewList.get(view_list_point)).setHash(hash);
	    			((ViewInfo)ViewList.get(view_list_point)).setComponent(l);
	    		}
	    	}else{
	    		ViewTreeNode n=new ViewTreeNode();
	    		n.setName(curA);
	    		node.addChild(n);
	    		currentNode=n;
	    		ViewInfo v=new ViewInfo();
	    		v.setHash(hash);
	    		v.setComponent(l);
	    		v.setPoint(0);
	    		v.setActivity(driver.currentActivity());
	    		v.setFatherActivity(fatherActivity);
	    		ViewList.add(v);
	    		view_list_point=ViewList.size()-1;
	    	}
	    	
	    	fatherActivity=driver.currentActivity();
	    	String current=driver.currentActivity();
	    	int point=((ViewInfo)ViewList.get(view_list_point)).getPoint();
	    	
	    	if(((ViewInfo)ViewList.get(view_list_point)).getDirtyWord()){  //涓轰簡鍑忓皯閲嶅閬嶅巻锛屼篃鍙互涓嶅垽鏂�
	    		
	    		System.out.println("I'm going to return beacuse I'm over");
		    	
		    	((ViewInfo)ViewList.get(view_list_point)).setDirtyWord(true);
		    	
		    	while(current.equals(driver.currentActivity())){
		    		driver.sendKeyEvent(4);
			    	try {
						Thread.sleep(2000);
					} catch (InterruptedException a) {
						// TODO Auto-generated catch block
						a.printStackTrace();
					}
		    	}
		    	return;
	    	}else{
	    		for(int j=point;j<l.size();j++){
		    		String c=driver.currentActivity();
		    		System.out.println(c+"'s point is "+j+"/"+l.size());
		    		((ViewInfo)ViewList.get(view_list_point)).setPoint(j+1);
		    		if(l.get(j)!=null){
		    			String nodeInfo=(String)l.get(j);
		    			
		    			if(nodeInfo.equals("com.xdf.ucan:id/item_main_tv")){
		    				WebElement ne=driver.findElementById("FFFFFFF"); //force to end
		    			}
		    			
		    			boolean judge=false;
		    			boolean isOutOfRange=false;
		    			do{
		    				judge=false;
		    				try{
		    					System.out.println("Pressing "+nodeInfo);
		    					if(nodeInfo.substring(0,1).equals("/")){
				    				e=driver.findElementByXPath(nodeInfo);
				    			}else{
				    				e=driver.findElementById(nodeInfo);
				    			}
		    					
				    			//e=driver.findElementById(rid);
				    			}catch(Exception exception){	
				    				System.out.println("We can't find element "+nodeInfo);
				    				judge=true;
				    				j++;
				    				if(j<l.size()){
				    					nodeInfo=(String)l.get(j);
				    				}
				    			}finally{
				    				if(j>=l.size()){
				    					isOutOfRange=true;
				    					break;
				    				}
				    			}
		    			}while(judge);
		    			
		    			if(isOutOfRange){
		    				break;
		    			}
		    			
		    			
		    			String fa=((ViewInfo)ViewList.get(view_list_point)).getFatherActivity();
		    			if(nodeInfo.equals("com.xdf.ucan:id/editText1")){
	        				e.sendKeys("15861819028");
	        			}else if(nodeInfo.equals("com.xdf.ucan:id/editText2")){
	        				e.sendKeys("00zoosong");
	        			}else{
	        				e.click();
	        				try {
	            				Thread.sleep(2000);
	            			} catch (InterruptedException a) {
	            				// TODO Auto-generated catch block
	            				a.printStackTrace();
	            			}
	        				
	        				String ca=driver.currentActivity();
        					String newPage=driver.getPageSource();
            				int newHash=newPage.hashCode();
            				if(newHash==hash){
            					System.out.println("The page has not changed");
            				}else if(c.equals(ca)){
            					System.out.println("The activity has not changed");
            					String p=driver.getPageSource();
        				    	int h=p.hashCode();
        				    	getPageInfo(p,i);
        				    	XmlParser xp2=new XmlParser();
        				    	String path2="PageSource\\"+i+".xml";
        				    	List l2=xp2.run(path2);
        				    	
        				    	if(l.hashCode()!=l2.hashCode()){    				    		
        				    		int length=0;
        				    		int count=0;
        				    		if(l.size()<=l2.size()){
        				    			length=l.size();
        				    		}else{
        				    			length=l2.size();
        				    		}
        				    		for(int m=0;m<length;m++){
        				    			for(int n=0;n<length;n++){
        				    				if(((String)l2.get(n)).equals((String)l.get(m))){
        				    					count++;
        				    				}
        				    			}
        				    		}
        				    		if((2*count)>length){
        				    			((ViewInfo)ViewList.get(view_list_point)).setComponent(l2);
	        				    		l=l2;
        				    		}else{
        				    			driver.sendKeyEvent(4);
        	        					try {
        	        						Thread.sleep(1000);
        	        					} catch (InterruptedException a) {
        	        						// TODO Auto-generated catch block
        	        						a.printStackTrace();
        	        					}
        				    		}
        				    	}
            				}else{
            					String newActivity=driver.currentActivity();
            					if(newActivity.equals(fa)){
            						System.out.println("I'm going to return because of my dad"+"\r\n");
            						return;
            					}else{
            	/*					int newPagePoint=isInclude(newActivity);
            						if(newPagePoint>=1){
            							String s=((ViewInfo)ViewList.get(newPagePoint)).getFatherActivity();
            							if((!s.equals(null))&&(!s.equals(c))){
            								System.out.println("I'm going to return because not my son"+"\r\n");
            								driver.sendKeyEvent(4);
	        	        					try {
	        	        						Thread.sleep(1000);
	        	        					} catch (InterruptedException a) {
	        	        						// TODO Auto-generated catch block
	        	        						a.printStackTrace();
	        	        					}
            								return;
            							}else{ 
            								DFS(i+1);
		            						System.out.println("Has returned");
		            						fatherActivity=driver.currentActivity();
		            						
		            						String p=driver.getPageSource();
		            				    	int h=p.hashCode();
		            				    	getPageInfo(p,i);
		            				    	XmlParser xp2=new XmlParser();
		            				    	String path2="D:\\"+i+".xml";
		            				    	List l2=xp2.run(path2);
		            				    	
		            				    	if(l.hashCode()!=l2.hashCode()){
		            				    		((ViewInfo)ViewList.get(view_list_point)).setComponent(l2);
		            				    		l=l2;
		            				    	}
            							} 
            						}else{ */
            							DFS(i+1,currentNode,driver);
	            						System.out.println("Has returned");
	            						fatherActivity=driver.currentActivity();
	            						
	            						String p=driver.getPageSource();
	            				    	int h=p.hashCode();
	            				    	getPageInfo(p,i);
	            				    	XmlParser xp2=new XmlParser();
	            				    	String path2="PageSource\\"+i+".xml";
	            				    	List l2=xp2.run(path2);
	            				    	
	            				    	if(l.hashCode()!=l2.hashCode()){
	            				    		((ViewInfo)ViewList.get(view_list_point)).setComponent(l2);
	            				    		l=l2;
	            				    	}
            						}
            			//		}
            				}
	        				
	        			}
		    			
		    		} 
		    	}
	    		System.out.println("I'm going to return beacuse I've done"+"\r\n");
	    		((ViewInfo)ViewList.get(view_list_point)).setDirtyWord(true);
		    	
		    	while(current.equals(driver.currentActivity())){
		    		driver.sendKeyEvent(4);
			    	try {
						Thread.sleep(2000);
					} catch (InterruptedException a) {
						// TODO Auto-generated catch block
						a.printStackTrace();
					}
		    	}
		    	return;
		    	
	    	}
	    	
	    }
	    
	    int isInclude(String curA){      //view杩涘垪琛ㄧ殑妫�煡锛岄渶瑕侀噸鍐�   	
	    	for(int i=0;i<ViewList.size();i++){
	    		ViewInfo v=(ViewInfo)ViewList.get(i);
	    		if(curA.equals(v.getActivity())){
	    			return i;
	    		}
	    	}
	    	return -1;
	    }
	    
	    int isIncludeInList(String c,List l){      //view杩涘垪琛ㄧ殑妫�煡锛岄渶瑕侀噸鍐�   	
	    	for(int i=0;i<l.size();i++){
	    		String component=(String)l.get(i);
	    		if(c.equals(component)){
	    			return i;
	    		}
	    	}
	    	return -1;
	    }
	    
	    String  getAllRid(String page){
	    	String path="buff.xml";
	    	byte[] bytexml = page.getBytes();  
	        
	        try {  
	            OutputStream os = new FileOutputStream( new File(path));  
	            os.write(bytexml);  
	            os.flush();  
	            os.close();  
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }
	        XmlParser xp=new XmlParser();
	        List l=xp.run(path);
	        String allRid="";
	    	for(int k=0;k<l.size();k++){
	    		allRid=allRid+(String)l.get(k);
	    	}
	    	return allRid;
	    }

	    
	    void getPageInfo(String page,int i){
	    	String path="PageSource\\"+i+".xml";
	    	
	        byte[] bytexml = page.getBytes();  
	          
	        try {  
	            OutputStream os = new FileOutputStream(new File(path));  
	            os.write(bytexml);  
	            os.flush();  
	            os.close();  
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        } 
	    }
	    
	    void takeScreenShot(AppiumDriver driver,String udid)
	    {  
	    	
	    	while(true){
	       File screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);  
	       try {   
	    	   Calendar c = Calendar.getInstance();
	    	   String time=String.valueOf(c.get(Calendar.DATE))+String.valueOf(c.get(Calendar.HOUR))+String.valueOf(c.get(Calendar.MINUTE))+String.valueOf(c.get(Calendar.SECOND));
	          FileUtils.copyFile(screenShotFile, new File("ScreenShots\\"+udid+"\\" +udid+"_"+time+ ".jpg"));  
	          } 
	       catch (IOException e) {e.printStackTrace();} 
	       try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    	}
	    }
	    
	    public static void stopAppiumServer(String Port) throws ExecuteException, IOException
	    {
	        Runtime.getRuntime().exec("cmd /c echo off & FOR /F \"usebackq tokens=5\" %a in"
	                + " (`netstat -nao ^| findstr /R /C:\"" + Port + "\"`) do (FOR /F \"usebackq\" %b in"
	                + " (`TASKLIST /FI \"PID eq %a\" ^| findstr /I node.exe`) do taskkill /F /PID %a)");
	    }
	    public static boolean isLocalPortUsing(String port){  
	        boolean flag = true;  
	        try {  
	            flag = isPortUsing("127.0.0.1", port);  
	        } catch (Exception e) {  
	        }  
	        return flag;  
	    }
	    public static boolean isPortUsing(String host,String port) throws UnknownHostException{  
	        boolean flag = false;  
	        InetAddress theAddress = InetAddress.getByName(host);  
	        int p=Integer.valueOf(port);
	        try {  
	            Socket socket = new Socket(theAddress,p);  
	            flag = true;  
	        } catch (IOException e) {  
	              
	        }  
	        return flag;  
	    } 
}

