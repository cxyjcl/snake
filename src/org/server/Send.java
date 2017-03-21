package org.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Send extends Thread{
	DatagramSocket ds;
	int num;
	String string;
	boolean status=true;
	public DatagramSocket getDs() {
		return ds;
	}
	public void setDs(DatagramSocket ds) {
		this.ds = ds;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Send(DatagramSocket ds,int num, String string) {
		super();
		this.ds = ds;
		this.num = num;
		this.string = string;
	}
	@Override
	public synchronized void run() {
				byte[] buf = string.getBytes();
				DatagramPacket dp2;
				try {
					dp2 = new DatagramPacket(buf, buf.length,InetAddress.getByName("113.57.74.175"),num);
					ds.send(dp2);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
}
