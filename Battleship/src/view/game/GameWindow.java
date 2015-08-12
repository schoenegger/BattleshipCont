package view.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.SystemColor;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import logging.Logging;
import net.miginfocom.swing.MigLayout;
import view.StartView;
import view.GlobalStrings.Definitions;
import view.listener.GameViewListener;
import Game.Logic;
import GameUtilities.Ship;
import GameUtilities.ShipPosition;
import GameUtilities.ShipType;
import GameUtilities.Field.Field;
import GameUtilities.Field.FieldState;

/**
 * Game Window
 * 
 * @author Thomas Schönegger
 * @version 1.0
 * 
 */
public class GameWindow extends JDialog
{
	private static final long serialVersionUID = 1L;

	private StartView refStartView;

	private JPanel panel_1;
	private JFrame frmSettings;
	private DrawingPanelGameFields drawPanel;
	private JTextField textField;
	private JLabel lblMessages;
	private JComboBox<String> comboBox;
	private JLabel lblXY;
	private JButton btnAttack;
	// private JButton btnExit;

	private Field refOwnField;
	private Field refEnemyField;
	// private volatile String nextMove = null;
	// private String currFieldText = "";
	private JRadioButton rdbtnVertical;
	private JRadioButton rdbtnHorizontal;
	private JButton btnSetShip;

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private Ship currShipToSet;

	private GameViewListener gameViewListener;

	private int shipCounter = 0;
	private JLabel lblYourTurn;

	/**
	 * Create the frame.
	 */
	public GameWindow(StartView refStartView, Logic refLogic)
	{
		try
		{
			UIManager
					.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		}
		catch (Exception e)
		{
			Logging.writeErrorMessage("Look And Feel nimbus in GameView not possible");
		}

		this.refEnemyField = new Field();
		this.refOwnField = new Field(true);
		this.refStartView = refStartView;
		this.gameViewListener = new GameViewListener(refLogic);
		initializeComponents();

	}

	private void initializeComponents()
	{

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

		frmSettings = new JFrame();
		frmSettings.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSettings.getContentPane().setBackground(new Color(135, 206, 235));
		frmSettings.setBackground(SystemColor.inactiveCaption);
		frmSettings.setMinimumSize(new Dimension(1500, 850));
		frmSettings.setResizable(true);
		frmSettings.setTitle("Game Field");

		frmSettings.getContentPane().setLayout(null);

		setBounds(100, 100, 1200, 735);
		frmSettings.getContentPane().setLayout(
				new MigLayout("", "[grow]",
						"[600.00:600.00,grow][21.00,baseline]"));

		drawPanel = new DrawingPanelGameFields(refOwnField, refEnemyField);
		drawPanel.setBackground(SystemColor.info);
		drawPanel.addMouseMotionListener(gameViewListener);
		drawPanel.addMouseListener(gameViewListener);
		drawPanel.revalidate();
		drawPanel.repaint();

		frmSettings.getContentPane().add(drawPanel, "cell 0 0,grow");

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 100, 0));
		frmSettings.getContentPane().add(panel_1, "cell 0 1,grow");
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]
		{ 57, 90, 97, 176, 496, 86, 0, 0, 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[]
		{ 16, 0, 23, 0 };
		gbl_panel_1.columnWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[]
		{ 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);

		// btnExit = new JButton("EXIT");
		// btnExit.setEnabled(false);
		// GridBagConstraints gbc_btnExit = new GridBagConstraints();
		// gbc_btnExit.anchor = GridBagConstraints.NORTHWEST;
		// gbc_btnExit.insets = new Insets(0, 0, 5, 5);
		// gbc_btnExit.gridx = 1;
		// gbc_btnExit.gridy = 0;
		// panel_1.add(btnExit, gbc_btnExit);

		rdbtnVertical = new JRadioButton("vertical");
		rdbtnVertical.setBackground(new Color(0, 100, 0));
		rdbtnVertical.setSelected(true);
		buttonGroup.add(rdbtnVertical);
		GridBagConstraints gbc_rdbtnVertical = new GridBagConstraints();
		gbc_rdbtnVertical.anchor = GridBagConstraints.WEST;
		gbc_rdbtnVertical.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnVertical.gridx = 2;
		gbc_rdbtnVertical.gridy = 0;
		panel_1.add(rdbtnVertical, gbc_rdbtnVertical);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel(new String[]
		{ "Destroyer", "AirCarrier", "YellowSubmarine" }));
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

		btnAttack = new JButton("Attack");
		btnAttack.setActionCommand(Definitions.BUTTON_ATTAC);
		btnAttack.addActionListener(this.gameViewListener);
		btnAttack.setEnabled(false);
		btnAttack.setVisible(false);
		btnAttack.setMaximumSize(new Dimension(100, 30));
		GridBagConstraints gbc_btnAttack = new GridBagConstraints();
		gbc_btnAttack.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAttack.insets = new Insets(0, 0, 5, 5);
		gbc_btnAttack.gridx = 1;
		gbc_btnAttack.gridy = 1;
		panel_1.add(btnAttack, gbc_btnAttack);

		rdbtnHorizontal = new JRadioButton("horizontal");
		rdbtnHorizontal.setBackground(new Color(0, 100, 0));
		buttonGroup.add(rdbtnHorizontal);
		GridBagConstraints gbc_rdbtnHorizontal = new GridBagConstraints();
		gbc_rdbtnHorizontal.anchor = GridBagConstraints.WEST;
		gbc_rdbtnHorizontal.insets = new Insets(0, 0, 5, 5);
		gbc_rdbtnHorizontal.gridx = 2;
		gbc_rdbtnHorizontal.gridy = 1;
		panel_1.add(rdbtnHorizontal, gbc_rdbtnHorizontal);

		lblMessages = new JLabel("INIT FIELD..........");
		lblMessages.setFont(new Font("Segoe Script", Font.BOLD, 16));
		GridBagConstraints gbc_lblMessages = new GridBagConstraints();
		gbc_lblMessages.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessages.gridx = 4;
		gbc_lblMessages.gridy = 1;
		panel_1.add(lblMessages, gbc_lblMessages);

		lblYourTurn = new JLabel("YOUR TURN");
		lblYourTurn.setVisible(false);
		lblYourTurn.setForeground(Color.RED);
		lblYourTurn.setFont(new Font("SansSerif", Font.BOLD, 13));
		GridBagConstraints gbc_lblYourTurn = new GridBagConstraints();
		gbc_lblYourTurn.insets = new Insets(0, 0, 5, 5);
		gbc_lblYourTurn.gridx = 8;
		gbc_lblYourTurn.gridy = 1;
		panel_1.add(lblYourTurn, gbc_lblYourTurn);

		textField = new JTextField();
		textField.setText("1,2");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 2;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);

		btnSetShip = new JButton("Set Ship");
		btnSetShip.setActionCommand(Definitions.BUTTON_SET_SHIP);
		btnSetShip.addActionListener(this.gameViewListener);

		GridBagConstraints gbc_btnSetShip = new GridBagConstraints();
		gbc_btnSetShip.anchor = GridBagConstraints.WEST;
		gbc_btnSetShip.insets = new Insets(0, 0, 0, 5);
		gbc_btnSetShip.gridx = 2;
		gbc_btnSetShip.gridy = 2;
		panel_1.add(btnSetShip, gbc_btnSetShip);

		lblXY = new JLabel("X:    Y:");
		GridBagConstraints gbc_lblXY = new GridBagConstraints();
		gbc_lblXY.anchor = GridBagConstraints.EAST;
		gbc_lblXY.gridx = 9;
		gbc_lblXY.gridy = 2;
		panel_1.add(lblXY, gbc_lblXY);

		frmSettings.revalidate();
		frmSettings.repaint();

		frmSettings.setVisible(true);
	}

	/**
	 * Creates a game3 view by game Fields
	 */
	public void createGameViewByGameFields()
	{
		drawPanel.revalidate();
		drawPanel.repaint();
	}

	public void getNextMove()
	{
		sendMessge("Please Attac your Enemy");
	}

	private void updateComboBox()
	{
		Vector<Ship> activeShipsInField = refOwnField.getListOfSetableShips();

		int counterAirCarr = 0;
		int counterDestr = 0;
		int counterYellSubmar = 0;

		for (Ship ship : activeShipsInField)
		{
			if (ship.getType() == ShipType.AIRCARRIER)
			{
				counterAirCarr++;
			}
			else if (ship.getType() == ShipType.DESTROYER)
			{
				counterDestr++;
			}
			else
			{
				counterYellSubmar++;
			}
		}

		for (int i = 0; i < comboBox.getItemCount(); i++)
		{
			String currItemString = comboBox.getItemAt(i).toString()
					.toLowerCase();

			if (currItemString.equals("destroyer") && counterDestr >= 2)
			{
				comboBox.removeItemAt(i);
			}
			else if (currItemString.equals("aircarrier") && counterAirCarr >= 2)
			{
				comboBox.removeItemAt(i);
			}
			else if (currItemString.equals("yellowsubmarine")
					&& counterYellSubmar >= 2)
			{
				comboBox.removeItemAt(i);
			}
		}
		comboBox.revalidate();
		refresh();

	}

	private void startGame()
	{
		refStartView.setInitFieldInLogic(this.refOwnField);
		this.disableInitButtonsAndEnableAttacButtons();
	}

	private void disableInitButtonsAndEnableAttacButtons()
	{
		comboBox.setVisible(false);
		rdbtnHorizontal.setVisible(false);
		rdbtnVertical.setVisible(false);
		btnSetShip.setVisible(false);
		btnAttack.setEnabled(true);
		btnAttack.setVisible(true);
		// btnExit.setEnabled(true);
	}

	private void setShipToField()
	{
		refOwnField.setShipOnField(currShipToSet);
		refresh();
	}

	private boolean checkIfPositionIsAvailableAndCreateCurrentShip(int x,
			int y, String shipFromCombobox)
	{
		boolean posAvailable = true;

		currShipToSet = createShipByPositionAndName(x, y, shipFromCombobox);
		posAvailable &= refOwnField.checkIfPositionIsInField(x, y);
		posAvailable &= refOwnField.canSetShip(currShipToSet);

		return posAvailable;
	}

	private boolean checkIfPositionTextIsValid(String positionText)
	{
		boolean isValid = true;

		if (positionText.length() > 3)
			isValid = false;

		String[] position = positionText.split(",");

		if (position.length < 2)
			isValid = false;

		int posx = 0;
		int posy = 0;

		try
		{
			posx = Integer.parseInt(position[0]);
			posy = Integer.parseInt(position[1]);
		}
		catch (NumberFormatException e)
		{
			isValid = false;
		}

		isValid &= (posx < 10) && (posx >= 0) && (posy < 10) && (posy >= 0);
		if (posx >= 0 && posy >= 0) // no negative Index allowed
		{
			isValid &= (refEnemyField.getFieldElement(posx, posy)
					.getFieldState() == FieldState.UNKNOWN);
		}

		if (!isValid)
		{
			writeWrongMoveMessage();
		}

		return true;
	}

	private String getAlignmentFromRdButton()
	{
		String align;
		if (rdbtnHorizontal.isSelected())
			align = "horizontal";
		else
			align = "vertical";

		return align;
	}

	private ShipType getShipTypeFromComboBox()
	{
		if (comboBox.getItemCount() > 0)
		{
			String itemCombBox = this.comboBox.getSelectedItem().toString();

			switch (itemCombBox.toLowerCase())
			{
			case "destroyer" :
				return ShipType.DESTROYER;

			case "aircarrier" :
				return ShipType.AIRCARRIER;

			case "yellowsubmarine" :
				return ShipType.YELLOW_SUBMARINE;

			default :
				return ShipType.AIRCARRIER;

			}
		}
		return ShipType.AIRCARRIER;
	}

	private Ship createShipByPositionAndName(int x, int y,
			String shipFromCombobox)
	{
		String shipAlignment = "";

		shipAlignment = getAlignmentFromRdButton();

		ShipPosition shipPos = new ShipPosition(new Point(x, y), shipAlignment);

		ShipType shipType;
		if (shipFromCombobox.equals("Destroyer"))
		{
			shipType = ShipType.DESTROYER;
		}
		else if (shipFromCombobox.equals("AirCarrier"))
		{
			shipType = ShipType.AIRCARRIER;
		}
		else
		{
			shipType = ShipType.YELLOW_SUBMARINE;
		}

		Ship ship = new Ship(shipPos, shipType, ++shipCounter);

		return ship;
	}

	public void setEnemyField(Field enemyField)
	{
		drawPanel.setEnemyField(enemyField);
		refEnemyField = enemyField;
	}

	// ************ Called from Listener*******************

	public void attacShipButtonPressed()
	{
		String attackCommand = textField.getText();
		if (checkIfPositionTextIsValid(attackCommand))
		{
			String[] points = attackCommand.split(",");
			int x;
			int y;

			try
			{
				x = Integer.parseInt(points[0]);
				y = Integer.parseInt(points[1]);

				if (refEnemyField.IsValidAttacPosition(x, y))
				{
					// setYourTurnLabel(false);
					refStartView.sendAttackCommandToEnemy(attackCommand);
				}
				else
				{
					writeWrongAttacMessage();
				}
			}
			catch (NumberFormatException e)
			{
				Logging.writeErrorMessage("Game Window -> no valid Input");
				writeWrongAttacMessage();
			}
		}
	}

	public void setShipButtonPressed()
	{
		try
		{
			if (comboBox.getItemCount() > 0)
			{
				if (checkIfPositionTextIsValid(textField.getText().trim()))
				{
					String[] points = textField.getText().split(",");
					int x = Integer.parseInt(points[0]);
					int y = Integer.parseInt(points[1]);

					if (checkIfPositionIsAvailableAndCreateCurrentShip(x, y,
							comboBox.getSelectedItem().toString()))
					{
						setShipToField();
						updateComboBox();
					}
					else
					{
						writeWrongMoveMessage();
					}
				}
				else
				{
					writeWrongMoveMessage();
				}
			}
		}
		catch (NumberFormatException e)
		{
			Logging.writeInfoMessage("Game Window -> Not valid input To Set Ship");
			writeWrongMoveMessage();
		}

		if (refOwnField.isFieldInit())
		{
			startGame();
		}
	}

	public void refreshByMouseMove(int x, int y)
	{
		String align;
		this.lblXY.setText("X: " + x + " Y: " + y);
		this.lblXY.revalidate();

		align = getAlignmentFromRdButton();

		this.drawPanel.setMouseCourser(x, y, getShipTypeFromComboBox(), align);
		this.drawPanel.repaint();
		refresh();
	}

	public void MouseClickToGameView()
	{
		textField.setText(this.drawPanel.getCurrentMousePosition());
	}

	// ******** Messages *********************************

	private void writeWrongMoveMessage()
	{
		textField.setText("-1,0");
		JOptionPane.showMessageDialog(null,
				"Wrong Attac Command, Command must be written like this : 0,1",
				"Not Valid Input", JOptionPane.OK_CANCEL_OPTION);
	}

	private void writeWrongAttacMessage()
	{
		textField.setText("-1,0");
		JOptionPane.showMessageDialog(null,
				"Wrong Attac Position, Position is not attackable",
				"Not Valid Position", JOptionPane.OK_CANCEL_OPTION);
	}

	public void sendMessge(String message)
	{
		lblMessages.setText(message);
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			Logging.writeErrorMessage("GameWindow -> wait send message not possible");
		}

		lblMessages.repaint();

		lblMessages.revalidate();

		refresh();
	}

	// *********************Refresh**************************

	private void refresh()
	{
		frmSettings.repaint();
		frmSettings.revalidate();
	}

	public void refreshAttacTextField(String attacFieldText)
	{
		textField.getText();
		this.textField.setText(attacFieldText);
	}

	public void displayGameOver()
	{
		this.btnAttack.setEnabled(false);
		this.btnAttack.setVisible(false);

		sendMessge("Game Over");
		JOptionPane.showMessageDialog(null, "Game Over", "Game Over",
				JOptionPane.OK_CANCEL_OPTION);

		this.setVisible(false);
	}

}
