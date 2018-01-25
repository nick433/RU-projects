#include <stdio.h>
#include <stdlib.h>
#include "second.h"
main(int argc, char *argv[]) 
{

  if(argv[1]==NULL){
    printf("error\n");
    return 0;
  } 
  int a = atoi(argv[1]);
  if(a<2){
    printf("no\n");
    return 0;
  }  
  if(a==2){
    printf("yes\n");
    return 0;
  }
  int i;
  for(i=2;i<=a/2;i++){
    if(a%i == 0){
      printf("no\n");
      return 0;
    }
  }
  printf("yes\n");

  return 0;

}



