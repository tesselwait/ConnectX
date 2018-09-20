public class ConnectPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private Connect gameBoard;
	
	
	public ConnectPlayer(int x, String playerName, Connect board) {
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
	
	public Connect getBoard() {
		return gameBoard;
	}
}
