public class ConnectXPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private ConnectX gameBoard;
	
	
	public ConnectXPlayer(int x, String playerName, ConnectX board) {
		playerNum = x;
		wins = 0;
		name = playerName;
		gameBoard = board;
	}
	
	
	public int makeMove() {
		return 1;
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
	
	public ConnectX getBoard() {
		return gameBoard;
	}
}
