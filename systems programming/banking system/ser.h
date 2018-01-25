#ifndef ff
#define ff

#include <pthread.h>

typedef struct Account{
	pthread_mutex_t lock;
	int session;
	int init;
	char name[100];
	double balance;
} Account;

typedef struct Bank
{
	pthread_mutex_t lock;
	int numAccounts;
	Account accounts[20];
} Bank;

void create_shm();

void get_shm();

int claim_port(const char * port);

int client_session_actions(int sd);

int parse_request(char * request, char * response, int fd);

#endif
