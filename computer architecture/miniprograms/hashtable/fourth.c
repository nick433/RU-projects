#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fourth.h"	

main(int argc, char **argv){
	
	char buffer[50];
	
	double table[1000];
	int i1;
	for(i1=0;i1<1000;i1++){
		table[i1] = 0.5;
	}
	
	int key;
	char decision = 'n';

	char* fileName = argv[1];
	
	FILE *pFile;

	pFile = fopen(fileName, "r");

	if(!pFile){
		printf("error\n");
		return 1;
	}
	char numChars[40];
	int numCount = 0;
	int incount = 0;
	while(fgets(buffer, 50, pFile) != NULL){
		if(incount==0){
			decision = buffer[0];
			incount++;
		}
		while(incount<sizeof(buffer)){
			if(buffer[incount] == ' '){
				incount++;
				continue;
			}
			numChars[numCount] = buffer[incount];
			numCount++;
			incount++;
		}
		
		key = atoi(numChars)%1000;
		int q = 0;
		int f = 0;
		double d = (double)atoi(numChars);
/*		printf("dub: %f \n",d); */
		if(decision=='i'){
			while(q < 1000){
				if(table[key] == (double)atoi(numChars)){
					printf("duplicate\n");
					break;	
				}
				if(table[key] == 0.5){
					table[key] = (double)atoi(numChars);
					printf("inserted\n");
					break;
				}	
				key++;
				q++;
				key = key % 1000;	 
			}
		}
		if(decision=='s'){
			while(f<1000){
				if(table[key] == 0.5){
					printf("absent\n");
					break;
				}	
				if(table[key] == (double)atoi(numChars)){
					printf("present\n");
					break;
				}
				key++; f++;
				key = key % 1000;
			}
		}
		if(decision!='i' && decision!='s'){
			printf("error\n");
		}
	 	int i = 0;
		

		incount=0;
		numCount=0;
	}

	return 0;
}

