package Game;

import java.awt.Point;

import GameUtilities.Ship;
import GameUtilities.ShipCreator;
import GameUtilities.ShipPosition;
import GameUtilities.ShipType;
import GameUtilities.Field.Field;

public class CpuGameHandler
{
	private int level;
	private Field enemyField;
	private Field ownField;
	private Point randXY;
	private String randAlignment;

	public CpuGameHandler(int level, Field enemyField)
	{
		this.level = level;
		this.enemyField = enemyField;
		this.ownField = new Field();
	}

	public Field getInitField()
	{
		for (ShipType st : ShipType.values())
		{
			setShipOnField(st);
		}

		return this.ownField;

	}

	private void setShipOnField(ShipType st)
	{
		ShipCreator shipCreator = new ShipCreator();

		Ship ship;

		do
		{
			setRandShipPosition();
			ShipPosition sp = new ShipPosition(this.randXY, this.randAlignment);
			ship = shipCreator.getShip(sp, st);
			if (ship != null)
			{
				while (!ownField.canSetShip(ship))
				{
					setRandShipPosition();
					ship.setShipPosition(randXY, randAlignment);
				}
				ownField.setShipOnField(ship);
			}

		} while (ship != null);

	}

	private void setRandShipPosition()
	{
		this.randXY = new Point((int) (Math.random() * 1000) % 10,
				(int) (Math.random() * 1000) % 10);

		if ((int) (Math.random() * 1000) % 2 == 0)
		{
			this.randAlignment = "horizontal";
		}
		else
		{
			this.randAlignment = "vertical";
		}

	}

}
