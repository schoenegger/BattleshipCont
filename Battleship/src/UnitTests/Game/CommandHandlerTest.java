package UnitTests.Game;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;

import java.awt.Point;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Game.CommandHandler;
import Game.Logic;
import GameConnections.DataBox;
import GameUtilities.Command;
import GameUtilities.AttackPosition.AttackPosition;

public class CommandHandlerTest
{
	Logic refLogic = mock(Logic.class);
	CommandHandler commandHandler;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
		this.commandHandler = new CommandHandler(refLogic);
	}

	@After
	public void tearDown() throws Exception
	{
		this.commandHandler = null;
	}

	@Test
	public void testCommandHandler()
	{
		Command attacCommand = new Command(1, new AttackPosition(
				new Point(5, 5)), "ATTAC_COMMAND");

		DataBox.pushReceiveCommand(attacCommand);
		this.commandHandler.sendAttacCommand(attacCommand);

		Command recCommand = DataBox.popSendCommand();
		assertTrue(recCommand.getCommandData() instanceof AttackPosition);
		Assert.assertEquals("Type must be ATTAC_COMMAND", "ATTAC_COMMAND",
				recCommand.getType());
	}

	@Test
	public void testSendInitField()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testSendAttacCommand()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveInitFieldFromEnemy()
	{
		fail("Not yet implemented");
	}

	@Test
	public void testReceiveCommandFromDataBox()
	{
		fail("Not yet implemented");
	}

}
