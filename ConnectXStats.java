import java.util.Random;
public class ConnectXStats//Default parameters take about 10 minutes to run.
{
	private Random generator;
	private int subSampleSize, winNum, boardMultiplier, offsetDivisor;
	private int winNumMax, boardMultiMax;
	private int offset;
	private ConnectX Board;
	public ConnectXStats(int w, int x, int a, int z, int y, int b) {
		subSampleSize = w;
		winNum = x;
		winNumMax = a;
		offsetDivisor = z;
		boardMultiplier = y;
		boardMultiMax = b;
		Board = new ConnectX(x, y, z);
		generator = new Random();
	}
	
	public ConnectXStats(int w, int x, int a, int y, int b) {
		subSampleSize = w;
		winNum = x;
		winNumMax = a;
		boardMultiplier = y;
		offset = -1;
		boardMultiMax = b;
		Board = new ConnectX(x, y, offset);
		generator = new Random();
	}
	
	public void setBoard(int a, int b, int c) {
		Board = new ConnectX(a, b, c);
	}
	
	public int getSubSampleSize() {
		return subSampleSize;
	}
	
	public int getWinNum() {
		return winNum;
	}
	
	public int getBoardMultiplier() {
		return boardMultiplier;
	}
	
	public int getOffsetDivisor() {
		return offsetDivisor;
	}
	
	public int getWinNumMax() {
		return winNumMax;
	}
	
	public int getBoardMultiplierMax() {
		return boardMultiMax;
	}
	
	public ConnectX getBoard() {
		return Board;
	}
	
	public int getDefaultOffset() {
		return offset;
	}
	
	
	public static void main(String[] args) {
		final long startTime = System.currentTimeMillis();
		//ConnectXStats conStats = new ConnectXStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), /**Integer.parseInt(args[5])**/);
		ConnectXStats conStats = new ConnectXStats(100, 22, 23, 70, 80);//(sample size, minX, maxX, minMultiplier, maxMultiplier)
		System.out.println("Total Games per subsample: "+ conStats.getSubSampleSize());
		System.out.println();
		for(int c=conStats.getWinNum(); c<conStats.getWinNumMax(); c++) {
			for(int d=conStats.getBoardMultiplier(); d<conStats.getBoardMultiplierMax(); d++) {
				/**for(int e=-conStats.getWinNum()/conStats.getOffsetDivisor(); e<conStats.getWinNum()/conStats.getOffsetDivisor(); e++) {**/
					
					conStats.setBoard(c, d, -1/**e**/);
					//conStats.getBoard().printBoard();
					while(conStats.getBoard().getTotalGames() < conStats.getSubSampleSize()) {
						conStats.getBoard().playerTurn(conStats.generator.nextInt(conStats.getBoard().getBoardSize()), 1, conStats.getBoard().one);
						conStats.getBoard().playerTurn(conStats.generator.nextInt(conStats.getBoard().getBoardSize()), 2, conStats.getBoard().two);
					}
					System.out.println("Stats for Connect"+c+" Board size = "+conStats.getBoard().getBoardSize()+".");
					System.out.println("Player one wins: "+ conStats.getBoard().one.getWins());
					System.out.println("Player two wins: "+ conStats.getBoard().two.getWins());
					System.out.println("Draws: "+ (conStats.getBoard().getTotalGames() - (conStats.getBoard().two.getWins()+conStats.getBoard().one.getWins())));
					System.out.println();
				}
			/**}**/
		}
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execustion time: " + (endTime - startTime));
	}
}
