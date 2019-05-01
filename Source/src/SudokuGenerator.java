import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JOptionPane;;
public class SudokuGenerator {
	static int numberOfSquares = 2;
	static int squaresSquared = numberOfSquares * numberOfSquares;
	static int speed;
	public static void main(String args[]) throws InterruptedException
	{
				
		JFrame frame = new JFrame("Sudoku Generator"); ///Create main frame
		
		///Loops through dialog box until user inputs proper square size, throws any exception(Null pointer if they cancel out of dialog)
		try {
		numberOfSquares = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter size of squares between 2 and 5 \n (A normal sudoku puzzle is size 3)"));
		while (numberOfSquares < 2 || numberOfSquares > 5)
			numberOfSquares = Integer.parseInt(JOptionPane.showInputDialog(frame, "Invalid amount of squares. Please enter number of squares between 2 and 5"));
		}
		catch (Exception e)
		{
			System.exit(0);
		}
		
		squaresSquared = numberOfSquares * numberOfSquares; ///set squares * squares
		
		///Repeat dialog loop for program speed
		try {
		speed = Integer.parseInt(JOptionPane.showInputDialog(frame, "Enter the speed of the program in milliseconds \n (between 0 and 1000"));
		while (speed < 0 || speed > 1000)
			speed = Integer.parseInt(JOptionPane.showInputDialog(frame, "Invalid speed.Please enter the speed of the program in milliseconds(between 1 and 1000"));;
		}
		catch (Exception e)
		{
			System.exit(0);
		}
		
		SudokuGrid sg = new SudokuGrid(squaresSquared,squaresSquared); ///Create panel grid set to squares^2 size
		
		
		///Create 2d array of same size, fill with '.' to denote empty
		String[][] board = new String[squaresSquared][squaresSquared];
		for(int i = 0; i < board.length; i++)
			for (int j = 0; j < board[i].length; j++) 
			{
				board[i][j] = ".";

			}
		
		

		///Set frame settings, add sudoku grid and set to visible
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(sg);
		frame.pack();
		frame.setVisible(true);
		
		long currentTime = System.currentTimeMillis(); ///Time stamp
		for (int i = 0; i < squaresSquared; i += numberOfSquares) ///Fill each diagonal box as they are independant of each other
		{
			fillBox(board, i, i, sg, frame); ///Call fill box at current box
		}

		fillRest(board, 0, 0, sg, frame); ///Call recursive fill rest method
		
		createPuzzle(board, (squaresSquared *  squaresSquared) / 4, sg, frame); ///Remove 75% of filled values
		
		frame.revalidate();
		
		///Display elapsed time
		currentTime = (System.currentTimeMillis() - currentTime) / 1000;
		JOptionPane.showMessageDialog(frame,
			    "Done! Elapsed time: " + currentTime + " seconds");


		
		
	}
	
	///Loops through each index in the given box, attempts to fill each sqaure with a value between 1 and squares^2
	public static void fillBox(String[][] board, int iStart, int jStart, SudokuGrid sg, JFrame frame) throws InterruptedException ///Fills a 3x3 square starting at the top left
	{
		Random rand = new Random();
		
		for (int i = iStart; i < iStart + numberOfSquares; i++)
			for (int j = jStart; j < jStart + numberOfSquares; j++) 
			{
				while (board[i][j] == ".")
				{
					Thread.sleep(speed);
					int randNum = 1 + rand.nextInt(squaresSquared);
					sg.sudokuArray[i][j].setDigit("" + randNum, Color.red);
					frame.revalidate();
					if (isValid("" + randNum, board, i, j))
					{
						board[i][j] = "" + randNum;
						sg.sudokuArray[i][j].setDigit("" + randNum, Color.green);
						frame.revalidate();
					}
				}
			}
		///Change green color to white
		for (int i = iStart; i < iStart + numberOfSquares; i++)
			for (int j = jStart; j < jStart + numberOfSquares; j++) 
			{
				sg.sudokuArray[i][j].setDigit("" + board[i][j], Color.white);
				frame.revalidate();
			}
		
	}	
	
	///Recursive method, finds a valid random value to fill the current square in, then checks to make sure the rest of the
	// puzzle is solvable by recalling the method on the next index, "valid" squares are denoted in green, red squares are 
	// found to be no longer valid.
	public static boolean fillRest(String[][] board, int i, int j, SudokuGrid sg, JFrame frame) throws InterruptedException
	{
		
		
		if (board[i][j] != ".") ///If current square is filled
		{
			if (i == board.length - 1 && j == board[i].length - 1) ///If end of list return true
				return true;
			else if (j == board[i].length - 1)
			{
				if (fillRest(board, i + 1, 0, sg, frame)) ///If last column go to next row, recur fillRest
					return true;
			}
			else
				if (fillRest(board, i, j + 1, sg, frame))/// recur fillRest
					return true;
			return false; ///If no possibilities, return false;
		}
		
		Integer[] randomNums = new Integer[squaresSquared]; ///Create List of all possible digits and shuffle, to avoid reusing numbers
		for (int k = 0; k < randomNums.length; k++)
		{
			randomNums[k] = k + 1;
		}
		List<Integer> numList = Arrays.asList(randomNums);
		Collections.shuffle(numList);
		Collections.shuffle(numList);
		Collections.shuffle(numList);
		
		for (int k = 0; k < numList.size(); k++)
		{
			if (isValid("" + numList.get(k), board, i, j))
			{
				Thread.sleep(speed);
				board[i][j] = "" + numList.get(k);
				sg.sudokuArray[i][j].setDigit("" + board[i][j], Color.green);
				frame.revalidate();
				if (i == board.length - 1 && j == board[i].length - 1)
				{
					Thread.sleep(speed);
					board[i][j] = "" + numList.get(k);
					sg.sudokuArray[i][j].setDigit("" + board[i][j], Color.white);
					frame.revalidate();
					return true;
				}
				else if (j == board[i].length - 1)
				{
					if (fillRest(board, i + 1, 0, sg, frame))
					{
						Thread.sleep(speed);
						board[i][j] = "" + numList.get(k);
						sg.sudokuArray[i][j].setDigit("" + board[i][j], Color.white);
						frame.revalidate();
						return true;
					}
				}
				else 
				{
					if (fillRest(board, i, j + 1, sg, frame))
					{
						Thread.sleep(speed);
						board[i][j] = "" + numList.get(k);
						sg.sudokuArray[i][j].setDigit("" + board[i][j], Color.white);
						frame.revalidate();
						return true;
					}
				}
			}
		}
		sg.sudokuArray[i][j].setColor(Color.red);
		board[i][j] = ".";
		return false;
	}
	
	///Verifies that the given value at the given index is valid by checking that it doesnt violate any of sudoku rules
	public static boolean isValid(String val, String[][] board, int row, int column)
    {
        int rowBox = row - (row % numberOfSquares);
        int columnBox = column - (column % numberOfSquares);
        for(int i = 0; i < board.length; i++)
            if(board[i][column].equals("" + val))
                return false;
        for(int i = 0; i < board[row].length; i++)
            if(board[row][i].equals("" + val))
                return false;
        for(int i = rowBox; i < rowBox + numberOfSquares; i++)
            for(int j = columnBox; j < columnBox + numberOfSquares; j++)
            {
                if(board[i][j].equals("" + val))
                    return false;
            }
        return true;
    }
	///Creates a list for each index in the puzzle, shuffles it and removes the first index values before amount to leave in the list.
	public static void createPuzzle(String[][] board, int amountToLeave, SudokuGrid sg, JFrame frame) throws InterruptedException
	{
		Integer[] randomNums = new Integer[board.length * board[0].length]; 
		for(int i = 0; i < randomNums.length; i++)
			randomNums[i] = new Integer(i);
		List<Integer> numList = Arrays.asList(randomNums);
		Collections.shuffle(numList);
		
		for (int i = 0; i < (board.length * board.length) - amountToLeave; i++)
		{
			
			int currentI = (numList.get(i).intValue() / board.length);
			int currentJ = (numList.get(i).intValue() % board.length);
			board[currentI][currentJ] = ".";
			sg.sudokuArray[currentI][currentJ].setDigit("    ", Color.white);
			frame.revalidate();
			Thread.sleep(speed);
			
		}
		
		
		
	}
}
