package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Map {
	
	public static final int IMPASSABLE = 0;
	public static final int UNBLOCKED = 1;
	public static final int HARD = 2;
	public static final int UNBLOCKEDHIGHWAY = 3;
	public static final int HARDHIGHWAY = 4;
	
	int[][] cellType = new int[120][160];
	Coordinate[] hard = new Coordinate[8];
	
	public Map()
	{
		this.initMapWith(1);
		this.initHard();
	//	this.addHard();
		this.initRiver();
		this.blockedCells();	
	}
	
	private boolean AdequateStartGoalDistance(Coordinate start, Coordinate goal)
	{
		return ((Math.abs(start.x - goal.x) + (Math.abs(start.y - goal.y)) >= 100));
	}
	
	public Coordinate[] StartGoalPair()
	{
		Coordinate[] sgp = new Coordinate[2];
		
		do {
			sgp[0] = Coordinate.startGoal();
			sgp[1] = Coordinate.startGoal();
			
			if ((getCellType(sgp[0])) > 0 && (getCellType(sgp[1]) > 0))
			{
				if (AdequateStartGoalDistance(sgp[0], sgp[1]))
				{
					return sgp;
				}
			}
			
		} while(true);
		
		
	}
	
	public void initHard(){
		for(int i=0;i<hard.length;i++){
			hard[i] = Coordinate.hardToTraverse();
			//System.out.println(hard[i].x + " " + hard[i].y);
		}
		System.out.println();
		for(int i=0;i<hard.length;i++){
			for(int j=0;j<hard.length;j++) {
				if(i==j) continue;
				if(hard[i].x == hard[j].x && hard[i].y==hard[j].y){
					System.out.println("Stopped a duplicate at" + " " + i + " and " + j);
					this.initHard();
				}
			}
		}
		addHard();
	}
	
	public void addHard()
	{
		for(int i=0;i <8; i++)
		{
			int xS = (hard[i].x)-15;
			if(xS<0) xS = 0;
			
			int yS = (hard[i].y)-15;
			if(yS<0) yS = 0;
			
			int xF = (hard[i].x)+15;
			if(xF>119) xF = 119;
			
			int yF = (hard[i].y)+15;
			if(yF>159) yF = 159;
			
			int xLength = Math.abs(xS-xF)+1;
			int yLength = Math.abs(yS-yF)+1;
			
			addHardHelper(xS, yS, i, xLength, yLength);
		}
	}
	
	public void addHardHelper(int x, int y, int z, int xl, int yl)
	{
		Random r = new Random();
//		System.out.println();
//		System.out.println();
//		System.out.println(z);
		for(int i=0; i<xl; i++)
		{
			for(int j=0; j<yl; j++)
			{
				if(cellType[x][y]!=2)
				{
					cellType[x][y] = r.ints(1,3).findFirst().getAsInt();
				}
				
				y++;
			}
			
			x++;
			y = (hard[z].y)-15;
			if(y<0) y = 0;
		}
	}
	
	public void initMapWith(int value)
	{
		for(int i=0; i<cellType.length; i++)
		{
			for(int j=0; j<cellType[0].length; j++)
			{
				cellType[i][j] = value;
			}
		}
	}
	
	public void initRiver(){
		Coordinate c = new Coordinate(0,0,0);
		for(int i=0;i<4;i++){
			c = c.river();
	//		System.out.println(c.toString());
			addRiver(c);
	//		System.out.println("loop");
		}
	}
	
	/*
	public void addRiver(Coordinate river){
		boolean edge = false;
		Coordinate c = new Coordinate(0,0,0);
		int length = 0;
		int y = 0;
		int direction = river.start;
		switch(river.start){
		case 0: for(int i=0;i<20;i++){
					if(cellType[river.x][i] == 4 || cellType[river.x][i] == 3){
				//		addRiver(river);
					}
					if(cellType[river.x][i] == 1){
					cellType[river.x][i] = 3;
					}else{
						cellType[river.x][i] = 4;
					}
				}
				length+=20;
				c = new Coordinate(river.x,19,river.start);
				break;
			
		case 1: for(int i=0;i<20;i++){
					if(cellType[i][river.y] == 4 || cellType[i][river.y] == 3){
				//		addRiver(river);
					}
					if(cellType[i][river.y] == 1){
						cellType[i][river.y] = 3;
					}else{
						cellType[i][river.y] = 4;
					}
				}
				length+=20;
				c = new Coordinate(19,river.y,river.start);
				break;
			
		case 2: for(int i=0;i<20;i++){
					if(cellType[river.x][159-i] == 4 || cellType[river.x][159-i] == 3){
					//	addRiver(river);
					}
					if(cellType[river.x][159-i] == 1){
						cellType[river.x][159-i] = 3;
					}else{
						cellType[river.x][159-i] = 4;
					}
				}
				length+=20;
				c = new Coordinate(river.x,139,river.start);
				break;
			
		case 3: for(int i=0;i<20;i++){
					if(cellType[i][river.y] == 4 || cellType[i][river.y] == 3){
					//	addRiver(river);
					}
					if(cellType[119-i][river.y] == 1){
						cellType[119-i][river.y] = 3;
					}else{
						cellType[119-i][river.y] = 4;
					}
				}
				length+=20;
				c = new Coordinate(99,river.y,river.start);
				break;
		}
		
		do{
		System.out.println(river.toString());
		System.out.println(c.toString());
		direction = c.start;
		int x = riverNextDirection();
		switch(x){
		case 0:
			switch(direction){
			case 0: riverRight(c,edge,length);
					c.y = c.y+20;
					break;
			case 1: riverDown(c,edge,length);
					c.x = c.x+20;
					break;
			case 2: riverLeft(c,edge,length);
					c.y = c.y-20;
					break;
			case 3: riverUp(c,edge,length);
					c.x = c.x-20;
					break;
			}
			break;
		case 1:
			switch(direction){
			case 0: riverUp(c,edge,length);
					c.x = c.x-20;
					break;
			case 1: riverRight(c,edge,length);
					c.y = c.y+20;
					break;
			case 2: riverDown(c,edge,length);
					c.x = c.x+20;
					break;
			case 3: riverLeft(c,edge,length);
					c.y = c.y-20;
					break;
			}
			break;
		case 2:
			switch(direction){
			case 0: riverDown(c,edge,length);
					c.x = c.x+20;
					break;
			case 1: riverLeft(c,edge,length);
					c.y = c.y-20;
					break;
			case 2: riverUp(c,edge,length);
					c.x = c.x-20;
					break;
			case 3: riverRight(c,edge,length);
					c.y = c.y+20;
					break;
			}
			break;
		}y++;
		}while(y<5);
		
	}
	
	void riverUp(Coordinate river,boolean edge,int length){
		for(int i=0;i<20;i++){
			if(river.x - i<0){
				length+=i;
				edge = true;
				break;
			}
			if(cellType[i][river.y] == 4 || cellType[i][river.y] == 3){
			//	addRiver(river);
			}
			if(cellType[Math.abs(river.x-i)][river.y] == 1){
				cellType[Math.abs(river.x-i)][river.y] = 3;
			}else{
				cellType[Math.abs(river.x-i)][river.y] = 4;
			}
		}
	//	river.x = river.x-20;
	}
	
	void riverDown(Coordinate river,boolean edge, int length){
		for(int i=0;i<20;i++){
			if(river.x+i>119){
				length+=i;
				edge = true;
				break;
			}
			if(cellType[river.x + i][river.y] == 4 || cellType[i][river.y] == 3){
		//		addRiver(river);
			}
			if(cellType[river.x + i][river.y] == 1){
				cellType[river.x + i][river.y] = 3;
			}else{
				cellType[river.x + i][river.y] = 4;
			}
		}
	//	river.x = river.x+20;
	}
	
	void riverLeft(Coordinate river,boolean edge,int length){
		for(int i=0;i<20;i++){
			if(river.y-i<0){
				length+=i;
				edge = true;
				break;
			}
			if(cellType[river.x][Math.abs(river.y - i)] == 4 || cellType[river.x][159-i] == 3){
		//		addRiver(river);
			}
			if(cellType[river.x][Math.abs(river.y - i)] == 1){
				cellType[river.x][Math.abs(river.y - i)] = 3;
			}else{
				cellType[river.x][Math.abs(river.y - i)] = 4;
			}
		}
	//	river.y = river.y-20;
	}

	void riverRight(Coordinate river,boolean edge,int length){
		for(int i=0;i<20;i++){
			if(river.y + i>159){
				length+=i;
				edge = true;
				break;
			}
			if(cellType[river.x][i] == 4 || cellType[river.x][i] == 3){
		//		addRiver(river);
			}
			if(cellType[river.x][river.y + i] == 1){
			cellType[river.x][river.y + i] = 3;
			}else{
				cellType[river.x][river.y + i] = 4;
			}
		}
	//	river.y = river.y+20;
	}*/
	
	public void addRiver(Coordinate river){
		int length = 0;
		switch(river.start){
		case 0: for(int i=0;i<20;i++){
					if(cellType[river.x][i] == 1){
					cellType[river.x][i] = 3;
					}else{
						cellType[river.x][i] = 4;
					}
				}
				
				break;
			
		case 1: for(int i=0;i<20;i++){
					if(cellType[i][river.y] == 1){
						cellType[i][river.y] = 3;
					}else{
						cellType[i][river.y] = 4;
					}
				}
				break;
			
		case 2: for(int i=0;i<20;i++){
					if(cellType[river.x][159-i] == 1){
						cellType[river.x][159-i] = 3;
					}else{
						cellType[river.x][159-i] = 4;
					}
				}
				break;
			
		case 3: for(int i=0;i<20;i++){
					if(cellType[119-i][river.y] == 1){
						cellType[119-i][river.y] = 3;
					}else{
						cellType[119-i][river.y] = 4;
					}
				}
				break;
		}
		
		
		
		
	}
	
	void riverUp(Coordinate river){
		for(int i=0;i<20;i++){
			if(cellType[119-i][river.y] == 1){
				cellType[119-i][river.y] = 3;
			}else{
				cellType[119-i][river.y] = 4;
			}
		}
	}
	
	void riverDown(Coordinate river){
		for(int i=0;i<20;i++){
			if(cellType[i][river.y] == 1){
				cellType[i][river.y] = 3;
			}else{
				cellType[i][river.y] = 4;
			}
		}
	}
	
	void riverLeft(Coordinate river){
		
	}

	void riverRight(Coordinate river){
		for(int i=0;i<20;i++){
			if(cellType[river.x][i] == 1){
			cellType[river.x][i] = 3;
			}else{
				cellType[river.x][i] = 4;
			}
		}
	}
	
	public int riverNextDirection(){
		Random r = new Random();
		int x = r.ints(0,10).findFirst().getAsInt();
		if(x<6) {x = 0; return x;}
		if(x>5 && x<8) {x = 1; return x;}
		x = 2;
		return x;
	}
	
	public void blockedCells(){
		Coordinate c = new Coordinate(0,0,0);
		for(int i=0;i<3840;i++){
			do{
				c = c.blocked();
			}while(getCellType(c.x, c.y)==3 || getCellType(c.x, c.y)==4);
			cellType[c.x][c.y] = 0;
		}
	}
	
	public String toString()
	{
		String result = "";
		
		for(int i=0;i<cellType.length;i++) {
			for(int j=0;j<cellType[0].length;j++){
				result += cellType[i][j];
			}
			result += "\n";
		}
		
		return result;
	}
	
	public int getCellType(Coordinate c)
	{
		return getCellType(c.x, c.y);
	}
	
	public int getCellType(int x, int y)
	{
		if ((x > 0) && (x < 120) && (y > 0) && (y < 160))
		{
			return cellType[x][y];
		}
		else
		{
			return -1;
		}
	}
	
	FileOutputStream fooStream = null;
	
	public boolean save(String filename) throws IOException
	{
		File myFoo = new File(filename);
		try {
			fooStream = new FileOutputStream(myFoo, false);
			fooStream.write(this.toString().getBytes());
			fooStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean load(String filename)
	{
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
		    String line = null;
		    
		    for(int i = 0; (line = br.readLine()) != null; i++)
		    {
		    	for(int j = 0; j < line.length(); j++)
		    	{
		    		cellType[i][j] = Character.digit(line.charAt(j), 5);
		    	}
		    }
		    
		    br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	} 
}
