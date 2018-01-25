#include <stdio.h>
#include "first.h"
main(int argc, char **argv){

    if(argv[1] == NULL){
        printf("error\n");
	return 0;
    }

    int a = atoi(argv[1]);
	
    if(a%2 == 0){
	printf("even\n");
        return 0;
    }
    else{ 
	printf("odd\n");
	return 0;
    }
		

}
