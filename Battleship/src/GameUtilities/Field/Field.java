package GameUtilities.Field;

import java.awt.Point;
import java.util.Vector;

import GameUtilities.Ship;

public class Field
{
	private Vector<Ship> shipsOnField = new Vector<Ship>();
	private FieldElement[][] fieldElemtens;
	private boolean isFieldInit = false;

	// Array of FieldElements 10x10--??

	public Field()
	{
		fieldElemtens = new FieldElement[10][10];
		initNewField();
	}

	public boolean isFieldInit()
	{
		return isFieldInit;
	}

	public boolean canSetShip(Ship ship)
	{
		// TODO valid check
		return true;
	}

	public boolean checkIfPositionIsInField(int x, int y)
	{
		boolean inField = true;
		inField &= (x <= 10 && x >= 0);
		inField &= (y <= 10 && y >= 0);
		return inField;
	}

	private void initNewField()
	{
		for (int i = 0; i <= 9; i++)
		{
			for (int j = 0; j <= 9; j++)
			{
				fieldElemtens[i][j] = new FieldElement();
				fieldElemtens[i][j].setFieldState(FieldState.UNKNOWN);
			}
		}
	}

	public Vector<Ship> getListOfActiveShips()
	{
		Vector<Ship> activeShipsInField = new Vector<Ship>();

		for (Ship ship : shipsOnField)
		{
			if (ship.isAlive())
			{
				activeShipsInField.addElement(ship);
			}
		}
		return activeShipsInField;
	}

	/**
	 * Sets a ship on the Field max 2AirCarrier, 2YellowSubmarines, 2Destroyer
	 * returnValue initValues valid = true, Invalid = false
	 * 
	 * @param ship
	 * @return
	 */
	public boolean setShipsOnField(Vector<Ship> ships)
	{

		shipsOnField = new Vector<Ship>();

		if (checkIfShipsListIsValid())
		{
			shipsOnField = ships;
			this.isFieldInit = true;
			return true;
		}

		return false;
	}

	private boolean checkIfShipsListIsValid()
	{
		// TODO Auto-generated method stub
		return true;
	}

	public boolean checkIfAllShipsAreCountersunk()
	{
		for (int i = 0; i <= 9; i++)
		{
			for (int j = 0; j <= 9; j++)
			{
				if (fieldElemtens[i][j].isTaken()
						&& !fieldElemtens[i][j].getFieldState().equals(
								FieldState.STRIKE_SHIP))
				{
					return false;
				}
			}
		}

		return true;
	}

	public Point getRandomfreeFieldCoordinate()
	{
		Point freePosition;

		for (int i = 0; i <= 9; i++)
		{
			for (int j = 0; j <= 9; j++)
			{
				if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.STRIKE_SHIP))
				{
					// TODO maybe something more intelligent^^
				}
				if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.UNKNOWN))
				{
					freePosition = new Point(i, j);
					return freePosition;
				}
			}
		}
		return new Point(0, 0);
	}

	public boolean IsValidAttacPosition(int posX, int posY)
	{

		if (this.fieldElemtens[posX][posY].getFieldState().equals(
				FieldState.UNKNOWN))
		{
			return true;
		}
		else
		{
			return false;
		}

	}

	// TODO maybe second fireToPosition
	public boolean fireToPosition(int posX, int posY)
	{
		if (fieldElemtens[posX][posY].isTaken())
		{
			fieldElemtens[posX][posY].setFieldState(FieldState.STRIKE_SHIP);
			return true;
		}
		else
		{
			fieldElemtens[posX][posY].setFieldState(FieldState.STRIKE_WATER);
		}

		return false;
	}

	@Override
	public String toString()
	{
		String transferDataString = "";

		for (Ship ship : shipsOnField)
		{
			transferDataString += ship.toTransferString();
		}

		return transferDataString;
	}

	public void setTaken()
	{
		for (Ship ship : shipsOnField)
		{
			Point currShipPoint = ship.getShipPosition().getXyPosition();
			String alignment = ship.getShipPosition().getAlignment();
			int countSector = ship.getCountSector();

			if (alignment.equals("VERTICAL"))
			{
				for (int i = 0; i < countSector; i++)
				{
					fieldElemtens[currShipPoint.x + i][currShipPoint.y]
							.setTaken();
				}
			}
			else
			// Horizontal
			{
				for (int i = 0; i < countSector; i++)
				{
					fieldElemtens[currShipPoint.x][currShipPoint.y + i]
							.setTaken();
				}
			}
		}
	}

	public void display()
	{
		String printField = "_____________________";
		for (int i = 0; i <= 9; i++)
		{
			printField += "\n";

			for (int j = 0; j <= 9; j++)
			{
				if (fieldElemtens[i][j].isTaken()
						&& !fieldElemtens[i][j].getFieldState().equals(
								FieldState.STRIKE_SHIP))
				{
					printField += "|S";
				}
				else if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.STRIKE_SHIP))
				{
					printField += "|X";
				}
				else if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.STRIKE_WATER))
				{
					printField += "|~";
				}
				else
				{
					printField += "| ";
				}
			}
			printField += "|";
			printField += Integer.toString(i);
		}

		System.out.println(printField);
		System.out.println(" 0 1 2 3 4 5 6 7 8 9");
	}

	public void displayIncognito()
	{
		String printField = "_____________________";
		for (int i = 0; i <= 9; i++)
		{
			printField += "\n";

			for (int j = 0; j <= 9; j++)
			{

				if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.STRIKE_SHIP))
				{
					printField += "|X";
				}
				else if (fieldElemtens[i][j].getFieldState().equals(
						FieldState.STRIKE_WATER))
				{
					printField += "|~";
				}
				else
				{
					printField += "| ";
				}
			}
			printField += "|";
			printField += Integer.toString(i);
		}

		System.out.println(printField);
		System.out.println(" 0 1 2 3 4 5 6 7 8 9");
	}

	public void setShipOnField(Ship ship)
	{
		if (shipsOnField.size() > 6)
			return;

		this.shipsOnField.addElement(ship);
	}
}