package org.server;

import java.awt.Font;
import java.awt.Label;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.game.Operation;
import org.food.Food;
import org.snake.Snake;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.application.Application;  
import javafx.scene.Group;  
import javafx.event.EventHandler;  
import javafx.scene.Scene;  
import javafx.scene.canvas.Canvas;  
import javafx.scene.canvas.GraphicsContext;  
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;  
import javafx.scene.paint.Color;  
import javafx.scene.shape.Circle;
import javafx.stage.Stage;  
import javafx.scene.control.ChoiceBox;  
import javafx.scene.effect.BlendMode;
import javafx.collections.FXCollections;  
import javafx.beans.value.ChangeListener;  
import javafx.beans.value.ObservableValue;  
import javafx.scene.layout.BorderPane;  
import javafx.scene.layout.Pane;  
  
public class CanvasView extends Application {  
	
	private static final int DISTANCE = 20;
	Random random = new Random();
    private Group root;  
    private BorderPane borderPane;  
    private Canvas layer;  
    private GraphicsContext gc;   
	Operation operation = new Operation();
	Food foods = new Food();
	List<Snake> snakeList = new ArrayList<Snake>();
	Scene scene;
	Thread thread = new Thread();
	Task<Void> progressTask;
	
    public void InitShow() throws SocketException{
    	operation.setHeight(20);
    	operation.setWidth(20);
    	snakeList.add(new Snake());
    	operation.init(snakeList.get(0));
    	thread= new Thread(snakeList.get(0));
    	thread.run();
    	addFood(foods.getX()*DISTANCE,foods.getY()*DISTANCE);
    }
	
    public void addFood(int x,int y){
        gc.setFill(Color.RED);
    	gc.fillOval(x,y, 20, 20);
    }
    
    public void SnakeShow(Snake snake){
        gc.setFill(Color.BLACK);
    	for(int j=0;j<snakeList.get(0).getLength();j++){
    		gc.fillOval(snakeList.get(0).getSnakeNode().get(j).getX()*DISTANCE,snakeList.get(0).getSnakeNode().get(j).getY()*DISTANCE, 20, 20);
    	}
    }
    
    private void headMove(Scene scene) {  
    	scene.addEventHandler(KeyEvent.KEY_PRESSED,new EventHandler<KeyEvent>() {  
        	public void handle(KeyEvent event) {  
        		switch (event.getCode()) {  
        		case UP:if(!snakeList.get(0).getDirection().equals("DOWN"))operation.keyboardControl("UP",snakeList.get(0)); break;  
        		case RIGHT:if(!snakeList.get(0).getDirection().equals("LEFT"))operation.keyboardControl("RIGHT",snakeList.get(0)); break;  
        		case DOWN:if(!snakeList.get(0).getDirection().equals("UP"))operation.keyboardControl("DOWN",snakeList.get(0)); break;  
        		case LEFT:if(!snakeList.get(0).getDirection().equals("RIGHT"))operation.keyboardControl("LEFT",snakeList.get(0)); break;
				default:
					break;
        		}
            event.consume();
          }
        });
      }  
    
    private void createLayers() {  
        layer = new Canvas(400, 400);  
        gc = layer.getGraphicsContext2D();  
    }
    
    private void addLayers() {   
        Pane pane = new Pane();  
        pane.getChildren().add(layer);  
        layer.toFront();  
        borderPane.setCenter(pane);  
        root.getChildren().add(borderPane);  
    }  
  
    public static void main(String[] args) {  
        launch(args);  
    }  
    public void show(){
    	if(!snakeList.get(0).isStatus()){
    		thread.interrupt();
    	}
    	else{
    		addFood(foods.getX()*DISTANCE,foods.getY()*DISTANCE);
    		operation.Data(snakeList.get(0));
    		SnakeShow(snakeList.get(0));
    		headMove(scene);
    	}
    }
    
    @Override  
    public void start(Stage primaryStage) throws SocketException {  
		borderPane = new BorderPane();
		primaryStage.setTitle("Ã∞≥‘…ﬂ");
		root = new Group();
		scene = new Scene(root);
		createLayers();
		InitShow();
		addLayers();
		progressTask = new Task<Void>() {
			protected Void call() throws Exception {
				while (true) {
					show();
					if (!snakeList.get(0).isStatus()) {
						break;
					}
					Thread.sleep(50);
					gc.clearRect(0, 0, 400, 400);
				}
				return null;
			}
      };
      new Thread(progressTask).start();
      primaryStage.setScene(scene);  
      primaryStage.show();
    }  
}  