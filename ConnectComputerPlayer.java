//Old algorithm implemented on arbitrary board parameters.
import java.util.*;
public class ConnectComputerPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private ConnectComputerBoard gameBoard;
	private int[][] gameState;
	private Coordinate[] currentMoves;
	private Stack<int[][]> history;
	private Random gen;
	private boolean isHuman;
	private int playerMoves;
	Scanner keyboard;
	
	public ConnectComputerPlayer(int x, String playerName, ConnectComputerBoard board, boolean human) {
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
		
	    Coordinate finishingMove = getGameEnders(currentMoves);
	    if (finishingMove != null){
	      return finishingMove.getX();
	    }
	    Coordinate[] secondPotentialMoves = findPotentialMoves(currentMoves);
	    if (secondPotentialMoves.length <= 0){
	      return pickMove();
	    }
	    Coordinate idealMove = findIdealMove(secondPotentialMoves);
	    if (secondPotentialMoves.length <= 0){
	      return pickMove();
	    }
	    return idealMove.getX();
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
	
	public ConnectComputerBoard getHost() {
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
	
	public int sumFactorials(int[] list){
		int streakTotal = 0;
		for (int x : list){
			int streakFactorial = 0;
			for (int i = x; i > 0; i--)
				streakFactorial += i;
			streakTotal += streakFactorial;
		}
		return streakTotal;
	}
	
	public Coordinate getGameEnders(Coordinate[]  potentialMoves){
		  for(Coordinate location : potentialMoves){
		    if (gameBoard.checkWin(location, getPlayerNum())){
		      return location;
		    }
		  }
		  for(Coordinate location : potentialMoves){
			    if(gameBoard.checkWin(location, gameBoard.getWaitingPlayer().getPlayerNum())){
			      return location;
			  }
		}
		  for(Coordinate location : potentialMoves){
			if(location.getY()<gameBoard.getHeight()-2)
				if(!gameBoard.checkWin(new Coordinate(location.getX(), location.getY()+1), gameBoard.getWaitingPlayer().getPlayerNum()))
					if(gameBoard.checkWin(new Coordinate(location.getX(), location.getY()+1), getPlayerNum()))
						if(gameBoard.checkWin(new Coordinate(location.getX(), location.getY()+2), getPlayerNum()))
							return location;
		  }
		  for(Coordinate location : potentialMoves) {
			if(location.getY()<gameBoard.getHeight()-1) { 
		      if(!gameBoard.checkWin(new Coordinate(location.getX(), location.getY()+1), gameBoard.getWaitingPlayer().getPlayerNum())){
		    	gameBoard.getBoard()[location.getX()][location.getY()]=getPlayerNum();  
		    	Coordinate[] potentialMoves2 = new Coordinate[gameBoard.getWidth()];
		    	for(int i = 0; i < gameBoard.getWidth(); i++){
		    		if(gameBoard.getBoard()[i][gameBoard.getHeight()-1]==0){
		    			potentialMoves2[i] = new Coordinate(i, gameBoard.findMoveInCol(i).getY());
		    		}
		        }
		        int winLocs = 0;
		        for (Coordinate loc2 : potentialMoves2){
		        	if (loc2!=null) {
		        		if (gameBoard.checkWin(loc2, gameBoard.getWaitingPlayer().getPlayerNum())){
		        			winLocs++;
		        			if (loc2.getY()<gameBoard.getHeight()-1 && (gameBoard.checkWin(new Coordinate(loc2.getX(), loc2.getY()+1), getPlayerNum())) || (winLocs >= 2)){
		        				gameBoard.getBoard()[location.getX()][ location.getY()]=0;
		        				return location;
		        			}
		        		}
		        	}
		        }
		        gameBoard.getBoard()[location.getX()][ location.getY()]=0;
		      }
		    }
		  }
		    return null;
		  }
	

	public Coordinate[] findPotentialMoves(Coordinate[] firstPotentialMoves){
		ArrayList<Coordinate> secondPotentialMovesList = new ArrayList<Coordinate>();
		Coordinate[] secondPotentialMoves;
		for(int i = 0; i < firstPotentialMoves.length; i++){
			if(firstPotentialMoves[i].getY()<gameBoard.getHeight()-1) {
				int[] nextStreaks = gameBoard.getStreaks(new Coordinate(firstPotentialMoves[i].getX(), firstPotentialMoves[i].getY()+1), gameBoard.getWaitingPlayer().getPlayerNum());
				boolean threeInRow = false;
				for(Integer num : nextStreaks){
					if(num.intValue() >= gameBoard.getWinNum()-2){
						threeInRow = true;
					}
				}
				if(!threeInRow) {
					secondPotentialMovesList.add(firstPotentialMoves[i]);
				}
			}
		}
		secondPotentialMoves = new Coordinate[secondPotentialMovesList.size()];
		for(int i=0; i<secondPotentialMovesList.size(); i++)
			secondPotentialMoves[i] = secondPotentialMovesList.get(i);
		return secondPotentialMoves;
	}
	
	public Coordinate findIdealMove(Coordinate[] potentialMoves){
		Coordinate greatestStreakLoc = potentialMoves[0];
		int greatestStreakTotal = 0;
		for (Coordinate location : potentialMoves){
			int tempStreakTotal = 0;
			int[] opStreaks = gameBoard.getStreaks(location, gameBoard.getWaitingPlayer().getPlayerNum());
			int opStreakSum = sumFactorials(opStreaks);
			tempStreakTotal += opStreakSum;
			int[] myStreaks = gameBoard.getStreaks(location, playerNum);
			int myStreakSum = sumFactorials(myStreaks);
			if(myStreakSum >= 2)
				tempStreakTotal += myStreakSum;
			int aboveStreakTotal = 0;
			if(location.getY() < gameBoard.getHeight()-1){
				int[] aboveStreaks = gameBoard.getStreaks(new Coordinate(location.getX(), location.getY()+1), getPlayerNum());
				int aboveStreakFactorialSum = sumFactorials(aboveStreaks); 
				int[] aboveOpStreaks = gameBoard.getStreaks(new Coordinate(location.getX(), location.getY()+1), gameBoard.getWaitingPlayer().getPlayerNum());
				int aboveOpStreakFactorialSum = sumFactorials(aboveOpStreaks);
				aboveStreakTotal = aboveStreakFactorialSum + (int)(aboveOpStreakFactorialSum * 0.8) ;
			}
			tempStreakTotal -= (int)(aboveStreakTotal / 1.0/*5D*/);
			if(tempStreakTotal > greatestStreakTotal){
				greatestStreakTotal = tempStreakTotal;
				greatestStreakLoc = location;
			}
		}
		return greatestStreakLoc;
	}

}
