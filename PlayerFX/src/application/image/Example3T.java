package application.image;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.animation.Timeline;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

// An alternative implementation of Example 3,
//    using the Timeline, KeyFrame, and Duration classes.

// Animation of Earth rotating around the sun. (Hello, world!)
public class Example3T extends Application 
{
	Mat matrix = null;
	
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Timeline Example" );
        
        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );
        
        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );
        
        System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        Image earth = new Image( "application/image/earth.png" );
        Image sun   = new Image( "application/image/sun.png" );
        Image space = new Image( "application/image/space.png" );
        
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( 5 );
        gc.clearRect(0, 0, 512,512);
        
        // background image clears canvas
        gc.drawImage( space, 0, 0 );
        
        final long timeStart = System.currentTimeMillis();
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.5),                // 60 FPS
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    double t = (System.currentTimeMillis() - timeStart) / 200.0; 
                                
                    double x = 232 + 128 * Math.cos(t);
                    double y = 232 + 128 * Math.sin(t);
                    System.out.println(t);
                   // gc.drawImage( earth, x, y );
                   // gc.drawImage( sun, 196, 196 );
                    gc.setStroke(Color.FORESTGREEN.brighter());
                    gc.setLineWidth(5);
                    gc.moveTo(30.5, 30.5);
                    gc.lineTo(150.5 +t , 30.5 );

                    gc.stroke();
                    work();
                }
            });
        
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
        
        theStage.show();
    }
    
    private void work() {
        Timeline gameLoop = new Timeline();
        gameLoop.setCycleCount( 10 );
    
        
        final long timeStart = System.currentTimeMillis();
        
        KeyFrame kf = new KeyFrame(
            Duration.seconds(0.5),                // 60 FPS
            new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent ae)
                {
                    double t = (System.currentTimeMillis() - timeStart) / 200.0; 
                                
                    double x = 232 + 128 * Math.cos(t);
                    double y = 232 + 128 * Math.sin(t);
                    System.out.println("Input "+t);
    
                }
            });
        
        gameLoop.getKeyFrames().add( kf );
        gameLoop.play();
    }
    
    public Image LoadImage() throws Exception {
        // Loading the OpenCV core library
      

        // Reading the Image from the file and storing it in to a Matrix object
        String file ="D:/images/testP.png";
        Mat matrix = Imgcodecs.imread(file);

        // Drawing a line
        Imgproc.line (
           matrix,                    //Matrix obj of the image
           new Point(10, 200),        //p1
           new Point(300, 200),       //p2
           new Scalar(0, 0, 255),     //Scalar object for color
           5                          //Thickness of the line
        );
        // Encoding the image
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", matrix, matOfByte);

        // Storing the encoded Mat in a byte array
        byte[] byteArray = matOfByte.toArray();

        // Displaying the image
        InputStream in = new ByteArrayInputStream(byteArray);
        BufferedImage bufImage = ImageIO.read(in);

        this.matrix = matrix;

        // Creating the Writable Image
        WritableImage writableImage = SwingFXUtils.toFXImage(bufImage, null);
        return SwingFXUtils.toFXImage(bufImage, null);
     }
}