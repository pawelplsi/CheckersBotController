package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public abstract class GuiComponent 
{
	protected boolean focused;
	protected ControlPanelGUI f;
	public GuiComponent(ControlPanelGUI f)
	{
		this.f=f;
	}
	public void paint(Graphics g, int xo, int yo, int width,  int height) {}
	public void keyRelease(KeyEvent e){}
	public void keyPress(KeyEvent e){}
	public void setFocused(boolean focused)
	{
		this.focused=focused;
	}
	public boolean isFocused()
	{
		return focused;
	};
	public boolean isEnterable()
	{
		return false;
	}
	public boolean isEntered()
	{
		return false;
	}
	public void setEntered(boolean entered)
	{
		
	}
}
