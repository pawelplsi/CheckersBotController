package com.TheJa.charleezardControlPanel;

import java.io.FileNotFoundException;

public class Main 
{
	static
	{
		System.loadLibrary(org.opencv.core.Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String[]args) throws FileNotFoundException
	{
		Configuration.loadRecent();
		Configuration.load(Configuration.getMostRecentConfig());
		ControlPanelGUI gui = new ControlPanelGUI();
		gui.setActive(true);
		if(Configuration.getBoolean("vision.cameraAutoOpen"))
		{
			VisionSystem.openCamera(Configuration.getInt("vision.cameraId"));
		}
		if(Configuration.getBoolean("vision.previewAutostart"))
		{
			VisionSystem.startRefreshThread();
		}
	}
}