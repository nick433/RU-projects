#include <iostream>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <cstdlib>
#include <ctime>
using namespace std;

class Node{
    
	public:
	
	int x;
	int y;
    Node* next;
    Node() {};
    void setCoord(int x1, int y1){
		x = x1;
		y = y1;
	}
    void setNext(Node* next1){ 
		next = next1; 
	}
    Node* Next(){ 
		return next; 
	}
};


class List {
	public:
	Node *head;
    List() {
		head = NULL; 
	}
	void print() {

		Node *tmp = head;

		if ( tmp == NULL ) {
		cout << "EMPTY" << endl;
		return;
		}

		if ( tmp->Next() == NULL ) {
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
	void addEnd(int x, int y) {

		Node* newNode = new Node();
		newNode->setCoord(x,y);
		newNode->setNext(NULL);
		
		if ( head == NULL ) { //empty
			head = newNode;
		}
		else {
		// First node in the list
			Node* temp = head;
			while(temp->Next() != NULL){
				temp = temp->Next();
			}
			temp->setNext(newNode);
		}
		
	}
	void addFront(int x, int y) { //should reject duplicates
		cout << "addFront "<< endl;
		/*
		Node *tmp = head;
		// One node in the list
		if ( tmp->Next() == NULL ) {
			if(tmp->x == x && tmp->y == y ){
				return false;
			}
			
		}
		else {
		// Parse and print the list
			do {
				if(tmp->x == x && tmp->y == y ){
					return false;
				}
				tmp = tmp->Next();
			}
			while ( tmp != NULL );

		} */
		if(head != NULL){
			if(x == head->x && y == head->y){
				cout << "no duplicates!" << endl;
				return; // hopefully this prevents what was causing duplicates
			}
		}
		Node* newNode = new Node();
		newNode->setCoord(x,y);
		newNode->setNext(head);
		
		head = newNode;
		
		cout << __LINE__ << endl;
	}
	void removeFront(){
		cout << "remove front"<< endl;
		if(head->next != NULL){
			Node *temp = head->Next();
			delete head;
			head = temp;
		}
		else if(head != NULL){
			head = NULL;
			
		}
		cout << __LINE__ << endl;
	}

		
};


class Coord {
	public:
	Coord *next;
	Coord(int x1, int y1){
		x = x1;
		y = y1;
	}
	set(int x1, int y1){
		x = x1;
		y = y1;
	}
	int x;
	int y;
};

class Tile {
   public:
	Tile(){
		setType('R');
		x = 0;
		y = 0; //coordinates should be set with the 3 arg constructor
		highway = false;
		border = false;
	}
	Tile(char t, int x1, int y1){ //x and y are the coordinates of this tile's location in the grid
		x = x1;
		y = y1;
		setType(t);
		highway = false;
		border = false;
	}
	//may or may not include a coordinate field
	//R is default (regular), H means harder to traverse, B = blocked 
	int x;
	int y;
	char type; 
	bool highway;
	bool border;
	float cardinal;   // Length of a box
	float diag;  // Breadth of a box
	void setType(char t){
		type = t;
		setMoves(t);
	}
	void setMoves(char t){ //might opt to use getdiag and getcardinal functions to limit size of objects
		if(t == 'R'){
			cardinal = 1;
			diag = 1.4142135;
		}
		else if(t == 'H'){
			cardinal = 2;
			diag = 2.8284271;				
		}
		else if(t == 'B'){
			cardinal = -1;
			diag = -1;				
		}
	}
	float getDistance(Tile* dest){
		int sx, sy, dx, dy;
		sx = this->x;
		sy = this->y;
		dx = dest->x;
		dy = dest->y;
		float sh = 1;
		float dh = 1;
		if(this->highway){
			sh = 0.25;
		}
		if(dest->highway){
			dh = 0.25;
		}
		if(dest->type == 'B'){
			return -1;
		}
		if(dx == sx || dy == sy){
			//dest is one above start
			return (this->cardinal*sh + dest->cardinal*dh)/2;
		}
		else{ //since we are only checking valid moves, the other options must be a diag
			return  (this->diag*sh + dest->diag*dh)/2;
		}
	}
};
void foo();

void foo(){
	
}