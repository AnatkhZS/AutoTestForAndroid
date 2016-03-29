package Upoc;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class CMDTest {
	public static void main(String args[]) throws IOException{
		ProcessBuilder pb = new ProcessBuilder();  
        pb.redirectErrorStream(true);  
        String command="appium -p 4237 -U ASDFDSF";
        String path="D:\\1.bat";
		Runtime r=Runtime.getRuntime();
		r.exec("cmd /c taskkill /f /t /im node.exe");
		//Desktop.getDesktop().open(new File("d:/1.bat"));
		//r.exec("cmd /k D:\\1.bat");
	}
}
