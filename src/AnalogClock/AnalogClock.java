//Tinkering around with some animation and menu interaction 
package AnalogClock;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.*;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line; 
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.shape.Circle;

import static java.lang.Math.random;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.toRadians;

import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleGroup;

public class AnalogClock extends Application{
	
	//main timeline
	private Timeline timeline;
	private AnimationTimer timer;
	
	//variable for storing actual frame
	private Integer i=0; 
	private Integer s=0;
	private Integer m=0;
	private Integer h=0; 
	private Integer mp=1; 
	private Integer t=0;
	
	//angle 
	private Integer ang = 270;
	private Integer minang = 270;
	private Integer hrang = 270;
	
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Clcok");
		GridPane grid = new GridPane(); // creates a GridPane object and assigns it to the variable named grid
		grid.setHgap(10);				//spacing between the rows and columns
		grid.setVgap(10);		
		grid.setPadding(new Insets(0, 25, 25, 25)); //space around the edge of the grid pane, insets: top, right, bottom, left. in pixels

		
		Group p = new Group();
		//Scene scene = new Scene(p); //root, 800, 600, Color.WHITE);
		//stage.setScene(scene);
        //stage.setWidth(500);
        //stage.setHeight(500);
        //p.setTranslateX(100);
        //p.setTranslateY(100);
        
        //Create the menu bar
        MenuBar mb = new MenuBar(); 
        
        // Create the File menu. 
        Menu fileMenu = new Menu("File");
        
        //Speed Multiplier Menu 
        Menu multiplyer = new Menu("Multiplier");
        
        ToggleGroup toggleGroup = new ToggleGroup(); 
        
        
        Label time = new Label("Time: "); 
        // p.getChildren().add(time);
        grid.add(time, 0, 4);
        time.setTranslateX(60);
        
        Label n12 = new Label("XII"); 
        n12.setTranslateX(92);
        n12.setTranslateY(2);
        Label n3 = new Label("III");
        n3.setTranslateX(188);
        n3.setTranslateY(91);
        Label n6 = new Label("VI");
        n6.setTranslateX(95);
        n6.setTranslateY(183);
        Label n9 = new Label("IX");
        n9.setTranslateX(5);
        n9.setTranslateY(91);
        
        p.getChildren().add(n12);
        p.getChildren().add(n3);
        p.getChildren().add(n6);
        p.getChildren().add(n9);
        
        Label timeOut = new Label("0:00"); 
        grid.add(timeOut, 0, 4);
        timeOut.setTranslateX(110);
		
		//create second hand line 
        final Line line = new Line();
        line.setStartX(100);
        line.setStartY(100);
        line.setEndX(100); 
        line.setEndY(0);
        
        //create minute hand line
        final Line minhand = new Line();
        minhand.setStartX(100);
        minhand.setStrokeWidth(2); 
        minhand.setStartY(100);
        minhand.setEndX(100);
        minhand.setEndY(18);
        
        //create hour hand line
        final Line hrhand = new Line();
        hrhand.setStartX(100);
        hrhand.setStrokeWidth(5); 
        hrhand.setStartY(100);
        hrhand.setEndX(100);
        hrhand.setEndY(30);
        
        Circle clockface = new Circle(100, 100, 100);  //x, y, radius
		clockface.setStrokeType(StrokeType.OUTSIDE); //border around circles 
		clockface.setStroke(Color.web("black", 0.66));
		clockface.setStrokeWidth(4); //boarder 
		clockface.setFill(null);
		
		Circle hourface = new Circle(100, 100, 70);
		hourface.setStrokeType(StrokeType.OUTSIDE); //border around circles 
		hourface.setStroke(Color.web("black", 0.16));
		hourface.setStrokeWidth(4); //boarder 
		hourface.setFill(null);
		
		Circle center = new Circle(100, 100, 5);
        
        p.getChildren().add(line);
        p.getChildren().add(minhand);
        p.getChildren().add(hrhand);
        p.getChildren().add(clockface);
        p.getChildren().add(hourface);
        p.getChildren().add(center);

        //create a timeline for moving the line
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        
 //You can add a specific action when each frame is started. 
        timer = new AnimationTimer() {
        	@Override
        	public void handle(long l){
        		i++;
        		//System.out.println("new frame");
        	}
        };
 
        //one can add a specific action when keyframe is reached
        EventHandler onFinished = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                //line.setTranslateX(java.lang.Math.random()*200-100);
            	line.setEndX(100+100*cos(toRadians(ang)));
            	line.setEndY(100+100*sin(toRadians(ang)));
            	ang=ang+6;
            	Integer ModCheckAngle = ang-276; 
            	String secangle = Integer.toString(ModCheckAngle);
            	int rem = ModCheckAngle % 2; 
            	if (s==60){
            		minang=minang+6;
            		minhand.setEndX(100+82*cos(toRadians(minang)));
            		minhand.setEndY(100+82*sin(toRadians(minang)));
            		Integer ModCheckMinAng = minang-270; 
            		String minangle = Integer.toString(ModCheckMinAng);
            		//System.out.println("Minute Angle= " +minangle+" Second= "+s );
            		m=m+1; 
            		s=0; 
            		if (m==60){
            			h=h+1;
                		hrang=hrang+30;
                		hrhand.setEndX(100+70*cos(toRadians(hrang)));
                		hrhand.setEndY(100+70*sin(toRadians(hrang)));
            			m=0; 
            			if (h==24){
            				h=0; // reset day
            			};
            		};
            	};
            	//System.out.println("Second Angle= "+secangle);
            	//reset.counter
                s = s+1;
                //System.out.println("Hour: " +h+" Minute: "+m+" Second: "+s);
                timeOut.setText(h+":"+m+":"+s);
           }
       }; 
       
       RadioMenuItem radioItem1 = new RadioMenuItem("x1");
       radioItem1.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent r1) {
        	   timeline.stop();
        	   timer.stop();
               System.out.println("x1");
               Duration duration = Duration.millis(1000);
               KeyFrame keyFrame = new KeyFrame(duration, onFinished);// 
               timeline.getKeyFrames().clear();
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timer.start();
           }
       });
       radioItem1.setToggleGroup(toggleGroup);
       
       RadioMenuItem radioItem2 = new RadioMenuItem("x10");
       radioItem2.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent r2) {
        	   timeline.stop();
        	   timer.stop();
               System.out.println("x10");
               Duration duration = Duration.millis(100);
               KeyFrame keyFrame = new KeyFrame(duration, onFinished);// 
               timeline.getKeyFrames().clear();
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timer.start();
           }
       });
       radioItem2.setToggleGroup(toggleGroup);
       
       RadioMenuItem radioItem3 = new RadioMenuItem("x100");
       radioItem3.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent r3) {
        	   timeline.stop();
        	   timer.stop();
               System.out.println("x100");
               Duration duration = Duration.millis(10);
               KeyFrame keyFrame = new KeyFrame(duration, onFinished);// 
               timeline.getKeyFrames().clear();
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timer.start();
           }
       });
       radioItem3.setToggleGroup(toggleGroup);
       
       RadioMenuItem radioItem4 = new RadioMenuItem("x1000");
       radioItem4.setOnAction(new EventHandler<ActionEvent>() {
           @Override public void handle(ActionEvent r4) {
        	   timeline.stop();
        	   timer.stop();
               System.out.println("x1000");
               Duration duration = Duration.millis(1);
               KeyFrame keyFrame = new KeyFrame(duration, onFinished);// 
               timeline.getKeyFrames().clear();
               timeline.getKeyFrames().add(keyFrame);
               timeline.play();
               timer.start();
           }
       });
       radioItem4.setToggleGroup(toggleGroup);


       multiplyer.getItems().add(radioItem1); 
       multiplyer.getItems().add(radioItem2); 
       multiplyer.getItems().add(radioItem3); 
       multiplyer.getItems().add(radioItem4); 
       
       MenuItem exit = new MenuItem("Exit");
       exit.setOnAction(new EventHandler<ActionEvent>() {
    	    public void handle(ActionEvent t) {
    	        System.exit(0);
    	    }
    	});
       
       
       fileMenu.getItems().addAll(multiplyer, new SeparatorMenuItem(), exit);
       
       // Add File menu to the menu bar. 
       mb.getMenus().add(fileMenu) ; 
       
       // Create the Help menu
       Menu helpMenu = new Menu("Help"); 
       MenuItem about = new MenuItem("About");
       about.setOnAction(new EventHandler<ActionEvent>() {
   	    public void handle(ActionEvent a) {
            final Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(primaryStage);
            VBox dialogVbox = new VBox(20);
            dialogVbox.getChildren().add(new Text(" Select Multiplier from File Menu"));
            Scene dialogScene = new Scene(dialogVbox, 250, 50);
            dialog.setScene(dialogScene);
            dialog.show();
   	    }
       });
       
       helpMenu.getItems().add(about);
       mb.getMenus().add(helpMenu);
       
       grid.add(mb, 0, 0);
        
       //create a keyFrame, the keyValue is reached at time 1s = 1000
       System.out.println(mp);
       Duration duration = Duration.millis(1000);
       KeyFrame keyFrame = new KeyFrame(duration, onFinished);// 
       timeline.getKeyFrames().add(keyFrame);
       timeline.play();
       timer.start();
       
       grid.add(p, 0, 3); // column, row 
       
       //grid.setGridLinesVisible(true); //added for debugging purposes, uncomment to see grid layout
       Scene scene = new Scene(grid, 260, 350); // scene created with grid being the root node
       primaryStage.setScene(scene);
       primaryStage.show();   
	}
	
	public static void main(String[] args){
		Application.launch(args);
	}
}
