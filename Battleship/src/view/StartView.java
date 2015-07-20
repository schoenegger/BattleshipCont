package view;

import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.GlobalStrings.Definitions;
import view.GlobalStrings.LanguageView;
import view.game.GameWindow;
import view.listener.StartViewButtonListener;
import view.settings.StartSettingsWindow;
import view.settings.StartViewSettingData;
import Game.Logic;
import GameUtilities.Field.Field;

public class StartView extends JDialog
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logic refGameLogic;
	private StartViewButtonListener viewButtListener;
	private GameWindow gameWindow;
	private StartSettingsWindow startSettWindow;
	private StartViewSettingData startSettData;

	private LanguageView languageView;

	private JFrame frmBattleshipCommander;

	private JButton btnPlayerCom;
	private JButton btn2Player;
	private JButton btnSettings;
	private JLabel label;

	/**
	 * Create the application.
	 */
	public StartView(Logic refGameLogic)// , StartViewSettingData startSettData
	{
		this.startSettData = new StartViewSettingData();
		this.refGameLogic = refGameLogic;

		this.languageView = new LanguageView(startSettData.getLanguage());

		initializeListeners();
		initializeComponets();
	}

	private void initializeListeners()
	{
		this.viewButtListener = new StartViewButtonListener(refGameLogic);
	}

	private void initializeComponets()
	{
		frmBattleshipCommander = new JFrame();
		frmBattleshipCommander.setIconImage(Toolkit.getDefaultToolkit()
				.getImage(StartView.class.getResource("/img/ship.png")));
		frmBattleshipCommander.getContentPane().setBackground(
				SystemColor.activeCaption);
		frmBattleshipCommander.setResizable(false);
		frmBattleshipCommander.setTitle("BATTLESHIP COMMANDER");
		frmBattleshipCommander.setBounds(100, 100, 339, 286);
		frmBattleshipCommander.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmBattleshipCommander.getContentPane().setLayout(null);
		this.btnPlayerCom = new JButton(
				languageView.getResourceString(LanguageView.PLAYER_VS_CPU));
		btnPlayerCom.setToolTipText("Alt+C");

		btnPlayerCom.addActionListener(this.viewButtListener);
		btnPlayerCom.addKeyListener(this.viewButtListener);
		btnPlayerCom.setMnemonic(KeyEvent.VK_C);
		btnPlayerCom.setBounds(171, 0, 162, 42);
		btnPlayerCom.setActionCommand(Definitions.PLAYER_VS_COM);
		frmBattleshipCommander.getContentPane().add(this.btnPlayerCom);
		this.btn2Player = new JButton(
				languageView.getResourceString(LanguageView.PLAYER_VS_PLAYER));
		btn2Player.setToolTipText("Alt+P");
		btn2Player.setBounds(0, 0, 162, 42);
		btn2Player.setActionCommand(Definitions.PLAYER_VS_PLAYER);

		// // add didi Activate when player vs. player is implements!
		// btn2Player.addActionListener(this.viewButtListener);
		// btn2Player.addKeyListener(this.viewButtListener);
		// btn2Player.setMnemonic(KeyEvent.VK_P);

		frmBattleshipCommander.getContentPane().add(btn2Player);
		this.btnSettings = new JButton(
				languageView.getResourceString(LanguageView.SETTINGS));
		btnSettings.setToolTipText("Alt+S");
		btnSettings.addActionListener(this.viewButtListener);

		btnSettings.addKeyListener(this.viewButtListener);
		btnSettings.setMnemonic(KeyEvent.VK_S);

		btnSettings.setBounds(83, 210, 162, 42);
		btnSettings.setActionCommand(Definitions.SETTING_START_VIEW);

		frmBattleshipCommander.getContentPane().add(btnSettings);

		label = new JLabel("");
		label.setIcon(new ImageIcon(StartView.class
				.getResource("/img/shipBackground.jpg")));
		label.setBounds(23, 28, 310, 203);
		frmBattleshipCommander.getContentPane().add(label);
		frmBattleshipCommander.setVisible(true);
		btnPlayerCom.requestFocus();
	}

	// *****************Called from Logic****************************

	public void openStartViewSettings()
	{
		// add didi
		this.startSettWindow = new StartSettingsWindow(startSettData,
				this.languageView, refGameLogic.getgameSoundPlayer());
		// old this.startSettWindow = new StartSettingsWindow(startSettData,
		// this.languageView);
	}

	public void openViewGameFields()
	{
		this.gameWindow = new GameWindow(this);
	}

	public String getNextCommandFromGameWindow()
	{
		return this.gameWindow.getNextMove();
	}

	/******************* Called from Game View *************/

	public void setInitFieldInLogic(Field ownInitField)
	{
		this.refGameLogic.setInitField(ownInitField);
	}

	/***************************** Listener *************/

	/**
	 * check which button have currently the focus for keylistener
	 * 
	 * @return -> the string of the focused Button
	 */
	public String checkFocusButton()
	{
		if (btnPlayerCom.isFocusOwner())
		{
			return Definitions.PLAYER_VS_COM;
		}
		else if (btn2Player.isFocusOwner())
		{
			return Definitions.PLAYER_VS_PLAYER;
		}
		else if (btnSettings.isFocusOwner())
		{
			return Definitions.SETTING_START_VIEW;
		}
		return "";
	}

	public void sendGameWindowMessage(String message)
	{
		this.gameWindow.sendMessge(message);

	}

	public void buildConnection()
	{
		// TODO Auto-generated method stub

	}

}
