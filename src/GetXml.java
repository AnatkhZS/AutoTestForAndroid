import io.appium.java_client.AppiumDriver;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities; 


import java.io.File;  
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.OutputStream;
import java.net.URL;  

import java.util.List;

 
public class GetXml {
	private AppiumDriver driver; 

    @Before
    public void setUp() throws Exception {
    	
        //设置apk的路径
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
        
        //初始化
        driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);       
    }
    
    //测试主体
    @Test
    public void addContact(){
    	getXml(0);
    }
    
    void getXml(int i){ //每产生一个新的界面都要重新调用一次
    	String page=driver.getPageSource(); //得到当前页面信息
    	getPageInfo(page,i); //把page写入xml文件中方便解析
    	XmlParser2 xp=new XmlParser2();
    	String path="D:\\"+i+".xml";
    	List l=xp.run(path); //获得当前页面叶子节点的resource id
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
    
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }
}