public class ConnectEnumerator {
	//Enumerates set of all possible winning combinations given board width, height, and win parameters.
	private Coordinate[][] winSet;
	public ConnectEnumerator() {
		winSet= null;
	}
	public ConnectEnumerator(int x, int y, int a) {
		winSet = enumerateWinners(x, y, a);
	}
	
	public Coordinate[][] enumerateWinners(int width, int height, int win){
		int x = width;
		int y = height;
		int a = win;
		Coordinate[][] s = new Coordinate[2*(x-a+1)*(y-a+1)+(x*(y-a+1))+(y*(x-a+1))][a];
		//System.out.println("Set size: "+(2*(x-a+1)*(y-a+1)+(x*(y-a+1))+(y*(x-a+1))));
		int r = 0;
		for(int i=0; i<x-a+1; i++) {
			for(int j=0; j<y-a+1; j++) {
					for(int l=0; l<a; l++) {
						s[r][l] = new Coordinate(i+l,j);
						s[r+1][l] = new Coordinate(i, j+l);
						s[r+2][l] = new Coordinate(i+l, j+l);
						s[r+3][l] = new Coordinate(i+l, j+a-l);
					}
				r+=4;
			}
			//System.out.println("r: "+ r);
		}
		//System.out.println("end first set");
		for(int i=x-a+1; i<x; i++) {
			for(int j=0; j<(y-a)+1; j++) {
					for(int l=0; l<a; l++) {
						s[r][l] = new Coordinate(i, j+l);
					}
				r++;
			}
			//System.out.println("r: "+ r);
		}
		//System.out.println("end second set");
	
		for(int i=0; i<x-a+1; i++) {
			for(int j=(y-a)+1; j<y; j++) {
				for(int l=0; l<a; l++) {
					s[r][l] = new Coordinate(i+l, j);
				}
				r++;
			}
			//System.out.println("r: "+r);
		}
		//System.out.println("end third set");
		//System.out.println();
		return s;
	}
	
	public Coordinate[][] getWinners() {
		if(winSet!=null)
			return winSet;
		else
			System.out.println("No instance of set.");
		return null;
	}
	
	public void setWinSet(int x, int y, int a) {
		winSet = enumerateWinners(x, y, a);
	}
	
	public void printWinSet(int x, int y, int a) {
		Coordinate[][] g = enumerateWinners(x,y,a);
		for(Coordinate[] h: g) {
			for(Coordinate i: h) {
					System.out.println("X: "+ i.getX() + " Y: "+ i.getY());
			}
			System.out.println();
		}
	}	
	
	public void printWinSet() {
		for(Coordinate[] h: winSet) {
			for(Coordinate i: h) {
					System.out.println("X: "+ i.getX() + " Y: "+ i.getY());
			}
			System.out.println();
		}
	}
}
