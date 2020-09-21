package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class ConfigBoundTextField extends GuiComponent
	{
		private int cursorPosition = 0;
		private String prefix = "";
		private String configKey="";
		private double incrementValue;
		private boolean leftBounded = true;
		public ConfigBoundTextField(ControlPanelGUI f, boolean focused, String prefix, String configKey) 
		{
			super(f);
			this.setFocused(focused);
			this.prefix = prefix;
			this.configKey=configKey;
		}
		public ConfigBoundTextField(ControlPanelGUI f, boolean focused, String prefix, String configKey, double incrementValue) 
		{
			super(f);
			this.setFocused(focused);
			this.prefix = prefix;
			this.configKey=configKey;
			this.incrementValue=incrementValue;
		}
		public void setConfigKey(String configKey)
		{
			this.configKey=configKey;
		}
		public String getText()
		{
			return configKey;
		}
		public void setCursorPoistion(int cursorPosition)
		{
			this.cursorPosition = cursorPosition;
		}
		public int getCursorPosition()
		{
			return cursorPosition;
		}
		@Override 
		public void paint(Graphics g, int xo, int yo, int width, int height)
		{
			if(cursorPosition>Configuration.getString(configKey).length())
			{
				cursorPosition=Configuration.getString(configKey).length();
			}
			if(!leftBounded)
			{
				if(focused)
				{
					g.fillRect(xo, yo, width, height);
					g.setColor(Color.BLACK);
					Utils.drawCenteredString(g, prefix+Utils.insertString(Configuration.getString(configKey),"|",cursorPosition), xo+5, yo, width, height);
					g.setColor(Color.WHITE);
				}
				else
				{
					g.drawRect(xo, yo, width, height);
					Utils.drawCenteredString(g, prefix+Configuration.getString(configKey), xo+5, yo, width, height);
				}
			}
			else
			{
				if(focused)
				{
					g.fillRect(xo, yo, width, height);
					g.setColor(Color.BLACK);
					Utils.drawLeftBoundedString(g, prefix+Utils.insertString(Configuration.getString(configKey),"|",cursorPosition), xo+5, yo, height);
					g.setColor(Color.WHITE);
				}
				else
				{
					g.drawRect(xo, yo, width, height);
					Utils.drawLeftBoundedString(g, prefix+Configuration.getString(configKey), xo+5, yo, height);
				}
			}
		}
		@Override
		public void keyPress(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				if(cursorPosition<Configuration.getString(configKey).length())
				{
					cursorPosition++;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT)
			{
				if(cursorPosition>0)
				{
					cursorPosition--;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP&&incrementValue!=0)
			{

				try
				{
					Configuration.set(configKey,(int)Configuration.getInt(configKey)+((int)incrementValue));
					return;
				}
				catch(NumberFormatException ex)
				{
					
				}
				try
				{
					Configuration.set(configKey,Configuration.getDouble(configKey)+incrementValue);
				}
				catch(NumberFormatException ex)
				{
					
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN&&incrementValue!=0)
			{
				try
				{
					Configuration.set(configKey,(int)Configuration.getInt(configKey)-((int)incrementValue));
					return;
				}
				catch(NumberFormatException ex)
				{
					
				}
				try
				{
					Configuration.set(configKey,Configuration.getDouble(configKey)-incrementValue);
				}
				catch(NumberFormatException ex)
				{
					
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			{
				if(cursorPosition>0)
				{
					Configuration.set(configKey, Configuration.getString(configKey).substring(0, cursorPosition-1)+Configuration.getString(configKey).substring(cursorPosition));
					cursorPosition--;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_DELETE)
			{
				if(cursorPosition<Configuration.getString(configKey).length())
				{
					Configuration.set(configKey, Configuration.getString(configKey).substring(0, cursorPosition)+Configuration.getString(configKey).substring(cursorPosition+1));
				}
			}
			else if(e.getKeyLocation()==KeyEvent.KEY_LOCATION_STANDARD)
			{
				Configuration.set(configKey, Utils.insertString(Configuration.getString(configKey), String.valueOf(e.getKeyChar()), cursorPosition));
				cursorPosition++;
			}
		}
		public boolean isLeftBounded() {
			return leftBounded;
		}
		public void setLeftBounded(boolean leftBounded) {
			this.leftBounded = leftBounded;
		}
		public String getPrefix() {
			return prefix;
		}
		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}
	}
