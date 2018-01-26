/*
* Authors:
* Nick Mangracina nsm83 
* Jaroor Modi jhm119
*
*/

#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cstdlib>
#include <ctime>
#include <math.h>
#include "map_build.h"
#include <fstream>

using namespace std;


void printGrid(Tile**,int,int);


//IMPORTANT NOTE: (0,20) in the cmd printout corresponds to 0 up, 20 to the right
int main(){
	srand((unsigned)time(0));
	int xx = rand();
	//ofstream myfile;
	//myfile.open ("example.txt");
	int numRows = 160; //to be read in later. 120 x 160, changing 120 to 160
	int numCols = 120;
	
	char choice[20];
	while(true){
		cout << "\nType the number that corresponds to your choice:\n\n";
		cout << "1 : Create 5 maps with 10 start/goal points and print data to text file,\n";
		cout << "    perform A* on the files created using choice 1 and open GUI\n";
		cout << "2 : Read provided text files to perform A* on and open GUI [this option will require user input of the file names]\n"; 
		cout << "0 : Exit program\n";
		cin.getline(choice,20);
		
		if(choice[0] == '1'){
			int ii;
			for(ii = 1;ii<51;ii+=10){
				Tile **grid;
				grid = (Tile**)malloc(numRows*sizeof(Tile*));
				int i,j;
				for(i=0;i<numRows;i++){ 
					grid[i] = (Tile*)malloc(numCols*sizeof(Tile));
				}
				cout << "after malloc" << endl;
				for(j=0;j<numRows;j++){
					for(i=0;i<numCols;i++){
							Tile t('R',j,i);
							if(j == 159 || j == 0 || i == 0 || i == 119){
								t.border = true;
							}
							grid[j][i] = t;
					}
				}
				cout << "after assignments" << endl;
				//grid[0][20].type = 'W';
				//printGrid(grid,160,120);
				while(true){
					if(genMap(grid,ii,xx))
						break;
				}
				
				printGrid(grid,160,120);
				cout << grid[55][20].x << " , " << grid[55][20].y << endl;
				//PERFORM A* HERE BY PASSING THE GRID TO Astar which will be in Astar.h and imported by main.cpp
				
				for(i=0;i<numRows;i++){
					free(grid[i]);
				}
				free(grid);
				
			}
		}
		else if(choice[0] == '2'){
			
			List difficultT;
			int sx,sy,gx,gy;
			List* difficultTp = &difficultT;
			Tile** grid;
			grid = (Tile**)malloc(numRows*sizeof(Tile*));
			int i,j;
			for(i=0;i<numRows;i++){ 
				grid[i] = (Tile*)malloc(numCols*sizeof(Tile));
			}
			cout << "Enter .txt file to read in:" << endl;
			string mapInput;
			cin >> mapInput;
			if(setGridFromFile(mapInput,grid,&sx,&sy,&gx,&gy,difficultTp)){ //sets start and goal points for this particular map
				cout << "successfully read from file\n";
			} 
			else{
				cout << "error reading from file\n";
				for(i=0;i<numRows;i++){
					free(grid[i]);
				}
				free(grid);
				continue;
			}
			
			printGrid(grid,160,120);
			
			//WA_star * trav = new WA_star(sx,sy,gx,gy,grid,1,1);
			cout << "starts: "  <<endl;
			//trav->dosearch();
			
			cout << "starts: " << sx << "," << sy << endl;
			cout << "goals: " << gx << "," << gy << endl;
			//********A_star(grid,sx,sy,gx,gy,inputMap);
			cout << grid[3][44].y;
			//perform A* here and display on GUI
			
			while(difficultT.head != NULL){
				difficultT.removeFront();
			}
			
			for(i=0;i<numRows;i++){
					free(grid[i]);
				}
			free(grid);
				
		}
		else if(choice[0] == '0'){
			printf("return from main \n");
			return 0;	
		}
		
	}
	printf("return from main \n");
	//myfile.close();	
	return 0;
}
 
void printGrid(Tile ** grid,int cols, int rows){ //this is what will be printed to file --switching rows and cols
	int i,j; //cols is 160, rows is 120
	//for(i=0;i<rows;i++){
	for(j=rows-1;j>=0;j--){	
		for(i=0;i<cols;i++){
			
			if(grid[i][j].type == 'W') //remove later
				printf("W");
			else if(grid[i][j].border == true) //remove later
				printf("B");
			else if(grid[i][j].type == 'R' && !grid[i][j].highway)
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
		printf(" %i",j);  //delet this
		puts("");
	}
} 
