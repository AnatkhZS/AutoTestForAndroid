package nju.edu.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

import nju.edu.bean.Device;

public interface PCService extends Remote {
	public ArrayList<String> getDeviceList() throws RemoteException;
	boolean upload(String filename, byte[] fileContent) throws RemoteException;
	void coverageTest(String filename) throws RemoteException;
}