package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;


public class ChessBoardView extends GuiComponent
{
	private Board board = new Board();
	private boolean editMode;
	private boolean entered;
	private int focusedRow;
	private int focusedCol;
	private Pawn cut;
	public ChessBoardView(ControlPanelGUI f, Board b, boolean editMode) 
	{
		super(f);
		this.board = b;
		this.editMode=editMode;
//		this.f=f;
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		for(int i = 0;i<9;i++)
		{
			g.drawLine(xo+i*width/8, yo, xo+i*width/8, yo+height);
			g.drawLine( xo,yo+i*height/8, xo+width, yo+i*height/8);
		}
		try
		{
			for(int x = 1; x<=8;x++)
				for(int y = 1; y<=8;y++)
				{
					Pawn p = board.getPawn(x, y);
					if(p==Pawn.WHITE)
					{
						g.fillOval(xo+(x-1)*width/8, yo+height-y*height/8, width/8, height/8);
					}
					else if(p==Pawn.BLACK)
					{
						g.drawOval(xo+(x-1)*width/8, yo+height-y*height/8, width/8, height/8);
					}
					else if(p==Pawn.WHITEQUEEN)
					{
						g.fillOval(xo+(x-1)*width/8, yo+height-y*height/8, width/8, height/8);
						g.setColor(Color.BLACK);
						g.fillOval(xo+(x-1)*width/8+width/32, yo+height-y*height/8+height/32, width/16, height/16);
						g.setColor(Color.WHITE);
					}
					else if(p==Pawn.BLACKQUEEN)
					{
						g.drawOval(xo+(x-1)*width/8, yo+height-y*height/8, width/8, height/8);
						g.drawOval(xo+(x-1)*width/8+width/32, yo+height-y*height/8+height/32, width/16, height/16);
					}
				}
			if(isEntered())
			{
				g.drawRect(xo+focusedCol*width/8+1, yo+focusedRow*height/8+1, width/8-2, height/8-2);
			}
			else if(isFocused())
			{
				g.drawRect(xo+1, yo+1, width-2, height-2);
				g.drawRect(xo+2, yo+2, width-4, height-4);
			}
		}
		catch(NullPointerException e)
		{
			board = new Board();
		}
	}
	public Board getBoard() 
	{
		return board;
	}
	public void setBoard(Board board) 
	{
		this.board = board;
	}
	@Override
	public boolean isEnterable() 
	{
		return editMode;
	}
	@Override
	public boolean isEntered() 
	{
		return entered;
	}
	@Override
	public void setEntered(boolean entered) 
	{
		this.entered=entered;
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		try
		{
			if(e.getKeyCode()==KeyEvent.VK_ENTER)
			{
				setEntered(!isEntered());
			}
			else if(e.getKeyCode()==KeyEvent.VK_LEFT&&focusedCol>0)
			{
				focusedCol--;
			}
			else if(e.getKeyCode()==KeyEvent.VK_UP&&focusedRow>0)
			{
				focusedRow--;
			}
			else if(e.getKeyCode()==KeyEvent.VK_RIGHT&&focusedCol<7)
			{
				focusedCol++;
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN&&focusedRow<7)
			{
				focusedRow++;
			}
			else if(isEntered())
			{
				if(e.getKeyChar()=='q')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.WHITE);
				}
				else if(e.getKeyChar()=='w')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.WHITEQUEEN);
				}
				else if(e.getKeyChar()=='e')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.BLACK);
				}
				else if(e.getKeyChar()=='r')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.BLACKQUEEN);
				}
				else if(e.getKeyChar()=='a')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.NULL);
				}
				else if(e.getKeyChar()=='s')
				{
					cut = board.getPawn(focusedCol+1, 8-focusedRow);
					board.setPawn(focusedCol+1, 8-focusedRow, Pawn.NULL);
				}
				else if(e.getKeyChar()=='d')
				{
					board.setPawn(focusedCol+1, 8-focusedRow, cut);
				}
			}
		}
		catch(IllegalArgumentException exc)
		{
		
		}
	}
}
