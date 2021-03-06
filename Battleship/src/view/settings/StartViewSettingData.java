package view.settings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import logging.Logging;

public class StartViewSettingData
{
	private String configFilePath = "cnf/configGame.txt";
	private String ipAddress;
	private String port;
	private String mode;
	private String level;
	private String language;

	public String getConfigFilePath()
	{
		return configFilePath;
	}

	public void setConfigFilePath(String configFilePath)
	{
		this.configFilePath = configFilePath;
	}

	public String getIpAddress()
	{
		return ipAddress;
	}

	public void setIpAddress(String ipAddress)
	{
		this.ipAddress = ipAddress;
	}

	public String getPort()
	{
		return port;
	}

	public void setPort(String port)
	{
		this.port = port;
	}

	/**
	 * Mode = Client, Host
	 * 
	 * @return
	 */
	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public String getLevel()
	{
		return level;
	}

	public void setLevel(String level)
	{
		this.level = level;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	/**
	 * Start ViewSettingData from file
	 */
	public StartViewSettingData()
	{
		loadSettingsFromFile();
	}

	/**
	 * load setting from txt file
	 */
	public void loadSettingsFromFile()
	{

		try
		{
			FileReader fr = new FileReader(configFilePath);
			BufferedReader br = new BufferedReader(fr);
			String readedLine = "";

			do
			{
				readedLine = br.readLine();
				if (readedLine != null)
				{
					allocateEntry(readedLine);
				}
			} while (readedLine != null);

			br.close();

		}
		catch (Exception e)
		{
			Logging.writeErrorMessage("StartViewSettings -> load from file is not possible");

		}

	}

	private void allocateEntry(String readedLine)
	{
		String[] entryData = readedLine.split(";");

		switch (entryData[0].trim())
		{
		case "ipAddress" :
			this.ipAddress = entryData[1].trim();
			break;
		case "port" :
			this.port = entryData[1].trim();
			break;
		case "mode" :
			this.mode = entryData[1].trim();
			break;
		case "level" :
			this.level = entryData[1].trim();
			break;
		case "language" :
			this.language = entryData[1].trim();
			break;
		default :
			break;
		}
	}

	/**
	 * Write setting to file
	 */
	public void writeSettingsToFile()
	{
		try
		{
			FileWriter fstream = new FileWriter(configFilePath, false);
			BufferedWriter out = new BufferedWriter(fstream);

			out.write("ipAddress" + ";" + this.getIpAddress() + "\n" + "port"
					+ ";" + this.getPort() + "\n" + "mode" + ";"
					+ this.getMode() + "\n" + "level;" + this.getLevel() + "\n"
					+ "language" + ";" + this.getLanguage() + "\n");

			out.close();
		}
		catch (Exception e)
		{
			Logging.writeErrorMessage("StartVieSettings -> Cannot write Start view Settings Data to File");

		}
	}
}
