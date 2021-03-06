#include <stdlib.h> 
#include <stdio.h> 
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <signal.h>
#include <pthread.h>
#include <unistd.h>
#include <ctype.h>

//#define localhost "127.0.0.1"

pthread_mutex_t counter;

int gsd;
const char * end = "end";
const char * killer = "kill";

int validFloat(char arg[]){
	printf("validFloat recieved the string %s\n", arg);
	if (*arg == '-') arg+=1;
   	if (*arg == '\0'){
		puts("empty or just -");
		return 0;
	}
	int decimalPointFlag = 0;
  	while(*arg != '\0'){
		if(*arg == '.'){
			if(decimalPointFlag > 0){
				puts("too many DPs");
				return 0;
			}
			decimalPointFlag+=1;
		}
		else if(isdigit(*arg) == 0){
			puts("found a nondigit");
			return 0;
		}
		arg+=1;
	}
	return 1;
}


void getInput(void* param){  //used to be a *func
	
//	printf("line: %d\n",__LINE__);		
	//pthread_mutex_lock(&counter);
	
	char message[200];	
	printf("Enter a message: >>");
        fgets(message, 200, stdin);

//	printf("line: %d\n",__LINE__);		
	char cmd[8];
	bzero(cmd, 8);

	char arg[200];
	bzero(arg, 200);
	
	sleep(2);

//		printf("line: %d\n",__LINE__);		
	//printf("you entered : %s\n", message);
	int argflag = 0;

	int i;
	for(i = 0; i < strlen(message) + 1; i++){
		if(message[i] == ' ' || message[i] == '\0'){

			memcpy(cmd, &message[0], i);
			if(message[i] == '\0') break;//there was no arg
			memcpy(arg, &message[i+1], strlen(message) - (i+1));
			argflag = 1;
			break;
		}
	}

	//printf("line: %d ENDINGGG \n",__LINE__);
	if(!argflag)cmd[strlen(cmd) - 1] = '\0';
	//printf("cmd is %s.\n", cmd);
	if(argflag){
		arg[strlen(arg) - 1] = '\0';
		printf("arg is %s.\n", arg);
		if(strcmp(arg, "")==0){
			puts("No name entered. ");
			return;
		}
	}
	//we have cmd. now, verify it. if its good, send back message. if not, send back null bytes.


	//DONT NEED ARGS
	if(strcmp(cmd, "exit") == 0 ||
	   strcmp(cmd, "balance") == 0 ||
	   strcmp(cmd, "finish") == 0 ||
	   strcmp(cmd, "list") == 0)
		strcpy(param, message);
	//NEED ARGS
	else if(argflag &&
		(strcmp(cmd, "start") == 0 ||
		strcmp(cmd, "open") == 0))
			strcpy(param, message);
	else if(argflag && validFloat(arg) &&
		((strcmp(cmd, "credit") == 0) ||
		strcmp(cmd, "debit") == 0))
			strcpy(param, message);
//////////addingg line becuse server responds to what it is doing
	//printf("%d\n", __LINE__);	
/////////////////	
//	pthread_mutex_unlock(&counter);
	return; //used to say NULL

}

 
void signalHandler(int signum){
	close(gsd);
	printf("\n\n\n\n\n______________________\n\nClosing client window.\n______________________\n\n");
	exit(0);
}

int main(int argc , char *argv[]){
    
	signal(SIGINT, signalHandler);

	if(argc < 2){
		printf("No IP specified. closing.\n");
		exit(0);
	}
	if(strcmp(argv[1], "localhost")==0)argv[1] = "127.0.0.1";
	struct sockaddr_in server;

	gsd = socket(AF_INET, SOCK_STREAM , 0);
	if (gsd == -1){
		printf("Socket connection creation unsuccessful.\n");
	}
	printf("Socket successfully created.\n");
     
	server.sin_addr.s_addr = inet_addr(argv[1]);//inet_addr("localhost");
	//printf("%d\n", inet_addr(INADDR_ANY));
	server.sin_family = AF_INET;
	server.sin_port = htons(4022);
	

     	
	while(connect(gsd, (struct sockaddr *)&server , sizeof(server)) < 0){
		printf("Connecting to server....\n");
		sleep(3);
	}	printf("\n\nSuccessfully connected to server.\n");
     
	char toReceive[200];
	toReceive[0] = '&';
	while(1){
        	char toSend[200];
		//memset(toSend, '\0', 200);
		bzero(toSend,200);
////////	pthread_t userthread;
	
		//this thread takes user input. 
		//if its valid, it writes the input to message. 
		//if not, message remains null bytes and gets caught by the if.
////////		pthread_create(&userthread, NULL, getInput, &toSend);
		getInput(&toSend);
		//printf("line: after pcrea%d\n",__LINE__);		
////////		pthread_join(userthread, NULL);

		//printf("line: after pjoia%d\n",__LINE__);	
		if(toSend[0] == '\0'){
			printf("Not a valid command. Try again.\n");
			continue;
		}

		//printf("line: %d\n",__LINE__);		
		//at this point, guaranteed to be properly formatted

		toSend[strlen(toSend) - 1] = '\0';

//		printf("Sending this string: \"%s.\"\n", toSend);

        	if(send(gsd, toSend, strlen(toSend), 0) < 0){
			printf("Unable to send message.\n");
			raise(SIGINT);
		}
	
		//printf("before endwhile, line: %d end?: %s\n",__LINE__,end);		

//////////////////// this is where the program stops, its waiting for a write from server
		
		bzero(toReceive, 200);	
		while( strcmp(toReceive,end) != 0){

			bzero(toReceive,200);
			if(recv(gsd, toReceive, 200, 0) < 0){
				printf("Nothing recieved back from server.\n");
				raise(SIGINT);
			}

			//printf("eric's printf: %s\n",toReceive);
			if(toReceive[0] == 'e' && toReceive[1] == 'n'){
				 break;
			}
			if(strcmp(toReceive, killer) == 0) raise(SIGINT);
			printf("Server: %s \n",toReceive);
		}
		
		//printf("eric's printf2: %s\n",toReceive);
		//printf("line: %d\n",__LINE__);		
		if(strcmp(toSend,"exit") == 0){
			raise(SIGINT);
		}		
		//printf("line: %d\n",__LINE__);		
//		if(strcmp(toReceive, "disconnect") == 0) exit(0);
		//printf("Server: %s \n", toReceive);
		bzero(toReceive, 200);
		//printf("line: %d\n",__LINE__);		
	}
    
	//printf("line: closeing %d\n",__LINE__);		
	close(gsd);
	return 0;
}
