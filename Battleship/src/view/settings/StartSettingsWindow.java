package view.settings;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import view.listener.StartViewSettingsListener;
import GameUtilities.GlobalValues;

public class StartSettingsWindow extends JDialog
{

	private StartViewSettingData startViewSettingsData;
	private JFrame frmSettings;
	private JTextField txtIPAddress;
	private JLabel lblPort;
	private JLabel lblHost;
	private JLabel lblLevel;
	private JSpinner spinnerLevel;
	private JSpinner spinnerPort;
	private JLabel lblIpAddress;
	private JCheckBox chckbxNewCheckBox;
	private JLabel lblLanguage;
	private JButton btnSave;
	private StartViewSettingsListener viewSettListener;

	/**
	 * Create the application.
	 */
	public StartSettingsWindow(StartViewSettingData startSettData)
	{
		this.viewSettListener = new StartViewSettingsListener();
		startViewSettingsData = startSettData;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		frmSettings = new JFrame();
		frmSettings.setResizable(false);
		frmSettings.setTitle("SETTINGS");
		frmSettings.getContentPane().setBackground(SystemColor.activeCaption);
		frmSettings.getContentPane().setLayout(null);

		txtIPAddress = new JTextField();
		txtIPAddress.setText(startViewSettingsData.getIpAddress());
		txtIPAddress.setBounds(149, 7, 133, 27);
		frmSettings.getContentPane().add(txtIPAddress);
		txtIPAddress.setColumns(10);

		spinnerPort = new JSpinner();
		spinnerPort.setModel(new SpinnerNumberModel(8000, 8000, 8500, 1));
		spinnerPort.setBounds(149, 49, 63, 28);
		frmSettings.getContentPane().add(spinnerPort);
		spinnerPort.setValue(2);

		lblIpAddress = new JLabel("IP Address");
		lblIpAddress.setBounds(27, 4, 73, 35);
		frmSettings.getContentPane().add(lblIpAddress);

		lblPort = new JLabel("Port");
		lblPort.setBounds(27, 42, 73, 35);
		frmSettings.getContentPane().add(lblPort);

		lblHost = new JLabel("Host");
		lblHost.setBounds(27, 83, 73, 35);
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
		chckbxNewCheckBox.setBounds(149, 95, 21, 23);
		frmSettings.getContentPane().add(chckbxNewCheckBox);

		lblLevel = new JLabel("LEVEL");
		lblLevel.setBounds(27, 132, 73, 35);
		frmSettings.getContentPane().add(lblLevel);

		spinnerLevel = new JSpinner();
		spinnerLevel.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinnerLevel.setBounds(149, 139, 63, 28);
		frmSettings.getContentPane().add(spinnerLevel);

		lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(27, 188, 73, 35);
		frmSettings.getContentPane().add(lblLanguage);

		JComboBox<String> comboBox = new JComboBox();
		comboBox.addItem("ENGLISH");
		comboBox.addItem("GERMAN");
		comboBox.setBounds(149, 192, 133, 26);
		frmSettings.getContentPane().add(comboBox);

		btnSave = new JButton("SAVE");
		btnSave.setBounds(27, 241, 90, 28);
		btnSave.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				if (checkIfSettingAreValid())
				{
					startViewSettingsData.setIpAddress(txtIPAddress.getText());
					startViewSettingsData.setPort(spinnerPort.getValue()
							.toString());
					startViewSettingsData.setMode(getHostState());
					startViewSettingsData.writeSettingsToFile();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Invalid Values",
							"Settings Values are not valid",
							JOptionPane.PLAIN_MESSAGE);
				}
			}

			private boolean checkIfSettingAreValid()
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
		});
		frmSettings.getContentPane().add(btnSave);
		frmSettings.setBounds(100, 100, 354, 303);

		frmSettings.setVisible(true);
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

}