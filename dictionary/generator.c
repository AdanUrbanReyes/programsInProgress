#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>//for functions write,read,close
#include <sys/types.h>//for functions open
#include <sys/stat.h>//for functions open
#include <fcntl.h>// for functions open
#include <time.h>//for functions time,localtime,strftime
#include <math.h>
char *alphabet;
unsigned char groups,*pointer,lenghtAlphabet;
double *limit=NULL;//array of two position, the position [0] containts lower limit and the position [1] containts upper limit 
void loadParameters(int ari,char **arc){
	unsigned char i;
	for(i=1;i<ari;i++){
		if(!memcmp(arc[i],"-a",2)){
			alphabet=arc[++i];
		}else{
			if(!memcmp(arc[i],"-g",2)){
				groups=atoi(arc[++i]);
			}else{
				if(!memcmp(arc[i],"-l",2)){
					limit=(double *)malloc(sizeof(double)*2);
					sscanf(arc[++i],"%le-%le",&limit[0],&limit[1]);
				}else{
					printf("parametro %s desconocido\n",arc[i]);
				}
			}
		}
	}	
}
void startPointers(double iteration,unsigned char indexPointer){
	if(iteration<=0)
		return;
	unsigned char residue=((int)iteration)%lenghtAlphabet;
	iteration-=residue;
	pointer[indexPointer]=residue;
	startPointers(iteration/lenghtAlphabet,indexPointer-1);	
}
char *getDateTime(){//this function return day and time actual
	char *dateTime=(char *)malloc(sizeof(char)*19);
	time_t t;
	struct tm *tm;
	t=time(NULL);
	tm=localtime(&t);
	strftime(dateTime,19,"%m:%d:%y_%H:%M:%S",tm);//set formto and save in variable dateTime
	return dateTime;
}
char *getNameFileTime(char *indexNameFile,char *extencion){
	char *nameFile=(char *)malloc(sizeof(char)*37);
	char *dateTime=getDateTime();//get date and time
	strcpy(nameFile,indexNameFile);//build name file
	strncat(nameFile,dateTime,strlen(dateTime));//build name file
	strcat(nameFile,extencion);//finish build name file
	return nameFile;
}
void rrecorre(unsigned char indexPointer){
	pointer[indexPointer]++;//recorro el puntero o indice a la siguiente pocicion del string alphabet
	if(pointer[indexPointer]==lenghtAlphabet){//pointer llego asta el final of string alphabet
		if(indexPointer==0)//indexPointer is 0 then llego asta el primer puntero no puedo recorrer ponter[0-1] stop recurcividad
			return;
		rrecorre(indexPointer-1);//recorro index anterior and
		pointer[indexPointer]=0;//put index actual in 0
	}
}
void combinations(){
	int descriptor=open(getNameFileTime("diccionario_",".dic"),O_CREAT|O_WRONLY|O_TRUNC,0666);//open file dictionary
	unsigned char i;
	for(;limit[0]<limit[1];limit[0]++){
		for(i=0;i<groups;i++)
			write(descriptor,&alphabet[pointer[i]],sizeof(char));
		write(descriptor,"\n",sizeof(char));
//		 printf("%c",alpahbet[pointer[i]]);
		//printf("\n");
		rrecorre(groups-1);
	}
	close(descriptor);
}
int main(int ari,char **arc){//./generator -a "abc" -g 4 -l 0-7
	if(ari<2){
		printf("ejecucion minima %s alfabeto grupos\n",arc[0]);
		return 0;
	}
	loadParameters(ari,arc);
	lenghtAlphabet=strlen(alphabet);
	pointer=(unsigned char *)malloc(sizeof(unsigned char)*groups);
	printf("alfabeto = %s\ngrupos = %d\n",alphabet,groups);
	if(limit!=NULL){
		printf("limite inferior = %e\nlimite superior = %e",limit[0],limit[1]);
		startPointers(limit[0],groups-1);
	}else{
		limit[0]=0;
		limit[1]=pow(lenghtAlphabet,groups);
	}
	combinations();
	return 0;
}
