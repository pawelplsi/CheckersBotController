package com.TheJa.charleezardControlPanel;

import java.util.List;

import com.TheJa.charleezardControlPanel.Pawn.Color;

public class ProBot extends RateBasedBot {

	@Override
	public double rate(Board b, int howDeep, boolean myTurn, Color color) 
	{
		if(howDeep<=1)
		{
			int wp = 0;
			int bp = 0;
			if(myTurn)
			{
				if(b.getAvaibleMoves(color).size()==0)
					bp += Configuration.getInt("bot.ProBot.winPoints");
			}
			else
			{
				if(b.getAvaibleMoves(color.getOposite()).size()==0)
					wp += Configuration.getInt("bot.ProBot.winPoints");
			}
			Pawn p;
			for(int x = 1; x <= 8; x++)
				for(int y = 1; y <=8; y++)
				{
					p = b.getPawn(x, y);
					if(p==Pawn.WHITE)
					{
						wp+=Configuration.getInt("bot.ProBot.pawnPoints");
						wp+=x*Configuration.getInt("bot.ProBot.pawnLocationPoints");
					}
					else if(p==Pawn.BLACK)
					{
						bp+=Configuration.getInt("bot.ProBot.pawnPoints");
						bp+=(9-x)*Configuration.getInt("bot.ProBot.pawnLocationPoints");
					}
					else if(p==Pawn.WHITEQUEEN)
					{
						wp+=Configuration.getInt("bot.ProBot.queenPoints");
					}
					else if(p==Pawn.BLACKQUEEN)
					{
						bp+=Configuration.getInt("bot.ProBot.queenPoints");
					}
				}
			if(color==Pawn.Color.WHITE)
			{
				return 1000*wp/(wp+bp);
			}
			return 1000*bp/(wp+bp);
		}
		double total;
		double tmp;
		List<Board> moves;
		if(myTurn)
		{
			total = Integer.MIN_VALUE;
			moves = b.getAvaibleMoves(color);
			for(Board b1 : moves)
			{
				tmp = rate(b1, howDeep-1, !myTurn, color);
				if(total<tmp)
				{
					total = tmp;
				}
			}
		}
		else
		{
			total = Integer.MAX_VALUE;
			moves = b.getAvaibleMoves(color.getOposite());
			for(Board b1 : moves)
			{
				tmp = rate(b1,howDeep-1, !myTurn, color);
				if(total>tmp)
				{
					total = tmp;
				}
			}
		}
		return total;
	}

	@Override
	public String getName() 
	{
		return "ProBot";
	}

}
