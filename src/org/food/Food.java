package org.food;

import java.util.Random;

public class Food{
	private static int x;
	private static int y; 
	private static boolean foodStatus=true;
	public static int width;
	public static int height;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public static boolean isFoodStatus() {
		return foodStatus;
	}
	public static void setFoodStatus(boolean foodStatus) {
		Food.foodStatus = foodStatus;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void addFood(){
		x=new Random().nextInt(width);
		y=new Random().nextInt(height);
	}
}
