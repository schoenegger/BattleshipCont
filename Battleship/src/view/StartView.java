package view;

import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import view.GlobalStrings.ViewStringDefinitions;
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

	private JFrame frmBattleshipCommander;

	private JButton btnPlayerCom;
	private JButton btn2Player;
	private JButton btnSettings;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	// public static void main(String[] args) {
	// try {
	// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
	// } catch (Throwable e) {
	// e.printStackTrace();
	// }
	// EventQueue.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// StartView window = new StartView();
	// window.frmBattleshipCommander.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// }

	/**
	 * Create the application.
	 */
	public StartView(Logic refGameLogic, StartViewSettingData startSettData)
	{
		this.startSettData = startSettData;
		this.refGameLogic = refGameLogic;
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

		this.btnPlayerCom = new JButton("PLAYER vs COM");
		btnPlayerCom.addActionListener(viewButtListener);
		btnPlayerCom.setBounds(171, 0, 162, 42);
		btnPlayerCom.setActionCommand(ViewStringDefinitions.PLAYER_VS_COM);
		frmBattleshipCommander.getContentPane().add(btnPlayerCom);

		this.btn2Player = new JButton("PLAYER vs PLAYER");
		btn2Player.setBounds(0, 0, 162, 42);
		btn2Player.setActionCommand(ViewStringDefinitions.PLAYER_VS_PLAYER);
		frmBattleshipCommander.getContentPane().add(btn2Player);

		this.btnSettings = new JButton("SETTINGS");
		btnSettings.addActionListener(this.viewButtListener);
		btnSettings.setBounds(83, 210, 162, 42);
		btnSettings.setActionCommand(ViewStringDefinitions.SETTING_START_VIEW);
		frmBattleshipCommander.getContentPane().add(btnSettings);

		label = new JLabel("");
		label.setIcon(new ImageIcon(StartView.class
				.getResource("/img/shipBackground.jpg")));
		label.setBounds(23, 28, 310, 203);
		frmBattleshipCommander.getContentPane().add(label);

		frmBattleshipCommander.setVisible(true);
	}

	// *************************************Called from
	// Logic****************************

	public void openStartViewSettings()
	{
		this.startSettWindow = new StartSettingsWindow(startSettData);
	}

	public void openViewGameFields(Field ownField, Field enemyField)
	{
		this.gameWindow = new GameWindow(ownField, enemyField);
	}
}
