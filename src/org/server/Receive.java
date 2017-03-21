package org.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javafx.scene.paint.Color;

import org.food.Food;
import org.snake.Snake;
import org.snake.Snake$Node;

public class Receive extends Thread{
	DatagramSocket ds;
	Snake snake;
	Food food;
	int j=1;
	boolean InetStatus=true;
	public Receive(DatagramSocket ds, Snake snake2, Food foods) {
		super();
		this.ds = ds;
		snake=snake2;
		food = foods;
	}
	public void Deal(String str){
		String arr[]=str.split(" ");
		int i;
		while(j<(arr.length-4)/2){
			snake.getSnakeNode().addFirst(new Snake$Node(1, 1, Color.RED));
			j++;
		}
		for(i=0;i<(arr.length-4)/2;i++){
			snake.getSnakeNode().get(i).setX(Integer.parseInt(arr[2*i]));
			snake.getSnakeNode().get(i).setY(Integer.parseInt(arr[2*i+1]));
		}
		food.setX(Integer.parseInt(arr[2*i]));
		food.setY(Integer.parseInt(arr[2*i+1]));
		snake.setLength(Integer.parseInt(arr[2*(i+1)]));
		snake.setDirection(arr[2*(i+1)+1]);
	}
	@Override
	public synchronized void run() {
		while(true){
			byte[] buf = new byte[1024];
			DatagramPacket dp = new DatagramPacket(buf, buf.length);
		try {
			ds.receive(dp);
			String str = new String(dp.getData());
			str = new String(str.substring(0,dp.getLength()));
			Deal(str);
		}catch (IOException e1) {
			e1.printStackTrace();
		}
	 }
  }
}
