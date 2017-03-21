package org.snake;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.paint.Color;

public class Snake implements Runnable {
	private LinkedList<Snake$Node> snakeNode = new LinkedList<Snake$Node>();
	private static boolean status = true;
	private String direction = "RIGHT";
	private long Speed = 500l;
	private int length = 1;
	Timer timer = new Timer();

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public long getSpeed() {
		return Speed;
	}

	public void setSpeed(long speed) {
		Speed = speed;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public LinkedList<Snake$Node> getSnakeNode() {
		return snakeNode;
	}

	public void setSnakeNode(Snake$Node node) {
		snakeNode.addFirst(node);
	}

	public boolean isStatus() {
		if (eatSelf()){
			System.out.println("咬自己了");
			status = false;
		}
		if(!status){
			System.out.println("蛇死了!");
			timer.cancel();
		}
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public synchronized void run() {
//		timer.schedule(new TimerTask() {
//			public void run() {
//				Snake$Node e = snakeNode.getFirst();
//				int x = e.getX();
//				int y = e.getY();
//				switch (direction) {
//				case "UP":
//					y--;
//					break;
//				case "DOWN":
//					y++;
//					break;
//				case "LEFT":
//					x--;
//					break;
//				case "RIGHT":
//					x++;
//					break;
//				}
//				snakeNode.removeLast();
//				snakeNode.addFirst(new Snake$Node(x, y, Color.GREEN));
//			}
//		},0,Speed);
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				Snake$Node e = snakeNode.getFirst();
				int x = e.getX();
				int y = e.getY();
				switch (direction) {
				case "UP":
					y--;
					break;
				case "DOWN":
					y++;
					break;
				case "LEFT":
					x--;
					break;
				case "RIGHT":
					x++;
					break;
				}
				snakeNode.removeLast();
				snakeNode.addFirst(new Snake$Node(x, y, Color.GREEN));
			}
		}, 0l,Speed);
	}

	public void eat() {
		System.out.println("吃到了!");
	}
	
	public void grow(){
		Snake$Node e = snakeNode.getLast();
		int x = e.getX();
		int y = e.getY();
		switch (direction) {
			case "UP":
				y++;
				break;
			case "DOWN":
				y--;
				break;
			case "LEFT":
				x++;
				break;
			case "RIGHT":
				x--;
				break;
		}
		snakeNode.addLast(new Snake$Node(x, y, Color.GREEN));
	}

	
	public boolean eatSelf() {
		int x = snakeNode.get(0).getX();
		int y = snakeNode.get(0).getY();
		for (int i = 1; i < snakeNode.size(); i++) {
			if (snakeNode.get(i).getX() == x && snakeNode.get(i).getY() == y)
				return true;
		}
		return false;
	}

}