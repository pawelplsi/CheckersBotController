package com.TheJa.charleezardControlPanel;

//import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JFrame;
import javax.swing.JTextField;

import lejos.nxt.Motor;
import lejos.nxt.NXT;
import lejos.nxt.TouchSensor;
import lejos.nxt.remote.NXTCommand;
import lejos.nxt.remote.RemoteMotor;
import lejos.nxt.remote.RemoteSensorPort;
import lejos.nxt.remote.RemoteMotorPort;
import lejos.pc.comm.NXTComm;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommUSB;
import lejos.pc.comm.NXTCommandConnector;
import lejos.pc.comm.NXTConnectionManager;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class RobotController 
{
	private static RemoteMotor yMotor1;
	private static RemoteMotor yMotor2;
	private static RemoteMotor xMotor;
	private static RemoteMotor heightMotor;
	private static RemoteMotor grabberMotor;
	private static RemoteMotor rotatorMotor;
	private static TouchSensor ySensor1fwd;
	private static TouchSensor ySensor2fwd;
	private static TouchSensor ySensor1back;
	private static TouchSensor ySensor2back;
	private static TouchSensor xSensorRight;
	private static TouchSensor xSensorLeft;
	private static TouchSensor nextSensor;
	private static NXTCommand CHAR;
	private static NXTCommand LEE;
	private static ReentrantLock robotlock = new ReentrantLock();
	
	public void emergencyStop()
	{
		yMotor1.stop();
		yMotor1.flt();
		yMotor2.stop();
		yMotor2.flt();
		xMotor.stop();
		xMotor.flt();
		heightMotor.stop();
		heightMotor.flt();
		grabberMotor.stop();
		grabberMotor.flt();
		rotatorMotor.stop();
		rotatorMotor.flt();
	}   
	public static void correctGrabberHeight(int angle)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			heightMotor.rotate(angle);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}
	}
	public static void correctGrabberOpen(int angle)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			grabberMotor.rotate(angle);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}
	}
	public static void correctRotator(int angle)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			rotatorMotor.rotate(angle);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}
	}
	public static void makeGrabberOpen(boolean open)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			if(open&&!Configuration.getBoolean("robot.state.grabberOpen"))
			{
				grabberMotor.rotate(Configuration.getInt("robot.state.grabberOpenAngle"));
			}
			else if(!open&&Configuration.getBoolean("robot.state.grabberOpen"))
			{
				grabberMotor.rotate(-Configuration.getInt("robot.state.grabberOpenAngle"));
			}
			Configuration.set("robot.state.grabberOpen",open);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}
	}
	public static void gotoWaitingPosition()
	{
		moveToY(1);
		moveToX(Configuration.getDouble("robot.state.waitingposX"));
	}
	public static void makeGrabberUp(boolean up)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			if(up&&!Configuration.getBoolean("robot.state.grabberUp"))
			{
				heightMotor.rotate(Configuration.getInt("robot.state.grabberUpAngle"));
			}
			else if(!up&&Configuration.getBoolean("robot.state.grabberUp"))
			{
				heightMotor.rotate(-Configuration.getInt("robot.state.grabberUpAngle"));
			}
			Configuration.set("robot.state.grabberUp", up);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}
	}
	public static void makeRotated(boolean rotated)
	{
//		if(!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			if(rotated&&!Configuration.getBoolean("robot.state.rotated"))
			{
				rotatorMotor.rotate(Configuration.getInt("robot.state.rotateAngle"));
			}
			else if(!rotated&&Configuration.getBoolean("robot.state.rotated"))
			{
				rotatorMotor.rotate(-Configuration.getInt("robot.state.rotateAngle"));
			}
			Configuration.set("robot.state.rotated", rotated);
//		}
//		finally
//		{
//			robotlock.unlock();
//		}	
	}
	public static void connectChar()
	{
		System.out.println("TRY");
		NXTConnector charConnector = new NXTConnector();
		charConnector.connectTo("CHAR");
		System.out.println(charConnector.getNXTComm());
		CHAR = new NXTCommand(charConnector.getNXTComm());
		yMotor1 = new RemoteMotor(CHAR, 0);
		yMotor2 = new RemoteMotor(CHAR, 1);
		xMotor = new RemoteMotor(CHAR, 2);
//		CHAR.i
		yMotor1.setSpeed(250);
		yMotor2.setSpeed(250);
		xMotor.setSpeed(250);
		ySensor1fwd = new TouchSensor(new RemoteSensorPort(CHAR, 0));
		ySensor2fwd = new TouchSensor(new RemoteSensorPort(CHAR, 1));
		ySensor1back = new TouchSensor(new RemoteSensorPort(CHAR, 2));
		ySensor2back = new TouchSensor(new RemoteSensorPort(CHAR, 3));
//		charConnector.
//		System.out.println("SUCCES");
	}
	public static void connectLee()
	{
		NXTConnector leeConnector = new NXTConnector();
//		leeConnector.getNXTComm().
		leeConnector.connectTo("LI");
		LEE = new NXTCommand(leeConnector.getNXTComm());
		heightMotor = new RemoteMotor(LEE, 0);
		grabberMotor = new RemoteMotor(LEE, 1);
		rotatorMotor = new RemoteMotor(LEE, 2);
		heightMotor.setSpeed((int) heightMotor.getMaxSpeed());
		grabberMotor.setSpeed(250);
		rotatorMotor.setSpeed(250);
		xSensorLeft = new TouchSensor(new RemoteSensorPort(LEE, 0));
		xSensorRight = new TouchSensor(new RemoteSensorPort(LEE, 1));
		nextSensor = new TouchSensor(new RemoteSensorPort(LEE, 2));
	}
	public static boolean isCharConnected()
	{
		if (CHAR==null)
		{
			return false;
		}
		return CHAR.isOpen();
	}
	public static boolean isLeeConnected()
	{
		if (LEE==null)
		{
			return false;
		}
		return LEE.isOpen();
	}
	public static void moveToY(int x)
	{
		if(!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			yMotor1.rotate(Configuration.getInt("robot.state.y1Diff")*(x-Configuration.getInt("robot.state.currentY"))/7,true);
			yMotor2.rotate(Configuration.getInt("robot.state.y2Diff")*(x-Configuration.getInt("robot.state.currentY"))/7);
			Configuration.set("robot.state.currentY",x);
		}
		finally
		{
			robotlock.unlock();
		}
	}
	public static void calibrateX()
	{
		boolean reenter = robotlock.isHeldByCurrentThread();
		if(!reenter&&!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{

			xMotor.forward();
			while(!xSensorLeft.isPressed());
			System.out.println("11");
			xMotor.stop();
			xMotor.resetTachoCount();
			xMotor.backward();
			while(!xSensorRight.isPressed());
			System.out.println("22");
			xMotor.stop();
			Configuration.set("robot.state.xDiff",-xMotor.getTachoCount());
			Configuration.set("robot.state.currentX",1);
		}
		finally
		{
//			if(!reenter)
			{
				robotlock.unlock();
			}
		}
		
	}
	public static void moveTo(int x, int y)
	{
//		boolean reenter = robotlock.isHeldByCurrentThread();
//		if(!reenter&&!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			if(Configuration.getInt("robot.state.currentY")<4)
			{
//			System.out.println();
				moveToY(4);
			}
			moveToX(x);
			moveToY(y);
//		}
//		finally
//		{
////			if(!reenter)
//			{
//				robotlock.unlock();
//			}
//		}
	}
	public static void moveToX(double x)
	{
		boolean reenter = robotlock.isHeldByCurrentThread();
		if(!reenter&&!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			xMotor.rotate((int) ((x-Configuration.getDouble("robot.state.currentX"))*Configuration.getInt("robot.state.xDiff")/7));
			Configuration.set("robot.state.currentX",x);
		}
		finally
		{
//			if(!reenter)
			{
				robotlock.unlock();
			}
		}
		
	}
	public static void rotatePawn()
	{	
		if(!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			makeRotated(false);
			makeGrabberUp(true);
			makeGrabberOpen(true);
			
			makeGrabberUp(false);
			makeGrabberOpen(false);
			
			makeGrabberUp(true);
			makeRotated(true);
			makeGrabberUp(false);
			makeGrabberOpen(true);
			
			makeGrabberUp(true);
			makeGrabberOpen(false);
		}
		finally
		{
			robotlock.unlock();
		}
	}
	public static void moveX(int angle)
	{
		boolean reenter = robotlock.isHeldByCurrentThread();
		if(!reenter&&!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			xMotor.rotate(angle);
		}
		finally
		{
//			if(!reenter)
			{
				robotlock.unlock();
			}
		}
	}
	public static void moveY1(int angle,boolean returnNow)
	{

		boolean reenter = robotlock.isHeldByCurrentThread();
		if(!reenter&&!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			yMotor1.rotate(angle,returnNow);
		}
		finally
		{
//			if(!reenter)
			{
				robotlock.unlock();
			}
		}
		
	}
	public static void moveY2(int angle,boolean returnNow)
	{
		boolean reenter = robotlock.isHeldByCurrentThread();
		if(!reenter&&!robotlock.tryLock())
		{
			throw new IllegalStateException("Robot is busy.");
		}
		try
		{
			yMotor2.rotate(angle,returnNow);
		}
		finally
		{
//			if(!reenter)
			{
				robotlock.unlock();
			}
		}
		
	}
	public static void movePawn(int x1, int y1, int x2, int y2)
	{
		makeGrabberUp(true);
		makeRotated(false);
		makeGrabberOpen(true);
		moveTo(x1, y1);
		makeGrabberUp(false);
		makeGrabberOpen(false);
		makeGrabberUp(true);
		moveTo(x2, y2);
		makeGrabberUp(false);
		makeGrabberOpen(true);
		
		makeGrabberUp(true);
		makeGrabberOpen(false);
	}
	public static void removePawn(int x, int y)
	{
		movePawn(x, y, 1, 8);
	}
	public static void calibrate()
	{
		calibrateX();
		calibrateY();
	}
	public static void calibrateY()
	{
		boolean y1ready = false;
		boolean y2ready = false;
		yMotor1.forward();
		yMotor2.forward();
		
		while(true)
		{
			if(ySensor1fwd.isPressed())
			{
				y1ready=true;
				yMotor1.stop();
			}
			if(ySensor2fwd.isPressed())
			{
				y2ready=true;
				yMotor2.stop();
			}
			if(y2ready&&y1ready)
			{
				break;
			}
		}
		yMotor1.resetTachoCount();
		yMotor2.resetTachoCount();
		yMotor1.backward();
		yMotor2.backward();
		y1ready = false;
		y2ready = false;
		while(true)
		{
			if(ySensor1back.isPressed())
			{
				y1ready=true;
				yMotor1.stop();
			}
			if(ySensor2back.isPressed())
			{
				y2ready=true;
				yMotor2.stop();
			}
			if(y2ready&&y1ready)
			{
				break;
			}
		}
		Configuration.set("robot.state.currentY",1);
		Configuration.set("robot.state.y1Diff", -yMotor1.getTachoCount());
		Configuration.set("robot.state.y2Diff", -yMotor2.getTachoCount());
	}
	public static boolean isBusy() {
		return robotlock.isLocked();
	}
	public static int getTachoX()
	{
		return xMotor.getTachoCount();
	}
	public static void resetTachoX()
	{
		xMotor.resetTachoCount();
	}
	public static int getTachoY1()
	{
		return yMotor1.getTachoCount();
	}
	public static void resetTachoY1()
	{
		yMotor1.resetTachoCount();
	}
	public static int getTachoY2()
	{
		return yMotor2.getTachoCount();
	}
	public static void resetTachoY2()
	{
		yMotor2.resetTachoCount();
	}
	public static boolean readSensorXR()
	{
		return xSensorRight.isPressed();
	}
	public static boolean readSensorXL()
	{
		return xSensorLeft.isPressed();
	}
	public static boolean readSensorY1B()
	{
		return ySensor1back.isPressed();
	}
	public static boolean readSensorY1T()
	{
		return ySensor1fwd.isPressed();
	}
	public static boolean readSensorY2B()
	{
		return ySensor2back.isPressed();
	}
	public static boolean readSensorY2T()
	{
		return ySensor2fwd.isPressed();
	}
	public static void calibrateXLeft()
	{
//		boolean reenter = robotlock.isHeldByCurrentThread();
//		if(!reenter&&!robotlock.tryLock())
//		{
//			throw new IllegalStateException("Robot is busy.");
//		}
//		try
//		{
			xMotor.forward();
			while(!xSensorLeft.isPressed());
			System.out.println("11");
			xMotor.stop();
			Configuration.set("robot.state.currentX",8);
//		}
//		finally
//		{
////			if(!reenter)
//			{
//				robotlock.unlock();
//			}
//		}
	}
}
