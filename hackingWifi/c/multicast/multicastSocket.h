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
#ifndef __MULTICASTSOCKET_H__
#define __MULTICASTSOCKET_H__
typedef struct Multicast{
	int socket;
	struct sockaddr_in group;
}Multicast;
void fillSockaddr_inServer(struct sockaddr_in *address,char *ip,int port){
	address->sin_family=AF_INET;
	address->sin_addr.s_addr=inet_addr(ip);
	address->sin_port=htons(port);
}
void fillSockaddr_inClient(struct sockaddr_in *address,int port){
	address->sin_family=AF_INET;
	address->sin_port=htons(port);
	address->sin_addr.s_addr=INADDR_ANY;
}
Multicast getMulticastConnectionServer(char *ip,int port){
	Multicast cm;//connection multicast
	bzero(&cm,sizeof(Multicast));
	if((cm.socket=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("error al obtener descriptor de socket multicast\n");
		exit(-1);
	}
	fillSockaddr_inServer(&cm.group,ip,port);
	return cm;
}
Multicast getMulticastConnectionClient(char *ip,int port){
	Multicast cm;//connection multicast
	bzero(&cm,sizeof(Multicast));
	if((cm.socket=socket(AF_INET,SOCK_DGRAM,0))<0){
		printf("error al obtener descriptor de socket multicast\n");
		exit(-1);
	}
	int reuseaddress = 1;
	if(setsockopt(cm.socket, SOL_SOCKET, SO_REUSEADDR,(void *)&reuseaddress, sizeof(reuseaddress)) < 0){//Enable SO_REUSEADDR to allow multiple instances of this 
		printf("error poniendo opcion al socket SO_REUSEADDR\n");
		close(cm.socket);
		exit(1);
	}
	fillSockaddr_inClient(&cm.group,port);
	if(bind(cm.socket,(struct sockaddr *)&cm.group,sizeof(struct sockaddr_in))<0){
		printf("error uniendo socket datagrama\n");
		close(cm.socket);
		exit(-1);
	}
	struct ip_mreq group;
	group.imr_multiaddr.s_addr=inet_addr(ip);
	group.imr_interface.s_addr=htonl(INADDR_ANY);
	if(setsockopt(cm.socket, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char *)&group, sizeof(struct ip_mreq)) < 0){
		printf("error agregando grupo multicast\n");
		close(cm.socket);
		exit(-1);
	}
	return cm;
}
short sendSize_t(Multicast *cm,size_t size){
	ssize_t bytesSended=sendto(cm->socket,&size,sizeof(size_t),0,(struct sockaddr *)&cm->group,sizeof(struct sockaddr_in));
	if(bytesSended<0){
		printf("error al enviar size_t:%zu atraves del socket %d\n",size,cm->socket);
		return -1;
	}
	return 0;
}
size_t *receiveSize_t(Multicast *cm){
	size_t *size=(size_t *)malloc(sizeof(size_t));
	size_t ss=sizeof(struct sockaddr_in);//sizeof sockaddr_in
	ssize_t bytesReaded=recvfrom(cm->socket,size,sizeof(size_t),0,(struct sockaddr *)&cm->group,(socklen_t *)&ss);
	if(bytesReaded<0){
		printf("error al recivir size_t atraves del socket %d\n",cm->socket);
		return NULL;
	}
	return size;
}
short sendObject(Multicast *cm,void *object,size_t sizeofObject){//send any type data 
	sendSize_t(cm,sizeofObject);
	ssize_t bytesSended=sendto(cm->socket,object,sizeofObject,0,(struct sockaddr *)&cm->group,sizeof(struct sockaddr_in));
	if(bytesSended<0){
		printf("error al enviar objecto atraves del socket %d\n",cm->socket);
		return -1;
	}
	return 0;
}
void *receiveObject(Multicast *cm){//receive any type data
	size_t sizeofObject=*(receiveSize_t(cm));
	void *object=(void *)malloc(sizeofObject);
	size_t ss=sizeof(struct sockaddr_in);//sizeof sockaddr_in
	ssize_t bytesReaded=recvfrom(cm->socket,object,sizeofObject,0,(struct sockaddr *)&cm->group,(socklen_t *)&ss);
	if(bytesReaded<0){
		printf("error al recivir objeto atraves del socket %d\n",cm->socket);
		return NULL;
	}
	return object;
}
char *receiveString(Multicast *cm,int sizeofString){
	size_t ssib=sizeof(char)*sizeofString;//sizeof string in bytes
	char *string=(char *)malloc(ssib);
	size_t ss=sizeof(struct sockaddr_in);//sizeof sockaddr_in
	if(recvfrom(cm->socket,string,ssib,0,(struct sockaddr *)&cm->group,(socklen_t *)&ss)<0){
		printf("error reciviendo string del socket %d\n",cm->socket);
		return NULL;
	}
	return string;
}
short sendString(Multicast cm,char *string){//cm connection multicast
	size_t ssib=strlen(string)*sizeof(char);//sizeof string in bytes	
	if(sendto(cm.socket,string,ssib,0,(struct sockaddr*)&cm.group, sizeof(struct sockaddr_in)) < 0){
		printf("error enviando string:%s por el socket:%d\n",string,cm.socket);
		return -1;
	}
	return 0;
}
#endif
