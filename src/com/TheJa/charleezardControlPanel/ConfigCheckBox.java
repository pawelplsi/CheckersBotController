package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

public class ConfigCheckBox extends GuiComponent 
{
	private String config;
	public ConfigCheckBox(ControlPanelGUI f, String config)
	{
		super(f);
		this.config=config;
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		if(isFocused())
		{
			g.fillRect(xo, yo, width, height);
			g.setColor(Color.BLACK);
		}
		g.drawRect(xo, yo, width, height);
		if(Configuration.getBoolean(config))
		{
			g.drawLine(xo, yo, xo+width, yo+height);
			g.drawLine(xo+width, yo, xo, yo+height);
		}
		g.setColor(Color.WHITE);
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_ENTER)
		{
			Configuration.set(config, !Configuration.getBoolean(config));
		}
	}
}
