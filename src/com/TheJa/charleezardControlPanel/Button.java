package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class Button extends GuiComponent 
{
	private ActionListener l;
	private String text;
	public Button(ControlPanelGUI f) 
	{
		super(f);
	}
	public Button(ControlPanelGUI f, ActionListener l, String label)
	{
		this(f);
		setActionListener(l);
		setText(label);
	}
	public Button(ControlPanelGUI f, String label)
	{
		this(f);
		setText(label);
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		if(focused)
		{
			g.fillRect(xo, yo, width, height);
			g.setColor(Color.BLACK);
			Utils.drawCenteredString(g, text, xo, yo, width, height);
			g.setColor(Color.WHITE);
		}
		else
		{
			g.drawRect(xo, yo, width, height);
			Utils.drawCenteredString(g, text, xo, yo, width, height);
		}
	}
	public String getText() 
	{
		return text;
	}
	public void setActionListener(ActionListener l)
	{
		this.l = l;
	}
	public ActionListener getActionListener()
	{
		return l;
	}
	public void setText(String text) 
	{
		this.text = text;
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			if(l!=null)
			{
				l.actionPerformed(new ActionEvent(this, 0, "CLICK"));
			}
		}
	}
}