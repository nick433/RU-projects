#include    <stdio.h>
#include    <string.h>
#include    <stdlib.h>
#include    "sorted-list.h"


int compInts(void *a, void *b){
	int r1,r2;
	
	r1 = *(int*)a;
	r2 = *(int*)b;

	if(r1 < r2)
		return -1;
	else if(r1 > r2)
		return 1;
	else 
		return 0;
}

int compDoubles(void *a, void *b){
	double r1,r2;
	
	r1 = *(double*)a;
	r2 = *(double*)b;


	if(r1 < r2)
		return -1;
	else if(r1 > r2)
		return 1;
	else 
		return 0;
}

int compStrings(void *a, void *b){
	char *r1 = a;
	char *r2 = b;

	return strcmp(r1, r2);
}

void destroy(void *p){
	free(p);
}

int main(){

	

	SortedListPtr list;
	list = SLCreate(compInts, destroy);
	
	int *itest1 = (int*)malloc(sizeof(int));
	*itest1 = 54;
	SLInsert(list, itest1);
	int *itest2 = (int*)malloc(sizeof(int));
	*itest2 = -54;
	SLInsert(list, itest2);
	int *itest3 = (int*)malloc(sizeof(int));
	*itest3 = 0;
	SLInsert(list, itest3);
	int *itest4 = (int*)malloc(sizeof(int));
	*itest4 = -3;
	SLInsert(list, itest4);
	int *itest5 = (int*)malloc(sizeof(int));
	*itest5 = 666;
	SLInsert(list, itest5);
	int *itest6 = (int*)malloc(sizeof(int));
	*itest6 = 12;
	SLInsert(list, itest6);
	int *itest7 = (int*)malloc(sizeof(int));
	*itest1 = 15;
	SLInsert(list, itest7);
	SortedListIteratorPtr curr = SLCreateIterator(list);
	printf("\nPrinting SL: \n");
	printf("%d ", *(int*)SLGetItem(curr));
	int *ip;
	while ( (ip = SLNextItem(curr)) ) {
		printf("%d ", *ip);
	}
	printf("\n");
	SLDestroyIterator(curr);

	printf("\nremoving head, end, and middle\n");
	SLRemove(list,itest5);
	SLRemove(list,itest2);
	SLRemove(list,itest3);
	curr = SLCreateIterator(list);
	printf("Printing SL: \n");
	printf("%d ", *(int*)SLGetItem(curr));
	while ( (ip = SLNextItem(curr)) ) {
		printf("%d ", *ip);
	}
	printf("\n\nAdding 3 more ints: ");	
	SLDestroyIterator(curr);
	int *itesta = (int*)malloc(sizeof(int));
	*itesta = 1;
	SLInsert(list, itesta);	
	int *itestb = (int*)malloc(sizeof(int));
	*itestb = 2;
	SLInsert(list, itestb);
	int *itestc = (int*)malloc(sizeof(int));
	*itestc = 3;
	SLInsert(list, itestc);	
	
	printf("\n");
	
	curr = SLCreateIterator(list);

	printf("Printing SL: \n");
	printf("%d ", *(int*)SLGetItem(curr));
	while ( (ip = SLNextItem(curr)) ) {
		printf("%d ", *ip);
	}
	printf("\n\nRemoving newly added 2 from list and adding 3 more ints\n");	
	SLDestroyIterator(curr);
	SLRemove(list, itestb);	

	int *itestd  = (int*)malloc(sizeof(int));
	*itestd = 104;
	int *iteste  = (int*)malloc(sizeof(int));
	*iteste = -4;
	int *itestf  = (int*)malloc(sizeof(int));
	*itestf = 1994;
	SLInsert(list, iteste);
	SLInsert(list, itestd);
	SLInsert(list, itestf);

	curr = SLCreateIterator(list);
	printf("Printing SL: \n");
	printf("%d ", *(int*)SLGetItem(curr));
	while ( (ip = SLNextItem(curr)) ) {
		printf("%d ", *ip);
	}
	printf("\n\n");	
	SLDestroyIterator(curr);	
	curr = SLCreateIterator(list);
	while ( (ip = SLNextItem(curr)) ) {
		if (*ip == -4)
			break;
	}
	printf("Deleting -4\n");
	SLRemove(list, ip);
	curr = SLCreateIterator(list);

	printf("%d ", *(int*)SLGetItem(curr));
	while ((ip = SLNextItem(curr))) {
		printf("%d ", *ip);
	}
	printf("\n");	
	SLDestroyIterator(curr);
	SLDestroy(list);

	printf("\nNow doubles:\n\n");
	list = SLCreate(compDoubles, destroy);

	double *dtest1 = (double*)malloc(sizeof(double));
	*dtest1 = 0.0001;
	SLInsert(list, dtest1);
	double *dtest2 = (double*)malloc(sizeof(double));
	*dtest2 = 0.000001;
	SLInsert(list, dtest2);
	double *dtest3 = (double*)malloc(sizeof(double));
	*dtest3 = -0.1;
	SLInsert(list, dtest3);
	double *dtest4 = (double*)malloc(sizeof(double));
	*dtest4 = 0.1;
	SLInsert(list, dtest4);
	double *dtest5 = (double*)malloc(sizeof(double));
	*dtest5 = 4;
	SLInsert(list, dtest5);
	double *dtest6 = (double*)malloc(sizeof(double));
	*dtest6 = 3.14;
	SLInsert(list, dtest6);
	double *dtest7 = (double*)malloc(sizeof(double));
	*dtest1 = 0;
	SLInsert(list, dtest7);
	
	curr = SLCreateIterator(list);
	double *dp;
	printf("printing SL: \n");
	printf("%f ", *(double*)SLGetItem(curr));
	while ((dp = SLNextItem(curr))) {
		printf("%f ", *dp);
	}
	printf("\n");
	SLDestroyIterator(curr);
	SLDestroy(list);

	printf("\nNow chars: \n\n");
	list = SLCreate(compStrings, destroy);
	char* ctest1 = (char*)malloc(sizeof(char)+1);
 	*ctest1 = 'a';
	ctest1[1] = '\0';
	char* ctest2 = (char*)malloc(sizeof(char)+1);
 	*ctest2 = 'z';
	ctest2[1] = '\0';
	char* ctest3 = (char*)malloc(sizeof(char)+1);
 	*ctest3 = 'f';
	ctest3[1] = '\0';
	char* ctest4 = (char*)malloc(sizeof(char)+1); 
	*ctest4 = 'r';
	ctest4[1] = '\0';
	char* ctest5 = (char*)malloc(sizeof(char)+1); 
	*ctest5 = 'f';
	ctest5[1] = '\0';
	
	printf("insert scrambled\n");
	SLInsert(list, ctest3);
	SLInsert(list, ctest2);
	SLInsert(list, ctest1);
	SLInsert(list, ctest4);
	SLInsert(list, ctest5);
	curr = SLCreateIterator(list);
	char *sp;
	printf("printing SL: \n");
	printf("%s ", (char*)SLGetItem(curr));
	while ( (sp = SLNextItem(curr)) ) {
		printf("%s ", sp);
	}
	printf("\n\n");

	SLDestroyIterator(curr);
	curr = SLCreateIterator(list);
	printf("Delete 'f'\n");
	sp = SLGetItem(curr);
	while ( (sp = SLNextItem(curr)) ) {
		if (*sp == 'f')
			break;
	}
	SLRemove(list, sp);

	curr = SLCreateIterator(list);
	printf("printing SL: \n");	
	printf("%s ", (char*)SLGetItem(curr));
	while ( (sp = SLNextItem(curr)) ) {
		printf("%s ", sp);
	}
	printf("\n\nAdding a string:\n");
	
	char* stest1 = (char*)malloc(sizeof(char)+1);
 	stest1[0] = 'h';
	stest1[1] = 'e';	
	stest1[2] = 'l';	
	stest1[3] = 'l';	
	stest1[4] = 'o';	
	stest1[5] = ' ';	
	stest1[6] = 'w';	
	stest1[7] = 'o';	
	stest1[8] = 'r';	
	stest1[9] = 'l';	
	stest1[10] = 'd';	
	stest1[11] = '\0';	
	SLInsert(list,stest1);
	
	curr = SLCreateIterator(list);
	printf("printing SL: \n");
	printf("%s ", (char*)SLGetItem(curr));
	while ( (sp = SLNextItem(curr)) ) {
		printf("%s ", sp);
	}

	printf("\n\n");	
	SLDestroyIterator(curr);
	printf("Freeing list, end.\n");
	SLDestroy(list);
	return 0;

}
