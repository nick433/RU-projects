#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cstdlib>
#include <ctime>
#include "map_class.h"
using namespace std;

//void printGrid(Tile**,int,int);
void genMap(Tile**);
void difficultTerrain(Tile **,int,int);
bool genHighways(List*,Tile **);
int highwayExtend(List*,Tile **,int);
//void deleteAndAdd(List*,Tile **);
void markHighways(List*,Tile**,bool);
//void deleteAndUnmark(List*,Tile**);
void printL(List *);
int listsize(List*);
void clearAndClean(List*, List*, List*, List*, Tile**);
int highwaySize(List*,Tile **);
int size = 0;
bool crash = false;

void genMap(Tile ** grid){ //to be done 5 times
	
	
	//first we pick 8 random centers for hard terrain
	int i = 0;
		int rx, ry;
		
		while(i<8){
			rx = rand() % 120; //line 0 of data file is start, 1 is goal, 2-10 will be these coordinates
			ry = rand() % 160; //************ here is where the coordinates will be added to the data file, no need to instansiate every random coordinate
			printf("(%i,%i)\n",rx,ry); // <- coords
			difficultTerrain(grid,rx,ry);
		i++;
	}
	i = 0;
	int j = 0;
	List hway1;
	List hway2;
	List hway3;
	List hway4;
	List* hway1p = &hway1;
	List* hway2p = &hway2;
	List* hway3p = &hway3;
	List* hway4p = &hway4;
	printf("here\n");
	bool a = false; //flags that let you know if hway 1 2 3 and 4 are completed
	bool b = false;
	bool c = false;
	bool d = false;
	int fails1 = 0;
	int fails2 = 0;
	int fails3 = 0;
	int fails4 = 0;
	int tries = 0;
	while(!(a ==true && b==true && c==true && d==true)){
		cout << "outside loop" << endl;
		//printGrid(grid,120,160); //delete this
		tries++;
		if(tries == 200){
			cout << "too many tries" << endl; //delete this
			exit(0);
		}
		if(a == false){
			if(genHighways(hway1p,grid)){
				a = true;
				cout << "before clear and clean" << endl;
				//printGrid(grid,120,160);
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				//markHighways(hway1p,grid,true); //true marks, false unmarks
				cout << "after markHighway" << endl;
				//printGrid(grid,120,160);
				continue;
				//make all as highways
			}
			else{
				cout << "failed at first path" << endl;
				//printGrid(grid,120,160); //failing is in this area, either markHighways, removefront, or the 2nd markHighways **********************************************
				markHighways(hway1p,grid,false);
				while(hway1p->head != NULL){
					hway1p->removeFront();
				}
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				//printGrid(grid,120,160);
				
				fails1++;
				cout << __LINE__ << endl;
				continue;
			}
			cout << "1st path" << endl;
			//printGrid(grid,120,160); 
		}
		else if(b == false){ //hway1 is done, 2 isn't, so we have to make 2 if b = false
			if(genHighways(hway2p,grid)){
				b = true;
				cout << "before clear and clean" << endl;
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				continue;
			}
			else{
				cout << "failed at 2nd path" << endl;
				//clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				markHighways(hway2p,grid,false);
				while(hway2p->head != NULL){
					hway2p->removeFront();
				}
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				fails2++;
				cout << __LINE__ << endl;
				continue;
			}
			cout << "2nd path" << endl;			
			//printGrid(grid,120,160); 
		}
		else if(c == false){
			if(genHighways(hway3p,grid)){
				c = true;
				cout << "before clear and clean" << endl;
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				continue;
			}
			else{
				cout << "failed at 3rd path" << endl;
				//clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				markHighways(hway3p,grid,false);
				while(hway3p->head != NULL){
					hway3p->removeFront();
				}
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				fails3++;
				cout << __LINE__ << endl;
				continue;
			}
			cout << "3rd path" << endl;			
			//printGrid(grid,120,160); 
		}
		else if(d == false){
			if(genHighways(hway4p,grid)){
				d = true;
				cout << "before clear and clean" << endl;
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				continue;
			}
			else{
				cout << "failed at 4th path" << endl;
				//clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				markHighways(hway4p,grid,false);
				while(hway4p->head != NULL){
					hway4p->removeFront();
				};
				clearAndClean(hway1p,hway2p,hway3p,hway4p,grid);
				fails4++;
				cout << __LINE__ << endl;
				continue;
			}			
			cout << "4th path" << endl;
			//printGrid(grid,120,160); 
		}
		if(fails1 > 30 || fails2 > 30 || fails3 > 30 || fails4 > 30){ //failed too many times on a certain run, redo everything
			a = false; b = false; c = false; d = false;
			fails1 = 0;
			fails2 = 0;
			fails3 = 0;
			fails4 = 0;
			markHighways(hway1p,grid,false);
			markHighways(hway2p,grid,false);
			markHighways(hway3p,grid,false);
			markHighways(hway4p,grid,false);
			//deleteAndUnmark(hway1p,grid);
			//deleteAndUnmark(hway2p,grid);
			//deleteAndUnmark(hway3p,grid);
			//deleteAndUnmark(hway4p,grid);
			while(hway1p->head != NULL){
				hway1p->removeFront();
			}
			while(hway2p->head != NULL){
				hway2p->removeFront();
			}
			while(hway3p->head != NULL){
				hway3p->removeFront();
			}
			while(hway4p->head != NULL){
				hway4p->removeFront();
			}

		}
		
	}
	
	printf("They were all true! before delete and add**\n");
//	deleteAndAdd(hway1p,grid); //at this point we just need to delete since adding has already happened, so the marking is redundant
//	deleteAndAdd(hway2p,grid);
//	deleteAndAdd(hway3p,grid);
//	deleteAndAdd(hway4p,grid);
//	markHighways(List*,Tile**);
//	deleteAndUnmark(List*,Tile**);
	
	//hway1.addFront(66,66);
	printL(hway1p);
	printL(hway2p);
	printL(hway3p);
	printL(hway4p);
	while(hway1p->head != NULL){
		grid[hway1p->head->x][hway1p->head->y].highway = true;
		hway1p->removeFront();		
	}
	while(hway2p->head != NULL){
		grid[hway2p->head->x][hway2p->head->y].highway = true;
		hway2p->removeFront();
	}
	while(hway3p->head != NULL){
		grid[hway3p->head->x][hway3p->head->y].highway = true;
		hway3p->removeFront();
	}
	while(hway4p->head != NULL){
		grid[hway4p->head->x][hway4p->head->y].highway = true;
		hway4p->removeFront();
	}
	delete(hway1p);
	delete(hway2p);
	delete(hway3p);
	delete(hway4p);
	printf("after delete**\n");
	int rando;
	int blockCount = 0;
	while(blockCount<3840){
		for(i=0;i<120;i++){
			for(j=0;j<160;j++){
				if(grid[i][j].highway == false && grid[i][j].type != 'B'){
					rando = rand();
					if(rando%8 == 0){
						grid[i][j].type = 'B';
						blockCount++;
						if(blockCount == 3840){
							cout << "block is done from center" << endl;
							cout << "block is done from center" << endl;
							//printGrid(grid,120,160);
							cout << "DONE" << endl;
							cout << "DONE" << endl;
							return;
						}
					}
				}
			}
		}
		cout << "block is done" << endl;
		cout << "DONE" << endl;
		//printGrid(grid,120,160);
		cout << "DONE" << endl;
		cout << "DONE" << endl;
	}

}
void difficultTerrain(Tile ** grid,int x,int y){
	int i,j;
	
	for(i=x-15;i<x+16;i++){
		for(j=y-15;j<y+16;j++){
			if(i>-1 && j>-1 && i<120 && j<160)
				if(rand()%2 == 0)
					grid[i][j].type = 'H';
			//printf("%i %i\n",i,j);
		}
	}
}

bool genHighways(List* hway,Tile ** grid){ //edge case to consider: we end the 20 step path directly on the border cell, we terminate
								//right here so we dont have a highway running up the whole side of a border
	int side = rand() % 4;
	int s = rand();
	int i;
	int crashCount = 0;
	int position;
	cout << "before genhighway" << endl;
	//printGrid(grid,120,160);
	if(side == 0){ //top border
		s = s%160;
		if(s==0 || s==159)
			return false;
		if(grid[0][s].highway == false){
			hway->addFront(0,s);
			size++;
			//grid[0][s].highway = true;
			for(i=0;i<21;i++){ //changed from 1 and 20 to 0 and 21 
				if(grid[i][s].highway == false){
					grid[i][s].highway = true;
				}
				else{
					hway->addFront(i-1,s);
					return false;
				}
			} //after this for loop, we have done 1 20 step move away from the boundary
			hway->addFront(i-1,s);
			size++;
			//here we will move straight with 60%, left and right 20%
			//here is where all of the borders will have the exact same code
			position = 0; //2nd argument is to let you know what direction you came from, so from 0 means down is "straight"
		}
		else{
			return false;
		}
	}
	else if(side == 1){ //right border
		s = s%120;
		if(s==0 || s==119)
			return false;
		if(grid[s][159].highway == false){
			hway->addFront(s,159);
			printf("heeere\n");
			size++;
			//grid[s][159].highway = true;
			for(i=0;i<21;i++){
				if(grid[s][159-i].highway == false){
					grid[s][159-i].highway = true;
				}
				else{
					hway->addFront(s,159-i+1);
					return false;
				}
			} //after this for loop, we have done 1 20 step move away from the boundary
			hway->addFront(s,159-i+1);
			size++;
			position = 1; //returns a boolean... might want to contain this in a loop that keeps running until we hit a border while size is > 100
									  //if this returns false, we revert the current extension and increment backtrack by 1, when true we increase size by 20.
		}
		else{
			
			return false;
		}
	}
	else if(side == 2){ //bottom border
		s = s%160;
		if(s==0 || s==159)
			return false;
		if(grid[119][s].highway == false){
			//grid[119][s].highway = true; //this will be done after all 4 highways are 100% done.
			hway->addFront(119,s);
			printf("heeere\n");
			size++;
			for(i=0;i<21;i++){
				if(grid[119-i][s].highway == false){
					grid[119-i][s].highway = true;
					//hways[0][0] = Coord(119,s);
				}
				else{
					hway->addFront(119-i+1,s);
					return false;
				}
			} //after this for loop, we have done 1 20 step move away from the boundary
			hway->addFront(119-i+1,s); //just added these
			size++;
			position = 2;
			//delete this later:
			//printf("hway: %i,%i\n",hway[0][1].x,hways[0][1].y);
		}
		else{
			return false;
		}
	}
	else if(side == 3){ //left border
		s = s%120;
		if(s==0 || s==119)
			return false;
		if(grid[s][0].highway == false){
			//grid[s][0].highway = true;
			hway->addFront(s,0);
			printf("heeere\n");
			size++;
			for(i=0;i<21;i++){
				if(grid[s][i].highway == false){
					grid[s][i].highway = true;
				}
				else{
					hway->addFront(s,i-1);
					return false;
				}
			} //after this for loop, we have done 1 20 step move away from the boundary
			hway->addFront(s,i-1);
			size++;
			position = 3; //returns the last direction extended
			
			
		}
		else{
			return false;
		}
	}
	int oldPosition; 
	oldPosition = position;
	while(crashCount < 20){
		printf("crash count = %i  ", crashCount);
		cout << __LINE__ ;
		cout << "--  ";
		printL(hway);
		if(crash == true){
			crashCount++;
	//		if(crashCount > 11 && crashCount < 50 && crashCount%10 == 0){ //every 10 crashes backtrack
	//			markHighways(hway,grid,false); 
	//			hway->removeFront();  //this part is wrong, it should remove nodes until just 2 are left //////////////////////////////////**********
	//			markHighways(hway,grid,true); 
	//		}
			cout << "crash occurred" << endl;
			//printGrid(grid,120,160);
			markHighways(hway,grid,false); //unmark all highways
			//printGrid(grid,120,160);
			hway->removeFront();
			markHighways(hway,grid,true); //remark after removing
			//printGrid(grid,120,160);
			crash = false;
			position = oldPosition;
		}
		if(position == -1){
			//if(listsize(hway)>5){ //total steps is >= 100, each coordinate in list represents 20 steps
			if(highwaySize(hway,grid) >= 100){
				cout << "adding to list" << endl;
				return true;
			}
			else{
				cout << "unmarking all highways from border crash" << endl;
				markHighways(hway,grid,false); //unmark all highways
				hway->removeFront();
				cout << "after removing head, remarking highways:" << endl;
				markHighways(hway,grid,true); //remark after removing
				printL(hway);
				crashCount++;
				position = oldPosition;
			}
		}
		
		oldPosition = position;
		if(crashCount < 20){
			printf("before highwayExt.. position: %i, oldposition: %i\n",position,oldPosition);
			position = highwayExtend(hway,grid,position);
			if(listsize(hway) > 14){
				return false;
			}
			printf("after highwayExt.. position: %i, oldposition: %i\n",position,oldPosition);
			//printGrid(grid,120,160);
		}
		//if crash count becomes 50 over time, restart all 4 highways
	}
	cout << "delete and unmark"<< endl;
	markHighways(hway,grid,false);
	cout << __LINE__ << endl;
	return false;
	//return true;
}

int highwayExtend(List * hway, Tile ** grid,int side){ //arg size will allow us to keep passing in a size to keep track of the total size as we recall this func
	
	int nd = rand()%10; //side is the perspective from which we come from
	int direction; //direction will change but we dont overwright side incase we need to backtrack
	int i;
	int x,y;
	x = hway->head->x;
	y = hway->head->y;
	cout << __LINE__ << endl;
	grid[x][y].highway = false; //we immidiatly have a problem after backtracking to a spot that we already marked.
	
	if(x==119 || y == 159){  //extra, this should never execute
		
		cout << "at borderrrrrrrrrrrrrrrrrrrr" << endl;
		hway->addFront(x,y);
		printL(hway);
		return -1;
		
	}
	printf("head: %i,%i\n",hway->head->x,hway->head->y);
	//hway->print();
	if(nd<6){
		//perspective stays the same
		direction = side;
	}
	else if(nd == 6 || nd == 7)
		direction = (side+1)%4; //corresponds to move right
	else if(nd == 8 || nd == 9)
		direction = (side+3)%4;   //0,1 means going left, 
	printf("begin highwayext, side: %i, direction: %i .. 0 going down, 1 going left, 2 going up, 3 going right",side,direction);
	cout << __LINE__ << endl;
	if(direction == 0){ //perspective going toward bottom, 0 was from the top border and continues moving down
		for(i=0;i<21;i++){ //might set these back to i=1, need to test this
			printf("direction 0: x,y: %i,%i\n",x+i,y);
			if(grid[x+i][y].highway == false){
				cout << __LINE__ << endl;
				if(grid[x+i][y].border == true){
					cout << "border" << endl;
					hway->addFront(x+i,y);
					return -1;
				}
				grid[x+i][y].highway = true;
			}
			else{
				cout << __LINE__ << endl;
				cout << "crash" << endl;
				hway->addFront(x+i-2,y);
				crash = true; //crash is global so the that you know you didn't make progress
				return side; //if failure, return the side you started with
			}
			
		}
		cout << __LINE__ << endl;
		hway->addFront(x+i-1,y);
		return direction; //the taken direction was successful so we return it
	}
	else if(direction == 1){ //from right
		for(i=0;i<21;i++){ 
			printf("direction 1: x,y: %i,%i\n",x,y-i);
			if(grid[x][y-i].highway == false){
				cout << __LINE__ << endl;
				if(grid[x][y-i].border == true){
					cout << "border" << endl;
					hway->addFront(x,y-i);
					return -1;
				}
				grid[x][y-i].highway = true;
			}
			else{
				cout << __LINE__ << endl;
				hway->addFront(x,y-i+2); //might need to change these all back to 1
				crash = true;
				return side; //if failure, return the side you started with
			}
			
		}
		cout << __LINE__ << endl;
		hway->addFront(x,y-i+1);  //added +1 **************************&&&&&&&&&************************
		return direction;
	}
	else if(direction == 2){ //bottom
		for(i=0;i<21;i++){ 
			printf("direction 2: x,y: %i,%i\n",x-i,y);
			if(grid[x-i][y].highway == false){
				cout << __LINE__ << endl;
				if(grid[x-i][y].border == true){
					cout << "border" << endl;
					hway->addFront(x-i,y);
					return -1;
				}
				grid[x-i][y].highway = true;
			}
			else{
				cout << __LINE__ << endl;
				hway->addFront(x-i+2,y);
				crash = true;
				return side; //if failure, return the side you started with
			}
	
		}
		cout << __LINE__ << endl;
		hway->addFront(x-i+1,y); 
		return direction;
	}
	else if(direction == 3){
		for(i=0;i<21;i++){ 
			printf("direction 3: x,y: %i,%i\n",x,y+i);
			if(grid[x][y+i].highway == false){
				cout << __LINE__ << endl;
				if(grid[x][y+i].border == true){
					cout << "border" << endl;
					hway->addFront(x,y+i);
					return -1;
				}
				grid[x][y+i].highway = true;
			}
			else{
				cout << __LINE__ << endl;
				hway->addFront(x,y+i-2);
				crash = true;
				return side; //if failure, return the side you started with
			}
			
		}
		cout << __LINE__ << endl;
		hway->addFront(x,y+i-1);
		return direction;
	}
	cout << __LINE__ << endl;
	return direction; //this line should never be reached
}

setGoals(){//to be done 10 times on each map
	//EZ, will do last
}
void printL(List* lp) { //change this to a get size method, each entry represents 20 steps of the highway delete this method when done*************

	Node *tmp = lp->head;
	if ( tmp == NULL ) {
	cout << "EMPTY" << endl;
	return;
	}
	printf("size: %i\n",size);
	if ( tmp->Next() == NULL) {
		printf("(%i,%i)",tmp->x,tmp->y);
		cout << " --> ";
		cout << "NULL" << endl;
	}
	else {
		do {
			printf("(%i,%i)",tmp->x,tmp->y);
			cout << " --> ";
			
			tmp = tmp->Next();
		}
		while ( tmp != NULL );
		cout << "NULL" << endl;
	}
}

int listsize(List* lp) { //change this to a get size method, each entry represents 20 steps of the highway
	cout << __LINE__ << endl;
	int size = 0;
	Node *tmp = lp->head;
	if ( tmp == NULL ) {
	
	return 0;
	}
	if ( tmp->Next() == NULL) {
		
	}
	else {
		do {
			size++;
			tmp = tmp->Next();
		}
		while ( tmp != NULL );
	}
	cout << __LINE__ << endl;
	return size;
}

void markHighways(List* lp,Tile ** grid,bool f){ //if bool is true we mark them, false, unmark
	
	if(lp->head == NULL)
		return;
	int x1 = lp->head->x;
	int y1 = lp->head->y;
	int x2;
	int y2;
	int i;
	Node* tmp = lp->head;
	tmp = tmp->Next();
	while(tmp != NULL){
		x2 = tmp->x;
		y2 = tmp->y;
		if(x1 == x2){
			if(y1 > y2){
				printf("x1y1: %i,%i\n",x1,y1);
				while(y1!=y2){
					grid[x1][y1].highway = f;
					y1--;
				}
				printf("x2y2: %i,%i\n",x2,y2);
			}
			else{
				printf("x1y1: %i,%i\n",x1,y1);
				while(y1!=y2){
					grid[x1][y1].highway = f;
					y1++;
				}
				printf("x2y2: %i,%i\n",x2,y2);
								
			}
		}
		else{
			if(x1 > x2){
				printf("x1y1: %i,%i\n",x1,y1);
				while(x1!=x2){
					grid[x1][y1].highway = f;
					x1--;
				}		
				printf("x2y2: %i,%i\n",x2,y2);
			}
			else{
				printf("x1y1: %i,%i\n",x1,y1);
				while(x1!=x2){
					grid[x1][y1].highway = f;
					x1++;
				}
				printf("x2y2: %i,%i\n",x2,y2);
			}
		}
		x1 = x2; //just added these 2 lines
		y1 = y2;
		tmp = tmp->Next();
	}
	cout << __LINE__ << endl;
}
int highwaySize(List* lp,Tile ** grid){ //if bool is true we mark them, false, unmark
	int size = 0;
	if(lp->head == NULL)
		return 0;
	int x1 = lp->head->x;
	int y1 = lp->head->y;
	int x2;
	int y2;
	int i;
	Node* tmp = lp->head;
	tmp = tmp->Next();
	while(tmp != NULL){
		x2 = tmp->x;
		y2 = tmp->y;
		if(x1 == x2){
			if(y1 > y2){
				printf("x1y1: %i,%i\n",x1,y1);
				while(y1!=y2){
					size++;
					y1--;
				}
				printf("x2y2: %i,%i\n",x2,y2);
			}
			else{
				printf("x1y1: %i,%i\n",x1,y1);
				while(y1!=y2){
					size++;
					y1++;
				}
				printf("x2y2: %i,%i\n",x2,y2);
								
			}
		}
		else{
			if(x1 > x2){
				while(x1!=x2){
					size++;
					x1--;
				}		
			}
			else{
				while(x1!=x2){
					size++;
					x1++;
				}
				printf("size of hway: %i \n",size);
			}
		}
		x1 = x2; //just added these 2 lines
		y1 = y2;
		tmp = tmp->Next();
	}
	printf("size of hway: %i \n",size);
	return size; //might need to subtract by the amount of coordinates if they are recounted
	cout << __LINE__ << endl;
}
void clearAndClean(List* hway1, List* hway2, List* hway3, List* hway4, Tile** grid){
		int i,j;
		for(i=0;i<120;i++){
			for(j=0;j<160;j++){
				if(grid[i][j].highway == true){
					grid[i][j].highway = false;
				}
			}
		}
		markHighways(hway1,grid,true);
		markHighways(hway2,grid,true);
		markHighways(hway3,grid,true);
		markHighways(hway4,grid,true);
}
/*void printGrid(Tile ** grid,int rows, int cols){ //this is what will be printed to file
	int i,j;
	for(i=0;i<rows;i++){
		for(j=0;j<cols;j++){
			if(grid[i][j].type == 'R' && !grid[i][j].highway)
				printf("1");
			else if(grid[i][j].type == 'R' && grid[i][j].highway)
				printf("a");
			else if(grid[i][j].type == 'H' && !grid[i][j].highway)
				printf("2");
			else if(grid[i][j].type == 'H' && grid[i][j].highway)
				printf("b");
			else if(grid[i][j].type == 'B')
				printf("0");
		}
		printf(" %i",i);  //delet this
		puts("");
	}
} */