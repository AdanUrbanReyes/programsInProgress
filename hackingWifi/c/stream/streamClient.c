#include <stdio.h>
#include "streamSocket.h"
#include <signal.h>
#define maximunSizeofString 400
void sendCommands(Stream connection){
	char string[maximunSizeofString];
	do{
		printf("ingrese el comando a enviar (no mayor a %d caracteres) o -1 para terminar la conexion\n",maximunSizeofString);
		scanf("%s",string);
		sendObject(connection.socket,(void *)&string,sizeof(char)*strlen(string));
	}while(strcmp(string,"-1")!=0);
}
int main(int ari,char **arc){
	if(ari<3){
		printf("ejecuta como %s ipServidor puerto\n",arc[0]);
		return 0;
	}
	Stream connection=getStreamClientConnection(atoi(arc[2]),arc[1]);
	sendCommands(connection);	
	return 0;
}
