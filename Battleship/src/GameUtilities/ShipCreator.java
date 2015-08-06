package GameUtilities;

import view.GlobalStrings.Definitions;

public class ShipCreator
{
	private int countAirCarrier = 0;
	private int countDestroyer = 0;
	private int countYellowSubmarine = 0;
	private int shipNumber = 0;

	public Ship getShip(ShipPosition shipPosition, ShipType type)
	{
		if (isPossibleToReturnShip(type))
		{
			return createShip(shipPosition, type);
		}

		return null;
	}

	private boolean isPossibleToReturnShip(ShipType type)
	{
		switch (type)
		{
		case AIRCARRIER :
			return (countAirCarrier++ < Definitions.MAX_AIRCARRIER);

		case DESTROYER :
			return (countDestroyer++ < Definitions.MAX_DESTROYER);

		default :
			return (countYellowSubmarine++ < Definitions.MAX_YELLOW_SUBMARINE);
		}

	}

	private Ship createShip(ShipPosition shipPosition, ShipType type)
	{
		return new Ship(shipPosition, type, ++shipNumber);
	}

}
