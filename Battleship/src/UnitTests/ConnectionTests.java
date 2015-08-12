package UnitTests;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import Game.CommandHandler;
import GameUtilities.Field.Field;

public class ConnectionTests
{

	// using MOCKITO for testing the application

	// http://docs.mockito.googlecode.com/hg/org/mockito/stubbing/OngoingStubbing.html

	@Test
	public void TestCommandHandler()
	{
		CommandHandler mockCommHandler = mock(CommandHandler.class);
		Field field = new Field(true);

		List<String> mockedList = mock(List.class);

		when(mockedList.isEmpty()).thenReturn(true);
		when(mockedList.size()).thenReturn(10);

		//
		// doAnswer(new Answer() {
		// public Object answer(InvocationOnMock invocation) {
		// // Object[] args = invocation.getArguments();
		// // Mock mock = invocation.getMock();
		// return null;
		// }})
		// .when(mockedList).mockedList.size();
		//
		// doAnswer(new Answer()
		// {
		// public Object answer(InvocationOnMock invocation)
		// {
		//
		// return null;
		// }
		// }).when(field).getFieldElement(10, 10);
		//
		// doAnswer(new Answer()
		// {
		// public Object answer(InvocationOnMock invocation)
		// {
		// FieldElement fieldElement = new FieldElement(true);
		//
		// return fieldElement;
		// }
		// }).when(field).getFieldElement(9, 9);

		doAnswer(new Answer()
		{
			public Object answer(InvocationOnMock invocation)
			{
				return "test";
			}
		}).when(mockCommHandler).toString();

		// when(mock.someMethod(anyString())).thenAnswer(new Answer() {
		// Object answer(InvocationOnMock invocation) {
		// Object[] args = invocation.getArguments();
		// Object mock = invocation.getMock();
		// return "called with arguments: " + args;
		// }
		// });

		// when(mockedList.size()).thenCallRealMethod(field.fireToPosition(10,
		// 10));
		// when(mockedList.addAll(new ArrayList<String>()).
		Assert.assertEquals("test", mockCommHandler.toString());
		// Assert.assertNotNull(field.getFieldElement(9, 9));
		// Assert.assertNull(field.getFieldElement(10, 10));
		Assert.assertEquals(10, mockedList.size());
		Assert.assertTrue(mockedList.isEmpty());

		mockedList.add("one");
		mockedList.clear(); // when(mockCommHandler.receiveInitFieldFromEnemy())then
	}

	@Test
	public void TestGameField()
	{

	}

}
