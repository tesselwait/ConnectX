//Old algorithm implemented on arbitrary board parameters without gridworld.
import java.util.*;
public class ConnectComputerBoard {
	private int[][] board;
	private int winNum, boardWidth, boardHeight;
	public ConnectComputerPlayer one, two;
	private int totalSpaces, gameMoves, totalGames, draws;
	private boolean playerBoolean;
	private Coordinate lastMove;

	public ConnectComputerBoard(int win, int width, int height, int human){
		winNum=win;
		boardWidth = width;
		boardHeight = height;
		totalSpaces = boardWidth * boardHeight;
		board = new int[boardWidth][boardHeight];
		draws = 0;
		initializeBoard();
		if(human==1) {
			one = new ConnectComputerPlayer(1, "one", this, true);
			two = new ConnectComputerPlayer(2, "two", this, false);
		}
		if(human==0) {
			one = new ConnectComputerPlayer(1, "one", this, false);
			two = new ConnectComputerPlayer(2, "two", this, false);
		}
		if(human==2) {
			one = new ConnectComputerPlayer(1, "one", this, true);
			two = new ConnectComputerPlayer(2, "two", this, true);
		}
		playerBoolean = true;
	}
	
	public void initializeBoard() {
		for(int i=0; i<boardWidth; i++)
			for(int j=0; j<boardHeight; j++)
				board[i][j]=0;
		gameMoves=0;
	}
	
	public void runGames(int x) {
		while(totalGames<x) {
			playerTurn(getCurrentPlayer().makeMove(), getCurrentPlayer().getPlayerNum());
		}
		System.out.println("Complete");
	}
	
	public boolean playerTurn(int column, int player) {
		if(gameMoves<totalSpaces) {
			if(placePiece(column, player)) {
				printBoard();
				getWaitingPlayer().pushGameState(getWaitingPlayer().cloneGameState(board));
				getCurrentPlayer().pushGameState(getCurrentPlayer().cloneGameState(board));
				if(checkWin(lastMove, player)){
					win(player);
					return true;
				}
				nextPlayer();
				return true;
			}
			return false;
		}
		else
		{
			draw();
			return true;
		}
	}
	
	public int[] getPlayableColumns() {
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<boardWidth; i++) {
			if(board[i][boardHeight-1]==0)
				temp.add(i);
		}
		int[] list = new int[temp.size()];
		for(int j=0; j<temp.size(); j++)
			list[j] = temp.get(j);
		return list;
	}
	
	public Coordinate[] getLegalMoves() {
		int[] cols = getPlayableColumns();
		Coordinate[] moves = new Coordinate[cols.length];
		for(int i=0; i<cols.length; i++) {
			moves[i] = findMoveInCol(cols[i]);
		}
		return moves;
	}
	
	public Coordinate findMoveInCol(int x) {
		int m = boardHeight-1;
		while(playableLocation(x, m)==false)
			m--;
		return new Coordinate(x, m);
	}
	
	public boolean isColumnFull(int col) {
		if(board[col][boardHeight-1]!=0)
			return true;
		return false;
	}
	
	public boolean playableLocation(int x, int y) {
		if(x<0||y<0||x>boardWidth-1||y>boardHeight-1)
			return false;
		if(board[x][y]==0) {
			if(y==0)
				return true;
			if(board[x][y-1]==0)
				return false;
			else
				return true;
		}
		else
			return false;
	}
	
	public boolean placePiece(int x, int color) {
		if(isColumnFull(x))
			return false;
		int j=boardHeight;
		for(int i=j; i>0&&board[x][i-1]==0; i--) {
			j--;
		}
		lastMove = new Coordinate(x, j);
		board[x][j] = color;
		getCurrentPlayer().incrementMoves();
		gameMoves++;
		return true;
	}
	
	public boolean checkWin(Coordinate loc, int color) {
		int x = loc.getX();
		int y = loc.getY();
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
		while(x+i<boardWidth && y+j < boardHeight && board[x+i][y+j]==color) {
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
		while(x+i<boardWidth && y-j>=0 && board[x+i][y-j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		i=1; j=1;
		while(x-i>=0 && y+j<boardHeight && board[x-i][y+j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		if(diagTwoStreak>=winNum)
			return true;
		i=1; j=1;
		while(x+i<boardWidth && board[x+i][y]==color) {
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
	
	public void win(int player) {
		System.out.println("Player: "+player+" wins.");
		if(player==1) {
			one.incrementWins();
			//one.printGameStack();
		}
		else {
			two.incrementWins();
			//two.printGameStack();
		}
		printBoard();
		resetGame();
	}
	
	public void draw() {
		//one.printGameStack();
		System.out.println("Game was a draw.");
		printBoard();
		draws++;
		resetGame();
	}
	
	public void resetGame() {
		totalGames++;
		printStats();
		getCurrentPlayer().reset();
		getWaitingPlayer().reset();
		System.out.println();
		System.out.println("New Game");
		gameMoves = 0;
		initializeBoard();
	}
	
	public void printBoard() {
		//System.out.print("  ");
		//for(int i=0; i<boardWidth; i++)
			//System.out.print("- ");
		//System.out.println();
		for(int j = boardHeight-1; j >= 0; j--)
		{
			//System.out.print("| ");
		  for(int i = 0; i< boardWidth; i++)
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
		  //System.out.println("| "+j);
		  System.out.println();
	    }
		//System.out.print("  ");
		//for(int i=0; i<boardWidth; i++)
			//System.out.print("- ");
		System.out.println();
		//System.out.print("  ");
		for(int i=0; i<boardWidth; i++)
			System.out.print(i%10+" ");
		System.out.println();
	}
	
	public Coordinate getLastMove() {
		return lastMove;
	}
	
	public int getTotalSpaces() {
		return totalSpaces;
	}
	
	public int getWinNum() {
		return winNum;
	}
	
	public int getWidth() {
		return boardWidth;
	}
	
	public int getHeight() {
		return boardHeight;
	}
	
	public int[][] getBoard(){
		return board;
	}
	
	public int getDraws() {
		return draws;
	}
	
	public ConnectComputerPlayer getCurrentPlayer() {
		if(getPlayer())
			return one;
		else
			return two;
	}
	
	public ConnectComputerPlayer getWaitingPlayer() {
		if(!getPlayer())
			return one;
		else
			return two;
	}
	
	public boolean getPlayer() {
		return playerBoolean;
	}
	
	public void nextPlayer() {
		playerBoolean = !playerBoolean;
	}
	
	public void printStats() {
		System.out.println("Stats for Connect"+winNum+"  on: "+boardWidth+"x"+boardHeight+" board.");
		System.out.println("P1 Win: "+ one.getWins());
		System.out.println("P2 Win: "+ two.getWins());
		System.out.println("Draws: "+ getDraws());
		System.out.println("Total Games: "+ totalGames);
		System.out.println();
	}
	
	
	public int[] getStreaks(Coordinate loc, int color) {
		int[] streaks = new int[4];
		int x = loc.getX();
		int y = loc.getY();
		int vertStreak=1;
		int horizStreak=1;
		int diagOneStreak=1;
		int diagTwoStreak=1;
		int i=1, j=1;
		while(y-j>=0 && board[loc.getX()][y-j]==color) {
			vertStreak++;
			j++;
		}
		j=1;
		while(x+i<boardWidth && y+j < boardHeight && board[x+i][y+j]==color) {
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
		i=1; j=1;
		while(x+i<boardWidth && y-j>=0 && board[x+i][y-j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		i=1; j=1;
		while(x-i>=0 && y+j<boardHeight && board[x-i][y+j]==color) {
			diagTwoStreak++;
			i++;
			j++;
		}
		i=1; j=1;
		while(x+i<boardWidth && board[x+i][y]==color) {
			horizStreak++;
			i++;
		}
		i=1; j=1;
		while(x-i>=0 && board[x-i][y]==color) {
			horizStreak++;
			i++;
		}
		streaks[0] = vertStreak;
		streaks[1] = horizStreak;
		streaks[2] = diagOneStreak;
		streaks[3] = diagTwoStreak;
		return streaks;
}
	

	public static void main(String[] args) {
		for(int c=6; c<7; c++) {
			ConnectComputerBoard test = new ConnectComputerBoard(c, 7*c/2, (5*c/2)+1, 1);//(connects to win, board width, board height, human players[0||1||2])
			test.runGames(10);
		}
	}
}
