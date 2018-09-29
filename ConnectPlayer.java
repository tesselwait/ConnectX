import java.util.*;

public class ConnectPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private Connect gameBoard;
	private int[][] gameState;
	private Coordinate[] currentMoves;
	private Coordinate lastMove;
	private Stack<int[][]> history;
	private Random gen;
	
	
	public ConnectPlayer(int x, String playerName, Connect board) {
		playerNum = x;
		wins = 0;
		name = playerName;
		gameBoard = board;
		gameState = board.getBoard();
		history = new Stack<int[][]>();
		gen = new Random();
	}
	
	
	public int makeMove() {
		gameState = gameBoard.getBoard();
		history.push(cloneGameState(gameState));
		currentMoves = gameBoard.getLegalMoves();
		printPotMoves(currentMoves);
		System.out.println();
		int nextMove = pickMove();
		lastMove = new Coordinate(nextMove, gameBoard.findMoveInCol(nextMove).getY());
		gameState[lastMove.getX()][lastMove.getY()] = playerNum;
		return nextMove;
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
		for(Coordinate a: moves) {
			System.out.println("potentialMove: x: "+ a.getX()+" y: "+a.getY());
		}
	}
	
	public int findEmptySpace() {
		for(int i=0; i<gameState.length; i++)
			for(int j=0; j<gameState[0].length; j++)
				if(gameState[i][j]==0)
					if(j==0 || (j!=0 && gameState[i][j-1]!=0))
						return i;
		return 0;		
	}
	
	public void incrementWins() {
		wins++;
	}
	
	public int getPlayerNum() {
		return playerNum;		
	}
	
	public int getWins() {
		return wins;
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
