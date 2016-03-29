import java.io.IOException;


public class Driver {
	public static void main(String args[]){
		Start1 r1=new Start1();
		Start2 r2=new Start2();
		Start3 r3=new Start3();
		Start4 r4=new Start4();
		
		Thread t1=new Thread(r1);
		Thread t2=new Thread(r2);
		Thread t3=new Thread(r3);
		Thread t4=new Thread(r4);
		
	/*	t1.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t2.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		t3.start();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
		
		t4.start();
		
		
	}

}

class Start1 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new SamSungTest().run();
		
	}
	
}
class Start2 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new HuaWeiTest().run();
		
	}
	
}
class Start3 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new XiaoMiTest().run();
		
	}
	
}
class Start4 implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		new MeiTuTest().run();
		
	}
	
}
