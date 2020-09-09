import java.util.Random;

// Author: Minh Anh Nguyen

public class MazeGenerator {
	private int size;
	private int[][] maze;
	private boolean[][] visit;
	Random ran = new Random();
	
	
	public MazeGenerator(int s) {
		size = s;
		maze = new int[size][size];
		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++)
			{
				maze[row][col] = 0;
			}
		
		visit = new boolean[size][size];
		for (int row = 0; row < visit.length; row++)
			for (int col = 0; col < visit[row].length; col++)
				visit[row][col] = false;
	}
	
	public int[][] generate() 
	{	
		if(setPath(0, 0, size - 1, size - 1))
			System.out.println("Maze Puzzle Is Generated: ");
		extendPath();
		
		return maze;
	}
	
	//create the path from (row, col) to (end_row, end_col)
	private boolean setPath(int row, int col, int end_row, int end_col)
	{
		if (row == end_row && col == end_col)
		{
			maze[row][col] = 1;
			return true;
		}
		
		if (!isValid(row, col))
			return false;

		setVisit(row, col);
		
		//go to the west
		int i = ran.nextInt(4);
		if (i == 3  && !isVisited(row, col -1) &&  setPath(row, col - 1, end_row, end_col))
		
			{
				maze[row][col] = 1;
				return true;
			} 
		
		// go to the south
		i = ran.nextInt(3);	
		if (i == 2  && !isVisited(row - 1, col) && setPath(row - 1, col, end_row, end_col))
		{
			maze[row][col] = 1;
			return true;
		}
		
		// go to the east
		i = ran.nextInt(2);	
		if (i == 1 && setPath(row, col + 1, end_row, end_col))
		{
			maze[row][col] = 1;
			return true;
		}
		
		// go to the north
		if (setPath(row +1, col, end_row, end_col))
		{
			maze[row][col] = 1;
			return true;
		} else if (setPath(row, col + 1, end_row, end_col)) // if the path is stuck, go to the east
		{
			maze[row][col] = 1;
			return true;
		}
		
		return false;
	}

	// get the length of the generated path
	private int getPathLength()
	{
		int length = 0;
		for (int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
				if (maze[row][col] == 1)
					length++;
		return length;
	}
	
	//create sub-path
	private void extendPath()
	{
		int length = getPathLength();
		int[] pathRows = new int[length];
		int[] pathCols = new int[length];
		
		int index = 0;
		for (int row = 0; row < size; row++)
			for(int col = 0; col < size; col++)
				if (maze[row][col] == 1)
				{
					pathRows[index] = row;
					pathCols[index] = col;
					index++;
				}
		
		for (int i = 0; i < size/4; i++)
		{
			int start = ran.nextInt(length);
			int goal_row = ran.nextInt(size - pathRows[start]) + pathRows[start];
			int goal_col = ran.nextInt(size - pathCols[start]) + pathCols[start];
			if(setPath(pathRows[start], pathCols[start], goal_row, goal_col))
				continue;
		}
		
	}
	
	private boolean isValid(int row, int col)
	{
		if (row >= 0 && row < size && col >=0 && col < size)
			return true;
		return false;
	}
	
	private boolean isVisited(int row, int col)
	{
		if (isValid(row,col))
			return visit[row][col];
		return false;
	}
	
	private void setVisit(int row, int col)
	{
		visit[row][col] = true;
	}
}
