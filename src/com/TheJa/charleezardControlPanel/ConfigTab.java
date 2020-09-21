package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
	public class ConfigTab extends GuiTab
	{
		private TextField renameField = new TextField(f);
		private List<String> props = new LinkedList<String>();
		private int row = 0;
		private boolean searchMode = false;
		private boolean createMode = false;
		private boolean editMode = false;
		private String renameFrom;
		private TextField searchField = new TextField(f);
		private TextField searchLabelField = new TextField(f);
		private boolean renameMode = false;
		private int insertPos = 0;
		public ConfigTab(ControlPanelGUI f) 
		{
			super(f);
			searchLabelField.setText("SEARCH");
			searchLabelField.setCursorPoistion(0);
			searchLabelField.setLeftBounded(false);
		}
		@Override
		public String getName()
		{
			return "CONFIG EDITOR";
		}
		@Override
		public void paint(Graphics g, int xo, int yo, int width, int height) 
		{
			props.clear();
			for(String prop : Configuration.getPropertyNames())
			{
				if(prop.startsWith(searchField.getText()))
				{
					props.add(prop);
				}
			}
			searchLabelField.paint(g, xo+10, yo+10, width-20, 20);
			searchField.paint(g, xo+10, yo+40, width-20, 20);
			Collections.sort(props, String.CASE_INSENSITIVE_ORDER);
			g.drawRect(xo, yo, width, height);
			for(int i = -5; i<=5; i++)
			{
				if(i+row<0||i+row>=props.size())
				{
					continue;
				}
				g.drawRect(xo+10, yo+height/2-10+30*(i), width/2-10, 20);
				Utils.drawLeftBoundedString(g, props.get(i+row), xo+15, yo+height/2-10+30*(i), 20);
				g.drawRect(xo+width/2+10, yo+height/2-10+30*(i), width/2-10, 20);
				Utils.drawLeftBoundedString(g, Configuration.getString(props.get(i+row)), xo+width/2+15, yo+height/2-10+30*(i), 20);
			}
			if(editMode)
			{
				if(insertPos>Configuration.getString(props.get(row)).length())
				{
					insertPos=Configuration.getString(props.get(row)).length();
				}
				g.fillRect(xo+width/2+10, yo+height/2-10, width/2-10, 20);
				g.setColor(Color.BLACK);
				Utils.drawLeftBoundedString(g, Utils.insertString(Configuration.getString(props.get(row)),"|",insertPos), xo+width/2+15, yo+height/2-10, 20);
				g.setColor(Color.WHITE);
			}
			else if(renameMode)
			{
				renameField.paint(g, xo+10, yo+height/2-10, width/2-10, 20);
			}
			else if (row>=0&&row<props.size())
			{
				g.fillRect(xo+10, yo+height/2-10, width/2-10, 20);
				g.setColor(Color.BLACK);
				Utils.drawLeftBoundedString(g, props.get(row), xo+15, yo+height/2-10, 20);	
				g.setColor(Color.WHITE);
			}
		}
		@Override
		public void keyPress(KeyEvent e) 
		{
			if(editMode)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					editMode=false;
				}
				else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
				{
					insertPos++;
				}
				else if(e.getKeyCode()==KeyEvent.VK_LEFT)
				{
					if(insertPos>0)
					{
						insertPos--;
					}
				}
				else if(e.getKeyCode()==KeyEvent.VK_BACK_SPACE)
				{
					if(insertPos>0)
					{
						Configuration.set(props.get(row), Configuration.getString(props.get(row)).substring(0, insertPos-1)+Configuration.getString(props.get(row)).substring(insertPos));
						insertPos--;
					}
				}
				else if(e.getKeyCode()==KeyEvent.VK_DELETE)
				{
					if(insertPos<Configuration.getString(props.get(row)).length())
					{
						Configuration.set(props.get(row), Configuration.getString(props.get(row)).substring(0, insertPos)+Configuration.getString(props.get(row)).substring(insertPos+1));
					}
				}
				else if(e.getKeyLocation()==KeyEvent.KEY_LOCATION_STANDARD)
				{
					Configuration.set(props.get(row), Utils.insertString(Configuration.getString(props.get(row)), String.valueOf(e.getKeyChar()), insertPos));
					insertPos++;
				}
			}
			else if(renameMode)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					if(createMode)
					{
						Configuration.set(renameField.getText(), "");
					}
					else if(!renameFrom.equals(renameField.getText()))
					{
						Configuration.set(renameField.getText(), Configuration.getString(renameFrom));
						Configuration.set(renameFrom, null);
					}
					renameMode=false;
					createMode=false;
				}
				else
				{
					renameField.keyPress(e);
				}
			}
			else if(searchMode)
			{
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					searchMode=false;
					searchField.setFocused(false);
				}
				else
				{
					searchField.keyPress(e);
					searchField.setFocused(true);
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_ADD)
			{
				renameMode=true;
				createMode=true;
				renameField.setText("");
				renameField.setFocused(true);
				renameField.setCursorPoistion(0);
			}
			else if(e.getKeyCode()==KeyEvent.VK_SUBTRACT||e.getKeyCode()==KeyEvent.VK_DELETE)
			{
				if(row>=0&&row<props.size())
				{
					Configuration.set(props.get(row), null);
					row--;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP&&row-1>=0&&row-1<props.size()&&!editMode)
			{
				row--;
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN&&row+1>=0&&row+1<props.size()&&!editMode)
			{
				row++;
			}
			else if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				if(row>=0&&row<props.size())
				{
					editMode=true;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_SHIFT)
			{
				searchMode=true;
				searchField.setFocused(true);
			}
			else if(e.getKeyCode()==KeyEvent.VK_R)
			{
				if(row>=0&&row<props.size())
				{
					renameMode=true;
					renameField.setText(props.get(row));
					renameField.setFocused(true);
					renameField.setCursorPoistion(0);
					renameFrom=props.get(row);
				}
			}
		}
	}