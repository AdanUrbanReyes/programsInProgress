#include <stdio.h>
#include "multicast/multicastSocket.h"
void *listenServersAvailables(void *args){
	Multicast connection=*((Multicast *)args);
	char *string=receiveString(&connection,5);
	int i=0;
	while(i++<5){
		printf("cliente recivio el mensaje:%s| de la ip:%s\n",string,inet_ntoa(connection.group.sin_addr));
		sleep(1);
	}
	//printf("receiveString ip:%s\n",inet_ntoa(addr.sin_addr));
	return NULL;
}
int main(int ari,char **arc){
	if(ari<3){
		printf("execute como %s ip puerto\n",arc[0]);
		return 0;
	}
	Multicast connection=getMulticastConnectionClient(arc[1],atoi(arc[2]));
	listenServersAvailables((void *)&connection);
	return 0;
}
