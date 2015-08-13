package Game;

import java.awt.Point;

import logging.Logging;
import GameUtilities.Command;
import GameUtilities.AttackPosition.AttackPosition;
import GameUtilities.Field.Field;

public class CpuPlayerLogic
{

	private Field ownField = new Field();
	private Field enemyField = new Field();
	private Command nextReturnCommand;
	private CpuGameHandler cpuGameHandler;
	private int level;

	public CpuPlayerLogic(int level)
	{
		this.level = level;
	}

	/**
	 * returns the next command
	 * 
	 * @return
	 */
	public Command getNextCommand()
	{
		try
		{
			Thread.sleep(200);
		}
		catch (InterruptedException e)
		{
			Logging.writeErrorMessage("Problem in wait Time Answer Command CPU Logic");

		}

		Command actualCommand = nextReturnCommand;
		nextReturnCommand = null;
		return actualCommand;
	}

	/**
	 * send command to cpu logic
	 * 
	 * @param command
	 */
	public void sendCommandToCpuLogic(Command command)
	{
		if (command == null)
		{
			Logging.writeInfoMessage("send command Cpu PlayerLogic null objectCPU");
		}

		if (isCommandInitField(command))
		{
			setEnemyField(command);
			setShipsOnOwnField();
			nextReturnCommand = buildFieldCommandByOwnField();
		}
		else if (isCommandAttacCommand(command))
		{
			int points[] = getCoordinatesfromAttacCommand(command);
			ownField.fireToPosition(points[0], points[1]);
			nextReturnCommand = getAnswerCommand();
		}
	}

	private Command buildFieldCommandByOwnField()
	{
		Command fieldCommand = new Command(1, ownField, "INIT_FIELD");

		return fieldCommand;
	}

	private Command getAnswerCommand()
	{
		Point point = enemyField.getRandomfreeFieldCoordinate(level);
		return buildAttacCommand(point);

		// return null;
	}

	private Command buildAttacCommand(Point point)
	{
		Command attacCommand = new Command(1, new AttackPosition(point),
				"ATTAC_COMMAND");

		return attacCommand;
	}

	private int[] getCoordinatesfromAttacCommand(Command command)
	{
		AttackPosition attackPos = (AttackPosition) command.getCommandData();
		Point point = attackPos.getXyPosition();
		int pointCoordinates[] = new int[2];
		pointCoordinates[0] = point.x;
		pointCoordinates[1] = point.y;

		return pointCoordinates;
	}

	private boolean isCommandInitField(Command command)
	{
		if (command.getType().equals("INIT_FIELD"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void setEnemyField(Command command)
	{
		enemyField = (Field) command.getCommandData();
	}

	private boolean isCommandAttacCommand(Command command)
	{
		if (command.getType().equals("ATTAC_COMMAND"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private void setShipsOnOwnField()
	{
		this.cpuGameHandler = new CpuGameHandler(level, enemyField);
		ownField = cpuGameHandler.getInitField();

	}

}