package UnitTests.GameConnection;

import org.junit.Test;
import org.junit.Assert;
import java.awt.Point;
import GameConnections.DataBox;
import GameUtilities.Command;
import GameUtilities.Ship;
import GameUtilities.ShipPosition;
import GameUtilities.ShipType;
import GameUtilities.Field.Field;

public class JDataBox
{

    @Test
    public void pushAndPullSendCommand()
    {
	Field field = new Field();
	Point point1 = new Point(2, 2);
	Point point2 = new Point(3, 3);

	ShipPosition shipPosition1 = new ShipPosition(point1, "vertical");
	ShipPosition shipPosition2 = new ShipPosition(point2, "vertical");

	Ship ship1 = new Ship(shipPosition1, ShipType.AIRCARRIER, 1);
	Ship ship2 = new Ship(shipPosition2, ShipType.DESTROYER, 2);

	field.setShipOnField(ship1);
	field.setShipOnField(ship2);

	Command command1 = new Command(1, field, "INIT_FIELD");
	Command command2 = new Command(2, field, "INIT_FIELD");

	DataBox.pushSendCommand(command1);
	DataBox.pushSendCommand(command2);

	Assert.assertEquals("1;INIT_FIELD;-1,AIRCARRIER,2,2,vertical-2,DESTROYER,3,3,vertical", DataBox
		.popSendCommand().toString());
    }

    @Test
    public void pushAndPollReceive()
    {
	Field field = new Field();
	Point point1 = new Point(2, 2);

	ShipPosition shipPosition1 = new ShipPosition(point1, "vertical");
	Ship ship1 = new Ship(shipPosition1, ShipType.AIRCARRIER, 1);

	field.setShipOnField(ship1);

	Command command1 = new Command(1, field, "INIT_FIELD");
	DataBox.pushReceiveCommand(command1);

	Assert.assertEquals("1;INIT_FIELD;-1,AIRCARRIER,2,2,vertical", DataBox.popReceiveCommand().toString());
    }

}
