#include <stdio.h>
#include <stdlib.h>
#include "third.h"

struct Node{
		int data;
		struct Node* next;
};
struct LL{
	struct Node* head;
};
struct Node* add(struct Node* head, int i);
void print(struct Node* head);
void deleteAll(struct LL* link);
struct Node* delete(struct Node* head,int data);
	

main(int argc, char **argv){
	
	struct LL* list = (struct LL*)malloc(sizeof(struct LL));
	list->head = NULL;
	
	char buffer[50];
	
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
	int spacecount = 0;
	while(fgets(buffer, 50, pFile) != NULL){
		if(incount==0){
			if(buffer[0] != 'i' && buffer[0] != 'd' && decision == 'n'){
				printf("error\n");
				return 1;
			}
			decision = buffer[0];
			incount++;
		}
		while(incount<sizeof(buffer)){
			if(buffer[incount] == ' '){
				incount++;
				spacecount++;
				if(spacecount==38){
					break;
				}
				continue;
			}
			numChars[numCount] = buffer[incount];
			numCount++;
			incount++;
		}
		if(spacecount == 38){
			incount=0;
			numCount = 0;
			spacecount = 0;
			continue;
		}
		if(decision=='i'){
			list->head = add(list->head,atoi(numChars)); 
		}
		if(decision=='d'){
			if(list->head!=NULL){
				list->head = delete(list->head,atoi(numChars));
			}
		}
	
/*		printf("line: %c  %s\n",decision,numChars); */
		incount=0;
		numCount=0;
	}

	print(list->head);

	if(list->head!=NULL){
		printf("\n");
		deleteAll(list);
		return 0;
	}else{ 
		printf("\n");
		return 0;
	}
}

struct Node* delete(struct Node* head, int i){
	struct Node* curr;
	curr = head;
	if(curr->data == i){
		head = head->next;
		free(curr);
		return head;	
	}
	struct Node* prev = head;
	curr = curr->next;
	while(curr!=NULL){
		if(curr->data==i){
			prev->next = curr->next;
			free(curr);
			return head;
		}
	prev = curr;
	curr = curr->next;
	}
	return head;
}

struct Node* add(struct Node* head, int i){
	if(head!=NULL){
		head = delete(head,i);
	}
	struct Node* n = (struct Node*)malloc(sizeof(struct Node));
	n->data = i;
	n->next = NULL;
	struct Node* temp = head;
	struct Node* prev = NULL;
	if(head == NULL){
		return n;
	}
	while(temp!=NULL){
		if(temp->data>i){
			if(prev==NULL){
				n->next = head;
				return n;
			}
			else{
				prev ->next =n;
				n->next = temp;
				return head;
			}
		}
		prev = temp;
		temp = temp->next;
	}
	prev->next = n;
	return head;
}
void print(struct Node* head){
	struct Node* temp = head;
	if(temp!=NULL){
		printf("%d",temp->data);
		temp = temp->next;
	}
	
	while(temp!=NULL){
		printf("\t%d",temp->data);
		temp = temp->next;
	}
}

void deleteAll(struct LL* link){
	struct Node* t = link->head;
	struct Node* n = link->head->next;
	while(t!=NULL){
		n = t->next;
		free(t);
		t = n;
	}
}



