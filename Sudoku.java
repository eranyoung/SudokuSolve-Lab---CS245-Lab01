import java.util.ArrayList;
import java.util.Scanner;


public class Sudoku{
	
	private int [][]sudokuPuzzle;


	public Sudoku(int [][] sudokuPuzzle){
			this.sudokuPuzzle=sudokuPuzzle;
	}

	public boolean validateBoard(int [][] board)//validates that initial board meets all constraints
	{
		for(int r=0;r<9;r++){
			for(int c=0;c<9;c++){
				if(board[r][c]>0){
					if(!(isSafe(r,c,board[r][c],board))){//Checks each value that is not initilized(not zero)to not be repeated
						return false;//in its respective row, column and subgrid
					}
				}
			}
		}
		return true;
	}

	

	public void setBoard(int [][] board){//copies board in its current state to the instance variable sudokuPuzzle
		for(int r=0;r<9;r++)
		{
			for(int c=0;c<9;c++)
			{
				sudokuPuzzle[r][c]=board[r][c];
			}
		}
	}

	public void printBoard()//prints stored Sudoku board
	{
		String row="";
		for(int r=0;r<9;r++){
			for(int c=0;c<9;c++){
				row=row+" "+sudokuPuzzle[r][c];
			}
			System.out.println(row);
			row="";
		}
	}

	public boolean isSafe(int row, int col, int val, int [][] board){//consolidates each checker function(checkRow, checkColumn, checkBox)
		if((checkRow(row,col,val,board)&&checkColumn(row,col,val,board)&&checkBox(row,col,val,board))){
			return true;
		}
		return false;
	}

	public boolean checkRow(int row, int col, int val, int [][] board)
	{
		for(int c=0;c<9;c++){
			if(board[row][c]==val&&c!=col)
				return false;
		}
		return true;
	}

	public boolean checkColumn(int row, int col, int val, int [][] board)
	{
		for(int r=0;r<9;r++){
			if(board[r][col]==val&&row!=r)
				return false;
		}
		return true;
	}

	public boolean checkBox(int row, int col, int val,int [][] board)
	{
		for(int r=(row/3)*3;r<((row/3)*3)+3;r++){
			for(int c=(col/3)*3;c<((col/3)*3)+3;c++){
				if(board[r][c]==val&&row!=r&&col!=c)
					return false;
			}
		}
		return true;
	}

	

	public boolean solve(int [][] board){//validates board and if it is valid, calls recursive backtracking method solveSudoku
		if(validateBoard(board))
			return solveSudoku(0,0,board);
		else
			return false;
	}

	public boolean solveSudoku(int r, int c, int [][] board)//recursive backtracking method to solve the given puzzle
	{
		int row=r;
		int col=c;

		if(row==9)//base case: if row is 9 then the puzzle has been solved
				return true;

		if(board[row][col]==0){//if the spot has not been initialized, checks the current cell 
			for(int val=1;val<=9;val++){//checks values 1 - 9
				if(isSafe(row, col, val, board)){//validates number
					board[row][col]=val;
					if(col==8){//end of row, move on to next row
						if(solveSudoku(row+1,0,board)){//recursive call 
							this.setBoard(board);
							return true;
						}
					}
					else{
						if(solveSudoku(row,col+1,board)){
							this.setBoard(board);
							return true;
						}
					}
				}
			}
		}
		else{//cell contained a value when initiliazed, skip this cell
			if(col==8){
				if(solveSudoku(row+1,0,board)){
					this.setBoard(board);
					return true;
				}
			}
			else{
				if(solveSudoku(row,col+1,board)){
					this.setBoard(board);
					return true;
				}
			}
			return false;//we dont reset this cell when backtracking because it can't be changed
		}
		
		board[row][col]=0;//set cell back to empty value
		return false;//backtrack
	}

	public static void main(String[] args)
	{
		int [][] board=new int[9][9];

		Scanner scanner=new Scanner(System.in);
		for(int r=0;r<9;r++){//input numbers one at a time
			for(int c=0;c<9;c++){
				board[r][c]=scanner.nextInt();
			}
		}

		Sudoku s=new Sudoku(board);
		System.out.println("\nInitial Board: \n");
		s.printBoard();
		if(s.solve(board)){
			System.out.println("\nSolved: \n");
			s.printBoard();
		}
		else{
			System.out.println("Invalid Board");
		}

	}
}
