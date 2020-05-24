package application.image;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Example3T extends Application 
{
	Mat matrix = null;
	String imageName = null;
	Image imageGlob = null;
	
    public static void main(String[] args) 
    {
        launch(args);
    }
    Boolean flag = true;
    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Timeline Example" );
        
        
       
        GridPane grid = new GridPane();
        Canvas canvas = new Canvas( 1100, 600);
       
        CheckBox currentTr = new CheckBox("Текущая траектория");
        CheckBox guessTr = new CheckBox("Предполагаемая траектория");
        
        final ComboBox fileNameComboBox = new ComboBox();
        fileNameComboBox.getItems().addAll(
            "Road",
            "Car",
            "Rotate",
            "Night"
        );   
        fileNameComboBox.setValue("Road");
        fileNameComboBox.setMinWidth(200);
       
        Button button1 = new Button("Start/Pause");
        button1.setMinSize(150, 30);
        grid.add(canvas, 0,0, 1, 1);
        
        TilePane tileButtons = new TilePane(Orientation.HORIZONTAL);
        tileButtons.setPadding(new Insets(20, 10, 20, 0));
        tileButtons.setHgap(10.0);
        tileButtons.setVgap(8.0);
        tileButtons.getChildren().addAll(button1);
        tileButtons.getChildren().addAll(currentTr);
        tileButtons.getChildren().addAll(guessTr); 
        tileButtons.getChildren().addAll(fileNameComboBox); 
        tileButtons.setMargin(button1, new Insets(40, 40, 60, 60));
        tileButtons.setMargin(currentTr, new Insets(40, 40, 60, 60));
        tileButtons.setMargin(guessTr, new Insets(40, 40, 60, 60));
        tileButtons.setMargin(fileNameComboBox, new Insets(40, 40, 60, 60));
        grid.add(tileButtons, 2, 0, 10, 1);
        
        
        
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Scene theScene = new Scene( grid,1400,600 );
        theStage.setScene( theScene );
        
      
        
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( 50 );
        gc.clearRect(0, 0, 1100, 600);
        
        // background image clears canvas
      
        Queue  queueImageGreen = new LinkedList<XYXYPoint>();
        Queue queueImageRed = new LinkedList<XYXYPoint>();
        
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
               System.out.println("Click");
               if (!flag) {
            	   gameLoop.pause();          	  
            	  
               }else {
            	   gameLoop.play();
               }
               flag = !flag;
               if (fileNameComboBox.getValue().equals("Road")) {
                   
                   queueImageGreen.addAll(RoadList.getRoadGreenList());
                   queueImageRed.addAll(RoadList.getRoadRedList());
                   }
                   if (fileNameComboBox.getValue().equals("Car")) {
                       
                       queueImageGreen.addAll(CarList.getCarGreenList());
                       queueImageRed.addAll(CarList.getCarRedList());
                       }
                   if (fileNameComboBox.getValue().equals("Rotate")) {
                       
                       queueImageGreen.addAll(RotateList.getRotateGreenList());
                       queueImageRed.addAll(RotateList.getRotateRedList());
                       }
                   if (fileNameComboBox.getValue().equals("Night")) {
                       
                       queueImageGreen.addAll(NightList.getNightGreenList());
                       queueImageRed.addAll(NightList.getNightRedList());
                       }
            }
        });
        
        
        
        

        final long timeStart = System.currentTimeMillis();
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.2),                // 60 FPS
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    double t = (System.currentTimeMillis() - timeStart) / 100.0; 
                    
                    if(!queueImageGreen.isEmpty()) {
                    	XYXYPoint itemGreen = (XYXYPoint)queueImageGreen.remove();
                    	XYXYPoint itemRed = (XYXYPoint)queueImageRed.remove();
                    	GraphicsContext gc = canvas.getGraphicsContext2D();
                    	String folderName = "";
                    	if(fileNameComboBox.getValue().equals("Road")) {
                    		 folderName = "road";
                    	}
                    	if(fileNameComboBox.getValue().equals("Car")) {
                   		 folderName = "car";
                   	}
                    	if(fileNameComboBox.getValue().equals("Rotate")) {
                      		 folderName = "rotate";                      		
                      	}
                    	if(fileNameComboBox.getValue().equals("Night")) {
                     		 folderName = "night";                      		
                     	}
                    	String imagePath =  "application/resource/image/"+ folderName+"/"+itemGreen.getName()+".png";
                    	 System.out.println(imagePath);
                    	Image space = new Image( imagePath );
	                   
	                    if(itemGreen.getName() != null && imageName!= null && itemGreen.getName().equals(imageName)) {
	                    	space = imageGlob;	                    	
	                   
	                    }
	                    imageName = itemGreen.getName();
	                    BufferedImage image = SwingFXUtils.fromFXImage(space, null);
	                    Graphics2D g2d = image.createGraphics();
	                    
	                    if (currentTr.isSelected()) { 
	                    g2d.setColor(java.awt.Color.GREEN);
	                    g2d.setStroke(new BasicStroke(10));
	                    g2d.drawLine(itemGreen.getX1(), itemGreen.getY1(), itemGreen.getX2(), itemGreen.getY2());
	                    }
	                    if (guessTr.isSelected()) { 
		                    g2d.setColor(java.awt.Color.RED);
		                    g2d.setStroke(new BasicStroke(10));
		                    g2d.drawLine(itemRed.getX1(), itemRed.getY1(), itemRed.getX2(), itemRed.getY2());
		                    }
	                   // g2d.fill(new Ellipse2D.Float(0, 0, 200, 100));
	                    g2d.dispose();
	                    imageGlob = SwingFXUtils.toFXImage(image, null);
	                    gc.drawImage(imageGlob, 0, 0);
	                    gc.stroke();
	                  //  drawLine(gc, t, item.getX1(), item.getY1(), item.getX2(), item.getY2() );
	                    if (currentTr.isSelected()) {
	                    	System.out.println("current");
	                    }
                    }
                   
                }
            });
        
        gameLoop.getKeyFrames().add( kf );
       // gameLoop.play();
        
        theStage.show();
        
    }
    
    private void drawLine(GraphicsContext gc, double t, Integer x1, Integer y1, Integer x2, Integer y2) {
            
            for (int i = 0; i < 10; i++) {
                  gc.setStroke(Color.FORESTGREEN.brighter());
                  gc.setLineWidth(5);
                  gc.moveTo(x1, y1);
                  gc.lineTo(x2,y2);
                  gc.stroke();
            }   
    }
 
}