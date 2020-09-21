package com.TheJa.charleezardControlPanel;

import java.util.ArrayList;
import java.util.List;

import com.TheJa.charleezardControlPanel.Pawn.Color;

public abstract class RateBasedBot extends Bot
{
	@Override
	public Board getMove(Board b, Color color)
	{	
		ArrayList<Board> bestMoves = new ArrayList<Board>();
		double bestRating = Integer.MIN_VALUE;
		double bestAvgRating = Double.MIN_VALUE;
		for(Board bb : b.getAvaibleMoves(color))
		{
			double rating = rate(bb, Configuration.getInt( "bot."+getName().replaceAll(" ", "")+".howDeep"), false, color);
			double avgrating = rateAvg(bb, Configuration.getInt("bot."+getName().replaceAll(" ", "")+".howDeep"), false, color);
			if(rating>bestRating||(rating==bestRating&&avgrating>bestAvgRating))
			{
				bestMoves.clear();
				bestMoves.add(bb);
				bestRating=rating;
				bestAvgRating=avgrating;
			}
			else if(rating==bestRating&&avgrating==bestAvgRating)
			{
				bestMoves.add(bb);
			}
		}
		int r  = (int) Math.round(Math.random()*(float) (bestMoves.size()-1));
		return bestMoves.get(r);
	}
	public abstract double rate(Board b, int howDeep, boolean myTurn, Color color);
	public double rateAvg(Board b, int howDeep, boolean myTurn, Color color)
	{
		double total = 0;
		if(myTurn)
		{
			List<Board> moves = b.getAvaibleMoves(color);
			for(Board b1 : moves)
			{
				total += rate(b1,howDeep-1, !myTurn, color);
			}
			total/=moves.size();
		}
		else
		{
			List<Board> moves = b.getAvaibleMoves(color.getOposite());
			for(Board b1 : moves)
			{
				total += rate(b1,howDeep-1, !myTurn, color);
			}
			total/=moves.size();
		}
		return total;
	}
}
