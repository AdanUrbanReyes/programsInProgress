#include <stdio.h>
#include <pthread.h>
#include "streamSocket.h"
extern void beforeEnd(int ,void *);
void handlerSignalSIGINT(int signal){
	exit(-1);
}
void *handlerClient(void *args){
	Stream client=*((Stream*)args);
	char *string=NULL;
	do{
		string=(char *)receiveObject(client.socket);
		printf("cliente ip:%s puerto:%d dice -> %s\n",inet_ntoa(client.address.sin_addr),client.address.sin_port,string);
	}while(strcmp(string,"-1")!=0);
	printf("cliente %s/%d finalizo su connecion\n",inet_ntoa(client.address.sin_addr),client.address.sin_port);
	return NULL;
}
void waitClientsForEver(Stream connection){
	Stream connectionClient;
	socklen_t soac=sizeof(struct sockaddr_in);//sizeof address client
	while(1){
		printf("esperando cliente ...\n");
		if((connectionClient.socket=accept(connection.socket,(struct sockaddr *)&connectionClient.address,&soac))<0){
			printf("error al aceptar la conexion con el cliente\n");
			exit(-1);
		}
		printf("cliente conectado desde ip:%s puerto:%d\n",inet_ntoa(connectionClient.address.sin_addr),connectionClient.address.sin_port);
		pthread_t thread;
		pthread_create(&thread,NULL,handlerClient,(void *)&connectionClient);
		sleep(1);
	}
}
int main(int ari,char **arc){//./server port maximumConnections
	if(ari<3){
		printf("ejecute como %s puerto maximoConeccionesParaAceptar\n",arc[0]);
		return 0;
	}
	Stream connection=getStreamServerConnection(atoi(arc[1]),atoi(arc[2]));
	waitClientsForEver(connection);
	return 0;
}
