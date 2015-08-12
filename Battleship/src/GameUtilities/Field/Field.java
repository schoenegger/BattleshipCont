package GameUtilities.Field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import logging.Logging;
import GameUtilities.Ship;
import GameUtilities.ShipPosition;
import GameUtilities.ShipType;

public class Field
{
	private Vector<Ship> shipsOnField = new Vector<Ship>();
	private FieldElement[][] fieldElemtens;
	private boolean isOwnField = false;
	private boolean isFieldInit = false;
	private boolean isShipSettingPossible = false;

	// Array of FieldElements 10x10--??

	public Field()
	{
		fieldElemtens = new FieldElement[10][10];
		initNewField();
	}

	public Field(boolean isOwnField)
	{
		this.isOwnField = isOwnField;
		fieldElemtens = new FieldElement[10][10];
		initNewField();
	}

	public boolean isFieldInit()
	{
		return isFieldInit;
	}

	public boolean canSetShip(Ship ship)
	{

		return checkIfShipIsInField(ship) && !isFieldInit
				&& checkAroundShipPosition(ship);

	}

	private boolean checkAroundField()
	{

		return true;
	}

	public boolean checkIfPositionIsInField(int x, int y)
	{
		boolean inField = true;
		inField &= (x <= 9 && x >= 0);
		inField &= (y <= 9 && y >= 0);
		return inField;
	}

	private void initNewField()
	{
		for (int i = 0; i <= 9; i++)
		{
			for (int j = 0; j <= 9; j++)
			{
				if (isOwnField)
					fieldElemtens[i][j] = new FieldElement(true);
				else
					fieldElemtens[i][j] = new FieldElement(false);

				fieldElemtens[i][j].setFieldState(FieldState.UNKNOWN);
			}
		}
	}

	public Vector<Ship> getListOfSetableShips()
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

	public FieldState getStateOfFieldElement(int x, int y)
	{
		FieldState fieldState = fieldElemtens[x][y].getFieldState();
		return fieldState;
	}

	public FieldElement getFieldElement(int x, int y)
	{
		return fieldElemtens[x][y];
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
		if (posX >= 0 && posX <= 9 && posY >= 0 && posY <= 9)
		{
			if (this.fieldElemtens[posX][posY].getFieldState() == FieldState.UNKNOWN)
			{
				return true;
			}
		}
		return false;
	}

	// TODO maybe second fireToPosition
	public boolean fireToPosition(int posX, int posY)
	{
		if (fieldElemtens[posX][posY].isTaken())
		{
			fieldElemtens[posX][posY].setFieldState(FieldState.STRIKE_SHIP);
			setAliveShipState();
			setTaken();
			return true;
		}
		else
		{
			fieldElemtens[posX][posY].setFieldState(FieldState.STRIKE_WATER);
		}

		return false;
	}

	private void setAliveShipState()
	{
		for (Ship ship : shipsOnField)
		{
			String align = ship.getShipPosition().getAlignment();
			int x = ship.getShipPosition().getXyPosition().x;
			int y = ship.getShipPosition().getXyPosition().y;

			boolean isAlive = false;
			boolean vert = false;
			int ind = 0;
			try
			{
				if (!align.equals("vertical"))
				{
					for (int i = x; i < (x + ship.getCountSector() - 1); i++)
					{
						if (!(fieldElemtens[i][y].getFieldState() == FieldState.STRIKE_SHIP))
						{
							isAlive = true;
						}
						vert = false;
						ind = i;
					}
				}
				else
				{
					for (int i = y; i < (y + ship.getCountSector() - 1); i++)
					{
						if (!(fieldElemtens[i][y].getFieldState() == FieldState.STRIKE_SHIP))
						{
							isAlive = true;
						}
						vert = true;
						ind = i;
					}
				}
			}
			catch (Exception e)
			{
				System.out.println("wrong num=" + ind + "  vertical = " + vert);
				e.printStackTrace();
			}
			ship.setAliveState(isAlive);
		}
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

			try
			{
				if (checkIfShipIsInField(ship))
				{
					if (alignment.toLowerCase().equals("horizontal")
							&& ship.isAlive())
					{
						for (int i = 0; i < countSector; i++)
						{
							fieldElemtens[currShipPoint.x + i][currShipPoint.y]
									.setTaken();

							if (!ship.isAlive())
							{
								fieldElemtens[currShipPoint.x + i][currShipPoint.y]
										.setSunkenShip();
							}
						}
					}
					else
					// Horizontal
					{
						for (int i = 0; i < countSector; i++)
						{
							fieldElemtens[currShipPoint.x][currShipPoint.y + i]
									.setTaken();

							if (!ship.isAlive())
							{
								fieldElemtens[currShipPoint.x + i][currShipPoint.y]
										.setSunkenShip();
							}
						}
					}
				}
				else
				{
					System.out
							.println("Field.setTaken -> Ship is not in field");
					Logging.writeInfoMessage("Field.setTaken -> Ship is not in field");
				}
			}
			catch (Exception e)
			{
				Logging.writeErrorMessage("Field -> No valid Field");
			}
		}

	}

	private boolean checkIfShipIsInField(Ship ship)
	{
		int pointX = ship.getShipPosition().getXyPosition().x;
		int pointY = ship.getShipPosition().getXyPosition().y;

		if (ship.getShipPosition().getAlignment().toLowerCase()
				.equals("horizontal")
				&& (pointX + ship.getCountSector()) > 10)
			return false;

		if (ship.getShipPosition().getAlignment().toLowerCase()
				.equals("vertical")
				&& (pointY + ship.getCountSector()) > 10)
			return false;

		return (pointX < 10) && (pointX >= 0) && (pointY < 10) && (pointY >= 0);
	}

	public void setShipOnField(Ship ship)
	{
		this.shipsOnField.addElement(ship);

		if (shipsOnField.size() > 5)
		{
			isFieldInit = true;
		}

		setTaken();
	}

	public void setPossibleFields(int x, int y, ShipType type, String align)
	{
		ShipPosition shipPos = new ShipPosition(new Point(x, y), align);

		// +1 is number of next ship
		Ship ship = new Ship(shipPos, type, 0, align); // ship never get add to
														// field
		isShipSettingPossible = true;
		unmarkFields();

		if (isOwnField && IsValidAttacPosition(x, y) && !isFieldInit)
		{
			if (checkAroundShipPosition(ship))
			{
				if (align.equals("horizontal"))
				{
					for (int i = x; i < (x + ship.getCountSector()); i++)
					{
						if (i >= 0 && i <= 9 && y >= 0 && y <= 9)
						{
							fieldElemtens[i][y].setisPossibleField(true);
						}
						else
						{
							isShipSettingPossible = false;
						}
					}
				}
				else
				{
					for (int i = y; i < (y + ship.getCountSector()); i++)
					{
						if (x >= 0 && x <= 9 && i >= 0 && i <= 9)
						{
							fieldElemtens[x][i].setisPossibleField(true);
						}
						else
						{
							isShipSettingPossible = false;
						}
					}
				}
			}
		}
		else if (!isOwnField && IsValidAttacPosition(x, y))
		{
			fieldElemtens[x][y].isAvailableToAttac();
			fieldElemtens[x][y].setisPossibleField(true);
			return;
		}
	}

	private boolean checkAroundShipPosition(Ship ship)
	{
		List<int[]> list = new ArrayList<int[]>();
		list = ship.getListOfReservatedFields();
		// boolean noFieldTaken = true;

		for (int[] coordinate : list)
		{
			int x = coordinate[0];
			int y = coordinate[1];
			if (fieldElemtens[x][y].isTaken())
			{
				return false;
			}
		}
		return true;
	}

	public void unmarkFields()
	{
		for (FieldElement[] feRow : fieldElemtens)
		{
			for (FieldElement fe : feRow)
			{
				fe.setisPossibleField(false);
			}
		}
	}

	public boolean isShipSettingPossiple()
	{
		return isShipSettingPossible & !isFieldInit;
	}

	@Deprecated
	/**
	 * Display Field in Console format
	 */
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

	@Deprecated
	/**
	 * Display hidden Field in console format
	 */
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

}