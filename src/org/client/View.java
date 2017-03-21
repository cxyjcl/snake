package org.client;
import java.awt.Paint;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import org.game.Operation;
import org.food.Food;
import org.server.Receive;
import org.server.Send;
import org.snake.Snake;
public class View extends Application {
	private static final int DISTANCE = 20;  
	Scene scene = new Scene(new Group(), 400, 400);//40个格子
	ObservableList<Node> content = ((Group)scene.getRoot()).getChildren();
	List<Circle> circleList =new ArrayList<Circle>();
	Circle food = new Circle();
	Operation operation = new Operation();
	Food foods = new Food();
	List<Snake> snakeList = new ArrayList<Snake>();
	Snake snake1 = new Snake();
	Snake snake2 = new Snake();
	int i=0;
//	DatagramSocket ds;
    public void start(Stage stage) throws Exception {
    	InitShow();
    	operation.Data(snakeList.get(0));
//      operation.Data(snake2);
    	stage.setScene(scene);
    	show();
    	stage.show();
    	 Task<Void> progressTask = new Task<Void>(){  
             protected Void call() throws Exception {  
                while(true){  
                     Thread.sleep(50);  
             			show();
                 }
             }  
       };
       new Thread(progressTask).start();
    }
    public void show(){
    	if(!snakeList.get(0).isStatus())
    		System.out.println("蛇已经死了");
    	else{
    		operation.Data(snakeList.get(0));
    		if(foods.isFoodStatus()){
    			foods.setFoodStatus(false);
    			Circle c= new Circle();
    			c.setId("蛇一");
    			circleList.add(c);
    		}
    		content.add(addSnake(0,0, new Circle()));
    		reproductFood(scene,food);
//    		SnakeShow(snake1,snake2);
    		SnakeShow(snakeList.get(0));
    		headMove(scene);
    	}
//    	bindString(snake1);
    }
    private void headMove(Scene scene) {  
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {  
        	public void handle(KeyEvent event) {  
        		switch (event.getCode()) {  
        		case UP:if(!snakeList.get(0).getDirection().equals("DOWN"))operation.keyboardControl("UP",snakeList.get(0)); break;  
        		case RIGHT:if(!snakeList.get(0).getDirection().equals("LEFT"))operation.keyboardControl("RIGHT",snakeList.get(0)); break;  
        		case DOWN:if(!snakeList.get(0).getDirection().equals("UP"))operation.keyboardControl("DOWN",snakeList.get(0)); break;  
        		case LEFT:if(!snakeList.get(0).getDirection().equals("RIGHT"))operation.keyboardControl("LEFT",snakeList.get(0)); break;
        		}
            event.consume();
          }
        });
      }  
    
//    public void bindString(Snake snake){
//    	String str ="";
//    	for(int j=0;j<snake.getLength();j++){
//    		str += snake.getSnakeNode().get(j).getX()+" "+snake.getSnakeNode().get(j).getY()+" ";
//    	}
//    	str+=foods.getX()+" ";
//    	str+=foods.getY()+" ";
//    	str+=snake.getLength()+" ";
//    	str+=snake.getDirection();
//    	new Thread(new Send(ds,10000,str)).start();
//    }
//    
    public void SnakeShow(Snake snake){
    	System.out.println(snake.getSnakeNode().get(0).getY());
    	for(int j=0;j<snake.getLength();j++){
    		circleList.get(j).setCenterX(snakeList.get(0).getSnakeNode().get(j).getX()*DISTANCE+10);
    		circleList.get(j).setCenterY(snakeList.get(0).getSnakeNode().get(j).getY()*DISTANCE+10);
    	}
    }
    
    public Node addSnake(int x,int y,Circle c){
    	c.setFill(Color.BLACK);
    	c.setCenterX(x);
    	c.setCenterY(y);
    	c.setRadius(10);	
		Group g = new Group();
		g.setBlendMode(BlendMode.SRC_OVER);
		g.getChildren().add(c);
		return g;
    }
    
    public Node addFood(int x,int y){
		food.setFill(Color.RED);
		food.setCenterX(x);
		food.setCenterY(y);
		food.setRadius(5);	
		Group g = new Group();
		g.setBlendMode(BlendMode.SRC_OVER);
		g.getChildren().add(food);
		return g;
    }
    
    private void reproductFood(Scene scene, final Circle foodScene) {  
    	foodScene.setCenterX(foods.getX()*DISTANCE+10);
    	foodScene.setCenterY(foods.getY()*DISTANCE+10);
    }
    
    public void InitShow() throws SocketException{
//    	ds = new DatagramSocket(8888);
//    	DatagramSocket dr = new DatagramSocket(10000);
//		new Thread(new Receive(dr,snake2,foods)).start();
    	operation.setHeight(20);
    	operation.setWidth(20);
    	snakeList.add(new Snake());
    	operation.init(snakeList.get(0));
    	new Thread(snakeList.get(0)).start();
    	content.add(addFood(foods.getX()*DISTANCE+10,foods.getY()*DISTANCE+10)); 
    }
    public static void main(String[] args) throws SocketException {
    	launch(args);
    }
}
