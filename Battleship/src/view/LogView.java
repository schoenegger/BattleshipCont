package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logging.Logging;

public class LogView
{

	private JFrame frmLogFile;
	private JTextArea textArea;
	private JButton btnClearLog;
	private JButton btnSaveLog;
	private String filePath;

	/**
	 * Create the application.
	 */
	public LogView()
	{
		initialize();
		readTextFromLogfile();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		frmLogFile = new JFrame();
		frmLogFile.getContentPane().setBackground(SystemColor.activeCaption);
		frmLogFile.setTitle("LOG FILE");
		frmLogFile.setBounds(100, 100, 800, 526);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]
		{ 87, 687, 0 };
		gridBagLayout.rowHeights = new int[]
		{ 23, 456, 0 };
		gridBagLayout.columnWeights = new double[]
		{ 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[]
		{ 0.0, 1.0, Double.MIN_VALUE };
		frmLogFile.getContentPane().setLayout(gridBagLayout);

		btnClearLog = new JButton("Clear Log");
		btnClearLog.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				clearTextFromLogfile();
			}
		});
		GridBagConstraints gbc_btnClearLog = new GridBagConstraints();
		gbc_btnClearLog.anchor = GridBagConstraints.NORTHEAST;
		gbc_btnClearLog.insets = new Insets(0, 0, 5, 5);
		gbc_btnClearLog.gridx = 0;
		gbc_btnClearLog.gridy = 0;
		frmLogFile.getContentPane().add(btnClearLog, gbc_btnClearLog);

		btnSaveLog = new JButton("Save Log");
		btnSaveLog.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveTextFromLogfile();
			}
		});
		GridBagConstraints gbc_btnSaveLog = new GridBagConstraints();
		gbc_btnSaveLog.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSaveLog.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveLog.gridx = 1;
		gbc_btnSaveLog.gridy = 0;
		frmLogFile.getContentPane().add(btnSaveLog, gbc_btnSaveLog);

		JScrollPane scrollPane = new JScrollPane();

		textArea = new JTextArea();
		textArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridwidth = 2;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		frmLogFile.getContentPane().add(scrollPane, gbc_scrollPane);
		frmLogFile.setVisible(true);
	}

	private void readTextFromLogfile()
	{
		try
		{
			FileReader reader = new FileReader("log/logfile.log");
			BufferedReader br = new BufferedReader(reader);
			textArea.read(br, null);
			br.close();
			textArea.requestFocus();
		}
		catch (Exception exception)
		{
			Logging.writeErrorMessage("LogView -> cannot read from Textfile");
		}
	}

	private void clearTextFromLogfile()
	{
		try
		{
			File outputFile = new File("log/logfile.log");
			FileWriter fw = new FileWriter(outputFile, false); // true für
																// append!
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("");
			bw.close();
			readTextFromLogfile();
		}
		catch (Exception exception)
		{
			Logging.writeErrorMessage("LogView -> cannot clear Logfile");
		}
	}

	private void saveTextFromLogfile()
	{
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showDialog(null, "Attach");

		if (checkCreateFile(fc.getSelectedFile().getPath()))
		{
			writeLogToFile();
		}
	}

	/**
	 * Check if Create Textfile is possible
	 * 
	 * @param filePath
	 * @return
	 */
	private boolean checkCreateFile(String filePath)
	{
		File fullFilePath = new File(filePath);

		if (fullFilePath.exists())
		{
			int result = JOptionPane.showConfirmDialog(null,
					"File arready exists ... Overwrite?");
			if (result == JOptionPane.YES_OPTION)
			{
				this.filePath = filePath;
				return true;
			}
			return false;
		}
		else
		{
			this.filePath = filePath;
			Logging.writeInfoMessage("Controller create CSV File because path does not exists");
			return true;
		}
	}

	private void writeLogToFile()
	{
		File file = new File(this.filePath + ".log");

		FileWriter fw;
		try
		{
			fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(textArea.getText());
			bw.close();
		}
		catch (IOException e)
		{
			Logging.writeErrorMessage("LogView -> Not Possible to Write File");
		}

	}
}
