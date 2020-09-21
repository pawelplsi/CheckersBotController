package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.text.AttributedCharacterIterator;

import javax.swing.JFrame;

public class ControlPanelGUI 
{
	private ControlPanelJFrame window = new ControlPanelJFrame("CharLeeZard control panel v.1.0");
	private boolean dialogActive;
	private String dialogMessage="";
	private BufferedImage content;
	private Board b = new Board();
	private Thread repaintThread = new Thread()
	{
		@Override
		public void run()
		{
			while(window.isVisible())
			{
				window.repaint();
			}
		}
	};
	private class ControlPanelJFrame extends JFrame implements KeyListener
	{
		private int activeTab = 3;
		private GuiTab[] tabs = {new ConfigTab(ControlPanelGUI.this),new ConfigStoreTab(ControlPanelGUI.this), new RobotControlTab(ControlPanelGUI.this),new VisionTab(ControlPanelGUI.this),new BotTab(ControlPanelGUI.this)};
		private static final long serialVersionUID = 7309824574091415748L;
		public ControlPanelJFrame(String string) 
		{
			super(string);
			try
			{
				activeTab=Configuration.getInt("configPanel.lastTab");
			}
			catch(Exception e)
			{
				
			}
		}
		public void paint(Graphics g)
		{
			if(content==null)
			{
				content=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			}
			Graphics g2=content.getGraphics();
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("monospace",Font.PLAIN,12));
			for(int i = 0; i<tabs.length;i++)
			{
				g2.drawRect(10+i*getWidth()/tabs.length, 10, getWidth()/tabs.length-20, 20);
				Utils.drawCenteredString(g2, tabs[i].getName(), 10+i*getWidth()/tabs.length, 10, getWidth()/tabs.length-20, 20);
			}
			g2.fillRect(10+activeTab*getWidth()/tabs.length, 10, getWidth()/tabs.length-20, 20);
			g2.setColor(Color.BLACK);
			Utils.drawCenteredString(g2, tabs[activeTab].getName(), 10+activeTab*getWidth()/tabs.length, 10, getWidth()/tabs.length-20, 20);
			g2.setColor(Color.WHITE);
			tabs[activeTab].paint(g2, 10, 40, getWidth()-20, getHeight()-50);
			if(dialogActive)
			{
				Utils.drawSimpleDialog(g2,dialogMessage,getWidth()/2,getHeight()/2);
			}
			g.drawImage(content, 0, 0, null);
		}
		
		@Override
		public void keyPressed(KeyEvent e) 
		{
			if(dialogActive)
			{
				dialogActive=false;
			}
			else if(e.getKeyCode()==KeyEvent.VK_ESCAPE)
			{
				Configuration.save(Configuration.getMostRecentConfig());
				VisionSystem.terminate();
				dispose();
			}
			else if(e.getKeyCode()==KeyEvent.VK_F1)
			{
				activeTab=0;
			}
			else if(e.getKeyCode()==KeyEvent.VK_F2)
			{
				activeTab=1;
			}
			else if(e.getKeyCode()==KeyEvent.VK_F3)
			{
				activeTab=2;
			}
			else if(e.getKeyCode()==KeyEvent.VK_F4)
			{
				activeTab=3;
			}
			else if(e.getKeyCode()==KeyEvent.VK_F5)
			{
				activeTab=4;
			}
			else
			{
				tabs[activeTab].keyPress(e);
			}
			Configuration.set("configPanel.lastTab",activeTab);
		}
		@Override
		public void keyReleased(KeyEvent arg0) 
		{
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) 
		{
			
		}
	}
	public void setActive(boolean active)
	{
		if(window.isVisible()!=active)
		{
			if(active)
			{
				window.addKeyListener(window);
				window.setUndecorated(true);
				window.setExtendedState(JFrame.MAXIMIZED_BOTH);
				window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				window.setVisible(true);
				repaintThread.start();
			}
			else
			{
				
			}
		}
	}
	public void viewSimpleDialog(String message)
	{
		this.dialogMessage = message;
		dialogActive = true;
	}
}
