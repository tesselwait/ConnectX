public class ConnectXPlayer {
	private int playerNum;
	private int wins;
	private String name;
	private ConnectX gameBoard;
	
	public ConnectXPlayer(int x, String playerName, ConnectX board) {
		gameBoard = board;
		playerNum = x;
		wins = 0;
		name = playerName;
	}
	
	public int makeMove(int[][] board) {
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
}
