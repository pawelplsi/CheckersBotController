package com.TheJa.charleezardControlPanel;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import com.TheJa.charleezardControlPanel.Pawn.Color;


public class Board 
{
	public static class Move
	{
		private boolean remove;
		private boolean rotate;
		private Point pawn1Coords = new Point();
		private Point pawn2Coords = new Point();
		public Move()
		{
			
		}
		public Move(boolean remove, boolean rotate, Point pawn1, Point pawn2)
		{
			this.remove=remove;
			this.rotate=rotate;
			this.pawn1Coords=pawn1;
			this.pawn2Coords=pawn2;
		}
		public boolean isRemove() 
		{
			return remove;
		}
		public void setRemove(boolean remove) 
		{
			this.remove = remove;
		}
		public boolean isRotate() 
		{
			return rotate;
		}
		public void setRotate(boolean rotate) 
		{
			this.rotate = rotate;
		}
		public Point getPawn1Coords() 
		{
			return pawn1Coords;
		}
		public void setPawn1Coords(Point pawn1Coords) 
		{
			this.pawn1Coords = pawn1Coords;
		}
		public Point getPawn2Coords() 
		{
			return pawn2Coords;
		}
		public void setPawn2Coords(Point pawn2Coords) 
		{
			this.pawn2Coords = pawn2Coords;
		}
		
	}
	private byte[] pawns = new byte[32];
	public Board clone()
	{
		Board b = new Board();
		b.pawns = pawns.clone();
		return b;
	}
	
	public void setPawn(int x, int y, Pawn p)
	{
		if((x+y)%2==1)
		{
			throw new IllegalArgumentException("Cannot place a pawn on white field.");
		}
		if(x<1||x>8||y<1||y>8)
		{
			throw new IndexOutOfBoundsException();
		} 
		if(y%2==1)
		{
			pawns[4*(y-1)+(x+1)/2-1]=p.getId();
		}
		else if(y%2==0)
		{
			pawns[4*(y-1)+(x)/2-1]=p.getId();
//			System.out.println(4*(y-1)+(x)/2);
		}
	}
	public Pawn getPawn(int x, int y)
	{
		if((x+y)%2==1)
		{
			return Pawn.NULL;
		}
		if(x<1||x>8||y<1||y>8)
		{
			return Pawn.NULL;
		} 
		if(y%2==1)
		{
			return Pawn.getById(pawns[4*(y-1)+(x+1)/2-1]);
		}
		else if(y%2==0)
		{
			return Pawn.getById(pawns[4*(y-1)+(x)/2-1]);
		}
		throw new Error("XD");
	}
	public String getPawnAsci(Pawn p)
	{
		if(p==Pawn.BLACK)
			return "O";
		if(p==Pawn.BLACKQUEEN)
			return "M";
		if(p==Pawn.WHITE)
			return "X";
		if(p==Pawn.WHITEQUEEN)
			return "W";
		return " ";
	}
	public void printAsci()
	{
		System.out.println("    A1  B2  C3  D4  E5  F6  G7  H8");
		for(int y = 8; y >= 1; y--)
		{
			System.out.print("  --------------------------------- \n");
			System.out.print(y+" | ");
			for(int x = 1; x <= 8; x++)
			{
				System.out.print(getPawnAsci(getPawn(x, y)));
				System.out.print(" | ");
			}
			System.out.print("\n");
		}
		System.out.print("  --------------------------------- \n");
	}
//	public int rate(int howDeep, boolean myTurn, Pawn.Color color)
//	{
//		if(howDeep<=1)
//		{
//			int wp = 0;
//			int bp = 0;
//			int ret = 0;
//			if(myTurn)
//			{
//				if(getAvaibleMoves(color).size()==0)
//				{
//					bp += 1000000;
//				}
//			}
//			else
//			{
//				if(getAvaibleMoves(color.getOposite()).size()==0)
//					wp += 1000000;
//			}
//			Pawn p;
//			for(int x = 1; x <= 8; x++)
//				for(int y = 1; y <=8; y++)
//				{
//					p = getPawn(x, y);
//					if(p==Pawn.WHITE)
//					{
//						wp+=40;
//						wp+=x;
//					}
//					else if(p==Pawn.BLACK)
//					{
//						bp+=40;
//						bp+=(9-x);
//					}
//					else if(p==Pawn.WHITEQUEEN)
//					{
//						wp+=80;
//					}
//					else if(p==Pawn.BLACKQUEEN)
//					{
//						bp+=80;
//					}
//				}
//			if(color==Pawn.Color.WHITE)
//			{
//				return 1000*wp/(wp+bp);
//			}
//			return 1000*bp/(wp+bp);
//		}
//		int total;
//		int tmp;
//		List<Board> moves;
//		if(myTurn)
//		{
//			total = Integer.MIN_VALUE;
//			moves = getAvaibleMoves(color);
//			for(Board b : moves)
//			{
//				tmp = b.rate(howDeep-1, !myTurn, color);
//				if(total<tmp)
//				{
//					total = tmp;
//				}
//			}
//		}
//		else
//		{
//
//			total = Integer.MAX_VALUE;
//			moves = getAvaibleMoves(color.getOposite());
//			for(Board b : moves)
//			{
//				tmp = b.rate(howDeep-1, !myTurn, color);
//				if(total>tmp)
//				{
//					total = tmp;
//				}
//			}
//		}
//		return total;
//	}
//	public int rateavg(int howDeep, boolean myTurn, Pawn.Color color)
//	{
//		if(howDeep<=1)
//		{
//			int wp = 0;
//			int bp = 0;
//			int ret = 0;
//			if(myTurn)
//			{
//				if(getAvaibleMoves(color).size()==0)
//				{
//					bp += 1000000;
//				}
//			}
//			else
//			{
//				if(getAvaibleMoves(color.getOposite()).size()==0)
//					return 1000000;
//			}
//			Pawn p;
//			for(int x = 1; x <= 8; x++)
//				for(int y = 1; y <=8; y++)
//				{
//					p = getPawn(x, y);
//					if(p==Pawn.WHITE)
//					{
//						wp+=40;
//						wp+=x;
//					}
//					else if(p==Pawn.BLACK)
//					{
//						bp+=40;
//						bp+=(9-x);
//					}
//					else if(p==Pawn.WHITEQUEEN)
//					{
//						wp+=80;
//					}
//					else if(p==Pawn.BLACKQUEEN)
//					{
//						bp+=80;
//					}
//				}
//			if(color==Pawn.Color.WHITE)
//			{
//				return 1000*wp/(wp+bp);
//			}
//			return 1000*bp/(wp+bp);
//		}
//		int total = 0;
//		int tmp;
//		List<Board> moves;
//		if(myTurn)
//		{
//			total = Integer.MIN_VALUE;
//			moves = getAvaibleMoves(color);
//			for(Board b : moves)
//			{
//				tmp = b.rate(howDeep-1, !myTurn, color);
//				if(total<tmp)
//				{
//					total = tmp;
//				}
//			}
//		}
//		else
//		{
//
////			total = Integer.MAX_VALUE;
//			moves = getAvaibleMoves(color.getOposite());
//			for(Board b : moves)
//			{
//				total += b.rate(howDeep-1, !myTurn, color);
//			}
//			total/=moves.size();
//		}
//		return total;
//	}
	public List<Board> getAvaibleCapturesForPawn(int x,  int y)
	{
		List<Board> captures = new ArrayList<Board>();
		if(getPawn(x, y)==Pawn.WHITE)
		{
			//x+2. y+2,
			if(x<7&&y<7&&getPawn(x+1, y+1).getColor()==Pawn.Color.BLACK&&getPawn(x+2, y+2)==Pawn.NULL)
			{
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x+1, y+1, Pawn.NULL);
				b.setPawn(x+2, y+2, y==6 ? Pawn.WHITEQUEEN : Pawn.WHITE);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x+2, y+2));
			}
			//x-2, y+2
			if(x>2&&y<7&&getPawn(x-1, y+1).getColor()==Pawn.Color.BLACK&&getPawn(x-2, y+2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x-(int)2)+" "+((int)y+(int)2)+" - "+((int)x-(int)1)+" "+((int)y+(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x-1, y+1, Pawn.NULL);
				b.setPawn(x-2, y+2, y==6 ? Pawn.WHITEQUEEN : Pawn.WHITE);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x-2, y+2));
			}
			//x-2, y-2
			if(x>2&&y>2&&getPawn(x-1, y-1).getColor()==Pawn.Color.BLACK&&getPawn(x-2, y-2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x-(int)2)+" "+((int)y-(int)2)+" - "+((int)x-(int)1)+" "+((int)y-(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x-1, y-1, Pawn.NULL);
				b.setPawn(x-2, y-2, Pawn.WHITE);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x-2, y-2));
			}
			//x+2, y-2
			if(x<7&&y>2&&getPawn(x+1, y-1).getColor()==Pawn.Color.BLACK&&getPawn(x+2, y-2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x+(int)2)+" "+((int)y-(int)2)+" - "+((int)x+(int)1)+" "+((int)y-(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x+1, y-1, Pawn.NULL);
				b.setPawn(x+2, y-2, Pawn.WHITE);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x+2, y-2));
			}
			
		}
		else if (getPawn(x, y)==Pawn.BLACK)
		{
			if(x<7&&y>2&&getPawn(x+1, y-1).getColor()==Pawn.Color.WHITE&&getPawn(x+2, y-2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x+(int)2)+" "+((int)y-(int)2)+" - "+((int)x+(int)1)+" "+((int)y-(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x+1, y-1, Pawn.NULL);
				b.setPawn(x+2, y-2, y==3 ? Pawn.BLACKQUEEN : Pawn.BLACK);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x+2, y-2));
			}
			//x-2, y-2
			else if(x>2&&y>2&&getPawn(x-1, y-1).getColor()==Pawn.Color.WHITE&&getPawn(x-2, y-2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x-(int)2)+" "+((int)y-(int)2)+" - "+((int)x-(int)1)+" "+((int)y-(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x-1, y-1, Pawn.NULL);
				b.setPawn(x-2, y-2, y==3 ? Pawn.BLACKQUEEN : Pawn.BLACK);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x-2, y-2));
			}
			//x-2, y+2
			if(x>2&&y<7&&getPawn(x-1, y+1).getColor()==Pawn.Color.WHITE&&getPawn(x-2, y+2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x-(int)2)+" "+((int)y+(int)2)+" - "+((int)x-(int)1)+" "+((int)y+(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x-1, y+1, Pawn.NULL);
				b.setPawn(x-2, y+2, Pawn.BLACK);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x-2, y+2));
			}
			//x+2, y+2
			if(x<7&&y<7&&getPawn(x+1, y+1).getColor()==Pawn.Color.WHITE&&getPawn(x+2, y+2)==Pawn.NULL)
			{
//				System.out.println(x+" "+y+" -> "+((int)x+(int)2)+" "+((int)y+(int)2)+" - "+((int)x+(int)1)+" "+((int)y+(int)1));
				Board b = this.clone();
				b.setPawn(x, y, Pawn.NULL);
				b.setPawn(x+1, y+1, Pawn.NULL);
				b.setPawn(x+2, y+2, Pawn.BLACK);
				captures.add(b);
				captures.addAll(b.getAvaibleCapturesForPawn(x+2, y+2));
			}

		}
		else if(getPawn(x, y)==Pawn.WHITEQUEEN)
		{
			for(int ix = -1; ix<=1;ix+=2)
				for(int iy = -1; iy<=1;iy+=2)
				{
					int limit = ((ix==1 ? 8-x : x-1)>(iy==1 ? 8-y : y-1)) ? (iy==1 ? 8-y : y-1) : (ix==1 ? 8-x : x-1);
					boolean captureNow = false;
					int captureX = 0;
					int captureY = 0;
					for(int i = 1; i<=limit; i++)
					{
						if(getPawn(x+i*ix, y+i*iy)==Pawn.NULL)
						{
							if(captureNow)
							{
								Board b = clone();
								b.setPawn(captureX, captureY, Pawn.NULL);
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x+i*ix, y+i*iy, Pawn.WHITEQUEEN);
								captures.add(b);
								captures.addAll(b.getAvaibleCapturesForPawn(x+i*ix, y+i*iy));
							}
						}
						else if(getPawn(x+i*ix, y+i*iy).getColor()==Color.WHITE)
						{
							break;
						}
						else 
						{
							if(captureNow)
							{
								break;
							}
							captureX = x+i*ix;
							captureY = y+i*iy;
							captureNow = true;
						}
					}
				}
		}
		
		else if(getPawn(x, y)==Pawn.BLACKQUEEN)
		{
			for(int ix = -1; ix<=1;ix+=2)
				for(int iy = -1; iy<=1;iy+=2)
				{
					int limit = ((ix==1 ? 8-x : x-1)>(iy==1 ? 8-y : y-1)) ? (iy==1 ? 8-y : y-1) : (ix==1 ? 8-x : x-1);
					boolean captureNow = false;
					int captureX = 0;
					int captureY = 0;
					for(int i = 1; i<=limit; i++)
					{
						if(getPawn(x+i*ix, y+i*iy)==Pawn.NULL)
						{
							if(captureNow)
							{
								Board b = clone();
								b.setPawn(captureX, captureY, Pawn.NULL);
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x+i*ix, y+i*iy, Pawn.BLACKQUEEN);
								captures.add(b);
								captures.addAll(b.getAvaibleCapturesForPawn(x+i*ix, y+i*iy));
							}
						}
						else if(getPawn(x+i*ix, y+i*iy).getColor()==Color.BLACK)
						{
							break;
						}
						else 
						{
							if(captureNow)
							{
								break;
							}
							captureX = x+i*ix;
							captureY = y+i*iy;
							captureNow = true;
						}
					}
				}
		}
		return captures;
	}
	public List<Board> getAvaibleMoves(Pawn.Color color)
	{
		List<Board> moves = new ArrayList<Board>();
		List<Board> captures = new ArrayList<Board>();
		for(int x = 1; x <= 8; x++)
			for(int y = 1; y <= 8; y++)
			{
				if(getPawn(x, y).getColor()==color)
				{
					if(getPawn(x, y)==Pawn.WHITE)
					{
						captures.addAll(getAvaibleCapturesForPawn(x, y));
						if(captures.size()==0)
						{
							//x+1, y+1
							if(x<8&&y<8&&getPawn(x+1, y+1)==Pawn.NULL)
							{
								Board b = this.clone();
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x+1, y+1, y==7 ? Pawn.WHITEQUEEN: Pawn.WHITE);
								moves.add(b);
							}
							//x-1, y+1
							if(x>1&&y<8&&getPawn(x-1, y+1)==Pawn.NULL)
							{
								Board b = this.clone();
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x-1, y+1, y==7 ? Pawn.WHITEQUEEN: Pawn.WHITE);
								moves.add(b);
							}
						}
					}
					else if(getPawn(x, y)==Pawn.BLACK)
					{
						captures.addAll(getAvaibleCapturesForPawn(x, y));
						if(captures.size()==0)
						{
							//x+1, y-1
							if(x<8&&y>1&&getPawn(x+1, y-1)==Pawn.NULL)
							{
//								System.out.println(x+" "+y+" -> "+((int)x+(int)1)+" "+((int)y-(int)1));
								Board b = this.clone();
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x+1, y-1, y==2 ? Pawn.BLACKQUEEN: Pawn.BLACK);
								moves.add(b);
							}
							//x-1, y-1
							if(x>1&&y>1&&getPawn(x-1, y-1)==Pawn.NULL)
							{
//								System.out.println(x+" "+y+" -> "+((int)x-(int)1)+" "+((int)y-(int)1));
								Board b = this.clone();
								b.setPawn(x, y, Pawn.NULL);
								b.setPawn(x-1, y-1, y==2 ? Pawn.BLACKQUEEN: Pawn.BLACK);
								moves.add(b);
							}
						}	
						
					}
					else if(getPawn(x, y)==Pawn.WHITEQUEEN)
					{
						for(int ix = -1; ix<=1;ix+=2)
						for(int iy = -1; iy<=1;iy+=2)
						{
							int limit = ((ix==1 ? 8-x : x-1)>(iy==1 ? 8-y : y-1)) ? (iy==1 ? 8-y : y-1) : (ix==1 ? 8-x : x-1);
							boolean captureNow = false;
							int captureX = 0;
							int captureY = 0;
							for(int i = 1; i<=limit; i++)
							{
								if(getPawn(x+i*ix, y+i*iy)==Pawn.NULL)
								{
									if(captureNow)
									{
										Board b = clone();
										b.setPawn(captureX, captureY, Pawn.NULL);
										b.setPawn(x, y, Pawn.NULL);
										b.setPawn(x+i*ix, y+i*iy, Pawn.WHITEQUEEN);
										captures.add(b);
										captures.addAll(b.getAvaibleCapturesForPawn(x+i*ix, y+i*iy));
									}
									else
									{
										Board b = clone();
										b.setPawn(x, y, Pawn.NULL);
										b.setPawn(x+i*ix, y+i*iy, Pawn.WHITEQUEEN);
										moves.add(b);
									}
								}
								else if(getPawn(x+i*ix, y+i*iy).getColor()==Color.WHITE)
								{
									break;
								}
								else 
								{
									if(captureNow)
									{
										break;
									}
									captureX = x+i*ix;
									captureY = y+i*iy;
									captureNow = true;
								}
							}
						}
					}
					
					else if(getPawn(x, y)==Pawn.BLACKQUEEN)
					{
						for(int ix = -1; ix<=1;ix+=2)
							for(int iy = -1; iy<=1;iy+=2)
							{
								int limit = ((ix==1 ? 8-x : x-1)>(iy==1 ? 8-y : y-1)) ? (iy==1 ? 8-y : y-1) : (ix==1 ? 8-x : x-1);
								boolean captureNow = false;
								int captureX = 0;
								int captureY = 0;
								for(int i = 1; i<=limit; i++)
								{
									if(getPawn(x+i*ix, y+i*iy)==Pawn.NULL)
									{
										if(captureNow)
										{
											Board b = clone();
											b.setPawn(captureX, captureY, Pawn.NULL);
											b.setPawn(x, y, Pawn.NULL);
											b.setPawn(x+i*ix, y+i*iy, Pawn.BLACKQUEEN);
											captures.add(b);
											captures.addAll(b.getAvaibleCapturesForPawn(x+i*ix, y+i*iy));
										}
										else
										{
											Board b = clone();
											b.setPawn(x, y, Pawn.NULL);
											b.setPawn(x+i*ix, y+i*iy, Pawn.BLACKQUEEN);
											moves.add(b);
										}
									}
									else if(getPawn(x+i*ix, y+i*iy).getColor()==Color.BLACK)
									{
										break;
									}
									else 
									{
										if(captureNow)
										{
											break;
										}
										captureX = x+i*ix;
										captureY = y+i*iy;
										captureNow = true;
									}
								}
							}
					}
				
				}
			}
		return captures.size()==0 ? moves : captures;
	}
	public List<Move>getMovesTo(Board b)
	{
		ArrayList<Move>moves = new ArrayList<Board.Move>();
		ArrayList<Point> blackRemove=new ArrayList<Point>();
		ArrayList<Point> blackAdd=new ArrayList<Point>();
		ArrayList<Point> whiteRemove=new ArrayList<Point>();
		ArrayList<Point> whiteAdd=new ArrayList<Point>();
		for(int x =1;x<=8;x++)
			for(int y =1;y<=8;y++)
			{
				if(getPawn(x, y)!=b.getPawn(x, y))
				{
					if(getPawn(x, y).getColor()==Color.WHITE)
					{
						whiteRemove.add(new Point(x, y));
					}
					else if(getPawn(x, y).getColor()==Color.BLACK)
					{
						blackRemove.add(new Point(x, y));
					}
					if(b.getPawn(x, y).getColor()==Color.WHITE)
					{
						whiteAdd.add(new Point(x, y));
					}
					else if(b.getPawn(x, y).getColor()==Color.BLACK)
					{
						blackAdd.add(new Point(x,y));
					}
				}
			}
		for(Point p : whiteRemove)
		{
			if(whiteAdd.isEmpty())
			{
				moves.add(new Move(true, false, p, null));
			}
			else
			{
				moves.add(new Move(false, getPawn(p.x, p.y)!=b.getPawn(whiteAdd.get(0).x,whiteAdd.get(0).y), p, whiteAdd.get(0)));
				whiteAdd.remove(0);
			}
		}
		for(Point p : blackRemove)
		{
			if(blackAdd.isEmpty())
			{
				moves.add(new Move(true, false, p, null));
			}
			else
			{
				moves.add(new Move(false, getPawn(p.x, p.y)!=b.getPawn(blackAdd.get(0).x,blackAdd.get(0).y), p, blackAdd.get(0)));
				blackAdd.remove(0);
			}
		}
		if(!(whiteAdd.isEmpty()&&blackAdd.isEmpty()))
		{
			throw new IllegalArgumentException("Too many pawns");
		}
		return moves;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(pawns);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		if (!Arrays.equals(pawns, other.pawns)) {
			return false;
		}
		return true;
	}
}