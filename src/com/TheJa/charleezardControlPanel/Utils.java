package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;

public class Utils 
{
	public static void drawCenteredString(Graphics g, String text, int x, int y, int width, int height) 
	{
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
	    int x1 = (width - metrics.stringWidth(text)) / 2;
	    int y1 = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.drawString(text, x1+x, y1+y);
	}
	public static void drawLeftBoundedString(Graphics g, String text, int x, int y, int height) 
	{
	    FontMetrics metrics = g.getFontMetrics(g.getFont());
	    int y1 = ((height - metrics.getHeight()) / 2) + metrics.getAscent();
	    g.drawString(text, x, y1+y);
	}
	public static String insertString(String string, String insert, int position)
	{
		return string.substring(0, position) + insert + string.substring(position, string.length());
	}
	public static void drawSimpleDialog(Graphics g, String message, int x, int y)
	{
//		String messages[] = message.split("\n");
		FontMetrics metrics = g.getFontMetrics(g.getFont());
	    int x1 = x-metrics.stringWidth(message) / 2;
	    int y1 = y-metrics.getHeight()/ 2;
	    g.setColor(Color.BLACK);
	    g.fillRect(x1-10, y1-10, metrics.stringWidth(message)+20, metrics.getHeight()+20);
	    g.setColor(Color.WHITE);
	    g.drawRect(x1-10, y1-10, metrics.stringWidth(message)+20, metrics.getHeight()+20);
	    g.drawString(message, x1, y1+metrics.getAscent());
	}
	public static BufferedImage createAwtImage(Mat mat) {

	    int type = 0;
	    if (mat.channels() == 1) {
	        type = BufferedImage.TYPE_BYTE_GRAY;
	    } else if (mat.channels() == 3) {
	        type = BufferedImage.TYPE_3BYTE_BGR;
	    } else {
	        return null;
	    }

	    BufferedImage image = new BufferedImage(mat.width(), mat.height(), type);
	    WritableRaster raster = image.getRaster();
	    DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
	    byte[] data = dataBuffer.getData();
	    mat.get(0, 0, data);

	    return image;
	}
	private static double[]da1 = new double[2];
	private static Mat m1 = new Mat();
	public static void hsvInRange(Mat src, Scalar low, Scalar high, Mat dst)
	{
		if(low.val[0]>high.val[0])
		{
			da1[0]=low.val[0];
			da1[1]=high.val[0];
			high.val[0] = 255;
			Core.inRange(src, low, high, m1);
			high.val[0] = da1[1];
			low.val[0] = 0;
			Core.inRange(src, low, high, dst);
			Core.bitwise_or(dst, m1, dst);
			low.val[0] = da1[1];
		}
		else
		{
			Core.inRange(src, low, high, dst);
		}
	}
//	public static 
}
