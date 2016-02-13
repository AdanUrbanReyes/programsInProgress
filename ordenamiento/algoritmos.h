#include <stdio.h>
#include "arbol.h"
#ifndef __ALGORITMOS_H__
#define __ALGORITMOS_H__
void saveNumberInArray(long *array,int sizeArray){
	int i;
	for(i=0;i<sizeArray;i++)
		scanf("%ld",&array[i]);
}
void tree(long *numbers,int numbersReaded){
	int i;
	nodo *raiz=NULL;
	for(i=0;i<numbersReaded;i++)
      addNodo(&raiz,createNodo(numbers[i]));
   inOrden(raiz);
}
void bubbleSimple(long *numbers,int numbersReaded){
	int i,j;
	long temp;
	for(i=1;i<numbersReaded;i++){
		for(j=0;j<numbersReaded-1;j++){
			if(numbers[j]>numbers[j+1]){
				temp=numbers[j];
				numbers[j]=numbers[j+1];
				numbers[j+1]=temp;
			}
		}
	}
	for(i=0;i<numbersReaded;i++)
		printf(" %ld ",numbers[i]);
}
void bubbleBest(long *numbers,int numbersReaded){
	int i,j;
	long temp;
	for(i=1;i<numbersReaded;i++){
		for(j=0;j<i;j++){
			if(numbers[i]<numbers[j]){
				temp=numbers[j];
				numbers[j]=numbers[i];
				numbers[i]=temp;
			}
		}
	}
	for(i=0;i<numbersReaded;i++)
		printf(" %ld ",numbers[i]);
}
void insercion(long *numbers,int numbersReaded){
	int i,j;
	long temp;
	for(i=1;i<numbersReaded;i++){
		temp=numbers[i];
		j=i-1;
		while((numbers[j]>temp)&&j>=0){
			numbers[j+1]=numbers[j];
			j--;
		}
		numbers[j+1]=temp;
	}
	for(i=0;i<numbersReaded;i++)
		printf(" %ld ",numbers[i]);
}
void seleccion(long *numbers,int numbersReaded){
	int i,j,p;
	long temp;
	for(i=0;i<numbersReaded-1;i++){
		p=i;
		for(j=i+1;j>numbersReaded-1;j--){
			if(numbers[j]<numbers[p])
				p=j;
			if(p!=i){
				temp=numbers[p];
				numbers[p]=numbers[i];
				numbers[i]=temp;
			}
		}
	}
	for(i=0;i<numbersReaded;i++)
		printf(" %ld ",numbers[i]);
}
#endif
