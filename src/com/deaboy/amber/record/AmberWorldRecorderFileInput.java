package com.deaboy.amber.record;

import java.io.*;

public class AmberWorldRecorderFileInput implements Closeable
{
	private final String dirPath = "plugins/Amber/Recordings";
	private final String extension = ".awr"; // AWR = Amber World Recording.
	private final String filename;
	private final String fullpath;
	
	private File directory;
	private File file = null;
	
	private BufferedReader input = null;

	/**
	 * The class that handles file writing, creating, deleting,
	 * reading, etc.
	 * @param filename
	 */
	public AmberWorldRecorderFileInput(String filename)
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

	private void initializeFile() throws IOException
	{
		this.file = new File(fullpath);
		if (!file.exists())
		{
			throw new IOException();
		}
	}

	/**
	 * Opens the output file
	 * @throws IOException 
	 */
	public void open()
	{
		try
		{
			initializeFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}
		if (input == null)
		{
			try
			{
				input = new BufferedReader(new FileReader(file));
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
		
	}

	/**
	 * Reads the next line from the input stream
	 */
	public String read()
	{
		if (input != null)
		{
			try
			{
				String data = new String(input.readLine());
				System.gc();
				return data;
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	/**
	 * Closes the output file
	 */
	@Override
	public void close()
	{
		if (input != null)
		{
			try
			{
				input.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		file = null;
	}

}
