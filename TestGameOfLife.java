/**
 * 
 */
package misc.game_of_life.tests;



import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import misc.game_of_life.GameOfLife;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;




/**
 * @author vdjinov
 *
 */
@RunWith(Parameterized.class)
public class TestGameOfLife {
	
	private int currentCellState;
	private int countOfLiveNeighbors;
	private int expectedResult;
	
	private GameOfLife game;
	
	@Before
	public void initialize() {
		game = new GameOfLife();
	}
	
	public TestGameOfLife(int currentCellState, int countOfLiveNeighbors, int expectedResult) {
		this.currentCellState = currentCellState;
		this.countOfLiveNeighbors = countOfLiveNeighbors;
		this.expectedResult = expectedResult;
	}
	
	
	@Parameterized.Parameters
	public static Collection<Object[]> data() {
	      return Arrays.asList(new Object[][] {
	         { 1, 1, 0},
	         { 1, 0, 0},
	         { 1, 2, 1},
	         { 1, 3, 1},
	         { 1, 4, 0},
	         { 0, 3, 1}
	      });
	}
	
	@Test
	public void test() {
		
	      System.out.println("Parameterized input is currentCellState, countOfLiveNeighbors, expectedResult: " + currentCellState + ", " + countOfLiveNeighbors + " ," + expectedResult);
	      
	      assertEquals(expectedResult, 
	      game.getNextCellState(currentCellState, countOfLiveNeighbors));
	}
	

}
