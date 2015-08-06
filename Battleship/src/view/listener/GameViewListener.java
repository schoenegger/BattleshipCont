package view.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import logging.Logging;
import view.GlobalStrings.Definitions;
import Game.Logic;

public class GameViewListener implements ActionListener, MouseMotionListener,
		MouseListener

{

	private Logic refLogic;

	public GameViewListener(Logic refLogic)
	{
		this.refLogic = refLogic;
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{

	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		try
		{
			Thread.sleep(20);
		}
		catch (InterruptedException e1)
		{
			Logging.writeErrorMessage("Mouse Listener failed");
		}

		refLogic.sendMousePositionsToGameView(e.getX(), e.getY());

	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String command = e.getActionCommand();
		switch (command)
		{
		case Definitions.BUTTON_SET_SHIP :
			refLogic.setShipButtonPressed();
			break;
		case Definitions.BUTTON_ATTAC :
			refLogic.attacShipButtonPressed();
			break;
		}

	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		refLogic.mouseClickToGameView();
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub

	}

	// must be called by valid Mouse Click Event
	// refLogic.mouseClickToGameView();

}
