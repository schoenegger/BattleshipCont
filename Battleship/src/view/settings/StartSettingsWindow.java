package view.settings;

import gameSounds.GameSoundPlayer;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import logging.Logging;
import view.LogView;
import view.GlobalStrings.Definitions;
import view.GlobalStrings.LanguageView;
import view.listener.StartViewSettingsListener;
import Game.Logic;
import GameUtilities.GlobalValues;

public class StartSettingsWindow extends JDialog
{

	private StartViewSettingData startViewSettingsData;
	private LanguageView languageView;
	private GameSoundPlayer gameSoundPlayer;
	private Logic refGameLogic;
	private StartViewSettingsListener viewSettingsListener;

	private JFrame frmSettings;
	private JTextField txtIPAddress;
	private JLabel lblPort;
	private JLabel lblHost;
	private JLabel lblLevel;
	private JSpinner spinnerLevel;
	private JSpinner spinnerPort;
	private JLabel lblIpAddress;
	private JCheckBox chckbxNewCheckBox;
	private JComboBox<String> comboBox;
	private JLabel lblLanguage;
	private JButton btnSave;
	private JLabel lblHeadSettings;
	private JButton buttonShowLog;

	/**
	 * Create the application.
	 * 
	 * @wbp.parser.constructor
	 */
	public StartSettingsWindow(StartViewSettingData startSettData,
			LanguageView languageView, Logic refLogic)
	{
		this.languageView = languageView;
		startViewSettingsData = startSettData;
		this.refGameLogic = refLogic;
		this.viewSettingsListener = new StartViewSettingsListener(refGameLogic,
				this);
		this.gameSoundPlayer = refLogic.getgameSoundPlayer();
		// this.gameSoundPlayer = gameSoundPlayer;
		initialize();
	}

	private void changeColorHeaderLabel()
	{
		synchronized (DEFAULT_MODALITY_TYPE)
		{
			Thread thread = new Thread()
			{
				public void run()
				{
					int blue = 100;
					int green = 100;
					int red = 0;
					while (true)
					{
						lblHeadSettings.setForeground(new Color(red, green,
								blue));
						try
						{
							Thread.sleep(50);
							blue += 10;
							green++;
							if (blue > 245)
							{
								blue = 0;
							}
							if (green > 100)
							{
								green = 0;
							}

						}
						catch (InterruptedException e)
						{
							Logging.writeErrorMessage("Label Coloring StartView does not work");
						}
					}
				}
			};

			thread.start();
		}

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frmSettings = new JFrame();
		frmSettings.setIconImage(Toolkit.getDefaultToolkit().getImage(
				StartSettingsWindow.class.getResource("/img/settings-24.png")));
		frmSettings.setResizable(false);
		frmSettings.setTitle(languageView
				.getResourceString(LanguageView.SETTINGS));
		frmSettings.getContentPane().setBackground(SystemColor.activeCaption);
		frmSettings.getContentPane().setLayout(null);

		txtIPAddress = new JTextField();
		txtIPAddress.setText(startViewSettingsData.getIpAddress());
		txtIPAddress.setBounds(182, 79, 133, 27);
		frmSettings.getContentPane().add(txtIPAddress);
		txtIPAddress.setColumns(10);

		spinnerPort = new JSpinner();
		spinnerPort.setModel(new SpinnerNumberModel(8000, 8000, 8500, 1));
		spinnerPort.setBounds(182, 121, 63, 28);
		frmSettings.getContentPane().add(spinnerPort);
		spinnerPort.setValue(Integer.parseInt(startViewSettingsData.getPort()));

		lblIpAddress = new JLabel(
				languageView.getResourceString(LanguageView.IP_ADDRESS));
		lblIpAddress.setBounds(40, 71, 73, 35);
		frmSettings.getContentPane().add(lblIpAddress);

		lblPort = new JLabel(languageView.getResourceString(LanguageView.PORT));
		lblPort.setBounds(40, 109, 73, 35);
		frmSettings.getContentPane().add(lblPort);

		lblHost = new JLabel("Host");
		lblHost.setBounds(40, 155, 73, 35);
		frmSettings.getContentPane().add(lblHost);

		chckbxNewCheckBox = new JCheckBox("");
		if (startViewSettingsData.getMode().equals("host"))
		{
			chckbxNewCheckBox.setSelected(true);
		}
		else
		{
			chckbxNewCheckBox.setSelected(false);
		}
		chckbxNewCheckBox.setBounds(182, 166, 21, 23);
		frmSettings.getContentPane().add(chckbxNewCheckBox);

		lblLevel = new JLabel(
				languageView.getResourceString(LanguageView.LEVEL));
		lblLevel.setBounds(40, 208, 73, 35);
		frmSettings.getContentPane().add(lblLevel);

		spinnerLevel = new JSpinner();
		spinnerLevel.setModel(new SpinnerNumberModel(1, 1, 3, 1));
		spinnerLevel.setBounds(182, 211, 63, 28);
		frmSettings.getContentPane().add(spinnerLevel);

		lblLanguage = new JLabel(
				languageView.getResourceString(LanguageView.LANGUAGE));
		lblLanguage.setBounds(40, 255, 73, 35);
		frmSettings.getContentPane().add(lblLanguage);

		comboBox = new JComboBox<String>();
		comboBox.addItem("ENGLISH");
		comboBox.addItem("DEUTSCH");
		comboBox.setBounds(182, 264, 133, 26);

		if (this.startViewSettingsData.getLanguage().toLowerCase()
				.equals(LanguageView.ENGLISH.toLowerCase()))
			comboBox.setSelectedIndex(0);
		else
			comboBox.setSelectedIndex(1);
		frmSettings.getContentPane().add(comboBox);

		btnSave = new JButton(languageView.getResourceString(LanguageView.SAVE));
		btnSave.setBounds(38, 313, 100, 28);

		// gameSoundPlayer.setMnemonic(KeyEvent.VK_M);
		// gameSoundPlayer.setActionCommand(Definitions.TURN_MUSIC_ON_OFF);
		// Listener adds
		// gameSoundPlayer.addActionListener(this.viewSettingsListener);
		refGameLogic.getgameSoundPlayer().setMnemonic(KeyEvent.VK_M);
		refGameLogic.getgameSoundPlayer().setActionCommand(
				Definitions.TURN_MUSIC_ON_OFF);
		txtIPAddress.addKeyListener(this.viewSettingsListener);
		chckbxNewCheckBox.addKeyListener(this.viewSettingsListener);
		comboBox.addKeyListener(this.viewSettingsListener);

		refGameLogic.getgameSoundPlayer().addActionListener(
				this.viewSettingsListener);
		frmSettings.addWindowListener(viewSettingsListener);

		btnSave.setMnemonic(KeyEvent.VK_S);

		btnSave.addKeyListener(this.viewSettingsListener);
		btnSave.addActionListener(this.viewSettingsListener);
		btnSave.setActionCommand(Definitions.SAVE_BUTTON_START_SETTINGS_VIEW);
		frmSettings.getContentPane().add(refGameLogic.getgameSoundPlayer());
		frmSettings.getContentPane().add(btnSave);

		lblHeadSettings = new JLabel("SETTINGS");
		lblHeadSettings.setIcon(new ImageIcon(StartSettingsWindow.class
				.getResource("/img/settings-24.png")));
		lblHeadSettings.setFont(new Font("Segoe Script", Font.BOLD
				| Font.ITALIC, 26));
		lblHeadSettings.setBounds(93, 16, 200, 50);
		frmSettings.getContentPane().add(lblHeadSettings);

		buttonShowLog = new JButton("Show Log");
		buttonShowLog.setMnemonic(KeyEvent.VK_L);
		buttonShowLog.addActionListener(this.viewSettingsListener);
		buttonShowLog
				.setActionCommand(Definitions.SHOW_LOG_BUTTON_START_SETTINGS_VIEW);
		buttonShowLog.setBounds(182, 313, 100, 28);
		frmSettings.getContentPane().add(buttonShowLog);
		frmSettings.setBounds(100, 100, 387, 424);

		frmSettings.setVisible(true);
		btnSave.requestFocus();
		changeColorHeaderLabel();
	}

	public void saveSettingsIfValidAndWriteToFile()
	{
		if (checkIfSettingAreValid())
		{
			startViewSettingsData.setIpAddress(txtIPAddress.getText());
			startViewSettingsData.setPort(spinnerPort.getValue().toString());
			startViewSettingsData.setMode(getHostState());
			startViewSettingsData.setLanguage(comboBox.getSelectedItem()
					.toString());
			startViewSettingsData.writeSettingsToFile();
			Logging.writeInfoMessage("changed Start View Settings");
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Invalid Values",
					"Settings Values are not valid", JOptionPane.PLAIN_MESSAGE);
		}
	}

	protected boolean checkIfSettingAreValid()
	{
		if (!(txtIPAddress.getText().equals("localhost") || txtIPAddress
				.getText().trim().length() == GlobalValues.IP_ADDRESS_LENGHT))
		{
			return false;
		}
		if (!((int) spinnerPort.getValue() >= 8000 && (int) spinnerPort
				.getValue() <= 8500))
		{
			return false;
		}

		return true;
	}

	public boolean focusOnBtnSave()
	{
		return btnSave.isFocusOwner();
	}

	public void closeWindow()
	{
		frmSettings.dispose();
	}

	private String getHostState()
	{
		if (chckbxNewCheckBox.isSelected())
		{
			return "host";
		}
		else
		{
			return "client";
		}
	}

	public void showLogView()
	{
		LogView logView = new LogView();

	}
}
