package model;

import java.util.Random;

public class River {
	Map m = new Map();
	
	
	public void addRiver(Coordinate river){
		boolean edge = false;
		Coordinate c = new Coordinate(0,0,0);
		int length = 0;
		int direction = river.start;
		switch(river.start){
		case 0: for(int i=0;i<20;i++){
					if(m.cellType[river.x][i] == 4 || m.cellType[river.x][i] == 3){
						addRiver(river);
					}
					if(m.cellType[river.x][i] == 1){
					m.cellType[river.x][i] = 3;
					}else{
						m.cellType[river.x][i] = 4;
					}
				}
				length+=20;
				c = new Coordinate(river.x,19,0);
				break;
			
		case 1: for(int i=0;i<20;i++){
					if(m.cellType[i][river.y] == 4 || m.cellType[i][river.y] == 3){
						addRiver(river);
					}
					if(m.cellType[i][river.y] == 1){
						m.cellType[i][river.y] = 3;
					}else{
						m.cellType[i][river.y] = 4;
					}
				}
				length+=20;
				c = new Coordinate(19,river.y,0);
				break;
			
		case 2: for(int i=0;i<20;i++){
					if(m.cellType[river.x][159-i] == 4 || m.cellType[river.x][159-i] == 3){
						addRiver(river);
					}
					if(m.cellType[river.x][159-i] == 1){
						m.cellType[river.x][159-i] = 3;
					}else{
						m.cellType[river.x][159-i] = 4;
					}
				}
				length+=20;
				c = new Coordinate(river.x,139,0);
				break;
			
		case 3: for(int i=0;i<20;i++){
					if(m.cellType[i][river.y] == 4 || m.cellType[i][river.y] == 3){
						addRiver(river);
					}
					if(m.cellType[119-i][river.y] == 1){
						m.cellType[119-i][river.y] = 3;
					}else{
						m.cellType[119-i][river.y] = 4;
					}
				}
				length+=20;
				c = new Coordinate(99,river.y,0);
				break;
		}
		
		do{
		int x = riverNextDirection();
		switch(x){
		case 0:
			switch(direction){
			case 0: riverRight(c);
					break;
			case 1: riverDown(c);
					break;
			case 2: riverLeft(c);
					break;
			case 3: riverUp(c);
					break;
			}
			break;
		case 1:
			switch(direction){
			case 0: riverUp(c);
					break;
			case 1: riverRight(c);
					break;
			case 2: riverDown(c);
					break;
			case 3: riverLeft(c);
					break;
			}
			break;
		case 2:
			switch(direction){
			case 0: riverDown(c);
					break;
			case 1: riverLeft(c);
					break;
			case 2: riverUp(c);
					break;
			case 3: riverRight(c);
					break;
			}
			break;
		}
		}while(!edge);
		
	}
	
	void riverUp(Coordinate river){
		for(int i=0;i<20;i++){
			if(m.cellType[i][river.y] == 4 || m.cellType[i][river.y] == 3){
				addRiver(river);
			}
			if(m.cellType[119-i][river.y] == 1){
				m.cellType[119-i][river.y] = 3;
			}else{
				m.cellType[119-i][river.y] = 4;
			}
		}
	}
	
	void riverDown(Coordinate river){
		for(int i=0;i<20;i++){
			if(m.cellType[i][river.y] == 4 || m.cellType[i][river.y] == 3){
				addRiver(river);
			}
			if(m.cellType[i][river.y] == 1){
				m.cellType[i][river.y] = 3;
			}else{
				m.cellType[i][river.y] = 4;
			}
		}
	}
	
	void riverLeft(Coordinate river){
		for(int i=0;i<20;i++){
			if(m.cellType[river.x][159-i] == 4 || m.cellType[river.x][159-i] == 3){
				addRiver(river);
			}
			if(m.cellType[river.x][159-i] == 1){
				m.cellType[river.x][159-i] = 3;
			}else{
				m.cellType[river.x][159-i] = 4;
			}
		}
	}

	void riverRight(Coordinate river){
		for(int i=0;i<20;i++){
			if(m.cellType[river.x][i] == 4 || m.cellType[river.x][i] == 3){
				addRiver(river);
			}
			if(m.cellType[river.x][i] == 1){
			m.cellType[river.x][i] = 3;
			}else{
				m.cellType[river.x][i] = 4;
			}
		}
	}
	
	
	public int riverNextDirection(){
		Random r = new Random();
		int x = r.ints(0,10).findFirst().getAsInt();
		if(x<6) {x = 0; return x;} //continue in same direction
		if(x>5 && x<8) {x = 1; return x;} //make a left from point of view of river.
		x = 2; //make a right from rivers point of view.
		return x;
	}
}
