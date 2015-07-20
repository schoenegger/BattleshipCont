package view.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import view.StartView;
import GameUtilities.Field.Field;

public class GameWindow extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private StartView refStartView;
	private JPanel panel_1;
	private JFrame frmSettings;
	private JPanel drawPanel;
	private JTextField textField;
	private Field refOwnField;
	private Field refEnemyField;
	private volatile String nextMove = null;
	private String currFieldText = "";
	private JRadioButton rdbtnVertical;
	private JRadioButton rdbtnHorizontal;
	private JButton btnSetShip;
	private JComboBox comboBox;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	// private DrawingPanelGameFields drawingPanelGameField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public GameWindow(StartView refStartView)
	{
		// this.ownField = ownField;
		// this.enemyField = enemyField;
		this.refStartView = refStartView;
		initializeComponents();
		initGameField();
	}

	private void initGameField()
	{
		Field refOwnField = new Field();

	}

	private void initializeComponents()
	{

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		frmSettings = new JFrame();
		frmSettings.setMinimumSize(new Dimension(1200, 800));
		frmSettings.setResizable(true);
		frmSettings.setTitle("Game Field");
		// frmSettings.getContentPane().setBackground(SystemColor.activeCaption);
		frmSettings.getContentPane().setLayout(null);

		setBounds(100, 100, 1200, 735);
		frmSettings.getContentPane().setLayout(
				new MigLayout("", "[grow]",
						"[600.00:600.00,grow][21.00,baseline]"));

		drawPanel = new DrawingPanelGameFields(refOwnField, refEnemyField);
		drawPanel.setBackground(SystemColor.info);
		drawPanel.revalidate();
		drawPanel.repaint();

		frmSettings.getContentPane().add(drawPanel, "cell 0 0,grow");

		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		frmSettings.getContentPane().add(panel_1, "cell 0 1,grow");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]
		{ 57, 90, 97, 176, 496, 86, 0 };
		gbl_panel_1.rowHeights = new int[]
		{ 16, 0, 23, 0 };
		gbl_panel_1.columnWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		JButton btnExit = new JButton("EXIT");
		GridBagConstraints gbc_btnExit = new GridBagConstraints();
		gbc_btnExit.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		gbc_btnExit.gridx = 1;
		gbc_btnExit.gridy = 0;
		panel_1.add(btnExit, gbc_btnExit);

		rdbtnVertical = new JRadioButton("vertical");
		rdbtnVertical.setSelected(true);
		buttonGroup.add(rdbtnVertical);
		GridBagConstraints gbc_rdbtnVertical = new GridBagConstraints();
		gbc_rdbtnVertical.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnVertical.gridx = 2;
		gbc_rdbtnVertical.gridy = 0;
		panel_1.add(rdbtnVertical, gbc_rdbtnVertical);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[]
		{ "Destroyer", "Air Carrier", "Yellow Submarine" }));
		comboBox.setToolTipText("ShipType");
		comboBox.setEditable(true);
		comboBox.setMinimumSize(new Dimension(20, 20));
		comboBox.setPreferredSize(new Dimension(20, 20));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.gridx = 3;
		gbc_comboBox.gridy = 0;
		panel_1.add(comboBox, gbc_comboBox);

		JButton btnAttack = new JButton("Attack");
		btnAttack.setMaximumSize(new Dimension(100, 30));
		GridBagConstraints gbc_btnAttack = new GridBagConstraints();
		gbc_btnAttack.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAttack.insets = new Insets(0, 0, 5, 5);
		gbc_btnAttack.gridx = 1;
		gbc_btnAttack.gridy = 1;
		panel_1.add(btnAttack, gbc_btnAttack);

		rdbtnHorizontal = new JRadioButton("horizontal");
		buttonGroup.add(rdbtnHorizontal);
		GridBagConstraints gbc_rdbtnHorizontal = new GridBagConstraints();
		gbc_rdbtnHorizontal.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHorizontal.gridx = 2;
		gbc_rdbtnHorizontal.gridy = 1;
		panel_1.add(rdbtnHorizontal, gbc_rdbtnHorizontal);

		JLabel lblMessages = new JLabel("Messages");
		GridBagConstraints gbc_lblMessages = new GridBagConstraints();
		gbc_lblMessages.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessages.gridx = 4;
		gbc_lblMessages.gridy = 1;
		panel_1.add(lblMessages, gbc_lblMessages);

		textField = new JTextField();
		textField.setText("0;1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);

		btnSetShip = new JButton("Set Ship");
		GridBagConstraints gbc_btnSetShip = new GridBagConstraints();
		gbc_btnSetShip.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetShip.gridx = 2;
		gbc_btnSetShip.gridy = 2;
		panel_1.add(btnSetShip, gbc_btnSetShip);

		frmSettings.revalidate();
		frmSettings.repaint();

		frmSettings.setVisible(true);
	}

	// *****************Functions called by Logic*******************

	public void createGameViewByGameFields()
	{
		drawPanel.revalidate();
		drawPanel.repaint();
	}

	public String getNextMove()
	{
		while (this.nextMove == null)
		{
			wait(500);
			sendMessge("Make your next Move");
		}
		if (this.nextMove != null)
		{
			String currNextMove = this.nextMove;
			nextMove = null;
			return currNextMove;
		}
		return null;
	}

	public void setShipButtonPressed()
	{
		if (checkIfPositionTextIsValid(textField.getText()))
		{
			if (checkIfPositionIsAvailable())
			{
				String[] points = textField.getText().split(",");
				int x = Integer.parseInt(points[0]);
				int y = Integer.parseInt(points[1]);

				setShipToField(this.comboBox.getSelectedItem().toString(), x, y);
			}
		}

		if (refOwnField.isFieldInit())
		{
			startGame();
		}
	}

	private void startGame()
	{
		refStartView.buildConnection();
		refStartView.setInitFieldInLogic(this.refOwnField);
		this.disableInitButtons();
	}

	private void disableInitButtons()
	{
		comboBox.setVisible(false);
		rdbtnHorizontal.setVisible(false);
		rdbtnVertical.setVisible(false);
	}

	private void setShipToField(String type, int x, int y)
	{

	}

	private boolean checkIfPositionIsAvailable()
	{
		// TODO Auto-generated method stub
		return false;
	}

	public void refreshAttacTextField(String attacFieldText)
	{
		textField.getText();
		this.textField.setText(attacFieldText);
	}

	/************** default help functions ************/

	private boolean checkIfPositionTextIsValid(String positionText)
	{
		return true;
	}

	private void wait(int ms)
	{
		try
		{
			Thread.sleep(ms);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendMessge(String message)
	{
		textField.setText(message);
		textField.revalidate();
	}
}
