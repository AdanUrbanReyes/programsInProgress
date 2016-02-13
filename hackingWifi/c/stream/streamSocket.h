#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <arpa/inet.h>
#include <unistd.h>
#ifndef __STREAMSOCKET_H__
#define __STREAMSOCKET_H__
typedef struct Stream{
	int socket;
	struct sockaddr_in address;
}Stream;
void fillSockaddr_inServer(struct sockaddr_in *address,int port){
	address->sin_family=AF_INET;
	address->sin_addr.s_addr=htonl(INADDR_ANY);
	address->sin_port=htons(port);
}
void fillSockaddr_inClient(struct sockaddr_in *address,struct hostent *host,int port){
	address->sin_family=host->h_addrtype;
	memcpy((char *)&address->sin_addr.s_addr,host->h_addr_list[0],host->h_length);
	address->sin_port=htons(port);
}
Stream getStreamClientConnection(int port,char *ip){
	struct hostent *host;
	struct Stream connection;
	bzero(&connection,sizeof(Stream));
	if((host=gethostbyname(ip))==NULL){
		printf("error ip invalida %s\n",ip);
		exit(-1);
	}
	if((connection.socket=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("error obteniendo descriptor de socket\n");
		exit(-1);
	}
	fillSockaddr_inClient(&connection.address,host,port);
	if(connect(connection.socket,(struct sockaddr *)&connection.address,sizeof(struct sockaddr_in))<0){
		printf("error al estableser la coneccion con el servidor ip:%s puerto:%d\n",ip,port);
		close(connection.socket);
		exit(-1);
	}
	printf("conexion establesida :D\n");
	return connection;
}
Stream getStreamServerConnection(int port,int mcta){//mcta maximun clients to accept
	struct Stream connection;
	bzero(&connection,sizeof(Stream));
	if((connection.socket=socket(AF_INET,SOCK_STREAM,0))<0){
		printf("error obteniendo descriptor de socket\n");
		exit(-1);
	}
	fillSockaddr_inServer(&connection.address,port);
	if(bind(connection.socket,(struct sockaddr *)&connection.address,sizeof(struct sockaddr_in))<0){
		printf("error asignando direccion al socket\n");
		close(connection.socket);
		exit(-1);
	}
	listen(connection.socket,mcta);
	return connection;
}
short sendSize_t(int socket,size_t size){
	ssize_t bytesSended=write(socket,&size,sizeof(size_t));
	if(bytesSended<0){
		printf("error al enviar size_t:%zu atraves del socket %d\n",size,socket);
		return -1;
	}
	return 0;
}
size_t *receiveSize_t(int socket){
	size_t *size=(size_t *)malloc(sizeof(size_t));
	ssize_t bytesReaded=read(socket,size,sizeof(size_t));
	if(bytesReaded<0){
		printf("error al recivir size_t atraves del socket %d\n",socket);
		return NULL;
	}
	return size;
}
short sendObject(int socket,void *object,size_t sizeofObject){//send any type data 
	sendSize_t(socket,sizeofObject);
	ssize_t bytesSended=write(socket,object,sizeofObject);
	if(bytesSended<0){
		printf("error al enviar objecto atraves del socket %d\n",socket);
		return -1;
	}
	return 0;
}
void *receiveObject(int socket){//receive any type data
	size_t sizeofObject=*(receiveSize_t(socket));
	void *object=(void *)malloc(sizeofObject);
	ssize_t bytesReaded=read(socket,object,sizeofObject);
	if(bytesReaded<0){
		printf("error al recivir objeto atraves del socket %d\n",socket);
		return NULL;
	}
	return object;
}
#endif
