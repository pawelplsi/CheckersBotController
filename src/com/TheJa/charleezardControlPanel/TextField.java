package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

public class TextField extends GuiComponent
	{
		private int cursorPosition = 0;
		private String text = new String();
		private String prefix = "";
		private boolean leftBounded = true;
		public TextField(ControlPanelGUI f) 
		{
			super(f);
		}
		public TextField(ControlPanelGUI f, boolean focused, String prefix, String text) 
		{
			super(f);
			this.setFocused(focused);
			this.prefix = prefix;
			this.text= text;
		}
		public void setText(String text)
		{
			this.text=text;
		}
		public String getText()
		{
			return text;
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
			if(!leftBounded)
			{
				if(focused)
				{
					g.fillRect(xo, yo, width, height);
					g.setColor(Color.BLACK);
					Utils.drawCenteredString(g, prefix+Utils.insertString(prefix+text,"|",cursorPosition), xo+5, yo, width, height);
					g.setColor(Color.WHITE);
				}
				else
				{
					g.drawRect(xo, yo, width, height);
					Utils.drawCenteredString(g, prefix+text, xo+5, yo, width, height);
				}
			}
			else
			{
				if(focused)
				{
					g.fillRect(xo, yo, width, height);
					g.setColor(Color.BLACK);
					Utils.drawLeftBoundedString(g, prefix+Utils.insertString(text,"|",cursorPosition), xo+5, yo, height);
					g.setColor(Color.WHITE);
				}
				else
				{
					g.drawRect(xo, yo, width, height);
					Utils.drawLeftBoundedString(g, prefix+text, xo+5, yo, height);
				}
			}
		}
		@Override
		public void keyPress(KeyEvent e)
		{
			if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				if(cursorPosition<text.length())
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
			else if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				
			}
			else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
			{
				if(cursorPosition>0)
				{
					text = text.substring(0, cursorPosition-1)+text.substring(cursorPosition);
					cursorPosition--;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_DELETE)
			{
				if(cursorPosition<text.length())
				{
					text = text.substring(0, cursorPosition)+text.substring(cursorPosition+1);
				}
			}
			else if(e.getKeyLocation()==KeyEvent.KEY_LOCATION_STANDARD)
			{
				text= Utils.insertString(text, String.valueOf(e.getKeyChar()), cursorPosition);
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
