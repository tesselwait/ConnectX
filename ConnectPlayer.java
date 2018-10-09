import java.util.*;

public class ConnectPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private Connect gameBoard;
	private int[][] gameState;
	private Coordinate[] currentMoves;
	private Stack<int[][]> history;
	private Random gen;
	private boolean isHuman;
	private int playerMoves;
	Scanner keyboard;
	
	public ConnectPlayer(int x, String playerName, Connect board, boolean human) {
		playerNum = x;
		wins = 0;
		name = playerName;
		gameBoard = board;
		gameState = board.getBoard();
		history = new Stack<int[][]>();
		gen = new Random();
		isHuman = human;
		playerMoves=0;
		keyboard = new Scanner(System.in);
	}
	
	public int makeMove() {
		if(!isHuman) {
		gameState = gameBoard.getBoard();
		currentMoves = gameBoard.getLegalMoves();
		//printPotMoves(currentMoves);
		int nextMove = pickMove();
		return nextMove;
		}
		else
			return humanMove();
	}
	
	public int humanMove() {
		gameBoard.printBoard();
		System.out.println("Enter column.");
		int myint = keyboard.nextInt();
		if(myint >= 0 && myint < gameBoard.getWidth() && !(gameBoard.isColumnFull(myint)))
			return myint;
		else
			System.out.println("Invalid Move");
		return 0;
	}
	
	public void pushGameState(int[][] state) {
		history.push(state);
	}
	
	public int pickMove() {
		if(currentMoves.length==0)
			return findEmptySpace();
		return currentMoves[gen.nextInt(currentMoves.length)].getX();
	}
	
	public void printPotMoves(Coordinate[] moves) {
		System.out.println("pot moves player: " +playerNum);
		for(Coordinate a: moves) {
			System.out.println("potentialMove: x: "+ a.getX()+" y: "+a.getY());
		}
		System.out.println("end pot moves");
	}
	
	public int findEmptySpace() {
		for(int i=0; i<gameState.length; i++)
			for(int j=0; j<gameState[0].length; j++)
				if(gameState[i][j]==0)
					if(j==0 || (j!=0 && gameState[i][j-1]!=0))
						return i;
		return 0;		
	}
	
	public void reset() {
		history = new Stack<int[][]>();
		playerMoves=0;
	}
	
	public void incrementWins() {
		wins++;
	}
	
	public void incrementMoves() {
		playerMoves++;
	}
	
	public void resetPlayerMoves() {
		playerMoves=0;
	}
	
	public int getPlayerNum() {
		return playerNum;		
	}
	
	public int getWins() {
		return wins;
	}
	
	public int getPlayerMoves() {
		return playerMoves;
	}
	
	public String getName() {
		return name;
	}
	
	public Connect getHost() {
		return gameBoard;
	}
	
	public void printGameStack() {
		System.out.println("Game Print Start");
		while(history.size()>0)
			printGameState(history.pop());
		System.out.println("Game Print End");
		System.out.println();
	}
	
	public int[][] cloneGameState(int[][] c1) {
		int[][] c2 = new int[c1.length][c1[0].length];
		for(int i=0; i<c1.length; i++)
			for(int j=0; j<c1[0].length; j++)
			{
				c2[i][j] = c1[i][j];
			}
		return c2;
	}
	
	public void printGameState(int[][] state) {
		int width = state.length;
		int height = state[0].length;
		for(int j = height-1; j >= 0; j--)
		{
		  for(int i = 0; i< width; i++)
		  {
			  switch(state[i][j]) {
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
}
