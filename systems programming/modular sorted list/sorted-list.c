#include "sorted-list.h"
#include "stdlib.h"
#include "stdio.h"
#include "string.h"
#include "ctype.h"

SortedListPtr SLCreate(CompareFuncT cf, DestructFuncT df){

	/*If the function pointers are null, it wont work. */
	if (cf == NULL || df == NULL){
		return NULL;
	}

	/*Initialize the SLPtr you're creating, should have all the features of  a SL but only point to the front of it.*/
	SortedListPtr SL = (SortedListPtr)malloc(sizeof(struct SortedList));

	/*Initialize the fields of the SLPtr */
	SL->comp = cf;
	SL->dest = df;
	SL->head = NULL;

	/*you're done, return the pointer, NOT the struct itself*/
	return SL;

}

void SLDestroy(SortedListPtr list){

	if(list == NULL){
		return; /*already NULL? great*/
	}

	/* need to destroy each value and free each Node, then free the list */


	while(list->head != NULL){
		Node* tempNode = list->head;
		list->head = list->head->next;
		list->dest(tempNode->data);
		free(tempNode);
	}


	free(list);

}

/*given some data, inserts it into the list in the correctly ordered spot. If it doesn't find the spot, it must be added on to the end. */

int SLInsert(SortedListPtr list, void *newObj) {
	
	//checks for null list or obj
	if(list == NULL || newObj == NULL)
		return 1;
	
	//create node with the object as its data
	Node *toInsert = (Node*)malloc(sizeof(Node));
	toInsert->data = newObj;
	toInsert->next = NULL;
	toInsert->NumPtrs = 1;

	//if empty, node is set as head

	if(list->head == NULL){
		list->head = toInsert;
		list->head->next = NULL;
		return 0;
	}
	
	int compared = list->comp(list->head->data, newObj);

	//if newObj greater than or equal to front, insert. must check head first
	if (compared <= 0){
		toInsert->next = list->head;
		list->head = toInsert;
		return 0;
	}

	Node *curr = list->head->next;
	Node *prev = list->head;
	//iterates through list using curr and prev pointers
	while(curr != NULL){

		//compares curr with newObj
		int result = list->comp(curr->data, newObj);

		//insert if newObj greater than or equal to
		if (result <= 0) {
			toInsert->next = curr;
			prev->next = toInsert;
			return 0;
		}

		prev = curr;
		curr = curr->next;
	}
	prev->next = toInsert;
	return 0;
}



/*iterates through a linked list, searching for a Node that contains the data we're looking for, If found, that Node is removed from the list. Removing the head node is a slightly different procedure */

int SLRemove(SortedListPtr list, void *toRemove){

	/*If either the list, the node at the head, or the thing we want to search for are NULL, thats unaccetable. Must return bad input. 0 is good, 1 is bad.*/
	if(list == NULL || list->head == NULL || toRemove == NULL) return 1;

	/*is head the Node we're looking for? If so, get a reference to head, set head to be the second Node, and then destroy the old head reference.*/
	if (list->comp(list->head->data, toRemove) == 0) {

		Node* temp = list->head;
		list->head = list->head->next;

		
		temp->NumPtrs--;

		if (temp->NumPtrs == 0) {
			free(temp->data);
			free(temp);
		}
		else list->head->NumPtrs++;
		
		return 0;
	}

	/*We know that it's not the first one, so we can start the loop at the second one, which still may be null. */
	Node* prev = list->head;
	Node* curr = list->head->next;

	while(curr != NULL){
		if(list->comp(curr->data, toRemove)==0){
			/*we found it. set the list to skip over it, and destroy it. Then return. */
			prev->next = curr->next;
			
			(curr->NumPtrs)--;
			if(curr->NumPtrs == 0){
				list->dest(curr->data);
				free(curr);	
			}
			else if(curr->next != NULL){
					(curr->next->NumPtrs)++;
			}
			return 0;
		}
		/*otherwise, keep iterating through the list */
		prev = curr;
		curr = curr->next;
	}
	/*If we get here, then a matching Node was never found */
	return 0;
}

/*given a list, creates a tool that can go through the list and look at each Node
 */
SortedListIteratorPtr SLCreateIterator(SortedListPtr list){
	/*as always, if given NULL input, return bad input in the form of NULL */
	if(list  == NULL) return NULL;
	/*initialize SLIPtr struct to return */	
	SortedListIteratorPtr SLI = (SortedListIteratorPtr)malloc(sizeof(struct SortedListIterator));
	/*initialize fields */
	SLI->curr = list->head;
	SLI->curr->NumPtrs++;
	SLI->dest = list->dest;
	return SLI;

}

void SLDestroyIterator(SortedListIteratorPtr SLI){
	/*If its already NULL, we're done */
	if(SLI == NULL) return;

	/*If the Node it points to is NULL, just free and return */
	if(SLI->curr == NULL){
		free(SLI);
		return;
	}

	/*decrement the pointer count */
	SLI->curr->NumPtrs--;
	
	/*If the count is 0, remove the Node and keep doing that for the next one */
	while(SLI->curr->NumPtrs == 0){
		Node* tempNode = SLI->curr;
		SLI->curr = SLI->curr->next;
		free(tempNode);
		SLI->curr->NumPtrs--;
	}
	free(SLI);
}

/*Given an iterator pointer, return the value of the Node at which it points.
 * But first, some nullity checks have to be done */
void *SLGetItem(SortedListIteratorPtr SLI){

	if(SLI == NULL){
		return NULL;
	}
	if(SLI->curr == NULL){
		return NULL;
	}
	return SLI->curr->data;
}

/*Given an iterator pointer, make it point to the next item after the one its currently looking at */
void *SLNextItem(SortedListIteratorPtr SLI){

	/*Must check for bad input, or if the pointer's at the end of the list */
	if(SLI == NULL){
		return NULL;
	}
	if(SLI->curr == NULL){
		return NULL;
	}
	
	/*check to see if the pointer's at the last item in the list */
	if(SLI->curr->next == NULL){
		/*deallocate the Node if it has 0 pointers, and then return NULL */
		if(SLI->curr->NumPtrs == 0){
			free(SLI->curr->data);
			free(SLI->curr);
			return NULL;
		}
		return NULL;
	}	
	
	Node* tempNode = SLI->curr;

	SLI->curr = SLI->curr->next;

	(tempNode->NumPtrs)--;

	if(tempNode->NumPtrs == 0){
		SLI->dest(tempNode->data);
		free(tempNode);
		(SLI->curr->NumPtrs)++;
	}
	else{
		(SLI->curr->NumPtrs)++;
	}
	/*we're done, return the data we wanted from the next one */
	return SLI->curr->data;

}
