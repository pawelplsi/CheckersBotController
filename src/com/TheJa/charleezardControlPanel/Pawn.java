package com.TheJa.charleezardControlPanel;

public enum Pawn 
{
	NULL(0,Color.NULL),WHITE(1,Color.WHITE),BLACK(2,Color.BLACK),WHITEQUEEN(3,Color.WHITE),BLACKQUEEN(4,Color.BLACK);
	public static enum Color
	{
		BLACK(-1), WHITE(1), NULL(0);
		private int idnt;
		private Color(int idnt)
		{
			this.idnt=idnt;
		}
		public Color getOposite()
		{
			return idnt==1 ? BLACK : WHITE ;
		}
	};
	private final byte id;
	private final Color c;
	private Pawn(int id, Color c)
	{
		this.id=(byte) id;
		this.c = c;
	}
	public byte getId()
	{
		return id;
	}
	public Pawn.Color getColor()
	{
		return c;
	}
	public static Pawn getById(byte id)
	{
		if(id==1)
			return WHITE;
		if(id==2)
			return BLACK;
		if(id==3)
			return WHITEQUEEN;
		if(id==4)
			return BLACKQUEEN;
		return NULL;
	}
}
