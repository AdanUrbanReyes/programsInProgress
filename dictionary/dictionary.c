#include <stdio.h>//for functions fopen,feof,fscanf,sscanf
#include <string.h>//for functions memcmp,strncat,strlen,strcasecmp,strdup,strtok,strcpy,strcat
#include <stdlib.h>//for functions malloc,atoi
#include <unistd.h>//for functions write,read,close
#include <sys/types.h>//for functions open
#include <sys/stat.h>//for functions open
#include <fcntl.h>// for functions open
#include <time.h>//for functions time,localtime,strftime
unsigned char groups,*pointer,*bei0,lenghtAlphabet;//pointer for simular a tree of permutaciones,bei0 (begin end index 0)
char *alphabet;//alphabet to make permutaciones	
int dsDictionary;//descriptor of file for save dictionary
/*
	use unsigned char then
	groups can maximum value 255
	and lenght alpahbet maximum is 255 characteres
*/
void rrecorre(unsigned char indexPointer){
	pointer[indexPointer]++;//recorro el puntero o indice a la siguiente pocicion del string alphabet
	if(pointer[indexPointer]==lenghtAlphabet){//pointer llego asta el final of string alphabet
		if(indexPointer==0)//indexPointer is 0 then llego asta el primer puntero no puedo recorrer ponter[0-1] stop recurcividad
			return;
		rrecorre(indexPointer-1);//recorro index anterior and
		pointer[indexPointer]=0;//put index actual in 0
	}
}
unsigned char isFinish(){
	unsigned char i;
	if(bei0!=NULL){
		if(pointer[0]==*(bei0+1))
			return 1;
	}
	if(pointer[0]<lenghtAlphabet)//if index 0 not a llegado al final of alphabet entonces no a terminado regresa false (0)
		return 0;
	for(i=1;i<groups;i++){//vasta conque uno no este en el indice 0 del alfabeto para que sepa que no a terminado
		if(pointer[i]!=0)
			return 0;
	}
	return 1;
}
void permutacionesWithRepeticion(){
	unsigned char i;
	if(bei0!=NULL)//question if bei0 not is null
		*pointer=*bei0;//put start for pointer[0]
	while(!isFinish()){
		for(i=0;i<groups;i++)
			write(dsDictionary,&alphabet[pointer[i]],sizeof(char));
//			printf("%c",alphabet[pointer[i]]);
		write(dsDictionary,"\n",sizeof(char));
		//printf("\n");
		rrecorre(groups-1);
	}
}
char loadParametersFile(char *nameFile){//load paramenters apartir a file
	FILE *file;
	char *name=(char *)malloc(sizeof(char)*10);//here save name of paramenter entered (-alphabet,-groups ..)
	char *value=(char *)malloc(sizeof(char)*1027);//here save value of paramenter
	char *token;//is for save values of paramenter -index
	int i=0;
	if((file=fopen(nameFile,"r"))==NULL){//open file with name received of paramenter
		printf("error opening file %s\nparameters not load :(\n",nameFile);
		return -1;
	}
	while(!feof(file)){//read file also end
		fscanf(file,"%[a-zA-Z-]=%s\n",name,value);//read line by line geting name parementer and value parameter
		if(!strcasecmp(name,"-alphabet")){//question if parameter is alphabet
			alphabet=strdup(value);//create a copy of value and save in alphabet
		}else{
			if(!strcasecmp(name,"-groups")){//question if parameter is groups
				groups=atoi(value);//save groups with function atoi convert value (char * ) to integer
			}else{
				if(!strcasecmp(name,"-bei0")){//question if parameter is begein end index 0 (bei0)
					bei0=(unsigned char *)malloc(sizeof(unsigned char)*2);//recervo memoria para bei0
					sscanf(value,"%d-%d",(int *)bei0,(int *)(bei0+1));//save integers that estan in value to bei0
				}else{
					if(!strcasecmp(name,"-indexs")){//for finish question if parameter is index
						pointer=(unsigned char *)malloc(sizeof(unsigned char)*groups);//init the "pointes" or indices, put the size envace a los grupos
						token=strtok(value,",");//make a split or get token with separados by ","
						for(i=0;token!=NULL;i++){
							*(pointer+i)=atoi(token);//save as integer the value of token in pointer
							token=strtok(NULL,",");
						}
					}
				}
			}
		}
	}
	fclose(file);//close file
	return 0;
}
char loadParametersSTDIN(int ari,char **arc){//load parameters entered in terminal
	int i;
	for(i=1;i<ari;i++){//start recorrer my array in 1 because 0 is ./nameexecutable
		if(!memcmp(arc[i],"-a",2)){//set alphabet
			alphabet=arc[++i];
		}else{
			if(!memcmp(arc[i],"-g",2)){//set groups and start pointer
				groups=atoi(arc[++i]);
				pointer=(unsigned char *)malloc(sizeof(unsigned char)*groups);//init the "pointes" or indices, put the size envace a los grupos	
			}else{
				if(!memcmp(arc[i],"-bei0",5)){//set begin and end of index 0 for pointers 
					bei0=(unsigned char*)malloc(sizeof(unsigned char)*2);
					sscanf(arc[++i],"%d-%d",(int*)bei0,(int *)(bei0+1));
				}else{
					if(!memcmp(arc[i],"-bf",3)){//set all parameters apartir of a file
						loadParametersFile(arc[++i]);
					}else{
						printf("parameter %s desconocido me salto\n",arc[i]);
					}
				}
			}
		}
	}
	return 0;
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
char saveProgress(){
	char *nameFile=getNameFileTime("progresSaved_",".txt");
	char *aux=(char *)malloc(sizeof(char)*7);//use as auxiliar for save numbers in file
	int i,dsFile=open(nameFile,O_CREAT|O_WRONLY|O_TRUNC,0722);//open file
	if(dsFile==-1){//question if file can open 
		printf("error opening file %s\nprogress not saved :(\n",nameFile);
		return -1;
	}
	//write alphabet
	write(dsFile,"-alphabet=",sizeof(char)*10);
	write(dsFile,(void *)alphabet,lenghtAlphabet);
	//write groups
	write(dsFile,"\n-groups=",sizeof(char)*9);
	sprintf(aux,"%d",groups);
	write(dsFile,aux,strlen(aux));
	//write begin and end index if variable bei0!=NULL
	if(bei0!=NULL){
		write(dsFile,"\n-bei0=",sizeof(char)*7);
		sprintf(aux,"%d-%d",*bei0,*(bei0+1));
		write(dsFile,aux,strlen(aux));
	}
	//write indexs
	write(dsFile,"\n-indexs=",sizeof(char)*9);
	for(i=0;i<groups;i++){
		sprintf(aux,"%d",*(pointer+i));
		write(dsFile,aux,strlen(aux));
		if(i<groups-1)
			write(dsFile,",",sizeof(char));
	}
	close(dsFile);
	return 0;
}
int main(int ari,char **arc){//./a.out -a alphabet -g groups 
	if(ari<2){
		printf("this program requiere as minimum a file with progress saved or 2 paremeters -a alphabet -g numberGroups\n");
		printf("explication of parameters\n-a especifica a alphabet\n-g especifica groups number\n-bei0 especifica range for pointer[0]\n\texample if alphabet is abc and groups 3 and you especificaste un rando de 0-2 imprimira aaa,aab,...bcc\n-bf especifica a boot file to load all paramentes antes mencionados\n");
		return -1;
	}
	loadParametersSTDIN(ari,arc);
	printf("alphabet=%s\ngroups=%d\n",alphabet,groups);
	lenghtAlphabet=strlen(alphabet);
	if(bei0!=NULL)
		printf("start pointer[0]=%d\nend pointer[0]=%d\n",*bei0,*(bei0+1));
	else{
		bei0=(unsigned char*)malloc(sizeof(unsigned char)*2);
		*bei0=0;
		*(bei0+1)=lenghtAlphabet;
	}
	dsDictionary=open(getNameFileTime("dictionary_",".dic"),O_CREAT|O_WRONLY|O_TRUNC,0722);//open file dictionary
	if(dsDictionary==-1){
		printf("error opening dictionary :(");
		return -1;
	}
	permutacionesWithRepeticion();
	close(dsDictionary);
	return 0;
}
