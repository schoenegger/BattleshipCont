package UnitTests.TestConnectionCommandHandler;

import java.awt.Point;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GameConnections.ConnectionCommandHandler;
import GameConnections.DataBox;
import GameUtilities.Command;
import GameUtilities.AttackPosition.AttackPosition;
import UnitTests.MockObjects.MockConnection;

public class TestConnectionCommandHandler
{

	MockConnection mockConnection;
	ConnectionCommandHandler connCommandHandler;

	@Before
	public void initializeTest()
	{
		mockConnection = new MockConnection();
		connCommandHandler = null;
	}

	@Test
	public void TestReceiveAttacCommands()
	{

		AttackPosition attacPosTest1 = new AttackPosition(new Point(2, 2));
		Command commandTest1 = new Command(1, attacPosTest1, "ATTAC_COMMAND");

		Command commandTest2 = new Command(2, attacPosTest1, "ATTAC_COMMAND");

		DataBox.pushSendCommand(commandTest1);
		DataBox.pushSendCommand(commandTest2);

		connCommandHandler = new ConnectionCommandHandler(mockConnection); // activate
																			// Thread

	}

	@After
	public void tearDownTest()
	{
		mockConnection = null;
		connCommandHandler = null;
	}
}
