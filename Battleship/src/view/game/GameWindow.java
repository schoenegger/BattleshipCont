package view.game;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import GameUtilities.Field.Field;

public class GameWindow extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel panel_1;
	private JFrame frmSettings;
	private JPanel drawPanel;
	private JTextField textField;
	private Field refOwnField;
	private Field refEnemyField;
	private volatile String nextMove = null;
	private String currFieldText = "";

	// private DrawingPanelGameFields drawingPanelGameField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public GameWindow()
	{
		// this.ownField = ownField;
		// this.enemyField = enemyField;
		initializeComponents();
		initGameField();
	}

	private void initGameField()
	{
		// TODO Auto-generated method stub

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
		{ 57, 90, 97, 127, 496, 86, 0 };
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
		gbc_btnExit.gridy = 1;
		panel_1.add(btnExit, gbc_btnExit);

		JButton btnAttack = new JButton("Attack");
		btnAttack.setMaximumSize(new Dimension(100, 30));
		GridBagConstraints gbc_btnAttack = new GridBagConstraints();
		gbc_btnAttack.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnAttack.insets = new Insets(0, 0, 5, 5);
		gbc_btnAttack.gridx = 2;
		gbc_btnAttack.gridy = 1;
		panel_1.add(btnAttack, gbc_btnAttack);

		textField = new JTextField();
		textField.setText("0;1");
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.anchor = GridBagConstraints.WEST;
		gbc_textField.gridx = 3;
		gbc_textField.gridy = 1;
		panel_1.add(textField, gbc_textField);
		textField.setColumns(10);

		JLabel lblMessages = new JLabel("Messages");
		GridBagConstraints gbc_lblMessages = new GridBagConstraints();
		gbc_lblMessages.insets = new Insets(0, 0, 5, 5);
		gbc_lblMessages.gridx = 4;
		gbc_lblMessages.gridy = 1;
		panel_1.add(lblMessages, gbc_lblMessages);

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
		}
		if (this.nextMove != null)
		{
			String currNextMove = this.nextMove;
			nextMove = null;
			return currNextMove;
		}
		return null;
	}

	public void refreshAttacTextField(String attacFieldText)
	{
		textField.setText(attacFieldText);
	}

	/************** default help functions ************/

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
}
