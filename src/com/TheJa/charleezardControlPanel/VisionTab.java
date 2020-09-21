package com.TheJa.charleezardControlPanel;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import org.opencv.core.Mat;

public class VisionTab extends GuiTab implements ActionListener
{
	private int focusedRow;
	private int focusedColumn;
	
	private Mat m = new Mat();

	private ConfigCheckBox whitePawnShow = new ConfigCheckBox(f, "vision.pawn.white.visible");
	private ConfigCheckBox blackPawnShow = new ConfigCheckBox(f, "vision.pawn.black.visible");
	private ConfigCheckBox whiteQueenShow = new ConfigCheckBox(f, "vision.pawn.whitequeen.visible");
	private ConfigCheckBox blackQueenShow = new ConfigCheckBox(f, "vision.pawn.blackqueen.visible");
	
	private ConfigBoundTextField whitePawnMinH = new ConfigBoundTextField(f,true,"H>","vision.pawn.white.minH",1);
	private ConfigBoundTextField whitePawnMinS = new ConfigBoundTextField(f,false,"S>","vision.pawn.white.minS",1);
	private ConfigBoundTextField whitePawnMinV = new ConfigBoundTextField(f,false,"V>","vision.pawn.white.minV",1);
	private ConfigBoundTextField whitePawnMaxH = new ConfigBoundTextField(f,false,"H<","vision.pawn.white.maxH",1);
	private ConfigBoundTextField whitePawnMaxS = new ConfigBoundTextField(f,false,"S<","vision.pawn.white.maxS",1);
	private ConfigBoundTextField whitePawnMaxV = new ConfigBoundTextField(f,false,"V<","vision.pawn.white.maxV",1);
	
	private ConfigBoundTextField blackPawnMinH = new ConfigBoundTextField(f,false,"H>","vision.pawn.black.minH",1);
	private ConfigBoundTextField blackPawnMinS = new ConfigBoundTextField(f,false,"S>","vision.pawn.black.minS",1);
	private ConfigBoundTextField blackPawnMinV = new ConfigBoundTextField(f,false,"V>","vision.pawn.black.minV",1);
	private ConfigBoundTextField blackPawnMaxH = new ConfigBoundTextField(f,false,"H<","vision.pawn.black.maxH",1);
	private ConfigBoundTextField blackPawnMaxS = new ConfigBoundTextField(f,false,"S<","vision.pawn.black.maxS",1);
	private ConfigBoundTextField blackPawnMaxV = new ConfigBoundTextField(f,false,"V<","vision.pawn.black.maxV",1);

	private ConfigBoundTextField whiteQueenMinH = new ConfigBoundTextField(f,false,"H>","vision.pawn.whitequeen.minH",1);
	private ConfigBoundTextField whiteQueenMinS = new ConfigBoundTextField(f,false,"S>","vision.pawn.whitequeen.minS",1);
	private ConfigBoundTextField whiteQueenMinV = new ConfigBoundTextField(f,false,"V>","vision.pawn.whitequeen.minV",1);
	private ConfigBoundTextField whiteQueenMaxH = new ConfigBoundTextField(f,false,"H<","vision.pawn.whitequeen.maxH",1);
	private ConfigBoundTextField whiteQueenMaxS = new ConfigBoundTextField(f,false,"S<","vision.pawn.whitequeen.maxS",1);
	private ConfigBoundTextField whiteQueenMaxV = new ConfigBoundTextField(f,false,"V<","vision.pawn.whitequeen.maxV",1);

	private ConfigBoundTextField blackQueenMinH = new ConfigBoundTextField(f,false,"H>","vision.pawn.blackqueen.minH",1);
	private ConfigBoundTextField blackQueenMinS = new ConfigBoundTextField(f,false,"S>","vision.pawn.blackqueen.minS",1);
	private ConfigBoundTextField blackQueenMinV = new ConfigBoundTextField(f,false,"V>","vision.pawn.blackqueen.minV",1);
	private ConfigBoundTextField blackQueenMaxH = new ConfigBoundTextField(f,false,"H<","vision.pawn.blackqueen.maxH",1);
	private ConfigBoundTextField blackQueenMaxS = new ConfigBoundTextField(f,false,"S<","vision.pawn.blackqueen.maxS",1);
	private ConfigBoundTextField blackQueenMaxV = new ConfigBoundTextField(f,false,"V<","vision.pawn.blackqueen.maxV",1);


	private ConfigBoundTextField erode1 = new ConfigBoundTextField(f,false,"ERODE = ","vision.filter.1erode",1);
	private ConfigBoundTextField dilate1 = new ConfigBoundTextField(f,false,"DILATE = ","vision.filter.2dilate",1);
	private ConfigBoundTextField erode2 = new ConfigBoundTextField(f,false,"ERODE = ","vision.filter.3erode",1);
	private ConfigBoundTextField dilate2 = new ConfigBoundTextField(f,false,"DILATE = ","vision.filter.4dilate",1);
	
	private Button whitePawnLabel = new Button(f, "WHITE");
	private Button blackPawnLabel = new Button(f, "BLACK");
	private Button whiteQueenLabel = new Button(f, "WHITE QUEEN");
	private Button blackQueenLabel = new Button(f, "BLACK QUEEN");

	private Button transformationLabel = new Button(f, "IMAGE TRANSFORMATION");

	private Button startPreview = new Button(f,this,"START");
	private Button stopPreview = new Button(f,this,"STOP");
	private Button previewLabel = new Button(f, "PREVIEW");
	private Button oneFrame = new Button(f, this, "READ BOARD");
	
	private ConfigBoundTextField cameraId = new ConfigBoundTextField(f, false, "id=", "vision.cameraId",1);
	private Button openCamera = new Button(f,this,"OPEN");
	private Button closeCamera = new Button(f,this,"CLOSE");
	private Button cameraLabel = new Button(f, "CAMERA");
	

	private ConfigBoundTextField fieldRotateBoard = new ConfigBoundTextField(f,false,"rotate=","vision.rotateBoard",1);
	
	private ChessBoardView cbv = new ChessBoardView(f,new Board(),false);
	
	private GuiComponent[][] focusableComps = 
		{
			{whitePawnMinH,whitePawnMinS,whitePawnMinV,whitePawnShow},
			{whitePawnMaxH,whitePawnMaxS,whitePawnMaxV},
			{blackPawnMinH,blackPawnMinS,blackPawnMinV,blackPawnShow},
			{blackPawnMaxH,blackPawnMaxS,blackPawnMaxV},
			{whiteQueenMinH,whiteQueenMinS,whiteQueenMinV,whiteQueenShow},
			{whiteQueenMaxH,whiteQueenMaxS,whiteQueenMaxV},
			{blackQueenMinH,blackQueenMinS,blackQueenMinV,blackQueenShow},
			{blackQueenMaxH,blackQueenMaxS,blackQueenMaxV},
			{startPreview,stopPreview, oneFrame},
			{cameraId,openCamera,closeCamera},
			{erode1},
			{dilate1},
			{erode2},
			{dilate2},
			{fieldRotateBoard},
		};
	public VisionTab(ControlPanelGUI f) 
	{
		super(f);
//		VisionSystem.openCamera(0);
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		cameraLabel.setText(VisionSystem.isCameraOpen() ? "CAMERA [OPEN]" : "CAMERA");
		previewLabel.setText(VisionSystem.isRefreshThreadStarted() ? "PREVIEW [ON]" : "PREVIEW");
		g.drawRect(xo, yo, width, height);

		whitePawnLabel.paint(g, xo+10, yo+10, 100, 50);
		blackPawnLabel.paint(g, xo+10, yo+80, 100, 50);
		whiteQueenLabel.paint(g, xo+10, yo+150, 100, 50);
		blackQueenLabel.paint(g, xo+10, yo+220, 100, 50);

		whitePawnMinH.paint(g, xo+120, yo+10, 80, 20);
		whitePawnMinS.paint(g, xo+210, yo+10, 80, 20);
		whitePawnMinV.paint(g, xo+300, yo+10, 80, 20);
		whitePawnMaxH.paint(g, xo+120, yo+40, 80, 20);
		whitePawnMaxS.paint(g, xo+210, yo+40, 80, 20);
		whitePawnMaxV.paint(g, xo+300, yo+40, 80, 20);
		whitePawnShow.paint(g, xo+390, yo+10, 20, 20);
		
		blackPawnMinH.paint(g, xo+120, yo+80, 80, 20);
		blackPawnMinS.paint(g, xo+210, yo+80, 80, 20);
		blackPawnMinV.paint(g, xo+300, yo+80, 80, 20);
		blackPawnMaxH.paint(g, xo+120, yo+110, 80, 20);
		blackPawnMaxS.paint(g, xo+210, yo+110, 80, 20);
		blackPawnMaxV.paint(g, xo+300, yo+110, 80, 20);
		blackPawnShow.paint(g, xo+390, yo+80, 20, 20);

		whiteQueenMinH.paint(g, xo+120, yo+150, 80, 20);
		whiteQueenMinS.paint(g, xo+210, yo+150, 80, 20);
		whiteQueenMinV.paint(g, xo+300, yo+150, 80, 20);
		whiteQueenMaxH.paint(g, xo+120, yo+180, 80, 20);
		whiteQueenMaxS.paint(g, xo+210, yo+180, 80, 20);
		whiteQueenMaxV.paint(g, xo+300, yo+180, 80, 20);
		whiteQueenShow.paint(g, xo+390, yo+150, 20, 20);
                                          
		blackQueenMinH.paint(g, xo+120, yo+220, 80, 20);
		blackQueenMinS.paint(g, xo+210, yo+220, 80, 20);
		blackQueenMinV.paint(g, xo+300, yo+220, 80, 20);
		blackQueenMaxH.paint(g, xo+120, yo+250, 80, 20);
		blackQueenMaxS.paint(g, xo+210, yo+250, 80, 20);
		blackQueenMaxV.paint(g, xo+300, yo+250, 80, 20);
		blackQueenShow.paint(g, xo+390, yo+220, 20, 20);

		previewLabel.paint(g, xo+10, yo+290, 100, 20);
		startPreview.paint(g, xo+120, yo+290, 80, 20);
		stopPreview.paint(g, xo+210, yo+290, 80, 20);
		oneFrame.paint(g, xo+300, yo+290, 80, 20);
		
		cameraLabel.paint(g, xo+10, yo+320, 100, 20);
		cameraId.paint(g, xo+120, yo+320, 80, 20);
		openCamera.paint(g, xo+210, yo+320, 80, 20);
		closeCamera.paint(g, xo+300, yo+320, 80, 20);
		
		transformationLabel.paint(g, xo+10, yo+360, 370, 20);
		erode1.paint(g, xo+10, yo+390, 370, 20);
		dilate1.paint(g, xo+10, yo+420, 370, 20);
		erode2.paint(g, xo+10, yo+450, 370, 20);
		dilate2.paint(g, xo+10, yo+480, 370, 20);
		fieldRotateBoard.paint(g, xo+10, yo+510, 370, 20);
		try
		{
			g.drawImage(Utils.createAwtImage(VisionSystem.getMasksImg()),xo+420,yo+10,530,310,null);
			g.drawImage(Utils.createAwtImage(VisionSystem.getMaskedImg()),xo+420,yo+340,530,310,null);
			g.drawImage(Utils.createAwtImage(VisionSystem.getTransformedImg()),xo+950,yo+340,310,310,null);
			cbv.setBoard(VisionSystem.getChessBoard());
			cbv.paint(g, xo+950,yo+10, 256, 256);
		}
		catch(Exception e)
		{
//			e.printStackTrace();
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
		return "VISION SYSTEM";
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
	public void actionPerformed(ActionEvent e) 
	{
		try
			{
			Object o = e.getSource();
			if(o==startPreview)
			{
				VisionSystem.startRefreshThread();
			}
			else if(o==stopPreview)
			{
				VisionSystem.stopRefreshThread();
			}
			else if(o==openCamera)
			{
				VisionSystem.openCamera(Configuration.getInt("vision.cameraId"));
			}
			else if(o==closeCamera)
			{
				VisionSystem.closeCamera();
			}
			else if(o==oneFrame)
			{
				VisionSystem.readBoard();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			f.viewSimpleDialog("ERROR");
		}
	}
}
