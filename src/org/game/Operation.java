package org.game;

import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.paint.Color;

import org.food.Food;
import org.snake.Snake;
import org.snake.Snake$Node;
import org.watcher.ConcreteWatched;
import org.watcher.ConcreteWatcher;
import org.watcher.Watched;
import org.watcher.Watcher;

public class Operation {//初始化，开始条件
Food food = new Food();
int foodX;
int foodY;
int snakeX;
int snakeY; 
static int width;
static int height;

Watched cw = new ConcreteWatched();
Watcher watcher1 = new ConcreteWatcher();

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
public void init(Snake snake){
	snakeX=new Random().nextInt(15);
	snakeY=new Random().nextInt(15);
	snake.setSnakeNode(new Snake$Node(snakeX,snakeY,Color.GREEN));
	food.setWidth(width);
	food.setHeight(height);
	food.addFood();
}

public void Data(Snake snake) {
		if(snake.isStatus()) {
			snakeX=snake.getSnakeNode().get(0).getX();
			snakeY=snake.getSnakeNode().get(0).getY();
			foodX=food.getX();
			foodY=food.getY();
			snake_food(snake);
			outBound(width,height,snake);
		}
	}

public  void outBound(int width,int height,Snake snake) {
	if(snakeX>width||snakeX<0||snakeY>height||snakeY<0){
		System.out.println("撞墙了");
		snake.setStatus(false);
	}
}

public void snake_food(Snake snake){
	if(snakeX==foodX&&snakeY==foodY){
		cw.addWatcher(watcher1);
		cw.notifyWatchers("食物被吃了");
		food.addFood();
		foodX=food.getX();
		foodY=food.getY();
		food.setFoodStatus(true);
		snake.eat();
		snake.grow();
		snake.setLength(snake.getLength()+1);
//		cw.removeWatcher(watcher1);
	}
}

public void keyboardControl(String direction,Snake snake) {
		snake.setDirection(direction);
	}
}