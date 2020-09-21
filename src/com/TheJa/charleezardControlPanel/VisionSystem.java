package com.TheJa.charleezardControlPanel;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.Algorithm;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.CLAHE;
import org.opencv.imgproc.Imgproc;

public class VisionSystem
{
	

	private static Mat imgoriginale = Highgui.imread("/home/Obrazy/sn2.jpg");
	private static VideoCapture camera = new VideoCapture();
	private static Mat imgOrg = new Mat(100,100,CvType.CV_8UC3);
	private static Mat imgMasks = new Mat();
	private static Mat imgMasksSafe = new Mat();
	private static Mat imgMasked = new Mat();
	private static Mat imgMaskedSafe = new Mat();
	private static Mat[] pawnMasks = new Mat[4];
	private static MatOfPoint2f chessBoardDetectedCorners = new MatOfPoint2f();
	private static Mat imgTransformed = new Mat();
	private static Mat imgTransformedSafe = new Mat();
	private static Scalar[] pawnColors = new Scalar[]{new Scalar(0,0,255),new Scalar(255,0,0),new Scalar(255,255,0),new Scalar(0,255,255)};
	private static Size chessboardSize = new Size(7,7);
	private static Scalar zeroScalar = new Scalar(0);
	private static float cbfs = 32;
	private static Mat chessBoardTransformation;
	private static MatOfPoint2f chessBoardTransformationCornersScene = new MatOfPoint2f();
	private static MatOfPoint2f chessBoardTransformationCroppedCorners = new MatOfPoint2f();
	private static boolean refreshThreadRunning = false;
	private static boolean threadStarted = false;
	private static Board board = new Board();
	private static Board boardSafe = new Board();
	private static Pawn[]pawns = {Pawn.WHITE,Pawn.BLACK,Pawn.WHITEQUEEN,Pawn.BLACKQUEEN};
	private static Thread refreshThread = new Thread()
	{
		public void run()
		{
			while(!isInterrupted())
			{
				while(refreshThreadRunning)
				{
					try
					{
						readBoard();
					}
					catch(Exception e)
					{
//						e.printStack4Trace();
					}
				}
			}
		}
	};
	static
	{
		pawnMasks[0]=new Mat();
		pawnMasks[1]=new Mat();
		pawnMasks[2]=new Mat();
		pawnMasks[3]=new Mat();
		chessBoardTransformationCroppedCorners.fromArray(new Point(1*cbfs, 1*cbfs), new Point(7*cbfs, 1*cbfs), new Point(7*cbfs, 7*cbfs), new Point(1*cbfs, 7*cbfs));
	}
	public static void terminate()
	{
		stopRefreshThread();
		closeCamera();
		refreshThread.interrupt();
	}
	public static Mat getMasksImg()
	{
		return imgMasksSafe;
	}
	public static Mat getMaskedImg()
	{
		return imgMaskedSafe;
	}
	public static boolean isRefreshThreadStarted()
	{
		return refreshThreadRunning;
	}
	public static void startRefreshThread()
	{
		if(!refreshThreadRunning)
		{
			refreshThreadRunning=true;
			if(!threadStarted)
			{
				refreshThread.start();
				threadStarted=true;
			}
		}
	}
	public static void stopRefreshThread()
	{
		refreshThreadRunning = false;
	}
	public static Mat getTransformedImg()
	{
		return imgTransformedSafe;
	}
	public static void openCamera(int id)
	{
		if(!camera.open(id))
		{
			throw new IllegalThreadStateException("Camera not avaible.");
		}
	}
	public static void closeCamera()
	{
		camera.release();
//		camera=null;
	}
	public static Board getChessBoard()
	{
		return boardSafe;
	}
	public static boolean isCameraOpen()
	{
//		if(camera==null)
//		{
//			return false;
//		}
		return camera.isOpened();
	}
	public static void readFrame(Mat m)
	{
		if(!camera.read(m))
		{
			throw new IllegalStateException("Cant read");
		}
//		imgoriginale.copyTo(m);
		//TODO
//		if(Configuration.getInt("vision.cameraId")==1)
		{
//			    Imgproc.cvtColor(m,m,Imgproc.COLOR_BGR2Lab);
//
//			    // Extract the L channel
//			   	List<Mat> lab_planes = new ArrayList<Mat>();
//			   	Core.split(m, lab_planes);  // now we have the L image in lab_planes[0]
//
//			    // apply the CLAHE algorithm to the L channel
////			   	CLAHE clahe = Imgproc.
////			    clahe->setClipLimit(4);
////			    cv::Mat dst;
////			    clahe->apply(lab_planes[0], dst);
//
//			    // Merge the the color planes back into an Lab image
//			   	Imgproc.equalizeHist(lab_planes.get(0), lab_planes.get(0));
////			    m.copyTo(lab_planes.get(0));
//			    Core.merge(lab_planes, m);
//
//			   // convert back to RGB
//			   Imgproc.cvtColor(m, m, Imgproc.COLOR_Lab2BGR);
//			 Imgproc.cvtColor(m,m,Imgproc.COLOR_BGR2Lab);
//			 List<Mat> planes = new ArrayList<Mat>();
//			 Core.split(m, planes);  
//			 Imgproc.equalizeHist(planes.get(0), planes.get(0));
//			 Imgproc.equalizeHist(planes.get(1), planes.get(1));
//			 Imgproc.equalizeHist(planes.get(2), planes.get(2));
//			 planes.get(0).setTo(new Scalar(Configuration.getInt("vision.cameraId")));
//			 planes.get(2).setTo(new Scalar(255));
//			 Core.merge(planes, m);
//			 Imgproc.cvtColor(m,m,Imgproc.COLOR_Lab2BGR);
			 
		}
	}
	public static synchronized void readBoard()
	{
		readFrame(imgOrg);
//		if(imgOrg)
//		System.out.println(imgOrg);
		imgOrg.copyTo(imgMasks);		
		imgMasks.copyTo(imgMasked);
		Imgproc.cvtColor(imgMasks, imgMasks, Imgproc.COLOR_BGR2HSV);
			Utils.hsvInRange(imgMasks, new Scalar(Configuration.getDouble("vision.pawn.white.minH"),Configuration.getDouble("vision.pawn.white.minS"),Configuration.getDouble("vision.pawn.white.minV")), 
				new Scalar(Configuration.getDouble("vision.pawn.white.maxH"),Configuration.getDouble("vision.pawn.white.maxS"),Configuration.getDouble("vision.pawn.white.maxV")),pawnMasks[0]);
//		if(Configuration.getBoolean("vision.pawn.black.visible"))
			Utils.hsvInRange(imgMasks, new Scalar(Configuration.getDouble("vision.pawn.black.minH"),Configuration.getDouble("vision.pawn.black.minS"),Configuration.getDouble("vision.pawn.black.minV")), 
				new Scalar(Configuration.getDouble("vision.pawn.black.maxH"),Configuration.getDouble("vision.pawn.black.maxS"),Configuration.getDouble("vision.pawn.black.maxV")),pawnMasks[1]);
//		if(Configuration.getBoolean("vision.pawn.whitequeen.visible"))
			Utils.hsvInRange(imgMasks, new Scalar(Configuration.getDouble("vision.pawn.whitequeen.minH"),Configuration.getDouble("vision.pawn.whitequeen.minS"),Configuration.getDouble("vision.pawn.whitequeen.minV")), 
				new Scalar(Configuration.getDouble("vision.pawn.whitequeen.maxH"),Configuration.getDouble("vision.pawn.whitequeen.maxS"),Configuration.getDouble("vision.pawn.whitequeen.maxV")),pawnMasks[2]);
//		if(Configuration.getBoolean("vision.pawn.blackqueen.visible"))
			Utils.hsvInRange(imgMasks, new Scalar(Configuration.getDouble("vision.pawn.blackqueen.minH"),Configuration.getDouble("vision.pawn.blackqueen.minS"),Configuration.getDouble("vision.pawn.blackqueen.minV")), 
				new Scalar(Configuration.getDouble("vision.pawn.blackqueen.maxH"),Configuration.getDouble("vision.pawn.blackqueen.maxS"),Configuration.getDouble("vision.pawn.blackqueen.maxV")),pawnMasks[3]);
		imgMasks.setTo(zeroScalar);
			for(int i = 0; i<4;i++)
		{
			if(!Configuration.getBoolean("vision.pawn.white.visible")&&i==0)
				continue;
			if(!Configuration.getBoolean("vision.pawn.black.visible")&&i==1)
				continue;
			if(!Configuration.getBoolean("vision.pawn.whitequeen.visible")&&i==2)
				continue;
			if(!Configuration.getBoolean("vision.pawn.blackqueen.visible")&&i==3)
			{
				pawnMasks[3].setTo(zeroScalar);
//				continue;
			}
			imgMasks.setTo(pawnColors[i], pawnMasks[i]);
			if(Configuration.getInt("vision.filter.1erode")>0)
				Imgproc.erode(pawnMasks[i], pawnMasks[i], Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(Configuration.getInt("vision.filter.1erode"),Configuration.getInt("vision.filter.1erode"))));
			if(Configuration.getInt("vision.filter.2dilate")>0)
				Imgproc.dilate(pawnMasks[i], pawnMasks[i], Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(Configuration.getInt("vision.filter.2dilate"),Configuration.getInt("vision.filter.2dilate"))));
			if(Configuration.getInt("vision.filter.3erode")>0)
				Imgproc.erode(pawnMasks[i], pawnMasks[i], Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(Configuration.getInt("vision.filter.3erode"),Configuration.getInt("vision.filter.3erode"))));
			if(Configuration.getInt("vision.filter.4dilate")>0)
				Imgproc.dilate(pawnMasks[i], pawnMasks[i], Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(Configuration.getInt("vision.filter.4dilate"),Configuration.getInt("vision.filter.4dilate"))));
			imgMasked.setTo(new Scalar(0,0,0), pawnMasks[i]);
		}
		imgMasks.copyTo(imgMasksSafe);
		imgMasked.copyTo(imgMaskedSafe);
		Calib3d.findChessboardCorners(imgMasked, chessboardSize, chessBoardDetectedCorners);
		if(chessBoardDetectedCorners.rows()==49)
		{
			imgOrg.copyTo(imgTransformed);
			Point[] chessBoardCornerArray = chessBoardDetectedCorners.toArray();
			chessBoardCornerArray[1]=chessBoardCornerArray[6];
			chessBoardCornerArray[3]=chessBoardCornerArray[42];
			chessBoardCornerArray[2]=chessBoardCornerArray[48];
			int maxCornerIndex = 0;
			double maxCornerField = 0;
			for(int i = 0;i<4;i++) 
			{
				if(chessBoardCornerArray[i].x*chessBoardCornerArray[i].y>maxCornerField)
				{
					maxCornerIndex=i;
					maxCornerField=chessBoardCornerArray[i].x*chessBoardCornerArray[i].y;
				}
			}
			maxCornerIndex+=Configuration.getInt("vision.rotateBoard");
			chessBoardTransformationCornersScene.fromArray(chessBoardCornerArray[maxCornerIndex%4], chessBoardCornerArray[(maxCornerIndex+1)%4], chessBoardCornerArray[(maxCornerIndex+2)%4], chessBoardCornerArray[(maxCornerIndex+3)%4]);
			chessBoardTransformation =  Imgproc.getPerspectiveTransform(chessBoardTransformationCornersScene, chessBoardTransformationCroppedCorners);
			Imgproc.warpPerspective(imgTransformed, imgTransformed, chessBoardTransformation, new Size(cbfs*8, cbfs*8));
			board = new Board();
			for(int i =0; i<4; i++)
			{
				Imgproc.warpPerspective(pawnMasks[i], pawnMasks[i], chessBoardTransformation, new Size(cbfs*8, cbfs*8));
				for(int x = 1; x<=8; x++)
					for(int y = 1; y<=8; y++)
					{
						if((x+y)%2==1)
						{
								if(pawnMasks[i].get((int)((y-0.5)*cbfs), (int)((x-0.5)*cbfs))[0]==255)
								{
									board.setPawn(x, 9-y, pawns[i]);
									Core.circle(imgTransformed, new Point((x-0.5)*cbfs, (y-0.5)*cbfs), 5, pawnColors[i],-1);
								}
								else
								{
									Core.circle(imgTransformed, new Point((x-0.5)*cbfs, (y-0.5)*cbfs), 5, pawnColors[i]);
								}
						}
					}
			}
			
		}
		else 
		{
			Core.putText(imgTransformed, "CANNOT DETECT", new Point(0, 128), Core.FONT_ITALIC, 1, new Scalar(0,0,255));
			board=null;
		}
		imgTransformed.copyTo(imgTransformedSafe);
		boardSafe=board;
	}
}
