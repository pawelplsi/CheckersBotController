package com.TheJa.charleezardControlPanel;

import com.TheJa.charleezardControlPanel.Pawn.Color;

public abstract class Bot 
{
	public abstract Board getMove(Board b,Color c);
	public abstract String getName();
}
