#include <stdio.h>
#include <stdlib.h>
#include <string.h>
/*
	este programa es para sacar todas las permutaciones con repeticion dado una cadena de entrada por linea de comandos asi como el numero de grupos dado por linea de comandos esto es que para ejecutar este programa se deve ase ./nombreExecutable alfabeto numeroGrupos el programa genera uno o varios archivos en donde pone las permutaciones
ejemplo ./a.out ab 2
el archivo generado contendria
aa
ab
ba
bb
*/
typedef struct nodo{
	char letter;
	struct nodo **son;
}nodo;
int groups,sizeAlphabet,nf=0;
unsigned int numberBlendInFile=0;
char *alphabet,*blend,nameFile[17];
FILE *file;
void createTree(nodo **root,char letter,int group){
	int i;
	if(*root==NULL){
		if((*root=(nodo *)malloc(sizeof(nodo)))==NULL){
			printf("\nmemory is empty :(");
			return;
		}
	}
	(*root)->letter=letter;
	(*root)->son=NULL;
	if(group<groups-1){
		if(((*root)->son=(nodo **)malloc(sizeof(nodo *)*sizeAlphabet))==NULL){
			printf("\nmemory is empty :(");
			return;
		}
		for(i=0,++group;i<sizeAlphabet;i++)
			createTree(&(*root)->son[i],alphabet[i],group);
	}
}
void readSavePermutaciones(nodo *root){
	int i;
	if(root!=NULL){
		strncat(blend,&root->letter,1);
		if(root->son!=NULL){
			for(i=0;i<sizeAlphabet;i++){
				readSavePermutaciones(root->son[i]);
			}
			blend[strlen(blend)-1]=0;//blend[strlen(blend)-2]=0;
		}else{
			fprintf(file,"%s\n",blend);
			printf("%s",blend);
			for(i=0;i<sizeAlphabet;i++)printf("\b");
			blend[strlen(blend)-1]=0;
		}
	}
}
int main(int ari,char **arc){
	if(ari!=3){
		printf("\nerror ejecute como %s alfabeto numeroGrupos\n",arc[0]);
		return -1;
	}
	int i;
	char name[30]="dic_ .dic";
	nodo *root=NULL;//root of my tree
	groups=atoi(arc[2]);//groups of user entered
	sizeAlphabet=strlen(arc[1]);//lenght of alphabet
	alphabet=arc[1];//alphabet of user entered
	blend=(char *)malloc(sizeof(char)*groups);//combination 
	createTree(&root,alphabet[0],0);
	for(i=0;i<sizeAlphabet;i++){
		name[strlen(name)-5]=alphabet[i];
		if((file=fopen(name,"w+"))==NULL){
			printf("\nerror in open file %s",name);
			return 1;	
		}
		root->letter=alphabet[i];
		readSavePermutaciones(root);
		fclose(file);
	}
	puts("\n");return 0;
}
