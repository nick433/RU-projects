package model;
import java.util.Objects;
import java.util.Random;




public class Coordinate {
	
	int x, y, start;
	
	public Coordinate(int x, int y, int start) {
		this.x = x;
		this.y = y;
		this.start = start;
	}
	
	public Coordinate(int x, int y) {
		this(x, y, -1);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Coordinate)) {
			return false;
		} else {
			Coordinate compareto = (Coordinate)o;
			return ((this.x == compareto.x) && (this.y == compareto.y));
		}
	}
	
	@Override
    public int hashCode()
    {
		return Objects.hash(this.x, this.y);
    }

	public static Coordinate hardToTraverse(){
		Random r = new Random();
		int x = r.ints(0,120).findFirst().getAsInt(); //figure out the distance where center can be so area is 31x31 cells.
		int y = r.ints(0,160).findFirst().getAsInt();
		
		Coordinate c = new Coordinate(x, y);
		return c;
	}
	
	public Coordinate river(){
		Random r = new Random();
		int xOrY = r.ints(0,4).findFirst().getAsInt();
		int x = 0,y = 0;
		switch(xOrY){
		case 0: y = 0;
				x = r.ints(1,119).findFirst().getAsInt();
				break;
		
		case 1: x = 0;
				y = r.ints(1,159).findFirst().getAsInt();
				break;
				
		case 2: y = 159;
				x = r.ints(1,119).findFirst().getAsInt();
				break;
				
		case 3: x = 119;
				y = r.ints(1,159).findFirst().getAsInt();
				break;
		}
		
		Coordinate c = new Coordinate(x, y, xOrY);
		return c;
	}
	
	public static Coordinate startGoal(){
		Random r = new Random();
		int x = 0,y = 0;
		int topOrBottom = r.ints(0,2).findFirst().getAsInt();
		int leftOrRight = r.ints(0,2).findFirst().getAsInt();
		
		switch(topOrBottom){
		case 0: x = r.ints(0,20).findFirst().getAsInt();
				break;
				
		case 1: x = r.ints(99,120).findFirst().getAsInt();
				break;
		}
		
		switch(leftOrRight){
		case 0: y = r.ints(0,20).findFirst().getAsInt();
				break;
				
		case 1: y = r.ints(139,160).findFirst().getAsInt();
				break;
		}
		
		Coordinate c = new Coordinate(x,y,-1);
		return c;
	}
	
	public Coordinate blocked(){
		Random r = new Random();
		int x = r.ints(0,120).findFirst().getAsInt();
		int y = r.ints(0,160).findFirst().getAsInt();
		Coordinate c = new Coordinate(x,y,-1);
		return c;
	}
	
	public String toString(){
		return this.x + " " + this.y;
	}
	
}