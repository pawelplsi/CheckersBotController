package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;

public class ConfigStoreTab extends GuiTab implements ActionListener
{
	private boolean inList;
	private int row;
	private TextField configField = new TextField(f);
	private GuiComponent[] rightComps = {configField,new Button(f,this,"SAVE"),new Button(f,this,"LOAD")};
	private int focusedRightComp = 0;
	private ArrayList<String> cfgs;
	public ConfigStoreTab(ControlPanelGUI f) 
	{
		super(f);
		rightComps[0].setFocused(true);
		configField.setText(Configuration.getMostRecentConfig());
	}
	@Override
	public String getName() 
	{
		return "CONFIG I/O";
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		cfgs = (ArrayList<String>) ((ArrayList<String>) Configuration.getRecentConfigs()).clone();
		Collections.reverse(cfgs);
		g.drawRect(xo, yo, width, height);
		g.drawRect(xo+10, yo+10, width/2-20, height-20);
		g.drawRect(xo+width/2+10, yo+10, width/2-20, height-20);
		for(int i = -5; i<=5; i++)
		{
			if(i+row<0||i+row>=cfgs.size())
			{
				continue;
			}
			g.drawRect(xo+20, yo+height/2-10+30*(i), width/2-40, 20);
			Utils.drawLeftBoundedString(g, cfgs.get(i+row), xo+25, yo+height/2-10+30*(i), 20);
		}
		if (row>=0&&row<cfgs.size()&&inList)
		{
			g.fillRect(xo+20, yo+height/2-10, width/2-40, 20);
			g.setColor(Color.BLACK);
			Utils.drawLeftBoundedString(g, cfgs.get(row), xo+25, yo+height/2-10, 20);	
			g.setColor(Color.WHITE);
		}
		for(int i = 0; i < rightComps.length; i++)
		{
			rightComps[i].paint(g, xo+width/2+20, yo+20+30*i, width/2-40, 20);
		}
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		if(inList)
		{
			if(e.getKeyCode()==KeyEvent.VK_UP&&row-1>=0&&row-1<cfgs.size())
			{
				row--;
				configField.setText(cfgs.get(row));
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN&&row+1>=0&&row+1<cfgs.size())
			{
				row++;
				configField.setText(cfgs.get(row));
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
			{
				inList=false;
				rightComps[focusedRightComp].setFocused(true);
				configField.setText(cfgs.get(row));
			}
		}
		else
		{
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				if(focusedRightComp>0)
				{
					rightComps[focusedRightComp].setFocused(false);
					focusedRightComp--;
					rightComps[focusedRightComp].setFocused(true);
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				if(focusedRightComp+1<rightComps.length)
				{
					rightComps[focusedRightComp].setFocused(false);
					focusedRightComp++;
					rightComps[focusedRightComp].setFocused(true);
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT&&focusedRightComp!=0)
			{
				inList=true;
				rightComps[focusedRightComp].setFocused(false);
				configField.setText(cfgs.get(row));
			}
			else
			{
				rightComps[focusedRightComp].keyPress(e);
			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource()==rightComps[1])
		{
			Configuration.save(configField.getText());
			Configuration.touchConfig(configField.getText());
			Configuration.saveRecent();
		}
		else if(e.getSource()==rightComps[2])
		{
			try 
			{
				Configuration.load(configField.getText());
			} 
			catch (FileNotFoundException e1) 
			{
				Configuration.clear();
			}
			Configuration.touchConfig(configField.getText());
			Configuration.saveRecent();
		}
	}
}
