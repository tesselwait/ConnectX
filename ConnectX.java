import java.util.*;
public class ConnectX {
	private int boardSize;
	private int[][] board;
	private int winNum, boardMultiplier, boardOffset;
	public ConnectXPlayer one, two;
	private int openSpaces, totalSpaces, gameMoves, totalMoves, totalGames;
	private Random gen;
	
	public ConnectX(int win, int multiplier, int offset){
		gen = new Random();
		winNum=win;
		boardMultiplier = multiplier;
		boardOffset = offset;
		boardSize = (boardMultiplier*winNum)+boardOffset;
		board = new int[boardSize][boardSize];
		totalSpaces = boardSize * boardSize;
		initializeBoard();
		one = new ConnectXPlayer(1, "one", this);
		two = new ConnectXPlayer(2, "two", this);
	}
	
	public void initializeBoard() {
		for(int i=0; i<boardSize; i++)
			for(int j=0; j<boardSize; j++)
				board[i][j]=0;
		openSpaces = totalSpaces;
		gameMoves=0;
	}
	
	public boolean playerTurn(int column, int player, ConnectxPlayer name) {
		if(openSpaces>0) {
			if(placePiece(column, player)) {
				gameMoves++;
				if(checkWin(column, player)){
					printWin(player);
					resetGame();
					return true;
				}
				else {
					//printBoard();
					return true;
				}
			}
			//System.out.println("Invalid move. Making random move.");
			playerTurn(gen.nextInt(boardSize), player, name);
			return false;
		}
		else {
			printDraw();
			resetGame();
		}
		return false;
	}
	
	public boolean placePiece(int x, int color) {
		if(board[x][boardSize-1]!=0)
			return false;
		int j=boardSize;
		for(int i=j; i>0&&board[x][i-1]==0; i--) {
			j--;
		}
		board[x][j] = color;
		openSpaces--;
		return true;
	}
	
	public boolean checkWin(int x, int color) {
		int a = boardSize-1;
		while(board[x][a-1]==0)
			a--;
		int y=a-1;
		int vertStreak=1;
		int horizStreak=1;
		int diagOneStreak=1;
		int diagTwoStreak=1;
		int i=1, j=1;
		while(y-j>=0 && board[x][y-j]==color) {
			vertStreak++;
			j++;
		}
		if(vertStreak>=winNum)
			return true;
		j=1;
		while(x+i<boardSize && y+j < boardSize && board[x+i][y+j]==color) {
			diagOneStreak++;
			i++;
			j++;
		}
		i=1; j=1;
		while(x-i>=0 && y-j>=0 && board[x-i][y-j]==color) {
			diagOneStreak++;
			i++;
			j++;
		}
		if(diagOneStreak>=winNum)
			return true;
		i=1; j=1;
		while(x+i<boardSize && y-j>=0 && board[x+i][y-j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		i=1; j=1;
		while(x-i>=0 && y+j<boardSize && board[x-i][y+j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		if(diagTwoStreak>=winNum)
			return true;
		i=1; j=1;
		while(x+i<boardSize && board[x+i][y]==color) {
			horizStreak++;
			i++;
		}
		i=1; j=1;
		while(x-i>=0 && board[x-i][y]==color) {
			horizStreak++;
			i++;
		}
		if(horizStreak>=winNum)
			return true;
		return false;
	}
	
	public int getTotalGames() {
		return totalGames;
	}
	
	public int getAverageMoves() {
		return (int)(totalMoves/totalGames);
	}
	
	public void printWin(int player) {
		//System.out.println("Player: "+player+" wins.");
		if(player==1)
			one.incrementWins();
		else
			two.incrementWins();
		//printBoard();
	}
	
	public void printDraw() {
		//System.out.println("Game was a draw.");
		//printBoard();
	}
	
	public void resetGame() {
		//System.out.println();
		//System.out.println("New Game");
		totalMoves+= gameMoves;
		totalGames++;
		initializeBoard();
		//printBoard();
	}
	
	public void printBoard() {
		for(int j = boardSize-1; j >= 0; j--)
		{
		  for(int i = 0; i< boardSize; i++)
		  {
			  switch(board[i][j]) {
			  case 1:
				  System.out.print("X ");
				  break;
			  case 2:
				  System.out.print("O ");
			      break;
			  
			  case 0:
				  System.out.print("_ ");
			  	  break;
			  }
		  }
		  System.out.println();
	    }
		System.out.println();
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public int[][] getBoard(){
		return board;
	}
}
