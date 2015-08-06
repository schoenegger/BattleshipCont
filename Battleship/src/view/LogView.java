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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logging.Logging;

public class LogView
{

	private JFrame frmLogFile;
	private JTextArea textArea;
	private JButton btnClearLog;
	private JButton btnSaveLog;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args)
	// {
	// EventQueue.invokeLater(new Runnable()
	// {
	// public void run()
	// {
	// try
	// {
	// LogView window = new LogView();
	// window.frame.setVisible(true);
	// }
	// catch (Exception e)
	// {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

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

		JButton btnSaveLog_1 = new JButton("Save Log");
		GridBagConstraints gbc_btnSaveLog_1 = new GridBagConstraints();
		gbc_btnSaveLog_1.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnSaveLog_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnSaveLog_1.gridx = 1;
		gbc_btnSaveLog_1.gridy = 0;
		frmLogFile.getContentPane().add(btnSaveLog_1, gbc_btnSaveLog_1);

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
}
