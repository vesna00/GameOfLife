package misc.game_of_life;

/* 
 * The playing field for Conway’s game of life consists of a two dimensional grid of cells. 
 * Each cell is identified as either alive or dead. For this exercise, let’s assume the
playing field is an 8x6 grid of cells (i.e. 8 columns, 6 rows).
The challenge is to calculate the next state of the playing field given any initial grid
state. To find the next state, follow these rules:

1. Any live cell with fewer than two live neighbors dies, as if caused by under-population.
2. Any live cell with more than three live neighbors dies, as if by overcrowding.
3. Any live cell with two or three live neighbors lives on to the next generation.
4. Any dead cell with exactly three live neighbors becomes a live cell.
5. A cell’s neighbors are those cells which are horizontally, vertically or
diagonally adjacent. Most cells will have eight neighbors. Cells placed on the
edge of the grid have 5 neighbors, while corner cells have 3 neighbors.

 */
public class GameOfLife {
	
	// Note: set checkSetMaxGridSize = false in order to calculate arbitrary size 2D grid's next generation 
	public static boolean checkSetMaxGridSize = true;
	// max_column_size and max_row_size are enforced only when checkSetMaxGridSize is true.
	public static int max_column_size = 8;
	public static int max_row_size = 6;
	
	// Neighbors of any cell are at the following locations
	enum Location {
	    NORTHWEST(-1, -1),NORTH(-1, 0),NORTHEAST(-1, 1),
	    EAST(0, -1),				   WEST(0, +1),
	    SOUTHEAST(1, -1),  SOUTH(1, 0), SOUTHWEST(1, 1),
	    ;

	    final int x;
	    final int y;

	    Location(int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
	}
	
	public GameOfLife() { }
	
	public GameOfLife(boolean checkSetMaxGridSize) {
		// Set checkSetMaxGridSize = false in order to calculate arbitrary size 2D grid's next generation 
		this.checkSetMaxGridSize = checkSetMaxGridSize;
	}
	
	
	/* Based on the current generation g and the 4 rules/transitions, return the nextGeneration ng.
	 * Input:
	 * a 2D grid with cell values 0 / 1 where 0 = live, and 1 = dead cell.
	 * Grid size:
	 * - an 8 columns x 6 rows OR 
	 * - any size 2D grid when checkSetMaxGridSize is false.
	 * Output: next state of the grid OR null when input is invalid.
	 */
	public int[][] getNextGeneration(int[][] generation) {
		
		// If input is invalid return null 
		if (isInputGridSizeInvalid(generation)) {
			return null;
		}
		
		int max_row = generation.length;
		int max_column = generation[0].length;
		// nextGrid is initialized to 0. 0 is default value for int.
		int[][] nextGrid = new int[max_row][max_column];
		
		for (int r = 0; r < max_row; r++ ) {
			for (int c = 0; c < max_column; c++) {
				
				int countOfLiveN = getCountOfLiveNeighbors(generation, r, c);
				
				// Set the next state only if nextState is Live. Otherwise, nexGrid is already initialized to 0.
				if (getNextCellState(generation[r][c],countOfLiveN ) == 1) {
					nextGrid[r][c] = 1;
				}
				
			}
		}
		
		return nextGrid;
	}
	
	
	public int getNextCellState(int cellState, int countOfLiveN) {

		// Rule: Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
		if (cellState == 0 && countOfLiveN == 3) {
			return 1; // live
		}
		
		// Rule: Any live cell with two or three live neighbors lives on to the next generation.
		if (cellState == 1 && countOfLiveN >= 2 && countOfLiveN <= 3) {
			return 1; // live
		}
		
		return 0; // dead
	}

	/*
	 * Input: 
	 * i, j are coordinates of the current cell.
	 * r = max row size, c = max column size of the grid
	 * Output: 
	 * the count of live neighbors.
	 */
	private int getCountOfLiveNeighbors(int[][] generation, int r, int c) {
		
		int live_neighbors = 0; // liveNeighbors
		
		for (Location l : Location.values()) {
			// Check only valid neighbor locations:
			// Corner cells have max 3 neighbors. Corner cells are: [0,0], [0,max_column],[max_row,0],[max_row,max_column] 
			// Outer edge cells that are not corners have 5 neighbors.
			// All inside cells have 8 neighbors.
			if ( (r + l.x) >= 0 && (r + l.x) < generation.length &&
				 (c + l.y) >= 0 && (c + l.y) < generation[0].length) {
				
				// Check if a neighbor is alive = 1
		        if (generation[r + l.x][c + l.y] == 1) {
		        	live_neighbors++;
		        }
			}

	    }
		
		return live_neighbors;
	}



	private boolean isInputGridSizeInvalid(int[][] grid) {
		
		if (grid == null ||
				grid.length == 0 ) {
				System.err.println("ERROR GameOfLife::isInputGridSizeValid(): Input is null.");
				
				return true;
	
		} else if ( checkSetMaxGridSize &&
				(grid.length > max_row_size ||
					grid[0].length > max_column_size)	) {
				System.err.println("ERROR GameOfLife::isInputGridSizeValid(): Input size invalid, must be: max_row_size=" + max_row_size + " max_column_size=" + max_column_size + " Input grid size is raw=" + grid.length + " column=" + grid[0].length);
				
				return true;
		} 
		
		return false;
	}
	
	
    // Print 2D grid, 0 = dead cell, 1 = live cell.
	public static void printGrid(int[][] g) {

        for (int i=0; i< g.length ; i++) {
            for (int j=0; j < g[0].length ; j++) {
                System.out.print(g[i][j] + " ");
            }
            System.out.println();
        }
	}
	

	
	

	public static void main(String[] args) {
		
		GameOfLife game = new GameOfLife();
		
		// Grid initialized to 0s. 
		int[][] g0 = new int[max_row_size][max_column_size];
		game.testGetNextGeneration(g0);
		
		// Grid initialized to 1s.
		int[][] g1 = { {1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1} };
		game.testGetNextGeneration(g1);
		
		int[][] g2 = { {0,0,0,0,0,0,1,0},{1,1,1,0,0,0,1,0},{0,0,0,0,0,0,1,0},{0,0,0,0,0,0,0,0},{0,0,0,1,1,0,0,0},{0,0,0,1,1,0,0,0} };
		game.testGetNextGeneration(g2);
		
		int[][] g3 = { {1,1,1,1,1,1,0,1},{0,0,0,1,1,1,0,1},{1,1,1,1,1,1,0,1},{1,1,1,1,1,1,1,1},{1,1,1,0,0,1,1,1},{1,1,1,0,0,1,1,1} };
		game.testGetNextGeneration(g3);
		
		// Test dead cell + 3 live neighbors = live next gen
		// Grid initialized to 1s.
		int[][] g4 = { {0,1,1,1,1,1,1,0},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{1,1,1,1,1,1,1,1},{0,1,1,1,1,1,1,0} };
		game.testGetNextGeneration(g4);

	}


	private void testGetNextGeneration(int[][] grid) {

		System.out.println("Input 2D grid:");
		printGrid(grid);
		System.out.println("Output nextGeneration is: ");
		printGrid(getNextGeneration(grid));
		System.out.println("");
	}


}
