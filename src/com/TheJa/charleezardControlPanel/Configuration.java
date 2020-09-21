package com.TheJa.charleezardControlPanel;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.Set;

public class Configuration 
{
//	private static String x;
	private static List<String> recentConfigs = new ArrayList<String>();
	private static Properties props = new Properties();
	public static void load(InputStream is)
	{
		try 
		{
			props.clear();
			props.load(is);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static void load(String file) throws FileNotFoundException
	{
		load(new FileInputStream(file));
	}
	public static void clear()
	{
		props.clear();
	}
	public static void save(OutputStream os)
	{
		try 
		{
			props.store(os, "---CHARLEEZARD CONTROL PANEL CONFIGURATION---");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static void touchConfig(String file)
	{
		recentConfigs.remove(file);
		recentConfigs.add(file);
	}
	public static String getMostRecentConfig()
	{
		try
		{
			return recentConfigs.get(recentConfigs.size()-1);
		}
		catch(Exception e)
		{
			return "default.cfg";
		}
	}
	public static void save(String file) 
	{
		for(;;)
		{
			try 
			{
				save(new FileOutputStream(file));
				break;
			} 
			catch (FileNotFoundException e) 
			{
				mkConfig(file);
			}
		}
	}
	public static void mkConfig(String file)
	{
		try 
		{
			new File(file).createNewFile();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public static List<String> getRecentConfigs()
	{
		return recentConfigs;
	}
	public static void saveRecent()
	{
		try
		{
			if(!new File("recents.cfg").exists())
			{
				mkConfig("recents.cfg");
			}
			FileOutputStream fos = new FileOutputStream(new File("recents.cfg"));
			for(int i = 0; i<recentConfigs.size();i++)
			{
				for(char c : recentConfigs.get(i).toCharArray())
				{
					fos.write(c);
				}
				fos.write('\n');
			}
			fos.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void loadRecent()
	{

		try 
		{
			Scanner s = new Scanner(new File("recents.cfg"));
			recentConfigs.clear();
			while(s.hasNextLine())
			{
				recentConfigs.add(s.nextLine());
			}
			s.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	public static void set(String key, Object value)
	{
		if(value==null)
		{
			props.remove(key);
		}
		else
		{
			props.setProperty(key, value.toString());
		}
//		if(key.equals("robot.state.currentY")){
//		try
//		{
//			throw new Exception ();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//			System.out.println(value);
//		}
//	}
	}
	public static boolean getBoolean(String key)
	{
		return Boolean.parseBoolean(props.getProperty(key));
	}
	public static int getInt(String key)
	{
		return Integer.parseInt(props.getProperty(key));
	}
	public static double getDouble(String key)
	{
		return Double.parseDouble(props.getProperty(key));
	}
	public static String getString(String key)
	{
		if(props.containsKey(key))
		{
			return props.getProperty(key);
		}
		return "";
	}
	public static Set<String> getPropertyNames()
	{
		return props.stringPropertyNames();
	}
}
