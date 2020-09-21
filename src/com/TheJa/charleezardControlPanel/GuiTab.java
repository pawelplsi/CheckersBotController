package com.TheJa.charleezardControlPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

import org.jfree.ui.KeyedComboBoxModel;

public abstract class GuiTab extends GuiComponent 
{
	public GuiTab(ControlPanelGUI f) 
	{
		super(f);
	}
	private String name;

	public String getName()
	{
		return name;
	}
}