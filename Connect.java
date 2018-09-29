import java.util.*;
public class Connect {
	private int boardSize;
	private int[][] board;
	private int winNum, boardWidth, boardHeight;
	public ConnectPlayer one, two;
	private int openSpaces, totalSpaces, gameMoves, totalMoves, totalGames, draws;
	private Random gen;
	private boolean playerBoolean;

	public Connect(int win, int width, int height){
		gen = new Random();
		winNum=win;
		boardWidth = width;
		boardHeight = height;
		boardSize = boardWidth * boardHeight;
		board = new int[boardWidth][boardHeight];
		totalSpaces = boardSize;
		draws = 0;
		initializeBoard();
		one = new ConnectPlayer(1, "one", this);
		two = new ConnectPlayer(2, "two", this);
		playerBoolean = true;
	}
	
	public void initializeBoard() {
		for(int i=0; i<boardWidth; i++)
			for(int j=0; j<boardHeight; j++)
				board[i][j]=0;
		openSpaces = totalSpaces;
		gameMoves=0;
	}
	
	public void runGames(int x) {
		while(totalGames<x) {
			playerTurn(getCurrentPlayer().makeMove(), getCurrentPlayer());
		}
	}
	
	public boolean playerTurn(int column, ConnectPlayer curPlayer) {//still debugging
		int player = curPlayer.getPlayerNum();
		if(openSpaces>0) {
			if(placePiece(column, player)) {
				printBoard();
				getWaitingPlayer().pushGameState(curPlayer.cloneGameState(board));
				curPlayer.pushGameState(curPlayer.cloneGameState(board));
				if(checkWin(column, player)){
					win(player);
					nextPlayer();
					resetGame();
					return true;
				}
				else {
				//printBoard();
				nextPlayer();
				return true;
				}
			}
			else {
			randomTurn(/**getCurrentPlayer().makeMove(),**/ getCurrentPlayer());
			return false;
			}
		}
		else {
			printDraw();
			resetGame();
		}
		return false;
	}
	
	
	public boolean randomTurn(ConnectPlayer curPlayer) {//still debugging
		int player = curPlayer.getPlayerNum();
		Coordinate[] legalMoves = getLegalMoves();
		if(legalMoves.length==0) {
			System.out.println("No valid moves.");
			printBoard();
			return false;
		}
		int move = legalMoves[gen.nextInt(legalMoves.length)].getX();
		if(placePiece(move, player)) {
			getWaitingPlayer().pushGameState(curPlayer.cloneGameState(board));
			curPlayer.pushGameState(curPlayer.cloneGameState(board));
			if(checkWin(move, player)) {
				win(player);
				nextPlayer();
				resetGame();
				return true;
			}
			else {
				//printBoard();
				nextPlayer();
				return true;
			}
		}
		else
			System.out.println("Error. Random move did not find a valid move location.");
			return false;
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
		if(col<0 || col>boardWidth) {
			System.out.println("Column is not on the board");
			return true;
		}
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
		board[x][j] = color;
		gameMoves++;
		openSpaces--;
		return true;
	}
	
	public boolean checkWin(int x, int color) {
		int a = boardHeight-1;
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
	
	public int getAverageMoves() {
		return (int)(totalMoves/totalGames);
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
	}
	
	public void printDraw() {
		System.out.println("Game was a draw.");
		printBoard();
		draws++;
	}
	
	public void resetGame() {
		printStats();
		//System.out.println();
		//System.out.println("New Game");
		totalMoves+= gameMoves;
		totalGames++;
		initializeBoard();
	}
	
	public void printBoard() {
		for(int j = boardHeight-1; j >= 0; j--)
		{
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
		  System.out.println();
	    }
		System.out.println();
	}
	
	public int getBoardSize() {
		return boardSize;
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
	
	public ConnectPlayer getCurrentPlayer() {
		if(getPlayer())
			return one;
		else
			return two;
	}
	
	public ConnectPlayer getWaitingPlayer() {
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
	public static void main(String[] args) {
		for(int c=8; c<9; c++) {
			Connect test = new Connect(c, (5*c)/2, (2*c)-1);
			test.runGames(5);
		//while(test.totalGames < 10) {
			//test.playerTurn(/**test.getCurrentPlayer().makeMove(),**/ test.getCurrentPlayer());
		//}
		//System.out.println("Stats for Connect"+test.winNum+"  on: "+test.boardWidth+"x"+test.boardHeight+" board.");
		//System.out.println("P1 Win: "+ test.one.getWins());
		//System.out.println("P2 Win: "+ test.two.getWins());
		//System.out.println("Draws: "+ (test.totalGames - (test.two.getWins()+test.one.getWins())));
		//System.out.println("Total Games: "+ test.getDraws());
		//System.out.println();
		}
	}
}
