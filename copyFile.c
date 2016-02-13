#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
void copyFile(char *pathOrigin,char *pathCopy){
	int descriptorCopy=open(pathCopy,O_WRONLY|O_CREAT,0666);
	int descriptorOrigin=open(pathOrigin,O_RDONLY);
	if(descriptorOrigin<0 || descriptorCopy<0){
		return;
	}
	long long int buffer;
	ssize_t bytesReaded;
	while((bytesReaded=read(descriptorOrigin,&buffer,sizeof(long long int)))>0){
		write(descriptorCopy,&buffer,bytesReaded);	
	}
	close(descriptorCopy);
	close(descriptorOrigin);
}
int main(int ari,char **arc){
	copyFile(arc[1],arc[2]);
	return 0;
}
