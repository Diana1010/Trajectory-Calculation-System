package application.image;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

public class VideoCap {
public static void main (String args[]){
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

VideoCapture cap = new VideoCapture();

    String input = "C:/Users/User-KP1/Downloads/time.mp4";
    String output = "C:/Users/User-KP1/Downloads/image_video";

cap.open(input);

    int video_length = (int) cap.get(Videoio.CAP_PROP_FRAME_COUNT);
    int frames_per_second = (int) cap.get(Videoio.CAP_PROP_FPS);
    int frame_number = (int) cap.get(Videoio.CAP_PROP_POS_FRAMES);

Mat frame = new Mat();

if (cap.isOpened())
{
        System.out.println("Video is opened");
        System.out.println("Number of Frames: " + video_length);
        System.out.println(frames_per_second + " Frames per Second");
        System.out.println("Converting Video...");

    cap.read(frame);

    while(frame_number <= 10)
    {
    	cap.read(frame);
    	//if (frame_number % 25 ==0) {
        Imgcodecs.imwrite(output + "/" + frame_number +".jpg", frame);
    	//}
        frame_number++;
        cap.release();
    }
    cap.release();

        System.out.println(video_length + " Frames extracted");

}

    else
    {
        System.out.println("Fail");
    }
} }