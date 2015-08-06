package view;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import logging.Logging;

public class LogView
{

	private JFrame frmLogFile;
	private JTextArea textArea;
	private JPanel panel;
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

		JScrollPane scrollPane = new JScrollPane();

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);

		btnClearLog = new JButton("Clear Log");
		btnClearLog.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
				clearTextFromLogfile();
			}
		});

		JButton btnSaveLog = new JButton("Save Log");
		GroupLayout groupLayout = new GroupLayout(frmLogFile.getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																scrollPane,
																GroupLayout.PREFERRED_SIZE,
																784,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addComponent(
																				btnClearLog)
																		.addGap(10)
																		.addComponent(
																				btnSaveLog)))
										.addContainerGap(
												GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGroup(
										groupLayout
												.createParallelGroup(
														Alignment.BASELINE)
												.addComponent(btnSaveLog)
												.addComponent(btnClearLog))
								.addGap(9)
								.addComponent(scrollPane,
										GroupLayout.PREFERRED_SIZE, 456,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		panel = new JPanel();
		scrollPane.setColumnHeaderView(panel);
		panel.setLayout(null);
		frmLogFile.getContentPane().setLayout(groupLayout);
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
