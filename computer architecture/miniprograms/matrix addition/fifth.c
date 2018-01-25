#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "fifth.h"	

main(int argc, char **argv){
	
	char buffer[1000];
	
	char* fileName = argv[1];
	
	FILE *pFile;

	pFile = fopen(fileName, "r");

	if(!pFile){
		printf("error\n");
		return 1;
	}


	int m = 0; int n = 0;
	fscanf(pFile, "%d %d", &m, &n);
 


	fgets(buffer, 10, pFile); 
	int m1[m][n];
	int m2[m][n];
	int m3[m][n];
	
	int mcount = 0;
	int ncount = 0;
		
	while(mcount < m){
		while(ncount < n){
			fscanf(pFile,"%d",&m1[mcount][ncount]);
			ncount++;
		}
		fgets(buffer,10,pFile);
		mcount++;
		ncount = 0;
	}
	mcount = 0;
	fgets(buffer,10,pFile);
	while(mcount < m){
		while(ncount < n){
			fscanf(pFile,"%d",&m2[mcount][ncount]);
			ncount++;
		}
		fgets(buffer,10,pFile);
		mcount++;
		ncount = 0;
	}
	mcount=0;
	
	while(mcount < m){
		while(ncount < n){
			m3[mcount][ncount] = m1[mcount][ncount] + m2[mcount][ncount];
			printf("%d",m3[mcount][ncount]);
			if(ncount <= n-1){
				printf("\t");
			}
			ncount++;
		}
		printf("\n");
		mcount++;
		ncount = 0;
	}
	
	

	return 0;
}


