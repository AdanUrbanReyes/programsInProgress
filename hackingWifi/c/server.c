#include <stdio.h>
#include "multicast/multicastSocket.h"
#define TBAIS 5//TIME BETWEEN ANNUNCES IN SECONDS
char *pta;//port to announce
void *announce(void *args){
	Multicast connection=*((Multicast *)args);
	int i=0;
	while(i++<5){
		sendString(connection,pta);
		sleep(TBAIS);
	}
	return NULL;
}
int main(int ari,char **arc){//./multicastServer ip port portToAnnounce
	if(ari<5){
		printf("ejecute como %s ipSocketMulticast puertoSocketMulticast  puertoAnunciar/puertoSocketFlujo conexionesMaximasSocketFlujo\n",arc[0]);
		return 0;
	}
	pta=arc[3];
	//Stream cs=getStreamServerConnection(atoi(arc[3]),atoi(arc[4]));//connection stream
	Multicast cm=getMulticastConnectionServer(arc[1],atoi(arc[2]));//connection multicast
	announce((void *)&cm);
	return 0;
}
