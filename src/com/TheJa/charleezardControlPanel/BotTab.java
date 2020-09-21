package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import com.TheJa.charleezardControlPanel.Board.Move;


public class BotTab extends GuiTab implements ActionListener
{
	private static class BotSelector extends GuiComponent
	{
		private int selected = 0;
		private Bot[] bots;
		public BotSelector(ControlPanelGUI f, Bot[] bots) 
		{
			super(f);
			this.bots=bots;
		}
		public Bot getSelectedBot()
		{
			return bots[selected];
		}
		@Override
		public void keyPress(KeyEvent e) 
		{
			if(e.getKeyCode()==KeyEvent.VK_UP)
			{
				System.out.println(e);
				if(selected>0)
				{
					selected--;
				}
			}
			else if(e.getKeyCode()==KeyEvent.VK_DOWN)
			{
				if((selected+1)<bots.length)
				{
					selected++;
				}
			}
		}
		@Override
		public void paint(Graphics g, int xo, int yo, int width, int height) 
		{
			g.drawRect(xo, yo, width, height);
			for(int i = -10; i<=10; i++)
			{
				try
				{
					if(bots[i+selected]!=null)
					{
						g.drawRect(xo+10, 30*i+yo+height/2-10, width-20, 20);
						Utils.drawLeftBoundedString(g, bots[i+selected].getName(), xo+15, 30*i+yo+height/2-10, 20);
					}
				}
				catch(ArrayIndexOutOfBoundsException e )
				{
					
				}
			}
			if(isFocused())
			{
				g.fillRect(xo+10, yo+height/2-10, width-20, 20);
				g.setColor(Color.BLACK);
				Utils.drawLeftBoundedString(g, bots[selected].getName(), xo+15,yo+height/2-10, 20);
				g.setColor(Color.WHITE);
			}
			g.drawLine(xo, yo+height/2, xo+10, yo+height/2);
			g.drawLine(xo+width, yo+height/2, xo-10+width, yo+height/2);
		}
	}
	private int focusedRow;
	private int focusedColumn;
	private ChessBoardView currentBoardView = new ChessBoardView(f, new Board(), false);
	private ChessBoardView botMoveView = new ChessBoardView(f, new Board(), false);
	private ConfigBoundTextField botPawnColor = new ConfigBoundTextField(f, false, "COLOR=", "bot.pawnColor");
	private BotSelector selector = new BotSelector(f, new Bot[]{new ProBot()});
	private Button getSolutionBtn = new Button(f, this, "GET SOLUTION");
	private Button readBoardBtn = new Button(f,this,"READ BOARD");
	private Button doMove = new Button(f,this,"MOVE");
	private Thread currentBotThread = new Thread();
	private Button stateLabel = new Button(f, "IDLE");
	
	private GuiComponent[][] focusableComps = 
		{
			{currentBoardView,readBoardBtn,selector},
			{null,getSolutionBtn},
			{null,doMove},
			{null,botPawnColor},
		};
	public BotTab(ControlPanelGUI f) 
	{
		super(f);
		focusableComps[0][0].setFocused(true);
	}
	private boolean compAvaible(int focusedRow, int focusedColumn)
	{
		try
		{
			if(focusableComps[focusedRow][focusedColumn]==null)
			{
				return false;
			}
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}
	@Override
	public String getName() 
	{
		return "BOT";
	}
	@Override
	public void paint(Graphics g, int xo, int yo, int width, int height) 
	{
		getSolutionBtn.setText(!currentBotThread.isInterrupted()&&currentBotThread.isAlive() ? "THINKING..." : "GET SOLUTION");
		g.drawRect(xo, yo, width, height);
		currentBoardView.paint(g, xo+10, yo+10, 304, 304);
		botMoveView.paint(g, xo+10, yo+340, 304, 304);
		readBoardBtn.paint(g, xo+320, yo+10, 100, 20);
		getSolutionBtn.paint(g, xo+320, yo+40, 100, 20);
		doMove.paint(g, xo+320, yo+70, 100, 20);
		selector.paint(g, xo+430, yo+10, 150, height-20);
		botPawnColor.paint(g, xo+320, yo+100, 100, 20);
		stateLabel.paint(g, xo+700, yo+10, 420, 20);
	}
	@Override
	public void keyPress(KeyEvent e) 
	{
		if(e.isControlDown()||focusableComps[focusedRow][focusedColumn].isEntered())
		{
			focusableComps[focusedRow][focusedColumn].keyPress(e);
		}
		else if(e.getKeyCode()==KeyEvent.VK_RIGHT)
		{
			if(compAvaible(focusedRow, focusedColumn+1))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedColumn++;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_LEFT)
		{
			if(compAvaible(focusedRow, focusedColumn-1))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedColumn--;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_DOWN)
		{
			if(compAvaible(focusedRow+1, focusedColumn))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedRow++;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_UP)
		{
			if(compAvaible(focusedRow-1, focusedColumn))
			{
				focusableComps[focusedRow][focusedColumn].setFocused(false);
				focusedRow--;
				focusableComps[focusedRow][focusedColumn].setFocused(true);
			}
		}
		else
		{
			focusableComps[focusedRow][focusedColumn].keyPress(e);
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object o = e.getSource();
		if(o==readBoardBtn)
		{
			VisionSystem.readBoard();
			currentBoardView.setBoard(VisionSystem.getChessBoard());
		}
		else if(o==getSolutionBtn)
		{
			if(currentBotThread!=null)
			{
				currentBotThread.interrupt();
			}
			currentBotThread = new Thread()
			{
				public void run()
				{
					Board b = null;
					if(Configuration.getString("bot.pawnColor").equals("white"))
					{
						b=(selector.getSelectedBot().getMove(currentBoardView.getBoard(), Pawn.Color.WHITE));
					}
					else if(Configuration.getString("bot.pawnColor").equals("black"))
					{
						b=(selector.getSelectedBot().getMove(currentBoardView.getBoard(), Pawn.Color.BLACK));
					}
					if(!isInterrupted())
					{
						botMoveView.setBoard(b);
					}
				}
			};
			currentBotThread.start();
		}
		else if(o==doMove)
		{
			new Thread()
			{
				public void run()
				{
					for(Move m : currentBoardView.getBoard().getMovesTo(botMoveView.getBoard()))
					{
						if(m.isRemove())
						{
						//	stateLabel.setText("REMOVING "+m.getPawn1Coords().x+"x"+m.getPawn1Coords().y);
						//	RobotController.removePawn(m.getPawn1Coords().x,m.getPawn1Coords().y);
						//	stateLabel.setText("IDLE");
						}
						else 
						{
							System.out.println("MOVING "+m.getPawn1Coords().x+"x"+m.getPawn1Coords().y+" to "+m.getPawn2Coords().x+"x"+m.getPawn2Coords().y);
							stateLabel.setText("MOVING "+m.getPawn1Coords().x+"x"+m.getPawn1Coords().y+" to "+m.getPawn2Coords().x+"x"+m.getPawn2Coords().y);
							RobotController.movePawn(m.getPawn1Coords().x,m.getPawn1Coords().y,m.getPawn2Coords().x,m.getPawn2Coords().y);
							stateLabel.setText("IDLE");
						}
						if(m.isRotate())
						{
							stateLabel.setText("ROTATING "+m.getPawn1Coords().x+"x"+m.getPawn1Coords().y);
							RobotController.rotatePawn();
							stateLabel.setText("IDLE");
						}
					}
					RobotController.moveTo(1, 8);
				}
			
			}.start();			
		}
	}
}
