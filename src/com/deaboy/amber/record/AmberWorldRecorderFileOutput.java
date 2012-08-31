package com.deaboy.amber.record;

import java.io.*;

public class AmberWorldRecorderFileOutput implements Closeable
{
	private final String dirPath = "plugins/Amber/Recordings";
	private final String extension = ".awr"; // AWR = Amber World Recording.
	private final String filename;
	private final String fullpath;
	
	private File directory;
	private File file = null;
	
	private PrintWriter output = null;

	/**
	 * The class that handles file writing, creating, deleting,
	 * reading, etc.
	 * @param filename
	 */
	public AmberWorldRecorderFileOutput(String filename)
	{
		this.filename = filename;
		this.fullpath = dirPath + "/" + this.filename + extension;
		
		initializeDirectory();
	}

	private void initializeDirectory()
	{
		this.directory = new File(dirPath);
		if (!directory.exists() || !directory.isDirectory())
		{
			directory.mkdir();
		}
	}

	private void initializeFile()
	{
		this.file = new File(fullpath);
		if (file.exists())
		{
			file.delete();
		}
		
		try
		{
			file.createNewFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Opens the output file
	 * @throws IOException 
	 */
	public void open()
	{
		initializeFile();
		if (output == null)
		{
			try
			{
				output = new PrintWriter(new FileWriter(file));
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
		
	}

	/**
	 * Writes a new line to the output stream
	 */
	public void write(String s)
	{
		if (output != null)
		{
			output.println(s);
		}
	}

	/**
	 * Closes the output file
	 */
	@Override
	public void close()
	{
		if (output != null)
		{
			output.close();
		}
		file = null;
	}

}
