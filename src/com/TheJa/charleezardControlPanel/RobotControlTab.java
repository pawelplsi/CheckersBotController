package com.TheJa.charleezardControlPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;

import javax.swing.JFrame;

import org.jfree.chart.block.ColumnArrangement;


public class RobotControlTab extends GuiTab implements ActionListener{

	private int focusedRow;
	private int focusedColumn;
	private TextField calibrationLabel = new TextField(null, false, "", "CALIBRATION");

	
	private Button calibrateButton = new Button(f, this, "AUTO X + Y");
	private Button calibrateXButton = new Button(f, this, "AUTO X");
	private Button calibrateYButton = new Button(f, this, "AUTO Y");
	private Button calibrateGrabberUp = new Button(f, this, "GRABBER UP");
	private Button calibrateGrabberDown = new Button(f, this, "GRABBER DOWN");
	private ConfigBoundTextField grabberCalibrationHeightAngle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.grabberHeightAngle");
	private Button calibrateGrabberOpen = new Button(f, this, "GRABBER OPEN");
	private Button calibrateGrabberClose = new Button(f, this, "GRABBER CLOSE");
	private ConfigBoundTextField grabberCalibrationOpenAngle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.grabberOpenAngle");
	private Button gotoBtn = new Button(f, this, "GOTO");
	private ConfigBoundTextField x1field = new ConfigBoundTextField(f, false, "x1 =", "robot.control.x1");
	private ConfigBoundTextField y1field = new ConfigBoundTextField(f, false, "y1 =", "robot.control.y1");
	private ConfigBoundTextField x2field = new ConfigBoundTextField(f, false, "x2 =", "robot.control.x2");
	private ConfigBoundTextField y2field = new ConfigBoundTextField(f, false, "y2 =", "robot.control.y2");
	private Button openGrabberBtn = new Button(f,this,"OPEN GRABBER");
	private Button closeGrabberBtn = new Button(f,this,"CLOSE GRABBER");
	private Button upGrabberBtn = new Button(f,this,"GRABBER UP");
	private Button downGrabberBtn = new Button(f,this,"GRABBER DOWN");
	private Button rotatePawnBtn = new Button(f,this,"ROTATE PAWN");
	private Button makeRotated = new Button(f,this,"ROTATE");
	private Button makeUnrotated = new Button(f,this,"NOT ROTATE");

	private Button movePawnBtn = new Button(f, this, "MOVE");
	
	
	private Button calibrateRotatorFwd = new Button(f, this, "ROTATOR FWD");
	private Button calibrateRotatorBw = new Button(f, this, "ROTATOR BW");
	private ConfigBoundTextField rotatorCalibrationAngle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.rotatorAngle");
	private Button calibrateY1Fwd = new Button(f, this, "1Y FORWARD");
	private Button calibrateY1Bw = new Button(f, this, "1Y BACKWARD");
	private ConfigBoundTextField calibrateY1Angle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.Y1Angle");

	private Button calibrateY2Fwd = new Button(f, this, "2Y FORWARD");
	private Button calibrateY2Bw = new Button(f, this, "2Y BACKWARD");
	private ConfigBoundTextField calibrateY2Angle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.Y2Angle");

	private Button calibrateXFwd = new Button(f, this, "X FORWARD");
	private Button calibrateXBw = new Button(f, this, "X BACKWARD");
	private ConfigBoundTextField calibrateXAngle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.XAngle");
	
	private Button calibrateYFwd = new Button(f, this, "Y FORWARD");
	private Button calibrateYBw = new Button(f, this, "Y BACKWARD");

	private TextField directControlLabel = new TextField(null, false, "", "DIRECT CONTROL");
	private TextField tachometersLabel = new TextField(null, false, "", "TACHOMETERS");

	private Button connectCharBtn = new Button(f,this,"CONNECT CHAR");
	private Button connectLeeBtn = new Button(f,this,"CONNECT LEE");

	private ConfigBoundTextField statusGrabberOpen = new ConfigBoundTextField(f, false, "GRABBER OPEN=", "robot.state.grabberOpen");
	private ConfigBoundTextField statusGrabberUp = new ConfigBoundTextField(f, false, "GRABBER UP=", "robot.state.grabberUp");
	private ConfigBoundTextField statusRotated = new ConfigBoundTextField(f, false, "ROTATED=", "robot.state.rotated");
	private ConfigBoundTextField statusXdiff = new ConfigBoundTextField(f, false, "Xdiff=", "robot.state.xDiff");
	private ConfigBoundTextField statusY1diff = new ConfigBoundTextField(f, false, "Y1diff=", "robot.state.y1Diff");
	private ConfigBoundTextField statusY2diff = new ConfigBoundTextField(f, false, "Y2diff=", "robot.state.y2Diff");
	private ConfigBoundTextField statusX = new ConfigBoundTextField(f, false, "X=", "robot.state.currentX");
	private ConfigBoundTextField statusY = new ConfigBoundTextField(f, false, "Y=", "robot.state.currentY");
	private Button busyStateBtn = new Button(f, null, "ROBOT IDLE");
	
	
	private TextField stateLabel = new TextField(null, false, "", "ROBOT STATE");
	private Button resetTachoX = new Button(f, this, "RESET X");
	private Button resetTachoY1 = new Button(f, this, "RESET Y1");
	private Button resetTachoY2= new Button(f, this, "RESET Y2");
	private Button tachoX = new Button(f,this, "N/A");
	private Button tachoY1 = new Button(f,this, "N/A");
	private Button tachoY2 = new Button(f,this, "N/A");

	private Button sensorXR = new Button(f,this,"N/A");
	private Button sensorXL = new Button(f,this,"N/A");
	private Button sensorY1T = new Button(f,this,"N/A");
	private Button sensorY1B = new Button(f,this,"N/A");
	private Button sensorY2T = new Button(f,this,"N/A");
	private Button sensorY2B = new Button(f,this,"N/A");
//	private ConfigBoundTextField calibrateXAngle = new ConfigBoundTextField(f, false, "ANGLE=", "robot.calibration.XAngle");
	
	private GuiComponent[][] focusableComps = 
		{
			{calibrateXButton,calibrateYButton,calibrateButton,x1field,y1field,gotoBtn,connectCharBtn},
			{calibrateGrabberUp,calibrateGrabberDown,grabberCalibrationHeightAngle,x2field,y2field,rotatePawnBtn,connectLeeBtn},
			{calibrateGrabberOpen,calibrateGrabberClose,grabberCalibrationOpenAngle, openGrabberBtn,closeGrabberBtn,movePawnBtn,statusGrabberOpen},
			{calibrateY1Fwd,calibrateY1Bw,calibrateY1Angle,upGrabberBtn,downGrabberBtn,null,statusGrabberUp},
			{calibrateY2Fwd,calibrateY2Bw,calibrateY2Angle,makeRotated,makeUnrotated,null,statusXdiff,statusY1diff,statusY2diff},
			{calibrateXFwd,calibrateXBw,calibrateXAngle,null,null,null,statusX,statusY,busyStateBtn},
			{calibrateRotatorFwd,calibrateRotatorBw,rotatorCalibrationAngle,null,null,null,resetTachoX,resetTachoY1,resetTachoY2},
			{calibrateYFwd,calibrateYBw}
		};
	public RobotControlTab(ControlPanelGUI f) 
	{	
		super(f);
		calibrationLabel.setLeftBounded(false);
		stateLabel.setLeftBounded(false);
		directControlLabel.setLeftBounded(false);
		tachometersLabel.setLeftBounded(false);
		calibrateXButton.setFocused(true);
	}
	public void updateState()
	{
		connectCharBtn.setText(RobotController.isCharConnected()?"RECONNECT CHAR (CONNECTED)": "CONNECT CHAR (DISCONNECTED)");
		connectLeeBtn.setText(RobotController.isLeeConnected()?"RECONNECT LEE (CONNECTED)": "CONNECT LEE (DISCONNECTED)");
		busyStateBtn.setText(RobotController.isBusy()?"ROBOT BUSY":"ROBOT IDLE");
//		try
//		{
//			tachoX.setText(Integer.toString(RobotController.getTachoX()));
//		}
//		catch (NullPointerException e)
//		{
//			tachoX.setText("N/A");
//		}
//		try
//		{
//			tachoY1.setText(Integer.toString(RobotController.getTachoY1()));
//		}
//		catch (NullPointerException e)
//		{
//			tachoY1.setText("N/A");
//		}
//		try
//		{
//			tachoY2.setText(Integer.toString(RobotController.getTachoY2()));
//		}
//		catch (NullPointerException e)
//		{
//			tachoY2.setText("N/A");
//		}
//		try
//		{
//			sensorXL.setText("XL "+RobotController.readSensorXL());
//		}
//		catch(NullPointerException e)
//		{
//			sensorXL.setText("XL: N/A");
//		}
//		try
//		{
//			sensorXR.setText("XR "+RobotController.readSensorXR());
//		}
//		catch(NullPointerException e)
//		{
//			sensorXR.setText("XR: N/A");
//		}
//		try
//		{
//			sensorY1B.setText("Y1B "+RobotController.readSensorY1B());
//		}
//		catch(NullPointerException e)
//		{
//			sensorY1B.setText("Y1B: N/A");
//		}
//		try
//		{
//			sensorY2B.setText("Y2B "+RobotController.readSensorY2B());
//		}
//		catch(NullPointerException e)
//		{
//			sensorY2B.setText("Y2B: N/A");
//		}
//		try
//		{
//			sensorY1T.setText("Y1T "+RobotController.readSensorY1T());
//		}
//		catch(NullPointerException e)
//		{
//			sensorY1T.setText("Y1T: N/A");
//		}
//		try
//		{
//			sensorY2T.setText("Y2T "+RobotController.readSensorY2T());
//		}
//		catch(NullPointerException e)
//		{
//			sensorY2T.setText("Y2T: N/A");
//		}
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		g.drawRect(xo, yo, width, height);
		updateState();
		calibrationLabel.paint(g, xo+10, yo+10, 320, 20);
		
		calibrateXButton.paint(g, xo+10, yo+40, 100, 20);
		calibrateYButton.paint(g, xo+120, yo+40, 100, 20);
		calibrateButton.paint(g, xo+230, yo+40, 100, 20);

		calibrateGrabberUp.paint(g, xo+10, yo+70, 100, 20);
		calibrateGrabberDown.paint(g, xo+120, yo+70, 100, 20);
		grabberCalibrationHeightAngle.paint(g, xo+230, yo+70, 100, 20);
		
		calibrateGrabberOpen.paint(g, xo+10, yo+100, 100, 20);
		calibrateGrabberClose.paint(g, xo+120, yo+100, 100, 20);
		grabberCalibrationOpenAngle.paint(g, xo+230, yo+100, 100, 20);

		calibrateY1Fwd.paint(g, xo+10, yo+130, 100, 20);
		calibrateY1Bw.paint(g, xo+120, yo+130, 100, 20);
		calibrateY1Angle.paint(g, xo+230, yo+130, 100, 20);

		calibrateY2Fwd.paint(g, xo+10, yo+160, 100, 20);
		calibrateY2Bw.paint(g, xo+120, yo+160, 100, 20);
		calibrateY2Angle.paint(g, xo+230, yo+160, 100, 20);

		calibrateXFwd.paint(g, xo+10, yo+190, 100, 20);
		calibrateXBw.paint(g, xo+120, yo+190, 100, 20);
		calibrateXAngle.paint(g, xo+230, yo+190, 100, 20);

		calibrateRotatorFwd.paint(g, xo+10, yo+220, 100, 20);
		calibrateRotatorBw.paint(g, xo+120, yo+220, 100, 20);
		rotatorCalibrationAngle.paint(g, xo+230, yo+220, 100, 20);
		
		calibrateYFwd.paint(g, xo+10, yo+250, 100, 20);
		calibrateYBw.paint(g, xo+120, yo+250, 100, 20);
		
		directControlLabel.paint(g, xo+360, yo+10, 320, 20);
		x1field.paint(g, xo+360, yo+40, 100, 20);
		y1field.paint(g, xo+470, yo+40, 100, 20);
		gotoBtn.paint(g, xo+580, yo+40, 100, 20);
		
		openGrabberBtn.paint(g, xo+360, yo+100, 100, 20);
		closeGrabberBtn.paint(g, xo+470, yo+100, 100, 20);
		
		rotatePawnBtn.paint(g, xo+580, yo+70, 100, 20);
		
		
		upGrabberBtn.paint(g, xo+360, yo+130, 100, 20);
		downGrabberBtn.paint(g, xo+470, yo+130, 100, 20);

		makeRotated.paint(g, xo+360, yo+160, 100, 20);
		makeUnrotated.paint(g, xo+470, yo+160, 100, 20);
		
		stateLabel.paint(g, xo+700, yo+10, 320, 20);
		connectCharBtn.paint(g, xo+700, yo+40, 320, 20);
		connectLeeBtn.paint(g, xo+700, yo+70, 320, 20);
		statusGrabberOpen.paint(g, xo+700, yo+100, 320, 20);
		statusGrabberUp.paint(g, xo+700, yo+130, 320, 20);
		statusRotated.paint(g, xo+700, yo+160, 320, 20);

		statusXdiff.paint(g, xo+700, yo+190, 100, 20);
		statusY1diff.paint(g, xo+810, yo+190, 100, 20);
		statusY2diff.paint(g, xo+920, yo+190, 100, 20);
		statusX.paint(g, xo+700, yo+220, 100, 20);
		statusY.paint(g, xo+810, yo+220, 100, 20);
		busyStateBtn.paint(g,xo+920, yo+220, 100, 20);

		tachometersLabel.paint(g, xo+700, yo+250, 320, 20);

		resetTachoX.paint(g, xo+700, yo+280, 100, 20);
		resetTachoY1.paint(g, xo+810, yo+280, 100, 20);
		resetTachoY2.paint(g, xo+920, yo+280, 100, 20);
		tachoX.paint(g, xo+700, yo+310, 100, 20);
		tachoY1.paint(g, xo+810, yo+310, 100, 20);
		tachoY2.paint(g, xo+920, yo+310, 100, 20);

		sensorXL.paint(g, xo+700, yo+340, 100, 20);
		sensorXR.paint(g, xo+810, yo+340, 100, 20);
		sensorY1T.paint(g, xo+700, yo+370, 100, 20);
		sensorY2T.paint(g, xo+810, yo+370, 100, 20);
		sensorY1B.paint(g, xo+700, yo+400, 100, 20);
		sensorY2B.paint(g, xo+810, yo+400, 100, 20);

		x2field.paint(g, xo+360, yo+70, 100, 20);
		y2field.paint(g, xo+470, yo+70, 100, 20);
		movePawnBtn.paint(g, xo+580, yo+100, 100, 20);
	}
	private boolean compAvaible(int focusedRow, int focusedColumn)
	{
		try
		{
			if(focusableComps[focusedRow][focusedColumn]==null)
			{
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		if(e.isControlDown())
		{
			focusableComps[focusedRow][focusedColumn].keyPress(e);
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(compAvaible(focusedRow, focusedColumn+1))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedColumn++;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(compAvaible(focusedRow, focusedColumn-1))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedColumn--;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			if(compAvaible(focusedRow+1, focusedColumn))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedRow++;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			if(compAvaible(focusedRow-1, focusedColumn))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedRow--;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else
		{
			focusableComps[focusedRow][focusedColumn].keyPress(e);
		}
	}
	@Override
	public String getName() 
	{
		return "ROBOT CONTROL";
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		new Thread()
		{
			public void run()
			{
				Object o = e.getSource();
				try
				{
					if(o==calibrateButton)
					{
						RobotController.calibrate();
					}
					else if(o==calibrateXButton)
					{
						RobotController.calibrateXLeft();
					}
					else if(o==calibrateYButton)
					{
						RobotController.calibrateY();
					}
					else if(o==calibrateXFwd)
					{
						RobotController.moveX(Configuration.getInt("robot.calibration.XAngle"));
					}
					else if(o==calibrateXBw)
					{
						RobotController.moveX(-Configuration.getInt("robot.calibration.XAngle"));
					}
					else if(o==calibrateY1Fwd)
					{
						RobotController.moveY1(Configuration.getInt("robot.calibration.Y1Angle"),false);
					}
					else if(o==calibrateY1Bw)
					{
						RobotController.moveY1(-Configuration.getInt("robot.calibration.Y1Angle"),false);
					}
					else if(o==calibrateY2Fwd)
					{
						RobotController.moveY2(Configuration.getInt("robot.calibration.Y2Angle"),false);
					}
					else if(o==calibrateY2Bw)
					{
						RobotController.moveY2(-Configuration.getInt("robot.calibration.Y2Angle"),false);
					}
					else if(o==calibrateYFwd)
					{
						RobotController.moveY1(Configuration.getInt("robot.calibration.Y1Angle"),true);
						RobotController.moveY2(Configuration.getInt("robot.calibration.Y2Angle"),false);
					}
					else if(o==calibrateYBw)
					{
						RobotController.moveY1(-Configuration.getInt("robot.calibration.Y1Angle"),true);
						RobotController.moveY2(-Configuration.getInt("robot.calibration.Y2Angle"),false);
					}
					else if(o==calibrateGrabberDown)
					{
						RobotController.correctGrabberHeight(-Configuration.getInt("robot.calibration.grabberHeightAngle"));
					}
					else if(o==calibrateGrabberUp)
					{
						RobotController.correctGrabberHeight(Configuration.getInt("robot.calibration.grabberHeightAngle"));
					}
					else if(o==calibrateGrabberOpen)
					{
						RobotController.correctGrabberOpen(Configuration.getInt("robot.calibration.grabberOpenAngle"));
					}
					else if(o==calibrateGrabberClose)
					{
						RobotController.correctGrabberOpen(-Configuration.getInt("robot.calibration.grabberOpenAngle"));
					}
					else if(o==upGrabberBtn)
					{
						RobotController.makeGrabberUp(true);
					}
					else if(o==downGrabberBtn)
					{
						RobotController.makeGrabberUp(false);
					}
					else if(o==openGrabberBtn)
					{
						RobotController.makeGrabberOpen(true);
					}
					else if(o==closeGrabberBtn)
					{
						RobotController.makeGrabberOpen(false);
					}
					else if(o==gotoBtn)
					{
						RobotController.moveTo(Configuration.getInt("robot.control.x1"), Configuration.getInt("robot.control.y1"));
					}
					else if(o==connectCharBtn)
					{
						RobotController.connectChar();
					}
					else if(o==connectLeeBtn)
					{
						RobotController.connectLee();
					}
					else if(o==calibrateRotatorFwd)
					{
						RobotController.correctRotator(Configuration.getInt("robot.calibration.rotatorAngle"));
					}
					else if(o==calibrateRotatorBw)
					{
						RobotController.correctRotator(-Configuration.getInt("robot.calibration.rotatorAngle"));
					}
					else if(o==rotatePawnBtn)
					{
						RobotController.rotatePawn();
					}
					else if(o==makeRotated)
					{
						RobotController.makeRotated(true);
					}
					else if(o==makeUnrotated)
					{
						RobotController.makeRotated(false);
					}
					else if(o==resetTachoX)
					{
						RobotController.resetTachoX();
					}
					else if(o==resetTachoY1)
					{
						RobotController.resetTachoY1();
					}
					else if(o==resetTachoY2)
					{
						RobotController.resetTachoY2();
					}
					else if(o==movePawnBtn)
					{
						RobotController.movePawn(Configuration.getInt("robot.control.x1"), Configuration.getInt("robot.control.y1"), Configuration.getInt("robot.control.x2"), Configuration.getInt("robot.control.y2"));
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					f.viewSimpleDialog("ERROR");
				}
			}
		}.start();
	}
}
