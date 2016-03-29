import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


public class HuaWeiTest {
	private AppiumDriver driver; 
	List ViewList=new ArrayList();
	int size=0;
	int judge=0;
	int count=0;
	
	void run(){
		  File classpathRoot = new File(System.getProperty("user.dir"));
	        File appDir = new File(classpathRoot, "apps");
	        File app = new File(appDir, "u.apk");
	        
	        //设置自动化相关参数
	        DesiredCapabilities capabilities = new DesiredCapabilities();
	        
	        capabilities.setCapability(CapabilityType.BROWSER_NAME, "");
	        capabilities.setCapability("platformName", "Android");
	        capabilities.setCapability("deviceName", "Android Emulator");
	        
	        //设置安卓系统版本
	        capabilities.setCapability("platformVersion", "4.3");
	        //设置apk路径
	        capabilities.setCapability("app", app.getAbsolutePath()); 
	        
	        //设置app的主包名和主类名
	        capabilities.setCapability("appPackage", "com.xdf.ucan");
	        capabilities.setCapability("appActivity", "com.xdf.ucan.ui.login.StartActivity");
	        //设置udid
	        capabilities.setCapability("udid","4C3ETK157B117566");
	        
	        //初始化
	        //driver =new AppiumDriver();
	        try {
				driver = new AppiumDriver(new URL("http://0.0.0.0:4724/wd/hub"), capabilities);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}    
	        
	        
	        try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    	DFS2(0);
	}
	
	 void DFS2(int i){
	    	String page=driver.getPageSource();
	    	int hash=page.hashCode();
	    	getPageInfo(page,i);
	    	XmlParser2 xp=new XmlParser2();
	    	String path="D:\\"+i+".xml";
	    	List l=xp.run(path);
	    	String allRid="";
	    	for(int k=0;k<l.size();k++){
	    		allRid=allRid+(String)l.get(k);
	    		System.out.println((String)l.get(k));
	    	}
	    	
	    	//System.out.println(activityName);
	    	int view_list_point=isInclude(hash);
	    	System.out.println(view_list_point);
	    	if(view_list_point>=0){
	    		int point=((ViewInfo)ViewList.get(view_list_point)).getPoint();
		
	    		for(int j=point+1;j<l.size();j++){
	    			((ViewInfo)ViewList.get(view_list_point)).setPoint(j);
	        		if(l.get(j)!=null){
	        			String rid=((String)l.get(j));
	        			System.out.println(rid);
	        			if(rid.equals("com.xdf.ucan:id/item_main_iv")||
	        					rid.equals("com.xdf.ucan:id/item_main_tv")){
	        				return;
	        			}
	        			WebElement e = driver.findElementById(rid);
	        			if(rid.equals("com.xdf.ucan:id/editText1")){
	        				e.sendKeys("15861819028");
	        			}else if(rid.equals("com.xdf.ucan:id/editText2")){
	        				e.sendKeys("00zoosong");
	        			}else if(rid.equals("com.xdf.ucan:id/et_search")){
	        				//e.sendKeys("AAAAA");
	        			}else{
	        				String activityName=driver.currentActivity();
	        				System.out.println(activityName);
	        				if(activityName.equals(".ui.me.ToBindingActivity")){
	        	    			driver.findElementById("com.xdf.ucan:id/title_back_btn").click();
	        	    			return;
	        	    		}else if(activityName.equals(".ui.subscript.SearchSubscriptionActivity")){
	        	    			driver.findElementById("com.xdf.ucan:id/tv_search_cancel").click();
	        	    			return;
	        	    		}else if(activityName.equals(".HwResolverActivity")){
	        	    			driver.sendKeyEvent(4);//back
	        	    			driver.sendKeyEvent(4);//back
	        	    			return;
	        	    		}else{
	        	    			if(rid.equals("com.xdf.ucan:id/ib_sub_add")){
	        	    				count++;
	        	    				if(count<5){
	        	    					System.out.println("Pressing1 "+rid);
	                    				e.click();
	        	    				}else{
	        	    					j=l.size();
	        	    					((ViewInfo)ViewList.get(view_list_point)).setPoint(j);
	        	    				}
	        	    			}else{
	        	    				System.out.println("Pressing1 "+rid);
	                				e.click();
	        	    			}	
	        	    		}

	        				if(activityName.equals(".ui.login.LoginActivity")&&
	        						rid.equals("com.xdf.ucan:id/button1")){
	        					try {
	                				Thread.sleep(3000);
	                			} catch (InterruptedException a) {
	                				// TODO Auto-generated catch block
	                				a.printStackTrace();
	                			}
	        				}else{
	        					try {
	                				Thread.sleep(1500);
	                			} catch (InterruptedException a) {
	                				// TODO Auto-generated catch block
	                				a.printStackTrace();
	                			}
	        				}	
	        				
	        				String newPage=driver.getPageSource();
	        				int newHash=newPage.hashCode();
	        				if(page.equals(newPage)){
	        					System.out.println("YES");
	        				}else{

	        					DFS2(i+1);
	        			
	        				}
	        			}		
	        		}
	        	}
	    		System.out.println("Exiting...");
	    		
	    		driver.sendKeyEvent(4);//back
	    		try {
					Thread.sleep(2000);
				} catch (InterruptedException a) {
					// TODO Auto-generated catch block
					a.printStackTrace();
				}
	    		
	    	}else{
	    		ViewInfo v=new ViewInfo();
	    		v.setHash(hash);
	    		v.setComponent(l);
	    		ViewList.add(v);
	    		for(int j=0;j<l.size();j++){
	    			
	    			((ViewInfo)ViewList.get(ViewList.size()-1)).setPoint(j);
	        		if(l.get(j)!=null){
	        			String rid=((String)l.get(j));
	        			System.out.println(rid);
	        			if(rid.equals("com.xdf.ucan:id/item_main_iv")||
	        					rid.equals("com.xdf.ucan:id/item_main_tv")){
	        				return;
	        			}
	        			WebElement e = driver.findElementById(rid);
	        			if(rid.equals("com.xdf.ucan:id/editText1")){
	        				e.sendKeys("15861819028");
	        			}else if(rid.equals("com.xdf.ucan:id/editText2")){
	        				e.sendKeys("00zoosong");
	        			}else if(rid.equals("com.xdf.ucan:id/et_search")){
	        				//e.sendKeys("AAAAA");
	        			}else{
	        				String activityName=driver.currentActivity();
	        				System.out.println(activityName);
	        				if(activityName.equals(".ui.me.ToBindingActivity")){
	        	    			driver.findElementById("com.xdf.ucan:id/title_back_btn").click();
	        	    			return;
	        	    		}else if(activityName.equals(".ui.subscript.SearchSubscriptionActivity")){
	        	    			driver.findElementById("com.xdf.ucan:id/tv_search_cancel").click();
	        	    			return;
	        	    		}else if(activityName.equals(".HwResolverActivity")){
	        	    			driver.sendKeyEvent(4);//back
	        	    			driver.sendKeyEvent(4);//back
	        	    			return;
	        	    		}else{
	        	    			if(rid.equals("com.xdf.ucan:id/ib_sub_add")){
	        	    				count++;
	        	    				if(count<5){
	        	    					System .out.println("Pressing2 "+rid);
	                    				e.click();
	        	    				}else{
	        	    					j=l.size();
	        	    					((ViewInfo)ViewList.get(view_list_point)).setPoint(j);
	        	    				}
	        	    			}else{
	        	    				System.out.println("Pressing2 "+rid);
	                				e.click();
	        	    			}	
	        	    		}
	        				if(activityName.equals(".ui.login.LoginActivity")&&
	        						rid.equals("com.xdf.ucan:id/button1")){
	        					try {
	                				Thread.sleep(3000);
	                			} catch (InterruptedException a) {
	                				// TODO Auto-generated catch block
	                				a.printStackTrace();
	                			}
	        				}else{
	        					try {
	                				Thread.sleep(1500);
	                			} catch (InterruptedException a) {
	                				// TODO Auto-generated catch block
	                				a.printStackTrace();
	                			}
	        				}
	        				String newPage=driver.getPageSource();
	        				int newHash=newPage.hashCode();
	        				if(newPage.equals(page)){
	        					System.out.println("YES");
	        				}else{
	        					DFS2(i+1);
	        				} 
	        			}
	        					
	        		}
	        	}
	    		
	    			driver.sendKeyEvent(4);//back
	    			try {
	    				Thread.sleep(2000);
	    			} catch (InterruptedException a) {
	    				// TODO Auto-generated catch block
	    				a.printStackTrace();
	    			}
	    	} 
	    	return;
	    }
	    
	    int isInclude(int h){
	    	for(int i=0;i<ViewList.size();i++){
	    		ViewInfo v=(ViewInfo)ViewList.get(i);
	    		if(h==v.getHash()){
	    			return i;
	    		}
	    	}
	    	
	    	return -1;
	    }
	    
	    String  getAllRid(String page){
	    	String path="buff.xml";
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
	        XmlParser2 xp=new XmlParser2();
	        List l=xp.run(path);
	        String allRid="";
	    	for(int k=0;k<l.size();k++){
	    		allRid=allRid+(String)l.get(k);
	    	}
	    	return allRid;
	    }

	    
	    void getPageInfo(String page,int i){
	    	String path="D:\\"+i+".xml";
	    	
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
}
