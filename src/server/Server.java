package server;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nju.edu.service.PCService;
import nju.edu.service.PCServiceImpl;


public class Server extends JFrame{
	Toolkit toolkit=Toolkit.getDefaultToolkit();
	private int frameWidth = 450;
	private int frameHeight = 350;
	int LocationX;
	int LocationY;
	int xOld,yOld;
	Dimension screen=toolkit.getScreenSize();
	JPanel decoratePanel ;
	JButton exitButton,vanishButton,setButton,confirmButton;
	JComboBox<String> portBox;
	ArrayList<String> port;
	
	
	public Server(){
		this.setTitle("Server");
		//===port====
		JLabel portLabel = new JLabel("port：");
		portLabel.setSize(60,30);
		portLabel.setLocation(125,255);
		this.add(portLabel);
		String ip="";
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JLabel IPLabel = new JLabel("IP：         " + ip);
		IPLabel.setSize(180,30);
		IPLabel.setLocation(125,215);
		this.add(IPLabel);
		port = new ArrayList<String>();
		portBox = new JComboBox<String>();
		portBox.setSize(150,30);
		portBox.setLocation(175,255);
		portBox.setEditable(true);
		portBox.setVisible(true);
		
		// 读取历史记录
		try {
			BufferedReader br = new BufferedReader(new FileReader("Port.txt"));
			String str = null;
			while ((str = br.readLine()) != null) {
				port.add(str);
			}
			br.close();
			// 最近一次记录存在最末行
			for (int i = port.size() - 1; i >= 0; i--)
				portBox.addItem(port.get(i));
			portBox.setSelectedIndex(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			portBox.setToolTipText("端口:");
		}
		this.add(portBox);
		
		confirmButton = new JButton("确认");
		confirmButton.setSize(200,34);
		confirmButton.setLocation(125,300);
		confirmButton.setFont(new Font("幼圆", Font.PLAIN, 15));
		confirmButton.setBackground(new Color(41,48,62));
		confirmButton.setForeground(Color.white);
		confirmButton.setFocusPainted(false);
		confirmButton.setBorderPainted(false);
		confirmButton.addActionListener(new confirmListener());
		confirmButton.setVisible(true);
		this.add(confirmButton);
		vanishButton = new JButton("",new ImageIcon("GUI/vanish-10.png"));
		vanishButton.setSize(12,12);
		vanishButton.setLocation(380,15);
		vanishButton.setFocusPainted(false);
		vanishButton.setBorderPainted(false);
		vanishButton.setContentAreaFilled(false);
		vanishButton.addActionListener(new vanishButtonListener());
		this.add(vanishButton);
		exitButton = new JButton("",new ImageIcon("GUI/exit-10.png"));
		exitButton.setSize(12,12);
		exitButton.setLocation(420,15);
		exitButton.setFocusPainted(false);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.addActionListener(new exitButtonListener());
		this.add(exitButton);
		//下面为居中
        this.setSize(frameWidth, frameHeight);
        this.LocationX=(screen.width -this.getWidth())/2;
        this.LocationY=(screen.height -this.getHeight())/2;       
        this.setLocation(this.LocationX,this.LocationY);
	    //居中完毕
        this.setLayout(null);
        this.setUndecorated(true);
        //设置图标
        try {
      			Image icon= ImageIO.read(new File("GUI/foxLogo50.png"));
      			this.setIconImage(icon);
      		} catch (IOException e) {
      			e.printStackTrace();
      		}
        
        this.setVisible(true);
		decoratePanel = new JPanel() {  
            @Override  
            protected void paintComponent(Graphics g) {  
                ImageIcon icon = new ImageIcon("GUI/loginBack3.jpg");  
                Image img = icon.getImage();  
                g.drawImage(img, 0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
                
                // 细致渲染、绘制背景，可控制截取图片，显示于指定的JPanel位置  
              g.drawImage(img, 0, 0, 0, 0,   
                          0, 0, icon.getIconWidth(), icon.getIconHeight(), icon.getImageObserver());  
            }  
        }; 
        decoratePanel.setLayout(null);
        decoratePanel.setSize(450,200);
        decoratePanel.setVisible(true);
        this.add(decoratePanel);
		//can drag
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
			xOld = e.getX();
			yOld = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseDragged(MouseEvent e) {
			int xOnScreen = e.getXOnScreen();
			int yOnScreen = e.getYOnScreen();
			int xx = xOnScreen - xOld;
			int yy = yOnScreen - yOld;
			Server.this.setLocation(xx, yy);
		}
		});
		
	}
	class exitButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}

	}
	class vanishButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			setExtendedState(JFrame.ICONIFIED);
			
		}
		
	}
	public class confirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String portNum = portBox.getSelectedItem().toString();
			//初始化Server RMI
			init(portNum);
			portBox.setEditable(false);
			// 存储历史记录
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter("Port.txt"));
				for (int i = 0; i < port.size(); i++)
					if (port.get(i).equals(portNum)) {
						port.remove(i);
						break;
					}
				port.add(portNum);
				int more = port.size() - 10;
				if (more > 0) {
					while (more > 0) {
						port.remove(0);
						more--;
					}
				}
				for (int i = 0; i < port.size(); i++)
					bw.write(port.get(i) + "\r\n");
				bw.close();

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
		
	}
	public void init(String port){
		try {
			String hostIP = InetAddress.getLocalHost().getHostAddress();
			int portNum = Integer.parseInt(port);
			LocateRegistry.createRegistry(portNum);
			System.out.println(hostIP);
			System.out.println(portNum);
			//------------------------------------------------------------
			PCService pcService;
			pcService = new PCServiceImpl();
			//rmi://127.0.0.1:6666/uds
			hostIP="127.0.0.1";
			String addr = "rmi://"+hostIP+":"+port+"/";
			System.out.println(addr);
			Naming.rebind(addr+"pcService",pcService);
			
			JOptionPane.showMessageDialog(null, "服务器已运行！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void main(String[] args){
		new Server();
	}
}
